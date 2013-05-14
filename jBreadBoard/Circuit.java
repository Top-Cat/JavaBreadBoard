/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public class Circuit extends JLayeredPane
/*     */ {
/*  29 */   private JBreadBoard jBreadBoard = null;
/*  30 */   private JBreadBoardEventSimulation simulation = new JBreadBoardEventSimulation(this);
/*  31 */   private Trace trace = new Trace(this);
/*     */ 
/*  36 */   public static final Integer BoardLayer = new Integer(100);
/*     */ 
/*  40 */   public static final Integer WireLayer = new Integer(1000);
/*     */   private Timer timer;
/*  46 */   private boolean issimulating = false;
/*     */ 
/*  51 */   public BreadBoard BBselect = null;
/*     */ 
/*  55 */   public CircuitSelection selected = null;
/*     */ 
/*  59 */   public boolean hiddenwires = false;
/*     */ 
/*     */   public void hideWires()
/*     */   {
/*  65 */     if (!this.hiddenwires) {
/*  66 */       this.hiddenwires = true;
/*     */ 
/*  68 */       if ((this.jBreadBoard.getMode() != null) && (this.jBreadBoard.getMode().equals("wire"))) {
/*  69 */         this.jBreadBoard.setMode("move", "OK");
/*     */       }
/*  71 */       Component[] components = getComponentsInLayer(WireLayer);
/*     */ 
/*  73 */       for (int i = 0; i < components.length; i++) {
/*  74 */         if ((components[i] instanceof Wire)) {
/*  75 */           Wire wire = (Wire)components[i];
/*  76 */           wire.setVisible(false);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  81 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unhideWires()
/*     */   {
/*  89 */     if (this.hiddenwires == true) {
/*  90 */       this.hiddenwires = false;
/*     */ 
/*  92 */       Component[] components = getComponentsInLayer(WireLayer);
/*     */ 
/*  94 */       for (int i = 0; i < components.length; i++) {
/*  95 */         if ((components[i] instanceof Wire)) {
/*  96 */           Wire wire = (Wire)components[i];
/*  97 */           wire.setVisible(true);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 102 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Trace getTrace()
/*     */   {
/* 109 */     return this.trace;
/*     */   }
/*     */ 
/*     */   public void updateTrace()
/*     */   {
/* 116 */     this.trace.simulate();
/*     */   }
/*     */ 
/*     */   public boolean isSimulating()
/*     */   {
/* 121 */     return this.issimulating;
/*     */   }
/*     */ 
/*     */   public JBreadBoardEventSimulation getSimulation()
/*     */   {
/* 128 */     return this.simulation;
/*     */   }
/*     */ 
/*     */   public long getSimTime()
/*     */   {
/* 135 */     return this.simulation.getCurrentTime();
/*     */   }
/*     */ 
/*     */   public void stepSimulation()
/*     */   {
/* 142 */     if ((!isSimulating()) || (!this.jBreadBoard.getMode().equals("sim"))) resetSimulation();
/* 143 */     this.issimulating = true;
/* 144 */     this.simulation.stepSimulation();
/* 145 */     this.jBreadBoard.setMode("sim", "In Simulation.  Current time = " + this.simulation.getCurrentTime() + "ns");
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 155 */     if (this.timer == null) {
/* 156 */       this.timer = new Timer(1, new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 158 */           Circuit.this.runFor(Circuit.this.jBreadBoard.getSimSpeed());
/*     */         }
/*     */       });
/*     */     }
/* 162 */     this.timer.start();
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */   {
/* 169 */     if (this.timer != null) this.timer.stop();
/*     */   }
/*     */ 
/*     */   public void runFor(int duration)
/*     */   {
/* 178 */     if ((!isSimulating()) || (!this.jBreadBoard.getMode().equals("sim"))) resetSimulation();
/* 179 */     this.issimulating = true;
/* 180 */     this.simulation.runFor(duration);
/* 181 */     this.jBreadBoard.setMode("sim", "In Simulation.  Current time = " + this.simulation.getCurrentTime() + "ns.");
/*     */   }
/*     */ 
/*     */   public void resetSimulation()
/*     */   {
/* 190 */     this.simulation = new JBreadBoardEventSimulation(this);
/* 191 */     Component[] components = getComponentsInLayer(BoardLayer);
/*     */ 
/* 193 */     this.trace.reset();
/*     */ 
/* 196 */     for (int i = 0; i < components.length; i++) {
/* 197 */       if ((components[i] instanceof Board)) {
/* 198 */         ((Board)components[i]).reset();
/*     */       }
/*     */     }
/* 201 */     this.issimulating = false;
/*     */   }
/*     */ 
/*     */   public JBreadBoard getJBreadBoard()
/*     */   {
/* 208 */     return this.jBreadBoard;
/*     */   }
/*     */ 
/*     */   public void select(CircuitSelection c)
/*     */   {
/* 215 */     CircuitSelection old = this.selected;
/* 216 */     this.selected = c;
/* 217 */     if (old != null) old.deselect();
/* 218 */     if (c != null) {
/* 219 */       c.select();
/* 220 */       if ((c instanceof Board)) {
/* 221 */         scrollRectToVisible(((Board)c).getBounds());
/*     */       }
/* 223 */       BreadBoard oldbb = this.BBselect;
/* 224 */       if ((c instanceof BreadBoard)) {
/* 225 */         this.BBselect = ((BreadBoard)c);
/* 226 */         if (oldbb != null)
/* 227 */           oldbb.repaint();
/*     */       }
/*     */     }
/* 230 */     this.jBreadBoard.getSelectPane().select(c);
/*     */   }
/*     */ 
/*     */   public void delete()
/*     */   {
/* 237 */     if (this.selected != null) {
/* 238 */       CircuitSelection cs = this.selected;
/* 239 */       cs.delete();
/*     */     }
/* 241 */     select(null);
/*     */   }
/*     */ 
/*     */   Circuit(JBreadBoard j)
/*     */   {
/* 251 */     this.jBreadBoard = j;
/* 252 */     setLayout(null);
/* 253 */     setPreferredSize(new Dimension(600, 400));
/* 254 */     addMouseListener(new WireDrawer(this));
/* 255 */     addMouseMotionListener(new WireDrawer(this));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 265 */     String string = new String("Circuit");
/*     */ 
/* 268 */     Component[] components = getComponentsInLayer(BoardLayer);
/* 269 */     for (int i = 0; i < components.length; i++)
/*     */     {
/* 271 */       string = string + "\n" + components[i].toString();
/*     */     }
/*     */ 
/* 276 */     for (int i = 0; i < components.length; i++) {
/* 277 */       if ((components[i] instanceof Board)) {
/* 278 */         Board board = (Board)components[i];
/*     */ 
/* 280 */         Iterator iterator = board.wires.iterator();
/*     */ 
/* 285 */         while (iterator.hasNext()) {
/* 286 */           Object obj = iterator.next();
/* 287 */           if ((obj instanceof Wire)) {
/* 288 */             Wire wire = (Wire)obj;
/* 289 */             string = string + "\n" + wire.toString();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 295 */     return string + "\n";
/*     */   }
/*     */ 
/*     */   public void add(Wire w)
/*     */   {
/* 302 */     add(w, WireLayer);
/*     */   }
/*     */ 
/*     */   public int getComponentCountinLayer(Integer i)
/*     */   {
/* 311 */     return getComponentCountInLayer(i.intValue());
/*     */   }
/*     */ 
/*     */   public Component[] getComponentsInLayer(Integer i)
/*     */   {
/* 319 */     return getComponentsInLayer(i.intValue());
/*     */   }
/*     */ 
/*     */   public Component getComponentAt(int x, int y, Integer layer)
/*     */   {
/* 326 */     int n = getComponentCountInLayer(layer.intValue());
/* 327 */     Component[] comps = getComponentsInLayer(layer.intValue());
/*     */ 
/* 329 */     for (int i = 0; i < n; i++)
/* 330 */       if (comps[i].contains(x - comps[i].getX(), y - comps[i].getY()))
/* 331 */         return comps[i];
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */   public Board getBoardAt(int x, int y)
/*     */   {
/* 339 */     return (Board)getComponentAt(x, y, BoardLayer);
/*     */   }
/*     */ 
/*     */   public Wire getWireAt(int x, int y)
/*     */   {
/* 346 */     return (Wire)getComponentAt(x, y, WireLayer);
/*     */   }
/*     */ 
/*     */   public Wire drawWire(Color col, int x1, int y1, int x2, int y2)
/*     */   {
/* 362 */     return extendWire(null, col, x1, y1, x2, y2);
/*     */   }
/*     */ 
/*     */   public Wire extendWire(Wire w, Color col, int x1, int y1, int x2, int y2)
/*     */   {
/* 376 */     boolean horizontal = true;
/*     */ 
/* 379 */     if (w != null) {
/* 380 */       x1 = w.getEndX();
/* 381 */       y1 = w.getEndY();
/*     */     }
/*     */     else {
/* 384 */       x1 = x1 - x1 % 8 + 4;
/* 385 */       y1 -= y1 % 8;
/*     */     }
/* 387 */     x2 = x2 - x2 % 8 + 4;
/* 388 */     y2 -= y2 % 8;
/*     */ 
/* 392 */     int difx = Math.abs(x1 - x2);
/* 393 */     int dify = Math.abs(y1 - y2);
/*     */ 
/* 395 */     if (dify > difx) {
/* 396 */       horizontal = false;
/*     */     }
/*     */ 
/* 399 */     if (w != null) {
/* 400 */       int x0 = w.getStartX();
/* 401 */       int y0 = w.getStartY();
/*     */ 
/* 403 */       if (horizontal) {
/* 404 */         if ((x1 > x0) && (x2 <= x1)) horizontal = false;
/* 405 */         else if ((x1 < x0) && (x2 >= x1)) horizontal = false;
/*     */       }
/* 407 */       else if ((y1 > y0) && (y2 <= y1)) horizontal = true;
/* 408 */       else if ((y1 < y0) && (y2 >= y1)) horizontal = true;
/*     */ 
/*     */     }
/*     */ 
/* 413 */     if (horizontal)
/* 414 */       y2 = y1;
/*     */     else {
/* 416 */       x2 = x1;
/*     */     }
/*     */ 
/* 420 */     if ((x2 == x1) && (y2 == y1)) {
/* 421 */       repaint();
/* 422 */       return null;
/*     */     }
/*     */ 
/* 425 */     Wire newsegment = new Wire(this, col, horizontal, x1, y1, x2, y2, w);
/* 426 */     add(newsegment);
/* 427 */     repaint();
/* 428 */     return newsegment;
/*     */   }
/*     */ 
/*     */   public boolean add(Board b)
/*     */   {
/* 440 */     Rectangle bounds = b.getBounds();
/* 441 */     Rectangle innerbounds = b.getInnerBounds();
/*     */ 
/* 444 */     boolean clear = true;
/* 445 */     Component[] comps = getComponentsInLayer(BoardLayer);
/* 446 */     int maxi = comps.length;
/*     */ 
/* 448 */     for (int i = 0; (clear) && (i < maxi); i++) {
/* 449 */       Board testboard = (Board)comps[i];
/* 450 */       if (innerbounds.intersects(testboard.getInnerBounds())) {
/* 451 */         clear = false;
/* 452 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 457 */     if (clear)
/*     */     {
/* 459 */       int X2 = bounds.x + bounds.width;
/* 460 */       int Y2 = bounds.y + bounds.height;
/*     */ 
/* 463 */       if (getPreferredSize().width < X2) {
/* 464 */         setPreferredSize(new Dimension(X2, getPreferredSize().height));
/* 465 */         revalidate();
/*     */       }
/* 467 */       if (getPreferredSize().height < Y2) {
/* 468 */         setPreferredSize(new Dimension(getPreferredSize().width, Y2));
/* 469 */         revalidate();
/*     */       }
/*     */ 
/* 472 */       add(b, BoardLayer);
/*     */     }
/* 474 */     return clear;
/*     */   }
/*     */ 
/*     */   public void place(Board b)
/*     */   {
/* 490 */     int y = 0;
/* 491 */     boolean added = false;
/*     */ 
/* 503 */     while (!added)
/*     */     {
/* 493 */       int x = 0;
/* 494 */       while ((!added) && (x < y)) {
/* 495 */         b.setLocation(x * 504, y * 168);
/* 496 */         added = add(b);
/* 497 */         x++;
/*     */       }
/* 499 */       y = 0;
/* 500 */       if ((!added) && (y <= x)) {
/* 501 */         b.setLocation(x * 504, y * 168);
/* 502 */         added = add(b);
/* 503 */         y++;
/*     */       }
/*     */     }
/* 506 */     select(b);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Circuit
 * JD-Core Version:    0.6.2
 */