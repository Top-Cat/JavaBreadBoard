/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class CircuitDiagramFrame_labelButton_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   CircuitDiagramFrame adaptee;
/*     */ 
/*     */   CircuitDiagramFrame_labelButton_mouseAdapter(CircuitDiagramFrame adaptee)
/*     */   {
/* 491 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 494 */     this.adaptee.labelButton_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame_labelButton_mouseAdapter
 * JD-Core Version:    0.6.2
 */