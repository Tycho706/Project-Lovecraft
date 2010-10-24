package edu.asu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

public class RoomObject extends GameObject { //needs to do description here

	
	static List<RoomObject> map = new ArrayList<RoomObject>();
	public static RoomObject getRoom(String RoomName){
		RoomObject room;
		for(Iterator<RoomObject> i = map.iterator(); i.hasNext(); ) { 
			room = i.next();
			if(room.name().equalsIgnoreCase(RoomName))  
				return room;
		}
		return null;
	}
	
	public RoomObject(Node XML) {
		super(XML);
		map.add(this);
	}
	@Override
	protected boolean initialize(){
		if(super.initialize()){
			_location = this;
			return true;
		}
		else
			return false;
	}
	//Will be doing an Override here to check for monsters and change the descriptions appropriately.
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
