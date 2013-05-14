package designTools;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;

public class HTMLViewer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane = new JScrollPane();
	JEditorPane htmlPane = new JEditorPane();
	JButton cmdClose = new JButton();
	JButton cmdBack = new JButton();

	private Stack<String> pageStack = new Stack<String>();
	GridBagLayout gridBagLayout1 = new GridBagLayout();

	public HTMLViewer(URL page, String title) {
		try {
			this.jbInit();
			this.displayPage(page);
			this.setTitle(title);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(this.gridBagLayout1);
		this.htmlPane.addHyperlinkListener(new HTMLViewer_htmlPane_hyperlinkAdapter(this));
		this.htmlPane.setEditable(false);
		this.htmlPane.setContentType("text/html");
		this.cmdClose.setText("Close");
		this.cmdClose.addMouseListener(new HTMLViewer_cmdClose_mouseAdapter(this));
		this.cmdBack.setText("Back");
		this.cmdBack.addMouseListener(new HTMLViewer_cmdBack_mouseAdapter(this));
		this.setSize(new Dimension(628, 440));
		this.getContentPane().add(this.scrollPane, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 8, 0, 11), 0, 353));

		this.getContentPane().add(this.cmdClose, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(11, 9, 17, 11), 14, 0));

		this.getContentPane().add(this.cmdBack, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(11, 441, 17, 0), 20, 0));

		this.scrollPane.getViewport().add(this.htmlPane, null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	void cmdClose_mouseClicked(MouseEvent e) {
		this.dispose();
	}

	public void displayPage(URL page) {
		try {
			this.pageStack.push(page.toString());

			this.htmlPane.setPage(page);
		} catch (IOException ex) {
			this.htmlPane.setText("Exception: " + ex);
		}
	}

	void htmlPane_hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			this.displayPage(e.getURL());
		}
	}

	void cmdBack_mouseClicked(MouseEvent e) {
		if (this.pageStack.size() > 1) {
			try {
				this.pageStack.pop();

				String pageString = (String) this.pageStack.peek();
				this.htmlPane.setPage(pageString);
			} catch (IOException ex) {
				this.htmlPane.setText("Exception: " + ex);
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.HTMLViewer JD-Core Version: 0.6.2
 */
