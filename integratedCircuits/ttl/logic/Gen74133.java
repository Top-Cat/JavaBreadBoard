package integratedCircuits.ttl.logic;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74133 extends IntegratedCircuit {
	public Gen74133() {
		this.initialise();

		this.pins.add(new OutputPin(9, "Y"));
	}

	public Gen74133(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(9, "Y", tplh, tphl));
	}

	private void initialise() {
		this.description = "13-Input Positive-NAND Gate";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74133.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "A"));
		this.pins.add(new InputPin(2, "B"));
		this.pins.add(new InputPin(3, "C"));
		this.pins.add(new InputPin(4, "D"));
		this.pins.add(new InputPin(5, "E"));
		this.pins.add(new InputPin(6, "F"));
		this.pins.add(new InputPin(7, "G"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(10, "H"));
		this.pins.add(new InputPin(11, "I"));
		this.pins.add(new InputPin(12, "J"));
		this.pins.add(new InputPin(13, "K"));
		this.pins.add(new InputPin(14, "L"));
		this.pins.add(new InputPin(15, "M"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String A, String B, String C, String D, String E, String F, String G, String H, String I, String J, String K, String L, String M, String Y) throws InvalidPinException {
		if (this.isHigh(A) && this.isHigh(B) && this.isHigh(C) && this.isHigh(D) && this.isHigh(E) && this.isHigh(F) && this.isHigh(G) && this.isHigh(H) && this.isHigh(I) && this.isHigh(J) && this.isHigh(K) && this.isHigh(L) && this.isHigh(M)) {
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
				this.updateGate("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "Y");
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
