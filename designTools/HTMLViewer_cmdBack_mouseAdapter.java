/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class HTMLViewer_cmdBack_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   HTMLViewer adaptee;
/*     */ 
/*     */   HTMLViewer_cmdBack_mouseAdapter(HTMLViewer adaptee)
/*     */   {
/* 152 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 155 */     this.adaptee.cmdBack_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.HTMLViewer_cmdBack_mouseAdapter
 * JD-Core Version:    0.6.2
 */