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
/*    */ public class Gen7432 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7432()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(3, "1Y"));
/* 27 */     this.pins.add(new OutputPin(6, "2Y"));
/* 28 */     this.pins.add(new OutputPin(8, "3Y"));
/* 29 */     this.pins.add(new OutputPin(11, "4Y"));
/*    */   }
/*    */ 
/*    */   public Gen7432(int tplh, int tphl)
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
/* 48 */     this.description = "Quadruple 2-Input Positive-OR Gates";
/* 49 */     this.manufacturer = "Generic TTL gate";
/* 50 */     this.diagram = ("images" + File.separator + "7432.gif");
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
/*    */   private void updateGate(String A, String B, String Y) throws InvalidPinException
/*    */   {
/* 67 */     if ((isLow(A)) && (isLow(B))) {
/* 68 */       setPin(Y, Pin.PinState.LOW);
/*    */     }
/*    */     else
/* 71 */       setPin(Y, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/*    */     try {
/* 81 */       if (isPowered()) {
/* 82 */         updateGate("1A", "1B", "1Y");
/* 83 */         updateGate("2A", "2B", "2Y");
/* 84 */         updateGate("3A", "3B", "3Y");
/* 85 */         updateGate("4A", "4B", "4Y");
/*    */       }
/*    */       else
/*    */       {
/* 89 */         for (Pin pin : this.pins)
/* 90 */           if (isPinDriven(pin.getPinName()))
/* 91 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 96 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7432
 * JD-Core Version:    0.6.2
 */