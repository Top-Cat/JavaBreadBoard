package integratedCircuits.memory;

import java.util.StringTokenizer;
import java.util.Vector;

public class MemSpace {
	private byte[] data;
	private int length;

	public MemSpace(int x) {
		this.data = new byte[x];
		this.length = x;
		for (int i = 0; i < x; i++) {
			this.setDatum(i, 0);

		}
	}

	private String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = new String("");
		while (st.hasMoreElements()) {
			t = t + st.nextElement();
		}
		return t;
	}

	public int getDatum(int addr) {
		return this.data[addr];
	}

	public void setDatum(int addr, int val) {
		this.data[addr] = (byte) val;
	}

	public int getLength() {
		return this.length;
	}

	public String getAll() {
		Vector<String> truthTable = new Vector<String>();
		for (int i = 0; i < this.length; i++) {
			truthTable.add(Integer.valueOf(this.getDatum(i)).toString());
		}

		String data = new String(truthTable.toString());
		data = this.removeSpaces(data.replace('[', ' '));
		data = this.removeSpaces(data.replace(']', ' '));
		return data;
	}

	public boolean setAll(String input) {
		int len = input.length();
		if (len > this.getLength()) {
			System.err.println("Input too large, truncating");
			len = this.getLength();
		}

		if (len > 0) {
			for (int i = 0; i < len; i++) {
				this.setDatum(i, input.charAt(i));
			}
			return true;
		}

		System.err.println("Empty Input");

		return false;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.memory.MemSpace JD-Core Version:
 * 0.6.2
 */
