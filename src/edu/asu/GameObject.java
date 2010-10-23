package edu.asu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		System.out.print("Initializing a " + XML.getNodeName() + ":'" + _name + "'; ");
		initialize();
	}
	public String setStatus(String state){
		_status = state;
		return _status;
	}
	public GameObject location(){
		return _location;
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
		String StatusTag = "";
		if(isLit())
			StatusTag = "Lit";
		
		for(int i = 0; i < _descriptions.getLength(); i++){
			if(Client.getXMLElement(_descriptions.item(i), "Status").equalsIgnoreCase(StatusTag)) {
				Node n = _descriptions.item(i);
				_description = n.getFirstChild().getNodeValue();
			}
			
		}
		return(_description);
	}
	protected boolean isLight(){
		if(this._status != null && this._status.equalsIgnoreCase("Lit")) 
			return true;
		return false;
	}
	protected boolean isLit(){
			if(this.isLight())
				return true;
		
		if(this != _location)
			return _location.isLit();
		GameObject entity = null;
		for(Iterator<GameObject> i = _inventoryList.iterator(); i.hasNext(); ){
			 entity = i.next();
			if(entity != null && entity != this && entity.isLight())  
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
	public String doVerb(GameObject subject, String input){ //checks to see if the verb received from getVerb is a legal action, and then perform that action
		GameCommand verb = getVerb(input);
		if(verb == null){
			return "That doesn't make sense.";
		}
		else{
			return Client.doVerb(subject, this, verb.getTranslation(input));
		}
	}
//	private String think(){
		//get program working and then I can work on this
//	}
	protected boolean initialize(){
		if(_XMLBlock != null){
			_inventoryList = new ArrayList<GameObject>();
			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Item");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					ItemObject item = new ItemObject(parseInventory.item(i));
					item.setLocation(this);
				}
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
	public boolean addToInventory(GameObject entity){
		_inventoryList.add(entity);
		return true;
	}
	public boolean removeFromInventory(GameObject entity){
		if(_inventoryList.contains(entity)){
			_inventoryList.remove(_inventoryList.indexOf(entity));
			return true;
		}
		return false;
	}
	public boolean setLocation(GameObject container){
		if(_location != null)
			_location.removeFromInventory(this);
		_location = container;
		return _location.addToInventory(this);
	}
	public GameCommand getVerb(String verb){
		GameCommand gc;
		for(Iterator<GameCommand> i = _verbs.iterator(); i.hasNext(); ) { 
			gc = i.next();
			if(gc.matches(verb))  
				return gc;
		}
		return null;
	}
}
