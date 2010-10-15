package edu.asu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**This is the superclass for all entities in the game.  It will need to define entities to their 
 * appropriate type and pull the appropriate information about them from the XML file.  Furthermore,
 * the bulk of the programs ability to manage game-state will be present in this class for the base
 * cases and will be overridden for specific types of special actions in its subclasses.  
 * @author Steven Honda
 *
 */
public class GameObject {    //Need to do think(),  need to do doVerb().  Need to finish GameCommad
	
	List<GameObject> _inventoryList =  new ArrayList<GameObject>();
	protected String _name;
	protected String _description;
	List<GameCommand> _verbs = new ArrayList<GameCommand>();
	protected Node _XMLBlock;
	protected String _status;
	protected GameObject _location;
	
	public GameObject(Node XML){
		_XMLBlock = XML.cloneNode(true);
		_name = Client.getXMLElement(XML, "Name");
		_description = Client.getXMLElement(XML, "Description");
		initialize();
	}
	public String setStatus(String state){
		_status = state;
		return _status;
	}
	public String getStatus(){
		return _status;
	}
	public String name(){
		return(_name);
	}
	public String description(){
		//get all possible descriptions for this object
		NodeList _descriptions = Client.getXMLNodes(_XMLBlock, "Description");
		if(isLit()){
			for(int i = 0; i < _descriptions.getLength(); i++){
				if(Client.getXMLElement(_descriptions.item(i), "Status") == "Lit") //need to do these as string compares
					_description = _descriptions.item(i).getNodeValue();
			}
		}
		else{
			for(int i = 0; i < _descriptions.getLength(); i++){
				if(Client.getXMLElement(_descriptions.item(i), "Status") == "")  //need to do these as strong compares
					_description = _descriptions.item(i).getNodeValue();
			}
		}
		return(_description);
	}
	protected boolean isLit(){
		if(this._status == "Lit") // change to a String compare
			return true;
		
		if(this != _location)
			return _location.isLit();
		GameObject entity = null;
		for(Iterator<GameObject> i = _inventoryList.iterator(); i.hasNext(); entity = i.next()) {
			if(entity != null && entity.isLit())  
				return true;
		}
		return false;
	}
	public List<GameCommand> verbs(){
		return(_verbs);
	}
	public List<GameObject> inventory(){
		return(_inventoryList);
	}
	public String doVerb(String verb){ //checks to see if the verb received from getVerb is a legal action, and then perform that action
		GameCommand _verb = getVerb(verb);
		//come back to this
	}
//	private String think(){
		//get program working and then I can work on this
//	}
	protected boolean initialize(){
		if(_XMLBlock != null){
			_inventoryList = new ArrayList<GameObject>();
			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Item");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++)
					_inventoryList.add(new ItemObject(parseInventory.item(i)));
			_verbs = new ArrayList<GameCommand>();
			NodeList parseVerbs = Client.getXMLNodes(_XMLBlock, "Command");
			if(parseVerbs != null && parseVerbs.getLength() > 0){
				for(int i = 0; i < parseVerbs.getLength(); i++){
					_verbs.add(new GameCommand(parseVerbs.item(i)));
				}
			}
			return true;
		}
		else
			return false;
	}
	public GameCommand getVerb(String verb){  //finished just waiting for GameCommand,  converts the String inputed into a usable verb in the list
		GameCommand gc;
		for(Iterator<GameCommand> i = _verbs.iterator(); i.hasNext(); gc = i.next()) {// lives when done
//			if()  GameCommand name or verbname and comparing it to String verb.  Checking to see if the verb is in the list of the GameObjects verbs.
//				return gc;
		}
		return null;
	}
}
