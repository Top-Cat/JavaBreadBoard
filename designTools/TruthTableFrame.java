package designTools;

import jBreadBoard.JBreadBoard;
import jBreadBoard.v1_00.ChipModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TruthTableFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JBreadBoard jBreadBoard;
	public static String defaultchip = "integratedCircuits.ttl.logic.Gen7400";
	JPanel contentPane;
	private static final int OUT_MAX = 16;
	private static final int IN_MAX = 8;
	private int inPinNumber;
	private int outPinNumber;
	private boolean inApplet = false;

	StandardMenu jMenuBar1 = new StandardMenu();
	JMenuBar menuBar = this.jMenuBar1.getMenuBar();
	JPanel jPanel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	JScrollPane tableFrame = new JScrollPane();
	ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
	StateTable truthTable;
	JPanel jPanel2 = new JPanel();
	JLabel lblStatus = new JLabel();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();

	public TruthTableFrame() {
		try {
			this.jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public TruthTableFrame(boolean isApplet) {
		this.inApplet = isApplet;
		try {
			this.jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.contentPane = (JPanel) this.getContentPane();
		this.contentPane.setLayout(this.gridBagLayout2);

		this.setDefaultCloseOperation(0);
		this.setJMenuBar(this.menuBar);

		this.setSize(new Dimension(759, 553));
		this.setTitle("Truth Table Editor");

		JComboBox<String> inPinBox = new JComboBox<String>();
		inPinBox.addItem("0");
		JComboBox<String> outPinBox = new JComboBox<String>();
		for (int i = 0; i < 16; i++) {
			outPinBox.addItem(String.valueOf(i + 1));
		}
		for (int i = 0; i < 8; i++) {
			inPinBox.addItem(String.valueOf(i + 1));
		}

		JOptionPane.showOptionDialog(this, "Please state the number of input pins and output pins required\n ", "Truth Table Editor", 2, 3, null, new Object[] { new JLabel("Input Pins"), inPinBox, new JLabel("OutputPins"), outPinBox, "Continue" }, "1");

		this.inPinNumber = Integer.parseInt((String) inPinBox.getSelectedItem());
		this.outPinNumber = Integer.parseInt((String) outPinBox.getSelectedItem());

		this.truthTable = new TruthTable(this.inPinNumber, this.outPinNumber);

		this.jPanel1.setBorder(null);
		this.jPanel1.setLayout(this.borderLayout2);
		this.tableFrame.setBorder(BorderFactory.createLoweredBevelBorder());
		this.propertiesPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.jPanel2.setBorder(BorderFactory.createLoweredBevelBorder());
		this.jPanel2.setLayout(this.gridBagLayout1);
		this.lblStatus.setText("Click on output pin cells to toggle values. Double-click column headers to change pin labels.");

		this.contentPane.add(this.propertiesPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 6, 0, 11), 741, 99));

		this.contentPane.add(this.jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 6, 0, 11), 288, -17));

		this.jPanel1.add(this.tableFrame, "Center");
		this.contentPane.add(this.jPanel2, new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 6, 14, 11), 0, 0));

		this.jPanel2.add(this.lblStatus, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 1, 0), 156, 0));

		this.tableFrame.getViewport().add(this.truthTable, null);

		this.jMenuBar1.getSaveMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				TruthTableFrame.this.createChipFile();
			}
		});
		this.jMenuBar1.getExitMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				TruthTableFrame.this.exitAction();
			}
		});
		this.jMenuBar1.getNewMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this table?\nAll unsaved work will be lost", "New Table", 0, 1) == 0) {
						TruthTableFrame.this.closeWindow();
						TruthTableFrame newFrame = new TruthTableFrame(TruthTableFrame.this.inApplet);

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

	public static ChipModel getDefaultModel() {
		ChipModel model = JBreadBoard.getChipModel(TruthTableFrame.defaultchip);
		return model;
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
			writer.println("Clocked Chip=False");
			for (int i = 0; i < this.inPinNumber; i++) {
				inputPinList = inputPinList + this.truthTable.getColumnName(i) + ";";
			}
			writer.println("Input Pins=" + inputPinList);
			for (int i = 0; i < this.outPinNumber; i++) {
				outputPinList = outputPinList + this.truthTable.getColumnName(i + this.inPinNumber) + ";";
			}
			writer.println("Output Pins=" + outputPinList);
			writer.println("Number of States=1");
			writer.println("Initial State=0");
			writer.println("++++++++++");

			for (int i = 0; i < this.truthTable.getRowCount(); i++) {
				String tableRow = "";
				for (int j = 0; j < this.inPinNumber; j++) {
					tableRow = tableRow + this.truthTable.getValueAt(i, j);
				}
				tableRow = tableRow + ";0;";
				for (int j = 0; j < this.outPinNumber; j++) {
					tableRow = tableRow + this.truthTable.getValueAt(i, j + this.inPinNumber);
				}
				tableRow = tableRow + ";0;";
				Number delayN = (Number) this.propertiesPanel.txtDelay.getValue();
				long delay = delayN.longValue();
				if (delay < 0L) {
					delay = 0L;
				}
				tableRow = tableRow + String.valueOf(delay);
				writer.println(tableRow);
			}

			writer.close();
		}

		if (!errorOccured) {
			if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the truth table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1) {
				this.immediateExit();
			}
		}
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
 * \classes\ Qualified Name: designTools.TruthTableFrame JD-Core Version: 0.6.2
 */
