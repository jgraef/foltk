package graef.foltk.formula;

public class Location {
	public static final Location NONE = new Location("<none>", 0, 0);
	private final String name;
	private final int line;
	private final int col;
	
	public Location(String name, int line, int col) {
		this.name = name;
		this.line = line;
		this.col = col;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return col;
	}
	
	public String toString() {
		return String.format("%s:%d:%d", name, line, col);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + line;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (col != other.col)
			return false;
		if (line != other.line)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
