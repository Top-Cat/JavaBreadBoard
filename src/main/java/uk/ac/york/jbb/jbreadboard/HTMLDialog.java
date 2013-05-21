package uk.ac.york.jbb.jbreadboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HTMLDialog {
	public static void addHTMLDialog(URL u, String title) {
		final JDialog dialog = new JDialog((JFrame) null, title, false);
		JEditorPane editorPane = new JEditorPane();
		JScrollPane scroller = new JScrollPane(editorPane);

		editorPane.setEditable(false);
		try {
			editorPane.setPage(u);

			dialog.getContentPane().setLayout(new BorderLayout());

			dialog.getContentPane().add(scroller, "Center");

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(1));
			dialog.getContentPane().add(buttonPane, "South");

			JButton closeButton = new JButton("Close");
			dialog.getRootPane().setDefaultButton(closeButton);
			buttonPane.add(closeButton);
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
			dialog.pack();
			dialog.setSize(new Dimension(600, 400));

			dialog.setVisible(true);
		} catch (IOException exception) {
			JOptionPane.showMessageDialog(null, "File Not Found", "Error HTMLDialog.001", 2);
		}
	}
}
