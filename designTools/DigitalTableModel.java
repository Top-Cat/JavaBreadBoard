/*     */ package designTools;
/*     */ 
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ public class DigitalTableModel extends AbstractTableModel
/*     */ {
/*     */   private Object[][] cells;
/*     */   private String[] columnNames;
/*     */   private int rowCount;
/*     */   private int columnCount;
/*     */   private int stateColumn;
/*     */   private int inPinCount;
/*  22 */   private boolean stateTable = false;
/*     */ 
/*     */   public DigitalTableModel(int inPinNumber, int outPinNumber, int stateNumber)
/*     */   {
/*  31 */     if (stateNumber > 0) {
/*  32 */       this.stateTable = true;
/*     */     }
/*     */     else {
/*  35 */       stateNumber = 1;
/*     */     }
/*     */ 
/*  39 */     this.rowCount = ((int)Math.pow(2.0D, inPinNumber) * stateNumber);
/*  40 */     if (this.stateTable) {
/*  41 */       this.columnCount = (inPinNumber + outPinNumber + 2);
/*     */     }
/*     */     else {
/*  44 */       this.columnCount = (inPinNumber + outPinNumber);
/*     */     }
/*     */ 
/*  47 */     this.inPinCount = inPinNumber;
/*     */ 
/*  51 */     this.columnNames = new String[this.columnCount];
/*  52 */     int index = 0;
/*  53 */     for (index = 0; index < inPinNumber; index++) {
/*  54 */       this.columnNames[index] = ("in" + String.valueOf(index + 1));
/*     */     }
/*  56 */     if (this.stateTable) {
/*  57 */       this.stateColumn = index;
/*  58 */       this.columnNames[index] = "Initial State";
/*     */     }
/*     */     else {
/*  61 */       this.stateColumn = -1;
/*  62 */       index -= 1;
/*     */     }
/*  64 */     for (index += 1; index < this.columnCount - 1; index++) {
/*  65 */       if (this.stateTable) this.columnNames[index] = ("out" + String.valueOf(index - this.stateColumn)); else
/*  66 */         this.columnNames[index] = ("out" + String.valueOf(index - inPinNumber + 1));
/*     */     }
/*  68 */     if (this.stateTable) {
/*  69 */       this.columnNames[index] = "Next State";
/*     */     }
/*     */     else {
/*  72 */       this.columnNames[index] = ("out" + String.valueOf(index - inPinNumber + 1));
/*     */     }
/*     */ 
/*  77 */     this.cells = new Object[this.rowCount][this.columnCount];
/*  78 */     if (this.stateTable) {
/*  79 */       for (int i = 0; i < this.rowCount; i++) {
/*  80 */         this.cells[i][this.stateColumn] = String.valueOf(i % stateNumber);
/*     */       }
/*  82 */       for (int i = this.stateColumn - 1; i >= 0; i--) {
/*  83 */         int count = 0;
/*  84 */         String currentValue = "0";
/*  85 */         for (int j = 0; j < this.rowCount; j++) {
/*  86 */           if (count == stateNumber * Math.pow(2.0D, this.stateColumn - i - 1)) {
/*  87 */             if (currentValue.equals("0")) currentValue = "1"; else
/*  88 */               currentValue = "0";
/*  89 */             count = 0;
/*     */           }
/*  91 */           this.cells[j][i] = currentValue;
/*  92 */           count++;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*  97 */       for (int i = 0; (i < this.rowCount) && (inPinNumber > 0); i++) {
/*  98 */         this.cells[i][(inPinNumber - 1)] = String.valueOf(i % 2);
/*     */       }
/* 100 */       for (int i = inPinNumber - 2; i >= 0; i--) {
/* 101 */         int count = 0;
/* 102 */         String currentValue = "0";
/* 103 */         for (int j = 0; j < this.rowCount; j++) {
/* 104 */           if (count == Math.pow(2.0D, inPinNumber - i - 1)) {
/* 105 */             if (currentValue.equals("0")) currentValue = "1"; else
/* 106 */               currentValue = "0";
/* 107 */             count = 0;
/*     */           }
/* 109 */           this.cells[j][i] = currentValue;
/* 110 */           count++;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 116 */     if (this.stateTable) {
/* 117 */       for (int i = this.stateColumn + 1; i < this.columnCount; i++) {
/* 118 */         for (int j = 0; j < this.rowCount; j++) {
/* 119 */           this.cells[j][i] = "0";
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/* 124 */       for (int i = inPinNumber; i < this.columnCount; i++)
/* 125 */         for (int j = 0; j < this.rowCount; j++)
/* 126 */           this.cells[j][i] = "0";
/*     */   }
/*     */ 
/*     */   public boolean isCellEditable(int r, int c)
/*     */   {
/* 138 */     return (this.stateTable) && (c == getNextStateColumn());
/*     */   }
/*     */ 
/*     */   public void setValueAt(Object value, int r, int c) {
/* 142 */     this.cells[r][c] = value;
/*     */   }
/*     */ 
/*     */   public void selectCell(int r, int c)
/*     */   {
/* 149 */     if (((this.stateTable) && (c > this.stateColumn)) || ((!this.stateTable) && (c >= this.inPinCount)))
/* 150 */       if (this.cells[r][c].equals("0")) this.cells[r][c] = "1"; else
/* 151 */         this.cells[r][c] = "0";
/*     */   }
/*     */ 
/*     */   public void setColumnHeader(int c, String label)
/*     */   {
/* 160 */     this.columnNames[c] = label;
/*     */   }
/*     */ 
/*     */   public int getNextStateColumn()
/*     */   {
/* 165 */     if (this.stateTable) return getColumnCount() - 1;
/* 166 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getInitialStateColumn() {
/* 170 */     return this.stateColumn;
/*     */   }
/*     */ 
/*     */   public int getRowCount() {
/* 174 */     return this.rowCount;
/*     */   }
/*     */ 
/*     */   public int getColumnCount() {
/* 178 */     return this.columnCount;
/*     */   }
/*     */ 
/*     */   public int getInPinCount() {
/* 182 */     return this.inPinCount;
/*     */   }
/*     */   public Object getValueAt(int rowIndex, int columnIndex) {
/* 185 */     return this.cells[rowIndex][columnIndex];
/*     */   }
/*     */   public String getColumnName(int c) {
/* 188 */     return this.columnNames[c];
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.DigitalTableModel
 * JD-Core Version:    0.6.2
 */