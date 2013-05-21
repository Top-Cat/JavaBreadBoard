package uk.ac.york.jbb.integratedcircuits.components;


import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;
import uk.ac.york.jbb.jbreadboard.v1_10.Graphical;

public class HexDisplay extends IntegratedCircuit implements Graphical {
	private int val = 8;

	public HexDisplay() {
		this.initialise();
	}

	private void initialise() {
		this.description = "Seven segment LED display";
		this.manufacturer = "Generic display component";
		this.diagram = "images" + File.separator + "7Seg.gif";
		this.wide = true;

		this.pins.add(new InputPin(1, "IN0"));
		this.pins.add(new InputPin(2, "IN1"));
		this.pins.add(new PowerPin(3, "GND"));
		this.pins.add(new InputPin(4, "IN2"));
		this.pins.add(new InputPin(5, "IN3"));
		this.pins.add(new PowerPin(6, "VCC"));
	}

	private void updateDisplay(String A, String B, String C, String D) throws InvalidPinException {
		this.val = 0;
		if (this.isHigh(A)) {
			this.val += 1;
		}
		if (this.isHigh(B)) {
			this.val += 2;
		}
		if (this.isHigh(C)) {
			this.val += 4;
		}
		if (this.isHigh(D)) {
			this.val += 8;
		}
		this.chip.repaint();
	}

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateDisplay("IN0", "IN1", "IN2", "IN3");
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
	public void paintComponent(Graphics g, boolean selected) {
		int[][] led = { { 1, 1, 1, 0, 1, 1, 1 }, { 0, 0, 1, 0, 0, 1, 0 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 0, 1, 1 }, { 0, 1, 1, 1, 0, 1, 0 }, { 1, 1, 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 1, 0, 0, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 0, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 0, 1 }, { 0, 0, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 0, 1 }, { 1, 1, 0, 1, 1, 0, 0 } };

		Color chipcolor = Color.black;
		Color pincolor = Color.lightGray;
		Color ledcolor = Color.red;

		g.setColor(chipcolor);
		g.fillRect(0, 4, this.chip.getWidth() - 1, this.chip.getHeight() - 9);

		g.setColor(ledcolor);

		if (led[this.val][0] == 1) {
			g.fillRect(2, 8, this.chip.getWidth() - 4, 4);
		}
		if (led[this.val][3] == 1) {
			g.fillRect(2, (this.chip.getHeight() - 8) / 2, this.chip.getWidth() - 4, 4);
		}
		if (led[this.val][6] == 1) {
			g.fillRect(2, this.chip.getHeight() - 16, this.chip.getWidth() - 4, 4);
		}

		if (led[this.val][1] == 1) {
			g.fillRect(2, 8, 4, this.chip.getHeight() / 2 - 8);
		}
		if (led[this.val][4] == 1) {
			g.fillRect(2, (this.chip.getHeight() - 8) / 2, 4, this.chip.getHeight() / 2 - 8);
		}
		if (led[this.val][2] == 1) {
			g.fillRect(this.chip.getWidth() - 6, 8, 4, this.chip.getHeight() / 2 - 8);
		}
		if (led[this.val][5] == 1) {
			g.fillRect(this.chip.getWidth() - 6, (this.chip.getHeight() - 8) / 2, 4, this.chip.getHeight() / 2 - 8);
		}

		g.setColor(pincolor);
		for (int i = 0; i < this.getNumberOfPins(); i++) {
			g.fillRect(i * 8 + 1, 2, 5, 2);
			g.fillRect(i * 8 + 1, this.chip.getHeight() - 5, 5, 2);
		}
	}
}
