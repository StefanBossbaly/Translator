   import java.util.Scanner;
   import java.io.*;
   
   public class LLRecognizer {
   
      static final char EOI = '$';
      static final char LP = '(';
      static final char RP = ')';
      static final char POS = '+';
      static final char NEG = '-';
      static final char DOT = '.';
      static final char EXP = 'E';
      static final char QUOTE = '"';
      static final char BLANK = ' ';
      
      static Scanner input;
      
      static String s;
      static int index = 0;
      
      static boolean rdpDebug = true;
      static boolean echoDebug = true;
      static boolean charDebug = true;
      static int level = 0;
      
      public static void main(String[] args) throws FileNotFoundException {
         System.out.println("PMJ's LLRecognizer ...");
         if(args.length > 0) {
            input = new Scanner(new File(args[0].trim()));
            s = readInput(input) + EOI;
            System.out.println(s);
            if(args.length > 1) {
               rdpDebug = Boolean.parseBoolean(args[1]);
               if(args.length > 2) {
                  echoDebug = Boolean.parseBoolean(args[2]);
                  if(args.length > 3) {
                     charDebug = Boolean.parseBoolean(args[3]);
                  }
               }
            }
            System.out.println(recognize());
         } 
         else {
            System.out.println("---ERROR: missing filename argument");
         }
      }
      
      public static String readInput(Scanner input) {
         String result = "";
         while(input.hasNextLine()) {
            result = result + input.nextLine() + BLANK;
         }
         return result;
      }
      
      public static boolean recognize() {
         return program();
      }
      
      public static boolean more() {
         nextIndex();
         return (index < s.length()) && (s.charAt(index) != EOI);
      }
      
      public static boolean program() {
         push("program");
         int programIndex = index;
         boolean result = expression();
         if(result && more()) {
            result = program();
         }
         return pop(result,"program",programIndex);
      }
      
      public static boolean expression() {
         push("expression");
         int expressionIndex = index;
         nextIndex();
         boolean result = atom();
         if(!result) {
            result = list();
         }
         return pop(result,"expression",expressionIndex);
      }
   
      public static boolean atom() {
         push("atom");
         int atomIndex = index;
         boolean result = identifier();
         if(!result) {
            result = literal();
         }
         return pop(result,"atom",atomIndex);
      }
      
      public static boolean identifier() {
         push("identifier");
         int identifierIndex = index;
         boolean result = letter();
         boolean consume = result;
         while(consume) {
            next();
            consume = letter() || numeral() || other();
         }
         return pop(result,"identifier",identifierIndex);
      }
      
      static String LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
      public static boolean letter() {
         return LETTER.indexOf(s.charAt(index)) >= 0;
      }
      
      static String NUMERAL = "0123456789";
      public static boolean numeral() {
         return NUMERAL.indexOf(s.charAt(index)) >= 0;
      }
      
      static String OTHER = "_";
      public static boolean other() {
         return OTHER.indexOf(s.charAt(index)) >= 0;
      }
      
      public static boolean literal() {
         push("literal");
         int literalIndex = index;
         boolean result = number();
         if(!result) {
            result = string();
         }
         return pop(result,"literal",literalIndex);
      }
         
      public static boolean number() {
         push("number");
         int numberIndex = index;
         index = nextIndex();
         boolean result = sign();
         result = numsWFE();
         return pop(result,"number",numberIndex);
      }
      
      public static boolean sign() {
         push("sign");
         int signIndex = index;
         char c = s.charAt(index);
         boolean result = (c == POS) || (c == NEG);
         if(result) {
            next();
         }
         return pop(result,"sign",signIndex);
      }
      
      static String followSet = "" + BLANK + LP + RP;
   
      public static boolean numsWFE() {
         boolean result = false;
         while(numeral()) {   //numsW
            next();
            result = true;
         }
         if(s.charAt(index) == DOT) {
            next();
            result = false;
            while(numeral()) {   //numsF
               next();
               result = true;
            }
            if(s.charAt(index) == EXP) {
               next();
               result = sign() && numeral();
               boolean consume = result;
               while(consume) {  //numsE
                  next();
                  consume = numeral();
               }
            }
         } 
         result = result && isInSet(s.charAt(index),followSet);
         return result;
      }
      
      public static boolean isInSet(char c, String followSet) {
         return followSet.indexOf(c) >= 0;
      }
      
      public static boolean string() {
         push("string");
         int stringIndex = index;
         boolean result = s.charAt(index) == QUOTE;
         if(result) {
            next();
            while((s.charAt(index) != QUOTE) && (s.charAt(index) != EOI)) {
               next();
            }
            result = s.charAt(index) == QUOTE;
            if(result) {
               next();
            }
         }
         return pop(result,"string",stringIndex);
      }
      
      public static int next() {
         if(charDebug) {
            System.out.println("<"+s.charAt(index)+">");
         }
         index = index + 1;
         return index;
      }
      
      public static int nextIndex() {
         while((index < s.length()) && (s.charAt(index) == BLANK)) {
            index = next();
         }
         return index;
      }
      
      public static boolean list() {
         push("list");
         int listIndex = index;
         boolean result = (s.charAt(nextIndex()) == LP);
         if(result) {
            next();
            index = nextIndex();
            result = (s.charAt(index) == RP);
            if(result) {
               next();
            } 
            else {
               result = sequence();
               if(result) {
                  result = (s.charAt(nextIndex()) == RP);
                  if(result) {
                     next();
                  }
               }
            }
         }
         return pop(result,"list",listIndex);
      }
      
      public static boolean sequence() {
         push("sequence");
         int sequenceIndex = index;
         boolean result = expression();
         if(result) {
            if(more() && (s.charAt(index) != RP)) {
               result = sequence();
            }
         }
         return pop(result,"sequence",sequenceIndex);
      }
      
      public static void push(String name) {
         if(rdpDebug) {
            level = level + 1;
            System.out.println(tab(level)+"<<< "+s.charAt(index)+" <<< "+name);
         }
      }
      
      public static boolean pop(boolean result, String name, int startIndex) {
         if(rdpDebug) {
            System.out.print(tab(level)+
                             ">>> "+s.charAt(index)+" >>> "+
                             name+BLANK+result);
            if(echoDebug) {
               System.out.print(" \""+s.substring(startIndex,index)+"\"");
            }
            System.out.println();
            level = level - 1;
         }
         return result;
      }
      
      public static String tab(int level) {
         String result = "";
         for(int i=0; i<level; i++) {
            result = result + "  ";
         }
         return result;
      }
   }