package edu.asu;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RoomObject extends GameObject {

	public RoomObject(Node XML) {
		super(XML);
	}
	@Override
	protected boolean initialize(){
		if(super.initialize()){
			NodeList parseInventory = Client.getXMLNodes(_XMLBlock, "Monster");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++)
					_inventoryList.add(new ItemObject(parseInventory.item(i)));
			parseInventory = Client.getXMLNodes(_XMLBlock, "Exit");
			if(parseInventory != null && parseInventory.getLength() > 0)
				for(int i = 0; i < parseInventory.getLength(); i++)
					_inventoryList.add(new ItemObject(parseInventory.item(i)));
			return true;
		}
		else
			return false;
	}
}
