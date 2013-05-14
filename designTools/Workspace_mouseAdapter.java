/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class Workspace_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   Workspace adaptee;
/*     */ 
/*     */   Workspace_mouseAdapter(Workspace adaptee)
/*     */   {
/* 433 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 436 */     this.adaptee.workspace_mouseClicked(e);
/*     */   }
/*     */   public void mousePressed(MouseEvent e) {
/* 439 */     this.adaptee.workspace_mousePressed(e);
/*     */   }
/*     */   public void mouseReleased(MouseEvent e) {
/* 442 */     this.adaptee.workspace_mouseReleased(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.Workspace_mouseAdapter
 * JD-Core Version:    0.6.2
 */