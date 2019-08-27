package gamelogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
		
		Element root = xmlDoc.getDocumentElement();
		NodeList nList = root.getElementsByTagName("attempt");
		
		
		NodeList functionScores = root.getElementsByTagName("score");
		long[] functionScoreArray = new long[Functions.NUM_FUNCTIONS];
		for (int i = 0; i < Functions.NUM_FUNCTIONS; i++) {
			Node n = functionScores.item(i);
			Element eElement = (Element) n;
			functionScoreArray[Integer.parseInt(eElement.getAttribute("funcNum"))] =
					Long.parseLong(eElement.getTextContent());
		}
		Player p = new Player(id, functionScoreArray);
		//Get all the attempts
		Function function; String[] args; String reply; AttemptType type; String guess;
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) n;
				type = AttemptType.valueOf(eElement.getElementsByTagName("type")
						.item(0)
						.getTextContent());
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
				if (type.equals(AttemptType.FUNCTION_GUESS) || type.equals(AttemptType.GUESS)) {
					guess = eElement.getElementsByTagName("guess")
							.item(0)
							.getTextContent();
					p.addAttempt(new Attempt(type, function, args, reply, guess));
				} else {
					p.addAttempt(new Attempt(type, function, args, reply));
				}
			}
		}
		
		return p;
	}
	
	/**
	 * Gets the list of player IDs from the "res/ids.xml" file.
	 * @return an array of playerIDs (long[])
	 * @throws ParserConfigurationException The file is not in
	 * the correct XML format. 
	 * @throws SAXException
	 * @throws IOException
	 */
	public static long[] playerIDs() throws ParserConfigurationException,
				SAXException, IOException {
		
		File xmlFile = new File("res/ids.xml");
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
		Document xmlDoc = dBuilder.parse(xmlFile);		
		xmlDoc.getDocumentElement().normalize();
		
		Element root = xmlDoc.getDocumentElement();
		NodeList nList = root.getElementsByTagName("id");
		long[] returner = new long[nList.getLength()];
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) n;
				System.out.println(eElement.getTextContent());
				returner[i] = Long.parseLong(eElement.getTextContent());
			}
		}
		
		return returner;
	}
	
	/**
	 * Saves the result of a player
	 * @param p the player
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void savePlayer (Player p) 
			throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory =
        DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element root = doc.createElement("attempts");
        
        Element newElement, f, a, r, t, g; 
        String argsString;
        ArrayList<Attempt> playerAttempts = p.getAttempts();
        for (Attempt a1 : playerAttempts) {
        	//Adds the stuff of the attempt
        	newElement = doc.createElement("attempt");
        	t = doc.createElement("type");
        	t.setTextContent(a1.getType().name());
        	f = doc.createElement("function");
        	f.setTextContent(a1.getFunction().name());
        	a = doc.createElement("args");
        	argsString = "";
        	for (String s : a1.getArgs()) {argsString += s; argsString += " ";}
        	a.setTextContent(argsString);
        	r = doc.createElement("reply");
        	r.setTextContent(a1.getAnswer());
        	if (a1.getType().equals(AttemptType.GUESS) || a1.getType().equals(AttemptType.FUNCTION_GUESS)) {
        		g = doc.createElement("guess");
        		g.setTextContent(a1.getGuess());
        		newElement.appendChild(g);
        	}
        	newElement.appendChild(t);
        	newElement.appendChild(f);
        	newElement.appendChild(a);
        	newElement.appendChild(r);
        	root.appendChild(newElement);
        }

        doc.appendChild(root);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("res/"+p.getID()+".xml"));
        transformer.transform(source, result);
        
	}
	
	/**
	 * Saves the ids to the ids file. 
	 * @param ids An array of IDs to save. 
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void saveIDs(long[] ids) 
			throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory =
        DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element root = doc.createElement("playerIDs");
        
        Element newElement;
        for (long l : ids) {
        	newElement = doc.createElement("id");
        	newElement.setTextContent(Long.toString(l));
        	root.appendChild(newElement);
        }

        doc.appendChild(root);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("res/ids.xml"));
        transformer.transform(source, result);
	}
}
