package integratedCircuits.ttl.decoder;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74137 extends IntegratedCircuit {
	public Gen74137() {
		this.initialise();

		this.pins.add(new OutputPin(15, "Y0"));
		this.pins.add(new OutputPin(14, "Y1"));
		this.pins.add(new OutputPin(13, "Y2"));
		this.pins.add(new OutputPin(12, "Y3"));
		this.pins.add(new OutputPin(11, "Y4"));
		this.pins.add(new OutputPin(10, "Y5"));
		this.pins.add(new OutputPin(9, "Y6"));
		this.pins.add(new OutputPin(7, "Y7"));
	}

	public Gen74137(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(15, "Y0", tplh, tphl));
		this.pins.add(new OutputPin(14, "Y1", tplh, tphl));
		this.pins.add(new OutputPin(13, "Y2", tplh, tphl));
		this.pins.add(new OutputPin(12, "Y3", tplh, tphl));
		this.pins.add(new OutputPin(11, "Y4", tplh, tphl));
		this.pins.add(new OutputPin(10, "Y5", tplh, tphl));
		this.pins.add(new OutputPin(9, "Y6", tplh, tphl));
		this.pins.add(new OutputPin(7, "Y7", tplh, tphl));
	}

	private void initialise() {
		this.description = "3-Line to 8-Line Decoder / Demultiplexers";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74137.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "A"));
		this.pins.add(new InputPin(2, "B"));
		this.pins.add(new InputPin(3, "C"));
		this.pins.add(new InputPin(4, "nGL"));
		this.pins.add(new InputPin(5, "nG2"));
		this.pins.add(new InputPin(6, "G1"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String A, String B, String C, String nGL, String nG2, String G1, String Y0, String Y1, String Y2, String Y3, String Y4, String Y5, String Y6, String Y7) throws InvalidPinException {
		if (this.isHigh(nG2) || this.isLow(G1)) {
			this.resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
		} else if (this.isLow(nGL)) {
			int count = 0;
			if (this.isHigh(C)) {
				count += 4;
			} else if (this.isHigh(B)) {
				count += 2;
			} else if (this.isHigh(A)) {
				count += 1;
			}
			switch (count) {
				case 0:
					this.setPin(Y0, Pin.PinState.LOW);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 1:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.LOW);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 2:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.LOW);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 3:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.LOW);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 4:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.LOW);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 5:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.LOW);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 6:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.LOW);
					this.setPin(Y7, Pin.PinState.HIGH);
					break;
				case 7:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					this.setPin(Y4, Pin.PinState.HIGH);
					this.setPin(Y5, Pin.PinState.HIGH);
					this.setPin(Y6, Pin.PinState.HIGH);
					this.setPin(Y7, Pin.PinState.LOW);
					break;
				default:
					this.resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
			}
		}
	}

	private void resetGate(String Y0, String Y1, String Y2, String Y3, String Y4, String Y5, String Y6, String Y7) throws InvalidPinException {
		this.setPin(Y0, Pin.PinState.HIGH);
		this.setPin(Y1, Pin.PinState.HIGH);
		this.setPin(Y2, Pin.PinState.HIGH);
		this.setPin(Y3, Pin.PinState.HIGH);
		this.setPin(Y4, Pin.PinState.HIGH);
		this.setPin(Y5, Pin.PinState.HIGH);
		this.setPin(Y6, Pin.PinState.HIGH);
		this.setPin(Y7, Pin.PinState.HIGH);
	}

	@Override
	public void reset() {
		try {
			this.resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
		}
	}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("A", "B", "C", "nGL", "nG2", "G1", "Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
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
 * \classes\ Qualified Name: integratedCircuits.ttl.decoder.Gen74137 JD-Core
 * Version: 0.6.2
 */
