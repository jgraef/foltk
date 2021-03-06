package graef.foltk.formula.lexer;

import graef.foltk.formula.Location;


public class Token {
	private final Location loc;
	private final TokenType type;
	private final String str;
	
	public Token(Location loc, TokenType type, String str) {
		this.loc = loc;
		this.type = type;
		this.str = str;
	}
	
	public Token(TokenType type, String str) {
		this(Location.NONE, type, str);
	}
	
	public Token(Location loc, TokenType type, int cp) {
		this(loc, type, new String(new int[] {cp}, 0, 1));
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String toString() {
		return str;
	}
}
