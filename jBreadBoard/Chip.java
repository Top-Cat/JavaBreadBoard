/*     */ package jBreadBoard;
/*     */ 
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import jBreadBoard.v1_10.Clickable;
/*     */ import jBreadBoard.v1_10.DblClick;
/*     */ import jBreadBoard.v1_10.Graphical;
/*     */ import jBreadBoard.v1_10.LoadSave;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class Chip extends JComponent
/*     */   implements Device, CircuitSelection
/*     */ {
/*     */   private Circuit circuit;
/*     */   private ChipModel model;
/*     */   private boolean dblClickable;
/*     */   private boolean loadSaveable;
/*     */   private boolean graphical;
/*     */   private boolean clickable;
/*     */   private boolean sized;
/*     */   private Node[] connections;
/*  37 */   private int textX = 0;
/*  38 */   private int textY = 0;
/*     */ 
/*  46 */   private MouseInputListener mia = new MouseInputAdapter() {
/*  47 */     int XFudge = 0;
/*  48 */     boolean moved = false;
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/*  51 */       int clicks = e.getClickCount();
/*     */ 
/*  53 */       if (clicks == 1)
/*  54 */         Chip.this.circuit.select((Chip)e.getComponent());
/*  55 */       if ((clicks == 1) && (Chip.this.clickable))
/*  56 */         ((Clickable)Chip.this.model).Clicked();
/*  57 */       if ((clicks == 2) && (Chip.this.dblClickable)) {
/*  58 */         ((DblClick)Chip.this.model).DblClicked();
/*     */       }
/*  60 */       this.XFudge = e.getX();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e) {
/*  64 */       Chip chip = (Chip)e.getComponent();
/*  65 */       BreadBoard origb = (BreadBoard)chip.getParent();
/*     */ 
/*  67 */       Circuit pane = (Circuit)origb.getParent();
/*  68 */       int origx = chip.getX();
/*     */ 
/*  70 */       if (pane.getJBreadBoard().getMode().equals("move")) {
/*  71 */         chip.delete();
/*     */ 
/*  73 */         this.moved = true;
/*     */ 
/*  76 */         Board board = pane.getBoardAt(e.getX() + chip.getX() + origb.getX() - this.XFudge, e.getY() + chip.getY() + origb.getY());
/*     */         BreadBoard newb;
/*  81 */         if ((board != null) && ((board instanceof BreadBoard)))
/*  82 */           newb = (BreadBoard)board;
/*     */         else {
/*  84 */           newb = origb;
/*     */         }
/*     */ 
/*  88 */         if (!newb.add(chip, e.getX() + chip.getX() + origb.getX() - newb.getX() - this.XFudge)) {
/*  89 */           boolean success = false;
/*     */ 
/*  91 */           for (int x = e.getX(); (!success) && (x > 0); x -= 8) {
/*  92 */             success = newb.add(chip, x + chip.getX() + origb.getX() - newb.getX() - this.XFudge);
/*     */           }
/*     */ 
/*  95 */           if (!success)
/*  96 */             origb.add(chip, origx);
/*     */         }
/*     */       }
/*     */     }
/*  46 */   };
/*     */ 
/*     */   public Chip(Circuit c, ChipModel m)
/*     */   {
/* 108 */     this.circuit = c;
/* 109 */     if (m == null)
/* 110 */       this.model = JBreadBoard.getChipModel("integratedCircuits.ttl.logic.Gen7400");
/*     */     else {
/* 112 */       this.model = m;
/*     */     }
/* 114 */     this.model.setAccess(new ChipAccess(this));
/* 115 */     this.connections = new Node[getNumberOfPins() * 2];
/*     */ 
/* 117 */     setBounds(getX(), getY(), getWidth(), getHeight());
/* 118 */     sizeText();
/* 119 */     addMouseListener(this.mia);
/* 120 */     addMouseMotionListener(this.mia);
/*     */ 
/* 124 */     this.dblClickable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.DblClick");
/* 125 */     this.loadSaveable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.LoadSave");
/* 126 */     this.graphical = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Graphical");
/* 127 */     this.clickable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Clickable");
/* 128 */     this.sized = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Sized");
/*     */   }
/*     */ 
/*     */   public void select()
/*     */   {
/* 138 */     repaint(); } 
/* 139 */   public void deselect() { repaint(); } 
/*     */   public void delete() {
/* 141 */     Board board = getBoard();
/* 142 */     board.remove(this);
/* 143 */     board.repaint();
/* 144 */     for (int i = 0; i < getNumberOfPins() * 2; i++)
/* 145 */       if (this.connections[i] != null) {
/* 146 */         this.connections[i].setType("NC");
/* 147 */         this.connections[i].setDevice(null);
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getInfo() {
/* 152 */     return "Component Name: \n" + getChipText() + "\n\n" + "Description: \n" + getDescription() + "\n\n" + "Manufacturer: \n" + getManufacturer() + "\n\n" + "JBreadBoard library name: \n" + getModelName().substring(getModelName().lastIndexOf('.') + 1) + ".class";
/*     */   }
/*     */ 
/*     */   public String getDiagram()
/*     */   {
/* 163 */     String fileName = this.model.toString().substring(0, this.model.toString().lastIndexOf('.')).replace('.', '/');
/* 164 */     fileName = "build/classes/" + fileName + '/' + this.model.getDiagram();
/*     */ 
/* 166 */     return fileName;
/*     */   }
/*     */ 
/*     */   public void updateConnections()
/*     */   {
/* 172 */     tryupdateConnections();
/*     */   }
/*     */ 
/*     */   public boolean tryupdateConnections()
/*     */   {
/* 185 */     boolean conflicts = false;
/*     */ 
/* 187 */     for (int i = 0; i < getNumberOfPins() * 2; i++) {
/* 188 */       if (this.connections[i] != null) {
/* 189 */         this.connections[i].setType("NC");
/* 190 */         this.connections[i].setDevice(null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 195 */     for (int i = 0; (!conflicts) && (i < getNumberOfPins()); i++) {
/* 196 */       this.connections[i] = getBoard().getNodeAt(getX() + i * 8, getY() + getHeight() - 8);
/*     */ 
/* 199 */       conflicts = NodeType.conflicts(this.connections[i].getType(), getPinType(i));
/* 200 */       this.connections[i].setType(getPinType(i));
/* 201 */       this.connections[i].setDevice(this);
/*     */     }
/*     */ 
/* 205 */     for (int i = getNumberOfPins(); (!conflicts) && (i < getNumberOfPins() * 2); i++) {
/* 206 */       this.connections[i] = getBoard().getNodeAt(getX() + (getNumberOfPins() * 2 - i - 1) * 8, getY());
/* 207 */       conflicts = NodeType.conflicts(this.connections[i].getType(), getPinType(i));
/* 208 */       this.connections[i].setType(getPinType(i));
/* 209 */       this.connections[i].setDevice(this);
/*     */     }
/*     */ 
/* 212 */     if (conflicts) {
/* 213 */       for (int i = 0; i < getNumberOfPins() * 2; i++) {
/* 214 */         if (this.connections[i] != null) {
/* 215 */           this.connections[i].setType("NC");
/* 216 */           this.connections[i].setDevice(null);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 221 */     return !conflicts;
/*     */   }
/*     */ 
/*     */   public void sizeText()
/*     */   {
/* 229 */     Font biggestFont = new Font("SansSerif", 0, 13);
/* 230 */     boolean fontFits = false;
/* 231 */     Font currentFont = biggestFont;
/* 232 */     FontMetrics currentMetrics = getFontMetrics(currentFont);
/* 233 */     int size = currentFont.getSize();
/* 234 */     String name = currentFont.getName();
/* 235 */     int style = currentFont.getStyle();
/*     */ 
/* 237 */     while (!fontFits) {
/* 238 */       if ((currentMetrics.getHeight() <= getHeight()) && (currentMetrics.stringWidth(getChipText()) <= getWidth() - 10))
/*     */       {
/* 240 */         fontFits = true;
/*     */       } else {
/* 242 */         currentFont = new Font(name, style, --size);
/* 243 */         currentMetrics = getFontMetrics(currentFont);
/*     */       }
/*     */     }
/* 246 */     setFont(currentFont);
/* 247 */     this.textX = (getWidth() / 2 - currentMetrics.stringWidth(getChipText()) / 2 + 2);
/* 248 */     this.textY = (getHeight() / 2 + currentMetrics.getAscent() / 2 - 3);
/*     */   }
/*     */ 
/*     */   public Board getBoard()
/*     */   {
/* 256 */     return (Board)getParent();
/*     */   }
/*     */ 
/*     */   public Circuit getCircuit()
/*     */   {
/* 263 */     return this.circuit;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 271 */     if (this.graphical) {
/* 272 */       ((Graphical)this.model).paintComponent(g, this.circuit.selected == this);
/*     */     }
/*     */     else
/*     */     {
/* 278 */       Color chipcolor = Color.black;
/* 279 */       Color notchcolor = Color.darkGray;
/* 280 */       Color pincolor = Color.lightGray;
/* 281 */       Color textcolor = Color.white;
/*     */ 
/* 283 */       if (this.circuit.selected == this) {
/* 284 */         chipcolor = Color.yellow;
/* 285 */         notchcolor = Color.orange;
/* 286 */         pincolor = Color.orange;
/* 287 */         textcolor = Color.black;
/*     */       }
/*     */ 
/* 291 */       g.setColor(chipcolor);
/* 292 */       g.fillRect(0, 4, getWidth() - 1, getHeight() - 9);
/*     */ 
/* 294 */       g.setColor(notchcolor);
/* 295 */       g.fillArc(-6, getHeight() / 2 - 6, 12, 10, -90, 180);
/*     */ 
/* 297 */       g.setColor(pincolor);
/* 298 */       for (int i = 0; i < getNumberOfPins(); i++) {
/* 299 */         g.fillRect(i * 8 + 1, 2, 5, 2);
/* 300 */         g.fillRect(i * 8 + 1, getHeight() - 5, 5, 2);
/*     */       }
/*     */ 
/* 303 */       g.setColor(textcolor);
/* 304 */       g.setFont(getFont());
/* 305 */       g.drawString(getChipText(), this.textX, this.textY);
/*     */     }
/* 307 */     super.paintComponent(g);
/*     */   }
/*     */ 
/*     */   public Node[] getConnections()
/*     */   {
/* 314 */     return this.connections;
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 318 */     this.model.reset();
/*     */   }
/*     */ 
/*     */   public void simulate() {
/* 322 */     this.model.simulate();
/*     */   }
/*     */ 
/*     */   public int getNumberOfPins()
/*     */   {
/* 329 */     return this.model.getNumberOfPins();
/*     */   }
/*     */ 
/*     */   public String getPinType(int i)
/*     */   {
/* 338 */     return this.model.getPinType(i);
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 345 */     return this.model.getDescription();
/*     */   }
/*     */ 
/*     */   public String getChipText()
/*     */   {
/* 352 */     return this.model.getChipText();
/*     */   }
/*     */ 
/*     */   public String getManufacturer()
/*     */   {
/* 359 */     return this.model.getManufacturer();
/*     */   }
/*     */ 
/*     */   public String getModelName()
/*     */   {
/* 366 */     return this.model.getClass().getName();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 373 */     String filename = "";
/* 374 */     if (this.loadSaveable) filename = " " + ((LoadSave)this.model).getFileName();
/*     */ 
/* 376 */     return new String("Device " + getClass().getName() + " " + getModelName() + " 0" + " 0" + " " + getX() + " " + getY() + filename);
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 391 */     return false;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 395 */     return this.model.getNumberOfPins() * 8;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 399 */     if (this.model.isWide()) return 48; return 32;
/*     */   }
/*     */ 
/*     */   public Rectangle getBounds()
/*     */   {
/* 406 */     return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   public boolean implementsLoadSave()
/*     */   {
/* 413 */     return this.loadSaveable;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Chip
 * JD-Core Version:    0.6.2
 */