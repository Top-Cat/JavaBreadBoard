/*    */ package designTools;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.table.DefaultTableCellRenderer;
/*    */ import javax.swing.table.TableColumn;
/*    */ import javax.swing.table.TableColumnModel;
/*    */ 
/*    */ public class TruthTable extends StateTable
/*    */ {
/*    */   public TruthTable(int inCount, int outCount)
/*    */   {
/* 23 */     super(inCount, outCount, 0);
/*    */   }
/*    */ 
/*    */   protected void renderCells()
/*    */   {
/* 31 */     TableColumnModel columnModel = getColumnModel();
/*    */ 
/* 36 */     DefaultTableCellRenderer blueCellRenderer = new DefaultTableCellRenderer();
/* 37 */     blueCellRenderer.setBackground(new Color(228, 228, 255));
/* 38 */     DefaultTableCellRenderer greenCellRenderer = new DefaultTableCellRenderer();
/* 39 */     greenCellRenderer.setBackground(new Color(228, 255, 228));
/*    */ 
/* 42 */     for (int i = 0; i < getDigitalTableModel().getInPinCount(); i++) {
/* 43 */       TableColumn column = columnModel.getColumn(i);
/* 44 */       column.setPreferredWidth(50);
/* 45 */       column.setCellRenderer(blueCellRenderer);
/*    */     }
/* 47 */     for (int i = getDigitalTableModel().getInPinCount(); i < getColumnCount(); i++) {
/* 48 */       TableColumn column = columnModel.getColumn(i);
/* 49 */       column.setPreferredWidth(50);
/* 50 */       column.setCellRenderer(greenCellRenderer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.TruthTable
 * JD-Core Version:    0.6.2
 */