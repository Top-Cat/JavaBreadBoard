/*    */ package integratedCircuits;
/*    */ 
/*    */ public class ClockOutputPin extends OutputPin
/*    */ {
/*    */   public ClockOutputPin(int pinNumber, String pinName)
/*    */   {
/* 16 */     super(pinNumber, pinName);
/*    */   }
/*    */ 
/*    */   public ClockOutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
/* 20 */     super(pinNumber, pinName);
/* 21 */     this.tplh = pinDelayTplh;
/* 22 */     this.tphl = pinDelayTphl;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ClockOutputPin
 * JD-Core Version:    0.6.2
 */