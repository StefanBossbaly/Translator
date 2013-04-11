import java.util.ArrayList;
import java.util.Collection;

public class SymbolTable {

	public static enum Type {
		intVar, definedFunction, intrinsicFunction
	};

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
	public void addEntry(String identifier, Type type) {
		if (hasIdentifer(identifier)) {
			throw new RuntimeException("Identifier already exist in symbol table");
		}

		SymbolEntry entry = new SymbolEntry(identifier, type);

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

	public class SymbolEntry {
		private String identifier;
		private Type type;

		public SymbolEntry(String identifer, Type type) {
			this.identifier = identifer;
			this.type = type;
		}

		public String getIdentifier() {
			return this.identifier;
		}

		public Type getType() {
			return this.type;
		}

		public boolean isIdentifier(String identifer) {
			if (identifer == null)
				return false;

			return identifer.equals(this.getIdentifier());
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof SymbolEntry)) {
				return false;
			}

			SymbolEntry entry = (SymbolEntry) other;

			return entry.getIdentifier().equals(this.getIdentifier());
		}
	}

	public class FunctionEntry extends SymbolEntry {

		/**
		 * Number of parameters that our function accepts
		 */
		private ArrayList<String> parameterIdentifiers;

		/**
		 * Symbol table for this scope
		 */
		private SymbolTable table;

		
		public FunctionEntry(String identifer, Type type, Collection<String> parameterIdentifiers) {
			super(identifer, type);
			this.parameterIdentifiers = new ArrayList<String>(parameterIdentifiers);
			
			table = new SymbolTable();
		}

	}
	
	public class VariableEntry extends SymbolEntry{
		
		public VariableEntry(String identifer, Type type){
			super(identifer, type);
		}
	}
}
