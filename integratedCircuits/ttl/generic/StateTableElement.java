/*    */ package integratedCircuits.ttl.generic;
/*    */ 
/*    */ public class StateTableElement extends LogicTableElement
/*    */ {
/* 16 */   private int nextState = 0;
/*    */ 
/*    */   public StateTableElement(int size, int data)
/*    */   {
/* 23 */     super(size, data, 1);
/* 24 */     this.nextState = 0;
/*    */   }
/*    */ 
/*    */   public StateTableElement(int size, int data, int delay) {
/* 28 */     super(size, data, delay);
/* 29 */     this.nextState = 0;
/*    */   }
/*    */ 
/*    */   public StateTableElement(int size, int data, int delay, int state) {
/* 33 */     super(size, data, delay);
/* 34 */     this.nextState = state;
/*    */   }
/*    */ 
/*    */   public void setState(int state)
/*    */   {
/* 42 */     this.nextState = state;
/*    */   }
/*    */ 
/*    */   public int getState() {
/* 46 */     return this.nextState;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.generic.StateTableElement
 * JD-Core Version:    0.6.2
 */