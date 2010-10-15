package edu.asu;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RoomObject extends GameObject { //need to do description here

	public RoomObject(Node XML) {
		super(XML);
	}
	@Override
	protected boolean initialize(){
		if(super.initialize()){
			_location = this;
			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Monster");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++)
					_inventoryList.add(new MonsterObject(parseInventory.item(i)));
			parseInventory = Client.getXMLNodes(_XMLBlock, "Exit");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++)
					_inventoryList.add(new ExitObject(parseInventory.item(i)));
			return true;
		}
		else
			return false;
	}
	//Will be doing an Override here to check for monsters and change the descriptions appropriately.
}
