package integratedCircuits.ttl.logic;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74260 extends IntegratedCircuit {
	public Gen74260() {
		this.initialise();

		this.pins.add(new OutputPin(5, "1Y"));
		this.pins.add(new OutputPin(6, "2Y"));
	}

	public Gen74260(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(5, "1Y", tplh, tphl));
		this.pins.add(new OutputPin(6, "2Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "Dual 5-Input Positive-NOR Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74260.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1A"));
		this.pins.add(new InputPin(2, "1B"));
		this.pins.add(new InputPin(3, "1C"));
		this.pins.add(new InputPin(4, "2A"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(8, "2B"));
		this.pins.add(new InputPin(9, "2C"));
		this.pins.add(new InputPin(10, "2D"));
		this.pins.add(new InputPin(11, "2E"));
		this.pins.add(new InputPin(12, "1D"));
		this.pins.add(new InputPin(13, "1E"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String B, String C, String D, String E, String Y) throws InvalidPinException {
		if (this.isLow(A) && this.isLow(B) && this.isLow(C) && this.isLow(D) && this.isLow(E)) {
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
				this.updateGate("1A", "1B", "1C", "1D", "1E", "1Y");
				this.updateGate("2A", "2B", "2C", "2D", "2E", "2Y");
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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.ttl.logic.Gen74260 JD-Core
 * Version: 0.6.2
 */
