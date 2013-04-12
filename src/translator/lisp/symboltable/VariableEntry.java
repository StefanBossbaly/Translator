package translator.lisp.symboltable;


public class VariableEntry extends SymbolEntry {

	public VariableEntry(String identifer) {
		super(identifer);
	}

	public String getVariableDeclaration() {
		return String.format("int %s", getIdentifier());
	}

	public String getAssigmentStatement(String rhand) {
		return String.format("%s = %s", getIdentifier(), rhand);
	}
}
