package graef.foltk.formula.lexer;

import java.util.HashMap;

public class Internalizer<T> {
	private final HashMap<T, T> storage;
	
	public Internalizer() {
		storage = new HashMap<>();
	}
	
	public T internalize(T x) {
		T y = storage.get(x);
		if (y == null) {
			storage.put(x, x);
			return x;
		}
		else {
			return y;
		}
	}
	
	public T get(T x) {
		return storage.get(x);
	}
}
