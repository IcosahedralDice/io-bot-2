package commands.gamecommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamelogic.Functions;
import gamelogic.GameManager;

public class QueryCommand extends Command {
	public QueryCommand() {
		this.name = "query";
		this.help = "Querys a function. ";
		this.arguments = "<function> <input 1> <input 2> ... <input n>";
	}
	
	
	@Override
	protected void execute(CommandEvent event) {
		// TODO Auto-generated method stub
		String args = event.getArgs();
		//System.out.println(args);
		String reply = "";
		try {
			reply = GameManager.getFunction(event.getArgs(), event.getAuthor().getIdLong());
		} catch (IllegalArgumentException e) {
			//TODO giving the function arguments
			event.reply("Arguments are wrong. Consult the argument table please. ");
			//return;
		}
		event.reply(reply);
		
	}

}
