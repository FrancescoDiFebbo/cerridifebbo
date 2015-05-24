package it.polimi.ingsw.cerridifebbo;

import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.User;
import it.polimi.ingsw.cerridifebbo.view.GUI.ConcreteSectorButtonFactory;
import it.polimi.ingsw.cerridifebbo.view.GUI.MapLayout;
import it.polimi.ingsw.cerridifebbo.view.GUI.SectorButton;
import it.polimi.ingsw.cerridifebbo.view.GUI.SectorButtonFactory;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.Test;

public class MapViewTest {
	@Test
	public void test() {
		Map map = Map.getInstance();
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		Container contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		SectorButtonFactory factory = new ConcreteSectorButtonFactory();

		for (int i = 0; i < Map.RAWMAP; i++) {
			for (int j = 0; j < Map.COLUMNMAP; j++) {
				Sector temp = map.getCell(i, j);
				String label = null;
				if (temp == null) {
					label = "";
				} else {
					label = temp.toString();
				}
				SectorButton button = factory.createSectorButton(temp, label);
				button.setBackground(Color.BLACK);
				button.setForeground(Color.YELLOW);
				contentPane.add(button);
			}
		}
		LayoutManager layout = new MapLayout(14, 23);
		contentPane.setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}
