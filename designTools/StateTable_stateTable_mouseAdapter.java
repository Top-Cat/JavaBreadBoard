/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ class StateTable_stateTable_mouseAdapter extends MouseAdapter
/*     */ {
/*     */   StateTable adaptee;
/*     */ 
/*     */   StateTable_stateTable_mouseAdapter(StateTable adaptee)
/*     */   {
/* 142 */     this.adaptee = adaptee;
/*     */   }
/*     */   public void mouseClicked(MouseEvent e) {
/* 145 */     this.adaptee.stateTable_mouseClicked(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.StateTable_stateTable_mouseAdapter
 * JD-Core Version:    0.6.2
 */