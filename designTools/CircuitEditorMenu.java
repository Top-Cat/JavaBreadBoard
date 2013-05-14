package designTools;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class CircuitEditorMenu extends StandardMenu {
	private Workspace wspace;

	public CircuitEditorMenu(Workspace workspace) {
		ButtonGroup colourGroup = new ButtonGroup();
		this.wspace = workspace;

		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		JMenuItem deleteItem = new JMenuItem("Delete", 68);
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(127, 0));
		editMenu.add(deleteItem);
		JMenuItem moveItem = new JMenuItem("Move", 77);
		editMenu.add(moveItem);
		JMenuItem renameItem = new JMenuItem("Rename", 82);
		editMenu.add(renameItem);

		JMenu insertMenu = new JMenu("Insert");
		insertMenu.setMnemonic('I');
		JMenuItem labelItem = new JMenuItem("Label", 76);
		insertMenu.add(labelItem);
		JMenuItem andItem = new JMenuItem("AND Gate", 65);
		insertMenu.add(andItem);
		JMenuItem orItem = new JMenuItem("OR Gate", 79);
		insertMenu.add(orItem);
		JMenuItem notItem = new JMenuItem("NOT Gate", 78);
		insertMenu.add(notItem);
		JMenuItem inItem = new JMenuItem("Input Pin", 73);
		insertMenu.add(inItem);
		JMenuItem outItem = new JMenuItem("Output Pin", 84);
		insertMenu.add(outItem);
		JMenuItem groundItem = new JMenuItem("Ground", 71);
		insertMenu.add(groundItem);
		JMenuItem vccItem = new JMenuItem("VCC", 86);
		insertMenu.add(vccItem);

		JMenu wireMenu = new JMenu("Wire");
		wireMenu.setMnemonic('W');
		JMenuItem wireItem = new JMenuItem("Enter Wiring Mode", 87);
		wireMenu.add(wireItem);
		JMenuItem unwireItem = new JMenuItem("Exit Wiring Mode", 88);
		wireMenu.add(unwireItem);
		JMenuItem cancelItem = new JMenuItem("Cancel Wire Segment", 67);
		cancelItem.setAccelerator(KeyStroke.getKeyStroke(27, 0));
		wireMenu.add(cancelItem);
		wireMenu.addSeparator();
		JRadioButtonMenuItem blackRadio = new JRadioButtonMenuItem("Black");
		colourGroup.add(blackRadio);
		wireMenu.add(blackRadio);
		blackRadio.setSelected(true);
		JRadioButtonMenuItem redRadio = new JRadioButtonMenuItem("Red");
		wireMenu.add(redRadio);
		colourGroup.add(redRadio);
		JRadioButtonMenuItem orangeRadio = new JRadioButtonMenuItem("Orange");
		wireMenu.add(orangeRadio);
		colourGroup.add(orangeRadio);
		JRadioButtonMenuItem yellowRadio = new JRadioButtonMenuItem("Yellow");
		wireMenu.add(yellowRadio);
		colourGroup.add(yellowRadio);
		JRadioButtonMenuItem greenRadio = new JRadioButtonMenuItem("Green");
		wireMenu.add(greenRadio);
		colourGroup.add(greenRadio);
		JRadioButtonMenuItem blueRadio = new JRadioButtonMenuItem("Blue");
		wireMenu.add(blueRadio);
		colourGroup.add(blueRadio);
		JRadioButtonMenuItem customRadio = new JRadioButtonMenuItem("Custom");
		wireMenu.add(customRadio);
		colourGroup.add(customRadio);
		this.menuBar.remove(this.helpMenu);
		this.menuBar.add(editMenu);
		this.menuBar.add(insertMenu);
		this.menuBar.add(wireMenu);
		this.menuBar.add(this.helpMenu);

		deleteItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.deleteSelectedComponent();
			}
		});
		moveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.moveSelectedComponent();
			}
		});
		renameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.renameSelectedComponent();
			}
		});
		labelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.createLabel();
			}
		});
		andItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(0);
			}
		});
		orItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(1);
			}
		});
		notItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(2);
			}
		});
		inItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(3);
			}
		});
		outItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(4);
			}
		});
		groundItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(5);
			}
		});
		vccItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.addCircuitComponent(6);
			}
		});
		wireItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWiringMode(true);
			}
		});
		unwireItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.exitPlacementMode();
			}
		});
		blackRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.BLACK);
			}
		});
		redRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.RED);
			}
		});
		orangeRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.ORANGE);
			}
		});
		yellowRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.YELLOW);
			}
		});
		greenRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.GREEN);
			}
		});
		blueRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(Color.BLUE);
			}
		});
		customRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.setWireColor(JColorChooser.showDialog(CircuitEditorMenu.this.wspace, "Wire Colour", Color.BLACK));
			}
		});
		cancelItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CircuitEditorMenu.this.wspace.cancelWireSegment();
			}
		});
	}

	@Override
	public JMenuBar getMenuBar() {
		return this.menuBar;
	}
}
