package tools;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import designTools.CircuitDiagramFrame;

public class SchematicEditor extends Thread {
	@Override
	public void run() {
		JFrame frame = null;

		frame = new CircuitDiagramFrame(true);

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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: tools.SchematicEditor JD-Core Version: 0.6.2
 */
