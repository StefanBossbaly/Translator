public class LISPtoCTranslator implements Translator {
	private SymbolTable table;
	
	public LISPtoCTranslator(){
		table = new SymbolTable();
		
		//Add the intrinsic functions
		table.addEntry("ADD", SymbolTable.Type.intrinsicFunction);
		table.addEntry("SUBTRACT", SymbolTable.Type.intrinsicFunction);
		table.addEntry("MULTIPLY", SymbolTable.Type.intrinsicFunction);
		table.addEntry("QUOTIENT", SymbolTable.Type.intrinsicFunction);
		table.addEntry("REMAINDER", SymbolTable.Type.intrinsicFunction);
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
}
