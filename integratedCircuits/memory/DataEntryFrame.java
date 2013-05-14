/*     */ package integratedCircuits.memory;
/*     */ 
/*     */ import designTools.FileOperations;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.ttl.generic.InvalidStateException;
/*     */ import jBreadBoard.HexMath;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class DataEntryFrame extends JFrame
/*     */   implements ListSelectionListener, ActionListener, FocusListener
/*     */ {
/*     */   private JList addList;
/*     */   private MemSpace data;
/*     */   private Memory access;
/*  35 */   private ByteTextField[] TextFields = new ByteTextField[256];
/*  36 */   private int AddBase = 0;
/*  37 */   private String type = "Hex";
/*  38 */   private JPanel frontPanel = new JPanel();
/*     */ 
/*  43 */   private String defaultDirectory = "configFiles";
/*     */ 
/*     */   public DataEntryFrame(MemSpace mem, Memory a)
/*     */   {
/*  52 */     this.data = mem;
/*  53 */     this.access = a;
/*     */ 
/*  55 */     setTitle("Data Edit");
/*  56 */     setDefaultCloseOperation(2);
/*  57 */     getContentPane().add(this.frontPanel);
/*     */ 
/*  59 */     GridBagLayout gridbag = new GridBagLayout();
/*  60 */     GridBagConstraints c = new GridBagConstraints();
/*     */ 
/*  62 */     setLayout(gridbag);
/*     */ 
/*  64 */     JPanel radioPanel = TwoRadioButtonPanel();
/*  65 */     JPanel panel = ByteFieldsPanel(this.data.getLength());
/*  66 */     JPanel listScroller = AddressListScroller();
/*  67 */     JPanel loadsavePanel = LoadSaveButtons();
/*     */ 
/*  69 */     c.gridwidth = 0;
/*  70 */     gridbag.setConstraints(panel, c);
/*  71 */     add(panel);
/*     */ 
/*  73 */     c.gridwidth = 1;
/*  74 */     c.weightx = 1.0D;
/*  75 */     gridbag.setConstraints(radioPanel, c);
/*  76 */     add(radioPanel);
/*     */ 
/*  78 */     gridbag.setConstraints(listScroller, c);
/*  79 */     add(listScroller);
/*     */ 
/*  81 */     c.gridwidth = 0;
/*     */ 
/*  83 */     gridbag.setConstraints(loadsavePanel, c);
/*  84 */     add(loadsavePanel);
/*     */ 
/*  86 */     updateTextFields();
/*     */ 
/*  88 */     pack();
/*     */   }
/*     */ 
/*     */   private JPanel LoadSaveButtons()
/*     */   {
/*  97 */     JPanel panel = new JPanel();
/*     */ 
/*  99 */     panel.setLayout(new GridLayout(2, 1));
/*     */ 
/* 103 */     JButton load = new JButton("Load");
/* 104 */     load.setActionCommand("Load");
/* 105 */     load.addActionListener(this);
/* 106 */     panel.add(load);
/*     */ 
/* 108 */     JButton save = new JButton("Save");
/* 109 */     save.setActionCommand("Save");
/* 110 */     save.addActionListener(this);
/* 111 */     panel.add(save);
/*     */ 
/* 113 */     return panel;
/*     */   }
/*     */ 
/*     */   private JPanel AddressListScroller()
/*     */   {
/* 118 */     JPanel panel = new JPanel();
/*     */ 
/* 120 */     int y = this.data.getLength() / 256;
/* 121 */     String[] addresses = new String[y];
/*     */ 
/* 123 */     for (int i = 0; i < y; i++) {
/* 124 */       addresses[i] = Integer.toHexString(i * 256);
/*     */     }
/* 126 */     for (int i = 0; i < y; i++) {
/* 127 */       while (addresses[i].length() < addresses[(y - 1)].length())
/* 128 */         addresses[i] = "0".concat(addresses[i]);
/*     */     }
/* 130 */     this.addList = new JList(addresses);
/* 131 */     this.addList.setSelectedIndex(0);
/* 132 */     this.addList.setSelectionMode(0);
/* 133 */     this.addList.addListSelectionListener(this);
/* 134 */     JScrollPane listScroller = new JScrollPane(this.addList);
/*     */ 
/* 136 */     listScroller.setPreferredSize(new Dimension(120, 80));
/*     */ 
/* 138 */     panel.add(listScroller);
/* 139 */     return panel;
/*     */   }
/*     */ 
/*     */   private JPanel ByteFieldsPanel(int x)
/*     */   {
/* 145 */     JPanel panel = new JPanel();
/*     */ 
/* 147 */     panel.setLayout(new GridLayout(16, 17));
/* 148 */     for (int i = 0; i < 16; i++) {
/* 149 */       JTextField temp = new JTextField(3);
/* 150 */       temp.setText(Integer.toHexString(i * 16) + "h");
/* 151 */       temp.setEditable(false);
/* 152 */       panel.add(temp);
/* 153 */       for (int j = 0; j < 16; j++)
/*     */       {
/* 155 */         this.TextFields[(i * 16 + j)] = new ByteTextField(3);
/* 156 */         panel.add(this.TextFields[(i * 16 + j)]);
/*     */       }
/*     */     }
/*     */ 
/* 160 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 162 */       this.TextFields[i].number = i;
/* 163 */       this.TextFields[i].addActionListener(this);
/* 164 */       this.TextFields[i].addFocusListener(this);
/* 165 */       this.TextFields[i].setActionCommand("textfields");
/*     */     }
/*     */ 
/* 168 */     return panel;
/*     */   }
/*     */ 
/*     */   private JPanel TwoRadioButtonPanel()
/*     */   {
/* 174 */     JPanel panel = new JPanel();
/* 175 */     ButtonGroup group = new ButtonGroup();
/*     */ 
/* 177 */     JRadioButton HexButton = new JRadioButton("Hex");
/* 178 */     HexButton.setActionCommand("Hex");
/* 179 */     HexButton.addActionListener(this);
/* 180 */     HexButton.setSelected(true);
/* 181 */     group.add(HexButton);
/*     */ 
/* 183 */     JRadioButton DecimalButton = new JRadioButton("Decimal");
/* 184 */     DecimalButton.setActionCommand("Decimal");
/* 185 */     DecimalButton.addActionListener(this);
/* 186 */     group.add(DecimalButton);
/*     */ 
/* 188 */     panel.setLayout(new GridLayout(2, 1));
/*     */ 
/* 190 */     panel.add(HexButton);
/* 191 */     panel.add(DecimalButton);
/* 192 */     return panel;
/*     */   }
/*     */ 
/*     */   private void updateTextFields()
/*     */   {
/* 198 */     for (int i = 0; i < 256; i++)
/* 199 */       if (this.type == "Hex")
/* 200 */         this.TextFields[i].setText(Integer.toHexString(this.data.getDatum(i + this.AddBase)));
/*     */       else
/* 202 */         this.TextFields[i].setText(Integer.toString(this.data.getDatum(i + this.AddBase)));
/*     */   }
/*     */ 
/*     */   public void valueChanged(ListSelectionEvent e)
/*     */   {
/* 208 */     if (!e.getValueIsAdjusting()) {
/* 209 */       this.AddBase = HexMath.HexStringtoInt((String)this.addList.getSelectedValue());
/* 210 */       updateTextFields();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void focusGained(FocusEvent event) {
/*     */   }
/*     */ 
/* 217 */   public void focusLost(FocusEvent event) { ByteTextField field = (ByteTextField)event.getSource();
/* 218 */     int i = field.number;
/* 219 */     if (this.type == "Hex") this.data.setDatum(this.AddBase + i, Integer.decode("0x" + field.getText()).intValue());
/* 220 */     else if (this.type == "Decimal") this.data.setDatum(this.AddBase + i, Integer.decode(field.getText()).intValue());
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent evt)
/*     */   {
/* 226 */     if (evt.getActionCommand() == "textfields")
/*     */     {
/* 228 */       ByteTextField field = (ByteTextField)evt.getSource();
/* 229 */       int i = field.number;
/* 230 */       if (this.type == "Hex") this.data.setDatum(this.AddBase + i, Integer.decode("0x" + field.getText()).intValue());
/* 231 */       else if (this.type == "Decimal") this.data.setDatum(this.AddBase + i, Integer.decode(field.getText()).intValue());
/*     */     }
/* 233 */     else if (evt.getActionCommand() == "Hex")
/*     */     {
/* 235 */       this.type = "Hex";
/* 236 */       for (int i = 0; i < 256; i++)
/* 237 */         this.TextFields[i].setHex();
/* 238 */       updateTextFields();
/*     */     }
/* 240 */     else if (evt.getActionCommand() == "Decimal")
/*     */     {
/* 242 */       this.type = "Decimal";
/* 243 */       for (int i = 0; i < 256; i++)
/* 244 */         this.TextFields[i].setDecimal();
/* 245 */       updateTextFields();
/*     */     }
/* 247 */     else if (evt.getActionCommand() == "Save")
/*     */     {
/* 249 */       saveRamFile();
/*     */     }
/* 251 */     else if (evt.getActionCommand() == "Load")
/*     */     {
/* 253 */       loadRamFile();
/*     */     }
/*     */     else {
/* 256 */       System.err.println("chiplib.DataEntryPanel: Action Command not recognized");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveRamFile() {
/* 261 */     boolean errorOccured = false;
/* 262 */     PrintWriter writer = null;
/*     */ 
/* 266 */     File ramFile = FileOperations.getFileSelection("mem", "SAVE");
/*     */ 
/* 268 */     if (ramFile == null) errorOccured = true;
/*     */ 
/* 273 */     if (!errorOccured) {
/*     */       try {
/* 275 */         writer = new PrintWriter(new FileWriter(ramFile));
/*     */       }
/*     */       catch (IOException ex)
/*     */       {
/* 279 */         JOptionPane.showMessageDialog(this, "Error: Could not open file " + ramFile.getAbsolutePath() + " for writing\n" + ex.getMessage(), "File Error", 0);
/*     */ 
/* 282 */         errorOccured = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 287 */     if (!errorOccured) {
/* 288 */       writer.println("Chip Text=" + this.access.getChipText());
/* 289 */       writer.println("Description=" + this.access.getDescription());
/* 290 */       writer.println("Manufacturer=" + this.access.getManufacturer());
/* 291 */       if (this.access.isWide()) {
/* 292 */         writer.println("Wide Chip=True");
/*     */       }
/*     */       else {
/* 295 */         writer.println("Wide Chip=False");
/*     */       }
/* 297 */       writer.println("Clocked Chip=False");
/*     */ 
/* 299 */       int dataPins = 0;
/* 300 */       int addrPins = 0;
/*     */ 
/* 302 */       for (int i = 0; i < this.access.getNumberOfPackagePins(); i++)
/*     */         try {
/* 304 */           if (this.access.isPinOffsetInput(i))
/*     */           {
/* 306 */             addrPins++;
/*     */           }
/* 308 */           if (this.access.isPinOffsetInputOutput(i))
/*     */           {
/* 310 */             dataPins++;
/*     */           }
/*     */         }
/*     */         catch (InvalidPinException e)
/*     */         {
/*     */         }
/* 316 */       addrPins -= 3;
/*     */ 
/* 319 */       writer.println("Addr Pins=" + addrPins);
/* 320 */       writer.println("Data Pins=" + dataPins);
/* 321 */       writer.println("++++++++++");
/* 322 */       writer.println(this.data.getAll());
/*     */     }
/* 324 */     writer.close();
/*     */ 
/* 327 */     String fileName = ramFile.getAbsolutePath();
/* 328 */     fileName = fileName.replace('\\', '/');
/* 329 */     this.access.setFileName(fileName);
/*     */ 
/* 332 */     if (!errorOccured)
/* 333 */       if (JOptionPane.showOptionDialog(this, "Ram file created\nDo you wish to exit the truth table editor or continue?", "Ram File Created", 0, 1, null, new String[] { "Continue", "Exit" }, "Continue") == 1)
/*     */       {
/* 341 */         setVisible(false);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void loadRamFile()
/*     */   {
/* 348 */     boolean errorOccured = false;
/*     */ 
/* 352 */     String extension = this.access.getFileExtension();
/* 353 */     File ramFile = FileOperations.getFileSelection(extension, "LOAD");
/*     */ 
/* 355 */     if (ramFile == null) errorOccured = true;
/*     */ 
/* 360 */     if (!errorOccured) {
/*     */       try {
/* 362 */         this.access.initialiseState(ramFile.getAbsolutePath());
/* 363 */         updateTextFields();
/*     */       }
/*     */       catch (InvalidStateException e) {
/* 366 */         System.out.println("Table Error");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 371 */       System.out.println("File Error");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.DataEntryFrame
 * JD-Core Version:    0.6.2
 */