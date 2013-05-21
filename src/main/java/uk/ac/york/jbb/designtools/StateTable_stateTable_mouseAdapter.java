package uk.ac.york.jbb.designtools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class StateTable_stateTable_mouseAdapter extends MouseAdapter {
	StateTable adaptee;

	StateTable_stateTable_mouseAdapter(StateTable adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.stateTable_mouseClicked(e);
	}
}
