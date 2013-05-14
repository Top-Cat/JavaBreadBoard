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
/*    */ public class Gen7410 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7410()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(12, "1Y"));
/* 27 */     this.pins.add(new OutputPin(6, "2Y"));
/* 28 */     this.pins.add(new OutputPin(8, "3Y"));
/*    */   }
/*    */ 
/*    */   public Gen7410(int tplh, int tphl)
/*    */   {
/* 33 */     initialise();
/*    */ 
/* 35 */     this.pins.add(new OutputPin(12, "1Y", tplh, tphl));
/* 36 */     this.pins.add(new OutputPin(6, "2Y", tplh, tphl));
/* 37 */     this.pins.add(new OutputPin(8, "3Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 46 */     this.description = "Triple 3-Input Positive-NAND Gates";
/* 47 */     this.manufacturer = "Generic TTL gate";
/* 48 */     this.diagram = ("images" + File.separator + "7410.gif");
/* 49 */     this.wide = false;
/*    */ 
/* 51 */     this.pins.add(new InputPin(1, "1A"));
/* 52 */     this.pins.add(new InputPin(2, "1B"));
/* 53 */     this.pins.add(new InputPin(3, "2A"));
/* 54 */     this.pins.add(new InputPin(4, "2B"));
/* 55 */     this.pins.add(new InputPin(5, "2C"));
/* 56 */     this.pins.add(new PowerPin(7, "GND"));
/* 57 */     this.pins.add(new InputPin(9, "3A"));
/* 58 */     this.pins.add(new InputPin(10, "3B"));
/* 59 */     this.pins.add(new InputPin(11, "3C"));
/* 60 */     this.pins.add(new InputPin(13, "1C"));
/* 61 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String C, String Y) throws InvalidPinException
/*    */   {
/* 66 */     if ((isHigh(A)) && (isHigh(B)) && (isHigh(C))) {
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
/* 81 */         updateGate("1A", "1B", "1C", "1Y");
/* 82 */         updateGate("2A", "2B", "2C", "2Y");
/* 83 */         updateGate("3A", "3B", "3C", "3Y");
/*    */       }
/*    */       else
/*    */       {
/* 87 */         for (Pin pin : this.pins)
/* 88 */           if (isPinDriven(pin.getPinName()))
/* 89 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 94 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7410
 * JD-Core Version:    0.6.2
 */