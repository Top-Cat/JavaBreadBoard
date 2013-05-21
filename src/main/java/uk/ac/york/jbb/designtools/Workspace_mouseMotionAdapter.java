package uk.ac.york.jbb.designtools;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class Workspace_mouseMotionAdapter extends MouseMotionAdapter {
	Workspace adaptee;

	Workspace_mouseMotionAdapter(Workspace adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.adaptee.workspace_mouseMoved(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.adaptee.workspace_mouseDragged(e);
	}
}
