package designTools;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class StateTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DigitalTableModel stateTableModel;
	private int stateCount;
	private int inPinCount;

	public StateTable(int inPinNumber, int outPinNumber, int stateNumber) {
		this.stateTableModel = new DigitalTableModel(inPinNumber, outPinNumber, stateNumber);

		this.setModel(this.stateTableModel);

		this.setRowSelectionAllowed(false);

		this.stateCount = stateNumber;
		this.inPinCount = inPinNumber;

		this.renderCells();

		this.setAutoResizeMode(0);

		this.addMouseListener(new StateTable_stateTable_mouseAdapter(this));

		this.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					int tableColumn = StateTable.this.columnAtPoint(event.getPoint());

					if (tableColumn != StateTable.this.stateTableModel.getInitialStateColumn() && tableColumn != StateTable.this.stateTableModel.getNextStateColumn()) {
						String pinLabel = JOptionPane.showInputDialog(null, "Please provide an alternate label for this pin or leave the label as it is", StateTable.this.stateTableModel.getColumnName(tableColumn));

						if (pinLabel != null) {
							StateTable.this.stateTableModel.setColumnHeader(tableColumn, pinLabel);
							TableColumnModel columnModel = StateTable.this.getColumnModel();
							TableColumn column = columnModel.getColumn(tableColumn);
							column.setHeaderValue(StateTable.this.stateTableModel.getColumnName(tableColumn));
							StateTable.this.getTableHeader().repaint();
						}
					}
				}
			}
		});
	}

	protected void renderCells() {
		JComboBox<String> stateSelector = new JComboBox<String>();
		for (int i = 0; i < this.stateCount; i++) {
			stateSelector.addItem(String.valueOf(i));
		}
		TableCellEditor stateEditor = new DefaultCellEditor(stateSelector);
		TableColumnModel columnModel = this.getColumnModel();
		TableColumn column = columnModel.getColumn(this.stateTableModel.getNextStateColumn());
		column.setCellEditor(stateEditor);

		DefaultTableCellRenderer blueCellRenderer = new DefaultTableCellRenderer();
		blueCellRenderer.setBackground(new Color(228, 228, 255));
		DefaultTableCellRenderer greenCellRenderer = new DefaultTableCellRenderer();
		greenCellRenderer.setBackground(new Color(228, 255, 228));
		DefaultTableCellRenderer yellowCellRenderer = new DefaultTableCellRenderer();
		yellowCellRenderer.setBackground(new Color(255, 255, 228));

		for (int i = 0; i < this.inPinCount; i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			column.setCellRenderer(blueCellRenderer);
		}
		for (int i = this.stateTableModel.getInitialStateColumn() + 1; i < this.stateTableModel.getNextStateColumn(); i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			column.setCellRenderer(greenCellRenderer);
		}
		column = columnModel.getColumn(this.stateTableModel.getInitialStateColumn());
		column.setCellRenderer(yellowCellRenderer);
	}

	void stateTable_mouseClicked(MouseEvent e) {
		if (e.getButton() == 1) {
			this.stateTableModel.selectCell(this.rowAtPoint(e.getPoint()), this.columnAtPoint(e.getPoint()));
			this.repaint();
		}
	}

	protected DigitalTableModel getDigitalTableModel() {
		return this.stateTableModel;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.StateTable JD-Core Version: 0.6.2
 */
