package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class HumanPlayer extends Player {

	HumanPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, 1);
		
	}
	
	@Override
	public void attack(ArrayList<User> userList) throws Exception {
		for (Card card : this.getOwnCards()){
			if(card instanceof AttackItemCard ){
				this.deleteCard(card);
				super.attack(userList);
				return;
			}
		}
			throw new Exception ("No attack card");
			
		}
	}
