package edu.asu;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreatureObject extends MobileObject {
	List<RoomObject> _pathback = new ArrayList<RoomObject>();
	protected CreatureObject _target;
	public CreatureObject(Node XML) {
		super(XML);
	}
	@Override
	public boolean initialize(){
		super.initialize();
		if(_XMLBlock != null){
			_path = new ArrayList<RoomObject>();
			NodeList parsePath = Client.getXMLNodes(_XMLBlock, "Path");
			if(parsePath != null && parsePath.getLength() > 0)
				for(int i = 0; i < parsePath.getLength(); i++)
//					_path.add(RoomObject(parsePath.item(i)));  need to find room objects and assign them  
			return true;
		}
		else
			return false;
	}
//	@Override
//	protected String think(){
		
//	}
}
