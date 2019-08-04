package gamelogic;

public class Functions {
	/**
	 * Represents the number of functions currently in play. 
	 */
	public static int NUM_FUNCTIONS = 6;
	
	public static String functionInterface (Function f, String[] args) {
		switch(f) {
			case IO_A:
				return IO_A(args);
			case IO_B:
				return IO_B(args);
			case IO_C:
				return IO_C(args);
			case IO_D:
				return IO_D(args);
			case IO_E:
				return IO_E(args);
			case IO_F:
				return IO_F(args);
		}
		
		
		return null;
		
	}
	
	private static String IO_A (String[] args) 
			throws IllegalArgumentException {
		
		return args[0];
	}
	
	private static String IO_B (String[] args) 
			throws IllegalArgumentException  {
		return args[0];
	}
	
	private static String IO_C (String[] args) 
			throws IllegalArgumentException  {
		return args[0];
	}
	
	private static String IO_D (String[] args) 
			throws IllegalArgumentException  {
		return args[0];
	}
	
	private static String IO_E (String[] args) 
			throws IllegalArgumentException  {
		return args[0];
	}
	
	private static String IO_F (String[] args) 
			throws IllegalArgumentException  {
		return args[0];
	}
}
