/*    */ package jBreadBoard;
/*    */ 
/*    */ public class NodeType
/*    */ {
/*    */   private String type;
/*    */ 
/*    */   public NodeType()
/*    */   {
/* 30 */     setType("NC");
/*    */   }
/*    */ 
/*    */   public NodeType(String s) {
/* 34 */     setType(s);
/*    */   }
/*    */ 
/*    */   public void setType(String s) {
/* 38 */     if ((s.equals("IN")) || (s.equals("OCO")) || (s.equals("OUT")) || (s.equals("IO")) || (s.equals("CLO")))
/*    */     {
/* 43 */       this.type = s;
/*    */     }
/* 45 */     else this.type = "NC"; 
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 49 */     return this.type;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 53 */     return this.type;
/*    */   }
/*    */ 
/*    */   public static boolean conflicts(String s1, String s2)
/*    */   {
/* 58 */     if ((s1.equals("OCO")) && (s2.equals("OCO")))
/* 59 */       return false;
/* 60 */     if (((s1.equals("OCO")) || (s1.equals("OUT")) || (s1.equals("CLO"))) && ((s2.equals("OCO")) || (s2.equals("OUT")) || (s2.equals("CLO"))))
/*    */     {
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   public static boolean conflicts(NodeType t1, NodeType t2)
/*    */   {
/* 69 */     return conflicts(t1.getType(), t2.getType());
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.NodeType
 * JD-Core Version:    0.6.2
 */