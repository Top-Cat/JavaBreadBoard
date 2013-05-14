/*    */ package jBreadBoard;
/*    */ 
/*    */ public class Node
/*    */ {
/*    */   private Potential potential;
/* 20 */   private Device device = null;
/*    */ 
/*    */   public Node()
/*    */   {
/* 26 */     this.potential = new Potential(this, null);
/*    */   }
/*    */ 
/*    */   public void setDevice(Device d)
/*    */   {
/* 33 */     this.device = d;
/*    */   }
/*    */ 
/*    */   public Device getDevice()
/*    */   {
/* 40 */     return this.device;
/*    */   }
/*    */ 
/*    */   public Potential getPotential()
/*    */   {
/* 47 */     if (this.potential != null) return this.potential.getPotential();
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   public void setPotential(String ttl_level, long currenttime)
/*    */   {
/* 55 */     this.potential.setValue(ttl_level, currenttime);
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 64 */     return getPotential().getType();
/*    */   }
/*    */ 
/*    */   public void setType(String s)
/*    */   {
/* 71 */     if (s != null)
/* 72 */       this.potential.setType(s);
/*    */     else
/* 74 */       this.potential.setType("NC");
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Node
 * JD-Core Version:    0.6.2
 */