/*    */ package integratedCircuits.oscillator;
/*    */ 
/*    */ import integratedCircuits.ClockOutputPin;
/*    */ import integratedCircuits.IntegratedCircuit;
/*    */ import integratedCircuits.InvalidPinException;
/*    */ import integratedCircuits.NotConnectedPin;
/*    */ import integratedCircuits.Pin;
/*    */ import integratedCircuits.Pin.PinState;
/*    */ import integratedCircuits.PowerPin;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GenClk extends IntegratedCircuit
/*    */ {
/* 18 */   protected int period = 1000000000;
/*    */ 
/*    */   public GenClk()
/*    */   {
/* 25 */     initialise();
/*    */ 
/* 27 */     this.pins.add(new ClockOutputPin(5, "C"));
/*    */   }
/*    */ 
/*    */   public GenClk(int tplh, int tphl) {
/* 31 */     initialise();
/*    */ 
/* 33 */     this.pins.add(new ClockOutputPin(5, "C", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 41 */     this.description = "Clock oscilator";
/* 42 */     this.manufacturer = "Generic clock";
/* 43 */     this.diagram = ("images" + File.separator + "clk.gif");
/* 44 */     this.wide = false;
/*    */ 
/* 46 */     this.pins.add(new NotConnectedPin(1, "NC"));
/* 47 */     this.pins.add(new NotConnectedPin(2, "NC"));
/* 48 */     this.pins.add(new NotConnectedPin(3, "NC"));
/* 49 */     this.pins.add(new PowerPin(4, "GND"));
/* 50 */     this.pins.add(new NotConnectedPin(6, "NC"));
/* 51 */     this.pins.add(new NotConnectedPin(7, "NC"));
/* 52 */     this.pins.add(new PowerPin(8, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String C)
/*    */     throws InvalidPinException
/*    */   {
/* 58 */     if (isHigh(C)) {
/* 59 */       setPin(C, Pin.PinState.LOW, this.period / 2);
/*    */     }
/*    */     else
/* 62 */       setPin(C, Pin.PinState.HIGH, this.period / 2);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */     try {
/* 68 */       for (Pin pin : this.pins)
/* 69 */         if (isPinDriven(pin.getPinName()))
/* 70 */           setPin(pin.getPinName(), Pin.PinState.LOW, 0);
/*    */     }
/*    */     catch (InvalidPinException e1) {
/* 73 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void simulate() {
/*    */     try { if (isPowered()) {
/* 79 */         updateGate("C");
/*    */       }
/*    */       else
/*    */       {
/* 83 */         for (Pin pin : this.pins)
/* 84 */           if (isPinDriven(pin.getPinName()))
/* 85 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 90 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.oscillator.GenClk
 * JD-Core Version:    0.6.2
 */