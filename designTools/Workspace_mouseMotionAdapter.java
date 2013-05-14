/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ 
/*     */ class Workspace_mouseMotionAdapter extends MouseMotionAdapter
/*     */ {
/*     */   Workspace adaptee;
/*     */ 
/*     */   Workspace_mouseMotionAdapter(Workspace adaptee)
/*     */   {
/* 419 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseMoved(MouseEvent e) {
/* 422 */     this.adaptee.workspace_mouseMoved(e);
/*     */   }
/*     */   public void mouseDragged(MouseEvent e) {
/* 425 */     this.adaptee.workspace_mouseDragged(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.Workspace_mouseMotionAdapter
 * JD-Core Version:    0.6.2
 */