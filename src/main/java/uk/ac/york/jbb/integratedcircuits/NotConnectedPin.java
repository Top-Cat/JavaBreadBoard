package uk.ac.york.jbb.integratedcircuits;

public class NotConnectedPin extends Pin {
	public NotConnectedPin(int pinNumber, String pinName) {
		super(pinNumber, pinName);
	}

	public NotConnectedPin(int pinNumber) {
		this(pinNumber, "NC");
	}
}
