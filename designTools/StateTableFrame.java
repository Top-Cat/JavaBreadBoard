/*     */ package designTools;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
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
/*     */ import javax.swing.JList;
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
/*     */ public class StateTableFrame extends JFrame
/*     */ {
/*     */   JPanel contentPane;
/*     */   private static final int OUT_STATE_MAX = 16;
/*     */   private static final int IN_MAX = 8;
/*  28 */   private boolean inApplet = false;
/*     */ 
/*  33 */   StandardMenu jMenuBar1 = new StandardMenu();
/*  34 */   JMenuBar menuBar = this.jMenuBar1.getMenuBar();
/*  35 */   JScrollPane tableFrame = new JScrollPane();
/*     */   StateTable stateTable;
/*  37 */   ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
/*  38 */   JPanel statusBar = new JPanel();
/*  39 */   BorderLayout borderLayout1 = new BorderLayout();
/*  40 */   JLabel lblStatusBar = new JLabel();
/*  41 */   JPanel jPanel1 = new JPanel();
/*  42 */   JLabel lblStarting = new JLabel();
/*  43 */   JLabel lblStarting2 = new JLabel();
/*  44 */   JLabel lblStarting3 = new JLabel();
/*  45 */   JLabel lblStarting4 = new JLabel();
/*  46 */   JLabel lblStarting5 = new JLabel();
/*  47 */   JList lstStarting = new JList();
/*  48 */   JScrollPane scrStarting = new JScrollPane();
/*     */   private int inPinNumber;
/*     */   private int outPinNumber;
/*     */   private int stateNumber;
/*  53 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  54 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*     */ 
/*     */   public StateTableFrame()
/*     */   {
/*  59 */     enableEvents(64L);
/*     */     try {
/*  61 */       jbInit();
/*     */     }
/*     */     catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public StateTableFrame(boolean isApplet)
/*     */   {
/*  75 */     this.inApplet = isApplet;
/*     */     try {
/*  77 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  80 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void jbInit()
/*     */     throws Exception
/*     */   {
/*  87 */     this.contentPane = ((JPanel)getContentPane());
/*  88 */     this.contentPane.setLayout(this.gridBagLayout2);
/*     */ 
/*  91 */     setDefaultCloseOperation(0);
/*  92 */     setJMenuBar(this.menuBar);
/*     */ 
/*  94 */     setSize(new Dimension(759, 548));
/*  95 */     setTitle("State Table Editor");
/*     */ 
/*  99 */     JComboBox inPinBox = new JComboBox();
/* 100 */     inPinBox.addItem("0");
/* 101 */     JComboBox outPinBox = new JComboBox();
/* 102 */     JComboBox stateBox = new JComboBox();
/* 103 */     for (int i = 0; i < 16; i++) {
/* 104 */       if (i < 8) inPinBox.addItem(String.valueOf(i + 1));
/* 105 */       outPinBox.addItem(String.valueOf(i + 1));
/* 106 */       stateBox.addItem(String.valueOf(i + 1));
/*     */     }
/*     */ 
/* 111 */     JOptionPane.showOptionDialog(this, "Please state the number of input pins, output pins\nand states required\n ", "State Table Editor", 2, 3, null, new Object[] { new JLabel("Input Pins"), inPinBox, new JLabel("OutputPins"), outPinBox, new JLabel("States"), stateBox, "Continue" }, "1");
/*     */ 
/* 117 */     this.inPinNumber = Integer.parseInt((String)inPinBox.getSelectedItem());
/* 118 */     this.outPinNumber = Integer.parseInt((String)outPinBox.getSelectedItem());
/* 119 */     this.stateNumber = Integer.parseInt((String)stateBox.getSelectedItem());
/*     */ 
/* 122 */     this.stateTable = new StateTable(this.inPinNumber, this.outPinNumber, this.stateNumber);
/*     */ 
/* 125 */     Object[] states = new Object[this.stateNumber];
/* 126 */     for (int i = 0; i < this.stateNumber; i++) {
/* 127 */       states[i] = Integer.toString(i);
/*     */     }
/* 129 */     this.lstStarting.setListData(states);
/* 130 */     this.lstStarting.setBorder(null);
/* 131 */     this.lstStarting.setSelectedIndex(0);
/* 132 */     this.lstStarting.setSelectionMode(0);
/*     */ 
/* 135 */     this.tableFrame.setBorder(BorderFactory.createLoweredBevelBorder());
/* 136 */     this.statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
/* 137 */     this.statusBar.setLayout(this.borderLayout1);
/* 138 */     this.lblStatusBar.setText("Click on output pin cells to toggle values. Double-click column headers to change pin labels.");
/*     */ 
/* 140 */     this.jPanel1.setBorder(BorderFactory.createLoweredBevelBorder());
/* 141 */     this.jPanel1.setLayout(this.gridBagLayout1);
/* 142 */     this.lblStarting.setHorizontalAlignment(0);
/* 143 */     this.lblStarting.setText("Starting State");
/* 144 */     this.lblStarting2.setFont(new Font("Dialog", 0, 12));
/* 145 */     this.lblStarting2.setText("Please select the");
/* 146 */     this.lblStarting3.setText("initial state in");
/* 147 */     this.lblStarting3.setFont(new Font("Dialog", 0, 12));
/* 148 */     this.lblStarting4.setText("which the chip will");
/* 149 */     this.lblStarting4.setFont(new Font("Dialog", 0, 12));
/* 150 */     this.lblStarting4.setToolTipText("");
/* 151 */     this.lblStarting5.setText("start on power-up");
/* 152 */     this.lblStarting5.setFont(new Font("Dialog", 0, 12));
/* 153 */     this.scrStarting.setBorder(BorderFactory.createLoweredBevelBorder());
/* 154 */     this.contentPane.add(this.tableFrame, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(8, 5, 0, 0), 563, 371));
/*     */ 
/* 156 */     this.tableFrame.getViewport().add(this.stateTable, null);
/* 157 */     this.contentPane.add(this.propertiesPanel, new GridBagConstraints(0, 1, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(6, 5, 0, 12), 741, 99));
/*     */ 
/* 159 */     this.contentPane.add(this.statusBar, new GridBagConstraints(0, 2, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 5, 11, 12), 156, 2));
/*     */ 
/* 161 */     this.statusBar.add(this.lblStatusBar, "Center");
/* 162 */     this.contentPane.add(this.jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0D, 1.0D, 10, 1, new Insets(8, 9, 0, 12), 3, 7));
/*     */ 
/* 164 */     this.jPanel1.add(this.lblStarting3, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 25), 20, 0));
/*     */ 
/* 166 */     this.jPanel1.add(this.lblStarting5, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 9), 6, 0));
/*     */ 
/* 168 */     this.jPanel1.add(this.lblStarting2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(23, 11, 0, 16), 9, 0));
/*     */ 
/* 170 */     this.jPanel1.add(this.lblStarting4, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 11, 0, 9), 4, 0));
/*     */ 
/* 172 */     this.jPanel1.add(this.lblStarting, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(16, 11, 0, 9), 27, 0));
/*     */ 
/* 174 */     this.jPanel1.add(this.scrStarting, new GridBagConstraints(0, 5, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(19, 11, 108, 15), -155, 18));
/*     */ 
/* 176 */     this.scrStarting.getViewport().add(this.lstStarting, null);
/*     */ 
/* 179 */     this.jMenuBar1.getSaveMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 182 */         StateTableFrame.this.createChipFile();
/*     */       }
/*     */     });
/* 185 */     this.jMenuBar1.getExitMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 188 */         StateTableFrame.this.exitAction();
/*     */       }
/*     */     });
/* 191 */     this.jMenuBar1.getNewMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/*     */         try {
/* 195 */           if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this table?\nAll unsaved work will be lost", "New Table", 0, 1) == 0)
/*     */           {
/* 198 */             StateTableFrame.this.closeWindow();
/* 199 */             StateTableFrame newFrame = new StateTableFrame(StateTableFrame.this.inApplet);
/*     */ 
/* 201 */             Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 202 */             Dimension frameSize = newFrame.getSize();
/* 203 */             if (frameSize.height > screenSize.height) {
/* 204 */               frameSize.height = screenSize.height;
/*     */             }
/* 206 */             if (frameSize.width > screenSize.width) {
/* 207 */               frameSize.width = screenSize.width;
/*     */             }
/* 209 */             newFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 210 */             newFrame.setVisible(true);
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 214 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void createChipFile()
/*     */   {
/* 225 */     boolean errorOccured = false;
/* 226 */     PrintWriter writer = null;
/* 227 */     String inputPinList = "";
/* 228 */     String outputPinList = "";
/*     */ 
/* 235 */     File chipFile = FileOperations.getFileSelection("chp");
/* 236 */     if (chipFile == null) errorOccured = true;
/*     */ 
/* 239 */     if (!errorOccured) {
/*     */       try {
/* 241 */         writer = new PrintWriter(new FileWriter(chipFile));
/*     */       }
/*     */       catch (IOException ex) {
/* 244 */         JOptionPane.showMessageDialog(this, "Error: Could not open file " + chipFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);
/*     */ 
/* 248 */         errorOccured = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 253 */     if (!errorOccured) {
/* 254 */       writer.println("Chip Text=" + this.propertiesPanel.txtChipText.getText());
/* 255 */       writer.println("Description=" + this.propertiesPanel.txtDescription.getText());
/* 256 */       writer.println("Manufacturer=" + this.propertiesPanel.txtManufacturer.getText());
/* 257 */       if (this.propertiesPanel.radYes.isSelected()) {
/* 258 */         writer.println("Wide Chip=True");
/*     */       }
/*     */       else {
/* 261 */         writer.println("Wide Chip=False");
/*     */       }
/* 263 */       writer.println("Clocked Chip=True");
/* 264 */       for (int i = 0; i < this.inPinNumber; i++) {
/* 265 */         inputPinList = inputPinList + this.stateTable.getColumnName(i) + ";";
/*     */       }
/* 267 */       writer.println("Input Pins=" + inputPinList);
/* 268 */       for (int i = 0; i < this.outPinNumber; i++) {
/* 269 */         outputPinList = outputPinList + this.stateTable.getColumnName(i + this.inPinNumber + 1) + ";";
/*     */       }
/* 271 */       writer.println("Output Pins=" + outputPinList);
/* 272 */       writer.println("Number of States=" + String.valueOf(this.stateNumber));
/* 273 */       writer.println("Initial State=" + this.lstStarting.getSelectedValue());
/* 274 */       writer.println("++++++++++");
/*     */ 
/* 277 */       for (int i = 0; i < this.stateTable.getRowCount(); i++) {
/* 278 */         String stateTableRow = "";
/* 279 */         for (int j = 0; j < this.inPinNumber; j++) {
/* 280 */           stateTableRow = stateTableRow + this.stateTable.getValueAt(i, j);
/*     */         }
/* 282 */         stateTableRow = stateTableRow + ";" + this.stateTable.getValueAt(i, this.inPinNumber) + ";";
/* 283 */         for (int j = 0; j < this.outPinNumber; j++) {
/* 284 */           stateTableRow = stateTableRow + this.stateTable.getValueAt(i, j + this.inPinNumber + 1);
/*     */         }
/* 286 */         stateTableRow = stateTableRow + ";" + this.stateTable.getValueAt(i, this.stateTable.getColumnCount() - 1) + ";";
/* 287 */         Number delayN = (Number)this.propertiesPanel.txtDelay.getValue();
/* 288 */         long delay = delayN.longValue();
/* 289 */         if (delay < 0L) delay = 0L;
/* 290 */         stateTableRow = stateTableRow + String.valueOf(delay);
/* 291 */         writer.println(stateTableRow);
/*     */       }
/*     */ 
/* 294 */       writer.close();
/*     */     }
/*     */ 
/* 298 */     if (!errorOccured)
/* 299 */       if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the state table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1)
/*     */       {
/* 303 */         immediateExit();
/*     */       }
/*     */   }
/*     */ 
/*     */   private void exitAction()
/*     */   {
/* 309 */     int confirm = JOptionPane.showConfirmDialog(null, "Are you sure that you want to close?\nAll unsaved work will be lost", "Exit Confirmation", 0, 1);
/*     */ 
/* 314 */     if (confirm == 0)
/* 315 */       immediateExit();
/*     */   }
/*     */ 
/*     */   private void immediateExit()
/*     */   {
/* 321 */     if (!this.inApplet) {
/* 322 */       System.exit(0);
/*     */     }
/*     */     else
/* 325 */       closeWindow();
/*     */   }
/*     */ 
/*     */   private void closeWindow()
/*     */   {
/* 330 */     dispose();
/*     */   }
/*     */ 
/*     */   protected void processWindowEvent(WindowEvent e)
/*     */   {
/* 337 */     super.processWindowEvent(e);
/* 338 */     if (e.getID() == 201)
/* 339 */       exitAction();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.StateTableFrame
 * JD-Core Version:    0.6.2
 */