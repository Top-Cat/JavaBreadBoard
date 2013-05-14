/*    */ package integratedCircuits.ttl.logic;
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
/*    */ public class Gen7404 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7404()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(2, "1Y"));
/* 27 */     this.pins.add(new OutputPin(4, "2Y"));
/* 28 */     this.pins.add(new OutputPin(6, "3Y"));
/* 29 */     this.pins.add(new OutputPin(8, "4Y"));
/* 30 */     this.pins.add(new OutputPin(10, "5Y"));
/* 31 */     this.pins.add(new OutputPin(12, "6Y"));
/*    */   }
/*    */ 
/*    */   public Gen7404(int tplh, int tphl) {
/* 35 */     initialise();
/*    */ 
/* 37 */     this.pins.add(new OutputPin(2, "1Y", tplh, tphl));
/* 38 */     this.pins.add(new OutputPin(4, "2Y", tplh, tphl));
/* 39 */     this.pins.add(new OutputPin(6, "3Y", tplh, tphl));
/* 40 */     this.pins.add(new OutputPin(8, "4Y", tplh, tphl));
/* 41 */     this.pins.add(new OutputPin(10, "5Y", tplh, tphl));
/* 42 */     this.pins.add(new OutputPin(12, "6Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 51 */     this.description = "Hex NOT Gates";
/* 52 */     this.manufacturer = "Generic TTL gate";
/* 53 */     this.diagram = ("images" + File.separator + "7404.gif");
/* 54 */     this.wide = false;
/*    */ 
/* 56 */     this.pins.add(new InputPin(1, "1A"));
/* 57 */     this.pins.add(new InputPin(3, "2A"));
/* 58 */     this.pins.add(new InputPin(5, "3A"));
/* 59 */     this.pins.add(new PowerPin(7, "GND"));
/* 60 */     this.pins.add(new InputPin(9, "4A"));
/* 61 */     this.pins.add(new InputPin(11, "5A"));
/* 62 */     this.pins.add(new InputPin(13, "6A"));
/* 63 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String Y) throws InvalidPinException
/*    */   {
/* 68 */     if (isLow(A)) {
/* 69 */       setPin(Y, Pin.PinState.HIGH);
/*    */     }
/*    */     else
/* 72 */       setPin(Y, Pin.PinState.LOW);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/*    */     try {
/* 82 */       if (isPowered()) {
/* 83 */         updateGate("1A", "1Y");
/* 84 */         updateGate("2A", "2Y");
/* 85 */         updateGate("3A", "3Y");
/* 86 */         updateGate("4A", "4Y");
/* 87 */         updateGate("5A", "5Y");
/* 88 */         updateGate("6A", "6Y");
/*    */       }
/*    */       else
/*    */       {
/* 92 */         for (Pin pin : this.pins)
/* 93 */           if (isPinDriven(pin.getPinName()))
/* 94 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 99 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7404
 * JD-Core Version:    0.6.2
 */