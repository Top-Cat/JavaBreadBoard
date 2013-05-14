package integratedCircuits;

public class InputOutputPin extends OutputPin {
	protected int tplh = 1;
	protected int tphl = 1;
	protected Pin.PinDriver pinDriver;

	public InputOutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelayTplh, int pinDelayTphl) {
		super(pinNumber, pinName);

		if (this.pinDriver == Pin.PinDriver.TOTEM_POLE) {
			this.pinDriver = Pin.PinDriver.TRI_STATE;
		} else {
			this.pinDriver = pinDriver;
		}
		this.tplh = pinDelayTplh;
		this.tphl = pinDelayTphl;
	}

	public InputOutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelay) {
		this(pinNumber, pinName, pinDriver, pinDelay, pinDelay);
	}

	public InputOutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
		this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, pinDelayTplh, pinDelayTphl);
	}

	public InputOutputPin(int pinNumber, String pinName, int pinDelay) {
		this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, pinDelay, pinDelay);
	}

	public InputOutputPin(int pinNumber, String pinName) {
		this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, 5, 5);
	}

	@Override
	public Pin.PinDriver getPinDriver() {
		return this.pinDriver;
	}

	@Override
	public int getPinDelayTphl() {
		return this.tphl;
	}

	@Override
	public int getPinDelayTplh() {
		return this.tplh;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.InputOutputPin JD-Core Version:
 * 0.6.2
 */
