package designTools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Workspace extends JPanel {
	private CircuitComponent movingComponent = null;
	private static final int GRID_SIZE = 10;
	private JScrollPane parentPane;
	private boolean wiringMode = false;
	private boolean drawingWire = false;
	private ArrayList wireList = new ArrayList();
	private ArrayList outputPinList = new ArrayList();
	private ArrayList inputPinList = new ArrayList();
	private CircuitComponent selectedCircuitComponent;
	private JLabel lblStatus;
	private Color wireColor = Color.BLACK;
	private CircuitComponent draggedComponent;
	private int selectedWireIndex = -1;

	public Workspace(JScrollPane container, JLabel statusBar) {
		this.lblStatus = statusBar;
		this.parentPane = container;
		this.setLayout(null);
		this.setBackground(Color.white);
		this.addMouseListener(new Workspace_mouseAdapter(this));
		this.addMouseMotionListener(new Workspace_mouseMotionAdapter(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i <= this.getWidth(); i += 10) {
			for (int j = 0; j <= this.getHeight(); j += 10) {
				if (i + j > 0) {
					g2.drawLine(i, j, i + 1, j);
				}
			}
		}
		for (int i = 0; i < this.wireList.size(); i++) {
			((Wire) this.wireList.get(i)).draw(g2);
		}
	}

	public void addCircuitComponent(int type) {
		this.unselectComponent();
		this.setWiringMode(false);
		this.movingComponent = new CircuitComponent(type);
		this.add(this.movingComponent, null);
		this.lblStatus.setText("Click on the workspace to place the component");
	}

	public void createLabel() {
		this.unselectComponent();

		this.setWiringMode(false);
		String text = JOptionPane.showInputDialog(null, "Insert Label Text", "Label Text", -1);
		if (text != null) {
			this.movingComponent = new CircuitComponent(text);
			this.add(this.movingComponent, null);
		}
	}

	public static Point snapToGrid(Point mousePosition) {
		double mouseX = mousePosition.getX();
		double mouseY = mousePosition.getY();
		double offset = mouseX % 10.0D;
		if (offset <= 5.0D) {
			mouseX -= offset;
		} else {
			mouseX += 10.0D - offset;
		}
		offset = mouseY % 10.0D;
		if (offset <= 5.0D) {
			mouseY -= offset;
		} else {
			mouseY += 10.0D - offset;
		}
		return new Point((int) mouseX, (int) mouseY);
	}

	void workspace_mouseMoved(MouseEvent e) {
		Point gridPos = Workspace.snapToGrid(e.getPoint());

		if (this.movingComponent != null) {
			this.movingComponent.setLocation(gridPos);
			this.repaint();
		} else if (this.drawingWire) {
			((Wire) this.wireList.get(this.wireList.size() - 1)).sketchLineTo(gridPos);
			this.repaint();
		}
	}

	private void placeComponentAt(Point gridPos) {
		if (gridPos.getX() >= this.getWidth() - 100) {
			this.parentPane.getViewport().remove(this);
			this.setPreferredSize(new Dimension(this.getWidth() + 100, this.getHeight()));
			this.parentPane.getViewport().add(this, null);
			this.repaint();
		}
		if (gridPos.getY() >= this.getHeight() - 80) {
			this.parentPane.getViewport().remove(this);
			this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() + 100));
			this.parentPane.getViewport().add(this, null);
			this.repaint();
		}

		if (this.movingComponent.getType() == 3) {
			this.inputPinList.add(this.movingComponent);
		} else if (this.movingComponent.getType() == 4) {
			this.outputPinList.add(this.movingComponent);
		}
		this.lblStatus.setText(" ");
	}

	void workspace_mouseClicked(MouseEvent e) {
		Point gridPos = Workspace.snapToGrid(e.getPoint());

		if (e.getButton() == 1) {
			this.unselectComponent();

			if (this.movingComponent != null) {
				this.placeComponentAt(gridPos);
			} else if (this.wiringMode) {
				this.showWiringModeText();
				if (this.drawingWire) {
					((Wire) this.wireList.get(this.wireList.size() - 1)).lineTo(gridPos);
					if (e.getClickCount() > 1) {
						this.drawingWire = false;
						((Wire) this.wireList.get(this.wireList.size() - 1)).connect(gridPos);
					}
				} else {
					this.drawingWire = true;
					this.wireList.add(new Wire(gridPos, this.wireColor, this));
					((Wire) this.wireList.get(this.wireList.size() - 1)).connect(gridPos);
				}

			} else if (this.getComponentAt(e.getPoint()).getClass().equals(new CircuitComponent(0).getClass())) {
				this.selectedCircuitComponent = (CircuitComponent) this.getComponentAt(e.getPoint());
				this.selectedCircuitComponent.setSelected(true);
			} else {
				for (int i = 0; i < this.wireList.size(); i++) {
					if (((Wire) this.wireList.get(i)).isWirePoint(gridPos)) {
						this.selectedWireIndex = i;
						((Wire) this.wireList.get(i)).setSelected(true);
						i = this.wireList.size();
					}
				}
			}
			this.repaint();
			this.movingComponent = null;
		} else if (e.getButton() != 3) {
			;
		}
	}

	void workspace_mouseDragged(MouseEvent e) {
		if (this.draggedComponent != null) {
			this.disconnectComponent(this.draggedComponent);
			this.draggedComponent.setLocation(Workspace.snapToGrid(e.getPoint()));
			this.repaint();
		}
	}

	void workspace_mousePressed(MouseEvent e) {
		if (!this.wiringMode) {
			Component comp = this.getComponentAt(e.getPoint());
			if (comp.getClass().equals(new CircuitComponent(0).getClass())) {
				this.draggedComponent = (CircuitComponent) comp;
			}
		}
	}

	void workspace_mouseReleased(MouseEvent e) {
		this.draggedComponent = null;
	}

	public void unselectComponent() {
		if (this.selectedCircuitComponent != null) {
			this.selectedCircuitComponent.setSelected(false);
		}
		this.selectedCircuitComponent = null;
		if (this.selectedWireIndex != -1) {
			((Wire) this.wireList.get(this.selectedWireIndex)).setSelected(false);
		}
		this.selectedWireIndex = -1;
	}

	public void selectComponent(CircuitComponent comp) {
		this.selectedCircuitComponent = comp;
		comp.setSelected(true);
	}

	private void showWiringModeText() {
		this.lblStatus.setText("Wiring Mode: Click to start wire; click to bend wire; double-click to end wire; click pointer icon to exit wiring mode");
	}

	public void setWiringMode(boolean mode) {
		this.wiringMode = mode;
		this.drawingWire = false;
		this.unselectComponent();

		if (mode) {
			this.showWiringModeText();
		} else {
			this.lblStatus.setText(" ");
		}
	}

	public void exitPlacementMode() {
		if (this.movingComponent != null) {
			this.remove(this.movingComponent);
		}
		if (this.drawingWire) {
			this.wireList.remove(this.wireList.size() - 1);
		}
		this.movingComponent = null;
		this.setWiringMode(false);
		this.unselectComponent();
		this.repaint();
	}

	public void disconnectComponent(CircuitComponent comp) {
		for (int i = this.wireList.size() - 1; i >= 0; i--) {
			if (i < this.wireList.size() && ((Wire) this.wireList.get(i)).disconnect(comp)) {
				((Wire) this.wireList.get(i)).removeWire();
			}

			this.repaint();
		}
	}

	public static int getGridSize() {
		return 10;
	}

	public ArrayList getOutputs() {
		return this.outputPinList;
	}

	public ArrayList getInputs() {
		return this.inputPinList;
	}

	public ArrayList getWires() {
		return this.wireList;
	}

	public JLabel getStatusBar() {
		return this.lblStatus;
	}

	public void deleteSelectedComponent() {
		if (this.selectedCircuitComponent != null) {
			this.disconnectComponent(this.selectedCircuitComponent);
			this.remove(this.selectedCircuitComponent);
			this.outputPinList.remove(this.selectedCircuitComponent);
			this.inputPinList.remove(this.selectedCircuitComponent);
			this.unselectComponent();
			this.repaint();
		} else if (this.selectedWireIndex > -1) {
			((Wire) this.wireList.get(this.selectedWireIndex)).removeWire();
			this.selectedWireIndex = -1;
			this.repaint();
		}
	}

	public void moveSelectedComponent() {
		if (this.selectedCircuitComponent != null) {
			this.disconnectComponent(this.selectedCircuitComponent);
			this.remove(this.selectedCircuitComponent);
			this.movingComponent = this.selectedCircuitComponent;
			this.add(this.movingComponent, null);
			this.unselectComponent();
			this.repaint();
		}
	}

	public void renameSelectedComponent() {
		if (this.selectedCircuitComponent != null) {
			int componentType = this.selectedCircuitComponent.getType();
			if (componentType == 3 || componentType == 4 || componentType == 7) {
				if (componentType == 3) {
					this.disconnectComponent(this.selectedCircuitComponent);
				}
				this.selectedCircuitComponent.updateText();
			}
		}
	}

	public void setWireColor(Color colour) {
		this.wireColor = colour;
	}

	public void cancelWireSegment() {
		if (this.drawingWire) {
			Wire lastWire = (Wire) this.wireList.get(this.wireList.size() - 1);
			lastWire.removeLastPoint();
			if (lastWire.getPointCount() < 2) {
				this.wireList.remove(lastWire);
				this.drawingWire = false;
			}
			this.repaint();
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.Workspace JD-Core Version: 0.6.2
 */
