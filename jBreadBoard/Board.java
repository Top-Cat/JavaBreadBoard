/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ 
/*     */ public abstract class Board extends JComponent
/*     */   implements CircuitSelection
/*     */ {
/*     */   private Circuit circuit;
/*  32 */   public LinkedList wires = new LinkedList();
/*     */ 
/*  36 */   public LinkedList interwires = new LinkedList();
/*     */ 
/*     */   public Board(Circuit c)
/*     */   {
/*  43 */     this.circuit = c;
/*  44 */     BoardDragListener bdl = new BoardDragListener(this);
/*  45 */     addMouseListener(bdl);
/*  46 */     addMouseMotionListener(bdl);
/*  47 */     BoardWireDrawer bwd = new BoardWireDrawer(this);
/*  48 */     addMouseListener(bwd);
/*  49 */     addMouseMotionListener(bwd);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  56 */     String string = new String("Board " + getClass().getName() + " " + getX() + " " + getY());
/*     */ 
/*  60 */     Component[] components = getComponents();
/*  61 */     for (int i = 0; i < components.length; i++) {
/*  62 */       string = new String(string + "\n" + components[i].toString());
/*     */     }
/*     */ 
/*  65 */     return string;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  73 */     Component[] components = getComponents();
/*  74 */     for (int i = 0; i < components.length; i++)
/*  75 */       if ((components[i] instanceof Device))
/*  76 */         ((Device)components[i]).reset();
/*     */   }
/*     */ 
/*     */   public void select() {
/*  80 */     repaint(); } 
/*  81 */   public void deselect() { repaint(); }
/*     */ 
/*     */   public void delete() {
/*  84 */     if ((getComponentCount() > 0) || (this.wires.size() > 0) || (this.interwires.size() > 0)) {
/*  85 */       Object[] options = { "Yes", "No" };
/*  86 */       int n = JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(this), "The selected Board is not empty.\nAre sure you want to delete it?", "WARNING", 0, 2, null, options, options[1]);
/*     */ 
/*  96 */       if (n == 0) {
/*  97 */         int maxi = this.wires.size();
/*  98 */         for (int i = 0; i < maxi; i++) {
/*  99 */           Wire wire = (Wire)this.wires.getFirst();
/* 100 */           wire.delete();
/* 101 */           this.wires.remove(wire);
/*     */         }
/* 103 */         maxi = this.interwires.size();
/* 104 */         for (int i = 0; i < maxi; i++) {
/* 105 */           Wire wire = (Wire)this.interwires.getFirst();
/* 106 */           wire.delete();
/* 107 */           this.interwires.remove(wire);
/*     */         }
/* 109 */         this.circuit.remove(this);
/* 110 */         this.circuit.repaint();
/* 111 */         this.circuit.BBselect = null;
/*     */       }
/*     */     } else {
/* 114 */       this.circuit.remove(this);
/* 115 */       this.circuit.repaint();
/* 116 */       this.circuit.BBselect = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract boolean isHole(int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract Node getNodeAt(int paramInt1, int paramInt2);
/*     */ 
/*     */   public Rectangle getInnerBounds()
/*     */   {
/* 135 */     return getBounds();
/*     */   }
/*     */ 
/*     */   public Circuit getCircuit()
/*     */   {
/* 142 */     return this.circuit;
/*     */   }
/*     */ 
/*     */   private class BoardDragListener extends MouseInputAdapter
/*     */   {
/*     */     private int XFudge;
/*     */     private int YFudge;
/* 157 */     private int GridX = 168;
/* 158 */     private int GridY = 168;
/*     */     private Board board;
/*     */ 
/*     */     BoardDragListener(Board b)
/*     */     {
/* 163 */       this.board = b;
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e) {
/* 167 */       this.board.getCircuit().select(this.board);
/* 168 */       this.XFudge = e.getX();
/* 169 */       this.YFudge = e.getY();
/*     */ 
/* 172 */       Node node = this.board.getNodeAt(e.getX(), e.getY());
/* 173 */       if ((node != null) && (Board.this.getCircuit().isSimulating()))
/* 174 */         this.board.getCircuit().getJBreadBoard().setProbebar(node.getPotential().toTTL());
/*     */       else
/* 176 */         this.board.getCircuit().getJBreadBoard().setProbebar("");
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 181 */       if (Board.this.getCircuit().getJBreadBoard().getMode().equals("move"))
/*     */       {
/* 183 */         Circuit circuit = this.board.getCircuit();
/*     */ 
/* 187 */         Vector boards = new Vector();
/* 188 */         int boardsIndex = 0;
/*     */ 
/* 192 */         boards.add(this.board);
/*     */ 
/* 194 */         while (boardsIndex < boards.size()) {
/* 195 */           Board current = (Board)boards.elementAt(boardsIndex);
/* 196 */           for (int i = 0; i < current.interwires.size(); i++) {
/* 197 */             Wire wire = (Wire)current.interwires.get(i);
/* 198 */             Board temp = circuit.getBoardAt(wire.getEndPiece().getX(), wire.getEndPiece().getY());
/* 199 */             if (!boards.contains(temp))
/* 200 */               boards.add(temp);
/* 201 */             temp = circuit.getBoardAt(wire.getStartPiece().getX(), wire.getStartPiece().getY());
/* 202 */             if (!boards.contains(temp))
/* 203 */               boards.add(temp);
/*     */           }
/* 205 */           boardsIndex++;
/*     */         }
/*     */ 
/* 209 */         Point[] locs = new Point[boards.size()];
/* 210 */         int[] X = new int[boards.size()];
/* 211 */         int[] Y = new int[boards.size()];
/*     */ 
/* 214 */         for (int i = 0; i < boards.size(); i++) {
/* 215 */           locs[i] = ((Board)boards.elementAt(i)).getLocation();
/* 216 */           X[i] = (locs[i].x + e.getX() - this.XFudge);
/* 217 */           Y[i] = (locs[i].y + e.getY() - this.YFudge);
/*     */         }
/*     */ 
/* 221 */         boolean addboard = true;
/*     */ 
/* 223 */         for (int i = 0; i < boards.size(); i++) {
/* 224 */           if (X[i] < 0)
/* 225 */             addboard = false;
/* 226 */           if (Y[i] < 0) {
/* 227 */             addboard = false;
/*     */           }
/*     */ 
/* 230 */           if (X[i] % this.GridX < this.GridX / 2) X[i] -= X[i] % this.GridX; else
/* 231 */             X[i] = (X[i] + this.GridX - X[i] % this.GridX);
/* 232 */           if (Y[i] % this.GridY < this.GridY / 2) Y[i] -= Y[i] % this.GridY; else {
/* 233 */             Y[i] = (Y[i] + this.GridY - Y[i] % this.GridY);
/*     */           }
/*     */ 
/* 237 */           circuit.remove((Board)boards.elementAt(i));
/*     */         }
/* 239 */         circuit.repaint();
/*     */ 
/* 243 */         for (int i = 0; i < boards.size(); i++) {
/* 244 */           ((Board)boards.elementAt(i)).setLocation(X[i], Y[i]);
/* 245 */           addboard = (addboard) && (circuit.add((Board)boards.elementAt(i)));
/*     */         }
/*     */ 
/* 250 */         if (!addboard) {
/* 251 */           for (int i = 0; i < boards.size(); i++)
/* 252 */             circuit.remove((Board)boards.elementAt(i));
/* 253 */           circuit.repaint();
/* 254 */           for (int i = 0; i < boards.size(); i++) {
/* 255 */             ((Board)boards.elementAt(i)).setLocation(locs[i]);
/* 256 */             circuit.add((Board)boards.elementAt(i));
/*     */           }
/*     */         }
/*     */ 
/* 260 */         if (!this.board.getLocation().equals(locs[0]))
/* 261 */           for (int i = 0; i < boards.size(); i++) {
/* 262 */             Board temp = (Board)boards.elementAt(i);
/* 263 */             for (int j = 0; j < temp.wires.size(); j++) {
/* 264 */               Wire wire = (Wire)temp.wires.get(j);
/* 265 */               wire.moveWire(X[i] - locs[i].x, Y[i] - locs[i].y);
/*     */             }
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Board
 * JD-Core Version:    0.6.2
 */