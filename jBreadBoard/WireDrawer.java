/*    */ package jBreadBoard;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.event.MouseInputAdapter;
/*    */ 
/*    */ public class WireDrawer extends MouseInputAdapter
/*    */ {
/* 29 */   static Color color = Color.red;
/*    */ 
/* 33 */   static int startx = 0; static int starty = 0;
/*    */ 
/* 38 */   static Wire last = null;
/*    */ 
/* 43 */   static Wire current = null;
/*    */   private Circuit circuit;
/*    */ 
/*    */   WireDrawer(Circuit c)
/*    */   {
/* 53 */     this.circuit = c;
/*    */   }
/*    */ 
/*    */   public Circuit getCircuit()
/*    */   {
/* 60 */     return this.circuit;
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent e)
/*    */   {
/* 67 */     if (this.circuit.getJBreadBoard().getMode().equals("drawingwire"))
/*    */     {
/* 69 */       if ((current != null) && (this.circuit != current.getCircuit())) {
/* 70 */         current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
/*    */       }
/* 72 */       else if ((last != null) && (current != null)) {
/* 73 */         last = current;
/* 74 */         current = null;
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void mouseMoved(MouseEvent e)
/*    */   {
/* 83 */     if (this.circuit.getJBreadBoard().getMode().equals("drawingwire"))
/*    */     {
/* 86 */       if ((current != null) && (this.circuit != current.getCircuit())) {
/* 87 */         current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
/* 88 */         return;
/*    */       }
/*    */ 
/* 91 */       if (current != null) {
/* 92 */         JComponent component = (JComponent)current.getParent();
/* 93 */         component.remove(current);
/* 94 */         current = null;
/*    */       }
/*    */       int x0;
/*    */       int y0;
/* 97 */       if (last != null) {
/* 98 */         x0 = last.getEndX();
/* 99 */         y0 = last.getEndY();
/*    */ 
/* 101 */         last.next = null;
/*    */       } else {
/* 103 */         x0 = startx;
/* 104 */         y0 = starty;
/*    */       }
/*    */       int y;
/*    */       int x;
/* 111 */       if ((e.getComponent() instanceof Circuit)) {
/* 112 */         x = e.getX();
/* 113 */         y = e.getY();
/*    */       }
/*    */       else {
/* 116 */         x = e.getX() + e.getComponent().getX();
/* 117 */         y = e.getY() + e.getComponent().getY();
/*    */       }
/*    */ 
/* 120 */       current = this.circuit.extendWire(last, color, x0, y0, x, y);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.WireDrawer
 * JD-Core Version:    0.6.2
 */