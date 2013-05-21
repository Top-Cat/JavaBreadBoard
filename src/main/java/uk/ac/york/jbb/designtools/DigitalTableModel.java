package uk.ac.york.jbb.designtools;

import javax.swing.table.AbstractTableModel;

public class DigitalTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object[][] cells;
	private String[] columnNames;
	private int rowCount;
	private int columnCount;
	private int stateColumn;
	private int inPinCount;
	private boolean stateTable = false;

	public DigitalTableModel(int inPinNumber, int outPinNumber, int stateNumber) {
		if (stateNumber > 0) {
			this.stateTable = true;
		} else {
			stateNumber = 1;
		}

		this.rowCount = (int) Math.pow(2.0D, inPinNumber) * stateNumber;
		if (this.stateTable) {
			this.columnCount = inPinNumber + outPinNumber + 2;
		} else {
			this.columnCount = inPinNumber + outPinNumber;
		}

		this.inPinCount = inPinNumber;

		this.columnNames = new String[this.columnCount];
		int index = 0;
		for (index = 0; index < inPinNumber; index++) {
			this.columnNames[index] = "in" + String.valueOf(index + 1);
		}
		if (this.stateTable) {
			this.stateColumn = index;
			this.columnNames[index] = "Initial State";
		} else {
			this.stateColumn = -1;
			index -= 1;
		}
		for (index += 1; index < this.columnCount - 1; index++) {
			if (this.stateTable) {
				this.columnNames[index] = "out" + String.valueOf(index - this.stateColumn);
			} else {
				this.columnNames[index] = "out" + String.valueOf(index - inPinNumber + 1);
			}
		}
		if (this.stateTable) {
			this.columnNames[index] = "Next State";
		} else {
			this.columnNames[index] = "out" + String.valueOf(index - inPinNumber + 1);
		}

		this.cells = new Object[this.rowCount][this.columnCount];
		if (this.stateTable) {
			for (int i = 0; i < this.rowCount; i++) {
				this.cells[i][this.stateColumn] = String.valueOf(i % stateNumber);
			}
			for (int i = this.stateColumn - 1; i >= 0; i--) {
				int count = 0;
				String currentValue = "0";
				for (int j = 0; j < this.rowCount; j++) {
					if (count == stateNumber * Math.pow(2.0D, this.stateColumn - i - 1)) {
						if (currentValue.equals("0")) {
							currentValue = "1";
						} else {
							currentValue = "0";
						}
						count = 0;
					}
					this.cells[j][i] = currentValue;
					count++;
				}
			}
		} else {
			for (int i = 0; i < this.rowCount && inPinNumber > 0; i++) {
				this.cells[i][inPinNumber - 1] = String.valueOf(i % 2);
			}
			for (int i = inPinNumber - 2; i >= 0; i--) {
				int count = 0;
				String currentValue = "0";
				for (int j = 0; j < this.rowCount; j++) {
					if (count == Math.pow(2.0D, inPinNumber - i - 1)) {
						if (currentValue.equals("0")) {
							currentValue = "1";
						} else {
							currentValue = "0";
						}
						count = 0;
					}
					this.cells[j][i] = currentValue;
					count++;
				}
			}

		}

		if (this.stateTable) {
			for (int i = this.stateColumn + 1; i < this.columnCount; i++) {
				for (int j = 0; j < this.rowCount; j++) {
					this.cells[j][i] = "0";
				}
			}
		} else {
			for (int i = inPinNumber; i < this.columnCount; i++) {
				for (int j = 0; j < this.rowCount; j++) {
					this.cells[j][i] = "0";
				}
			}
		}
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return this.stateTable && c == this.getNextStateColumn();
	}

	@Override
	public void setValueAt(Object value, int r, int c) {
		this.cells[r][c] = value;
	}

	public void selectCell(int r, int c) {
		if (this.stateTable && c > this.stateColumn || !this.stateTable && c >= this.inPinCount) {
			if (this.cells[r][c].equals("0")) {
				this.cells[r][c] = "1";
			} else {
				this.cells[r][c] = "0";
			}
		}
	}

	public void setColumnHeader(int c, String label) {
		this.columnNames[c] = label;
	}

	public int getNextStateColumn() {
		if (this.stateTable) {
			return this.getColumnCount() - 1;
		}
		return -1;
	}

	public int getInitialStateColumn() {
		return this.stateColumn;
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public int getColumnCount() {
		return this.columnCount;
	}

	public int getInPinCount() {
		return this.inPinCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.cells[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int c) {
		return this.columnNames[c];
	}
}
