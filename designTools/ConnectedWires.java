package designTools;

import java.util.ArrayList;

public class ConnectedWires extends ArrayList {
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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.ConnectedWires JD-Core Version: 0.6.2
 */
