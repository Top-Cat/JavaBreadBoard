package jBreadBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileDialogs {
	private static JFileChooser fc;

	private static boolean hasFileAccess() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				sm.checkRead("test");
				sm.checkWrite("test");
				sm.checkPropertyAccess("user.home");
				sm.checkPermission(new RuntimePermission("modifyThread"));
			}
			return true;
		} catch (SecurityException e) {
		}
		return false;
	}

	private static boolean hasClipboardAccess() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				sm.checkSystemClipboardAccess();
			}
			return true;
		} catch (SecurityException e) {
		}
		return false;
	}

	public static String save(Component parent, String filestring) {
		if (FileDialogs.hasFileAccess()) {
			URL url = ClassLoader.getSystemResource("circuits");
			String urlName = url.getFile().substring(1);

			if (FileDialogs.fc == null) {
				FileDialogs.fc = new JFileChooser(urlName);
			}
			FileDialogs.fc.setFileFilter(new TextFilter("cir"));

			int returnVal = FileDialogs.fc.showSaveDialog(parent);

			if (returnVal == 0) {
				try {
					File file = FileDialogs.fc.getSelectedFile();
					FileOutputStream fw = new FileOutputStream(file);
					OutputStreamWriter ow = new OutputStreamWriter(fw, "ISO-8859-1");
					BufferedWriter bw = new BufferedWriter(ow);

					StringReader sr = new StringReader(filestring);
					BufferedReader br = new BufferedReader(sr);
					String line;
					while ((line = br.readLine()) != null) {
						bw.write(line);
						bw.newLine();
					}

					bw.close();
					fw.close();
					ow.close();

					sr.close();
					br.close();

					return file.getCanonicalPath();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(parent, "Error Saving File.\n" + e.getMessage(), "Error FileDialogs.001", 0);
				}

			}

		} else if (FileDialogs.hasClipboardAccess()) {
			final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(parent), "Save", true);

			dialog.getContentPane().setLayout(new BorderLayout());

			JPanel pane = new JPanel(new BorderLayout());
			JLabel label = new JLabel("To Save copy text into a file");

			label.setForeground(Color.black);

			final JTextArea savearea = new JTextArea(filestring);
			savearea.setEditable(false);
			savearea.selectAll();
			savearea.setPreferredSize(new Dimension(200, 300));

			JScrollPane scroller = new JScrollPane(savearea);
			pane.add(scroller, "Center");

			dialog.getContentPane().add(label, "North");
			dialog.getContentPane().add(pane, "Center");

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(1));
			dialog.getContentPane().add(buttonPane, "South");

			JButton copyButton = new JButton("Copy");
			buttonPane.add(copyButton);
			copyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					savearea.selectAll();
					savearea.copy();
				}
			});
			JButton okButton = new JButton("Close");
			dialog.getRootPane().setDefaultButton(okButton);
			buttonPane.add(okButton);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
				}
			});
			dialog.setLocationRelativeTo(parent);
			dialog.pack();

			dialog.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(parent, "Insufficient privilages to save.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
		}

		return "";
	}

	public static String load(Component parent) {
		if (FileDialogs.hasFileAccess()) {
			URL url = ClassLoader.getSystemResource("circuits");
			String urlName = url.getFile().substring(1);

			if (FileDialogs.fc == null) {
				FileDialogs.fc = new JFileChooser(urlName);
			}
			FileDialogs.fc.setFileFilter(new TextFilter("cir"));

			int returnVal = FileDialogs.fc.showOpenDialog(parent);

			if (returnVal == 0) {
				try {
					File file = FileDialogs.fc.getSelectedFile();

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
					JOptionPane.showMessageDialog(parent, "Error Loading File.\n" + e.getMessage(), "Error  FileDialogs.002", 0);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(parent, "Error Loading File.\n" + e.getMessage(), "Error  FileDialogs.003", 0);
				}

			}

			return null;
		}
		if (FileDialogs.hasClipboardAccess()) {
			final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(parent), "Load", true);

			dialog.getContentPane().setLayout(new BorderLayout());

			JPanel pane = new JPanel(new BorderLayout());
			JLabel label = new JLabel("To Load paste contents of file here");

			label.setForeground(Color.black);

			final JTextArea loadarea = new JTextArea();
			loadarea.setEditable(true);
			loadarea.setPreferredSize(new Dimension(200, 300));

			JScrollPane scroller = new JScrollPane(loadarea);
			pane.add(scroller, "Center");

			dialog.getContentPane().add(label, "North");
			dialog.getContentPane().add(pane, "Center");

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(1));
			dialog.getContentPane().add(buttonPane, "South");

			JButton pasteButton = new JButton("Paste");
			buttonPane.add(pasteButton);
			pasteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					loadarea.selectAll();
					loadarea.replaceSelection("");
					loadarea.paste();
				}
			});
			JButton okButton = new JButton("OK");
			dialog.getRootPane().setDefaultButton(okButton);
			buttonPane.add(okButton);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
				}
			});
			JButton cancelButton = new JButton("Cancel");
			dialog.getRootPane().setDefaultButton(cancelButton);
			buttonPane.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					loadarea.selectAll();
					loadarea.replaceSelection("");
				}
			});
			dialog.setLocationRelativeTo(parent);
			dialog.pack();

			dialog.setVisible(true);

			loadarea.selectAll();
			return loadarea.getSelectedText();
		}

		JOptionPane.showMessageDialog(parent, "Insufficient privilages to load.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);

		return null;
	}

	public static String selectDir(Component parent) {
		if (FileDialogs.hasFileAccess()) {
			if (FileDialogs.fc == null) {
				FileDialogs.fc = new JFileChooser();
			}
			FileDialogs.fc.setFileSelectionMode(1);

			int returnVal = FileDialogs.fc.showOpenDialog(parent);

			if (returnVal == 0) {
				return FileDialogs.fc.getSelectedFile().getName();
			}
		} else {
			JOptionPane.showMessageDialog(parent, "Insufficient privilages to load.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
		}

		return null;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.FileDialogs JD-Core Version: 0.6.2
 */
