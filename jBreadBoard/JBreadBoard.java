package jBreadBoard;

import jBreadBoard.v1_00.ChipModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class JBreadBoard extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame jframe;
	private Circuit circuit = new Circuit(this);
	private JLabel statusbar = new JLabel(" ");
	private JLabel probebar = new JLabel(" ");
	private JSlider speedslider = new JSlider(0, 90, 30);
	private JBreadBoardActionListener jbbal = new JBreadBoardActionListener(this);
	private SelectPane selectpane = new SelectPane(this);
	private String mode;
	private static boolean inApplet = false;

	public static void main(String[] args) {
		JBreadBoard jBreadBoard = new JBreadBoard();
		jBreadBoard.jframe = jBreadBoard.createFrame();
	}

	@Override
	public void init() {
		JBreadBoard.inApplet = true;

		JLabel label = new JLabel("Java BreadBoard Simulator");
		label.setHorizontalAlignment(0);
		this.getContentPane().add(label, "North");

		JButton button = new JButton("Start Program");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JBreadBoard.this.jframe == null) {
					JBreadBoard.this.jframe = JBreadBoard.this.createFrame();
				}
			}
		});
		this.getContentPane().add(button, "Center");
	}

	public static boolean inApplet() {
		return JBreadBoard.inApplet;
	}

	public void exit() {
		if (JBreadBoard.inApplet) {
			this.jframe.dispose();
			this.jframe = null;
		} else {
			System.exit(0);
		}
	}

	public void newcircuit() {
		if (this.circuit.getComponentCount() > 0) {
			Object[] options = { "Yes", "No" };
			int n = JOptionPane.showOptionDialog(this.jframe, "The Circuit is not empty.\nAre sure you want to Erase it?", "WARNING", 0, 2, null, options, options[1]);

			if (n == 0) {
				this.circuit.removeAll();
				this.circuit.resetSimulation();
				this.circuit.repaint();
			}
		}
		this.setMode("move");
	}

	public void save() {
		FileDialogs.save(this.jframe, this.circuit.toString());
	}

	public void load() {
		this.newcircuit();
		if (this.circuit.getComponentCount() != 0) {
			return;
		}
		AdvChip.insertCircuit(this.jframe, this.circuit, 0, 0);
	}

	public void insertCircuit() {
		AdvChip.insertCircuit(this.jframe, this.circuit, 0, AdvChip.lowestBoard(this.circuit));
	}

	public Circuit getCircuit() {
		return this.circuit;
	}

	public JFrame getJFrame() {
		return this.jframe;
	}

	protected URL getURL(String filename) {
		URL url = ClassLoader.getSystemResource(filename);

		return url;
	}

	public ImageIcon getImageIcon(String s) {
		if (s == null) {
			return null;
		}

		if (JBreadBoard.inApplet) {
			URL iconURL = this.getURL(s);
			if (iconURL != null) {
				return new ImageIcon(iconURL);
			}
		} else {
			return new ImageIcon(s);
		}

		return null;
	}

	public void setMessage(String message) {
		this.statusbar.setText(message);
		this.statusbar.repaint();
	}

	public String getMessage() {
		return this.statusbar.getText();
	}

	public void setMode(String modename) {
		this.setMode(modename, "OK");
	}

	public String getMode() {
		if (this.mode == null) {
			return "";
		}
		return this.mode;
	}

	public int getSimSpeed() {
		return (int) Math.pow(10.0D, this.speedslider.getValue() / 10.0F);
	}

	public SelectPane getSelectPane() {
		return this.selectpane;
	}

	void setMode(String modename, String modetxt) {
		if (this.mode != null && this.mode.equals("drawingwire") && WireDrawer.current != null) {
			Wire w = WireDrawer.current;
			WireDrawer.current = null;
			WireDrawer.last = null;
			w.delete();
		}

		if (this.mode != null && this.mode.equals("sim") && modename != null && !modename.equals("sim")) {
			this.circuit.resetSimulation();
		}

		this.mode = modename;
		this.setMessage(modetxt);
	}

	void setProbebar(String s) {
		this.probebar.setText(s);
		if (s.equals("HIGH")) {
			this.probebar.setForeground(Color.red);
		} else if (s.equals("LOW")) {
			this.probebar.setForeground(new Color(41, 127, 55));
		} else {
			this.probebar.setForeground(null);
		}
	}

	public static boolean implementsInterface(ChipModel chipname, String inter) {
		boolean result = false;
		Class class_name = chipname.getClass();

		while (!class_name.getSimpleName().equalsIgnoreCase("Object")) {
			Class[] interfaces = class_name.getInterfaces();

			for (Class interface1 : interfaces) {
				if (interface1.getName().equals(inter)) {
					result = true;
				}
			}
			class_name = class_name.getSuperclass();
		}

		return result;
	}

	public static boolean implementsClass(ChipModel chipname, String className) {
		boolean result = false;
		Class class_name = chipname.getClass();

		while (!class_name.getSimpleName().equalsIgnoreCase("Object")) {
			if (class_name.getSimpleName().equalsIgnoreCase(className)) {
				result = true;
			}
			class_name = class_name.getSuperclass();
		}

		return result;
	}

	public static ChipModel getChipModel(String s) {
		Class chipModel = null;
		try {
			chipModel = Class.forName(s);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error  JBreadBoard.004\n" + e.getMessage(), "Error ", 0);
		} catch (NoClassDefFoundError e) {
			JOptionPane.showMessageDialog(null, "Error  JBreadBoard.005\n" + e.getMessage(), "Error", 0);
		}

		if (chipModel != null) {
			try {
				Object object = chipModel.newInstance();
				if (JBreadBoard.implementsInterface((ChipModel) object, "jBreadBoard.v1_00.ChipModel")) {
					return (ChipModel) object;
				}

				JOptionPane.showMessageDialog(null, "Error  JBreadBoard.006\n" + s + ".class" + "does not implement jBreadBoard.v1_00.ChipModel.", "Error: ", 0);
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(null, "Error  \n" + e.getMessage(), "Error  JBreadBoard.007", 0);
			} catch (InstantiationException e) {
				JOptionPane.showMessageDialog(null, "Error.\n" + e.getMessage(), "Error  JBreadBoard.008", 0);
			}

		}

		return null;
	}

	public JFrame createFrame() {
		JFrame frame = new JFrame("Java BreadBoard Simulator");

		this.circuit = new Circuit(this);
		this.setMode("move");

		frame.setJMenuBar(this.buildMenu());

		this.statusbar.setBorder(BorderFactory.createLoweredBevelBorder());
		this.probebar.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel statuspane = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		statuspane.setLayout(gridbag);
		frame.getContentPane().add(statuspane, "South");

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 4.0D;
		c.fill = 1;
		gridbag.setConstraints(this.statusbar, c);
		statuspane.add(this.statusbar);

		c.gridx = 1;
		c.weightx = 1.0D;
		gridbag.setConstraints(this.probebar, c);
		statuspane.add(this.probebar);

		JPanel inter1 = new JPanel(new BorderLayout());
		frame.getContentPane().add(inter1, "Center");

		inter1.add(this.buildToolBar(), "North");

		JPanel inter2 = new JPanel(new BorderLayout());
		inter1.add(inter2, "Center");

		inter2.add(this.selectpane, "West");

		JScrollPane scroller = new JScrollPane(this.circuit);
		inter2.add(scroller, "Center");

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JBreadBoard.this.exit();
			}
		});
		frame.pack();
		frame.setVisible(true);

		return frame;
	}

	private JMenuBar buildMenu() {
		JMenuBar jMenuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		jMenuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("New", 78);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Open", 79);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(79, 2));
		menuItem.setActionCommand("load");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Insert Circuit", 73);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(73, 2));
		menuItem.setActionCommand("insertcircuit");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save", 83);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
		menuItem.setActionCommand("save");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit", 120);
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu = new JMenu("Edit");
		menu.setMnemonic('E');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("Selection Mode", 115);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(155, 0));
		menuItem.setActionCommand("select");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Delete", 68);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(127, 0));
		menuItem.setActionCommand("delete");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu = new JMenu("Insert");
		menu.setMnemonic('I');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("BreadBoard", 66);
		menuItem.setActionCommand("breadboard");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Chip", 67);
		menuItem.setActionCommand("chip");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		JMenu submenu = new JMenu("Dip Switches");
		submenu.setMnemonic('D');
		menu.add(submenu);

		menuItem = new JMenuItem("Single", 83);
		menuItem.setActionCommand("dip1");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Double", 68);
		menuItem.setActionCommand("dip2");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Treble", 84);
		menuItem.setActionCommand("dip3");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Quad", 81);
		menuItem.setActionCommand("dip4");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		submenu = new JMenu("LED");
		submenu.setMnemonic('L');
		menu.add(submenu);

		menuItem = new JMenuItem("Red", 82);
		menuItem.setActionCommand("rled");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Yellow", 89);
		menuItem.setActionCommand("yled");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Green", 71);
		menuItem.setActionCommand("gled");
		menuItem.addActionListener(this.jbbal);
		submenu.add(menuItem);

		menu = new JMenu("Wire");
		menu.setMnemonic('W');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("Add Wires", 65);
		menuItem.setActionCommand("wire");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Cancel Wire Segment", 67);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(27, 0));
		menuItem.setActionCommand("cancelsegment");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Hide Wires", 72);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 2));
		menuItem.setActionCommand("hidewires");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Unhide Wires", 85);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(85, 2));
		menuItem.setActionCommand("unhidewires");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu.addSeparator();

		ButtonGroup group = new ButtonGroup();
		ActionListener colorActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command == "Custom") {
					final JColorChooser colorChooser = new JColorChooser();
					colorChooser.setColor(WireDrawer.color);
					JDialog dialog = JColorChooser.createDialog(null, "Pick a Colour", true, colorChooser, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							WireDrawer.color = colorChooser.getColor();
						}
					}, null);

					dialog.setVisible(true);
				} else if (command == "White") {
					WireDrawer.color = Color.white;
				} else if (command == "Black") {
					WireDrawer.color = Color.black;
				} else if (command == "Red") {
					WireDrawer.color = Color.red;
				} else if (command == "Orange") {
					WireDrawer.color = Color.orange;
				} else if (command == "Yellow") {
					WireDrawer.color = Color.yellow;
				} else if (command == "Green") {
					WireDrawer.color = Color.green;
				} else if (command == "Blue") {
					WireDrawer.color = Color.blue;
				}

			}
		};
		JRadioButtonMenuItem radio = new JRadioButtonMenuItem("White");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Black");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Red", true);
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Orange");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Yellow");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Green");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);
		radio = new JRadioButtonMenuItem("Blue");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);

		menu.addSeparator();
		radio = new JRadioButtonMenuItem("Custom");
		radio.addActionListener(colorActionListener);
		menu.add(radio);
		group.add(radio);

		menu = new JMenu("Simulation");
		menu.setMnemonic('S');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("Reset Simulation", 82);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(8, 0));
		menuItem.setActionCommand("reset");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Pause", 80);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 2));
		menuItem.setActionCommand("pause");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Step Simulation", 83);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(10, 0));
		menuItem.setActionCommand("step");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Run", 82);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(82, 2));
		menuItem.setActionCommand("animate");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu = new JMenu("Trace");
		menu.setMnemonic('T');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("Insert Probe", 80);
		menuItem.setActionCommand("addprobe");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save Trace", 83);
		menuItem.setActionCommand("savetrace");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu.addSeparator();

		JCheckBoxMenuItem checkitem = new JCheckBoxMenuItem("Dual data at Time", Trace.duplicate);
		checkitem.setActionCommand("toggledualdata");
		checkitem.addActionListener(this.jbbal);
		menu.add(checkitem);

		menu = new JMenu("Tools");
		menu.setMnemonic('T');
		jMenuBar.add(menu);

		String[] p = this.getToolList();

		for (String s : p) {
			String tmp = s.substring(0, s.lastIndexOf(".class"));
			menuItem = new JMenuItem(tmp);

			menuItem.setActionCommand("tool:" + s);
			menuItem.addActionListener(this.jbbal);

			menu.add(menuItem);
		}

		menu = new JMenu("Help");
		menu.setMnemonic('H');
		jMenuBar.add(menu);

		menuItem = new JMenuItem("User Guide", 85);
		menuItem.setActionCommand("userguide");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(112, 0));
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menuItem = new JMenuItem("Loading and Saving", 76);
		menuItem.setActionCommand("loadsave");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("About", 65);
		menuItem.setActionCommand("about");
		menuItem.addActionListener(this.jbbal);
		menu.add(menuItem);

		return jMenuBar;
	}

	private JToolBar buildToolBar() {
		JToolBar toolbar = new JToolBar();

		JButton button = new JButton(this.getImageIcon("images/new.gif"));
		button.setToolTipText("New");
		button.setActionCommand("new");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/open.gif"));
		button.setToolTipText("Open");
		button.setActionCommand("load");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/save.gif"));
		button.setToolTipText("Save");
		button.setActionCommand("save");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton(this.getImageIcon("images/select.gif"));
		button.setToolTipText("Selector");
		button.setActionCommand("select");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/del.gif"));
		button.setToolTipText("Delete Object");
		button.setActionCommand("delete");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton(this.getImageIcon("images/newb.gif"));
		button.setToolTipText("Insert BreadBoard");
		button.setActionCommand("breadboard");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/chip.gif"));
		button.setToolTipText("Add Chip");
		button.setActionCommand("defaultchip");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/chipq.gif"));
		button.setToolTipText("Select and Add Chip");
		button.setActionCommand("chip");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/wire.gif"));
		button.setToolTipText("Wiring Mode");
		button.setActionCommand("wire");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton(this.getImageIcon("images/led.gif"));
		button.setToolTipText("Add LED");
		button.setActionCommand("led");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/dip.gif"));
		button.setToolTipText("Add Dip Switches");
		button.setActionCommand("dip");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton(this.getImageIcon("images/reset.gif"));
		button.setToolTipText("Reset Simulation");
		button.setActionCommand("reset");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/pause.gif"));
		button.setToolTipText("Pause Simulation");
		button.setActionCommand("pause");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/step.gif"));
		button.setToolTipText("Step Simulation");
		button.setActionCommand("step");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		button = new JButton(this.getImageIcon("images/run.gif"));
		button.setToolTipText("Run Simulation");
		button.setActionCommand("animate");
		button.addActionListener(this.jbbal);
		toolbar.add(button);

		toolbar.addSeparator();

		JLabel label = new JLabel("Sim Speed: ");
		toolbar.add(label);

		this.speedslider.setPaintTicks(true);
		this.speedslider.setMajorTickSpacing(10);
		toolbar.add(this.speedslider);

		return toolbar;
	}

	public String[] getToolList() {
		URL url = ClassLoader.getSystemResource("tools");

		String fileName = url.getFile();

		File dir = new File(fileName);

		if (!dir.exists()) {
			return new String[] { "Tool Directory error" };
		}

		String[] tools = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});
		if (tools.equals(null)) {
			return new String[] { "a", "b", "c" };
		}
		return tools;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.JBreadBoard JD-Core Version: 0.6.2
 */
