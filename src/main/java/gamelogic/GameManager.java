package gamelogic;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class GameManager {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	private static long[] admins = {281300961312374785L};
	
	/**
	 * Checks to see whether a given user is authorised to 
	 * perform administrator operations (such as saving, etc). 
	 * Loops through the static long[] admins array. 
	 * @param id Discord user ID of the person
	 * @return whether they are authorised or not. 
	 */
	public static boolean isAdmin(long id) {
		for (long l : admins) {
			if (l == id) {return true;}
		}
		return false;
	}
	
	/**
	 * Gets the stats of the player with the specified ID. 
	 * @param id the ID of the player
	 * @return a nicely formatted String which can be printed
	 * to the Discord chat (but probably should be split into 2000
	 * character strings first). 
	 * @throws IllegalArgumentException if the method cannot find
	 * a player with that ID. 
	 */
	public static String getStats(long id) throws IllegalArgumentException {
		//Find a player with that ID
		for (Player p : players) {
			if (p.getID() == id) {
				return p.getAttemptsFormatted();
			}
		}
		throw new IllegalArgumentException("No player found with that ID. ");
	}
	
	/**
	 * Initialises the GameManager, i.e. loads all the
	 * entries stored as XML into the players ArrayList. 
	 */
	public static void init() {
		long[] ids;
		try {
			ids = PlayerParser.playerIDs();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return;
		}
		
		for (long l : ids) {
			try {
				players.add(PlayerParser.parsePlayer(l));
			} catch (IllegalArgumentException | ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
	}
	
	/**
	 * This method adds an query to the list. 
	 * @param args the arguments of the command. 
	 * @param id the ID of the user. 
	 * @return a reply to send back to the user
	 * @throws IndexOutOfBoundsException if there
	 * are not enough arguments for the command. 
	 */
	public static String query (String args, long id) 
		throws ArrayIndexOutOfBoundsException {
		String[] splitArgsF = args.split(" ");
		Function f = Function.valueOf(splitArgsF[0]);
		
		//Remove function argument
		String[] splitArgs = new String[splitArgsF.length -1];
		for (int i = 0; i < splitArgsF.length - 1; i++) {
			splitArgs[i] = splitArgsF[i+1];
		}
		
		String answer;
		try {
			answer = Functions.functionInterface(f, splitArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return "Incorrect arguments.";
		}
		
		Attempt a = new Attempt(AttemptType.QUERY, f, splitArgs, answer);
		String reply = a.getPrettyPrinted(0);
		
		//Find the correct player
		for (Player p : players) {
			if (p.getID() == id) {
				p.addAttempt(new Attempt(AttemptType.QUERY, f, splitArgs, answer));
				return reply;
			}
		}
		//Must add player
		players.add(new Player(id, new long[] {0,0,0,0,0,0}));
		players.get(players.size()-1).addAttempt(new Attempt(AttemptType.QUERY, f, splitArgs, answer));
		return reply;
	}

	/**
	 * This method adds a guess to the list. 
	 * @param args the arguments for the guess, including
	 * function name (args[0]) and the guess itself (args[1])
	 * @param id the id of the player making the guess
	 * @return a String which is to be replied to the user
	 * @throws ArrayIndexOutOfBoundsException if there are
	 * too few arguments provided by the user. 
	 */
	public static String guess (String args, long id)
		throws ArrayIndexOutOfBoundsException {
		String[] splitArgsF = args.split(" ");
		Function f = Function.valueOf(splitArgsF[0]);
		String guessVal = splitArgsF[1];
		
		//Remove function and guess
		String[] splitArgs = new String[splitArgsF.length-2];
		for (int i = 0; i < splitArgsF.length - 2; i++) {
			splitArgs[i] = splitArgsF[i+2];
		}
		
		String actualAnswer;
		try {
			actualAnswer = Functions.functionInterface(f, splitArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return "Incorrect arguments.";
		}
		
		Attempt a = new Attempt(AttemptType.GUESS, f, splitArgs, actualAnswer, guessVal);
		String reply = a.getPrettyPrinted(0);
		boolean correct = actualAnswer.contentEquals(guessVal);
		
		//Find the correct player
		for (Player p : players) {
			if (p.getID() == id) {
				p.addAttempt(a);
				if (!correct) {p.addScore(f, 2);}
				return reply;
			}
		}
		//Must add player
		players.add(new Player(id, new long[] {0,0,0,0,0,0}));
		players.get(players.size()-1).addAttempt(a);
		if (!correct) {players.get(players.size()-1).addScore(f, 2);}
		return reply;
	}
	
	/**
	 * Saves the current state of the game to files. As
	 * this is an administrator command the program should
	 * first call isAdmin on the player's ID to determine whether
	 * they are an admin or not before performing this operation.
	 * @return whether the saveGame suceeded. You might wish
	 * to add some error reporting (i.e. if the output is false, then
	 * reply "Something went wrong!"). 
	 */
	public static boolean saveGame() {
		boolean allGood = true;
		
		//Save IDs
		long[] playerIDs = new long[players.size()];
		for (int i = 0; i < players.size(); i++) {
			playerIDs[i] = players.get(i).getID();
		}
		try {
			PlayerParser.saveIDs(playerIDs);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			allGood = false;
		}
		
		//Save the player
		for (Player p : players) {
			try {
				PlayerParser.savePlayer(p);
			} catch (ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
				allGood = false;
			}
		}
		return allGood;
	}
}
