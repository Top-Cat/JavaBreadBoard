/*     */ package designTools;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
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
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.JViewport;
/*     */ 
/*     */ public class CircuitDiagramFrame extends JFrame
/*     */ {
/*  21 */   private boolean inApplet = false;
/*     */ 
/*  26 */   JScrollPane scrWorkspace = new JScrollPane();
/*  27 */   ChipPropertiesPanel propertiesPanel = new ChipPropertiesPanel();
/*  28 */   JPanel pnlStatus = new JPanel();
/*  29 */   BorderLayout borderLayout1 = new BorderLayout();
/*  30 */   JLabel lblStatus = new JLabel();
/*  31 */   Workspace workspace = new Workspace(this.scrWorkspace, this.lblStatus);
/*  32 */   CircuitEditorMenu standardMenuObject = new CircuitEditorMenu(this.workspace);
/*  33 */   JMenuBar menuBar = this.standardMenuObject.getMenuBar();
/*     */ 
/*  35 */   JToolBar toolBar = new JToolBar();
/*  36 */   JPanel jPanel1 = new JPanel();
/*  37 */   BorderLayout borderLayout2 = new BorderLayout();
/*  38 */   JButton andButton = new JButton();
/*  39 */   JButton orButton = new JButton();
/*  40 */   JButton notButton = new JButton();
/*  41 */   JButton inButton = new JButton();
/*  42 */   JButton outButton = new JButton();
/*  43 */   JButton wireButton = new JButton();
/*  44 */   JButton labelButton = new JButton();
/*  45 */   JButton groundButton = new JButton();
/*  46 */   JButton vccButton = new JButton();
/*  47 */   JButton pointerButton = new JButton();
/*  48 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*     */ 
/*     */   public CircuitDiagramFrame(boolean isApplet)
/*     */   {
/*  58 */     this.inApplet = isApplet;
/*     */     try {
/*  60 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  63 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   void jbInit() throws Exception
/*     */   {
/*  69 */     getContentPane().setLayout(this.gridBagLayout1);
/*  70 */     setSize(new Dimension(759, 599));
/*  71 */     setTitle("Circuit Diagram Editor");
/*     */ 
/*  73 */     setDefaultCloseOperation(0);
/*  74 */     setJMenuBar(this.menuBar);
/*  75 */     this.scrWorkspace.setBorder(BorderFactory.createLoweredBevelBorder());
/*  76 */     this.propertiesPanel.setBorder(BorderFactory.createLoweredBevelBorder());
/*  77 */     this.pnlStatus.setBorder(BorderFactory.createLoweredBevelBorder());
/*  78 */     this.pnlStatus.setLayout(this.borderLayout1);
/*  79 */     this.lblStatus.setText(" ");
/*  80 */     this.toolBar.setOrientation(0);
/*  81 */     this.jPanel1.setLayout(this.borderLayout2);
/*  82 */     this.andButton.addMouseListener(new CircuitDiagramFrame_andButton_mouseAdapter(this));
/*  83 */     this.orButton.addMouseListener(new CircuitDiagramFrame_orButton_mouseAdapter(this));
/*  84 */     this.notButton.addMouseListener(new CircuitDiagramFrame_notButton_mouseAdapter(this));
/*  85 */     this.inButton.addMouseListener(new CircuitDiagramFrame_inButton_mouseAdapter(this));
/*  86 */     this.outButton.addMouseListener(new CircuitDiagramFrame_outButton_mouseAdapter(this));
/*  87 */     this.wireButton.setFont(new Font("Dialog", 1, 32));
/*  88 */     this.wireButton.setToolTipText("Enter wiring mode");
/*  89 */     this.wireButton.addMouseListener(new CircuitDiagramFrame_wireButton_mouseAdapter(this));
/*  90 */     this.labelButton.setFont(new Font("Lucida Bright", 1, 16));
/*  91 */     this.labelButton.setToolTipText("Add a text label to the workspace");
/*  92 */     this.labelButton.setVerifyInputWhenFocusTarget(true);
/*  93 */     this.labelButton.setText("A");
/*  94 */     this.labelButton.addMouseListener(new CircuitDiagramFrame_labelButton_mouseAdapter(this));
/*  95 */     this.andButton.setToolTipText("Add an AND gate to the workspace");
/*  96 */     this.orButton.setToolTipText("Add an OR gate to the workspace");
/*  97 */     this.notButton.setToolTipText("Add a NOT gate to the workspace");
/*  98 */     this.inButton.setToolTipText("Add a chip input pin to the workspace");
/*  99 */     this.outButton.setToolTipText("Add a chip output pin to the workspace");
/* 100 */     this.groundButton.addMouseListener(new CircuitDiagramFrame_groundButton_mouseAdapter(this));
/* 101 */     this.vccButton.addMouseListener(new CircuitDiagramFrame_vccButton_mouseAdapter(this));
/* 102 */     this.pointerButton.setToolTipText("Click here to exit wiring mode or component placement mode");
/* 103 */     this.pointerButton.addMouseListener(new CircuitDiagramFrame_pointerButton_mouseAdapter(this));
/* 104 */     this.groundButton.setToolTipText("Add a component which provides a constant 0 value");
/* 105 */     this.vccButton.setToolTipText("Add a comnponent which provides a constant 1 value");
/* 106 */     getContentPane().add(this.propertiesPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(6, 6, 0, 11), 741, 101));
/*     */ 
/* 110 */     URL iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(0));
/* 111 */     this.andButton.setIcon(new ImageIcon(iconURL));
/* 112 */     this.andButton.setSize(this.andButton.getWidth(), 40);
/* 113 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(1));
/* 114 */     this.orButton.setIcon(new ImageIcon(iconURL));
/* 115 */     this.orButton.setSize(this.orButton.getWidth(), 40);
/* 116 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(2));
/* 117 */     this.notButton.setIcon(new ImageIcon(iconURL));
/* 118 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(3));
/* 119 */     this.inButton.setIcon(new ImageIcon(iconURL));
/* 120 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(4));
/* 121 */     this.outButton.setIcon(new ImageIcon(iconURL));
/* 122 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(5));
/* 123 */     this.groundButton.setIcon(new ImageIcon(iconURL));
/* 124 */     iconURL = URLMaker.getURL(CircuitComponent.getToolbarIcon(6));
/* 125 */     this.vccButton.setIcon(new ImageIcon(iconURL));
/* 126 */     iconURL = URLMaker.getURL("images/select.gif");
/* 127 */     this.pointerButton.setIcon(new ImageIcon(iconURL));
/* 128 */     this.pointerButton.setSize(40, 40);
/* 129 */     iconURL = URLMaker.getURL("images/wire.gif");
/* 130 */     this.wireButton.setIcon(new ImageIcon(iconURL));
/*     */ 
/* 132 */     getContentPane().add(this.jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 6, 0, 11), 591, 346));
/*     */ 
/* 134 */     this.jPanel1.add(this.toolBar, "North");
/* 135 */     this.jPanel1.add(this.scrWorkspace, "Center");
/* 136 */     getContentPane().add(this.pnlStatus, new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 15, 1, new Insets(7, 6, 11, 11), 719, 3));
/*     */ 
/* 138 */     this.pnlStatus.add(this.lblStatus, "Center");
/* 139 */     this.scrWorkspace.getViewport().add(this.workspace, null);
/* 140 */     this.toolBar.add(this.pointerButton, null);
/* 141 */     this.toolBar.addSeparator();
/* 142 */     this.toolBar.add(this.wireButton, null);
/* 143 */     this.toolBar.add(this.labelButton, null);
/* 144 */     this.toolBar.addSeparator();
/* 145 */     this.toolBar.add(this.andButton, null);
/* 146 */     this.toolBar.add(this.orButton, null);
/* 147 */     this.toolBar.add(this.notButton, null);
/* 148 */     this.toolBar.add(this.inButton, null);
/* 149 */     this.toolBar.add(this.outButton, null);
/* 150 */     this.toolBar.add(this.groundButton, null);
/* 151 */     this.toolBar.add(this.vccButton, null);
/*     */ 
/* 154 */     this.standardMenuObject.getSaveMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 157 */         CircuitDiagramFrame.this.createChipFile();
/*     */       }
/*     */     });
/* 160 */     this.standardMenuObject.getExitMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 163 */         CircuitDiagramFrame.this.exitAction();
/*     */       }
/*     */     });
/* 166 */     this.standardMenuObject.getNewMenuItem().addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/*     */         try {
/* 170 */           if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this workspace?\nAll unsaved work will be lost", "New Circuit", 0, 1) == 0)
/*     */           {
/* 173 */             CircuitDiagramFrame.this.closeWindow();
/* 174 */             CircuitDiagramFrame newFrame = new CircuitDiagramFrame(CircuitDiagramFrame.this.inApplet);
/*     */ 
/* 176 */             Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 177 */             Dimension frameSize = newFrame.getSize();
/* 178 */             if (frameSize.height > screenSize.height) {
/* 179 */               frameSize.height = screenSize.height;
/*     */             }
/* 181 */             if (frameSize.width > screenSize.width) {
/* 182 */               frameSize.width = screenSize.width;
/*     */             }
/* 184 */             newFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 185 */             newFrame.setVisible(true);
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 189 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void exitAction()
/*     */   {
/* 198 */     int confirm = JOptionPane.showConfirmDialog(null, "Are you sure that you want to close?\nAll unsaved work will be lost", "Exit Confirmation", 0, 1);
/*     */ 
/* 203 */     if (confirm == 0)
/* 204 */       immediateExit();
/*     */   }
/*     */ 
/*     */   private void immediateExit()
/*     */   {
/* 210 */     if (!this.inApplet) {
/* 211 */       System.exit(0);
/*     */     }
/*     */     else
/* 214 */       closeWindow();
/*     */   }
/*     */ 
/*     */   private void closeWindow()
/*     */   {
/* 219 */     dispose();
/*     */   }
/*     */ 
/*     */   private void createChipFile()
/*     */   {
/* 224 */     boolean errorOccured = false;
/* 225 */     String inputPinList = "";
/* 226 */     String outputPinList = "";
/*     */ 
/* 230 */     ArrayList inList = this.workspace.getInputs();
/* 231 */     ArrayList outList = this.workspace.getOutputs();
/* 232 */     int inPinCount = inList.size();
/* 233 */     int outPinCount = outList.size();
/* 234 */     File chipFile = null;
/*     */ 
/* 240 */     PrintWriter writer = null;
/*     */ 
/* 243 */     this.workspace.setWiringMode(false);
/* 244 */     this.lblStatus.setText("Simulating circuit and creating chip file...");
/*     */ 
/* 247 */     if (outPinCount < 1) {
/* 248 */       JOptionPane.showMessageDialog(this, "Error: Chip has no output pins!", "No Output Pins!", 2);
/*     */ 
/* 250 */       errorOccured = true;
/*     */     }
/*     */ 
/* 253 */     if (!errorOccured)
/*     */     {
/* 255 */       chipFile = FileOperations.getFileSelection("chp");
/* 256 */       if (chipFile == null) errorOccured = true;
/*     */ 
/*     */     }
/*     */ 
/* 263 */     if (!errorOccured) {
/*     */       try {
/* 265 */         writer = new PrintWriter(new FileWriter(chipFile));
/*     */       }
/*     */       catch (IOException ex) {
/* 268 */         JOptionPane.showMessageDialog(this, "Error: Could not open file " + chipFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);
/*     */ 
/* 272 */         errorOccured = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 278 */     if (!errorOccured)
/*     */     {
/* 280 */       writer.println("Chip Text=" + this.propertiesPanel.txtChipText.getText());
/* 281 */       writer.println("Description=" + this.propertiesPanel.txtDescription.getText());
/* 282 */       writer.println("Manufacturer=" + this.propertiesPanel.txtManufacturer.getText());
/* 283 */       if (this.propertiesPanel.radYes.isSelected()) {
/* 284 */         writer.println("Wide Chip=True");
/*     */       }
/*     */       else {
/* 287 */         writer.println("Wide Chip=False");
/*     */       }
/* 289 */       writer.println("Clocked Chip=False");
/* 290 */       for (int i = 0; i < inPinCount; i++) {
/* 291 */         inputPinList = inputPinList + ((CircuitComponent)inList.get(i)).getText() + ";";
/*     */       }
/* 293 */       writer.println("Input Pins=" + inputPinList);
/* 294 */       for (int i = 0; i < outPinCount; i++) {
/* 295 */         outputPinList = outputPinList + ((CircuitComponent)outList.get(i)).getText() + ";";
/*     */       }
/* 297 */       writer.println("Output Pins=" + outputPinList);
/* 298 */       writer.println("Number of States=1");
/* 299 */       writer.println("Initial State=0");
/* 300 */       writer.println("++++++++++");
/*     */ 
/* 305 */       int combinationCount = (int)Math.pow(2.0D, inPinCount);
/*     */ 
/* 307 */       for (int i = 0; i < combinationCount; i++) {
/* 308 */         String strCombination = Integer.toBinaryString(i);
/* 309 */         strCombination = fillWithZeros(strCombination, inPinCount);
/*     */ 
/* 312 */         for (int j = 0; j < inPinCount; j++) {
/* 313 */           CircuitComponent inputPin = (CircuitComponent)this.workspace.getInputs().get(j);
/*     */           boolean state;
/* 314 */           if (strCombination.charAt(j) == '1') state = true; else
/* 315 */             state = false;
/* 316 */           inputPin.setState(state);
/*     */         }
/* 318 */         String outputString = "";
/*     */ 
/* 321 */         for (int j = 0; j < outPinCount; j++) {
/* 322 */           CircuitComponent comp = (CircuitComponent)this.workspace.getOutputs().get(j);
/* 323 */           boolean state = comp.simulate();
/* 324 */           if (state) outputString = outputString + '1'; else
/* 325 */             outputString = outputString + '0';
/*     */         }
/* 327 */         writer.println(strCombination + ";0;" + outputString + ";0;" + this.propertiesPanel.txtDelay.getValue());
/*     */       }
/*     */ 
/* 346 */       writer.close();
/* 347 */       this.lblStatus.setText("Chip file created");
/*     */     }
/*     */ 
/* 352 */     if (!errorOccured) {
/* 353 */       if (JOptionPane.showOptionDialog(this, "Chip file created\nDo you wish to exit the state table editor or continue?", "Chip File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1)
/*     */       {
/* 357 */         immediateExit();
/*     */       }
/*     */     }
/*     */     else
/* 361 */       this.lblStatus.setText("Error creating chip file");
/*     */   }
/*     */ 
/*     */   private String fillWithZeros(String binaryString, int length)
/*     */   {
/* 368 */     String filledString = binaryString;
/* 369 */     for (int i = filledString.length(); i < length; i++) {
/* 370 */       filledString = "0" + filledString;
/*     */     }
/* 372 */     if (length == 0) filledString = "";
/* 373 */     return filledString;
/*     */   }
/*     */ 
/*     */   void andButton_mouseClicked(MouseEvent e)
/*     */   {
/* 378 */     this.workspace.addCircuitComponent(0);
/*     */   }
/*     */   void orButton_mouseClicked(MouseEvent e) {
/* 381 */     this.workspace.addCircuitComponent(1);
/*     */   }
/*     */   void notButton_mouseClicked(MouseEvent e) {
/* 384 */     this.workspace.addCircuitComponent(2);
/*     */   }
/*     */   void inButton_mouseClicked(MouseEvent e) {
/* 387 */     this.workspace.addCircuitComponent(3);
/*     */   }
/*     */   void outButton_mouseClicked(MouseEvent e) {
/* 390 */     this.workspace.addCircuitComponent(4);
/*     */   }
/*     */   void wireButton_mouseClicked(MouseEvent e) {
/* 393 */     this.workspace.setWiringMode(true);
/*     */   }
/*     */   void labelButton_mouseClicked(MouseEvent e) {
/* 396 */     this.workspace.createLabel();
/*     */   }
/*     */   void groundButton_mouseClicked(MouseEvent e) {
/* 399 */     this.workspace.addCircuitComponent(5);
/*     */   }
/*     */   void vccButton_mouseClicked(MouseEvent e) {
/* 402 */     this.workspace.addCircuitComponent(6);
/*     */   }
/*     */   void pointerButton_mouseClicked(MouseEvent e) {
/* 405 */     this.workspace.exitPlacementMode();
/*     */   }
/*     */ 
/*     */   protected void processWindowEvent(WindowEvent e)
/*     */   {
/* 412 */     super.processWindowEvent(e);
/* 413 */     if (e.getID() == 201)
/* 414 */       exitAction();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame
 * JD-Core Version:    0.6.2
 */