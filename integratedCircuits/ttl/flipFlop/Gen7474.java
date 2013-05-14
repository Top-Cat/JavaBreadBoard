package integratedCircuits.ttl.flipFlop;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen7474 extends IntegratedCircuit {
	public Gen7474() {
		this.initialise();

		this.pins.add(new OutputPin(5, "1Q"));
		this.pins.add(new OutputPin(6, "1nQ"));
		this.pins.add(new OutputPin(9, "2Q"));
		this.pins.add(new OutputPin(8, "2nQ"));
	}

	public Gen7474(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(5, "1Q", tplh, tphl));
		this.pins.add(new OutputPin(6, "1nQ", tplh, tphl));
		this.pins.add(new OutputPin(9, "2Q", tplh, tphl));
		this.pins.add(new OutputPin(8, "2nQ", tplh, tphl));
	}

	private void initialise() {
		this.description = "Dual D Flip-Flops with preset and clear";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7474.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1nCLR"));
		this.pins.add(new InputPin(2, "1D"));
		this.pins.add(new InputPin(3, "1CLK"));
		this.pins.add(new InputPin(4, "1nPRE"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(10, "2nPRE"));
		this.pins.add(new InputPin(11, "2CLK"));
		this.pins.add(new InputPin(12, "2D"));
		this.pins.add(new InputPin(13, "2nCLR"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String CLK, String CLR, String PRE, String D, String Q, String NQ) throws InvalidPinException {
		if (this.isLow(CLR)) {
			this.setPin(Q, Pin.PinState.LOW);
			this.setPin(NQ, Pin.PinState.HIGH);
		} else if (this.isLow(PRE)) {
			this.setPin(Q, Pin.PinState.HIGH);
			this.setPin(NQ, Pin.PinState.LOW);
		} else if (this.isRisingEdge(CLK)) {
			if (this.isLow(D)) {
				this.setPin(Q, Pin.PinState.LOW);
				this.setPin(NQ, Pin.PinState.HIGH);
			} else {
				this.setPin(Q, Pin.PinState.HIGH);
				this.setPin(NQ, Pin.PinState.LOW);
			}
		}
	}

	@Override
	public void reset() {
		try {
			for (Pin pin : this.pins) {
				if (this.isPinDriven(pin.getPinName())) {
					this.setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
				}
			}
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
		}
	}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("1CLK", "1nCLR", "1nPRE", "1D", "1Q", "1nQ");
				this.updateGate("2CLK", "2nCLR", "2nPRE", "2D", "2Q", "2nQ");
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
 * \classes\ Qualified Name: integratedCircuits.ttl.flipFlop.Gen7474 JD-Core
 * Version: 0.6.2
 */
