/*    */ package integratedCircuits;
/*    */ 
/*    */ public abstract class Pin
/*    */ {
/*    */   protected int pinNumber;
/*    */   protected String pinName;
/* 35 */   protected PinState pinState = PinState.LOW;
/*    */ 
/*    */   protected Pin(int pinNumber, String pinName)
/*    */   {
/* 42 */     this.pinNumber = pinNumber;
/* 43 */     this.pinName = pinName;
/*    */   }
/*    */ 
/*    */   public int getPinNumber()
/*    */   {
/* 51 */     return this.pinNumber;
/*    */   }
/*    */ 
/*    */   public String getPinName() {
/* 55 */     return this.pinName;
/*    */   }
/*    */ 
/*    */   public PinState getPinState() {
/* 59 */     return this.pinState;
/*    */   }
/*    */ 
/*    */   public void setPinState(PinState state) {
/* 63 */     this.pinState = state;
/*    */   }
/*    */ 
/*    */   public static enum PinDriver
/*    */   {
/* 24 */     OPEN_COLLECTOR, 
/* 25 */     TOTEM_POLE, 
/* 26 */     TRI_STATE;
/*    */   }
/*    */ 
/*    */   public static enum PinState
/*    */   {
/* 16 */     HIGH, 
/* 17 */     LOW, 
/* 18 */     HIGH_IMPEDANCE, 
/* 19 */     NOT_CONNECTED, 
/* 20 */     UNKNOWN;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.Pin
 * JD-Core Version:    0.6.2
 */