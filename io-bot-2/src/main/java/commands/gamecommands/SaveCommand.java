package commands.gamecommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamelogic.GameManager;

public class SaveCommand extends Command {
	public SaveCommand() {
		this.name = "save";
		this.aliases = new String[] {"s"};
		this.help = "Saves the current state of the game. Can only"
				+ "be used by an admin of the IO game. ";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		// TODO Auto-generated method stub
		if (!GameManager.isAdmin(event.getAuthor().getIdLong())) {
			event.reply("This is an admin command!");
			return;
		}
		
		boolean allGood = GameManager.saveGame();
		if (!allGood) {
			event.reply("Something went wrong here. Please check the error logs. ");
		} else {
			event.reply("Done. ");
		}
	}

}
