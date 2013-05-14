package integratedCircuits.ttl.decoder;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74139 extends IntegratedCircuit {
	public Gen74139() {
		this.initialise();

		this.pins.add(new OutputPin(4, "1Y0"));
		this.pins.add(new OutputPin(5, "1Y1"));
		this.pins.add(new OutputPin(6, "1Y2"));
		this.pins.add(new OutputPin(7, "1Y3"));
		this.pins.add(new OutputPin(12, "2Y0"));
		this.pins.add(new OutputPin(11, "2Y1"));
		this.pins.add(new OutputPin(10, "2Y2"));
		this.pins.add(new OutputPin(9, "2Y3"));
	}

	public Gen74139(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(4, "1Y0", tplh, tphl));
		this.pins.add(new OutputPin(5, "1Y1", tplh, tphl));
		this.pins.add(new OutputPin(6, "1Y2", tplh, tphl));
		this.pins.add(new OutputPin(7, "1Y3", tplh, tphl));
		this.pins.add(new OutputPin(12, "2Y0", tplh, tphl));
		this.pins.add(new OutputPin(11, "2Y1", tplh, tphl));
		this.pins.add(new OutputPin(10, "2Y2", tplh, tphl));
		this.pins.add(new OutputPin(9, "2Y3", tplh, tphl));
	}

	private void initialise() {
		this.description = "3-Line to 8-Line Decoder / Demultiplexers";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74139.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1nG"));
		this.pins.add(new InputPin(2, "1A"));
		this.pins.add(new InputPin(3, "1B"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(15, "2nG"));
		this.pins.add(new InputPin(14, "2A"));
		this.pins.add(new InputPin(13, "2B"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String A, String B, String G, String Y0, String Y1, String Y2, String Y3) throws InvalidPinException {
		if (this.isHigh(G)) {
			this.resetGate(Y0, Y1, Y2, Y3);
		} else {
			int count = 0;
			if (this.isHigh(B)) {
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
					break;
				case 1:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.LOW);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.HIGH);
					break;
				case 2:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.LOW);
					this.setPin(Y3, Pin.PinState.HIGH);
					break;
				case 3:
					this.setPin(Y0, Pin.PinState.HIGH);
					this.setPin(Y1, Pin.PinState.HIGH);
					this.setPin(Y2, Pin.PinState.HIGH);
					this.setPin(Y3, Pin.PinState.LOW);
					break;
				default:
					this.resetGate(Y0, Y1, Y2, Y3);
			}
		}
	}

	private void resetGate(String Y0, String Y1, String Y2, String Y3) throws InvalidPinException {
		this.setPin(Y0, Pin.PinState.HIGH);
		this.setPin(Y1, Pin.PinState.HIGH);
		this.setPin(Y2, Pin.PinState.HIGH);
		this.setPin(Y3, Pin.PinState.HIGH);
	}

	@Override
	public void reset() {
		try {
			this.resetGate("1Y0", "1Y1", "1Y2", "1Y3");
			this.resetGate("2Y0", "2Y1", "2Y2", "2Y3");
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
		}
	}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("1A", "1B", "1nG", "1Y0", "1Y1", "1Y2", "1Y3");
				this.updateGate("2A", "2B", "2nG", "2Y0", "2Y1", "2Y2", "2Y3");
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
 * \classes\ Qualified Name: integratedCircuits.ttl.decoder.Gen74139 JD-Core
 * Version: 0.6.2
 */
