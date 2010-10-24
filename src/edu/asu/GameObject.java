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
	ArrayList<ExitObject> _exits;
	ArrayList<CreatureObject> _monsters;
	ArrayList<ItemObject> _items;

	public GameObject(Node XML){
		_XMLBlock = XML.cloneNode(true);
		_name = Client.getXMLElement(XML, "Name");
		_description = Client.getXMLElement(XML, "Description");
		setStatus(Client.getXMLElement(XML, "Status"));
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
		if(_status == null)
			return "";
		return _status;
	}
	public String name(){
		return(_name);
	}
	public String description(String input){
		//get all possible descriptions for this object
		NodeList descriptions = Client.getXMLNodes(_XMLBlock, "Description");
		String StatusTag = "";
		if(isLit() || "Attacking".equalsIgnoreCase(input)){
			if(input != null)
				StatusTag = input;
			else
				StatusTag = "Lit";
		}
		for(int i = 0; i < descriptions.getLength(); i++){
			if(descriptions.item(i).getParentNode() != null && descriptions.item(i).getParentNode().equals(_XMLBlock)) {
				if(Client.getXMLElement(descriptions.item(i), "Status").equalsIgnoreCase(StatusTag)) {
					Node n = descriptions.item(i);
					_description = n.getFirstChild().getNodeValue();
				}
			}	
		}
		return(_description);
	}
	protected boolean isLight(){
		if(this.getStatus().equalsIgnoreCase("Light")) 
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
			if(entity != null && entity != this) { 
				if(entity.isLight())  
					return true;
			}
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

	public ArrayList<ExitObject> exits(){
		_exits = new ArrayList<ExitObject>();
		for(Iterator<GameObject> i 	= _inventoryList.iterator(); i.hasNext(); ){
			 GameObject e = i.next();
			 if(e instanceof ExitObject)
				 _exits.add((ExitObject)e);
		}

		return _exits;
	}
	
	public ExitObject getExit(String name){
		ExitObject exit;
		for(Iterator<ExitObject> i = exits().iterator(); i.hasNext(); ) { 
			exit = i.next();
			if(exit.name().equalsIgnoreCase(name))
				return exit;
		}
		return null;
	}	
	public ArrayList<CreatureObject> creatures(){
		_monsters = new ArrayList<CreatureObject>();
		for(Iterator<GameObject> i 	= _inventoryList.iterator(); i.hasNext(); ){
			 GameObject e = i.next();
			 if(e instanceof CreatureObject)
				 _monsters.add((CreatureObject)e);
		}
		return _monsters;
	}

	public ArrayList<ItemObject> items(){
		_items = new ArrayList<ItemObject>();
		for(Iterator<GameObject> i 	= _inventoryList.iterator(); i.hasNext(); ){
			 GameObject e = i.next();
			 if(e instanceof ItemObject)
				 _items.add((ItemObject)e);
		}
		return _items;
	}

	protected boolean initialize(){
		if(_XMLBlock != null){
			_inventoryList = new ArrayList<GameObject>();


			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Character");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					Node XML = parseInventory.item(i);
					if(_XMLBlock.equals(XML.getParentNode())) {
						CharacterObject e = new CharacterObject(XML);
						e.setLocation(this);
					}
				}

			parseInventory = Client.getXMLNodes(_XMLBlock, "Monster");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					Node XML = parseInventory.item(i);
					if(_XMLBlock.equals(XML.getParentNode())) {
						MonsterObject e = new MonsterObject(XML);
						e.setLocation(this);
					}
				}
			
			parseInventory = Client.getXMLNodes(_XMLBlock, "Exit");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					Node XML = parseInventory.item(i);
					if(_XMLBlock.equals(XML.getParentNode())) {
						ExitObject e = new ExitObject(XML);
						e.setLocation(this);
					}
				}
			parseInventory = Client.getXMLNodes(_XMLBlock, "Item");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					Node XML = parseInventory.item(i);
					if(_XMLBlock.equals(XML.getParentNode())) {
						ItemObject e = new ItemObject(XML);
						e.setLocation(this);
					}
				}


			_verbs = new ArrayList<GameCommand>();
			NodeList parseVerbs = Client.getXMLNodes(_XMLBlock, "Command");
			if(parseVerbs != null && parseVerbs.getLength() > 0){
				for(int i = 0; i < parseVerbs.getLength(); i++){
					Node XML = parseVerbs.item(i);
					if(_XMLBlock.equals(XML.getParentNode())) {
						GameCommand gc = new GameCommand(XML, _name);
						_verbs.add(gc);
						//System.out.println("Added verb '" + gc.getVerb() + "' to entity '" + _name + "'; ");
					}
					
//					
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
		System.out.println("???");
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
	public GameObject getContents(String name){
		GameObject entity = null;
		for(Iterator<GameObject> i = _inventoryList.iterator(); i.hasNext(); ) { 
			entity = i.next();
			if(entity.name().equalsIgnoreCase(name))
				return entity;
		}
		return null;
	}
}
