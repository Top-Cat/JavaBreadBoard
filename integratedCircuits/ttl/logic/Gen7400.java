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
/*    */ public class Gen7400 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7400()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(3, "1Y"));
/* 27 */     this.pins.add(new OutputPin(6, "2Y"));
/* 28 */     this.pins.add(new OutputPin(8, "3Y"));
/* 29 */     this.pins.add(new OutputPin(11, "4Y"));
/*    */   }
/*    */ 
/*    */   public Gen7400(int tplh, int tphl)
/*    */   {
/* 34 */     initialise();
/*    */ 
/* 36 */     this.pins.add(new OutputPin(3, "1Y", tplh, tphl));
/* 37 */     this.pins.add(new OutputPin(6, "2Y", tplh, tphl));
/* 38 */     this.pins.add(new OutputPin(8, "3Y", tplh, tphl));
/* 39 */     this.pins.add(new OutputPin(11, "4Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 48 */     this.description = "Quadruple 2-Input Positive-NAND Gates";
/* 49 */     this.manufacturer = "Generic TTL gate";
/* 50 */     this.diagram = ("images" + File.separator + "7400.gif");
/* 51 */     this.wide = false;
/*    */ 
/* 53 */     this.pins.add(new InputPin(1, "1A"));
/* 54 */     this.pins.add(new InputPin(2, "1B"));
/* 55 */     this.pins.add(new InputPin(4, "2A"));
/* 56 */     this.pins.add(new InputPin(5, "2B"));
/* 57 */     this.pins.add(new PowerPin(7, "GND"));
/* 58 */     this.pins.add(new InputPin(9, "3A"));
/* 59 */     this.pins.add(new InputPin(10, "3B"));
/* 60 */     this.pins.add(new InputPin(12, "4A"));
/* 61 */     this.pins.add(new InputPin(13, "4B"));
/* 62 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String Y) throws InvalidPinException {
/* 66 */     if ((isHigh(A)) && (isHigh(B))) {
/* 67 */       setPin(Y, Pin.PinState.LOW);
/*    */     }
/*    */     else
/* 70 */       setPin(Y, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/*    */     try {
/* 80 */       if (isPowered()) {
/* 81 */         updateGate("1A", "1B", "1Y");
/* 82 */         updateGate("2A", "2B", "2Y");
/* 83 */         updateGate("3A", "3B", "3Y");
/* 84 */         updateGate("4A", "4B", "4Y");
/*    */       }
/*    */       else
/*    */       {
/* 88 */         for (Pin pin : this.pins)
/* 89 */           if (isPinDriven(pin.getPinName()))
/* 90 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 95 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7400
 * JD-Core Version:    0.6.2
 */