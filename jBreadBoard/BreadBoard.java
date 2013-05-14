/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class BreadBoard extends Board
/*     */ {
/*     */   private ImageIcon graphic;
/*  27 */   private Node[] nodes = new Node[550];
/*     */ 
/*  56 */   private static final Rectangle[] holes = { new Rectangle(76, 40, 376, 40), new Rectangle(76, 96, 376, 40), new Rectangle(76, 16, 40, 8), new Rectangle(124, 16, 40, 8), new Rectangle(172, 16, 40, 8), new Rectangle(220, 16, 40, 8), new Rectangle(268, 16, 40, 8), new Rectangle(316, 16, 40, 8), new Rectangle(364, 16, 40, 8), new Rectangle(412, 16, 40, 8), new Rectangle(76, 152, 40, 8), new Rectangle(124, 152, 40, 8), new Rectangle(172, 152, 40, 8), new Rectangle(220, 152, 40, 8), new Rectangle(268, 152, 40, 8), new Rectangle(316, 152, 40, 8), new Rectangle(364, 152, 40, 8), new Rectangle(412, 152, 40, 8) };
/*     */ 
/*     */   public String getDiagram()
/*     */   {
/*  29 */     return "images/sboard.gif";
/*     */   }
/*  31 */   public String getInfo() { return "Component Name: \nBreadBoard\n\nJBreadBoard library name: \n" + getClass().getName().substring(12) + ".class"; }
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/*  38 */     super.reset();
/*  39 */     for (int i = 0; i < this.nodes.length; i++) {
/*  40 */       this.nodes[i].setPotential("NC", -1L);
/*     */     }
/*     */ 
/*  44 */     this.nodes[0].setType("OUT");
/*  45 */     this.nodes[510].setType("OUT");
/*  46 */     getCircuit().getSimulation().addEvent(this.nodes[0], 0, "HIGH");
/*  47 */     getCircuit().getSimulation().addEvent(this.nodes[510], 0, "LOW");
/*     */   }
/*     */ 
/*     */   public Rectangle getInnerBounds()
/*     */   {
/*  52 */     return new Rectangle(getX() + 12, getY(), 504, 168);
/*     */   }
/*     */ 
/*     */   public boolean isHole(int x, int y)
/*     */   {
/*  78 */     boolean ishole = false;
/*     */ 
/*  80 */     for (int i = 0; (!ishole) && (i < holes.length); i++) {
/*  81 */       if (holes[i].contains(x, y)) ishole = true;
/*     */     }
/*  83 */     return ishole;
/*     */   }
/*     */ 
/*     */   public Node getNodeAt(int x, int y)
/*     */   {
/*  89 */     int X = (x - 76) / 8;
/*  90 */     int Y = (y - 40) / 8 * 47 + 40;
/*     */ 
/*  92 */     if ((x >= 76) && (x < 452)) {
/*  93 */       if ((y >= 40) && (y < 80)) return this.nodes[(X + Y)];
/*  94 */       if ((y >= 96) && (y < 136)) return this.nodes[(X + Y - 94)];
/*  95 */       if ((y >= 16) && (y < 24)) {
/*  96 */         if (x < 116) return this.nodes[X];
/*  97 */         if ((x >= 124) && (x < 164)) return this.nodes[(X - 1)];
/*  98 */         if ((x >= 172) && (x < 212)) return this.nodes[(X - 2)];
/*  99 */         if ((x >= 220) && (x < 260)) return this.nodes[(X - 3)];
/* 100 */         if ((x >= 268) && (x < 308)) return this.nodes[(X - 4)];
/* 101 */         if ((x >= 316) && (x < 356)) return this.nodes[(X - 5)];
/* 102 */         if ((x >= 364) && (x < 404)) return this.nodes[(X - 6)];
/* 103 */         if (x >= 412) return this.nodes[(X - 7)];
/*     */       }
/* 105 */       else if ((y >= 152) && (y < 160)) {
/* 106 */         if (x < 116) return this.nodes[(X + 510)];
/* 107 */         if ((x >= 124) && (x < 164)) return this.nodes[(X + 510 - 1)];
/* 108 */         if ((x >= 172) && (x < 212)) return this.nodes[(X + 510 - 2)];
/* 109 */         if ((x >= 220) && (x < 260)) return this.nodes[(X + 510 - 3)];
/* 110 */         if ((x >= 268) && (x < 308)) return this.nodes[(X + 510 - 4)];
/* 111 */         if ((x >= 316) && (x < 356)) return this.nodes[(X + 510 - 5)];
/* 112 */         if ((x >= 364) && (x < 404)) return this.nodes[(X + 510 - 6)];
/* 113 */         if (x >= 412) return this.nodes[(X + 510 - 7)];
/*     */       }
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public void add(LED l)
/*     */   {
/* 128 */     if (l != null) {
/* 129 */       boolean clear = false;
/* 130 */       Rectangle dest = new Rectangle(76, 72, l.getWidth(), l.getHeight());
/*     */ 
/* 132 */       for (int i = 0; (!clear) && (i < 47); i++) {
/* 133 */         dest.x = (76 + i * 8);
/* 134 */         Component[] components = getComponents();
/* 135 */         clear = true;
/* 136 */         for (int j = 0; (clear) && (j < components.length); j++) {
/* 137 */           clear = !dest.intersects(components[j].getBounds());
/*     */         }
/*     */       }
/*     */ 
/* 141 */       if (clear) {
/* 142 */         l.setLocation(dest.x, dest.y);
/* 143 */         add((Component) l);
/* 144 */         repaint();
/* 145 */         l.updateConnections();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void add(Probe p)
/*     */   {
/* 159 */     if (p != null) {
/* 160 */       boolean clear = false;
/* 161 */       Rectangle dest = new Rectangle(76, 40, p.getWidth(), p.getHeight());
/*     */ 
/* 163 */       for (int i = 0; (!clear) && (i < 47); i++) {
/* 164 */         dest.x = (76 + i * 8);
/* 165 */         Component[] components = getComponents();
/* 166 */         clear = true;
/* 167 */         for (int j = 0; (clear) && (j < components.length); j++) {
/* 168 */           clear = !dest.intersects(components[j].getBounds());
/*     */         }
/*     */       }
/*     */ 
/* 172 */       if (clear) {
/* 173 */         p.setLocation(dest.x, dest.y);
/* 174 */         add((Component) p);
/* 175 */         repaint();
/* 176 */         p.updateConnections();
/*     */       } else {
/* 178 */         getCircuit().getTrace().removeProbe(p);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void add(Dipswitch d)
/*     */   {
/* 192 */     if (d != null) {
/* 193 */       boolean clear = false;
/* 194 */       Rectangle dest = new Rectangle(76, 72, d.getWidth(), d.getHeight());
/*     */ 
/* 196 */       for (int i = 0; (!clear) && (i <= 47 - d.getWidth() / 8); i++) {
/* 197 */         dest.x = (76 + i * 8);
/* 198 */         Component[] components = getComponents();
/* 199 */         clear = true;
/* 200 */         for (int j = 0; (clear) && (j < components.length); j++) {
/* 201 */           clear = !dest.intersects(components[j].getBounds());
/*     */         }
/*     */       }
/*     */ 
/* 205 */       if (clear) {
/* 206 */         d.setLocation(dest.x, dest.y);
/* 207 */         add((Component) d);
/* 208 */         repaint();
/* 209 */         d.updateConnections();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean add(Chip c)
/*     */   {
/* 225 */     return add(c, 0);
/*     */   }
/*     */ 
/*     */   public boolean add(Chip c, int Xpos)
/*     */   {
/* 241 */     boolean success = false;
/*     */ 
/* 243 */     Xpos = Xpos - Xpos % 8 + 4;
/*     */ 
/* 245 */     if (Xpos < 76) Xpos = 76;
/* 246 */     if (Xpos > 452 - c.getWidth()) Xpos = 452 - c.getWidth();
/*     */ 
/* 249 */     Rectangle dest = new Rectangle(Xpos, 88 - c.getHeight() / 2, c.getWidth(), c.getHeight());
/*     */ 
/* 251 */     while ((!success) && (dest.x + dest.width <= 452)) {
/* 252 */       success = true;
/* 253 */       int x2 = dest.x;
/*     */ 
/* 255 */       for (int i = 0; i < getComponentCount(); i++) {
/* 256 */         Rectangle bounds = getComponent(i).getBounds();
/* 257 */         if (bounds.intersects(dest)) {
/* 258 */           success = false;
/* 259 */           if (bounds.x + bounds.width > x2) {
/* 260 */             x2 = bounds.x + bounds.width;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 265 */       dest.x = x2;
/*     */     }
/*     */ 
/* 271 */     if (success) {
/* 272 */       add((Component) c);
/* 273 */       c.setBounds(dest);
/* 274 */       if (!c.tryupdateConnections()) {
/* 275 */         c.delete();
/* 276 */         if (dest.x < 452 - c.getWidth()) return add(c, dest.x + 8);
/* 277 */         return false;
/*     */       }
/*     */     }
/* 280 */     return success;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 287 */     super.paintComponent(g);
/* 288 */     if (getCircuit().BBselect == this)
/* 289 */       this.graphic.paintIcon(this, g, 0, -180);
/*     */     else
/* 291 */       this.graphic.paintIcon(this, g, 0, 0);
/*     */   }
/*     */ 
/*     */   private void initialiseNodes()
/*     */   {
/* 299 */     int i = 0;
/* 300 */     int x = 0;
/* 301 */     int y = 0;
/*     */ 
/* 304 */     this.nodes[0] = new Node();
/* 305 */     for (i = 1; i < 40; i++) {
/* 306 */       this.nodes[i] = new Node();
/* 307 */       new Potential(this.nodes[i], this.nodes[0]);
/*     */     }
/*     */ 
/* 312 */     for (x = 0; x < 47; x++) {
/* 313 */       this.nodes[(40 + x)] = new Node();
/* 314 */       for (y = 1; y < 5; y++) {
/* 315 */         this.nodes[(40 + x + y * 47)] = new Node();
/* 316 */         new Potential(this.nodes[(40 + x + y * 47)], this.nodes[(40 + x)]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 321 */     for (x = 0; x < 47; x++) {
/* 322 */       this.nodes[(275 + x)] = new Node();
/* 323 */       for (y = 1; y < 5; y++) {
/* 324 */         this.nodes[(275 + x + y * 47)] = new Node();
/* 325 */         new Potential(this.nodes[(275 + x + y * 47)], this.nodes[(275 + x)]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 330 */     this.nodes[510] = new Node();
/* 331 */     for (i = 511; i < 550; i++) {
/* 332 */       this.nodes[i] = new Node();
/* 333 */       new Potential(this.nodes[i], this.nodes[510]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public BreadBoard(Circuit c)
/*     */   {
/* 342 */     super(c);
/* 343 */     this.graphic = c.getJBreadBoard().getImageIcon("images/board.gif");
/* 344 */     initialiseNodes();
/* 345 */     setSize(516, 180);
/* 346 */     setOpaque(false);
/*     */ 
/* 349 */     reset();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.BreadBoard
 * JD-Core Version:    0.6.2
 */