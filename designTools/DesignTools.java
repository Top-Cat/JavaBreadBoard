package designTools;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DesignTools extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean inApplet = false;

	public static void main(String[] args) {
		DesignTools designTools = new DesignTools();
		designTools.startEditors();
	}

	@Override
	public void init() {
		this.inApplet = true;

		JLabel label = new JLabel("JBB Chip Design Tools");
		label.setHorizontalAlignment(0);
		this.getContentPane().add(label, "North");

		JButton button = new JButton("Start Program");

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DesignTools.this.startEditors();
			}
		});
		this.getContentPane().add(button, "Center");
	}

	private void startEditors() {
		JFrame frame = null;
		if (this.inApplet) {
			new URLMaker(this.getCodeBase());
		} else {
			new URLMaker(null);
		}

		Object[] supportedTools = { "State Table", "Truth Table", "Circuit Diagram" };
		Object defaultTool = "State Table";
		int result = JOptionPane.showOptionDialog(null, "Please select a chip entry tool", "Design Tool Selector", 2, 3, null, supportedTools, defaultTool);

		if (result == 0) {
			frame = new StateTableFrame(this.inApplet);
		} else if (result == 1) {
			frame = new TruthTableFrame(this.inApplet);
		} else {
			frame = new CircuitDiagramFrame(this.inApplet);
		}

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
