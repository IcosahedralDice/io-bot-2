package gamelogic;

public class Attempt {
	private Function function;
	
	private String[] args;
	
	private String reply;
	
	public Attempt(Function f, String[] a, String r) {
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
}
