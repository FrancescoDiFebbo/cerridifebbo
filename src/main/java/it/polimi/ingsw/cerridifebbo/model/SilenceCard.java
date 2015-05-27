package it.polimi.ingsw.cerridifebbo.model;

public class SilenceCard extends SectorCard {
	
	SilenceCard(){
		super(false);
	}

	@Override
	public Object performAction(Player player, Object target, Game game) {
		return null;
	}

}
