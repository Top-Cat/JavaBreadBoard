package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CircuitDiagramFrame_andButton_mouseAdapter extends MouseAdapter {
	CircuitDiagramFrame adaptee;

	CircuitDiagramFrame_andButton_mouseAdapter(CircuitDiagramFrame adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.andButton_mouseClicked(e);
	}
}
