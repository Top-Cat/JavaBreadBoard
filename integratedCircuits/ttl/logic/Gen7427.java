package integratedCircuits.ttl.logic;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen7427 extends IntegratedCircuit {
	public Gen7427() {
		this.initialise();

		this.pins.add(new OutputPin(12, "1Y"));
		this.pins.add(new OutputPin(6, "2Y"));
		this.pins.add(new OutputPin(8, "3Y"));
	}

	public Gen7427(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(12, "1Y", tplh, tphl));
		this.pins.add(new OutputPin(6, "2Y", tplh, tphl));
		this.pins.add(new OutputPin(8, "3Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "Triple 3-Input Positive-NOR Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7427.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1A"));
		this.pins.add(new InputPin(2, "1B"));
		this.pins.add(new InputPin(3, "2A"));
		this.pins.add(new InputPin(4, "2B"));
		this.pins.add(new InputPin(5, "2C"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(9, "3A"));
		this.pins.add(new InputPin(10, "3B"));
		this.pins.add(new InputPin(11, "3C"));
		this.pins.add(new InputPin(13, "1C"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String B, String C, String Y) throws InvalidPinException {
		if (this.isLow(A) && this.isLow(B) && this.isLow(C)) {
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
				this.updateGate("1A", "1B", "1C", "1Y");
				this.updateGate("2A", "2B", "2C", "2Y");
				this.updateGate("3A", "3B", "3C", "3Y");
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
