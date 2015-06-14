package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;

public class ConcreteSectorButtonFactory implements SectorButtonFactory {

	private static final String DANGEROUS = DangerousSector.class
			.getSimpleName();
	private static final String SECURE = SecureSector.class.getSimpleName();
	private static final String HATCH = EscapeHatchSector.class.getSimpleName();
	private static final String ALIEN = AlienSector.class.getSimpleName();
	private static final String HUMAN = HumanSector.class.getSimpleName();

	@Override
	public SectorButton createSectorButton(SectorRemote type, String name) {
		if (type == null)
			return new NoSectorButton("");
		if (type.getType().equals(SECURE))
			return new SecureSectorButton(name);
		if (type.getType().equals(DANGEROUS))
			return new DangerousSectorButton(name);
		if (type.getType().equals(HATCH))
			return new EscapeHatchSectorButton(name);
		if (type.getType().equals(ALIEN))
			return new AlienSectorButton(name);
		if (type.getType().equals(HUMAN))
			return new HumanSectorButton(name);

		return null;
	}

}
