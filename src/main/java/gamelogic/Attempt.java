package gamelogic;

public class Attempt {
	private AttemptType type;
	
	private Function function;
	
	private String[] args;
	
	private String answer;
	
	private String guess;
	
	public Attempt(AttemptType t, Function f, String[] a, String r) {
		this.type = t;
		this.function = f;
		this.args = a;
		this.answer = r;
	}
	
	public Attempt(AttemptType t, Function f, String[] a, String r, String g) {
		this.type = t;
		this.function = f;
		this.args = a;
		this.answer = r;
		this.guess = g;
	}
	
	public Function getFunction() {
		return this.function;
	}
	
	public String[] getArgs() {
		return this.args;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public AttemptType getType() {
		return this.type;
	}
	
	public String getGuess() {
		return this.guess;
	}
	
	public String getPrettyPrinted(int counter) {
		StringBuilder returner = new StringBuilder();
		returner.append("``");
		returner.append(this.getFunction());
		returner.append("(");
		for (int i = 0; i < args.length - 1; i++) {
			String s = args[i];
			returner.append(s);
			returner.append(", ");
		}
		returner.append(args[args.length-1]);
		returner.append(") = ");
		returner.append(this.getAnswer());
		returner.append(" [");
		returner.append(counter);
		returner.append("]");
		
		if (this.type.equals(AttemptType.GUESS) || this.type.equals(AttemptType.FUNCTION_GUESS)) {
			if (this.guess.contentEquals(this.answer)) {
				returner.append(" (Correct) ");
			} else {
				returner.append(" (Wrong guess of ");
				returner.append(this.guess);
				returner.append(")");
			}
		}
		returner.append("``");
		
		
		return returner.toString();
	}
}
