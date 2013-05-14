package integratedCircuits.ttl.shiftRegister;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74164 extends IntegratedCircuit {
	public Gen74164() {
		this.initialise();

		this.pins.add(new OutputPin(3, "QA"));
		this.pins.add(new OutputPin(4, "QB"));
		this.pins.add(new OutputPin(5, "QC"));
		this.pins.add(new OutputPin(6, "QD"));
		this.pins.add(new OutputPin(10, "QE"));
		this.pins.add(new OutputPin(11, "QF"));
		this.pins.add(new OutputPin(12, "QG"));
		this.pins.add(new OutputPin(13, "QH"));
	}

	public Gen74164(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(3, "QA", tplh, tphl));
		this.pins.add(new OutputPin(4, "QB", tplh, tphl));
		this.pins.add(new OutputPin(5, "QC", tplh, tphl));
		this.pins.add(new OutputPin(6, "QD", tplh, tphl));
		this.pins.add(new OutputPin(10, "QE", tplh, tphl));
		this.pins.add(new OutputPin(11, "QF", tplh, tphl));
		this.pins.add(new OutputPin(12, "QG", tplh, tphl));
		this.pins.add(new OutputPin(13, "QH", tplh, tphl));
	}

	private void initialise() {
		this.description = "8-Bit Parallel-out Serial Shift Register";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74164.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "A"));
		this.pins.add(new InputPin(2, "B"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(8, "CLK"));
		this.pins.add(new InputPin(9, "nCLR"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String CLK, String CLR, String A, String B, String QA, String QB, String QC, String QD, String QE, String QF, String QG, String QH) throws InvalidPinException {
		if (this.isLow(CLR)) {
			this.setPin(QA, Pin.PinState.LOW);
			this.setPin(QB, Pin.PinState.LOW);
			this.setPin(QC, Pin.PinState.LOW);
			this.setPin(QD, Pin.PinState.LOW);
			this.setPin(QE, Pin.PinState.LOW);
			this.setPin(QF, Pin.PinState.LOW);
			this.setPin(QG, Pin.PinState.LOW);
			this.setPin(QH, Pin.PinState.LOW);
		} else if (this.isRisingEdge(CLK)) {
			this.setPin(QH, this.getPinState(QG));
			this.setPin(QG, this.getPinState(QF));
			this.setPin(QF, this.getPinState(QE));
			this.setPin(QE, this.getPinState(QD));
			this.setPin(QD, this.getPinState(QC));
			this.setPin(QC, this.getPinState(QB));
			this.setPin(QB, this.getPinState(QA));

			if (this.isHigh(A) && this.isHigh(B)) {
				this.setPin(QA, Pin.PinState.HIGH);
			} else {
				this.setPin(QA, Pin.PinState.LOW);
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
				this.updateGate("CLK", "nCLR", "A", "B", "QA", "QB", "QC", "QD", "QE", "QF", "QG", "QH");
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
