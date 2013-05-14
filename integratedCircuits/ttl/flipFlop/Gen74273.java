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
/*    */ public class Gen74273 extends IntegratedCircuit
/*    */ {
/*    */   public Gen74273()
/*    */   {
/* 19 */     initialise();
/*    */ 
/* 21 */     this.pins.add(new OutputPin(2, "1Q"));
/* 22 */     this.pins.add(new OutputPin(5, "2Q"));
/* 23 */     this.pins.add(new OutputPin(6, "3Q"));
/* 24 */     this.pins.add(new OutputPin(9, "4Q"));
/* 25 */     this.pins.add(new OutputPin(12, "5Q"));
/* 26 */     this.pins.add(new OutputPin(15, "6Q"));
/* 27 */     this.pins.add(new OutputPin(16, "7Q"));
/* 28 */     this.pins.add(new OutputPin(19, "8Q"));
/*    */   }
/*    */ 
/*    */   public Gen74273(int tplh, int tphl)
/*    */   {
/* 33 */     initialise();
/*    */ 
/* 35 */     this.pins.add(new OutputPin(2, "1Q", tplh, tphl));
/* 36 */     this.pins.add(new OutputPin(5, "2Q", tplh, tphl));
/* 37 */     this.pins.add(new OutputPin(6, "3Q", tplh, tphl));
/* 38 */     this.pins.add(new OutputPin(9, "4Q", tplh, tphl));
/* 39 */     this.pins.add(new OutputPin(12, "5Q", tplh, tphl));
/* 40 */     this.pins.add(new OutputPin(15, "6Q", tplh, tphl));
/* 41 */     this.pins.add(new OutputPin(16, "7Q", tplh, tphl));
/* 42 */     this.pins.add(new OutputPin(19, "8Q", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 51 */     this.description = "Octal D-Type Flip-Flops with clear";
/* 52 */     this.manufacturer = "Generic TTL gate";
/* 53 */     this.diagram = ("images" + File.separator + "74273.gif");
/* 54 */     this.wide = false;
/*    */ 
/* 56 */     this.pins.add(new InputPin(1, "CLR"));
/* 57 */     this.pins.add(new InputPin(3, "1D"));
/* 58 */     this.pins.add(new InputPin(4, "2D"));
/* 59 */     this.pins.add(new InputPin(7, "3D"));
/* 60 */     this.pins.add(new InputPin(8, "4D"));
/* 61 */     this.pins.add(new PowerPin(10, "GND"));
/* 62 */     this.pins.add(new InputPin(11, "CLK"));
/* 63 */     this.pins.add(new InputPin(13, "5D"));
/* 64 */     this.pins.add(new InputPin(14, "6D"));
/* 65 */     this.pins.add(new InputPin(17, "7D"));
/* 66 */     this.pins.add(new InputPin(18, "8D"));
/* 67 */     this.pins.add(new PowerPin(20, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String CLK, String CLR, String D, String Q) throws InvalidPinException
/*    */   {
/* 72 */     if (isLow(CLR)) {
/* 73 */       setPin(Q, Pin.PinState.LOW);
/*    */     }
/* 76 */     else if (isRisingEdge(CLK))
/* 77 */       if (isLow(D))
/* 78 */         setPin(Q, Pin.PinState.LOW);
/*    */       else
/* 80 */         setPin(Q, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */     try
/*    */     {
/* 87 */       for (Pin pin : this.pins)
/* 88 */         if (isPinDriven(pin.getPinName()))
/* 89 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */     }
/*    */     catch (InvalidPinException e1) {
/* 92 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void simulate() {
/*    */     try { if (isPowered()) {
/* 98 */         updateGate("CLK", "CLR", "1D", "1Q");
/* 99 */         updateGate("CLK", "CLR", "2D", "2Q");
/* 100 */         updateGate("CLK", "CLR", "3D", "3Q");
/* 101 */         updateGate("CLK", "CLR", "4D", "4Q");
/* 102 */         updateGate("CLK", "CLR", "5D", "5Q");
/* 103 */         updateGate("CLK", "CLR", "6D", "6Q");
/* 104 */         updateGate("CLK", "CLR", "7D", "7Q");
/* 105 */         updateGate("CLK", "CLR", "8D", "8Q");
/*    */       }
/*    */       else
/*    */       {
/* 109 */         for (Pin pin : this.pins)
/* 110 */           if (isPinDriven(pin.getPinName()))
/* 111 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 116 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.flipFlop.Gen74273
 * JD-Core Version:    0.6.2
 */