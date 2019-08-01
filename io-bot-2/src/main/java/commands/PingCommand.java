package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {
	public PingCommand() {
		this.name = "ping";
		this.aliases = new String[] {"pong", "ping!"};
		this.help = "Pings to check whether the bot is working. ";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		// TODO Auto-generated method stub
		event.reply("Pong!");
	}

}
