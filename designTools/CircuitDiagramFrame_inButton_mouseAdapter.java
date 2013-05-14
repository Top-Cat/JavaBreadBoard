package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CircuitDiagramFrame_inButton_mouseAdapter extends MouseAdapter {
	CircuitDiagramFrame adaptee;

	CircuitDiagramFrame_inButton_mouseAdapter(CircuitDiagramFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.inButton_mouseClicked(e);
	}
}
