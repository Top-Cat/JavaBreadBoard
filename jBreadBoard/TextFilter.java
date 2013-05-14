package jBreadBoard;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TextFilter extends FileFilter {
	private String extension = new String("");

	public TextFilter() {}

	public TextFilter(String extension) {
		this.extension = extension;
	}

	@Override
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith("." + this.extension) || f.isDirectory();
	}

	@Override
	public String getDescription() {
		return this.extension + " Files";
	}
}
