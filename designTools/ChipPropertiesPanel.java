package designTools;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChipPropertiesPanel extends JPanel {
	JLabel lblChipText = new JLabel();
	JTextField txtChipText = new JTextField();
	JLabel lblManufacturer = new JLabel();
	JTextField txtManufacturer = new JTextField();
	JLabel lblWide = new JLabel();
	ButtonGroup booleanGroup = new ButtonGroup();
	JRadioButton radYes = new JRadioButton();
	JRadioButton radNo = new JRadioButton();
	JPanel jPanel1 = new JPanel();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JLabel lblDescription = new JLabel();
	JTextArea txtDescription = new JTextArea();
	JScrollPane descriptionPanel = new JScrollPane();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	JLabel lblDelay = new JLabel();
	JFormattedTextField txtDelay = new JFormattedTextField(NumberFormat.getIntegerInstance());

	public ChipPropertiesPanel() {
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.lblChipText.setToolTipText("Text which will appear on the chip on the breadboard");
		this.lblChipText.setText("Chip Label");
		this.setLayout(null);
		this.txtChipText.setToolTipText("Text which will appear on the chip on the breadboard");
		this.txtChipText.setText("Chip");
		this.lblManufacturer.setToolTipText("If the chip is modelled on an existing chip the manufacturer can be given here");

		this.lblManufacturer.setText("Manufacturer");
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.txtManufacturer.setToolTipText("If the chip is modelled on an existing chip the manufacturer can be given here");

		this.txtManufacturer.setText("Manufacturer");
		this.lblWide.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");

		this.lblWide.setHorizontalAlignment(2);
		this.lblWide.setText("Wide Chip");
		this.lblWide.setBounds(new Rectangle(12, 13, 67, 15));
		this.radYes.setFont(new Font("Dialog", 0, 12));
		this.radYes.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");

		this.radYes.setText("Yes");
		this.radYes.setBounds(new Rectangle(88, 10, 48, 23));
		this.radNo.setText("No");
		this.radNo.setBounds(new Rectangle(139, 10, 43, 23));
		this.radNo.setFont(new Font("Dialog", 0, 12));
		this.radNo.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");

		this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
		this.jPanel1.setBounds(new Rectangle(8, 7, 238, 86));
		this.jPanel1.setLayout(this.gridBagLayout1);
		this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
		this.jPanel2.setBounds(new Rectangle(253, 7, 201, 86));
		this.jPanel2.setLayout(null);
		this.jPanel3.setBorder(BorderFactory.createEtchedBorder());
		this.jPanel3.setBounds(new Rectangle(463, 7, 268, 86));
		this.jPanel3.setLayout(this.gridBagLayout2);
		this.lblDescription.setToolTipText("Optional, brief description of the chip");
		this.lblDescription.setText("Chip Description");
		this.txtDescription.setBorder(null);
		this.txtDescription.setToolTipText("Optional, brief description of the chip");
		this.txtDescription.setText("");
		this.txtDescription.setLineWrap(true);
		this.txtDescription.setWrapStyleWord(true);
		this.lblDelay.setText("Chip Delay / ns");
		this.lblDelay.setBounds(new Rectangle(12, 52, 105, 15));
		this.txtDelay.setHorizontalAlignment(11);
		this.txtDelay.setValue(new Integer(50));
		this.txtDelay.setBounds(new Rectangle(120, 50, 60, 20));
		this.add(this.jPanel1, null);
		this.jPanel1.add(this.txtManufacturer, new GridBagConstraints(1, 1, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(15, 0, 10, 13), 42, 6));

		this.jPanel1.add(this.txtChipText, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(7, 0, 0, 13), 92, 6));

		this.jPanel1.add(this.lblChipText, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 11, 7, 21), 8, 0));

		this.jPanel1.add(this.lblManufacturer, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(18, 11, 17, 0), 14, 0));

		this.jPanel2.add(this.lblWide, null);
		this.jPanel2.add(this.lblDelay, null);
		this.jPanel2.add(this.txtDelay, null);
		this.jPanel2.add(this.radNo, null);
		this.jPanel2.add(this.radYes, null);
		this.add(this.jPanel3, null);
		this.jPanel3.add(this.descriptionPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(6, 9, 12, 10), 0, 25));

		this.jPanel3.add(this.lblDescription, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 9, 0, 140), 42, 0));

		this.descriptionPanel.getViewport().add(this.txtDescription, null);
		this.add(this.jPanel2, null);
		this.booleanGroup.add(this.radYes);
		this.booleanGroup.add(this.radNo);
		this.radNo.setSelected(true);
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.ChipPropertiesPanel JD-Core Version:
 * 0.6.2
 */
