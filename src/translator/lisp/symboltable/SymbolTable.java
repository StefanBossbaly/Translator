package translator.lisp.symboltable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import translator.lisp.TranslatorException;

public class SymbolTable {
	
	private ArrayList<SymbolEntry> list;

	/**
	 * Constructs an empty symbol table
	 */
	public SymbolTable() {
		list = new ArrayList<SymbolEntry>();
	}

	/**
	 * Checks to see if the symbol table has the identifier
	 * 
	 * @param identifer
	 * @return true if the identifier is contained in the symbol table
	 */
	public boolean hasIdentifer(String identifier) {
		for (SymbolEntry entry : list) {
			if (entry.isIdentifier(identifier))
				return true;
		}

		return false;
	}

	/**
	 * Gets the entry for the identifier from the symbol table
	 * 
	 * @param identifier
	 * @return entry for the identifier from the symbol table
	 */
	public SymbolEntry getEntry(String identifier) {
		for (SymbolEntry entry : list) {
			if (entry.isIdentifier(identifier))
				return entry;
		}

		throw new RuntimeException("Identifier does not exist in symbol table");
	}

	/**
	 * Adds the entry into the symbol table
	 * 
	 * @param identifier
	 *            the identifier of the entry
	 * @param type
	 *            the type of the entry
	 */
	public void addEntry(String identifier) {
		if (hasIdentifer(identifier)) {
			throw new RuntimeException(
					"Identifier already exist in symbol table");
		}

		SymbolEntry entry = new SymbolEntry(identifier);

		list.add(entry);
	}
	
	public void addFunctionEntry(String idenifier, List<String> parameters){
		if (hasIdentifer(idenifier)) {
			throw new RuntimeException(
					"Identifier already exist in symbol table");
		}
		
		FunctionEntry entry = new FunctionEntry(idenifier, parameters);
		
		list.add(entry);

	}

	/**
	 * Removes the entry with the identifier
	 * 
	 * @param identifier
	 *            the identifier of the entry
	 */
	public void removeEntry(String identifier) {
		SymbolEntry entry = getEntry(identifier);
		list.remove(entry);
	}
}
