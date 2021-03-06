package uk.ac.york.jbb.integratedcircuits.ttl.logic;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen7430 extends IntegratedCircuit {
	public Gen7430() {
		this.initialise();

		this.pins.add(new OutputPin(8, "Y"));
	}

	public Gen7430(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(8, "Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "8-Input Positive-NAND Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7430.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "A"));
		this.pins.add(new InputPin(2, "B"));
		this.pins.add(new InputPin(3, "C"));
		this.pins.add(new InputPin(4, "D"));
		this.pins.add(new InputPin(5, "E"));
		this.pins.add(new InputPin(6, "F"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(9, "NC"));
		this.pins.add(new InputPin(10, "NC"));
		this.pins.add(new InputPin(11, "G"));
		this.pins.add(new InputPin(12, "H"));
		this.pins.add(new InputPin(13, "NC"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String B, String C, String D, String E, String F, String G, String H, String Y) throws InvalidPinException {
		if (this.isHigh(A) && this.isHigh(B) && this.isHigh(C) && this.isHigh(D) && this.isHigh(E) && this.isHigh(F) && this.isHigh(G) && this.isHigh(H)) {
			this.setPin(Y, Pin.PinState.LOW);
		} else {
			this.setPin(Y, Pin.PinState.HIGH);

		}
	}

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("A", "B", "C", "D", "E", "F", "G", "H", "Y");
			} else {
				for (Pin pin : this.pins) {
					if (this.isPinDriven(pin.getPinName())) {
						this.setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);

					}
				}
			}
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
		}
	}
}
