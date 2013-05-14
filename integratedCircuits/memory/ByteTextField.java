package integratedCircuits.memory;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ByteTextField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Toolkit toolkit;
	private NumberFormat ByteFormat;
	private ByteNumberDocument ByteNumDoc;
	public int number;

	public ByteTextField(int columns) {
		super(columns);
		this.toolkit = Toolkit.getDefaultToolkit();
		this.ByteFormat = NumberFormat.getNumberInstance(Locale.UK);
		this.ByteFormat.setParseIntegerOnly(false);
	}

	public void setHex() {
		this.ByteNumDoc.setHex();
	}

	public void setDecimal() {
		this.ByteNumDoc.setDecimal();
	}

	@Override
	protected Document createDefaultModel() {
		this.ByteNumDoc = new ByteNumberDocument();
		return this.ByteNumDoc;
	}

	protected class ByteNumberDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String type = "Hex";

		protected ByteNumberDocument() {}

		public void setHex() {
			this.type = "Hex";
		}

		public void setDecimal() {
			this.type = "Decimal";
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			char[] source = str.toCharArray();
			char[] result = new char[source.length];

			int j = 0;

			String currentText = this.getText(0, this.getLength());
			currentText.toCharArray();

			for (int i = 0; i < result.length; i++) {
				if (this.type == "Hex") {
					if (Character.isDigit(source[i]) || source[i] >= 'A' && source[i] <= 'F' || source[i] >= 'a' && source[i] <= 'f') {
						result[j++] = source[i];
					} else {
						ByteTextField.this.toolkit.beep();
						System.err.println("insertString: " + source[i]);
					}
				} else if (this.type == "Decimal") {
					if (Character.isDigit(source[i])) {
						result[j++] = source[i];
					} else {
						ByteTextField.this.toolkit.beep();
						System.err.println("insertString: " + source[i]);
					}
				}
			}

			super.insertString(offs, new String(result, 0, j), a);
		}
	}
}
