package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CircuitDiagramFrame_notButton_mouseAdapter extends MouseAdapter {
	CircuitDiagramFrame adaptee;

	CircuitDiagramFrame_notButton_mouseAdapter(CircuitDiagramFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.notButton_mouseClicked(e);
	}
}
