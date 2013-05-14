/*    */ package integratedCircuits.ttl.flipFlop;
/*    */ 
/*    */ import integratedCircuits.InputPin;
/*    */ import integratedCircuits.IntegratedCircuit;
/*    */ import integratedCircuits.InvalidPinException;
/*    */ import integratedCircuits.OutputPin;
/*    */ import integratedCircuits.Pin;
/*    */ import integratedCircuits.Pin.PinState;
/*    */ import integratedCircuits.PowerPin;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Gen74279 extends IntegratedCircuit
/*    */ {
/*    */   public Gen74279()
/*    */   {
/* 19 */     initialise();
/*    */ 
/* 21 */     this.pins.add(new OutputPin(4, "1Q"));
/* 22 */     this.pins.add(new OutputPin(7, "2Q"));
/* 23 */     this.pins.add(new OutputPin(9, "3Q"));
/* 24 */     this.pins.add(new OutputPin(13, "4Q"));
/*    */   }
/*    */ 
/*    */   public Gen74279(int tplh, int tphl)
/*    */   {
/* 29 */     initialise();
/*    */ 
/* 31 */     this.pins.add(new OutputPin(4, "1Q", tplh, tphl));
/* 32 */     this.pins.add(new OutputPin(7, "2Q", tplh, tphl));
/* 33 */     this.pins.add(new OutputPin(9, "3Q", tplh, tphl));
/* 34 */     this.pins.add(new OutputPin(13, "4Q", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 43 */     this.description = "Quadruple SR Latch";
/* 44 */     this.manufacturer = "Generic TTL gate";
/* 45 */     this.diagram = ("images" + File.separator + "74279.gif");
/* 46 */     this.wide = false;
/*    */ 
/* 48 */     this.pins.add(new InputPin(1, "1nR"));
/* 49 */     this.pins.add(new InputPin(2, "1nS1"));
/* 50 */     this.pins.add(new InputPin(3, "1nS2"));
/* 51 */     this.pins.add(new InputPin(5, "2nR"));
/* 52 */     this.pins.add(new InputPin(6, "2nS"));
/* 53 */     this.pins.add(new PowerPin(8, "GND"));
/* 54 */     this.pins.add(new InputPin(10, "3nR"));
/* 55 */     this.pins.add(new InputPin(11, "3nS1"));
/* 56 */     this.pins.add(new InputPin(12, "3nS2"));
/* 57 */     this.pins.add(new InputPin(14, "4nR"));
/* 58 */     this.pins.add(new InputPin(15, "4nS"));
/* 59 */     this.pins.add(new PowerPin(26, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String S, String R, String Q) throws InvalidPinException
/*    */   {
/* 64 */     if ((isLow(R)) && (isHigh(S))) {
/* 65 */       setPin(Q, Pin.PinState.LOW);
/*    */     }
/* 67 */     else if ((isLow(S)) && (isHigh(R)))
/* 68 */       setPin(Q, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   private void updateGate(String S1, String S2, String R, String Q) throws InvalidPinException {
/* 72 */     if ((isLow(R)) && (isHigh(S1)) && (isHigh(S2))) {
/* 73 */       setPin(Q, Pin.PinState.LOW);
/*    */     }
/* 75 */     else if (((isLow(S1)) || (isLow(S2))) && (isHigh(R)))
/* 76 */       setPin(Q, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   public void reset() {
/*    */     try {
/* 81 */       for (Pin pin : this.pins)
/* 82 */         if (isPinDriven(pin.getPinName()))
/* 83 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */     }
/*    */     catch (InvalidPinException e1) {
/* 86 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void simulate() {
/*    */     try { if (isPowered()) {
/* 92 */         updateGate("1nS1", "1nS2", "1nR", "1Q");
/* 93 */         updateGate("2nS", "2nR", "2Q");
/* 94 */         updateGate("3nS1", "3nS2", "3nR", "3Q");
/* 95 */         updateGate("4nS", "4nR", "4Q");
/*    */       }
/*    */       else
/*    */       {
/* 99 */         for (Pin pin : this.pins)
/* 100 */           if (isPinDriven(pin.getPinName()))
/* 101 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 106 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.flipFlop.Gen74279
 * JD-Core Version:    0.6.2
 */