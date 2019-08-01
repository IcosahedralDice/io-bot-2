import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws LoginException {
		//Command parser stuff
		CommandClientBuilder ccBuilder = new CommandClientBuilder();
		ccBuilder.setPrefix("io.");  //Set prefix to c.
		ccBuilder.useDefaultGame();
		ccBuilder.setOwnerId("281300961312374785");     //This is my userID.
		//Adding commands
		ccBuilder.addCommands(new PingCommand());
		
		
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		System.out.print("Input API Token: ");
		String token = sc.nextLine();
		builder.setToken(token);
		builder.addEventListener(ccBuilder.build());
		builder.build();
	}
}
