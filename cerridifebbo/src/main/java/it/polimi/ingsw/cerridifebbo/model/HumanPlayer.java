package it.polimi.ingsw.cerridifebbo.model;


public class HumanPlayer extends Player {

	private boolean escaped;

	HumanPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, 1);
		setEscaped(false);
	}

	@Override
	public void attack(Game game) throws Exception {
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
					}
				}
			}
		}
	}

	@Override
	public boolean movement(Sector destination) {
		if (getPosition().getReachableSectors(getMaxMovement()).contains(destination)) {
			setPosition(destination);
			return true;
		}
		return false;
	}

	public boolean isEscaped() {
		return escaped;
	}

	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}
}
