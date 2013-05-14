package integratedCircuits.memory;

import integratedCircuits.InputOutputPin;
import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.NotConnectedPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;
import integratedCircuits.ttl.generic.InvalidStateException;
import jBreadBoard.v1_00.ChipModel;
import jBreadBoard.v1_10.DblClick;
import jBreadBoard.v1_10.LoadSave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Memory extends IntegratedCircuit implements ChipModel, DblClick, LoadSave {
	protected int cntlBits = 0;
	protected int addrBits = 8;
	protected int dataBits = 8;
	protected MemSpace memory;
	protected DataEntryFrame frame;
	private String filename;
	private String chipExtension = "mem";
	private boolean chipInstantiateBlank = true;

	protected void initialise() {
		this.memory = new MemSpace((int) Math.pow(2.0D, this.addrBits));

		for (int i = 0; i < this.addrBits; i++) {
			String name = "A" + i;
			this.pins.add(new InputPin(this.cntlBits + 1 + i, name));
		}

		if (this.dataBits - (this.addrBits + this.cntlBits) == 0) {
			this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1, "GND"));
			this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
		} else if (this.dataBits - (this.addrBits + this.cntlBits) > 0) {
			for (int i = 0; i < this.dataBits - (this.addrBits + this.cntlBits); i++) {
				this.pins.add(new NotConnectedPin(i + this.addrBits + this.cntlBits + 1, "NC"));
			}

			for (int i = 0; i < this.dataBits; i++) {
				String name = "D" + i;
				this.pins.add(new InputOutputPin(this.addrBits + this.dataBits - (this.addrBits + this.cntlBits) + this.cntlBits + 2 + i, name));
			}

			this.pins.add(new PowerPin(this.addrBits + this.dataBits - (this.addrBits + this.cntlBits) + this.cntlBits + 1, "GND"));
			this.pins.add(new PowerPin(this.addrBits + this.dataBits - (this.addrBits + this.cntlBits) + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
		} else {
			for (int i = 0; i < this.dataBits; i++) {
				String name = "D" + i;
				this.pins.add(new InputOutputPin(this.addrBits + this.cntlBits + 2 + i, name));
			}

			for (int i = 0; i < this.addrBits + this.cntlBits - this.dataBits; i++) {
				this.pins.add(new NotConnectedPin(i + this.addrBits + this.dataBits + this.cntlBits + 2, "NC"));
			}

			this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1, "GND"));
			this.pins.add(new PowerPin(this.addrBits + this.addrBits + this.cntlBits - this.dataBits + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
		}
	}

	@Override
	public void initialiseState(String filename) throws InvalidStateException {
		this.filename = filename;

		if (this.filename.isEmpty() || this.filename.equalsIgnoreCase("null")) {
			System.out.println("NULL File Name");
		} else {
			String newFilename = this.updateConfigFilePath(this.filename);
			try {
				System.out.println("Open File " + newFilename);
				FileInputStream istream = new FileInputStream(newFilename);
				Scanner fileScanner = new Scanner(istream);

				while (fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();

					Scanner lineScanner = new Scanner(line);
					try {
						if (lineScanner.hasNext(Pattern.compile("^[0123456789].*"))) {
							lineScanner.useDelimiter(",");
							int count = 0;
							int data = 0;
							while (lineScanner.hasNext()) {
								String value = lineScanner.next();
								if (value != null) {
									data = Integer.valueOf(value).intValue();
									this.memory.setDatum(count, data);
									count++;
								}
							}
						}
					} catch (NoSuchElementException e1) {
					}
				}
			} catch (FileNotFoundException e1) {
				System.out.print(e1);
				throw new InvalidStateException("No File found exit");
			}
		}
	}

	protected abstract void updateGate() throws InvalidPinException;

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate();
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
	public void setFileName(String file) {
		this.filename = file;
	}

	@Override
	public String getFileName() {
		return '"' + this.filename + '"';
	}

	@Override
	public String getFileExtension() {
		return this.chipExtension;
	}

	@Override
	public boolean instantiateBlank() {
		return this.chipInstantiateBlank;
	}

	@Override
	public void DblClicked() {
		this.frame = new DataEntryFrame(this.memory, this);
		this.frame.setVisible(true);
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.memory.Memory JD-Core Version:
 * 0.6.2
 */
