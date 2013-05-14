package integratedCircuits.ttl.logic;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.NotConnectedPin;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen7421 extends IntegratedCircuit {
	public Gen7421() {
		this.initialise();

		this.pins.add(new OutputPin(6, "1Y"));
		this.pins.add(new OutputPin(8, "2Y"));
	}

	public Gen7421(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(6, "1Y", tplh, tphl));
		this.pins.add(new OutputPin(8, "2Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "Dual 4-Input Positive-AND Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7421.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1A"));
		this.pins.add(new InputPin(2, "1B"));
		this.pins.add(new NotConnectedPin(3, "NC"));
		this.pins.add(new InputPin(4, "1C"));
		this.pins.add(new InputPin(5, "1D"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(9, "2A"));
		this.pins.add(new InputPin(10, "2B"));
		this.pins.add(new NotConnectedPin(11, "NC"));
		this.pins.add(new InputPin(12, "2C"));
		this.pins.add(new InputPin(13, "2D"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String B, String C, String D, String Y) throws InvalidPinException {
		if (this.isHigh(A) && this.isHigh(B) && this.isHigh(C) && this.isHigh(D)) {
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
				this.updateGate("1A", "1B", "1C", "1D", "1Y");
				this.updateGate("2A", "2B", "2C", "2D", "2Y");
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
