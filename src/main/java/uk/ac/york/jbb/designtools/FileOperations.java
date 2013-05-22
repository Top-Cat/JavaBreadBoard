package uk.ac.york.jbb.designtools;


import java.io.File;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.ac.york.jbb.jbreadboard.TextFilter;

public class FileOperations {
	public static boolean hasFileAccess() {
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

	public static boolean hasClipboardAccess() {
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

	public static File getFileSelection(String extension) {
		return FileOperations.getFileSelection(extension, "Choose Filename");
	}

	public static File getFileSelection(String extension, String DialogTitle) {
		String urlName = System.getProperty("user.dir");

		File selectedFile = null;

		if (!FileOperations.hasFileAccess()) {
			JOptionPane.showMessageDialog(null, "Insufficient priviliges to save.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
		} else {
			JFileChooser saveFileChooser = new JFileChooser(urlName);
			saveFileChooser.setFileFilter(new TextFilter(extension));

			int result = saveFileChooser.showDialog(null, DialogTitle);

			if (result == 0) {
				selectedFile = saveFileChooser.getSelectedFile();

				String filename = selectedFile.getPath();

				if (!filename.toLowerCase().endsWith("." + extension)) {
					filename = filename + "." + extension;
				}

				selectedFile = new File(filename);
			}
		}

		return selectedFile;
	}
}
