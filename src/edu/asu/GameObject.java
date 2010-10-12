package edu.asu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GameObject {
	List<GameObject> _inventoryList =  new ArrayList<GameObject>();
	private String _name;
	private String _description;
	List<GameCommand> _verbs = new ArrayList<GameCommand>();
	private Node _XMLBlock;
	
	public GameObject(Node XML){
		_XMLBlock = XML.cloneNode(true);
		_name = Client.getXMLElement(XML, "Name");
		_description = Client.getXMLElement(XML, "Description");
		initialize();
	}
	public String name(){
		return(_name);
	}
	public String description(){
		//add stuff to check internal and makes changes
		return(_description);
	}
	public List<GameCommand> verbs(){
		return(_verbs);
	}
	public List<GameObject> inventory(){
		return(_inventoryList);
	}
	public String doVerb(String verb){
		GameCommand _verb = getVerb(verb);
		//come back to this
	}
//	private String think(){
		//get program working and then I can work on this
//	}
	private boolean initialize(){
		
		//_inventoryList = Client.getXMLElement(_XMLBlock, "Inventory");
		_verbs = new ArrayList<GameCommand>();
		NodeList parseVerbs = Client.getXMLNodes(_XMLBlock, "Verb");
		if(parseVerbs != null && parseVerbs.getLength() > 0){
			for(int i = 0; i < parseVerbs.getLength(); i++){
//				_verbs.add(new GameCommand(parseVerbs.item(i)));
			}
		}
	}
	public GameCommand getVerb(String verb){
		GameCommand gc;
		for(Iterator<GameCommand> i = _verbs.iterator(); i.hasNext(); gc = i.next()) {// lives when done
//			if()  GameCommand name or verbname and comparing it to String verb.  
				return gc;
		}
		return null;
	}
}
