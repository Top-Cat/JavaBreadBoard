package uk.ac.york.jbb.integratedcircuits.ttl.logic;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen7428 extends IntegratedCircuit {
	public Gen7428() {
		this.initialise();

		this.pins.add(new OutputPin(1, "1Y"));
		this.pins.add(new OutputPin(4, "2Y"));
		this.pins.add(new OutputPin(10, "3Y"));
		this.pins.add(new OutputPin(13, "4Y"));
	}

	public Gen7428(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(1, "1Y", tplh, tphl));
		this.pins.add(new OutputPin(4, "2Y", tplh, tphl));
		this.pins.add(new OutputPin(10, "3Y", tplh, tphl));
		this.pins.add(new OutputPin(13, "4Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "Quadruple 2-Input Positive-NOR Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7428.gif";
		this.wide = false;

		this.pins.add(new InputPin(2, "1A"));
		this.pins.add(new InputPin(3, "1B"));
		this.pins.add(new InputPin(5, "2A"));
		this.pins.add(new InputPin(6, "2B"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(8, "3A"));
		this.pins.add(new InputPin(9, "3B"));
		this.pins.add(new InputPin(11, "4A"));
		this.pins.add(new InputPin(12, "4B"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String B, String Y) throws InvalidPinException {
		if (this.isLow(A) && this.isLow(B)) {
			this.setPin(Y, Pin.PinState.HIGH);
		} else {
			this.setPin(Y, Pin.PinState.LOW);

		}
	}

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("1A", "1B", "1Y");
				this.updateGate("2A", "2B", "2Y");
				this.updateGate("3A", "3B", "3Y");
				this.updateGate("4A", "4B", "4Y");
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
