package integratedCircuits.memory;

import integratedCircuits.InvalidPinException;
import integratedCircuits.ttl.generic.InvalidStateException;
import jBreadBoard.HexMath;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import designTools.FileOperations;

public class DataEntryFrame extends JFrame implements ListSelectionListener, ActionListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> addList;
	private MemSpace data;
	private Memory access;
	private ByteTextField[] TextFields = new ByteTextField[256];
	private int AddBase = 0;
	private String type = "Hex";
	private JPanel frontPanel = new JPanel();

	private String defaultDirectory = "configFiles";

	public DataEntryFrame(MemSpace mem, Memory a) {
		this.data = mem;
		this.access = a;

		this.setTitle("Data Edit");
		this.setDefaultCloseOperation(2);
		this.getContentPane().add(this.frontPanel);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		this.setLayout(gridbag);

		JPanel radioPanel = this.TwoRadioButtonPanel();
		JPanel panel = this.ByteFieldsPanel(this.data.getLength());
		JPanel listScroller = this.AddressListScroller();
		JPanel loadsavePanel = this.LoadSaveButtons();

		c.gridwidth = 0;
		gridbag.setConstraints(panel, c);
		this.add(panel);

		c.gridwidth = 1;
		c.weightx = 1.0D;
		gridbag.setConstraints(radioPanel, c);
		this.add(radioPanel);

		gridbag.setConstraints(listScroller, c);
		this.add(listScroller);

		c.gridwidth = 0;

		gridbag.setConstraints(loadsavePanel, c);
		this.add(loadsavePanel);

		this.updateTextFields();

		this.pack();
	}

	private JPanel LoadSaveButtons() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(2, 1));

		JButton load = new JButton("Load");
		load.setActionCommand("Load");
		load.addActionListener(this);
		panel.add(load);

		JButton save = new JButton("Save");
		save.setActionCommand("Save");
		save.addActionListener(this);
		panel.add(save);

		return panel;
	}

	private JPanel AddressListScroller() {
		JPanel panel = new JPanel();

		int y = this.data.getLength() / 256;
		String[] addresses = new String[y];

		for (int i = 0; i < y; i++) {
			addresses[i] = Integer.toHexString(i * 256);
		}
		for (int i = 0; i < y; i++) {
			while (addresses[i].length() < addresses[y - 1].length()) {
				addresses[i] = "0".concat(addresses[i]);
			}
		}
		this.addList = new JList<String>(addresses);
		this.addList.setSelectedIndex(0);
		this.addList.setSelectionMode(0);
		this.addList.addListSelectionListener(this);
		JScrollPane listScroller = new JScrollPane(this.addList);

		listScroller.setPreferredSize(new Dimension(120, 80));

		panel.add(listScroller);
		return panel;
	}

	private JPanel ByteFieldsPanel(int x) {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(16, 17));
		for (int i = 0; i < 16; i++) {
			JTextField temp = new JTextField(3);
			temp.setText(Integer.toHexString(i * 16) + "h");
			temp.setEditable(false);
			panel.add(temp);
			for (int j = 0; j < 16; j++) {
				this.TextFields[i * 16 + j] = new ByteTextField(3);
				panel.add(this.TextFields[i * 16 + j]);
			}
		}

		for (int i = 0; i < 256; i++) {
			this.TextFields[i].number = i;
			this.TextFields[i].addActionListener(this);
			this.TextFields[i].addFocusListener(this);
			this.TextFields[i].setActionCommand("textfields");
		}

		return panel;
	}

	private JPanel TwoRadioButtonPanel() {
		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();

		JRadioButton HexButton = new JRadioButton("Hex");
		HexButton.setActionCommand("Hex");
		HexButton.addActionListener(this);
		HexButton.setSelected(true);
		group.add(HexButton);

		JRadioButton DecimalButton = new JRadioButton("Decimal");
		DecimalButton.setActionCommand("Decimal");
		DecimalButton.addActionListener(this);
		group.add(DecimalButton);

		panel.setLayout(new GridLayout(2, 1));

		panel.add(HexButton);
		panel.add(DecimalButton);
		return panel;
	}

	private void updateTextFields() {
		for (int i = 0; i < 256; i++) {
			if (this.type == "Hex") {
				this.TextFields[i].setText(Integer.toHexString(this.data.getDatum(i + this.AddBase)));
			} else {
				this.TextFields[i].setText(Integer.toString(this.data.getDatum(i + this.AddBase)));
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			this.AddBase = HexMath.HexStringtoInt((String) this.addList.getSelectedValue());
			this.updateTextFields();
		}
	}

	@Override
	public void focusGained(FocusEvent event) {}

	@Override
	public void focusLost(FocusEvent event) {
		ByteTextField field = (ByteTextField) event.getSource();
		int i = field.number;
		if (this.type == "Hex") {
			this.data.setDatum(this.AddBase + i, Integer.decode("0x" + field.getText()).intValue());
		} else if (this.type == "Decimal") {
			this.data.setDatum(this.AddBase + i, Integer.decode(field.getText()).intValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == "textfields") {
			ByteTextField field = (ByteTextField) evt.getSource();
			int i = field.number;
			if (this.type == "Hex") {
				this.data.setDatum(this.AddBase + i, Integer.decode("0x" + field.getText()).intValue());
			} else if (this.type == "Decimal") {
				this.data.setDatum(this.AddBase + i, Integer.decode(field.getText()).intValue());
			}
		} else if (evt.getActionCommand() == "Hex") {
			this.type = "Hex";
			for (int i = 0; i < 256; i++) {
				this.TextFields[i].setHex();
			}
			this.updateTextFields();
		} else if (evt.getActionCommand() == "Decimal") {
			this.type = "Decimal";
			for (int i = 0; i < 256; i++) {
				this.TextFields[i].setDecimal();
			}
			this.updateTextFields();
		} else if (evt.getActionCommand() == "Save") {
			this.saveRamFile();
		} else if (evt.getActionCommand() == "Load") {
			this.loadRamFile();
		} else {
			System.err.println("chiplib.DataEntryPanel: Action Command not recognized");
		}
	}

	private void saveRamFile() {
		boolean errorOccured = false;
		PrintWriter writer = null;

		File ramFile = FileOperations.getFileSelection("mem", "SAVE");

		if (ramFile == null) {
			errorOccured = true;
		}

		if (!errorOccured) {
			try {
				writer = new PrintWriter(new FileWriter(ramFile));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error: Could not open file " + ramFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);

				errorOccured = true;
			}

		}

		if (!errorOccured) {
			writer.println("Chip Text=" + this.access.getChipText());
			writer.println("Description=" + this.access.getDescription());
			writer.println("Manufacturer=" + this.access.getManufacturer());
			if (this.access.isWide()) {
				writer.println("Wide Chip=True");
			} else {
				writer.println("Wide Chip=False");
			}
			writer.println("Clocked Chip=False");

			int dataPins = 0;
			int addrPins = 0;

			for (int i = 0; i < this.access.getNumberOfPackagePins(); i++) {
				try {
					if (this.access.isPinOffsetInput(i)) {
						addrPins++;
					}
					if (this.access.isPinOffsetInputOutput(i)) {
						dataPins++;
					}
				} catch (InvalidPinException e) {
				}
			}
			addrPins -= 3;

			writer.println("Addr Pins=" + addrPins);
			writer.println("Data Pins=" + dataPins);
			writer.println("++++++++++");
			writer.println(this.data.getAll());
		}
		writer.close();

		String fileName = ramFile.getAbsolutePath();
		fileName = fileName.replace('\\', '/');
		this.access.setFileName(fileName);

		if (!errorOccured) {
			if (JOptionPane.showOptionDialog(this, "Ram file created\nDo you wish to exit the truth table editor or continue?", "Ram File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1) {
				this.setVisible(false);
			}
		}
	}

	private void loadRamFile() {
		boolean errorOccured = false;

		String extension = this.access.getFileExtension();
		File ramFile = FileOperations.getFileSelection(extension, "LOAD");

		if (ramFile == null) {
			errorOccured = true;
		}

		if (!errorOccured) {
			try {
				this.access.initialiseState(ramFile.getAbsolutePath());
				this.updateTextFields();
			} catch (InvalidStateException e) {
				System.out.println("Table Error");
			}
		} else {
			System.out.println("File Error");
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.memory.DataEntryFrame JD-Core
 * Version: 0.6.2
 */
