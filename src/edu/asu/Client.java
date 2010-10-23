package edu.asu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Scanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input;
		//step 1: Load XML file
		Element gameFile = parseXMLFile("Project Lovecraft.xml");
		//step 2: Run through the XML file, and Build the map
		NodeList parseRoom = Client.getXMLNodes(gameFile, "Room");
		if(parseRoom != null && parseRoom.getLength() > 0)
			for(int i = 0; i < parseRoom.getLength(); i++){
				System.out.print((i+1) + ": ");
				new RoomObject(parseRoom.item(i));
				System.out.println("");
			}
		Scanner scan = new Scanner(System.in);
		FileOutputStream logger;
		PrintStream logged;
		try {
				logger = new FileOutputStream("gamelog.txt");
				logged = new PrintStream(logger);
		} catch (FileNotFoundException e) {
				System.err.println("file could not be read for some reason");
				e.printStackTrace();
				return;
		}
		Date now = new Date();
		logged.println("==========| " + now.toString() + " |==========");
		System.out.println(CharacterObject.you.location().description());
		do {
			System.out.println(CharacterObject.you.location().name() + ">");
			input = scan.nextLine();
			logged.println(input);
			System.out.println(CharacterObject.you.doVerb(null, input));
		} while(!input.equalsIgnoreCase("Quit"));
		try {
			logger.close();
		} catch (IOException e) {
			System.err.println("Something went wrong.");   //change that message
			e.printStackTrace();
			return;
		}
	}
	public static Element parseXMLFile(String _fileName){
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(_fileName);
			return dom.getDocumentElement();
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch(SAXException se){
			se.printStackTrace();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}
	public static String getXMLElement(Node XML, String tagName){
		NodeList nl = getXMLNodes(XML, tagName);
		Element el;
		if(nl == null || nl.getLength() == 0) // If the node has no contents return its attribute value instead
		{
			el = (Element)XML;
			return el.getAttribute(tagName);
		}
		el = (Element)getXMLNodes(XML, tagName).item(0);
		
		return el.getFirstChild().getNodeValue();  //needs to be broken down and given try blocks		
	}
	public static NodeList getXMLNodes(Node XML, String NodeType){
		Element XMLData = (Element)XML;
		return XMLData.getElementsByTagName(NodeType);  //needs a try catch
	}
	/**This method takes in the subject and object of a translated verb statement.  From there it
	 * will perform the necessary steps needed to carry out that user inputed verb command.
	 * @param gSubject
	 * @param gObject
	 * @param translation
	 * @return
	 * @author Steven Honda
	 */
	public static String doVerb(GameObject gSubject, GameObject gObject, String translation) {
		String[] parsed = translation.split(" ");
		if(parsed[0].equalsIgnoreCase("Go")){ // "Go @Exit", where @Exit is the name of a Exit
			if(parsed.length < 2)
				return "Go where?";
			else {
				RoomObject room = (RoomObject)gObject; // gObject had better be a room Object
				ExitObject exit = room.getExit(parsed[1]);
				if(exit == null || exit.destination() == null)
					return "You can't go that way.";
				else{
					CreatureObject you = (CreatureObject)gSubject;
					you.setLocation(exit.destination());
					return "You go " + parsed[1] + ".\n" + exit.destination().description() + "\n";
				}
			}
				
		}

		else if(parsed[0].equalsIgnoreCase("Look")){ // "Look ...", a series of commands (see below)			
			
		}
		else if(parsed[0].equalsIgnoreCase("Run")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Get")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Drop")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Put")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Give")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Use")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Attack")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Help")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Hint")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Options")){ // "Go @Exit", where @Exit is the name of a Exit
			
		}

		else if(parsed[0].equalsIgnoreCase("Quit")){ // "Go @Exit", where @Exit is the name of a Exit
			return "Goodbye!";
		}

		return "Huh?";
	}
}
