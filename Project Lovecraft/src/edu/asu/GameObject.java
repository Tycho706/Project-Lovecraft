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
public class GameObject {    //Need to do think(),  need to do doVerb().  Need to finish description and GameCommad
	
	List<GameObject> _inventoryList =  new ArrayList<GameObject>();
	private String _name;
	private String _description;
	List<GameCommand> _verbs = new ArrayList<GameCommand>();
	protected Node _XMLBlock;
	
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
	public GameCommand getVerb(String verb){
		GameCommand gc;
		for(Iterator<GameCommand> i = _verbs.iterator(); i.hasNext(); gc = i.next()) {// lives when done
//			if()  GameCommand name or verbname and comparing it to String verb.  
//				return gc;
		}
		return null;
	}
}
