package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CircuitDiagramFrame_vccButton_mouseAdapter extends MouseAdapter {
	CircuitDiagramFrame adaptee;

	CircuitDiagramFrame_vccButton_mouseAdapter(CircuitDiagramFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.vccButton_mouseClicked(e);
	}
}
