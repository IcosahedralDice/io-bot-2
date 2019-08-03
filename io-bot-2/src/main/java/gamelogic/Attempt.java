package gamelogic;

public class Attempt {
	private AttemptType type;
	
	private Function function;
	
	private String[] args;
	
	private String reply;
	
	public Attempt(AttemptType t, Function f, String[] a, String r) {
		this.type = t;
		this.function = f;
		this.args = a;
		this.reply = r;
	}
	
	public Function getFunction() {
		return this.function;
	}
	
	public String[] getArgs() {
		return this.args;
	}
	
	public String getReply() {
		return this.reply;
	}
	
	public AttemptType getType() {
		return this.type;
	}
}
