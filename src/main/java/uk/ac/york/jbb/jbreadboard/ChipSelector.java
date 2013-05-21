package uk.ac.york.jbb.jbreadboard;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.ac.york.jbb.designtools.FileOperations;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.ttl.generic.InvalidStateException;
import uk.ac.york.jbb.jbreadboard.v1_00.ChipModel;
import uk.ac.york.jbb.jbreadboard.v1_10.LoadSave;


public class ChipSelector extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String defaultchip = "uk.ac.york.jbb.integratedcircuits.ttl.logic.Gen7400";
	private JBreadBoard jBreadBoard;
	private JList<String> componentList = new JList<String>();
	private JLabel chipname = new JLabel();
	private JTextArea chipdescription = new JTextArea();
	private JLabel chipmanufacturer = new JLabel();
	private JComboBox<String> chipderivatives = new JComboBox<String>();
	private ImageIcon chipdiagram;
	private String defaultDirectory = "uk/ac/york/jbb/integratedcircuits";
	private String currentDirectory = "uk/ac/york/jbb/integratedcircuits";

	private Hashtable<String, ClassRecord> directoryClasses = new Hashtable<String, ClassRecord>();

	private JPanel imagepanel = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (ChipSelector.this.chipdiagram != null) {
				ChipSelector.this.chipdiagram.paintIcon(this, g, (this.getWidth() - ChipSelector.this.chipdiagram.getIconWidth()) / 2, (this.getHeight() - ChipSelector.this.chipdiagram.getIconHeight()) / 2);
			}
		}
	};

	public static void addChipDialog(Component c, JBreadBoard j) {
		final JBreadBoard jBreadBoard = j;
		ChipSelector pane = new ChipSelector(j);
		final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(c), "Select a Chip", true);

		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(pane, "Center");

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(1));
		dialog.getContentPane().add(buttonPane, "South");

		JButton okButton = new JButton("OK");
		dialog.getRootPane().setDefaultButton(okButton);
		buttonPane.add(okButton);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);

				ChipModel chipModel = ChipSelector.getDefaultModel();

				if (JBreadBoard.implementsInterface(chipModel, "uk.ac.york.jbb.jbreadboard.v1_10.LoadSave")) {
					String extension = ((LoadSave) chipModel).getFileExtension();

					File file = FileOperations.getFileSelection(extension, "Load Config File ?");

					if (file == null) {
						if (((LoadSave) chipModel).instantiateBlank()) {
							Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
							jBreadBoard.getCircuit().BBselect.add(chip);
						} else {
							JOptionPane.showMessageDialog(null, "Error : need config file", "Error: ", 0);
						}

					} else {
						String fileName = file.toString();
						fileName = fileName.replace('\\', '/');
						try {
							((LoadSave) chipModel).initialiseState(fileName);
							Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
							jBreadBoard.getCircuit().BBselect.add(chip);
						} catch (InvalidStateException e1) {
							JOptionPane.showMessageDialog(null, "Error : InvalidTStateException", "Error: ", 0);
						}

					}

				} else {
					Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
					jBreadBoard.getCircuit().BBselect.add(chip);
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		dialog.setLocationRelativeTo(c);
		dialog.pack();
		dialog.setVisible(true);
	}

	private void refreshDirectoryList(Direction direction, String selectedDirectory) {
		Vector<String> listNames = new Vector<String>();
		Vector<String> directoryNames = new Vector<String>();
		Vector<String> fileNames = new Vector<String>();
		Vector<String> classNames = new Vector<String>();

		this.directoryClasses.clear();
		URL url;
		switch (direction.ordinal()) {
			case 2:
				url = ClassLoader.getSystemResource(this.currentDirectory);
				break;
			case 0:
				if (this.currentDirectory.equals(this.defaultDirectory)) {
					url = ClassLoader.getSystemResource(this.currentDirectory);
				} else {
					url = ClassLoader.getSystemResource(this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf("/")));
				}
				break;
			case 1:
				url = ClassLoader.getSystemResource(this.currentDirectory + "/" + selectedDirectory);
				break;
			default:
				url = ClassLoader.getSystemResource(this.currentDirectory);
		}

		try {
			String fileName = url.getFile();
			File directory = new File(fileName);

			if (directory.isDirectory()) {
				switch (direction.ordinal()) {
					case 2:
						this.currentDirectory = this.defaultDirectory;
						break;
					case 0:
						if (!this.currentDirectory.equals(this.defaultDirectory)) {
							this.currentDirectory = this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf("/"));
						} else {
							this.currentDirectory = this.defaultDirectory;
						}
						break;
					case 1:
						this.currentDirectory = this.currentDirectory + "/" + selectedDirectory;
						break;
					default:
						this.currentDirectory = this.defaultDirectory;
						System.out.println("currentDirectory ERROR");
				}
			}
		} catch (NullPointerException e1) {
			String selectedItem = this.componentList.getSelectedValue();
			System.out.println("Directory does not exist : " + selectedItem);
		}

		url = ClassLoader.getSystemResource(this.currentDirectory);
		String fileName = url.getFile();
		File directory = new File(fileName);
		String[] contents = directory.list();

		for (int i = 0; i < contents.length; i++) {
			File item = new File(directory + "/" + contents[i]);

			if (item.isDirectory()) {
				if (!contents[i].equals("images")) {
					directoryNames.add(contents[i]);
				}

			} else if (contents[i].endsWith(".class")) {
				String fullClassName = item.toString().replace("\\", "/");
				fullClassName = fullClassName.substring(fullClassName.indexOf(this.defaultDirectory), fullClassName.length() - 6).replace('/', '.');

				fileNames.add(fullClassName);
			}

		}

		for (int i = 0; i < directoryNames.size(); i++) {
			listNames.add(directoryNames.get(i));
		}

		if (!this.currentDirectory.equals(this.defaultDirectory)) {
			for (int i = 0; i < fileNames.size(); i++) {
				String key = fileNames.get(i).toString().substring(fileNames.get(i).toString().lastIndexOf('.') + 1);
				try {
					this.directoryClasses.put(key, new ClassRecord(Class.forName(fileNames.get(i))));
				} catch (ClassNotFoundException e1) {
					System.out.println("ClassNotFoundException Error ");
				}
			}

			for (int i = 0; i < fileNames.size(); i++) {
				try {
					Class<?> c = Class.forName(fileNames.get(i));

					for (int j = 0; j < fileNames.size(); j++) {
						try {
							Class<?> clazz = Class.forName(fileNames.get(j));
							if (IntegratedCircuit.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
								Object o = Class.forName(fileNames.get(j)).newInstance();
	
								if (c.isInstance(o)) {
									String classKey = fileNames.get(i).toString().substring(fileNames.get(i).toString().lastIndexOf('.') + 1);
									String objectKey = fileNames.get(j).toString().substring(fileNames.get(j).toString().lastIndexOf('.') + 1);
	
									this.directoryClasses.get(classKey).addClass(o.getClass());
									this.directoryClasses.get(objectKey).incReferencedCount();
								}
							}
						} catch (InstantiationException e1) {
							System.out.println("InstantiationException Error " + e1);
							System.out.println(fileNames.get(j));
						} catch (IllegalAccessException e1) {
							System.out.println("IllegalAccessException Error " + e1);
							System.out.println(fileNames.get(j));
						}
					}
				} catch (ClassNotFoundException e1) {
					System.out.println("ClassNotFoundException Error " + e1);
				}
			}
			Enumeration<String> keys = this.directoryClasses.keys();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				ClassRecord classSet = this.directoryClasses.get(key);

				if (classSet.getReferencedCount() == 1) {
					classNames.add(key);
				}

			}

		}

		Collections.sort(classNames);
		listNames.addAll(classNames);

		this.componentList.setForeground(Color.blue);
		this.componentList.setListData(listNames);
	}

	public static ChipModel getDefaultModel() {
		ChipModel model = JBreadBoard.getChipModel(ChipSelector.defaultchip);
		return model;
	}

	private void updateChipDetails(ChipModel ic) {
		this.chipdescription.setText(ic.getDescription());
		this.chipmanufacturer.setText(ic.getManufacturer());
		this.chipdiagram = this.jBreadBoard.getImageIcon(this.currentDirectory + "/" + ic.getDiagram());
		this.imagepanel.repaint();
	}

	private void updateSelectedChip(String selectedItem) {
		try {
			if (!selectedItem.isEmpty()) {
				ClassRecord classSet = this.directoryClasses.get(selectedItem);

				String className = classSet.getBaseClass().toString().substring(classSet.getBaseClass().toString().lastIndexOf(' ') + 1);

				String componentName = className.substring(className.lastIndexOf('.') + 1);
				try {
					ChipModel ic = (ChipModel) Class.forName(className).newInstance();
					this.updateChipDetails(ic);

					this.chipderivatives.removeAllItems();
					this.chipderivatives.addItem(componentName);
					Class<?>[] classArray = classSet.getClasses();
					for (int i = 0; i < classArray.length; i++) {
						if (!componentName.equals(classArray[i].getSimpleName())) {
							this.chipderivatives.addItem(classArray[i].getSimpleName());
						}
					}
				} catch (InstantiationException e1) {
					System.out.println("InstantiationException Error ");
				} catch (IllegalAccessException e1) {
					System.out.println("IllegalAccessException Error ");
				} catch (ClassNotFoundException e1) {
					System.out.println("ClassNotFoundException Error ");
				}
			}
		} catch (NullPointerException e1) {
		}
	}

	public ChipSelector(JBreadBoard j) {
		this.jBreadBoard = j;

		this.refreshDirectoryList(Direction.CURRENT, "");

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		this.setLayout(gridbag);
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.chipname.setForeground(Color.black);

		this.chipmanufacturer.setForeground(Color.black);
		this.chipmanufacturer.setBackground(Color.white);
		this.chipmanufacturer.setOpaque(true);
		this.chipmanufacturer.setBorder(BorderFactory.createEtchedBorder());

		this.chipdescription.setColumns(20);
		this.chipdescription.setLineWrap(true);
		this.chipdescription.setWrapStyleWord(true);
		this.chipdescription.setEditable(false);
		this.chipdescription.setForeground(Color.black);
		this.chipdescription.setBackground(Color.white);
		this.chipdescription.setOpaque(true);
		this.chipdescription.setBorder(BorderFactory.createEtchedBorder());

		this.imagepanel.setBackground(Color.white);
		this.imagepanel.setOpaque(true);
		this.imagepanel.setPreferredSize(new Dimension(128, 128));
		this.imagepanel.setMinimumSize(new Dimension(128, 128));
		this.imagepanel.setMaximumSize(new Dimension(128, 128));
		this.imagepanel.setSize(new Dimension(128, 128));
		this.imagepanel.setBorder(BorderFactory.createEtchedBorder());

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 9;
		c.weighty = 2.0D;
		c.weightx = 1.0D;
		c.fill = 1;
		JScrollPane scrolllist = new JScrollPane(this.componentList);
		this.componentList.addMouseListener(new ChipSelectorClicked());
		this.componentList.addListSelectionListener(new ChipSelectorUpdate());
		gridbag.setConstraints(scrolllist, c);
		this.add(scrolllist);

		c.gridx = 2;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weighty = 0.0D;
		c.weightx = 0.0D;
		c.fill = 0;
		gridbag.setConstraints(this.chipname, c);
		this.add(this.chipname);

		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = 13;
		JLabel jlabel = new JLabel("Derivatives: ");
		jlabel.setForeground(Color.black);
		gridbag.setConstraints(jlabel, c);
		this.add(jlabel);

		c.gridx = 3;
		c.anchor = 17;
		gridbag.setConstraints(this.chipderivatives, c);
		this.add(this.chipderivatives);
		this.chipderivatives.addActionListener(new ChipDerivativesAction());

		c.gridy = 3;
		c.gridx = 2;
		c.insets = new Insets(2, 1, 2, 1);
		c.anchor = 13;
		jlabel = new JLabel("Manufacturer: ");
		jlabel.setForeground(Color.black);
		gridbag.setConstraints(jlabel, c);
		this.add(jlabel);

		c.gridx = 3;
		c.gridwidth = 2;
		c.fill = 2;
		c.anchor = 17;
		gridbag.setConstraints(this.chipmanufacturer, c);
		this.add(this.chipmanufacturer);

		c.gridy = 4;
		c.gridx = 2;
		c.gridwidth = 1;
		c.fill = 2;
		c.anchor = 13;
		jlabel = new JLabel("Description: ");
		jlabel.setForeground(Color.black);
		gridbag.setConstraints(jlabel, c);
		this.add(jlabel);

		c.gridx = 3;
		c.gridwidth = 2;
		c.gridheight = 3;
		c.anchor = 17;
		gridbag.setConstraints(this.chipdescription, c);
		this.add(this.chipdescription);

		c.gridx = 2;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = 0;
		c.anchor = 13;
		jlabel = new JLabel("Pin Diagram: ");
		jlabel.setForeground(Color.black);
		gridbag.setConstraints(jlabel, c);
		this.add(jlabel);

		c.gridx = 2;
		c.gridy = 8;
		c.gridwidth = 3;
		c.fill = 1;
		c.anchor = 11;
		gridbag.setConstraints(this.imagepanel, c);
		this.add(this.imagepanel);

		this.setPreferredSize(new Dimension(500, 330));
	}

	private class ChipSelectorClicked implements MouseListener {
		private ChipSelectorClicked() {}

		@Override
		public void mouseClicked(MouseEvent e) {
			int numberOfClicks = e.getClickCount();
			int buttonClicked = e.getButton();

			String selectedItem = ChipSelector.this.componentList.getSelectedValue();

			if (numberOfClicks == 1 && buttonClicked == 1) {
				ChipSelector.this.updateSelectedChip(selectedItem);
			}

			if (numberOfClicks == 2) {
				if (buttonClicked == 1) {
					ChipSelector.this.refreshDirectoryList(ChipSelector.Direction.DOWN, selectedItem);
				} else {
					ChipSelector.this.refreshDirectoryList(ChipSelector.Direction.UP, selectedItem);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}

	private class ChipSelectorUpdate implements ListSelectionListener {
		private ChipSelectorUpdate() {}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			String selectedItem = ChipSelector.this.componentList.getSelectedValue();

			ChipSelector.this.updateSelectedChip(selectedItem);
		}
	}

	private class ChipDerivativesAction implements ActionListener {
		private ChipDerivativesAction() {}

		@Override
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) e.getSource();
			String name = (String) cb.getSelectedItem();

			if (name != null) {
				ChipSelector.ClassRecord classSet = ChipSelector.this.directoryClasses.get(name);
				String className = classSet.getBaseClass().toString().substring(classSet.getBaseClass().toString().lastIndexOf(' ') + 1);
				try {
					Class.forName(className).newInstance();
					ChipSelector.defaultchip = className;
				} catch (InstantiationException e1) {
					System.out.println("InstantiationException Error ");
				} catch (IllegalAccessException e1) {
					System.out.println("IllegalAccessException Error ");
				} catch (ClassNotFoundException e1) {
					System.out.println("ClassNotFoundException Error ");
				}
			}
		}
	}

	private class ClassRecord {
		private HashSet<Class<?>> implementsClass;
		private int referencedCount;
		private Class<?> baseClass;

		ClassRecord(Class<?> base) {
			this.implementsClass = new HashSet<Class<?>>();
			this.referencedCount = 0;
			this.baseClass = base;
		}

		public void addClass(Class<?> c) {
			this.implementsClass.add(c);
		}

		public Class<?>[] getClasses() {
			return this.implementsClass.toArray(new Class[this.implementsClass.size()]);
		}

		public Class<?> getBaseClass() {
			return this.baseClass;
		}

		public void incReferencedCount() {
			this.referencedCount += 1;
		}

		public int getReferencedCount() {
			return this.referencedCount;
		}

	}

	private static enum Direction {
		UP, DOWN, CURRENT;
	}
}
