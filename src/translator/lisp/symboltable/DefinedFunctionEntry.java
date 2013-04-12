package translator.lisp.symboltable;

import java.util.Collection;

public class DefinedFunctionEntry extends FunctionEntry {
	
	private SymbolTable table;
	
	public DefinedFunctionEntry(String identifer, SymbolTable superTable, Collection<String> parameterIdentifiers){
		super(identifer, superTable, parameterIdentifiers);
		
		this.table = new SymbolTable();
	}
}
