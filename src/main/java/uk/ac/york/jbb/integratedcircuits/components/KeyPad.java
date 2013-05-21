package uk.ac.york.jbb.integratedcircuits.components;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import uk.ac.york.jbb.jbreadboard.v1_00.ChipModel;

public class KeyPad extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Value = 0;
	private ChipModel chipModel;

	public KeyPad(ChipModel model) {
		this.chipModel = model;

		this.setLayout(new GridLayout(4, 4));

		JButton b0 = new JButton("0");
		b0.addActionListener(this);
		this.add(b0);

		JButton b1 = new JButton("1");
		b1.addActionListener(this);
		this.add(b1);

		JButton b2 = new JButton("2");
		b2.addActionListener(this);
		this.add(b2);

		JButton b3 = new JButton("3");
		b3.addActionListener(this);
		this.add(b3);

		JButton b4 = new JButton("4");
		b4.addActionListener(this);
		this.add(b4);

		JButton b5 = new JButton("5");
		b5.addActionListener(this);
		this.add(b5);

		JButton b6 = new JButton("6");
		b6.addActionListener(this);
		this.add(b6);

		JButton b7 = new JButton("7");
		b7.addActionListener(this);
		this.add(b7);

		JButton b8 = new JButton("8");
		b8.addActionListener(this);
		this.add(b8);

		JButton b9 = new JButton("9");
		b9.addActionListener(this);
		this.add(b9);

		JButton bA = new JButton("A");
		bA.addActionListener(this);
		this.add(bA);

		JButton bB = new JButton("B");
		bB.addActionListener(this);
		this.add(bB);

		JButton bC = new JButton("C");
		bC.addActionListener(this);
		this.add(bC);

		JButton bD = new JButton("D");
		bD.addActionListener(this);
		this.add(bD);

		JButton bE = new JButton("E");
		bE.addActionListener(this);
		this.add(bE);

		JButton bF = new JButton("F");
		bF.addActionListener(this);
		this.add(bF);
	}

	public int getValue() {
		return this.Value;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		switch (evt.getActionCommand().charAt(0)) {
			case '0':
				this.Value = 0;
				break;
			case '1':
				this.Value = 1;
				break;
			case '2':
				this.Value = 2;
				break;
			case '3':
				this.Value = 3;
				break;
			case '4':
				this.Value = 4;
				break;
			case '5':
				this.Value = 5;
				break;
			case '6':
				this.Value = 6;
				break;
			case '7':
				this.Value = 7;
				break;
			case '8':
				this.Value = 8;
				break;
			case '9':
				this.Value = 9;
				break;
			case 'A':
				this.Value = 10;
				break;
			case 'B':
				this.Value = 11;
				break;
			case 'C':
				this.Value = 12;
				break;
			case 'D':
				this.Value = 13;
				break;
			case 'E':
				this.Value = 14;
				break;
			case 'F':
				this.Value = 15;
				break;
			case ':':
			case ';':
			case '<':
			case '=':
			case '>':
			case '?':
			case '@':
			default:
				this.Value = 0;
		}
		this.chipModel.simulate();
	}
}
