package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Workspace_mouseAdapter extends MouseAdapter {
	Workspace adaptee;

	Workspace_mouseAdapter(Workspace adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.workspace_mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.adaptee.workspace_mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.adaptee.workspace_mouseReleased(e);
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.Workspace_mouseAdapter JD-Core Version:
 * 0.6.2
 */
