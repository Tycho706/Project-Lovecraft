package edu.asu;

import java.util.Iterator;

import org.w3c.dom.Node;

public class ExitObject extends GameObject {
	protected String _destination;
	public ExitObject(Node XML) {
		super(XML);
	}
	@Override
	public boolean initialize(){
		if(super.initialize()){
			_destination = Client.getXMLElement(_XMLBlock, "Destination");
			System.out.print("connecting to " + _destination + "; ");
			return true;
		}
		return false;
	}
	public RoomObject destination(){
		RoomObject destination;
		for(Iterator<RoomObject> i = RoomObject.map.iterator(); i.hasNext(); ) { 
			destination = i.next();
			if(destination.name().equalsIgnoreCase(_destination))
				return destination;
		}
		return null;
	}
}
