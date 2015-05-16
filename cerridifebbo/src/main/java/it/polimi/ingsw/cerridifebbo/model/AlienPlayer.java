package it.polimi.ingsw.cerridifebbo.model;

import java.beans.DesignMode;

public class AlienPlayer extends Player {

	private static final int MOVEMENT_BEFORE_EATING = 2;
	private static final int MOVEMENT_AFTER_EATING = 3;

	AlienPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, MOVEMENT_BEFORE_EATING);
	}

	@Override
	public void attack(Game game) throws Exception {
		boolean eatenHuman = false;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player.getPosition() == getPosition() && player != this) {
				if (player instanceof AlienPlayer) {
					player.kill();
				} else {
					boolean safe = false;
					for (Card card : player.getOwnCards()) {
						if (card instanceof DefenseItemCard) {
							card.performAction(player, game);
							safe = true;
						}
					}
					if (!safe) {
						player.kill();
						eatenHuman = true;
					}
				}
			}
		}
		if (eatenHuman && getMaxMovement() == MOVEMENT_BEFORE_EATING) {
			setMaxMovement(MOVEMENT_AFTER_EATING);
		}
	}

	@Override
	public boolean movement(Sector destination) {
		if (destination instanceof EscapeHatchSector) {
			return false;
		}
		if (getPosition().getReachableSectors(getMaxMovement()).contains(destination)) {
			setPosition(destination);
			return true;
		}
		return false;
	}
}
