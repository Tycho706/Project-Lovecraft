package edu.asu;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class GameCommand {
	private String _verb;
	private ArrayList<String> _modifiers;
	public GameCommand(Node XML){
		_verb = Client.getXMLElement(XML, "Verb");
		for(int i = 0; i < parseVerbs.getLength(); i++){
			_verbs.add(new GameCommand(parseVerbs.item(i)));
	}

}
