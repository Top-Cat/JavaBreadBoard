/*     */ package designTools;
/*     */ 
/*     */ import jBreadBoard.JBreadBoard;
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JViewport;
/*     */ 
/*     */ public class TruthTableFrame extends JFrame
/*     */ {
/*     */   private JBreadBoard jBreadBoard;
/*  23 */   public static String defaultchip = "integratedCircuits.ttl.logic.Gen7400";
/*     */   JPanel contentPane;
/*     */   private static final int OUT_MAX = 16;
/*     */   private static final int IN_MAX = 8;
/*     */   private int inPinNumber;
/*     */   private int outPinNumber;
/*  33 */   private boolean inApplet = false;
/*     */ 
/*  38 */   StandardMenu jMenuBar1 = new StandardMenu();
/*  39 */   JMenuBar menuBar = this.jMenuBar1.getMenuBar();
/*  40 */   JPanel jPanel1 = new JPanel();
/*  41 */   BorderLayout borderLayout1 = new BorderLayout();
/*  42 */   BorderLayout borderLayout2 = new BorderLayout();
/*  43 */   JScrollPane tableFrame = new JScrollPane();
/*  44 */   ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
/*     */   StateTable truthTable;
/*  46 */   JPanel jPanel2 = new JPanel();
/*  47 */   JLabel lblStatus = new JLabel();
/*  48 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  49 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*     */ 
/*     */   public TruthTableFrame()
/*     */   {
/*     */     try {
/*  54 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  57 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public TruthTableFrame(boolean isApplet)
/*     */   {
/*  68 */     this.inApplet = isApplet;
/*     */     try {
/*  70 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  73 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   void jbInit() throws Exception
/*     */   {
/*  79 */     this.contentPane = ((JPanel)getContentPane());
/*  80 */     this.contentPane.setLayout(this.gridBagLayout2);
/*     */ 
/*  83 */     setDefaultCloseOperation(0);
/*  84 */     setJMenuBar(this.menuBar);
/*     */ 
/*  86 */     setSize(new Dimension(759, 553));
/*  87 */     setTitle("Truth Table Editor");
/*     */ 
/*  91 */     JComboBox inPinBox = new JComboBox();
/*  92 */     inPinBox.addItem("0");
/*  93 */     JComboBox outPinBox = new JComboBox();
/*  94 */     for (int i = 0; i < 16; i++) {
/*  95 */       outPinBox.addItem(String.valueOf(i + 1));
/*     */     }
/*  97 */     for (int i = 0; i < 8; i++) {
/*  98 */       inPinBox.addItem(String.valueOf(i + 1));
/*     */     }
/*     */ 
/* 103 */     JOptionPane.showOptionDialog(this, "Please state the number of input pins and output pins required\n ", "Truth Table Editor", 2, 3, null, new Object[] { new JLabel("Input Pins"), inPinBox, new JLabel("OutputPins"), outPinBox, "Continue" }, "1");
/*     */ 
/* 109 */     this.inPinNumber = Integer.parseInt((String)inPinBox.getSelectedItem());
/* 110 */     this.outPinNumber = Integer.parseInt((String)outPinBox.getSelectedItem());
/*     */ 
/* 113 */     this.truthTable = new TruthTable(this.inPinNumber, this.outPinNumber);
/*     */ 
/* 117 */     this.jPanel1.setBorder(null);
/* 118 */     this.jPanel1.setLayout(this.borderLayout2);
/* 119 */     this.tableFrame.setBorder(BorderFactory.createLoweredBevelBorder());
/* 120 */     this.propertiesPanel.setBorder(BorderFactory.createLoweredBevelBorder());
/* 121 */     this.jPanel2.setBorder(BorderFactory.createLoweredBevelBorder());
/* 122 */     this.jPanel2.setLayout(this.gridBagLayout1);
/* 123 */     this.lblStatus.setText("Click on output pin cells to toggle values. Double-click column headers to change pin labels.");
/*     */ 
/* 125 */     this.contentPane.add(this.propertiesPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 6, 0, 11), 741, 99));
/*     */ 
/* 127 */     this.contentPane.add(this.jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 6, 0, 11), 288, -17));
/*     */ 
/* 129 */     this.jPanel1.add(this.tableFrame, "Center");
/* 130 */     this.contentPane.add(this.jPanel2, new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 6, 14, 11), 0, 0));
/*     */ 
/* 132 */     this.jPanel2.add(this.lblStatus, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 1, 0), 156, 0));
/*     */ 
/* 134 */     this.tableFrame.getViewport().add(this.truthTable, null);
/*     */ 
/* 137 */     this.jMenuBar1.getSaveMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 140 */         TruthTableFrame.this.createChipFile();
/*     */       }
/*     */     });
/* 143 */     this.jMenuBar1.getExitMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 146 */         TruthTableFrame.this.exitAction();
/*     */       }
/*     */     });
/* 149 */     this.jMenuBar1.getNewMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/*     */         try {
/* 153 */           if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this table?\nAll unsaved work will be lost", "New Table", 0, 1) == 0)
/*     */           {
/* 156 */             TruthTableFrame.this.closeWindow();
/* 157 */             TruthTableFrame newFrame = new TruthTableFrame(TruthTableFrame.this.inApplet);
/*     */ 
/* 159 */             Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 160 */             Dimension frameSize = newFrame.getSize();
/* 161 */             if (frameSize.height > screenSize.height) {
/* 162 */               frameSize.height = screenSize.height;
/*     */             }
/* 164 */             if (frameSize.width > screenSize.width) {
/* 165 */               frameSize.width = screenSize.width;
/*     */             }
/* 167 */             newFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 168 */             newFrame.setVisible(true);
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 172 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void exitAction()
/*     */   {
/* 181 */     int confirm = JOptionPane.showConfirmDialog(null, "Are you sure that you want to close?\nAll unsaved work will be lost", "Exit Confirmation", 0, 1);
/*     */ 
/* 186 */     if (confirm == 0)
/* 187 */       immediateExit();
/*     */   }
/*     */ 
/*     */   private void immediateExit()
/*     */   {
/* 193 */     if (!this.inApplet) {
/* 194 */       System.exit(0);
/*     */     }
/*     */     else
/* 197 */       closeWindow();
/*     */   }
/*     */ 
/*     */   private void closeWindow()
/*     */   {
/* 202 */     dispose();
/*     */   }
/*     */ 
/*     */   public static ChipModel getDefaultModel() {
/* 206 */     ChipModel model = JBreadBoard.getChipModel(defaultchip);
/* 207 */     return model;
/*     */   }
/*     */ 
/*     */   private void createChipFile()
/*     */   {
/* 214 */     boolean errorOccured = false;
/* 215 */     PrintWriter writer = null;
/* 216 */     String inputPinList = "";
/* 217 */     String outputPinList = "";
/*     */ 
/* 224 */     File chipFile = FileOperations.getFileSelection("chp");
/* 225 */     if (chipFile == null) errorOccured = true;
/*     */ 
/* 228 */     if (!errorOccured) {
/*     */       try {
/* 230 */         writer = new PrintWriter(new FileWriter(chipFile));
/*     */       }
/*     */       catch (IOException ex) {
/* 233 */         JOptionPane.showMessageDialog(this, "Error: Could not open file " + chipFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);
/*     */ 
/* 237 */         errorOccured = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 242 */     if (!errorOccured) {
/* 243 */       writer.println("Chip Text=" + this.propertiesPanel.txtChipText.getText());
/* 244 */       writer.println("Description=" + this.propertiesPanel.txtDescription.getText());
/* 245 */       writer.println("Manufacturer=" + this.propertiesPanel.txtManufacturer.getText());
/* 246 */       if (this.propertiesPanel.radYes.isSelected()) {
/* 247 */         writer.println("Wide Chip=True");
/*     */       }
/*     */       else {
/* 250 */         writer.println("Wide Chip=False");
/*     */       }
/* 252 */       writer.println("Clocked Chip=False");
/* 253 */       for (int i = 0; i < this.inPinNumber; i++) {
/* 254 */         inputPinList = inputPinList + this.truthTable.getColumnName(i) + ";";
/*     */       }
/* 256 */       writer.println("Input Pins=" + inputPinList);
/* 257 */       for (int i = 0; i < this.outPinNumber; i++) {
/* 258 */         outputPinList = outputPinList + this.truthTable.getColumnName(i + this.inPinNumber) + ";";
/*     */       }
/* 260 */       writer.println("Output Pins=" + outputPinList);
/* 261 */       writer.println("Number of States=1");
/* 262 */       writer.println("Initial State=0");
/* 263 */       writer.println("++++++++++");
/*     */ 
/* 266 */       for (int i = 0; i < this.truthTable.getRowCount(); i++) {
/* 267 */         String tableRow = "";
/* 268 */         for (int j = 0; j < this.inPinNumber; j++) {
/* 269 */           tableRow = tableRow + this.truthTable.getValueAt(i, j);
/*     */         }
/* 271 */         tableRow = tableRow + ";0;";
/* 272 */         for (int j = 0; j < this.outPinNumber; j++) {
/* 273 */           tableRow = tableRow + this.truthTable.getValueAt(i, j + this.inPinNumber);
/*     */         }
/* 275 */         tableRow = tableRow + ";0;";
/* 276 */         Number delayN = (Number)this.propertiesPanel.txtDelay.getValue();
/* 277 */         long delay = delayN.longValue();
/* 278 */         if (delay < 0L) delay = 0L;
/* 279 */         tableRow = tableRow + String.valueOf(delay);
/* 280 */         writer.println(tableRow);
/*     */       }
/*     */ 
/* 283 */       writer.close();
/*     */     }
/*     */ 
/* 287 */     if (!errorOccured)
/* 288 */       if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the truth table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1)
/*     */       {
/* 292 */         immediateExit();
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void processWindowEvent(WindowEvent e)
/*     */   {
/* 302 */     super.processWindowEvent(e);
/* 303 */     if (e.getID() == 201)
/* 304 */       exitAction();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.TruthTableFrame
 * JD-Core Version:    0.6.2
 */