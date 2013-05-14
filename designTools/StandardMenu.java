package designTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class StandardMenu {
	protected JMenuBar menuBar = new JMenuBar();
	private JMenuItem saveItem;
	private JMenuItem exitItem;
	private JMenuItem newItem;
	protected JMenu helpMenu;

	public StandardMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		this.menuBar.add(fileMenu);
		this.helpMenu = new JMenu("Help");
		this.helpMenu.setMnemonic('H');
		this.menuBar.add(this.helpMenu);
		this.newItem = new JMenuItem("New", 78);
		this.newItem.setAccelerator(KeyStroke.getKeyStroke(78, 2));
		fileMenu.add(this.newItem);
		this.saveItem = new JMenuItem("Create Chip File", 67);
		this.saveItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
		fileMenu.add(this.saveItem);
		fileMenu.addSeparator();
		this.exitItem = new JMenuItem("Exit", 120);
		fileMenu.add(this.exitItem);
		JMenuItem helpItem = new JMenuItem("User Guide", 85);
		helpItem.setAccelerator(KeyStroke.getKeyStroke(112, 0));
		this.helpMenu.add(helpItem);
		JMenuItem loadingItem = new JMenuItem("Loading and Saving", 76);
		this.helpMenu.add(loadingItem);
		this.helpMenu.addSeparator();
		JMenuItem aboutItem = new JMenuItem("About", 65);
		this.helpMenu.add(aboutItem);

		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, "Chip Design Tools for the Java Breadboard Simulator\n\nStephen Halstead\n2004-2005\nUniversity of York\nComputer Science Department", "Chip Design Tools", 1);
			}
		});
		loadingItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				URL loadingPage = null;

				loadingPage = URLMaker.getURL("loadsave.html");
				HTMLViewer helpBrowser = new HTMLViewer(loadingPage, "Enabling Loading and Saving");
				helpBrowser.setVisible(true);
			}
		});
		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				URL indexPage = null;

				indexPage = URLMaker.getURL("designTools/doc/index.htm");
				HTMLViewer helpBrowser = new HTMLViewer(indexPage, "Chip Design Tools Help");
				helpBrowser.setVisible(true);
			}
		});
	}

	public JMenuBar getMenuBar() {
		return this.menuBar;
	}

	public JMenuItem getSaveMenuItem() {
		return this.saveItem;
	}

	public JMenuItem getExitMenuItem() {
		return this.exitItem;
	}

	public JMenuItem getNewMenuItem() {
		return this.newItem;
	}
}
