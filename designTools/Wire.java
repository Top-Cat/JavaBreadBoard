package designTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Wire {
	private ArrayList<Point> pointList = new ArrayList<Point>();
	private Point sketchPoint;
	private CircuitComponent sourceComponent = null;
	private CircuitComponent targetComponent = null;
	private Workspace workSpace;
	private ArrayList<Point> nodeList = new ArrayList<Point>();
	private Color wireColor;
	private boolean isSelected;
	private ConnectedWires connectedWires = new ConnectedWires();

	public Wire(Point startPoint, Color colour, Workspace workspace) {
		this.workSpace = workspace;
		this.wireColor = colour;

		this.pointList.add(startPoint);
		this.sketchPoint = startPoint;
		this.connectedWires.add(this);
	}

	public void connect(Point point) {
		Component comp = this.workSpace.getComponentAt((int) point.getX() - Workspace.getGridSize(), (int) point.getY());
		if (comp.getClass().equals(new CircuitComponent(0).getClass())) {
			CircuitComponent circuitComp = (CircuitComponent) comp;
			if (point.equals(circuitComp.getOutputLocation())) {
				if (this.connectedWires.getSourceComponent() != null) {
					this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
					this.showErrorMessage();
				} else {
					this.nodeList.add(point);
					this.sourceComponent = circuitComp;
					this.connectedWires.setSourceComponent(circuitComp);

					for (int i = 0; i < this.connectedWires.size(); i++) {
						if (this.connectedWires.get(i).targetComponent != null) {
							this.connectedWires.get(i).targetComponent.connectInput(this.sourceComponent);
						}
					}
				}
			}

		}

		comp = this.workSpace.getComponentAt(point);
		if (comp.getClass().equals(new CircuitComponent(0).getClass())) {
			CircuitComponent circuitComp = (CircuitComponent) comp;
			for (int i = 0; i < circuitComp.getInputLocations().length; i++) {
				if (point.equals(circuitComp.getInputLocations()[i])) {
					boolean alreadyConnected = false;
					for (int j = 0; j < this.workSpace.getWires().size() - 1; j++) {
						if (this.workSpace.getWires().get(j).isWirePoint(point)) {
							alreadyConnected = true;
						}
					}
					if (alreadyConnected) {
						this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
						this.showErrorMessage();
					} else {
						this.nodeList.add(point);
						this.targetComponent = circuitComp;

						if (this.sourceComponent != null) {
							this.targetComponent.connectInput(this.sourceComponent);
						} else if (this.connectedWires.getSourceComponent() != null) {
							this.targetComponent.connectInput(this.connectedWires.getSourceComponent());
						}

					}

				}

			}

		} else {
			ArrayList<Wire> wireList = this.workSpace.getWires();

			for (int i = 0; i < wireList.size() - 1; i++) {
				Wire wire = wireList.get(i);
				if (wire.isWirePoint(point)) {
					if (wire.getConnectedWires().getSourceComponent() != null && this.connectedWires.getSourceComponent() != null && !wire.getConnectedWires().getSourceComponent().equals(this.connectedWires.getSourceComponent())) {
						this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
						this.showErrorMessage();
					} else {
						for (int j = 0; j < this.connectedWires.size(); j++) {
							wire.getConnectedWires().addExclusive(this.connectedWires.get(j));
						}

						this.connectedWires = wire.getConnectedWires();

						if (this.connectedWires.getSourceComponent() == null) {
							this.connectedWires.setSourceComponent(this.sourceComponent);
						}

						if (this.connectedWires.getSourceComponent() != null) {
							for (int j = 0; j < this.connectedWires.size(); j++) {
								Wire w = this.connectedWires.get(j);
								if (w.targetComponent != null) {
									boolean needToUpdate = true;
									for (int k = 0; k < w.targetComponent.getConnectedInputs().size(); k++) {
										if (this.connectedWires.getSourceComponent().equals(w.targetComponent.getConnectedInputs().get(k))) {
											needToUpdate = false;
										}
									}
									if (needToUpdate) {
										w.targetComponent.connectInput(this.connectedWires.getSourceComponent());
									}
								}
							}
						}

						this.nodeList.add(point);
					}
				}
			}
		}
	}

	private void showErrorMessage() {
		this.workSpace.getStatusBar().setText(" WARNING:- Wire removed. That wire potentially directly connected outputs");
	}

	public ConnectedWires getConnectedWires() {
		return this.connectedWires;
	}

	public void lineTo(Point bendPoint) {
		Point newPoint = this.toHorizontalOrVertical(bendPoint);
		this.pointList.add(newPoint);
	}

	public void sketchLineTo(Point mousePoint) {
		this.sketchPoint = this.toHorizontalOrVertical(mousePoint);
	}

	private Point toHorizontalOrVertical(Point mousePoint) {
		Point previousPoint = this.pointList.get(this.pointList.size() - 1);

		double xlength = mousePoint.getX() - previousPoint.getX();
		double ylength = mousePoint.getY() - previousPoint.getY();
		if (xlength < 0.0D) {
			xlength *= -1.0D;
		}
		if (ylength < 0.0D) {
			ylength *= -1.0D;
		}
		Point returnPoint;
		if (xlength >= ylength) {
			returnPoint = new Point((int) mousePoint.getX(), (int) previousPoint.getY());
		} else {
			returnPoint = new Point((int) previousPoint.getX(), (int) mousePoint.getY());
		}
		return returnPoint;
	}

	public void removePoint(Point bendPoint) {
		this.pointList.remove(bendPoint);
	}

	public void removeLastPoint() {
		if (this.pointList.size() > 1) {
			this.pointList.remove(this.pointList.size() - 1);
		}
		this.sketchPoint = this.pointList.get(this.pointList.size() - 1);
	}

	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	public int getPointCount() {
		return this.pointList.size();
	}

	public boolean isWirePoint(Point point) {
		boolean isOnWire = false;

		for (int i = 0; i < this.pointList.size() - 1; i++) {
			Point currentPoint = this.pointList.get(i);
			Point nextPoint = this.pointList.get(i + 1);

			if (currentPoint.getX() == point.getX()) {
				if (nextPoint.getY() >= currentPoint.getY() && nextPoint.getY() >= point.getY() && point.getY() >= currentPoint.getY()) {
					isOnWire = true;
				} else if (currentPoint.getY() > nextPoint.getY() && currentPoint.getY() >= point.getY() && point.getY() >= nextPoint.getY()) {
					isOnWire = true;
				}

			} else if (currentPoint.getY() == point.getY()) {
				if (nextPoint.getX() >= currentPoint.getX() && nextPoint.getX() >= point.getX() && point.getX() >= currentPoint.getX()) {
					isOnWire = true;
				} else if (currentPoint.getX() > nextPoint.getX() && currentPoint.getX() >= point.getX() && point.getX() >= nextPoint.getX()) {
					isOnWire = true;
				}
			}
		}
		return isOnWire;
	}

	public void draw(Graphics2D g) {
		Stroke previousStroke = g.getStroke();
		Color previousColor = g.getColor();

		if (this.isSelected) {
			g.setStroke(new BasicStroke(4.0F));
		} else {
			g.setStroke(new BasicStroke(2.0F));
		}
		g.setColor(this.wireColor);
		GeneralPath path = new GeneralPath();
		path.moveTo((float) this.pointList.get(0).getX(), (float) this.pointList.get(0).getY());
		for (int i = 1; i < this.pointList.size(); i++) {
			path.lineTo((float) this.pointList.get(i).getX(), (float) this.pointList.get(i).getY());
		}
		path.lineTo((float) this.sketchPoint.getX(), (float) this.sketchPoint.getY());
		g.draw(path);

		g.setColor(previousColor);

		g.setStroke(new BasicStroke(6.0F));
		for (int i = 0; i < this.nodeList.size(); i++) {
			Point p = this.nodeList.get(i);
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(), (int) p.getY());
		}

		g.setStroke(previousStroke);
	}

	public void removeWire() {
		for (int i = 0; i < this.connectedWires.size(); i++) {
			if (this.connectedWires.getSourceComponent() != null && this.connectedWires.get(i).targetComponent != null) {
				this.connectedWires.get(i).targetComponent.disconnectInput(this.connectedWires.getSourceComponent());
			}

			this.workSpace.getWires().remove(this.connectedWires.get(i));
		}
	}

	public boolean disconnect(CircuitComponent comp) {
		boolean returnValue = false;

		for (int i = 0; i < comp.getInputLocations().length; i++) {
			for (int j = 0; j < this.pointList.size(); j++) {
				if (comp.getInputLocations()[i].equals(this.pointList.get(j))) {
					returnValue = true;
				}
			}
		}

		for (int j = 0; j < this.pointList.size(); j++) {
			if (comp.getOutputLocation() != null && comp.getOutputLocation().equals(this.pointList.get(j))) {
				returnValue = true;
				if (this.targetComponent != null) {
					this.targetComponent.disconnectInput(comp);
				}
			}
		}
		return returnValue;
	}
}
