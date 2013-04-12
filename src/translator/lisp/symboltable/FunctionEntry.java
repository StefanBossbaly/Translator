package translator.lisp.symboltable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import translator.lisp.TranslatorException;

public abstract class FunctionEntry extends SymbolEntry {

	/**
	 * Number of parameters that our function accepts
	 */
	protected ArrayList<String> parameterIdentifiers;
	
	/**
	 * Symbol table for the outside scope
	 */
	protected SymbolTable superTable;

	public FunctionEntry(String identifer, SymbolTable superTable, Collection<String> parameterIdentifiers) {
		super(identifer);
		this.parameterIdentifiers = new ArrayList<String>(parameterIdentifiers);
		this.superTable = superTable;
	}
	
	public String generateMethodCall(List<String> parameters) {
		// Make sure we have the same amount of parameters
		if (parameters.size() != parameterIdentifiers.size()) {
			throw new TranslatorException(String.format("Function %s requires %d parameters, %d parameters provided", getIdentifier(),
					parameterIdentifiers.size(), parameters.size()));
		}

		return String.format("%s(%s)", super.getIdentifier(), join(parameters.toArray(), ","));
	}

	public String generateMethodDeclaration() {
		ArrayList<String> parameterDeclarations = new ArrayList<String>();

		for (String parameter : parameterIdentifiers) {
			parameterDeclarations.add(String.format("int %s", parameter));
		}

		return String.format("int %s(%s)", getIdentifier(), join(parameterDeclarations.toArray(), ","));
	}

	private String join(Object r[], String d) {
		if (r.length == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < r.length - 1; i++)
			sb.append(r[i].toString() + d);
		return sb.toString() + r[i];
	}
}
