package translator.lisp.symboltable;

import java.util.Collection;

public class IntrinsicFunctionEntry extends FunctionEntry {	
	
	public IntrinsicFunctionEntry(String identifer, SymbolTable superTable, Collection<String> parameterIdentifiers){
		super(identifer, superTable, parameterIdentifiers);
	}

}
