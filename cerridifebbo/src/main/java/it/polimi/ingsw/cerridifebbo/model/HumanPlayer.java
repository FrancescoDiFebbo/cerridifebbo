package it.polimi.ingsw.cerridifebbo.model;

public class HumanPlayer extends Player {

	private boolean escaped;

	HumanPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, 1);
		setEscaped(false);
	}

	@Override
	public boolean attack(Game game) throws Exception {
		for (Card card : this.getOwnCards()) {
			if (card instanceof AttackItemCard) {
				card.performAction(this, game);
				return super.attack(game);
			}
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
