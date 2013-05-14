package designTools;

import java.awt.Color;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TruthTable extends StateTable {
	public TruthTable(int inCount, int outCount) {
		super(inCount, outCount, 0);
	}

	@Override
	protected void renderCells() {
		TableColumnModel columnModel = this.getColumnModel();

		DefaultTableCellRenderer blueCellRenderer = new DefaultTableCellRenderer();
		blueCellRenderer.setBackground(new Color(228, 228, 255));
		DefaultTableCellRenderer greenCellRenderer = new DefaultTableCellRenderer();
		greenCellRenderer.setBackground(new Color(228, 255, 228));

		for (int i = 0; i < this.getDigitalTableModel().getInPinCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			column.setCellRenderer(blueCellRenderer);
		}
		for (int i = this.getDigitalTableModel().getInPinCount(); i < this.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			column.setCellRenderer(greenCellRenderer);
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.TruthTable JD-Core Version: 0.6.2
 */
