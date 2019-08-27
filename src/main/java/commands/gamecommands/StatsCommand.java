package commands.gamecommands;

import java.util.ArrayList;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamelogic.GameManager;
import net.dv8tion.jda.core.entities.User;

public class StatsCommand extends Command {

	public StatsCommand() {
		this.name = "stats";
		this.help = "View your stats in a DM";
		this.guildOnly = false;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		String stats;
		User u = event.getAuthor();
		try {
			stats = GameManager.getStats(event.getAuthor().getIdLong());
			System.out.println("Stats for user " + u.getId() + ": ");
			System.out.println(stats);
		} catch (IllegalArgumentException e) {
			event.reply("You haven't had any attempts. Cannot display stats!");
			return;
		}
		
		ArrayList<String> splitStats = CommandEvent.splitMessage(stats);
		for (String s : splitStats) {System.out.println(s);}
        u.openPrivateChannel().queue((channel) ->
        {
        	for (String s : splitStats) {
        		channel.sendMessage(s).queue();
        	}
        });
	}

}
