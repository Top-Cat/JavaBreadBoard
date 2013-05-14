package jBreadBoard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class ChipAccess {
	private Chip chip;

	public ChipAccess(Chip c) {
		this.chip = c;
	}

	public String save(String filestring) {
		return FileDialogs.save(this.chip, filestring);
	}

	public String load() {
		return FileDialogs.load(this.chip);
	}

	public long getSimTime() {
		return this.chip.getCircuit().getSimTime();
	}

	public String readTTL(int pin) {
		return this.chip.getConnections()[pin].getPotential().toTTL();
	}

	public void write(int pin, String value, int delay) {
		if (value == null || value != null && !value.equals("HIGH") && !value.equals("LOW") && !value.equals("NC")) {
			JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to use an invalid output value: " + value, "Error ChipAccess.001 in Chip Model", 0);

			this.chip.getCircuit().pause();
			this.chip.getCircuit().resetSimulation();
		} else if (delay < 0) {
			JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to use a negative delay: " + delay, "Error ChipAccess.002 in Chip Model", 0);

			this.chip.getCircuit().pause();
			this.chip.getCircuit().resetSimulation();
		} else if (pin < 0 || pin >= this.chip.getNumberOfPins() * 2) {
			JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to write to a pin that did not exist " + pin, "Error ChipAccess.003 in Chip Model", 0);

			this.chip.getCircuit().pause();
			this.chip.getCircuit().resetSimulation();
		} else if (this.chip.getPinType(pin).equals("IN") || this.chip.getPinType(pin).equals("NC")) {
			JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to write to pin " + pin + " which is declared type " + this.chip.getPinType(pin) + ".", "Error ChipAccess.004 in Chip Model", 0);

			this.chip.getCircuit().pause();
			this.chip.getCircuit().resetSimulation();
		} else {
			this.chip.getCircuit().getSimulation().addEvent(this.chip.getConnections()[pin], delay, value);
		}
	}

	public String getFile(String filename) {
		try {
			File file = new File(filename);
			FileInputStream fr = new FileInputStream(file);
			InputStreamReader ir = new InputStreamReader(fr, "ISO-8859-1");
			BufferedReader in = new BufferedReader(ir);

			String input = in.readLine();
			String line;
			while ((line = in.readLine()) != null) {
				input = input + "\n" + line;
			}

			in.close();
			fr.close();

			return input;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return null;
	}

	public int getWidth() {
		return this.chip.getWidth();
	}

	public int getHeight() {
		return this.chip.getHeight();
	}

	public void repaint() {
		this.chip.repaint();
	}
}
