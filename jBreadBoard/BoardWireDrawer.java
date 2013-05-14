/*    */ package jBreadBoard;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ 
/*    */ public class BoardWireDrawer extends WireDrawer
/*    */ {
/*    */   private Board board;
/*    */ 
/*    */   public BoardWireDrawer(Board b)
/*    */   {
/* 37 */     super(b.getCircuit());
/* 38 */     this.board = b;
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e)
/*    */   {
/* 50 */     if (getCircuit().getJBreadBoard().getMode().equals("wire"))
/*    */     {
/* 52 */       int x = e.getX() - e.getX() % 8;
/* 53 */       int y = e.getY() - e.getY() % 8;
/*    */ 
/* 57 */       if (e.getX() % 8 < 4) x -= 4; else {
/* 58 */         x += 4;
/*    */       }
/*    */ 
/* 61 */       if (this.board.isHole(x, y)) {
/* 62 */         Circuit circuit = getCircuit();
/* 63 */         x += this.board.getX();
/* 64 */         y += this.board.getY();
/* 65 */         startx = x;
/* 66 */         starty = y;
/* 67 */         current = null;
/* 68 */         last = null;
/* 69 */         circuit.getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
/*    */       }
/*    */     }
/* 72 */     else if (getCircuit().getJBreadBoard().getMode().equals("drawingwire"))
/*    */     {
/* 74 */       if ((current != null) && (getCircuit() != current.getCircuit())) {
/* 75 */         current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
/* 76 */         return;
/*    */       }
/*    */ 
/* 79 */       if ((e.getClickCount() == 1) && (current != null)) {
/* 80 */         last = current;
/* 81 */         current = null;
/*    */       }
/* 83 */       else if ((last != null) && (e.getClickCount() == 2))
/*    */       {
/* 85 */         if (current != null) last = current;
/* 86 */         current = null;
/*    */ 
/* 88 */         if (!this.board.isHole(last.getEndX() - this.board.getX(), last.getEndY() - this.board.getY()))
/*    */         {
/* 90 */           return;
/*    */         }
/*    */ 
/* 93 */         if ((last.prev == null) && (last.getStartX() == last.getEndX()) && (last.getStartY() == last.getEndX()))
/*    */         {
/* 96 */           last.delete();
/* 97 */           getCircuit().getJBreadBoard().setMessage("Wire Removed. Wiring Mode. Click to Start Wire");
/*    */         }
/*    */         else {
/* 100 */           last.fixate();
/*    */         }
/*    */ 
/* 103 */         String message = "Wiring Mode. Click to start Wire";
/*    */ 
/* 105 */         if (last.getParent() == null) {
/* 106 */           message = getCircuit().getJBreadBoard().getMessage();
/*    */         }
/* 108 */         last = null;
/* 109 */         getCircuit().getJBreadBoard().setMode("wire", message);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.BoardWireDrawer
 * JD-Core Version:    0.6.2
 */