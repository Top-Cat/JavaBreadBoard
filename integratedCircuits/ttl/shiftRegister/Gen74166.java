package integratedCircuits.ttl.shiftRegister;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74166 extends IntegratedCircuit {
	protected Pin.PinState a = Pin.PinState.LOW;
	protected Pin.PinState b = Pin.PinState.LOW;
	protected Pin.PinState c = Pin.PinState.LOW;
	protected Pin.PinState d = Pin.PinState.LOW;
	protected Pin.PinState e = Pin.PinState.LOW;
	protected Pin.PinState f = Pin.PinState.LOW;
	protected Pin.PinState g = Pin.PinState.LOW;

	public Gen74166() {
		this.initialise();

		this.pins.add(new OutputPin(13, "QH"));
	}

	public Gen74166(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(13, "QH", tplh, tphl));
	}

	private void initialise() {
		this.description = "Parallel-load 8-Bit Shift Register";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74166.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "SER"));
		this.pins.add(new InputPin(2, "A"));
		this.pins.add(new InputPin(3, "B"));
		this.pins.add(new InputPin(4, "C"));
		this.pins.add(new InputPin(5, "D"));
		this.pins.add(new InputPin(6, "CLKINH"));
		this.pins.add(new InputPin(7, "CLK"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(9, "CLR"));
		this.pins.add(new InputPin(10, "E"));
		this.pins.add(new InputPin(11, "F"));
		this.pins.add(new InputPin(12, "G"));
		this.pins.add(new InputPin(14, "H"));
		this.pins.add(new InputPin(15, "SH/nLD"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String CLK, String CLR, String SHLD, String CLKINH, String SER, String A, String B, String C, String D, String E, String F, String G, String H, String QH) throws InvalidPinException {
		if (this.isLow(CLR)) {
			this.a = Pin.PinState.LOW;
			this.b = Pin.PinState.LOW;
			this.c = Pin.PinState.LOW;
			this.d = Pin.PinState.LOW;
			this.e = Pin.PinState.LOW;
			this.f = Pin.PinState.LOW;
			this.g = Pin.PinState.LOW;
			this.setPin(QH, Pin.PinState.LOW);
		} else if (this.isLow(SHLD)) {
			this.a = this.getPinState(A);
			this.b = this.getPinState(B);
			this.c = this.getPinState(C);
			this.d = this.getPinState(D);
			this.e = this.getPinState(E);
			this.f = this.getPinState(F);
			this.g = this.getPinState(G);

			if (this.isHigh(H)) {
				this.setPin(QH, Pin.PinState.HIGH);
			} else {
				this.setPin(QH, Pin.PinState.LOW);
			}
		} else if (this.isRisingEdge(CLK) && this.isLow(CLKINH)) {
			if (this.g.equals(Pin.PinState.HIGH)) {
				this.setPin(QH, Pin.PinState.HIGH);
			} else {
				this.setPin(QH, Pin.PinState.LOW);
			}
			this.g = this.f;
			this.f = this.e;
			this.e = this.d;
			this.d = this.c;
			this.c = this.b;
			this.b = this.a;
			this.a = this.getPinState(SER);
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
				this.updateGate("CLK", "CLR", "SH/nLD", "CLKINH", "SER", "A", "B", "C", "D", "E", "F", "G", "H", "QH");
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
 * \classes\ Qualified Name: integratedCircuits.ttl.shiftRegister.Gen74166
 * JD-Core Version: 0.6.2
 */
