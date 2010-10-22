package edu.asu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	ArrayList<ExitObject> _exits;
	ArrayList<CreatureObject> _monsters;
	public RoomObject(Node XML) {
		super(XML);
		map.add(this);
	}
	@Override
	protected boolean initialize(){
		if(super.initialize()){
			_location = this;
			_exits = new ArrayList<ExitObject>();
			_monsters = new ArrayList<CreatureObject>();
			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Character");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					CharacterObject m = new CharacterObject(parseInventory.item(i));
					m.setLocation(this);
					_monsters.add(m);
				}
			parseInventory = Client.getXMLNodes(_XMLBlock, "Monster");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					MonsterObject m = new MonsterObject(parseInventory.item(i));
					m.setLocation(this);
					_monsters.add(m);
				}
			parseInventory = Client.getXMLNodes(_XMLBlock, "Exit");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++) {
					ExitObject e = new ExitObject(parseInventory.item(i));
					e.setLocation(this);
					_exits.add(e);
					
				}
			return true;
		}
		else
			return false;
	}
	public ArrayList<ExitObject> exits(){
		return _exits;
	}
	
	public ExitObject getExit(String name){
		ExitObject exit;
		for(Iterator<ExitObject> i = _exits.iterator(); i.hasNext(); ) { 
			exit = i.next();
			if(exit.name().equalsIgnoreCase(name))
				return exit;
		}
		return null;
	}
	public ArrayList<CreatureObject> creatures(){
		return _monsters;
	}
	//Will be doing an Override here to check for monsters and change the descriptions appropriately.
}
