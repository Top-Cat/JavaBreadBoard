package uk.ac.york.jbb.jbreadboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Probe extends JComponent implements Device, CircuitSelection {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int next = 0;
	private Circuit circuit;
	private Trace trace;
	private ImageIcon image;
	private String label = "probe" + Probe.next;

	private long time = -1L;
	private int oldval = -1;
	private int val = -1;

	private Node node = null;

	private MouseInputListener mia = new MouseInputAdapter() {
		int XFudge = 0;
		int YFudge = 0;

		@Override
		public void mousePressed(MouseEvent e) {
			Probe.this.circuit.select((Probe) e.getComponent());
			this.XFudge = e.getX();
			this.YFudge = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Probe probe = (Probe) e.getComponent();
			BreadBoard origb = (BreadBoard) probe.getParent();

			Circuit pane = (Circuit) origb.getParent();

			if (pane.getJBreadBoard().getMode().equals("move")) {
				origb.remove(probe);

				int X = e.getX() + probe.getX() + origb.getX() - this.XFudge;
				int Y = e.getY() + probe.getY() + origb.getY() - this.YFudge;

				Board board = pane.getBoardAt(X, Y);
				BreadBoard newb;
				if (board != null && board instanceof BreadBoard) {
					newb = (BreadBoard) board;
				} else {
					newb = origb;
				}

				int X2 = X - newb.getX();
				int Y2 = Y - newb.getY();

				X2 -= (X2 - 4) % 8;
				if (X2 < 76) {
					X2 = 76;
				} else if (X2 > 444) {
					X2 = 444;
				}

				Y2 -= Y2 % 8;
				if (Y2 < 28) {
					Y2 = 16;
				} else if (Y2 < 40) {
					Y2 = 40;
				} else if (Y2 > 72 && Y2 < 86) {
					Y2 = 72;
				} else if (Y2 > 128 && Y2 < 138) {
					Y2 = 128;
				} else if (Y2 > 152) {
					Y2 = 152;
				}

				if (newb.isHole(X2, Y2)) {
					Rectangle dest = new Rectangle(X2, Y2, Probe.this.getWidth(), Probe.this.getHeight());
					Component[] components = newb.getComponents();
					boolean clear = true;

					for (int i = 0; clear && i < components.length; i++) {
						clear = !dest.intersects(components[i].getBounds());
					}
					if (clear) {
						probe.setLocation(X2, Y2);
					} else {
						newb = origb;
					}
				} else {
					newb = origb;
				}

				newb.add(probe);
				probe.updateConnections();

				origb.repaint();
				newb.repaint();
			}
		}
	};

	@Override
	public String toString() {
		return "Probe \"" + this.label + "\" " + this.getX() + " " + this.getY();
	}

	public Probe(Circuit c) {
		if (c != null) {
			this.circuit = c;
			Probe.next += 1;

			this.image = c.getJBreadBoard().getImageIcon("uk/ac/york/jbb/images/probe.gif");
			this.trace = c.getTrace();
			this.trace.addProbe(this);
		}
		this.setForeground(Color.black);
		this.setBackground(Color.black);

		this.setBounds(this.getX(), this.getY(), 8, 8);
		this.addMouseListener(this.mia);
		this.addMouseMotionListener(this.mia);
	}

	public void setLabel(String s) {
		if (s != null && !s.equals("")) {
			this.label = s;
		}
	}

	public String getLabel() {
		return this.label;
	}

	public int getOldValue() {
		return this.oldval;
	}

	public int getValue() {
		return this.val;
	}

	@Override
	public void select() {
		this.repaint();
	}

	@Override
	public void deselect() {
		this.repaint();
	}

	@Override
	public void delete() {
		this.trace.removeProbe(this);
		Container c = this.getParent();
		c.remove(this);
		c.repaint();
		if (this.node != null) {
			this.node.setDevice(null);
			this.node.setType("NC");
		}
	}

	@Override
	public String getInfo() {
		return "Component Name: \nVirtual Probe Device \n\nLabel: \n" + this.label + "\n\n" + "JBreadBoard library name: \n" + this.getClass().getName().substring(12) + ".class";
	}

	@Override
	public String getDiagram() {
		return null;
	}

	@Override
	public void reset() {
		this.time = this.oldval = this.val = -1;
	}

	@Override
	public void simulate() {
		if (this.node != null && this.circuit.getSimTime() != this.time) {
			String potential = this.node.getPotential().toTTL();

			this.time = this.circuit.getSimTime();
			this.oldval = this.val;

			if (potential != null) {
				if (potential.equals("HIGH")) {
					this.val = 1;
				} else if (potential.equals("LOW")) {
					this.val = 0;
				} else {
					this.val = -1;
				}
			} else {
				this.val = -1;
			}

			this.circuit.updateTrace();
		}
	}

	@Override
	public void updateConnections() {
		int x = this.getX();
		int y = this.getY();
		if (this.node != null) {
			this.node.setDevice(null);
			this.node.setType(null);
		}
		Board b = (Board) this.getParent();
		this.node = b.getNodeAt(x, y);
		this.node.setDevice(this);
		this.node.setType("IN");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.image.paintIcon(this, g, 0, 0);
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

	@Override
	public int getWidth() {
		return 8;
	}

	@Override
	public int getHeight() {
		return 8;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
