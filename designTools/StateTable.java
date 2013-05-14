/*     */ package designTools;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class StateTable extends JTable
/*     */ {
/*     */   private DigitalTableModel stateTableModel;
/*     */   private int stateCount;
/*     */   private int inPinCount;
/*     */ 
/*     */   public StateTable(int inPinNumber, int outPinNumber, int stateNumber)
/*     */   {
/*  33 */     this.stateTableModel = new DigitalTableModel(inPinNumber, outPinNumber, stateNumber);
/*     */ 
/*  35 */     setModel(this.stateTableModel);
/*     */ 
/*  37 */     setRowSelectionAllowed(false);
/*     */ 
/*  39 */     this.stateCount = stateNumber;
/*  40 */     this.inPinCount = inPinNumber;
/*     */ 
/*  42 */     renderCells();
/*     */ 
/*  46 */     setAutoResizeMode(0);
/*     */ 
/*  49 */     addMouseListener(new StateTable_stateTable_mouseAdapter(this));
/*     */ 
/*  54 */     getTableHeader().addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseClicked(MouseEvent event)
/*     */       {
/*  58 */         if (event.getClickCount() >= 2)
/*     */         {
/*  60 */           int tableColumn = StateTable.this.columnAtPoint(event.getPoint());
/*     */ 
/*  63 */           if ((tableColumn != StateTable.this.stateTableModel.getInitialStateColumn()) && (tableColumn != StateTable.this.stateTableModel.getNextStateColumn()))
/*     */           {
/*  66 */             String pinLabel = JOptionPane.showInputDialog(null, "Please provide an alternate label for this pin or leave the label as it is", StateTable.this.stateTableModel.getColumnName(tableColumn));
/*     */ 
/*  69 */             if (pinLabel != null) {
/*  70 */               StateTable.this.stateTableModel.setColumnHeader(tableColumn, pinLabel);
/*  71 */               TableColumnModel columnModel = StateTable.this.getColumnModel();
/*  72 */               TableColumn column = columnModel.getColumn(tableColumn);
/*  73 */               column.setHeaderValue(StateTable.this.stateTableModel.getColumnName(tableColumn));
/*  74 */               StateTable.this.getTableHeader().repaint();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void renderCells()
/*     */   {
/*  86 */     JComboBox stateSelector = new JComboBox();
/*  87 */     for (int i = 0; i < this.stateCount; i++) {
/*  88 */       stateSelector.addItem(String.valueOf(i));
/*     */     }
/*  90 */     TableCellEditor stateEditor = new DefaultCellEditor(stateSelector);
/*  91 */     TableColumnModel columnModel = getColumnModel();
/*  92 */     TableColumn column = columnModel.getColumn(this.stateTableModel.getNextStateColumn());
/*  93 */     column.setCellEditor(stateEditor);
/*     */ 
/*  97 */     DefaultTableCellRenderer blueCellRenderer = new DefaultTableCellRenderer();
/*  98 */     blueCellRenderer.setBackground(new Color(228, 228, 255));
/*  99 */     DefaultTableCellRenderer greenCellRenderer = new DefaultTableCellRenderer();
/* 100 */     greenCellRenderer.setBackground(new Color(228, 255, 228));
/* 101 */     DefaultTableCellRenderer yellowCellRenderer = new DefaultTableCellRenderer();
/* 102 */     yellowCellRenderer.setBackground(new Color(255, 255, 228));
/*     */ 
/* 105 */     for (int i = 0; i < this.inPinCount; i++) {
/* 106 */       column = columnModel.getColumn(i);
/* 107 */       column.setPreferredWidth(50);
/* 108 */       column.setCellRenderer(blueCellRenderer);
/*     */     }
/* 110 */     for (int i = this.stateTableModel.getInitialStateColumn() + 1; i < this.stateTableModel.getNextStateColumn(); i++) {
/* 111 */       column = columnModel.getColumn(i);
/* 112 */       column.setPreferredWidth(50);
/* 113 */       column.setCellRenderer(greenCellRenderer);
/*     */     }
/* 115 */     column = columnModel.getColumn(this.stateTableModel.getInitialStateColumn());
/* 116 */     column.setCellRenderer(yellowCellRenderer);
/*     */   }
/*     */ 
/*     */   void stateTable_mouseClicked(MouseEvent e)
/*     */   {
/* 125 */     if (e.getButton() == 1) {
/* 126 */       this.stateTableModel.selectCell(rowAtPoint(e.getPoint()), columnAtPoint(e.getPoint()));
/* 127 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected DigitalTableModel getDigitalTableModel()
/*     */   {
/* 133 */     return this.stateTableModel;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.StateTable
 * JD-Core Version:    0.6.2
 */