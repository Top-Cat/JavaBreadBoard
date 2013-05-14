package designTools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StateTableFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	private static final int OUT_STATE_MAX = 16;
	private static final int IN_MAX = 8;
	private boolean inApplet = false;

	StandardMenu jMenuBar1 = new StandardMenu();
	JMenuBar menuBar = this.jMenuBar1.getMenuBar();
	JScrollPane tableFrame = new JScrollPane();
	StateTable stateTable;
	ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
	JPanel statusBar = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JLabel lblStatusBar = new JLabel();
	JPanel jPanel1 = new JPanel();
	JLabel lblStarting = new JLabel();
	JLabel lblStarting2 = new JLabel();
	JLabel lblStarting3 = new JLabel();
	JLabel lblStarting4 = new JLabel();
	JLabel lblStarting5 = new JLabel();
	JList<String> lstStarting = new JList<String>();
	JScrollPane scrStarting = new JScrollPane();
	private int inPinNumber;
	private int outPinNumber;
	private int stateNumber;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();

	public StateTableFrame() {
		this.enableEvents(64L);
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StateTableFrame(boolean isApplet) {
		this.inApplet = isApplet;
		try {
			this.jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.contentPane = (JPanel) this.getContentPane();
		this.contentPane.setLayout(this.gridBagLayout2);

		this.setDefaultCloseOperation(0);
		this.setJMenuBar(this.menuBar);

		this.setSize(new Dimension(759, 548));
		this.setTitle("State Table Editor");

		JComboBox<String> inPinBox = new JComboBox<String>();
		inPinBox.addItem("0");
		JComboBox<String> outPinBox = new JComboBox<String>();
		JComboBox<String> stateBox = new JComboBox<String>();
		for (int i = 0; i < 16; i++) {
			if (i < 8) {
				inPinBox.addItem(String.valueOf(i + 1));
			}
			outPinBox.addItem(String.valueOf(i + 1));
			stateBox.addItem(String.valueOf(i + 1));
		}

		JOptionPane.showOptionDialog(this, "Please state the number of input pins, output pins\nand states required\n ", "State Table Editor", 2, 3, null, new Object[] { new JLabel("Input Pins"), inPinBox, new JLabel("OutputPins"), outPinBox, new JLabel("States"), stateBox, "Continue" }, "1");

		this.inPinNumber = Integer.parseInt((String) inPinBox.getSelectedItem());
		this.outPinNumber = Integer.parseInt((String) outPinBox.getSelectedItem());
		this.stateNumber = Integer.parseInt((String) stateBox.getSelectedItem());

		this.stateTable = new StateTable(this.inPinNumber, this.outPinNumber, this.stateNumber);

		String[] states = new String[this.stateNumber];
		for (int i = 0; i < this.stateNumber; i++) {
			states[i] = Integer.toString(i);
		}
		this.lstStarting.setListData(states);
		this.lstStarting.setBorder(null);
		this.lstStarting.setSelectedIndex(0);
		this.lstStarting.setSelectionMode(0);

		this.tableFrame.setBorder(BorderFactory.createLoweredBevelBorder());
		this.statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		this.statusBar.setLayout(this.borderLayout1);
		this.lblStatusBar.setText("Click on output pin cells to toggle values. Double-click column headers to change pin labels.");

		this.jPanel1.setBorder(BorderFactory.createLoweredBevelBorder());
		this.jPanel1.setLayout(this.gridBagLayout1);
		this.lblStarting.setHorizontalAlignment(0);
		this.lblStarting.setText("Starting State");
		this.lblStarting2.setFont(new Font("Dialog", 0, 12));
		this.lblStarting2.setText("Please select the");
		this.lblStarting3.setText("initial state in");
		this.lblStarting3.setFont(new Font("Dialog", 0, 12));
		this.lblStarting4.setText("which the chip will");
		this.lblStarting4.setFont(new Font("Dialog", 0, 12));
		this.lblStarting4.setToolTipText("");
		this.lblStarting5.setText("start on power-up");
		this.lblStarting5.setFont(new Font("Dialog", 0, 12));
		this.scrStarting.setBorder(BorderFactory.createLoweredBevelBorder());
		this.contentPane.add(this.tableFrame, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(8, 5, 0, 0), 563, 371));

		this.tableFrame.getViewport().add(this.stateTable, null);
		this.contentPane.add(this.propertiesPanel, new GridBagConstraints(0, 1, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(6, 5, 0, 12), 741, 99));

		this.contentPane.add(this.statusBar, new GridBagConstraints(0, 2, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 5, 11, 12), 156, 2));

		this.statusBar.add(this.lblStatusBar, "Center");
		this.contentPane.add(this.jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0D, 1.0D, 10, 1, new Insets(8, 9, 0, 12), 3, 7));

		this.jPanel1.add(this.lblStarting3, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 25), 20, 0));

		this.jPanel1.add(this.lblStarting5, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 9), 6, 0));

		this.jPanel1.add(this.lblStarting2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(23, 11, 0, 16), 9, 0));

		this.jPanel1.add(this.lblStarting4, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 9), 4, 0));

		this.jPanel1.add(this.lblStarting, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(16, 11, 0, 9), 27, 0));

		this.jPanel1.add(this.scrStarting, new GridBagConstraints(0, 5, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(19, 11, 108, 15), -155, 18));

		this.scrStarting.getViewport().add(this.lstStarting, null);

		this.jMenuBar1.getSaveMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				StateTableFrame.this.createChipFile();
			}
		});
		this.jMenuBar1.getExitMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				StateTableFrame.this.exitAction();
			}
		});
		this.jMenuBar1.getNewMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this table?\nAll unsaved work will be lost", "New Table", 0, 1) == 0) {
						StateTableFrame.this.closeWindow();
						StateTableFrame newFrame = new StateTableFrame(StateTableFrame.this.inApplet);

						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						Dimension frameSize = newFrame.getSize();
						if (frameSize.height > screenSize.height) {
							frameSize.height = screenSize.height;
						}
						if (frameSize.width > screenSize.width) {
							frameSize.width = screenSize.width;
						}
						newFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
						newFrame.setVisible(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private void createChipFile() {
		boolean errorOccured = false;
		PrintWriter writer = null;
		String inputPinList = "";
		String outputPinList = "";

		File chipFile = FileOperations.getFileSelection("chp");
		if (chipFile == null) {
			errorOccured = true;
		}

		if (!errorOccured) {
			try {
				writer = new PrintWriter(new FileWriter(chipFile));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error: Could not open file " + chipFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);

				errorOccured = true;
			}

		}

		if (!errorOccured) {
			writer.println("Chip Text=" + this.propertiesPanel.txtChipText.getText());
			writer.println("Description=" + this.propertiesPanel.txtDescription.getText());
			writer.println("Manufacturer=" + this.propertiesPanel.txtManufacturer.getText());
			if (this.propertiesPanel.radYes.isSelected()) {
				writer.println("Wide Chip=True");
			} else {
				writer.println("Wide Chip=False");
			}
			writer.println("Clocked Chip=True");
			for (int i = 0; i < this.inPinNumber; i++) {
				inputPinList = inputPinList + this.stateTable.getColumnName(i) + ";";
			}
			writer.println("Input Pins=" + inputPinList);
			for (int i = 0; i < this.outPinNumber; i++) {
				outputPinList = outputPinList + this.stateTable.getColumnName(i + this.inPinNumber + 1) + ";";
			}
			writer.println("Output Pins=" + outputPinList);
			writer.println("Number of States=" + String.valueOf(this.stateNumber));
			writer.println("Initial State=" + this.lstStarting.getSelectedValue());
			writer.println("++++++++++");

			for (int i = 0; i < this.stateTable.getRowCount(); i++) {
				String stateTableRow = "";
				for (int j = 0; j < this.inPinNumber; j++) {
					stateTableRow = stateTableRow + this.stateTable.getValueAt(i, j);
				}
				stateTableRow = stateTableRow + ";" + this.stateTable.getValueAt(i, this.inPinNumber) + ";";
				for (int j = 0; j < this.outPinNumber; j++) {
					stateTableRow = stateTableRow + this.stateTable.getValueAt(i, j + this.inPinNumber + 1);
				}
				stateTableRow = stateTableRow + ";" + this.stateTable.getValueAt(i, this.stateTable.getColumnCount() - 1) + ";";
				Number delayN = (Number) this.propertiesPanel.txtDelay.getValue();
				long delay = delayN.longValue();
				if (delay < 0L) {
					delay = 0L;
				}
				stateTableRow = stateTableRow + String.valueOf(delay);
				writer.println(stateTableRow);
			}

			writer.close();
		}

		if (!errorOccured) {
			if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the state table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1) {
				this.immediateExit();
			}
		}
	}

	private void exitAction() {
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure that you want to close?\nAll unsaved work will be lost", "Exit Confirmation", 0, 1);

		if (confirm == 0) {
			this.immediateExit();
		}
	}

	private void immediateExit() {
		if (!this.inApplet) {
			System.exit(0);
		} else {
			this.closeWindow();
		}
	}

	private void closeWindow() {
		this.dispose();
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == 201) {
			this.exitAction();
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.StateTableFrame JD-Core Version: 0.6.2
 */
