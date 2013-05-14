/*    */ package integratedCircuits;
/*    */ 
/*    */ public class OutputPin extends Pin
/*    */ {
/* 15 */   protected int tplh = 1;
/* 16 */   protected int tphl = 1;
/* 17 */   protected Pin.PinDriver pinDriver = Pin.PinDriver.TOTEM_POLE;
/*    */ 
/*    */   public OutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelayTplh, int pinDelayTphl)
/*    */   {
/* 24 */     super(pinNumber, pinName);
/* 25 */     this.pinDriver = pinDriver;
/* 26 */     this.tplh = pinDelayTplh;
/* 27 */     this.tphl = pinDelayTphl;
/*    */   }
/*    */ 
/*    */   public OutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelay) {
/* 31 */     this(pinNumber, pinName, pinDriver, pinDelay, pinDelay);
/*    */   }
/*    */ 
/*    */   public OutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
/* 35 */     this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, pinDelayTplh, pinDelayTphl);
/*    */   }
/*    */ 
/*    */   public OutputPin(int pinNumber, String pinName, int pinDelay) {
/* 39 */     this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, pinDelay, pinDelay);
/*    */   }
/*    */ 
/*    */   public OutputPin(int pinNumber, String pinName) {
/* 43 */     this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, 1, 1);
/*    */   }
/*    */ 
/*    */   public Pin.PinDriver getPinDriver()
/*    */   {
/* 55 */     return this.pinDriver;
/*    */   }
/*    */ 
/*    */   public int getPinDelayTphl() {
/* 59 */     return this.tphl;
/*    */   }
/*    */ 
/*    */   public int getPinDelayTplh() {
/* 63 */     return this.tplh;
/*    */   }
/*    */ 
/*    */   public void setPinDriver(Pin.PinDriver pinDriver)
/*    */   {
/* 71 */     this.pinDriver = pinDriver;
/*    */   }
/*    */ 
/*    */   public void setPinDelayTphl(int tphl) {
/* 75 */     this.tphl = tphl;
/*    */   }
/*    */ 
/*    */   public void setPinDelayTplh(int tplh) {
/* 79 */     this.tplh = tplh;
/*    */   }
/*    */ 
/*    */   public void setPinDelay(int tplh, int tphl) {
/* 83 */     this.tplh = tplh;
/* 84 */     this.tphl = tphl;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.OutputPin
 * JD-Core Version:    0.6.2
 */