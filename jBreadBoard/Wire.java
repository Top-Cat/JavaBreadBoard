/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.LinkedList;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class Wire extends JComponent
/*     */   implements CircuitSelection
/*     */ {
/*  31 */   private Boolean locset = Boolean.valueOf(false);
/*     */   private int xloc;
/*     */   private int yloc;
/*     */   private int x1;
/*     */   private int y1;
/*     */   private int x2;
/*     */   private int y2;
/*     */   private int width;
/*     */   private int height;
/*  42 */   public Wire next = null;
/*     */ 
/*  46 */   public Wire prev = null;
/*     */ 
/*  50 */   private boolean Horizontal = true;
/*     */ 
/*  53 */   private Color color = Color.red;
/*  54 */   private Color selectedcolor = Color.cyan;
/*  55 */   private Color maincolor = this.color;
/*  56 */   private Color outlinecolor = Color.black;
/*  57 */   private Color tipcolor = Color.yellow;
/*     */   private Circuit circuit;
/*     */   private Potential potential;
/* 147 */   static final MouseInputListener wireselect = new MouseInputAdapter() {
/*     */     public void mouseMoved(MouseEvent e) {
/* 149 */       Wire wire = (Wire)e.getComponent();
/*     */ 
/* 151 */       if (wire.getCircuit().getJBreadBoard().getMode().equals("drawingwire")) {
/* 152 */         WireDrawer wd = new WireDrawer(wire.getCircuit());
/* 153 */         wd.mouseMoved(e);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 159 */       Wire wire = (Wire)e.getComponent();
/* 160 */       String mode = wire.getCircuit().getJBreadBoard().getMode();
/*     */       int clickyend;
/*     */       int clickxend;
/* 164 */       if (wire.Horizontal) {
/* 165 */         clickxend = e.getX() + wire.getX();
/* 166 */         clickxend = clickxend - clickxend % 8 - 4;
/* 167 */         clickyend = wire.getY();
/*     */       } else {
/* 169 */         clickyend = e.getY() + wire.getY();
/* 170 */         clickyend -= clickyend % 8;
/* 171 */         clickxend = wire.getX();
/*     */       }
/*     */ 
/* 174 */       if ((!mode.equals("sim")) && (!mode.equals("drawingwire")) && (wire.getStartPiece() == wire) && (e.getClickCount() == 2) && (clickxend == wire.x1) && (clickyend == wire.y1))
/*     */       {
/* 183 */         Wire start = wire;
/* 184 */         Wire end = wire.getEndPiece();
/*     */ 
/* 186 */         wire.deselect();
/*     */ 
/* 189 */         while (wire != null) {
/* 190 */           wire.removeMouseListener(Wire.wireselect);
/* 191 */           wire = wire.next;
/*     */         }
/*     */ 
/* 194 */         wire = start;
/*     */ 
/* 197 */         while (wire != null) {
/* 198 */           Wire temp = wire.next;
/* 199 */           wire.reverse();
/* 200 */           wire = temp;
/*     */         }
/*     */ 
/* 204 */         if (end.potential != null) {
/* 205 */           end.potential.delete();
/* 206 */           end.getCircuit().getSimulation().addEvent(end.potential.getNode1(), 0);
/* 207 */           end.getCircuit().getSimulation().addEvent(end.potential.getNode2(), 0);
/*     */         }
/*     */ 
/* 210 */         Board board1 = start.getCircuit().getBoardAt(start.getStartX(), start.getStartY());
/* 211 */         Board board2 = end.getCircuit().getBoardAt(end.getEndX(), end.getEndY());
/*     */ 
/* 213 */         if (board1 != board2) {
/* 214 */           board1.interwires.remove(end);
/* 215 */           board2.interwires.remove(end);
/*     */         }
/*     */ 
/* 218 */         board2.wires.remove(end);
/*     */ 
/* 220 */         Wire temp = start; start = end; end = temp;
/*     */ 
/* 223 */         WireDrawer.color = start.color;
/* 224 */         WireDrawer.startx = start.x1;
/* 225 */         WireDrawer.starty = start.y1;
/* 226 */         WireDrawer.last = end.prev;
/* 227 */         WireDrawer.current = end;
/* 228 */         start.getCircuit().getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
/*     */       }
/* 232 */       else if ((!mode.equals("sim")) && (!mode.equals("drawingwire")) && (wire.next == null) && (e.getClickCount() == 2) && (clickxend == wire.x2) && (clickyend == wire.y2))
/*     */       {
/* 239 */         Wire start = wire.getStartPiece();
/* 240 */         Wire end = wire.getEndPiece();
/*     */ 
/* 242 */         wire = start;
/* 243 */         wire.deselect();
/*     */ 
/* 246 */         while (wire != null) {
/* 247 */           wire.removeMouseListener(Wire.wireselect);
/* 248 */           wire.removeMouseMotionListener(Wire.wireselect);
/* 249 */           wire = wire.next;
/*     */         }
/*     */ 
/* 253 */         if (end.potential != null) {
/* 254 */           end.potential.delete();
/* 255 */           end.getCircuit().getSimulation().addEvent(end.potential.getNode1(), 0);
/* 256 */           end.getCircuit().getSimulation().addEvent(end.potential.getNode2(), 0);
/*     */         }
/*     */ 
/* 259 */         Board board1 = start.getCircuit().getBoardAt(start.getStartX(), start.getStartY());
/* 260 */         Board board2 = end.getCircuit().getBoardAt(end.getEndX(), end.getEndY());
/*     */ 
/* 262 */         if (board1 != board2) {
/* 263 */           board1.interwires.remove(end);
/* 264 */           board2.interwires.remove(end);
/*     */         }
/*     */ 
/* 267 */         board2.wires.remove(end);
/*     */ 
/* 270 */         WireDrawer.color = start.color;
/* 271 */         WireDrawer.startx = start.x1;
/* 272 */         WireDrawer.starty = start.y1;
/* 273 */         WireDrawer.last = end.prev;
/* 274 */         WireDrawer.current = end;
/* 275 */         start.getCircuit().getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
/*     */       }
/*     */       else
/*     */       {
/* 279 */         wire.getCircuit().select(wire);
/*     */       }
/*     */     }
/* 147 */   };
/*     */ 
/*     */   public int getStartX()
/*     */   {
/*  34 */     if (this.locset.booleanValue()) return this.x1; return getX(); } 
/*  35 */   public int getEndX() { if (this.locset.booleanValue()) return this.x2; return getX(); } 
/*  36 */   public int getStartY() { if (this.locset.booleanValue()) return this.y1; return getY(); } 
/*  37 */   public int getEndY() { if (this.locset.booleanValue()) return this.y2; return getY();
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean b) {
/*  73 */     if (!b) setSize(0, 0); else
/*  74 */       setSize(this.width, this.height);
/*     */   }
/*     */ 
/*     */   public Circuit getCircuit()
/*     */   {
/*  79 */     return this.circuit;
/*     */   }
/*  81 */   public String getInfo() { return ""; } 
/*  82 */   public String getDiagram() { return null; } 
/*     */   public void select() {
/*  84 */     Wire wire = getStartPiece();
/*  85 */     while (wire != null) {
/*  86 */       wire.paintinverse();
/*  87 */       wire = wire.next;
/*     */     }
/*  89 */     getParent().repaint();
/*     */   }
/*     */   public void deselect() {
/*  92 */     Wire wire = getStartPiece();
/*  93 */     while (wire != null) {
/*  94 */       wire.paintnormal();
/*  95 */       wire = wire.next;
/*     */     }
/*  97 */     Component parent = getParent();
/*  98 */     if (parent != null) parent.repaint(); 
/*     */   }
/*     */ 
/* 101 */   public void delete() { Wire start = getStartPiece();
/* 102 */     Wire end = getEndPiece();
/*     */ 
/* 105 */     if (end.potential != null) {
/* 106 */       end.potential.delete();
/* 107 */       this.circuit.getSimulation().addEvent(end.potential.getNode1(), 0);
/* 108 */       this.circuit.getSimulation().addEvent(end.potential.getNode2(), 0);
/*     */     }
/*     */ 
/* 111 */     Board board1 = this.circuit.getBoardAt(start.getStartX(), start.getStartY());
/* 112 */     Board board2 = this.circuit.getBoardAt(end.getEndX(), end.getEndY());

/* 114 */     if (board1 != board2) {
/*     */        if (board1 != null) {
/* 115 */           board1.interwires.remove(end);
/*     */        }
/*     */        if (board2 != null) {
/* 116 */           board2.interwires.remove(end);
/*     */        }
/*     */     }

/*     */     if (board2 != null) {
/* 119 */        board2.wires.remove(end);
/*     */     }
/*     */ 
/* 121 */     while (start != null) {
/* 122 */       this.circuit.remove(start);
/* 123 */       start = start.next;
/*     */     }
/* 125 */     this.circuit.repaint();
/*     */   }
/*     */ 
/*     */   private void reverse()
/*     */   {
/* 132 */     Wire tempwire = this.next;
/* 133 */     this.next = this.prev;
/* 134 */     this.prev = tempwire;
/*     */ 
/* 137 */     int t = this.x1; this.x1 = this.x2; this.x2 = t;
/* 138 */     t = this.y1; this.y1 = this.y2; this.y2 = t;
/*     */   }
/*     */ 
/*     */   public void fixate()
/*     */   {
/* 289 */     Wire first = getStartPiece();
/* 290 */     Wire last = getEndPiece();
/*     */ 
/* 292 */     Board startboard = this.circuit.getBoardAt(first.getStartX(), first.getStartY());
/* 293 */     Board endboard = this.circuit.getBoardAt(last.getEndX(), last.getEndY());
/*     */ 
/* 296 */     Node startnode = startboard.getNodeAt(first.getStartX() - startboard.getX(), first.getStartY() - startboard.getY());
/*     */ 
/* 298 */     Node endnode = endboard.getNodeAt(last.getEndX() - endboard.getX(), last.getEndY() - endboard.getY());
/*     */ 
/* 301 */     boolean ok = true;
/*     */ 
/* 303 */     if (NodeType.conflicts(startnode.getType(), endnode.getType())) {
/* 304 */       ok = false;
/* 305 */       this.circuit.getJBreadBoard().setMessage("Wire Removed. That wire created a potential short circuit.");
/*     */     }
/*     */ 
/* 309 */     if (startnode.getPotential() == endnode.getPotential()) {
/* 310 */       ok = false;
/* 311 */       this.circuit.getJBreadBoard().setMessage("Wire Removed. That wire created a connection that was already made.");
/*     */     }
/*     */ 
/* 315 */     if (ok) {
/* 316 */       last.potential = new Potential(startnode, endnode);
/* 317 */       this.circuit.getSimulation().addEvent(startnode, 0);
/*     */ 
/* 319 */       endboard.wires.add(last);
/*     */ 
/* 321 */       if (endboard != startboard) {
/* 322 */         endboard.interwires.add(last);
/* 323 */         startboard.interwires.add(last);
/*     */       }
/*     */ 
/* 327 */       Wire temp = last;
/* 328 */       while (temp != null) {
/* 329 */         temp.addMouseListener(wireselect);
/* 330 */         temp.addMouseMotionListener(wireselect);
/* 331 */         temp = temp.prev;
/*     */       }
/*     */     } else {
/* 334 */       last.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Wire getStartPiece()
/*     */   {
/* 342 */     Wire wire = this;
/* 343 */     while (wire.prev != null)
/* 344 */       wire = wire.prev;
/* 345 */     return wire;
/*     */   }
/*     */ 
/*     */   public Wire getEndPiece()
/*     */   {
/* 352 */     Wire wire = this;
/* 353 */     while (wire.next != null)
/* 354 */       wire = wire.next;
/* 355 */     return wire;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 362 */     int count = 0;
/* 363 */     Wire wire = getStartPiece();
/* 364 */     while (wire != null) {
/* 365 */       wire = wire.next;
/* 366 */       count++;
/*     */     }
/* 368 */     return count;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 375 */     String string = "Wire";
/* 376 */     Wire wire = getStartPiece();
/* 377 */     string = string + " " + this.color.getRed();
/* 378 */     string = string + " " + this.color.getGreen();
/* 379 */     string = string + " " + this.color.getBlue();
/* 380 */     string = string + " " + wire.x1;
/* 381 */     string = string + " " + wire.y1;
/*     */ 
/* 383 */     while (wire != null) {
/* 384 */       string = string + " " + wire.x2 + " " + wire.y2;
/* 385 */       wire = wire.next;
/*     */     }
/* 387 */     return string;
/*     */   }
/*     */ 
/*     */   public void setLocation(int x, int y) {
/* 391 */     super.setLocation(x, y);
/*     */ 
/* 393 */     if (this.locset.booleanValue()) {
/* 394 */       int dx = x - this.xloc;
/* 395 */       int dy = y - this.yloc;
/*     */ 
/* 397 */       this.x1 += dx;
/* 398 */       this.x2 += dx;
/* 399 */       this.y1 += dy;
/* 400 */       this.y2 += dy;
/*     */     } else {
/* 402 */       this.x1 = x; this.x2 = x;
/* 403 */       this.y1 = y; this.y2 = y;
/* 404 */       this.locset = Boolean.valueOf(true);
/*     */     }
/* 406 */     this.xloc = x;
/* 407 */     this.yloc = y;
/*     */   }
/*     */ 
/*     */   public void moveWire(int dx, int dy)
/*     */   {
/* 415 */     Wire wire = getStartPiece();
/* 416 */     while (wire != null) {
/* 417 */       wire.setLocation(wire.getX() + dx, wire.getY() + dy);
/* 418 */       wire = wire.next;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintinverse()
/*     */   {
/* 426 */     this.maincolor = this.selectedcolor;
/* 427 */     this.outlinecolor = Color.white;
/* 428 */     this.tipcolor = Color.blue;
/*     */   }
/*     */ 
/*     */   public void paintnormal()
/*     */   {
/* 435 */     this.maincolor = this.color;
/* 436 */     this.outlinecolor = Color.black;
/* 437 */     this.tipcolor = Color.yellow;
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 444 */     int middlestart = 8;
/* 445 */     int middleend = 8;
/* 446 */     super.paintComponent(g);
/*     */ 
/* 449 */     if (this.circuit.hiddenwires) return;
/*     */ 
/* 452 */     if ((this.width == 8) && (this.height == 8)) return;
/*     */ 
/* 455 */     if (this.prev == null) {
/* 456 */       paintStart(g);
/*     */     }
/* 458 */     else if (this.prev.Horizontal != this.Horizontal) {
/* 459 */       paintCorner(g);
/*     */     }
/* 462 */     else if ((this.x2 < this.x1) || (this.y2 < this.y1)) middleend = 0; else {
/* 463 */       middlestart = 0;
/*     */     }
/*     */ 
/* 468 */     if (this.Horizontal) {
/* 469 */       if (this.width > middlestart + middleend) {
/* 470 */         g.setColor(this.outlinecolor);
/* 471 */         g.drawLine(middlestart, 1, this.width - middleend - 1, 1);
/* 472 */         g.drawLine(middlestart, 5, this.width - middleend - 1, 5);
/* 473 */         g.setColor(this.maincolor);
/* 474 */         g.fillRect(middlestart, 2, this.width - middleend - middlestart, 3);
/*     */       }
/*     */     }
/* 477 */     else if (this.height > middlestart + middleend) {
/* 478 */       g.setColor(this.outlinecolor);
/* 479 */       g.drawLine(1, middlestart, 1, this.height - middleend - 1);
/* 480 */       g.drawLine(5, middlestart, 5, this.height - middleend - 1);
/* 481 */       g.setColor(this.maincolor);
/* 482 */       g.fillRect(2, middlestart, 3, this.height - middleend - middlestart);
/*     */     }
/*     */ 
/* 486 */     if (this.next == null) paintEnd(g);
/*     */   }
/*     */ 
/*     */   private void paintStart(Graphics g)
/*     */   {
/* 493 */     int relx = this.x1 - this.xloc;
/* 494 */     int rely = this.y1 - this.yloc;
/*     */ 
/* 496 */     if (this.Horizontal)
/*     */     {
/* 498 */       if (this.x2 > this.x1) {
/* 499 */         g.setColor(this.outlinecolor);
/* 500 */         g.drawRect(relx + 2, rely + 2, 2, 2);
/* 501 */         g.drawRect(relx + 4, rely + 1, 3, 4);
/* 502 */         g.setColor(this.tipcolor);
/* 503 */         g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
/* 504 */         g.setColor(this.maincolor);
/* 505 */         g.fillRect(relx + 5, rely + 2, 3, 3);
/*     */       }
/*     */       else {
/* 508 */         g.setColor(this.outlinecolor);
/* 509 */         g.drawRect(relx + 3, rely + 2, 2, 2);
/* 510 */         g.drawRect(relx + 0, rely + 1, 3, 4);
/* 511 */         g.setColor(this.tipcolor);
/* 512 */         g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
/* 513 */         g.setColor(this.maincolor);
/* 514 */         g.fillRect(relx + 0, rely + 2, 3, 3);
/*     */       }
/*     */ 
/*     */     }
/* 519 */     else if (this.y2 < this.y1) {
/* 520 */       g.setColor(this.outlinecolor);
/* 521 */       g.drawRect(relx + 2, rely + 2, 2, 2);
/* 522 */       g.drawRect(relx + 1, rely + 0, 4, 2);
/* 523 */       g.setColor(this.tipcolor);
/* 524 */       g.drawLine(relx + 3, rely + 2, relx + 3, rely + 3);
/* 525 */       g.setColor(this.maincolor);
/* 526 */       g.fillRect(relx + 2, rely + 0, 3, 2);
/*     */     }
/*     */     else {
/* 529 */       g.setColor(this.outlinecolor);
/* 530 */       g.drawRect(relx + 2, rely + 2, 2, 2);
/* 531 */       g.drawRect(relx + 1, rely + 4, 4, 3);
/* 532 */       g.setColor(this.tipcolor);
/* 533 */       g.drawLine(relx + 3, rely + 3, relx + 3, rely + 4);
/* 534 */       g.setColor(this.maincolor);
/* 535 */       g.fillRect(relx + 2, rely + 5, 3, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintEnd(Graphics g)
/*     */   {
/* 545 */     int relx = this.x2 - this.xloc;
/* 546 */     int rely = this.y2 - this.yloc;
/*     */ 
/* 548 */     if (this.Horizontal)
/*     */     {
/* 550 */       if (this.x2 < this.x1) {
/* 551 */         g.setColor(this.outlinecolor);
/* 552 */         g.drawRect(relx + 2, rely + 2, 2, 2);
/* 553 */         g.drawRect(relx + 4, rely + 1, 3, 4);
/* 554 */         g.setColor(this.tipcolor);
/* 555 */         g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
/* 556 */         g.setColor(this.maincolor);
/* 557 */         g.fillRect(relx + 5, rely + 2, 3, 3);
/*     */       }
/*     */       else {
/* 560 */         g.setColor(this.outlinecolor);
/* 561 */         g.drawRect(relx + 3, rely + 2, 2, 2);
/* 562 */         g.drawRect(relx + 0, rely + 1, 3, 4);
/* 563 */         g.setColor(this.tipcolor);
/* 564 */         g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
/* 565 */         g.setColor(this.maincolor);
/* 566 */         g.fillRect(relx + 0, rely + 2, 3, 3);
/*     */       }
/*     */ 
/*     */     }
/* 571 */     else if (this.y2 > this.y1) {
/* 572 */       g.setColor(this.outlinecolor);
/* 573 */       g.drawRect(relx + 2, rely + 2, 2, 2);
/* 574 */       g.drawRect(relx + 1, rely + 0, 4, 2);
/* 575 */       g.setColor(this.tipcolor);
/* 576 */       g.drawLine(relx + 3, rely + 2, relx + 3, rely + 3);
/* 577 */       g.setColor(this.maincolor);
/* 578 */       g.fillRect(relx + 2, rely + 0, 3, 2);
/*     */     }
/*     */     else {
/* 581 */       g.setColor(this.outlinecolor);
/* 582 */       g.drawRect(relx + 2, rely + 2, 2, 2);
/* 583 */       g.drawRect(relx + 1, rely + 4, 4, 3);
/* 584 */       g.setColor(this.tipcolor);
/* 585 */       g.drawLine(relx + 3, rely + 3, relx + 3, rely + 4);
/* 586 */       g.setColor(this.maincolor);
/* 587 */       g.fillRect(relx + 2, rely + 5, 3, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void paintCorner(Graphics g)
/*     */   {
/* 598 */     int relx = this.x1 - this.xloc;
/* 599 */     int rely = this.y1 - this.yloc;
/*     */     int corner;
/* 602 */     if (this.Horizontal)
/*     */     {
/* 603 */       if (getEndX() > getStartX())
/*     */       {
/* 604 */         if (this.prev.getEndY() > this.prev.getStartY()) corner = 3; else
/* 605 */           corner = 1;
/*     */       }
/*     */       else
/*     */       {
/* 607 */         if (this.prev.getEndY() > this.prev.getStartY()) corner = 4; else
/* 608 */           corner = 2;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 611 */       if (getEndY() > getStartY())
/*     */       {
/* 612 */         if (this.prev.getEndX() > this.prev.getStartX()) corner = 2; else
/* 613 */           corner = 1;
/*     */       }
/*     */       else
/*     */       {
/* 615 */         if (this.prev.getEndX() > this.prev.getStartX()) corner = 4; else {
/* 616 */           corner = 3;
/*     */         }
/*     */       }
/*     */     }
/* 620 */     switch (corner) {
/*     */     case 1:
/* 622 */       g.setColor(this.maincolor);
/* 623 */       g.fillRect(relx + 3, rely + 2, 5, 4);
/* 624 */       g.fillRect(relx + 2, rely + 3, 3, 5);
/* 625 */       g.setColor(this.outlinecolor);
/* 626 */       g.drawLine(relx + 3, rely + 1, relx + 7, rely + 1);
/* 627 */       g.drawLine(relx + 2, rely + 2, relx + 2, rely + 2);
/* 628 */       g.drawLine(relx + 1, rely + 3, relx + 1, rely + 7);
/* 629 */       g.drawLine(relx + 6, rely + 5, relx + 7, rely + 5);
/* 630 */       g.drawLine(relx + 5, rely + 6, relx + 5, rely + 7);
/* 631 */       break;
/*     */     case 2:
/* 633 */       g.setColor(this.maincolor);
/* 634 */       g.fillRect(relx + 0, rely + 2, 4, 4);
/* 635 */       g.fillRect(relx + 2, rely + 3, 3, 5);
/* 636 */       g.setColor(this.outlinecolor);
/* 637 */       g.drawLine(relx + 0, rely + 1, relx + 3, rely + 1);
/* 638 */       g.drawLine(relx + 4, rely + 2, relx + 4, rely + 2);
/* 639 */       g.drawLine(relx + 5, rely + 3, relx + 5, rely + 7);
/* 640 */       g.drawLine(relx + 0, rely + 5, relx + 0, rely + 5);
/* 641 */       g.drawLine(relx + 1, rely + 6, relx + 1, rely + 7);
/* 642 */       break;
/*     */     case 3:
/* 644 */       g.setColor(this.maincolor);
/* 645 */       g.fillRect(relx + 2, rely + 0, 4, 4);
/* 646 */       g.fillRect(relx + 3, rely + 2, 5, 3);
/* 647 */       g.setColor(this.outlinecolor);
/* 648 */       g.drawLine(relx + 1, rely + 0, relx + 1, rely + 3);
/* 649 */       g.drawLine(relx + 5, rely + 0, relx + 5, rely + 0);
/* 650 */       g.drawLine(relx + 6, rely + 1, relx + 7, rely + 1);
/* 651 */       g.drawLine(relx + 2, rely + 4, relx + 2, rely + 4);
/* 652 */       g.drawLine(relx + 3, rely + 5, relx + 7, rely + 5);
/* 653 */       break;
/*     */     case 4:
/* 655 */       g.setColor(this.maincolor);
/* 656 */       g.fillRect(relx + 1, rely + 0, 4, 4);
/* 657 */       g.fillRect(relx + 0, rely + 2, 4, 3);
/* 658 */       g.setColor(this.outlinecolor);
/* 659 */       g.drawLine(relx + 1, rely + 0, relx + 1, rely + 0);
/* 660 */       g.drawLine(relx + 5, rely + 0, relx + 5, rely + 3);
/* 661 */       g.drawLine(relx + 0, rely + 1, relx + 0, rely + 1);
/* 662 */       g.drawLine(relx + 4, rely + 4, relx + 4, rely + 4);
/* 663 */       g.drawLine(relx + 0, rely + 5, relx + 3, rely + 5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Wire(Circuit c, Color col, boolean orientation, int px1, int py1, int px2, int py2, Wire previous)
/*     */   {
/* 678 */     this.circuit = c;
/*     */ 
/* 680 */     this.prev = previous;
/* 681 */     if (this.prev != null) {
/* 682 */       this.color = this.prev.color;
/* 683 */       px1 = this.prev.x2;
/* 684 */       py1 = this.prev.y2;
/* 685 */       this.prev.next = this;
/*     */     } else {
/* 687 */       this.color = col;
/*     */     }
/*     */ 
/* 690 */     this.Horizontal = orientation;
/* 691 */     if (this.Horizontal) {
/* 692 */       py2 = py1;
/* 693 */       this.height = 8;
/* 694 */       this.yloc = py1;
/*     */ 
/* 696 */       if (px2 > px1) {
/* 697 */         this.width = (8 + (px2 - px1));
/* 698 */         this.xloc = px1;
/*     */       } else {
/* 700 */         this.width = (8 + (px1 - px2));
/* 701 */         this.xloc = px2;
/*     */       }
/*     */     } else {
/* 704 */       px2 = px1;
/* 705 */       this.width = 8;
/* 706 */       this.xloc = px1;
/*     */ 
/* 708 */       if (py2 > py1) {
/* 709 */         this.height = (8 + (py2 - py1));
/* 710 */         this.yloc = py1;
/*     */       } else {
/* 712 */         this.height = (8 + (py1 - py2));
/* 713 */         this.yloc = py2;
/*     */       }
/*     */     }
/*     */ 
/* 717 */     setLocation(this.xloc, this.yloc);
/* 718 */     this.x1 = px1; this.x2 = px2; this.y1 = py1; this.y2 = py2;
/* 719 */     setSize(this.width, this.height);
/*     */ 
/* 722 */     if (this.color != null) {
/* 723 */       this.maincolor = this.color;
/* 724 */       this.selectedcolor = new Color(255 - this.color.getRed(), 255 - this.color.getGreen(), 255 - this.color.getBlue());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Wire
 * JD-Core Version:    0.6.2
 */