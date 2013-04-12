package translator.lisp;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import translator.lisp.symboltable.FunctionEntry;
import translator.lisp.symboltable.SymbolTable;

public class LISPtoCTranslator {
	
	private static final String STACK_SOS = "+START";
	
	private SymbolTable table;

	public LISPtoCTranslator() {
		table = new SymbolTable();

		ArrayList<String> list = new ArrayList<String>();
		list.add("num1");
		list.add("num2");
		
		// Add the intrinsic functions
		table.addIntrinsicFunctionEntry("ADD", list);
		table.addIntrinsicFunctionEntry("SUBTRACT", list);
		table.addIntrinsicFunctionEntry("MULTIPLY", list);
		table.addIntrinsicFunctionEntry("QUOTIENT", list);
		table.addIntrinsicFunctionEntry("REMAINDER", list);
		table.addIntrinsicFunctionEntry("SET", list);
	}

	public String translateStatment(String line) {
		// Stack for store variables
		Stack<String> stack = new Stack<String>();

		// Buffer for what we are reading
		String buffer = "";

		for (int i = 0; i < line.length(); i++) {
			char letter = line.charAt(i);

			// Starting a new statement
			if (letter == '(') {
				stack.push(STACK_SOS);
			//Evaluate the statement
			} else if (letter == ')') {
				//Is there anything in the buffer and if so push it
				if (! "".equals(buffer))
				{
					stack.push(buffer);
					buffer = "";
				}
				
				ArrayList<String> entries = new ArrayList<String>();

				// Pop things off the stack until we hit a start
				while (!STACK_SOS.equals(stack.peek())) {
					entries.add(0, stack.pop());
				}

				// Get rid of the start
				stack.pop();
				
				//Get the command
				String command = entries.get(0);
				
				//Get the parameters
				List<String> parameters = entries.subList(1, entries.size());
				
				if (!table.hasIdentifer(command)){
					throw new TranslatorException(String.format("The identifier '%s' does not exist!", command));
				}
				
				FunctionEntry entry = (FunctionEntry) table.getEntry(command);
				
				stack.push(entry.generateMethodCall(parameters));
			} else if (letter == ' ') {
				stack.push(buffer);
				buffer = "";
			}
			else{
				buffer += String.valueOf(letter);
			}
		}

		return stack.pop() + ";";
	}
}
