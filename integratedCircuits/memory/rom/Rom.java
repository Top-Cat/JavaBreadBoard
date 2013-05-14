package integratedCircuits.memory.rom;

import integratedCircuits.InputPin;
import integratedCircuits.InvalidPinException;
import integratedCircuits.Pin;
import integratedCircuits.memory.Memory;

import java.io.File;

public class Rom extends Memory {
	public Rom() {
		this.setup();
	}

	public Rom(int addrBits) {
		this.addrBits = addrBits;
		this.setup();
	}

	private void setup() {
		System.out.println("ROM : setup");

		this.description = "Programmable Memory";
		this.manufacturer = "Generic ROM 256x8";
		this.diagram = "images" + File.separator + "rom.gif";
		this.wide = true;

		this.pins.add(new InputPin(1, "CS"));
		this.pins.add(new InputPin(2, "OE"));

		this.cntlBits = 2;
		this.initialise();
	}

	@Override
	protected void updateGate() throws InvalidPinException {
		int addr = 0;
		boolean addrValid = true;

		for (int i = 0; i < this.addrBits; i++) {
			String name = "A" + i;
			if (this.isHigh(name)) {
				addr += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
				addrValid = false;
			}

		}

		if (this.isHigh("CS")) {
			if (this.isHigh("OE")) {
				if (addrValid) {
					int memoryLocationData = this.memory.getDatum(addr);
					for (int i = 0; i < this.dataBits; i++) {
						String name = "D" + i;
						if ((memoryLocationData & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					for (int i = 0; i < this.dataBits; i++) {
						String name = "D" + i;
						this.setPin(name, Pin.PinState.LOW);
					}
				}
			} else {
				for (int i = 0; i < this.dataBits; i++) {
					String name = "D" + i;
					this.setPin(name, Pin.PinState.HIGH_IMPEDANCE);
				}
			}
		}
	}
}
