/*     */ package jBreadBoard;
/*     */ 
/*     */ class JBreadBoardChangeEvent extends JBreadBoardEvent
/*     */ {
/*     */   private String value;
/*     */ 
/*     */   public JBreadBoardChangeEvent(Node n, long t, String v)
/*     */   {
/* 249 */     super(n, t);
/* 250 */     this.value = v;
/*     */   }
/*     */ 
/*     */   public DeviceSet process(DeviceSet initial) {
/* 254 */     Node node = getNode();
/* 255 */     if (node != null) {
/* 256 */       Potential potential = node.getPotential();
/* 257 */       if (potential != null) {
/* 258 */         String oldvalue = potential.toTTL();
/* 259 */         node.setPotential(this.value, getTime());
/*     */ 
/* 262 */         if (!oldvalue.equals(potential.toTTL()))
/*     */         {
/* 266 */           initial = potential.getDevicesWithInputs(initial);
/*     */         }
/*     */       }
/*     */     }
/* 270 */     return initial;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.JBreadBoardChangeEvent
 * JD-Core Version:    0.6.2
 */