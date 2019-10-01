package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class EchoCommand extends Command {

	public EchoCommand() {
		this.name = "echo";
		this.help = "Echos a text";
		this.guildOnly = false;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		// TODO Auto-generated method stub
		event.reply(event.getArgs());
	}

}
