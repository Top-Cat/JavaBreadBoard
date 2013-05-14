/*     */ package jBreadBoard;
/*     */ 
/*     */ class JBreadBoardEvent
/*     */ {
/*     */   public JBreadBoardEvent next;
/*     */   private Node node;
/*     */   private long time;
/*     */ 
/*     */   public Node getNode()
/*     */   {
/* 194 */     return this.node;
/*     */   }
/*     */ 
/*     */   public long getTime()
/*     */   {
/* 201 */     return this.time;
/*     */   }
/*     */ 
/*     */   public JBreadBoardEvent(Node n, long t)
/*     */   {
/* 208 */     this.node = n;
/* 209 */     this.time = t;
/*     */   }
/*     */ 
/*     */   public DeviceSet process(DeviceSet initial)
/*     */   {
/* 219 */     if (this.node != null) {
/* 220 */       Potential potential = this.node.getPotential();
/* 221 */       if (potential != null)
/*     */       {
/* 225 */         initial = potential.getDevicesWithInputs(initial);
/*     */       }
/*     */     }
/* 228 */     return initial;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.JBreadBoardEvent
 * JD-Core Version:    0.6.2
 */