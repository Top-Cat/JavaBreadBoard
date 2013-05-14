/*     */ package jBreadBoard;
/*     */ 
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
/*     */ public class LED extends JComponent
/*     */   implements Device, CircuitSelection
/*     */ {
/*     */   public static final int red = 0;
/*     */   public static final int yellow = 1;
/*     */   public static final int green = 2;
/*  29 */   private static final String[] gfxfiles = { "rled.gif", "yled.gif", "gled.gif" };
/*     */ 
/*  31 */   private static int defaultcolor = 0;
/*     */   private Circuit circuit;
/*     */   private int color;
/*     */   private ImageIcon image;
/*  48 */   private boolean lit = false;
/*  49 */   private Node[] pins = new Node[2];
/*     */ 
/* 176 */   private MouseInputListener mia = new MouseInputAdapter() {
/* 177 */     int XFudge = 0;
/* 178 */     int YFudge = 0;
/* 179 */     boolean moved = false;
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 182 */       LED.this.circuit.select((LED)e.getComponent());
/* 183 */       this.XFudge = e.getX();
/* 184 */       this.YFudge = e.getY();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e) {
/* 188 */       LED led = (LED)e.getComponent();
/* 189 */       BreadBoard origb = (BreadBoard)led.getParent();
/*     */ 
/* 191 */       Circuit pane = (Circuit)origb.getParent();
/*     */ 
/* 193 */       if (pane.getJBreadBoard().getMode().equals("move")) {
/* 194 */         origb.remove(led);
/* 195 */         if (LED.this.pins[0] != null) {
/* 196 */           LED.this.pins[0].setType("NC");
/* 197 */           LED.this.pins[0].setDevice(null);
/*     */         }
/* 199 */         if (LED.this.pins[1] != null) {
/* 200 */           LED.this.pins[1].setType("NC");
/* 201 */           LED.this.pins[1].setType(null);
/*     */         }
/*     */ 
/* 204 */         int X = e.getX() + led.getX() + origb.getX() - this.XFudge;
/* 205 */         int Y = e.getY() + led.getY() + origb.getY() - this.YFudge;
/*     */ 
/* 208 */         Board board = pane.getBoardAt(X, Y);
/*     */         BreadBoard newb;
/* 211 */         if ((board != null) && ((board instanceof BreadBoard)))
/* 212 */           newb = (BreadBoard)board;
/*     */         else {
/* 214 */           newb = origb;
/*     */         }
/*     */ 
/* 218 */         int X2 = X - newb.getX();
/* 219 */         int Y2 = Y - newb.getY();
/*     */ 
/* 221 */         X2 -= (X2 - 4) % 8;
/* 222 */         if (X2 < 76) X2 = 76;
/*     */ 
/* 224 */         if (Y2 < 46) Y2 = 16;
/* 225 */         else if (Y2 < 102) Y2 = 72; else {
/* 226 */           Y2 = 128;
/*     */         }
/* 228 */         if ((newb.isHole(X2, Y2)) && (newb.isHole(X2, Y2 + 24))) {
/* 229 */           Rectangle dest = new Rectangle(X2, Y2, LED.this.getWidth(), LED.this.getHeight());
/* 230 */           Component[] components = newb.getComponents();
/* 231 */           boolean clear = true;
/*     */ 
/* 233 */           for (int i = 0; (clear) && (i < components.length); i++) {
/* 234 */             clear = !dest.intersects(components[i].getBounds());
/*     */           }
/* 236 */           if (clear)
/* 237 */             led.setLocation(X2, Y2);
/*     */           else
/* 239 */             newb = origb;
/*     */         }
/*     */         else {
/* 242 */           newb = origb;
/*     */         }
/*     */ 
/* 246 */         newb.add(led);
/* 247 */         led.updateConnections();
/*     */ 
/* 249 */         origb.repaint();
/* 250 */         newb.repaint();
/*     */       }
/*     */     }
/* 176 */   };
/*     */ 
/*     */   public static void setDefaultColor(int d)
/*     */   {
/*  40 */     if ((d == 0) || (d == 1) || (d == 2)) defaultcolor = d;
/*     */   }
/*     */ 
/*     */   public boolean getState()
/*     */   {
/*  55 */     return this.lit;
/*     */   }
/*     */   public void select() {
/*  58 */     repaint(); } 
/*  59 */   public void deselect() { repaint(); } 
/*     */   public void delete() {
/*  61 */     Container cont = getParent();
/*  62 */     cont.remove(this);
/*  63 */     cont.repaint();
/*  64 */     for (int i = 0; i < 2; i++)
/*  65 */       if (this.pins[i] != null) {
/*  66 */         this.pins[i].setDevice(null);
/*  67 */         this.pins[i].setType("NC");
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getInfo()
/*     */   {
/*  73 */     String lit;
/*  73 */     if (getState()) lit = "ON"; else
/*  74 */       lit = "OFF";
/*  75 */     return "Component Name: \nLED\n\nStatus:\n" + lit + "\n\n" + "JBreadBoard library name: \n" + getClass().getName().substring(12) + ".class";
/*     */   }
/*     */ 
/*     */   public String getDiagram()
/*     */   {
/*  82 */     return "images/leddia.gif";
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  87 */     this.lit = false;
/*  88 */     repaint();
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*  92 */     if ((!this.pins[0].getType().equals("NC")) && (!this.pins[0].getType().equals("IN")) && (!this.pins[1].getType().equals("NC")) && (!this.pins[1].getType().equals("IN")))
/*     */     {
/*  95 */       if ((this.pins[0].getPotential().toTTL().equals("HIGH")) && (this.pins[1].getPotential().toTTL().equals("LOW")))
/*     */       {
/*  97 */         this.lit = true;
/*     */       }
/*  99 */       else this.lit = false; 
/*     */     }
/*     */     else
/*     */     {
/* 102 */       this.lit = false;
/* 103 */       repaint();
/*     */     }
/* 105 */     repaint();
/* 106 */     if (this.circuit.selected == this) this.circuit.select(this); 
/*     */   }
/*     */ 
/*     */   public void updateConnections()
/*     */   {
/* 110 */     int x = getX();
/* 111 */     int y = getY();
/* 112 */     for (int i = 0; i < 2; i++) {
/* 113 */       if (this.pins[i] != null) {
/* 114 */         this.pins[i].setDevice(null);
/* 115 */         this.pins[i].setType(null);
/*     */       }
/* 117 */       Board b = (Board)getParent();
/* 118 */       this.pins[i] = b.getNodeAt(x, y);
/* 119 */       this.pins[i].setDevice(this);
/* 120 */       this.pins[i].setType("IN");
/* 121 */       y += 24;
/*     */     }
/*     */   }
/*     */ 
/*     */   public LED(Circuit c)
/*     */   {
/* 129 */     this(c, defaultcolor);
/*     */   }
/*     */ 
/*     */   public LED(Circuit c, int colint)
/*     */   {
/* 138 */     this.circuit = c;
/* 139 */     if ((colint == 0) || (colint == 1) || (colint == 2)) this.color = colint;
/* 140 */     this.image = c.getJBreadBoard().getImageIcon("images/" + gfxfiles[this.color]);
/* 141 */     setBounds(getX(), getY(), getWidth(), getHeight());
/* 142 */     addMouseListener(this.mia);
/* 143 */     addMouseMotionListener(this.mia);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 150 */     return new String("Device " + getClass().getName() + " " + getX() + " " + getY() + " " + this.color);
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 161 */     super.paintComponent(g);
/* 162 */     int x = 0;
/* 163 */     if (this.lit) x += 8;
/* 164 */     if (this.circuit.selected == this) x += 16;
/*     */ 
/* 166 */     this.image.paintIcon(this, g, -x, 0);
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 264 */     return 8;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 268 */     return 32;
/*     */   }
/*     */ 
/*     */   public Rectangle getBounds() {
/* 272 */     return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.LED
 * JD-Core Version:    0.6.2
 */