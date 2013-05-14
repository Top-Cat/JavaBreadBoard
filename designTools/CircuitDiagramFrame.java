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
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class CircuitDiagramFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean inApplet = false;

	JScrollPane scrWorkspace = new JScrollPane();
	ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
	JPanel pnlStatus = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JLabel lblStatus = new JLabel();
	Workspace workspace = new Workspace(this.scrWorkspace, this.lblStatus);
	CircuitEditorMenu standardMenuObject = new CircuitEditorMenu(this.workspace);
	JMenuBar menuBar = this.standardMenuObject.getMenuBar();

	JToolBar toolBar = new JToolBar();
	JPanel jPanel1 = new JPanel();
	BorderLayout borderLayout2 = new BorderLayout();
	JButton andButton = new JButton();
	JButton orButton = new JButton();
	JButton notButton = new JButton();
	JButton inButton = new JButton();
	JButton outButton = new JButton();
	JButton wireButton = new JButton();
	JButton labelButton = new JButton();
	JButton groundButton = new JButton();
	JButton vccButton = new JButton();
	JButton pointerButton = new JButton();
	GridBagLayout gridBagLayout1 = new GridBagLayout();

	public CircuitDiagramFrame(boolean isApplet) {
		this.inApplet = isApplet;
		try {
			this.jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.getContentPane().setLayout(this.gridBagLayout1);
		this.setSize(new Dimension(759, 599));
		this.setTitle("Circuit Diagram Editor");

		this.setDefaultCloseOperation(0);
		this.setJMenuBar(this.menuBar);
		this.scrWorkspace.setBorder(BorderFactory.createLoweredBevelBorder());
		this.propertiesPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.pnlStatus.setBorder(BorderFactory.createLoweredBevelBorder());
		this.pnlStatus.setLayout(this.borderLayout1);
		this.lblStatus.setText(" ");
		this.toolBar.setOrientation(0);
		this.jPanel1.setLayout(this.borderLayout2);
		this.andButton.addMouseListener(new CircuitDiagramFrame_andButton_mouseAdapter(this));
		this.orButton.addMouseListener(new CircuitDiagramFrame_orButton_mouseAdapter(this));
		this.notButton.addMouseListener(new CircuitDiagramFrame_notButton_mouseAdapter(this));
		this.inButton.addMouseListener(new CircuitDiagramFrame_inButton_mouseAdapter(this));
		this.outButton.addMouseListener(new CircuitDiagramFrame_outButton_mouseAdapter(this));
		this.wireButton.setFont(new Font("Dialog", 1, 32));
		this.wireButton.setToolTipText("Enter wiring mode");
		this.wireButton.addMouseListener(new CircuitDiagramFrame_wireButton_mouseAdapter(this));
		this.labelButton.setFont(new Font("Lucida Bright", 1, 16));
		this.labelButton.setToolTipText("Add a text label to the workspace");
		this.labelButton.setVerifyInputWhenFocusTarget(true);
		this.labelButton.setText("A");
		this.labelButton.addMouseListener(new CircuitDiagramFrame_labelButton_mouseAdapter(this));
		this.andButton.setToolTipText("Add an AND gate to the workspace");
		this.orButton.setToolTipText("Add an OR gate to the workspace");
		this.notButton.setToolTipText("Add a NOT gate to the workspace");
		this.inButton.setToolTipText("Add a chip input pin to the workspace");
		this.outButton.setToolTipText("Add a chip output pin to the workspace");
		this.groundButton.addMouseListener(new CircuitDiagramFrame_groundButton_mouseAdapter(this));
		this.vccButton.addMouseListener(new CircuitDiagramFrame_vccButton_mouseAdapter(this));
		this.pointerButton.setToolTipText("Click here to exit wiring mode or component placement mode");
		this.pointerButton.addMouseListener(new CircuitDiagramFrame_pointerButton_mouseAdapter(this));
		this.groundButton.setToolTipText("Add a component which provides a constant 0 value");
		this.vccButton.setToolTipText("Add a comnponent which provides a constant 1 value");
		this.getContentPane().add(this.propertiesPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(6, 6, 0, 11), 741, 101));

		URL iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(0));
		this.andButton.setIcon(new ImageIcon(iconURL));
		this.andButton.setSize(this.andButton.getWidth(), 40);
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(1));
		this.orButton.setIcon(new ImageIcon(iconURL));
		this.orButton.setSize(this.orButton.getWidth(), 40);
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(2));
		this.notButton.setIcon(new ImageIcon(iconURL));
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(3));
		this.inButton.setIcon(new ImageIcon(iconURL));
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(4));
		this.outButton.setIcon(new ImageIcon(iconURL));
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(5));
		this.groundButton.setIcon(new ImageIcon(iconURL));
		iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(6));
		this.vccButton.setIcon(new ImageIcon(iconURL));
		iconURL = URLMaker.getURL("images/select.gif");
		this.pointerButton.setIcon(new ImageIcon(iconURL));
		this.pointerButton.setSize(40, 40);
		iconURL = URLMaker.getURL("images/wire.gif");
		this.wireButton.setIcon(new ImageIcon(iconURL));

		this.getContentPane().add(this.jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 6, 0, 11), 591, 346));

		this.jPanel1.add(this.toolBar, "North");
		this.jPanel1.add(this.scrWorkspace, "Center");
		this.getContentPane().add(this.pnlStatus, new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 15, 1, new Insets(7, 6, 11, 11), 719, 3));

		this.pnlStatus.add(this.lblStatus, "Center");
		this.scrWorkspace.getViewport().add(this.workspace, null);
		this.toolBar.add(this.pointerButton, null);
		this.toolBar.addSeparator();
		this.toolBar.add(this.wireButton, null);
		this.toolBar.add(this.labelButton, null);
		this.toolBar.addSeparator();
		this.toolBar.add(this.andButton, null);
		this.toolBar.add(this.orButton, null);
		this.toolBar.add(this.notButton, null);
		this.toolBar.add(this.inButton, null);
		this.toolBar.add(this.outButton, null);
		this.toolBar.add(this.groundButton, null);
		this.toolBar.add(this.vccButton, null);

		this.standardMenuObject.getSaveMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitDiagramFrame.this.createChipFile();
			}
		});
		this.standardMenuObject.getExitMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitDiagramFrame.this.exitAction();
			}
		});
		this.standardMenuObject.getNewMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this workspace?\nAll unsaved work will be lost", "New Circuit", 0, 1) == 0) {
						CircuitDiagramFrame.this.closeWindow();
						CircuitDiagramFrame newFrame = new CircuitDiagramFrame(CircuitDiagramFrame.this.inApplet);

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

	private void createChipFile() {
		boolean errorOccured = false;
		String inputPinList = "";
		String outputPinList = "";

		ArrayList<CircuitComponent> inList = this.workspace.getInputs();
		ArrayList<CircuitComponent> outList = this.workspace.getOutputs();
		int inPinCount = inList.size();
		int outPinCount = outList.size();
		File chipFile = null;

		PrintWriter writer = null;

		this.workspace.setWiringMode(false);
		this.lblStatus.setText("Simulating circuit and creating chip file...");

		if (outPinCount < 1) {
			JOptionPane.showMessageDialog(this, "Error: Chip has no output pins!", "No Output Pins!", 2);

			errorOccured = true;
		}

		if (!errorOccured) {
			chipFile = FileOperations.getFileSelection("chp");
			if (chipFile == null) {
				errorOccured = true;
			}

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
			for (int i = 0; i < inPinCount; i++) {
				inputPinList = inputPinList + inList.get(i).getText() + ";";
			}
			writer.println("Input Pins=" + inputPinList);
			for (int i = 0; i < outPinCount; i++) {
				outputPinList = outputPinList + outList.get(i).getText() + ";";
			}
			writer.println("Output Pins=" + outputPinList);
			writer.println("Number of States=1");
			writer.println("Initial State=0");
			writer.println("++++++++++");

			int combinationCount = (int) Math.pow(2.0D, inPinCount);

			for (int i = 0; i < combinationCount; i++) {
				String strCombination = Integer.toBinaryString(i);
				strCombination = this.fillWithZeros(strCombination, inPinCount);

				for (int j = 0; j < inPinCount; j++) {
					CircuitComponent inputPin = this.workspace.getInputs().get(j);
					boolean state;
					if (strCombination.charAt(j) == '1') {
						state = true;
					} else {
						state = false;
					}
					inputPin.setState(state);
				}
				String outputString = "";

				for (int j = 0; j < outPinCount; j++) {
					CircuitComponent comp = this.workspace.getOutputs().get(j);
					boolean state = comp.simulate();
					if (state) {
						outputString = outputString + '1';
					} else {
						outputString = outputString + '0';
					}
				}
				writer.println(strCombination + ";0;" + outputString + ";0;" + this.propertiesPanel.txtDelay.getValue());
			}

			writer.close();
			this.lblStatus.setText("Chip file created");
		}

		if (!errorOccured) {
			if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the state table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1) {
				this.immediateExit();
			}
		} else {
			this.lblStatus.setText("Error creating chip file");
		}
	}

	private String fillWithZeros(String binaryString, int length) {
		String filledString = binaryString;
		for (int i = filledString.length(); i < length; i++) {
			filledString = "0" + filledString;
		}
		if (length == 0) {
			filledString = "";
		}
		return filledString;
	}

	void andButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(0);
	}

	void orButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(1);
	}

	void notButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(2);
	}

	void inButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(3);
	}

	void outButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(4);
	}

	void wireButton_mouseClicked(MouseEvent e) {
		this.workspace.setWiringMode(true);
	}

	void labelButton_mouseClicked(MouseEvent e) {
		this.workspace.createLabel();
	}

	void groundButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(5);
	}

	void vccButton_mouseClicked(MouseEvent e) {
		this.workspace.addCircuitComponent(6);
	}

	void pointerButton_mouseClicked(MouseEvent e) {
		this.workspace.exitPlacementMode();
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == 201) {
			this.exitAction();
		}
	}
}
