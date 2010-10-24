package edu.asu;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MonsterObject extends CreatureObject {

	public MonsterObject(Node XML) {
		super(XML);
	}
	@Override
	public String description(String input){
		if(getStatus().equalsIgnoreCase("Dead")){
			NodeList descriptions = Client.getXMLNodes(_XMLBlock, "Description");
			String deadStatus;
			if(isLit())
				deadStatus = "Dead";
			else
				deadStatus = "Dead,Dark";
			for(int i = 0; i < descriptions.getLength(); i++){
				if(descriptions.item(i).getParentNode() != null && descriptions.item(i).getParentNode().equals(_XMLBlock)) {
					if(Client.getXMLElement(descriptions.item(i), "Status").equalsIgnoreCase(deadStatus)) {
						Node n = descriptions.item(i);
						_description = n.getFirstChild().getNodeValue();
					}
				}	
			}
			return _description;
		}
		else
			return super.description(input);
	}
	@Override
	protected String think(){
		if(!"Dead".equalsIgnoreCase(getStatus())) {
			CharacterObject.you.setStatus("Dead");
			return description("Attacking") + "\n";
		}
		return "";
	}
}
