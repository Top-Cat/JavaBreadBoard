/*    */ package designTools;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class ConnectedWires extends ArrayList
/*    */ {
/*    */   private CircuitComponent sourceComponent;
/*    */ 
/*    */   public void setSourceComponent(CircuitComponent component)
/*    */   {
/* 30 */     this.sourceComponent = component;
/*    */   }
/*    */ 
/*    */   public CircuitComponent getSourceComponent()
/*    */   {
/* 35 */     return this.sourceComponent;
/*    */   }
/*    */ 
/*    */   public void addExclusive(Wire wire)
/*    */   {
/* 42 */     boolean isMember = false;
/* 43 */     for (int i = 0; i < size(); i++) {
/* 44 */       if (get(i).equals(wire)) isMember = true;
/*    */     }
/* 46 */     if (!isMember) add(wire);
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.ConnectedWires
 * JD-Core Version:    0.6.2
 */