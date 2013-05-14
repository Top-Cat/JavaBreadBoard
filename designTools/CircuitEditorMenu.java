/*     */ package designTools;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class CircuitEditorMenu extends StandardMenu
/*     */ {
/*     */   private Workspace wspace;
/*     */ 
/*     */   public CircuitEditorMenu(Workspace workspace)
/*     */   {
/*  26 */     ButtonGroup colourGroup = new ButtonGroup();
/*  27 */     this.wspace = workspace;
/*     */ 
/*  29 */     JMenu editMenu = new JMenu("Edit");
/*  30 */     editMenu.setMnemonic('E');
/*  31 */     JMenuItem deleteItem = new JMenuItem("Delete", 68);
/*  32 */     deleteItem.setAccelerator(KeyStroke.getKeyStroke(127, 0));
/*  33 */     editMenu.add(deleteItem);
/*  34 */     JMenuItem moveItem = new JMenuItem("Move", 77);
/*  35 */     editMenu.add(moveItem);
/*  36 */     JMenuItem renameItem = new JMenuItem("Rename", 82);
/*  37 */     editMenu.add(renameItem);
/*     */ 
/*  39 */     JMenu insertMenu = new JMenu("Insert");
/*  40 */     insertMenu.setMnemonic('I');
/*  41 */     JMenuItem labelItem = new JMenuItem("Label", 76);
/*  42 */     insertMenu.add(labelItem);
/*  43 */     JMenuItem andItem = new JMenuItem("AND Gate", 65);
/*  44 */     insertMenu.add(andItem);
/*  45 */     JMenuItem orItem = new JMenuItem("OR Gate", 79);
/*  46 */     insertMenu.add(orItem);
/*  47 */     JMenuItem notItem = new JMenuItem("NOT Gate", 78);
/*  48 */     insertMenu.add(notItem);
/*  49 */     JMenuItem inItem = new JMenuItem("Input Pin", 73);
/*  50 */     insertMenu.add(inItem);
/*  51 */     JMenuItem outItem = new JMenuItem("Output Pin", 84);
/*  52 */     insertMenu.add(outItem);
/*  53 */     JMenuItem groundItem = new JMenuItem("Ground", 71);
/*  54 */     insertMenu.add(groundItem);
/*  55 */     JMenuItem vccItem = new JMenuItem("VCC", 86);
/*  56 */     insertMenu.add(vccItem);
/*     */ 
/*  58 */     JMenu wireMenu = new JMenu("Wire");
/*  59 */     wireMenu.setMnemonic('W');
/*  60 */     JMenuItem wireItem = new JMenuItem("Enter Wiring Mode", 87);
/*  61 */     wireMenu.add(wireItem);
/*  62 */     JMenuItem unwireItem = new JMenuItem("Exit Wiring Mode", 88);
/*  63 */     wireMenu.add(unwireItem);
/*  64 */     JMenuItem cancelItem = new JMenuItem("Cancel Wire Segment", 67);
/*  65 */     cancelItem.setAccelerator(KeyStroke.getKeyStroke(27, 0));
/*  66 */     wireMenu.add(cancelItem);
/*  67 */     wireMenu.addSeparator();
/*  68 */     JRadioButtonMenuItem blackRadio = new JRadioButtonMenuItem("Black");
/*  69 */     colourGroup.add(blackRadio);
/*  70 */     wireMenu.add(blackRadio);
/*  71 */     blackRadio.setSelected(true);
/*  72 */     JRadioButtonMenuItem redRadio = new JRadioButtonMenuItem("Red");
/*  73 */     wireMenu.add(redRadio);
/*  74 */     colourGroup.add(redRadio);
/*  75 */     JRadioButtonMenuItem orangeRadio = new JRadioButtonMenuItem("Orange");
/*  76 */     wireMenu.add(orangeRadio);
/*  77 */     colourGroup.add(orangeRadio);
/*  78 */     JRadioButtonMenuItem yellowRadio = new JRadioButtonMenuItem("Yellow");
/*  79 */     wireMenu.add(yellowRadio);
/*  80 */     colourGroup.add(yellowRadio);
/*  81 */     JRadioButtonMenuItem greenRadio = new JRadioButtonMenuItem("Green");
/*  82 */     wireMenu.add(greenRadio);
/*  83 */     colourGroup.add(greenRadio);
/*  84 */     JRadioButtonMenuItem blueRadio = new JRadioButtonMenuItem("Blue");
/*  85 */     wireMenu.add(blueRadio);
/*  86 */     colourGroup.add(blueRadio);
/*  87 */     JRadioButtonMenuItem customRadio = new JRadioButtonMenuItem("Custom");
/*  88 */     wireMenu.add(customRadio);
/*  89 */     colourGroup.add(customRadio);
/*  90 */     this.menuBar.remove(this.helpMenu);
/*  91 */     this.menuBar.add(editMenu);
/*  92 */     this.menuBar.add(insertMenu);
/*  93 */     this.menuBar.add(wireMenu);
/*  94 */     this.menuBar.add(this.helpMenu);
/*     */ 
/*  98 */     deleteItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 101 */         CircuitEditorMenu.this.wspace.deleteSelectedComponent();
/*     */       }
/*     */     });
/* 104 */     moveItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 107 */         CircuitEditorMenu.this.wspace.moveSelectedComponent();
/*     */       }
/*     */     });
/* 110 */     renameItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 113 */         CircuitEditorMenu.this.wspace.renameSelectedComponent();
/*     */       }
/*     */     });
/* 117 */     labelItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 120 */         CircuitEditorMenu.this.wspace.createLabel();
/*     */       }
/*     */     });
/* 123 */     andItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 126 */         CircuitEditorMenu.this.wspace.addCircuitComponent(0);
/*     */       }
/*     */     });
/* 129 */     orItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 132 */         CircuitEditorMenu.this.wspace.addCircuitComponent(1);
/*     */       }
/*     */     });
/* 135 */     notItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 138 */         CircuitEditorMenu.this.wspace.addCircuitComponent(2);
/*     */       }
/*     */     });
/* 141 */     inItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 144 */         CircuitEditorMenu.this.wspace.addCircuitComponent(3);
/*     */       }
/*     */     });
/* 147 */     outItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 150 */         CircuitEditorMenu.this.wspace.addCircuitComponent(4);
/*     */       }
/*     */     });
/* 153 */     groundItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 156 */         CircuitEditorMenu.this.wspace.addCircuitComponent(5);
/*     */       }
/*     */     });
/* 159 */     vccItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 162 */         CircuitEditorMenu.this.wspace.addCircuitComponent(6);
/*     */       }
/*     */     });
/* 165 */     wireItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 168 */         CircuitEditorMenu.this.wspace.setWiringMode(true);
/*     */       }
/*     */     });
/* 171 */     unwireItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 174 */         CircuitEditorMenu.this.wspace.exitPlacementMode();
/*     */       }
/*     */     });
/* 178 */     blackRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 181 */         CircuitEditorMenu.this.wspace.setWireColor(Color.BLACK);
/*     */       }
/*     */     });
/* 184 */     redRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 187 */         CircuitEditorMenu.this.wspace.setWireColor(Color.RED);
/*     */       }
/*     */     });
/* 190 */     orangeRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 193 */         CircuitEditorMenu.this.wspace.setWireColor(Color.ORANGE);
/*     */       }
/*     */     });
/* 196 */     yellowRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 199 */         CircuitEditorMenu.this.wspace.setWireColor(Color.YELLOW);
/*     */       }
/*     */     });
/* 202 */     greenRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 205 */         CircuitEditorMenu.this.wspace.setWireColor(Color.GREEN);
/*     */       }
/*     */     });
/* 208 */     blueRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 211 */         CircuitEditorMenu.this.wspace.setWireColor(Color.BLUE);
/*     */       }
/*     */     });
/* 214 */     customRadio.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 217 */         CircuitEditorMenu.this.wspace.setWireColor(JColorChooser.showDialog(CircuitEditorMenu.this.wspace, "Wire Colour", Color.BLACK));
/*     */       }
/*     */     });
/* 220 */     cancelItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/* 223 */         CircuitEditorMenu.this.wspace.cancelWireSegment();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public JMenuBar getMenuBar()
/*     */   {
/* 232 */     return this.menuBar;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitEditorMenu
 * JD-Core Version:    0.6.2
 */