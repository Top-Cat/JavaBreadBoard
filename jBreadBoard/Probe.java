/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class Probe extends JComponent
/*     */   implements Device, CircuitSelection
/*     */ {
/*  25 */   private static int next = 0;
/*     */   private Circuit circuit;
/*     */   private Trace trace;
/*     */   private ImageIcon image;
/*  30 */   private String label = "probe" + next;
/*     */ 
/*  32 */   private long time = -1L;
/*  33 */   private int oldval = -1;
/*  34 */   private int val = -1;
/*     */ 
/*  36 */   private Node node = null;
/*     */ 
/* 163 */   private MouseInputListener mia = new MouseInputAdapter() {
/* 164 */     int XFudge = 0;
/* 165 */     int YFudge = 0;
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 168 */       Probe.this.circuit.select((Probe)e.getComponent());
/* 169 */       this.XFudge = e.getX();
/* 170 */       this.YFudge = e.getY();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e) {
/* 174 */       Probe probe = (Probe)e.getComponent();
/* 175 */       BreadBoard origb = (BreadBoard)probe.getParent();
/*     */ 
/* 177 */       Circuit pane = (Circuit)origb.getParent();
/*     */ 
/* 179 */       if (pane.getJBreadBoard().getMode().equals("move")) {
/* 180 */         origb.remove(probe);
/*     */ 
/* 182 */         int X = e.getX() + probe.getX() + origb.getX() - this.XFudge;
/* 183 */         int Y = e.getY() + probe.getY() + origb.getY() - this.YFudge;
/*     */ 
/* 186 */         Board board = pane.getBoardAt(X, Y);
/*     */         BreadBoard newb;
/* 189 */         if ((board != null) && ((board instanceof BreadBoard)))
/* 190 */           newb = (BreadBoard)board;
/*     */         else {
/* 192 */           newb = origb;
/*     */         }
/*     */ 
/* 196 */         int X2 = X - newb.getX();
/* 197 */         int Y2 = Y - newb.getY();
/*     */ 
/* 199 */         X2 -= (X2 - 4) % 8;
/* 200 */         if (X2 < 76) X2 = 76;
/* 201 */         else if (X2 > 444) X2 = 444;
/*     */ 
/* 203 */         Y2 -= Y2 % 8;
/* 204 */         if (Y2 < 28) Y2 = 16;
/* 205 */         else if (Y2 < 40) Y2 = 40;
/* 206 */         else if ((Y2 > 72) && (Y2 < 86)) Y2 = 72;
/* 207 */         else if ((Y2 > 128) && (Y2 < 138)) Y2 = 128;
/* 208 */         else if (Y2 > 152) Y2 = 152;
/*     */ 
/* 210 */         if (newb.isHole(X2, Y2)) {
/* 211 */           Rectangle dest = new Rectangle(X2, Y2, Probe.this.getWidth(), Probe.this.getHeight());
/* 212 */           Component[] components = newb.getComponents();
/* 213 */           boolean clear = true;
/*     */ 
/* 215 */           for (int i = 0; (clear) && (i < components.length); i++) {
/* 216 */             clear = !dest.intersects(components[i].getBounds());
/*     */           }
/* 218 */           if (clear)
/* 219 */             probe.setLocation(X2, Y2);
/*     */           else
/* 221 */             newb = origb;
/*     */         }
/*     */         else {
/* 224 */           newb = origb;
/*     */         }
/*     */ 
/* 227 */         newb.add(probe);
/* 228 */         probe.updateConnections();
/*     */ 
/* 230 */         origb.repaint();
/* 231 */         newb.repaint();
/*     */       }
/*     */     }
/* 163 */   };
/*     */ 
/*     */   public String toString()
/*     */   {
/*  42 */     return "Probe \"" + this.label + "\" " + getX() + " " + getY();
/*     */   }
/*     */ 
/*     */   public Probe(Circuit c)
/*     */   {
/*  52 */     if (c != null) {
/*  53 */       this.circuit = c;
/*  54 */       next += 1;
/*     */ 
/*  56 */       this.image = c.getJBreadBoard().getImageIcon("images/probe.gif");
/*  57 */       this.trace = c.getTrace();
/*  58 */       this.trace.addProbe(this);
/*     */     }
/*  60 */     setForeground(Color.black);
/*  61 */     setBackground(Color.black);
/*     */ 
/*  63 */     setBounds(getX(), getY(), 8, 8);
/*  64 */     addMouseListener(this.mia);
/*  65 */     addMouseMotionListener(this.mia);
/*     */   }
/*     */ 
/*     */   public void setLabel(String s)
/*     */   {
/*  72 */     if ((s != null) && (!s.equals("")))
/*  73 */       this.label = s;
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/*  80 */     return this.label;
/*     */   }
/*     */ 
/*     */   public int getOldValue()
/*     */   {
/*  85 */     return this.oldval;
/*     */   }
/*     */ 
/*     */   public int getValue()
/*     */   {
/*  90 */     return this.val;
/*     */   }
/*  92 */   public void select() { repaint(); } 
/*  93 */   public void deselect() { repaint(); } 
/*     */   public void delete() {
/*  95 */     this.trace.removeProbe(this);
/*  96 */     Container c = getParent();
/*  97 */     c.remove(this);
/*  98 */     c.repaint();
/*  99 */     if (this.node != null) {
/* 100 */       this.node.setDevice(null);
/* 101 */       this.node.setType("NC");
/*     */     }
/*     */   }
/*     */ 
/* 105 */   public String getInfo() { return "Component Name: \nVirtual Probe Device \n\nLabel: \n" + this.label + "\n\n" + "JBreadBoard library name: \n" + getClass().getName().substring(12) + ".class"; }
/*     */ 
/*     */ 
/*     */   public String getDiagram()
/*     */   {
/* 112 */     return null;
/*     */   }
/*     */   public void reset() {
/* 115 */     this.time = (this.oldval = this.val = -1);
/*     */   }
/*     */ 
/*     */   public void simulate() {
/* 119 */     if ((this.node != null) && (this.circuit.getSimTime() != this.time)) {
/* 120 */       String potential = this.node.getPotential().toTTL();
/*     */ 
/* 122 */       this.time = this.circuit.getSimTime();
/* 123 */       this.oldval = this.val;
/*     */ 
/* 125 */       if (potential != null) {
/* 126 */         if (potential.equals("HIGH")) this.val = 1;
/* 127 */         else if (potential.equals("LOW")) this.val = 0; else
/* 128 */           this.val = -1;
/*     */       } else this.val = -1;
/*     */ 
/* 131 */       this.circuit.updateTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateConnections() {
/* 136 */     int x = getX();
/* 137 */     int y = getY();
/* 138 */     if (this.node != null) {
/* 139 */       this.node.setDevice(null);
/* 140 */       this.node.setType(null);
/*     */     }
/* 142 */     Board b = (Board)getParent();
/* 143 */     this.node = b.getNodeAt(x, y);
/* 144 */     this.node.setDevice(this);
/* 145 */     this.node.setType("IN");
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 152 */     super.paintComponent(g);
/* 153 */     this.image.paintIcon(this, g, 0, 0);
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 243 */     return 8;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 247 */     return 8;
/*     */   }
/*     */ 
/*     */   public Rectangle getBounds() {
/* 251 */     return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Probe
 * JD-Core Version:    0.6.2
 */