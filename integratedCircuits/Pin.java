package integratedCircuits;

public abstract class Pin {
	protected int pinNumber;
	protected String pinName;
	protected PinState pinState = PinState.LOW;

	protected Pin(int pinNumber, String pinName) {
		this.pinNumber = pinNumber;
		this.pinName = pinName;
	}

	public int getPinNumber() {
		return this.pinNumber;
	}

	public String getPinName() {
		return this.pinName;
	}

	public PinState getPinState() {
		return this.pinState;
	}

	public void setPinState(PinState state) {
		this.pinState = state;
	}

	public static enum PinDriver {
		OPEN_COLLECTOR, TOTEM_POLE, TRI_STATE;
	}

	public static enum PinState {
		HIGH, LOW, HIGH_IMPEDANCE, NOT_CONNECTED, UNKNOWN;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.Pin JD-Core Version: 0.6.2
 */
