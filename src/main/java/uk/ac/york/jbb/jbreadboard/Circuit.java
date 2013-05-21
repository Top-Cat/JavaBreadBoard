package uk.ac.york.jbb.jbreadboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class Circuit extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JBreadBoard jBreadBoard = null;
	private JBreadBoardEventSimulation simulation = new JBreadBoardEventSimulation(this);
	private Trace trace = new Trace(this);

	public static final Integer BoardLayer = new Integer(100);

	public static final Integer WireLayer = new Integer(1000);
	private Timer timer;
	private boolean issimulating = false;

	public BreadBoard BBselect = null;

	public CircuitSelection selected = null;

	public boolean hiddenwires = false;

	public void hideWires() {
		if (!this.hiddenwires) {
			this.hiddenwires = true;

			if (this.jBreadBoard.getMode() != null && this.jBreadBoard.getMode().equals("wire")) {
				this.jBreadBoard.setMode("move", "OK");
			}
			Component[] components = this.getComponentsInLayer(Circuit.WireLayer);

			for (Component component2 : components) {
				if (component2 instanceof Wire) {
					Wire wire = (Wire) component2;
					wire.setVisible(false);
				}

			}

			this.repaint();
		}
	}

	public void unhideWires() {
		if (this.hiddenwires == true) {
			this.hiddenwires = false;

			Component[] components = this.getComponentsInLayer(Circuit.WireLayer);

			for (Component component2 : components) {
				if (component2 instanceof Wire) {
					Wire wire = (Wire) component2;
					wire.setVisible(true);
				}

			}

			this.repaint();
		}
	}

	public Trace getTrace() {
		return this.trace;
	}

	public void updateTrace() {
		this.trace.simulate();
	}

	public boolean isSimulating() {
		return this.issimulating;
	}

	public JBreadBoardEventSimulation getSimulation() {
		return this.simulation;
	}

	public long getSimTime() {
		return this.simulation.getCurrentTime();
	}

	public void stepSimulation() {
		if (!this.isSimulating() || !this.jBreadBoard.getMode().equals("sim")) {
			this.resetSimulation();
		}
		this.issimulating = true;
		this.simulation.stepSimulation();
		this.jBreadBoard.setMode("sim", "In Simulation.  Current time = " + this.simulation.getCurrentTime() + "ns");
	}

	public void run() {
		if (this.timer == null) {
			this.timer = new Timer(1, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Circuit.this.runFor(Circuit.this.jBreadBoard.getSimSpeed());
				}
			});
		}
		this.timer.start();
	}

	public void pause() {
		if (this.timer != null) {
			this.timer.stop();
		}
	}

	public void runFor(int duration) {
		if (!this.isSimulating() || !this.jBreadBoard.getMode().equals("sim")) {
			this.resetSimulation();
		}
		this.issimulating = true;
		this.simulation.runFor(duration);
		this.jBreadBoard.setMode("sim", "In Simulation.  Current time = " + this.simulation.getCurrentTime() + "ns.");
	}

	public void resetSimulation() {
		this.simulation = new JBreadBoardEventSimulation(this);
		Component[] components = this.getComponentsInLayer(Circuit.BoardLayer);

		this.trace.reset();

		for (Component component2 : components) {
			if (component2 instanceof Board) {
				((Board) component2).reset();
			}
		}
		this.issimulating = false;
	}

	public JBreadBoard getJBreadBoard() {
		return this.jBreadBoard;
	}

	public void select(CircuitSelection c) {
		CircuitSelection old = this.selected;
		this.selected = c;
		if (old != null) {
			old.deselect();
		}
		if (c != null) {
			c.select();
			if (c instanceof Board) {
				this.scrollRectToVisible(((Board) c).getBounds());
			}
			BreadBoard oldbb = this.BBselect;
			if (c instanceof BreadBoard) {
				this.BBselect = (BreadBoard) c;
				if (oldbb != null) {
					oldbb.repaint();
				}
			}
		}
		this.jBreadBoard.getSelectPane().select(c);
	}

	public void delete() {
		if (this.selected != null) {
			CircuitSelection cs = this.selected;
			cs.delete();
		}
		this.select(null);
	}

	Circuit(JBreadBoard j) {
		this.jBreadBoard = j;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(600, 400));
		this.addMouseListener(new WireDrawer(this));
		this.addMouseMotionListener(new WireDrawer(this));
	}

	@Override
	public String toString() {
		String string = new String("Circuit");

		Component[] components = this.getComponentsInLayer(Circuit.BoardLayer);
		for (Component component2 : components) {
			string = string + "\n" + component2.toString();
		}

		for (Component component2 : components) {
			if (component2 instanceof Board) {
				Board board = (Board) component2;

				Iterator<Wire> iterator = board.wires.iterator();

				while (iterator.hasNext()) {
					Wire wire = iterator.next();
					string = string + "\n" + wire.toString();
				}
			}
		}

		return string + "\n";
	}

	public void add(Wire w) {
		this.add(w, Circuit.WireLayer);
	}

	public int getComponentCountinLayer(Integer i) {
		return this.getComponentCountInLayer(i.intValue());
	}

	public Component[] getComponentsInLayer(Integer i) {
		return this.getComponentsInLayer(i.intValue());
	}

	public Component getComponentAt(int x, int y, Integer layer) {
		int n = this.getComponentCountInLayer(layer.intValue());
		Component[] comps = this.getComponentsInLayer(layer.intValue());

		for (int i = 0; i < n; i++) {
			if (comps[i].contains(x - comps[i].getX(), y - comps[i].getY())) {
				return comps[i];
			}
		}
		return null;
	}

	public Board getBoardAt(int x, int y) {
		return (Board) this.getComponentAt(x, y, Circuit.BoardLayer);
	}

	public Wire getWireAt(int x, int y) {
		return (Wire) this.getComponentAt(x, y, Circuit.WireLayer);
	}

	public Wire drawWire(Color col, int x1, int y1, int x2, int y2) {
		return this.extendWire(null, col, x1, y1, x2, y2);
	}

	public Wire extendWire(Wire w, Color col, int x1, int y1, int x2, int y2) {
		boolean horizontal = true;

		if (w != null) {
			x1 = w.getEndX();
			y1 = w.getEndY();
		} else {
			x1 = x1 - x1 % 8 + 4;
			y1 -= y1 % 8;
		}
		x2 = x2 - x2 % 8 + 4;
		y2 -= y2 % 8;

		int difx = Math.abs(x1 - x2);
		int dify = Math.abs(y1 - y2);

		if (dify > difx) {
			horizontal = false;
		}

		if (w != null) {
			int x0 = w.getStartX();
			int y0 = w.getStartY();

			if (horizontal) {
				if (x1 > x0 && x2 <= x1) {
					horizontal = false;
				} else if (x1 < x0 && x2 >= x1) {
					horizontal = false;
				}
			} else if (y1 > y0 && y2 <= y1) {
				horizontal = true;
			} else if (y1 < y0 && y2 >= y1) {
				horizontal = true;
			}

		}

		if (horizontal) {
			y2 = y1;
		} else {
			x2 = x1;
		}

		if (x2 == x1 && y2 == y1) {
			this.repaint();
			return null;
		}

		Wire newsegment = new Wire(this, col, horizontal, x1, y1, x2, y2, w);
		this.add(newsegment);
		this.repaint();
		return newsegment;
	}

	public boolean add(Board b) {
		Rectangle bounds = b.getBounds();
		Rectangle innerbounds = b.getInnerBounds();

		boolean clear = true;
		Component[] comps = this.getComponentsInLayer(Circuit.BoardLayer);
		int maxi = comps.length;

		for (int i = 0; clear && i < maxi; i++) {
			Board testboard = (Board) comps[i];
			if (innerbounds.intersects(testboard.getInnerBounds())) {
				clear = false;
				i++;
			}

		}

		if (clear) {
			int X2 = bounds.x + bounds.width;
			int Y2 = bounds.y + bounds.height;

			if (this.getPreferredSize().width < X2) {
				this.setPreferredSize(new Dimension(X2, this.getPreferredSize().height));
				this.revalidate();
			}
			if (this.getPreferredSize().height < Y2) {
				this.setPreferredSize(new Dimension(this.getPreferredSize().width, Y2));
				this.revalidate();
			}

			this.add(b, Circuit.BoardLayer);
		}
		return clear;
	}

	public void place(Board b) {
		int y = 0;
		boolean added = false;

		while (!added) {
			int x = 0;
			while (!added && x < y) {
				b.setLocation(x * 504, y * 168);
				added = this.add(b);
				x++;
			}
			y = 0;
			if (!added && y <= x) {
				b.setLocation(x * 504, y * 168);
				added = this.add(b);
				y++;
			}
		}
		this.select(b);
	}
}
