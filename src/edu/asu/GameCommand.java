package edu.asu;

import org.w3c.dom.Node;

public class GameCommand {
	private String _verb;
	private String[] _parsedVerb;
	private String _name;
	public GameCommand(Node XML, String MyName){
		_verb = Client.getXMLElement(XML, "Verb");
		_name = MyName;
		_parsedVerb = _verb.split(" ");
		Client.getXMLElement(XML, "Do");
	}
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
