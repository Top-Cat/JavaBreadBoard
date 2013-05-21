package uk.ac.york.jbb.designtools;

import java.util.ArrayList;

public class ConnectedWires extends ArrayList<Wire> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CircuitComponent sourceComponent;

	public void setSourceComponent(CircuitComponent component) {
		this.sourceComponent = component;
	}

	public CircuitComponent getSourceComponent() {
		return this.sourceComponent;
	}

	public void addExclusive(Wire wire) {
		boolean isMember = false;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(wire)) {
				isMember = true;

			}
		}
		if (!isMember) {
			this.add(wire);

		}
	}
}
