package it.polimi.ingsw.cerridifebbo.view.GUI;

import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;

public class ConcreteSectorButtonFactory implements SectorButtonFactory {

	@Override
	public SectorButton createSectorButton(Sector type, String name) {
		if (type == null)
			return new NoSectorButton("");
		if (type instanceof SecureSector)
			return new SecureSectorButton(type.toString());
		if (type instanceof DangerousSector)
			return new DangerousSectorButton(type.toString());
		if (type instanceof EscapeHatchSector)
			return new EscapeHatchSectorButton(type.toString());
		if (type instanceof AlienSector)
			return new AlienSectorButton(type.toString());
		if (type instanceof HumanSector)
			return new HumanSectorButton(type.toString());

		return null;
	}

}
