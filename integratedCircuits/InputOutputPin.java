/*    */ package integratedCircuits;
/*    */ 
/*    */ public class InputOutputPin extends OutputPin
/*    */ {
/* 15 */   protected int tplh = 1;
/* 16 */   protected int tphl = 1;
/*    */   protected Pin.PinDriver pinDriver;
/*    */ 
/*    */   public InputOutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelayTplh, int pinDelayTphl)
/*    */   {
/* 24 */     super(pinNumber, pinName);
/*    */ 
/* 26 */     if (this.pinDriver == Pin.PinDriver.TOTEM_POLE)
/* 27 */       this.pinDriver = Pin.PinDriver.TRI_STATE;
/*    */     else {
/* 29 */       this.pinDriver = pinDriver;
/*    */     }
/* 31 */     this.tplh = pinDelayTplh;
/* 32 */     this.tphl = pinDelayTphl;
/*    */   }
/*    */ 
/*    */   public InputOutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelay) {
/* 36 */     this(pinNumber, pinName, pinDriver, pinDelay, pinDelay);
/*    */   }
/*    */ 
/*    */   public InputOutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
/* 40 */     this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, pinDelayTplh, pinDelayTphl);
/*    */   }
/*    */ 
/*    */   public InputOutputPin(int pinNumber, String pinName, int pinDelay) {
/* 44 */     this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, pinDelay, pinDelay);
/*    */   }
/*    */ 
/*    */   public InputOutputPin(int pinNumber, String pinName) {
/* 48 */     this(pinNumber, pinName, Pin.PinDriver.TRI_STATE, 5, 5);
/*    */   }
/*    */ 
/*    */   public Pin.PinDriver getPinDriver()
/*    */   {
/* 56 */     return this.pinDriver;
/*    */   }
/*    */ 
/*    */   public int getPinDelayTphl() {
/* 60 */     return this.tphl;
/*    */   }
/*    */ 
/*    */   public int getPinDelayTplh() {
/* 64 */     return this.tplh;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.InputOutputPin
 * JD-Core Version:    0.6.2
 */