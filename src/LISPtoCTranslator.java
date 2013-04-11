import java.util.ArrayList;
import java.util.Stack;

import javax.sql.rowset.Predicate;

public class LISPtoCTranslator implements Translator {

	private static enum StackType {
		intLiteral, name, start, statement
	};

	private SymbolTable table;

	public LISPtoCTranslator() {
		table = new SymbolTable();

		// Add the intrinsic functions
		table.addEntry("ADD", SymbolTable.Type.intrinsicFunction);
		table.addEntry("SUBTRACT", SymbolTable.Type.intrinsicFunction);
		table.addEntry("MULTIPLY", SymbolTable.Type.intrinsicFunction);
		table.addEntry("QUOTIENT", SymbolTable.Type.intrinsicFunction);
		table.addEntry("REMAINDER", SymbolTable.Type.intrinsicFunction);
		table.addEntry("SET", SymbolTable.Type.intrinsicFunction);
	}

	public String translateStatment(String line) {
		// Stack for store variables
		Stack<StackEntry> stack = new Stack<StackEntry>();

		// Buffer for what we are reading
		String buffer = "";

		for (int i = 0; i < line.length(); i++) {
			char letter = line.charAt(i);

			// Starting a new thingy
			if (letter == '(') {
				stack.push(StackEntry.ENTRY_START);
			} else if (letter == ')') {
				//Is there anything in the buffer and if so push it
				if (! "".equals(buffer))
				{
					stack.push(new StackEntry(buffer, StackType.name));
					buffer = "";
				}
				
				ArrayList<StackEntry> entries = new ArrayList<StackEntry>();

				// Pop things off the stack until we hit a start
				while (stack.peek().type != StackType.start) {
					entries.add(0, stack.pop());
				}

				// Get rid of the start
				stack.pop();
				
				StackEntry command = entries.get(0);
				StackEntry paramater1 = entries.get(1);
				StackEntry paramater2 = entries.get(2);
				
				if (!table.hasIdentifer(command.getEntry())){
					throw new TranslatorException(String.format("The identifier '%s' does not exist!", command.getEntry()));
				}
				
				//TODO put this in a different place
				//TODO add more functions
				if ("ADD".equals(command.getEntry()))
				{
					String s = add(paramater1.getEntry(), paramater2.getEntry());
					stack.push(new StackEntry(s, StackType.statement));
				}
			} else if (letter == ' ') {
				stack.push(new StackEntry(buffer, StackType.name));
				buffer = "";
			}
			else{
				buffer += String.valueOf(letter);
			}
		}

		return stack.pop().getEntry() + ";";
	}

	@Override
	public String add(String paramater1, String paramater2) {
		return String.format("add(%s, %s)", paramater1, paramater2);
	}

	@Override
	public String subtract(String paramater1, String paramater2) {
		return String.format("subtract(%s, %s)", paramater1, paramater2);
	}

	@Override
	public String multiply(String paramater1, String paramater2) {
		return String.format("multiply(%s, %s)", paramater1, paramater2);
	}

	@Override
	public String quotient(String paramater1, String paramater2) {
		return String.format("quotient(%s, %s)", paramater1, paramater2);
	}

	@Override
	public String remainder(String paramater1, String paramater2) {
		return String.format("remainder(%s, %s)", paramater1, paramater2);
	}

	private static class StackEntry {

		public static final StackEntry ENTRY_START = new StackEntry(null, StackType.start);

		private String entry;

		private StackType type;

		public StackEntry(String entry, StackType type) {
			this.entry = entry;
			this.type = type;
		}

		public String getEntry() {
			return this.entry;
		}

		public StackType getType() {
			return this.type;
		}
	}
}
