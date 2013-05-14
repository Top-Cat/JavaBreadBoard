/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class CircuitDiagramFrame_orButton_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   CircuitDiagramFrame adaptee;
/*     */ 
/*     */   CircuitDiagramFrame_orButton_mouseAdapter(CircuitDiagramFrame adaptee)
/*     */   {
/* 436 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 439 */     this.adaptee.orButton_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame_orButton_mouseAdapter
 * JD-Core Version:    0.6.2
 */