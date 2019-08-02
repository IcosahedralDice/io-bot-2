package gamelogic;

public enum AttemptType {
	/**
	 * Player queried the bot. 
	 */
	QUERY,
	/**
	 * Player guessed the answer to a function. 
	 */
	GUESS,
	/**
	 * Player guessed the function. 
	 */
	FUNCTION_GUESS
}
