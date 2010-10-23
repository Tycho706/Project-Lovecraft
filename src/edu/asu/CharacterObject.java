package edu.asu;

import org.w3c.dom.Node;

public class CharacterObject extends CreatureObject {

	static CharacterObject you;
	public CharacterObject(Node XML) {
		super(XML);
		you = this;
	}
	@Override
	public String doVerb(GameObject subject, String input){
		GameCommand verb;
		if(equipped() != null)
		{
			verb = equipped().getVerb(input);
			if(verb != null){
				return equipped().doVerb(this, input);
			}
		}
		verb = _location.getVerb(input);
		if(verb != null){
			return _location.doVerb(this, input);//return Client.doVerb(equipped(), this, verb.getTranslation(input));
		}
		verb = this.getVerb(input);
		if(verb == null)
		{
			return "I don't understand.";
		}
		else {
			return Client.doVerb(subject, this, verb.getTranslation(input));  // in this case the character is the object not the subject
		}
	}
	public ItemObject equipped(){
		return _equipped;
	}
	public boolean equip(ItemObject equipment){
		equipment.setLocation(this);
		_equipped = equipment;
		return true;
	}
}
