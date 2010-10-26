package edu.asu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
/**
 * This class handles the rooms that make up the room.  It sets up the map for the room and
 * spawns the exits to each of the rooms while connecting them in the way that the XML document
 * dictates.
 * @author Steven Honda
 *
 */
public class RoomObject extends GameObject { 

	
	static List<RoomObject> map = new ArrayList<RoomObject>();
	/**
	 * This method goes through the list of all RoomObjects and returns the one that has a 
	 * attribute value that is the String that was passed to the method.
	 * @param RoomName
	 * @return RoomObject
	 * @author Steven Honda
	 */
	public static RoomObject getRoom(String RoomName){
		RoomObject room;
		for(Iterator<RoomObject> i = map.iterator(); i.hasNext(); ) { 
			room = i.next();
			if(room.name().equalsIgnoreCase(RoomName))  
				return room;
		}
		return null;
	}
	/**
	 * The constructor calls the constructor for GameObject and then adds all RoomObjects to an
	 * array that becomes the map.
	 * @param XML
	 * @author Steven Honda
	 */
	public RoomObject(Node XML) {
		super(XML);
		map.add(this);
	}
	/**
	 * Checks to see if the initialize method in GameObject worked properly and then sets the
	 * location that is being handled to the RoomObject that is being initialized. 
	 * @return boolean
	 * @author Steven Honda
	 */
	@Override
	protected boolean initialize(){
		if(super.initialize()){
			_location = this;
			return true;
		}
		else
			return false;
	}
	/**
	 * This method overrides the description in GameObject and checks to see if a monster 
	 * is in the room.  If it does contain a monster and the monster is still alive it overrides
	 * the rooms description. 
	 * @param input
	 * @author Steven Honda
	 */
	@Override
	public String description(String input){
		super.description(input);
		String contents = "";
		CreatureObject monster = null;
		for(Iterator<CreatureObject> i 	= creatures().iterator(); i.hasNext(); ){
			 monster = i.next();
			 if(monster != null && monster != CharacterObject.you && !"Dead".equalsIgnoreCase(monster.getStatus()))
				 contents += monster.description(input) + "\n";
		}
		if(contents.length() > 0){
			return contents;
		}
		GameObject e;
		for(Iterator<GameObject> i 	= _inventoryList.iterator(); i.hasNext(); ){
			 e = i.next();
			 if(e.description(input).length() > 0)
			 {
				 if(e instanceof ExitObject)
					 contents += e.description(input) + "\n";
				 else
					 contents += e.description(null) + "\n";
			 }
		}

		return _description + "\n" + contents;
	}
}
