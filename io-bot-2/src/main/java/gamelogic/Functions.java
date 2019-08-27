package gamelogic;

public class Functions {
	/**
	 * Represents the number of functions currently in play. 
	 */
	public static int NUM_FUNCTIONS = 6;
	
	public static String functionInterface (Function f, String[] args) 
		throws IllegalArgumentException {
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
	
	/**
	 * Gets the index of a function (0 for IO_A, 1 for IO_B, etc)
	 * @param f the function
	 * @return the index
	 */
	public static int indexOfFunc (Function f) {
		switch (f) {
			case IO_A:
				return 0;
			case IO_B:
				return 1;
			case IO_C:
				return 2;
			case IO_D:
				return 3;
			case IO_E:
				return 4;
			case IO_F:
				return 5;
			default:
				return -1;
		}
	}
	
	private static String IO_A (String[] args) 
			throws IllegalArgumentException {
		if (args.length != 3) {
			throw new IllegalArgumentException("Incorrect number of arguments!");
		}
		int a, b, c;
		try {
			a = Integer.parseInt(args[0]);
			b = Integer.parseInt(args[1]);
			c = Integer.parseInt(args[2]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Not integers!");
		}
		
		return Integer.toString((a+b+c));
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
