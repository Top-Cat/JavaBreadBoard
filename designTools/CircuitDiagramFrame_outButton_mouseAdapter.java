/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class CircuitDiagramFrame_outButton_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   CircuitDiagramFrame adaptee;
/*     */ 
/*     */   CircuitDiagramFrame_outButton_mouseAdapter(CircuitDiagramFrame adaptee)
/*     */   {
/* 469 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 472 */     this.adaptee.outButton_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame_outButton_mouseAdapter
 * JD-Core Version:    0.6.2
 */