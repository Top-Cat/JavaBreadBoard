package uk.ac.york.jbb.tools;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import uk.ac.york.jbb.designtools.StateTableFrame;


public class StateTableEditor extends Thread {
	@Override
	public void run() {
		JFrame frame = null;

		frame = new StateTableFrame(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension frameSize = frame.getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}
}
