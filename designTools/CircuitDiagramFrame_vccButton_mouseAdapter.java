/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class CircuitDiagramFrame_vccButton_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   CircuitDiagramFrame adaptee;
/*     */ 
/*     */   CircuitDiagramFrame_vccButton_mouseAdapter(CircuitDiagramFrame adaptee)
/*     */   {
/* 513 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 516 */     this.adaptee.vccButton_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitDiagramFrame_vccButton_mouseAdapter
 * JD-Core Version:    0.6.2
 */