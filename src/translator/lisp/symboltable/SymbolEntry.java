package translator.lisp.symboltable;


public class SymbolEntry {
	private String identifier;

	public SymbolEntry(String identifer) {
		this.identifier = identifer;
	}

	public String getIdentifier() {
		return this.identifier;
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
