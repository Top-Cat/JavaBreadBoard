package jBreadBoard;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class BreadBoard extends Board {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon graphic;
	private Node[] nodes = new Node[550];

	private static final Rectangle[] holes = { new Rectangle(76, 40, 376, 40), new Rectangle(76, 96, 376, 40), new Rectangle(76, 16, 40, 8), new Rectangle(124, 16, 40, 8), new Rectangle(172, 16, 40, 8), new Rectangle(220, 16, 40, 8), new Rectangle(268, 16, 40, 8), new Rectangle(316, 16, 40, 8), new Rectangle(364, 16, 40, 8), new Rectangle(412, 16, 40, 8), new Rectangle(76, 152, 40, 8), new Rectangle(124, 152, 40, 8), new Rectangle(172, 152, 40, 8), new Rectangle(220, 152, 40, 8), new Rectangle(268, 152, 40, 8), new Rectangle(316, 152, 40, 8), new Rectangle(364, 152, 40, 8), new Rectangle(412, 152, 40, 8) };

	@Override
	public String getDiagram() {
		return "images/sboard.gif";
	}

	@Override
	public String getInfo() {
		return "Component Name: \nBreadBoard\n\nJBreadBoard library name: \n" + this.getClass().getName().substring(12) + ".class";
	}

	@Override
	public void reset() {
		super.reset();
		for (Node node : this.nodes) {
			node.setPotential("NC", -1L);
		}

		this.nodes[0].setType("OUT");
		this.nodes[510].setType("OUT");
		this.getCircuit().getSimulation().addEvent(this.nodes[0], 0, "HIGH");
		this.getCircuit().getSimulation().addEvent(this.nodes[510], 0, "LOW");
	}

	@Override
	public Rectangle getInnerBounds() {
		return new Rectangle(this.getX() + 12, this.getY(), 504, 168);
	}

	@Override
	public boolean isHole(int x, int y) {
		boolean ishole = false;

		for (int i = 0; !ishole && i < BreadBoard.holes.length; i++) {
			if (BreadBoard.holes[i].contains(x, y)) {
				ishole = true;
			}
		}
		return ishole;
	}

	@Override
	public Node getNodeAt(int x, int y) {
		int X = (x - 76) / 8;
		int Y = (y - 40) / 8 * 47 + 40;

		if (x >= 76 && x < 452) {
			if (y >= 40 && y < 80) {
				return this.nodes[X + Y];
			}
			if (y >= 96 && y < 136) {
				return this.nodes[X + Y - 94];
			}
			if (y >= 16 && y < 24) {
				if (x < 116) {
					return this.nodes[X];
				}
				if (x >= 124 && x < 164) {
					return this.nodes[X - 1];
				}
				if (x >= 172 && x < 212) {
					return this.nodes[X - 2];
				}
				if (x >= 220 && x < 260) {
					return this.nodes[X - 3];
				}
				if (x >= 268 && x < 308) {
					return this.nodes[X - 4];
				}
				if (x >= 316 && x < 356) {
					return this.nodes[X - 5];
				}
				if (x >= 364 && x < 404) {
					return this.nodes[X - 6];
				}
				if (x >= 412) {
					return this.nodes[X - 7];
				}
			} else if (y >= 152 && y < 160) {
				if (x < 116) {
					return this.nodes[X + 510];
				}
				if (x >= 124 && x < 164) {
					return this.nodes[X + 510 - 1];
				}
				if (x >= 172 && x < 212) {
					return this.nodes[X + 510 - 2];
				}
				if (x >= 220 && x < 260) {
					return this.nodes[X + 510 - 3];
				}
				if (x >= 268 && x < 308) {
					return this.nodes[X + 510 - 4];
				}
				if (x >= 316 && x < 356) {
					return this.nodes[X + 510 - 5];
				}
				if (x >= 364 && x < 404) {
					return this.nodes[X + 510 - 6];
				}
				if (x >= 412) {
					return this.nodes[X + 510 - 7];
				}
			}
		}
		return null;
	}

	public void add(LED l) {
		if (l != null) {
			boolean clear = false;
			Rectangle dest = new Rectangle(76, 72, l.getWidth(), l.getHeight());

			for (int i = 0; !clear && i < 47; i++) {
				dest.x = 76 + i * 8;
				Component[] components = this.getComponents();
				clear = true;
				for (int j = 0; clear && j < components.length; j++) {
					clear = !dest.intersects(components[j].getBounds());
				}
			}

			if (clear) {
				l.setLocation(dest.x, dest.y);
				this.add((Component) l);
				this.repaint();
				l.updateConnections();
			}
		}
	}

	public void add(Probe p) {
		if (p != null) {
			boolean clear = false;
			Rectangle dest = new Rectangle(76, 40, p.getWidth(), p.getHeight());

			for (int i = 0; !clear && i < 47; i++) {
				dest.x = 76 + i * 8;
				Component[] components = this.getComponents();
				clear = true;
				for (int j = 0; clear && j < components.length; j++) {
					clear = !dest.intersects(components[j].getBounds());
				}
			}

			if (clear) {
				p.setLocation(dest.x, dest.y);
				this.add((Component) p);
				this.repaint();
				p.updateConnections();
			} else {
				this.getCircuit().getTrace().removeProbe(p);
			}
		}
	}

	public void add(Dipswitch d) {
		if (d != null) {
			boolean clear = false;
			Rectangle dest = new Rectangle(76, 72, d.getWidth(), d.getHeight());

			for (int i = 0; !clear && i <= 47 - d.getWidth() / 8; i++) {
				dest.x = 76 + i * 8;
				Component[] components = this.getComponents();
				clear = true;
				for (int j = 0; clear && j < components.length; j++) {
					clear = !dest.intersects(components[j].getBounds());
				}
			}

			if (clear) {
				d.setLocation(dest.x, dest.y);
				this.add((Component) d);
				this.repaint();
				d.updateConnections();
			}
		}
	}

	public boolean add(Chip c) {
		return this.add(c, 0);
	}

	public boolean add(Chip c, int Xpos) {
		boolean success = false;

		Xpos = Xpos - Xpos % 8 + 4;

		if (Xpos < 76) {
			Xpos = 76;
		}
		if (Xpos > 452 - c.getWidth()) {
			Xpos = 452 - c.getWidth();
		}

		Rectangle dest = new Rectangle(Xpos, 88 - c.getHeight() / 2, c.getWidth(), c.getHeight());

		while (!success && dest.x + dest.width <= 452) {
			success = true;
			int x2 = dest.x;

			for (int i = 0; i < this.getComponentCount(); i++) {
				Rectangle bounds = this.getComponent(i).getBounds();
				if (bounds.intersects(dest)) {
					success = false;
					if (bounds.x + bounds.width > x2) {
						x2 = bounds.x + bounds.width;
					}
				}
			}

			dest.x = x2;
		}

		if (success) {
			this.add((Component) c);
			c.setBounds(dest);
			if (!c.tryupdateConnections()) {
				c.delete();
				if (dest.x < 452 - c.getWidth()) {
					return this.add(c, dest.x + 8);
				}
				return false;
			}
		}
		return success;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.getCircuit().BBselect == this) {
			this.graphic.paintIcon(this, g, 0, -180);
		} else {
			this.graphic.paintIcon(this, g, 0, 0);
		}
	}

	private void initialiseNodes() {
		int i = 0;
		int x = 0;
		int y = 0;

		this.nodes[0] = new Node();
		for (i = 1; i < 40; i++) {
			this.nodes[i] = new Node();
			new Potential(this.nodes[i], this.nodes[0]);
		}

		for (x = 0; x < 47; x++) {
			this.nodes[40 + x] = new Node();
			for (y = 1; y < 5; y++) {
				this.nodes[40 + x + y * 47] = new Node();
				new Potential(this.nodes[40 + x + y * 47], this.nodes[40 + x]);
			}

		}

		for (x = 0; x < 47; x++) {
			this.nodes[275 + x] = new Node();
			for (y = 1; y < 5; y++) {
				this.nodes[275 + x + y * 47] = new Node();
				new Potential(this.nodes[275 + x + y * 47], this.nodes[275 + x]);
			}

		}

		this.nodes[510] = new Node();
		for (i = 511; i < 550; i++) {
			this.nodes[i] = new Node();
			new Potential(this.nodes[i], this.nodes[510]);
		}
	}

	public BreadBoard(Circuit c) {
		super(c);
		this.graphic = c.getJBreadBoard().getImageIcon("images/board.gif");
		this.initialiseNodes();
		this.setSize(516, 180);
		this.setOpaque(false);

		this.reset();
	}
}
