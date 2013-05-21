package uk.ac.york.jbb.integratedcircuits.components;


import java.io.File;

import javax.swing.JFrame;

import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;
import uk.ac.york.jbb.jbreadboard.v1_10.DblClick;

public class HexKeyPad extends IntegratedCircuit implements DblClick {
	private JFrame frame = new JFrame("KeyPad");
	private KeyPad panel = new KeyPad(this);

	public HexKeyPad() {
		this.initialise();
	}

	private void initialise() {
		this.description = "Hexidecimal keypad";
		this.manufacturer = "Generic input device";
		this.diagram = "images" + File.separator + "keyPad.gif";
		this.wide = false;

		this.pins.add(new OutputPin(1, "OUT0"));
		this.pins.add(new OutputPin(2, "OUT1"));
		this.pins.add(new PowerPin(3, "GND"));
		this.pins.add(new OutputPin(4, "OUT2"));
		this.pins.add(new OutputPin(5, "OUT3"));
		this.pins.add(new PowerPin(6, "VCC"));
	}

	private void updateGate(String A, String B, String C, String D) throws InvalidPinException {
		if ((this.panel.getValue() & 0x1) == 1) {
			this.setPin(A, Pin.PinState.HIGH);
		} else {
			this.setPin(A, Pin.PinState.LOW);
		}
		if ((this.panel.getValue() & 0x2) == 2) {
			this.setPin(B, Pin.PinState.HIGH);
		} else {
			this.setPin(B, Pin.PinState.LOW);
		}
		if ((this.panel.getValue() & 0x4) == 4) {
			this.setPin(C, Pin.PinState.HIGH);
		} else {
			this.setPin(C, Pin.PinState.LOW);
		}
		if ((this.panel.getValue() & 0x8) == 8) {
			this.setPin(D, Pin.PinState.HIGH);
		} else {
			this.setPin(D, Pin.PinState.LOW);
		}
	}

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate("OUT0", "OUT1", "OUT2", "OUT3");
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

	@Override
	public void DblClicked() {
		this.frame.setDefaultCloseOperation(2);
		this.frame.getContentPane().add(this.panel);
		this.frame.pack();
		this.frame.setVisible(true);
	}
}
