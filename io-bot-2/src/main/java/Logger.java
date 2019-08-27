import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Logger extends ListenerAdapter {
	private static BufferedWriter br;
	
	public static void initialise() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("./log-");
		sb.append(LocalDate.now().toString());
		sb.append("-");
		sb.append(LocalTime.now().getHour());
		sb.append("-");
		sb.append(LocalTime.now().getMinute());
		sb.append("-");
		sb.append(LocalTime.now().getSecond());
		sb.append(".txt");
		File f = new File(sb.toString());
		try {
			f.createNewFile();
		} catch (Exception e) {e.printStackTrace();}
		FileWriter fw = new FileWriter(f);
		br = new BufferedWriter(fw);
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(event.getAuthor().getName());
		sb.append("]\t @ ");
		sb.append(LocalDate.now().toString());
		sb.append("::");
		sb.append(LocalTime.now().toString());
		sb.append(": ");
		sb.append(event.getMessage().getContentRaw());
		String sbs = sb.toString();
		System.out.println(sbs);
		try {
			br.write(sbs);
			br.newLine();
			br.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}