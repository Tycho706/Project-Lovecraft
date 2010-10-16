package edu.asu;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class MobileObject extends GameObject {
	protected int _pathindex = 0;
	List<RoomObject> _path = new ArrayList<RoomObject>();
	
	public MobileObject(Node XML) {
		super(XML);
	}
//	@Override
//	protected String think(){
		
//	}
}
