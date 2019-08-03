package gamelogic;

import java.util.ArrayList;

public class Player {
	
	private long id;
	
	private ArrayList<Attempt> attempts = new ArrayList<Attempt>();
	
	private int[] numAttempts = new int[Functions.NUM_FUNCTIONS];
	
	public Player (long playerID) {
		this.id = playerID;
		
		//Clears the number of attempts
		for (int i = 0; i < Functions.NUM_FUNCTIONS; i++) {
			numAttempts[i] = 0;
		}
	}
	
	public ArrayList<Attempt> getAttempts() {
		return this.attempts;
	}
	
	/**
	 * @return the ID of the player. 
	 */
	public long getID() {
		return this.id;
	}
	
	/**
	 * Generates a nicely formatted string of all the
	 * attempts the player has made at all the functions. 
	 * They should all be in order. 
	 * @return the string
	 */
	public String getAttemptsFormatted() {
		ArrayList<ArrayList<Attempt>> attemptsList = new ArrayList<ArrayList<Attempt>>();
		StringBuilder returner = new StringBuilder();
		for (Attempt a : attempts) {
			//Sort attempts by function
			for (ArrayList<Attempt> l : attemptsList) {
				if (a.getFunction().equals(l.get(0).getFunction())) {
					l.add(a);
				}
			}
			//Nothing with that function has been added yet so we add a new function
			ArrayList<Attempt> newlist = new ArrayList<Attempt>();
			newlist.add(a);
			attemptsList.add(newlist);
		}
		
		//Builds the string
		String[] fArgs; int counter = 1;
		for(ArrayList<Attempt> l : attemptsList) {
			returner.append("``Function: ");
			returner.append(l.get(0).getFunction().name());
			returner.append("``\n");
			counter = 1;
			for(Attempt a : l) {
				fArgs = a.getArgs();
				//example: ``ioA(1, 2, 3) = 2 [3]``
				returner.append("``");
				returner.append(a.getFunction());
				returner.append("(");
				for (int i = 0; i < fArgs.length - 1; i++) {
					String s = fArgs[i];
					returner.append(s);
					returner.append(", ");
				}
				returner.append(fArgs[fArgs.length-1]);
				returner.append(") = ");
				returner.append(a.getReply());
				returner.append(" [");
				returner.append(counter);
				returner.append("]``\n");
				counter++;
			}
		}
		
		return returner.toString();
	}
	
	public String attempt(String[] args) {
		//TODO: implement functions
		return "";
	}
	
	/**
	 * Adds an attempt to the Player's ArrayList of
	 * stored attempts. 
	 * @param a the attempt to add
	 */
	public void addAttempt (Attempt a) {
		this.attempts.add(a);
	}
}
