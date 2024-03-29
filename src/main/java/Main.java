import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.*;
import commands.gamecommands.GuessCommand;
import commands.gamecommands.QueryCommand;
import commands.gamecommands.SaveCommand;
import commands.gamecommands.StatsCommand;
import gamelogic.GameManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws LoginException, IOException {
		//Command parser stuff
		CommandClientBuilder ccBuilder = new CommandClientBuilder();
		ccBuilder.setPrefix("io.");  //Set prefix to c.
		ccBuilder.useDefaultGame();
		ccBuilder.setOwnerId("281300961312374785");     //This is my userID.
		//Adding commands
		ccBuilder.addCommands(new PingCommand(),
								new StatsCommand(),
								new QueryCommand(),
								new SaveCommand(),
								new GuessCommand(),
								new EchoCommand());
		
		GameManager.init();
		Logger.initialise();
		
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = System.getenv("APITOKEN");
		if (token == null) {
			System.out.print("Input API Token: ");
			token = sc.nextLine();
		}
		builder.setToken(token);
		builder.addEventListener(ccBuilder.build());
		builder.addEventListener(new Logger());
		builder.build();
	}
}
