/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class CircuitDiagramFrame_notButton_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   CircuitDiagramFrame adaptee;
/*     */ 
/*     */   CircuitDiagramFrame_notButton_mouseAdapter(CircuitDiagramFrame adaptee)
/*     */   {
/* 447 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 450 */     this.adaptee.notButton_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame_notButton_mouseAdapter
 * JD-Core Version:    0.6.2
 */