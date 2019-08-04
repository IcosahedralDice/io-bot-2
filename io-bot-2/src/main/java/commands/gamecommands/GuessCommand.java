package commands.gamecommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamelogic.GameManager;

public class GuessCommand extends Command {
	public GuessCommand() {
		this.name = "guess";
		this.arguments = "<function name> <guess> <arg 1> <arg 2> <arg 3>";
	}

	@Override
	protected void execute(CommandEvent event) {
		String args = event.getArgs();
		//System.out.println(args);
		String reply = "";
		try {
			reply = GameManager.guess(event.getArgs(), event.getAuthor().getIdLong());
		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException a) {
			//TODO giving the function arguments
			event.reply("Arguments are wrong. Consult the argument table please. ");
			return;
		}
		
		final String areply = reply;
		event.getAuthor().openPrivateChannel().queue((channel) -> {
			channel.sendMessage(areply).queue();
		});		

	}

}
