package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;

/**
 * This class implements the SectorButtonFactory
 * 
 * @see SectorButtonFactory
 * @author cerridifebbo
 *
 */
public class ConcreteSectorButtonFactory implements SectorButtonFactory {

	private static final String DANGEROUS = DangerousSector.class
			.getSimpleName();
	private static final String SECURE = SecureSector.class.getSimpleName();
	private static final String HATCH = EscapeHatchSector.class.getSimpleName();
	private static final String ALIEN = AlienSector.class.getSimpleName();
	private static final String HUMAN = HumanSector.class.getSimpleName();

	/**
	 * This method creates a new SectorButton.
	 * 
	 * @author cerridifebbo
	 * @param temp
	 *            the sectorRemote
	 * @return the new SectorButton. Null if type id not one of the type of this
	 *         factory
	 */
	@Override
	public SectorButton createSectorButton(SectorRemote type) {
		if (type == null)
			return new NoSectorButton("");
		if (type.getType().equals(SECURE))
			return new SecureSectorButton(type.getName());
		if (type.getType().equals(DANGEROUS))
			return new DangerousSectorButton(type.getName());
		if (type.getType().equals(HATCH))
			return new EscapeHatchSectorButton(type.getName());
		if (type.getType().equals(ALIEN))
			return new AlienSectorButton(type.getName());
		if (type.getType().equals(HUMAN))
			return new HumanSectorButton(type.getName());

		return null;
	}

}
