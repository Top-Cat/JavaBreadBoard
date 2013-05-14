/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class Dipswitch extends JComponent
/*     */   implements Device, CircuitSelection
/*     */ {
/*  24 */   private int pins = 3;
/*     */   private boolean[] states;
/*     */   private Node[] topnode;
/*     */   private Node[] bottomnode;
/*     */   private Potential[] connections;
/*     */   private Circuit circuit;
/*  31 */   MouseInputListener mia = new MouseInputAdapter() {
/*  32 */     int XFudge = 0;
/*  33 */     int YFudge = 0;
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/*  36 */       Dipswitch dip = (Dipswitch)e.getComponent();
/*  37 */       Dipswitch.this.circuit.select(dip);
/*  38 */       this.XFudge = e.getX();
/*  39 */       this.YFudge = e.getY();
/*     */ 
/*  42 */       if ((e.getY() > 7) && (e.getY() < 23) && (e.getX() % 8 > 1) && (e.getX() % 8 < 6)) {
/*  43 */         if (e.getY() < 16) dip.setState(e.getX() / 8, true); else {
/*  44 */           dip.setState(e.getX() / 8, false);
/*     */         }
/*  46 */         Dipswitch.this.repaint();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e) {
/*  51 */       Dipswitch dip = (Dipswitch)e.getComponent();
/*  52 */       BreadBoard origb = (BreadBoard)dip.getParent();
/*     */ 
/*  54 */       Circuit pane = (Circuit)origb.getParent();
/*     */ 
/*  56 */       if (pane.getJBreadBoard().getMode().equals("move")) {
/*  57 */         int X = e.getX() + dip.getX() + origb.getX() - this.XFudge;
/*  58 */         int Y = e.getY() + dip.getY() + origb.getY() - this.YFudge;
/*     */ 
/*  61 */         Board board = pane.getBoardAt(X, Y);
/*     */         BreadBoard newb;
/*  64 */         if ((board != null) && ((board instanceof BreadBoard)))
/*  65 */           newb = (BreadBoard)board;
/*     */         else {
/*  67 */           newb = origb;
/*     */         }
/*     */ 
/*  70 */         int X2 = X - newb.getX();
/*  71 */         int Y2 = Y - newb.getY();
/*     */ 
/*  73 */         X2 -= (X2 - 4) % 8;
/*  74 */         if (X2 < 76) X2 = 76;
/*     */ 
/*  76 */         if (Y2 < 46) Y2 = 16;
/*  77 */         else if (Y2 < 102) Y2 = 72; else {
/*  78 */           Y2 = 128;
/*     */         }
/*  80 */         Component[] components = newb.getComponents();
/*  81 */         boolean isspace = false;
/*     */ 
/*  83 */         while ((!isspace) && (X2 < 452 - dip.getWidth())) {
/*  84 */           Rectangle dest = new Rectangle(X2, Y2, dip.getWidth(), dip.getHeight());
/*  85 */           isspace = true;
/*     */ 
/*  87 */           for (int i = 0; (isspace) && (i < components.length); i++) {
/*  88 */             if (components[i] != dip) {
/*  89 */               isspace = !dest.intersects(components[i].getBounds());
/*     */             }
/*     */           }
/*     */ 
/*  93 */           for (int i = 0; (isspace) && (i < dip.getPins()); i++) {
/*  94 */             isspace = (isspace) && (newb.isHole(X2 + i * 8, Y2));
/*  95 */             isspace = (isspace) && (newb.isHole(X2 + i * 8, Y2 + dip.getHeight() - 8));
/*     */           }
/*  97 */           X2 += 8;
/*     */         }
/*     */ 
/* 100 */         X2 -= 8;
/*     */ 
/* 104 */         for (int dx = e.getX(); (!isspace) && (dx > 0); dx -= 8) {
/* 105 */           X2 -= 8;
/* 106 */           Rectangle dest = new Rectangle(X2, Y2, dip.getWidth(), dip.getHeight());
/* 107 */           isspace = true;
/*     */ 
/* 109 */           for (int i = 0; (isspace) && (i < components.length); i++) {
/* 110 */             if (components[i] != dip) {
/* 111 */               isspace = !dest.intersects(components[i].getBounds());
/*     */             }
/*     */           }
/*     */ 
/* 115 */           for (int i = 0; (isspace) && (i < dip.getPins()); i++) {
/* 116 */             isspace = (isspace) && (newb.isHole(X2 + i * 8, Y2));
/* 117 */             isspace = (isspace) && (newb.isHole(X2 + i * 8, Y2 + dip.getHeight() - 8));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 122 */         if (isspace) {
/* 123 */           int oldx = dip.getX();
/* 124 */           int oldy = dip.getY();
/*     */ 
/* 126 */           dip.setLocation(X2, Y2);
/*     */ 
/* 129 */           if ((origb != newb) || (oldx != X2) || (oldy != Y2))
/* 130 */             Dipswitch.this.updateConnections();
/*     */         }
/*     */         else
/*     */         {
/* 134 */           newb = origb;
/*     */         }
/*     */ 
/* 137 */         newb.add(dip);
/* 138 */         newb.repaint();
/* 139 */         origb.repaint();
/*     */       }
/*     */     }
/*  31 */   };
/*     */ 
/*     */   public int getPins()
/*     */   {
/* 148 */     return this.pins;
/*     */   }
/*     */   public void select() {
/* 151 */     repaint(); } 
/* 152 */   public void deselect() { repaint(); } 
/*     */   public void delete() {
/* 154 */     Container cont = getParent();
/* 155 */     cont.remove(this);
/* 156 */     cont.repaint();
/*     */ 
/* 158 */     for (int i = 0; i < this.connections.length; i++)
/* 159 */       if (this.connections[i] != null)
/* 160 */         this.connections[i].delete();
/*     */   }
/*     */ 
/*     */   public String getInfo() {
/* 164 */     return "Component Name: \nDip Switches\n\nJBreadBoard library name: \n" + getClass().getName().substring(12) + ".class";
/*     */   }
/*     */ 
/*     */   public String getDiagram()
/*     */   {
/* 169 */     return "images/dipdia.gif";
/*     */   }
/*     */   public void simulate() {
/*     */   }
/*     */   public void reset() {
/*     */   }
/*     */   public void updateConnections() {
/* 176 */     Board board = (Board)getParent();
/* 177 */     for (int i = 0; i < this.pins; i++) {
/* 178 */       setState(i, false);
/* 179 */       this.topnode[i] = board.getNodeAt(getX() + i * 8, getY());
/* 180 */       this.bottomnode[i] = board.getNodeAt(getX() + i * 8, getY() + getHeight() - 8);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setState(int pin, boolean state)
/*     */   {
/* 190 */     if ((pin >= 0) && (pin <= this.pins)) {
/* 191 */       this.states[pin] = state;
/* 192 */       if (!state) {
/* 193 */         if (this.connections[pin] != null) {
/* 194 */           this.connections[pin].delete();
/* 195 */           this.connections[pin] = null;
/* 196 */           this.circuit.getSimulation().addEvent(this.topnode[pin], 0);
/* 197 */           this.circuit.getSimulation().addEvent(this.bottomnode[pin], 0);
/*     */         }
/*     */       }
/* 200 */       else if (this.connections[pin] == null)
/* 201 */         if (!NodeType.conflicts(this.topnode[pin].getType(), this.bottomnode[pin].getType())) {
/* 202 */           this.connections[pin] = new Potential(this.topnode[pin], this.bottomnode[pin]);
/* 203 */           this.circuit.getSimulation().addEvent(this.topnode[pin], 0);
/*     */         } else {
/* 205 */           setState(pin, false);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dipswitch(Circuit c, int p)
/*     */   {
/* 217 */     this.circuit = c;
/* 218 */     this.pins = p;
/* 219 */     if (this.pins < 1) this.pins = 1;
/* 220 */     this.states = new boolean[this.pins];
/* 221 */     this.topnode = new Node[this.pins];
/* 222 */     this.bottomnode = new Node[this.pins];
/* 223 */     this.connections = new Potential[this.pins];
/*     */ 
/* 225 */     for (int i = 0; i < this.pins; i++) {
/* 226 */       this.states[i] = false;
/*     */     }
/*     */ 
/* 229 */     addMouseListener(this.mia);
/* 230 */     addMouseMotionListener(this.mia);
/* 231 */     setBounds(getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 238 */     return new String("Device " + getClass().getName() + " " + getX() + " " + getY() + " " + getPins());
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 250 */     super.paintComponent(g);
/*     */ 
/* 253 */     Color dipcolor = new Color(33, 49, 255);
/* 254 */     Color pincolor = Color.lightGray;
/* 255 */     Color slidecolor = new Color(0, 0, 120);
/* 256 */     Color switchcolor = Color.lightGray;
/*     */ 
/* 258 */     if (this.circuit.selected == this) {
/* 259 */       dipcolor = Color.yellow;
/* 260 */       pincolor = Color.orange;
/* 261 */       slidecolor = Color.orange;
/* 262 */       switchcolor = Color.white;
/*     */     }
/*     */ 
/* 266 */     g.setColor(dipcolor);
/* 267 */     g.fillRect(0, 4, getWidth() - 1, getHeight() - 9);
/*     */ 
/* 269 */     g.setColor(pincolor);
/* 270 */     for (int i = 0; i < this.pins; i++) {
/* 271 */       g.fillRect(i * 8 + 1, 2, 5, 2);
/* 272 */       g.fillRect(i * 8 + 1, 27, 5, 2);
/*     */     }
/*     */ 
/* 275 */     g.setColor(slidecolor);
/* 276 */     for (int i = 0; i < this.pins; i++) {
/* 277 */       g.fillRect(i * 8 + 1, 8, 5, 14);
/*     */     }
/*     */ 
/* 280 */     g.setColor(switchcolor);
/* 281 */     for (int i = 0; i < this.pins; i++)
/* 282 */       if (this.states[i]) g.fillRect(i * 8 + 3, 9, 2, 6); else
/* 283 */         g.fillRect(i * 8 + 3, 15, 2, 6);
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 295 */     return this.pins * 8;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 299 */     return 32;
/*     */   }
/*     */ 
/*     */   public Rectangle getBounds()
/*     */   {
/* 305 */     return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Dipswitch
 * JD-Core Version:    0.6.2
 */