package translator.lisp.symboltable;

import java.util.Collection;
import java.util.List;

import translator.lisp.TranslatorException;

public class SetFunctionEntry extends FunctionEntry {

	public SetFunctionEntry(String identifer, SymbolTable superTable, Collection<String> parameterIdentifiers) {
		super(identifer, superTable, parameterIdentifiers);
	}
	
	public String generateMethodCall(List<String> parameters) {
		if (parameters.size() != 2) {
			throw new TranslatorException(String.format("Function %s requires %d parameters, %d parameters provided", getIdentifier(),
					2, parameters.size()));
		}
		
		String output = "";
		String varName = parameters.get(0);
		
		if (!superTable.hasIdentifer(varName))
		{
			superTable.addVariableEntry(parameters.get(0));
			output += ((VariableEntry) superTable.getEntry(parameters.get(0))).getVariableDeclaration() + ";\n";
		}
		
		output += ((VariableEntry) superTable.getEntry(parameters.get(0))).getAssigmentStatement(parameters.get(1));
		
		return output;
	}

}
