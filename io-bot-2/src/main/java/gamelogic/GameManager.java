package gamelogic;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GameManager {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	
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
	 * This method adds an attempt to the list. 
	 * @param args the arguments of the command. 
	 * @param id the ID of the user. 
	 * @return a reply to send back to the user
	 * @throws IndexOutOfBoundsException if there
	 * are not enough arguments for the command. 
	 */
	public static String getFunction (String args, long id) 
		throws IndexOutOfBoundsException {
		String[] splitArgsF = args.split(" ");
		Function f = Function.valueOf(splitArgsF[0]);
		
		//Remove function argument
		String[] splitArgs = new String[splitArgsF.length -1];
		for (int i = 0; i < splitArgsF.length - 1; i++) {
			splitArgs[i] = splitArgsF[i+1];
		}
		
		//TODO: get actual reply
		String reply = splitArgs[0];
		
		//Find the correct player
		for (Player p : players) {
			if (p.getID() == id) {
				p.addAttempt(new Attempt(f, splitArgs, reply));
				return reply;
			}
		}
		//Must add player
		players.add(new Player(id));
		players.get(players.size()-1).addAttempt(new Attempt(f, splitArgs, reply));
		return reply;
	}
}
