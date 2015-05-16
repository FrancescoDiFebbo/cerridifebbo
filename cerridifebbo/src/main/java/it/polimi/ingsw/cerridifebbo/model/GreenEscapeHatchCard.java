package it.polimi.ingsw.cerridifebbo.model;

public class GreenEscapeHatchCard extends EscapeHatchCard {

	@Override
	public Object performAction(Player target, Game game) {
		if (target == null || !(target instanceof HumanPlayer)) {
			throw new IllegalArgumentException();
		}
		HumanPlayer human = (HumanPlayer) target;
		human.setEscaped(true);
		human.getPosition().setPassable(false);
		return null;
	}
}
