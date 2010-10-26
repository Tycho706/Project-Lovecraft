package edu.asu;

import org.w3c.dom.Node;
/**
 * This class handles takes the user input and decides if that command is a valid command for 
 * the game to handle.
 * @author Steven Honda
 *
 */
public class GameCommand {
	private String _verb;
	private String[] _parsedVerb;
	private String _name;
	/**
	 * This method sets what the verb and split the input into it's constituent parts, 
	 * separated by the spaces.
	 * @param XML
	 * @param MyName
	 * @author Steven Honda
	 */
	public GameCommand(Node XML, String MyName){
		_verb = Client.getXMLElement(XML, "Verb");
		_name = MyName;
		_parsedVerb = _verb.split(" ");
		Client.getXMLElement(XML, "Do");
	}
	/**
	 * This method checks the inputted verb, that must be the first word in the inputed sentence, 
	 * against the list of allowed words for a GameObject as defined in the XML document. 
	 * @param input
	 * @return boolean
	 */
	public boolean matches(String input){
		String[] _parsedInput = input.split(" ");
		if(_parsedInput.length == _parsedVerb.length) {
			for(int i=0; i < _parsedInput.length; i++) {
				if(_parsedVerb[i].startsWith("@")) {
					if(_parsedVerb[i].equalsIgnoreCase("@ME") && !_parsedInput[i].equalsIgnoreCase(_name)) {
						return false;
					}
					if(!_parsedVerb[i].equalsIgnoreCase("@ME") && _parsedInput[i].equalsIgnoreCase(_name)) {
						return false;
					}
				}
				else if(!_parsedVerb[i].equalsIgnoreCase(_parsedInput[i]))
					return false;
			}
			return true;
		}
		return false;
	}
	public String getVerb(){
		return _verb;
	}
	public String getTranslation(String input){
		return input;
	}
}
