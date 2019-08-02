package gamelogic;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class PlayerParser {
	/**
	 * Parses a player based on the id given. It
	 * uses this to find an XML file containing all
	 * information about the player. It will then
	 * put that information into the Player object
	 * which it returns. 
	 * @param id the discord user ID of the specified
	 * player. This is used to select and find the files
	 * required. 
	 * @return a Player object with the data found in the
	 * file. 
	 * @throws IllegalArgumentException if there cannot
	 * be found a file with the specified user ID. 
	 * @throws ParserConfigurationException if the XML file
	 * is incorrectly configured. 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Player parsePlayer (long id) 
			throws IllegalArgumentException, ParserConfigurationException, SAXException, 
			IOException {
		
		File xmlFile = new File("res/" + id + ".xml");
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
		Document xmlDoc = dBuilder.parse(xmlFile);		
		xmlDoc.getDocumentElement().normalize();
		
		Player p = new Player(id);
		NodeList nList = xmlDoc.getElementsByTagName("attempt");
		
		//Get all the attempts
		Function function; String[] args; String reply;
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) n;
				function = Function.valueOf(
						eElement.getElementsByTagName("function")
						.item(0)
						.getTextContent());
				args = eElement.getElementsByTagName("args")
						.item(0)
						.getTextContent()
						.split(" ");
				reply = eElement.getElementsByTagName("reply")
						.item(0)
						.getTextContent();
				p.addAttempt(new Attempt(function, args, reply));
			}
		}
		
		return p;
	}
}
