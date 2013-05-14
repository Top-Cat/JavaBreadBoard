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
/*    */ public class Gen74260 extends IntegratedCircuit
/*    */ {
/*    */   public Gen74260()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(5, "1Y"));
/* 27 */     this.pins.add(new OutputPin(6, "2Y"));
/*    */   }
/*    */ 
/*    */   public Gen74260(int tplh, int tphl)
/*    */   {
/* 32 */     initialise();
/*    */ 
/* 34 */     this.pins.add(new OutputPin(5, "1Y", tplh, tphl));
/* 35 */     this.pins.add(new OutputPin(6, "2Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 44 */     this.description = "Dual 5-Input Positive-NOR Gates";
/* 45 */     this.manufacturer = "Generic TTL gate";
/* 46 */     this.diagram = ("images" + File.separator + "74260.gif");
/* 47 */     this.wide = false;
/*    */ 
/* 49 */     this.pins.add(new InputPin(1, "1A"));
/* 50 */     this.pins.add(new InputPin(2, "1B"));
/* 51 */     this.pins.add(new InputPin(3, "1C"));
/* 52 */     this.pins.add(new InputPin(4, "2A"));
/* 53 */     this.pins.add(new PowerPin(7, "GND"));
/* 54 */     this.pins.add(new InputPin(8, "2B"));
/* 55 */     this.pins.add(new InputPin(9, "2C"));
/* 56 */     this.pins.add(new InputPin(10, "2D"));
/* 57 */     this.pins.add(new InputPin(11, "2E"));
/* 58 */     this.pins.add(new InputPin(12, "1D"));
/* 59 */     this.pins.add(new InputPin(13, "1E"));
/* 60 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String C, String D, String E, String Y) throws InvalidPinException {
/* 64 */     if ((isLow(A)) && (isLow(B)) && (isLow(C)) && (isLow(D)) && (isLow(E))) {
/* 65 */       setPin(Y, Pin.PinState.HIGH);
/*    */     }
/*    */     else
/* 68 */       setPin(Y, Pin.PinState.LOW);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/*    */     try {
/* 78 */       if (isPowered()) {
/* 79 */         updateGate("1A", "1B", "1C", "1D", "1E", "1Y");
/* 80 */         updateGate("2A", "2B", "2C", "2D", "2E", "2Y");
/*    */       }
/*    */       else
/*    */       {
/* 85 */         for (Pin pin : this.pins)
/* 86 */           if (isPinDriven(pin.getPinName()))
/* 87 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 92 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen74260
 * JD-Core Version:    0.6.2
 */