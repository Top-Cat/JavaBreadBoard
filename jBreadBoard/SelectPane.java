package jBreadBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SelectPane extends JPanel {
	private JBreadBoard jBreadBoard;
	private CircuitSelection selection;
	private ImageIcon imageicon;
	private JPanel imagepanel = new JPanel(new BorderLayout());
	private JTextArea infotext = new JTextArea();

	public void select(CircuitSelection c) {
		this.selection = c;
		if (c == null) {
			this.imageicon = null;
			this.remove(this.infotext);
		} else {
			this.imageicon = this.jBreadBoard.getImageIcon(c.getDiagram());
			this.remove(this.infotext);
			this.infotext = new JTextArea(c.getInfo(), 0, 10);
			this.infotext.setOpaque(false);
			this.infotext.setMargin(new Insets(0, 0, 0, 0));
			this.infotext.setEditable(false);
			this.infotext.setLineWrap(true);
			this.infotext.setWrapStyleWord(true);
			this.infotext.setRows(this.infotext.getLineCount() + 2);
			this.infotext.setPreferredSize(new Dimension(0, 0));
			this.add(this.infotext, "Center");
		}
		this.revalidate();
		this.repaint();
	}

	public SelectPane(JBreadBoard j) {
		this.jBreadBoard = j;

		this.setLayout(new BorderLayout());

		this.setBorder(BorderFactory.createEtchedBorder());

		this.imagepanel.setBackground(Color.white);

		JComponent c = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (SelectPane.this.selection == null) {
					g.drawString("Nothing Selected", 50, 100);
				} else if (SelectPane.this.selection instanceof Wire) {
					g.drawString("Wire Selected", 10, 70);
				} else if (SelectPane.this.selection instanceof Probe) {
					g.drawString("Probe Selected", 10, 70);
				} else if (SelectPane.this.imageicon != null) {
					SelectPane.this.imageicon.paintIcon(this, g, (this.getWidth() - SelectPane.this.imageicon.getIconWidth()) / 2, (this.getHeight() - SelectPane.this.imageicon.getIconHeight()) / 2);

				}
			}
		};
		c.setPreferredSize(new Dimension(200, 210));
		c.setMaximumSize(new Dimension(200, 210));

		c.setBorder(BorderFactory.createEtchedBorder());

		this.imagepanel.add(c, "Center");

		this.add(this.imagepanel, "North");
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.SelectPane JD-Core Version: 0.6.2
 */
