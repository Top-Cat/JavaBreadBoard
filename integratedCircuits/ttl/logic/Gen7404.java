package integratedCircuits.ttl.logic;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen7404 extends IntegratedCircuit {
	public Gen7404() {
		this.initialise();

		this.pins.add(new OutputPin(2, "1Y"));
		this.pins.add(new OutputPin(4, "2Y"));
		this.pins.add(new OutputPin(6, "3Y"));
		this.pins.add(new OutputPin(8, "4Y"));
		this.pins.add(new OutputPin(10, "5Y"));
		this.pins.add(new OutputPin(12, "6Y"));
	}

	public Gen7404(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(2, "1Y", tplh, tphl));
		this.pins.add(new OutputPin(4, "2Y", tplh, tphl));
		this.pins.add(new OutputPin(6, "3Y", tplh, tphl));
		this.pins.add(new OutputPin(8, "4Y", tplh, tphl));
		this.pins.add(new OutputPin(10, "5Y", tplh, tphl));
		this.pins.add(new OutputPin(12, "6Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "Hex NOT Gates";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7404.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1A"));
		this.pins.add(new InputPin(3, "2A"));
		this.pins.add(new InputPin(5, "3A"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(9, "4A"));
		this.pins.add(new InputPin(11, "5A"));
		this.pins.add(new InputPin(13, "6A"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String Y) throws InvalidPinException {
		if (this.isLow(A)) {
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
				this.updateGate("1A", "1Y");
				this.updateGate("2A", "2Y");
				this.updateGate("3A", "3Y");
				this.updateGate("4A", "4Y");
				this.updateGate("5A", "5Y");
				this.updateGate("6A", "6Y");
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
