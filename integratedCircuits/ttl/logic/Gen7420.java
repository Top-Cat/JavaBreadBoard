/*    */ package integratedCircuits.ttl.logic;
/*    */ 
/*    */ import integratedCircuits.InputPin;
/*    */ import integratedCircuits.IntegratedCircuit;
/*    */ import integratedCircuits.InvalidPinException;
/*    */ import integratedCircuits.NotConnectedPin;
/*    */ import integratedCircuits.OutputPin;
/*    */ import integratedCircuits.Pin;
/*    */ import integratedCircuits.Pin.PinState;
/*    */ import integratedCircuits.PowerPin;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Gen7420 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7420()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(6, "1Y"));
/* 27 */     this.pins.add(new OutputPin(8, "2Y"));
/*    */   }
/*    */ 
/*    */   public Gen7420(int tplh, int tphl)
/*    */   {
/* 32 */     initialise();
/*    */ 
/* 34 */     this.pins.add(new OutputPin(6, "1Y", tplh, tphl));
/* 35 */     this.pins.add(new OutputPin(8, "2Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 44 */     this.description = "Dual 4-Input Positive-NAND Gates";
/* 45 */     this.manufacturer = "Generic TTL gate";
/* 46 */     this.diagram = ("images" + File.separator + "7420.gif");
/* 47 */     this.wide = false;
/*    */ 
/* 49 */     this.pins.add(new InputPin(1, "1A"));
/* 50 */     this.pins.add(new InputPin(2, "1B"));
/* 51 */     this.pins.add(new NotConnectedPin(3, "NC"));
/* 52 */     this.pins.add(new InputPin(4, "1C"));
/* 53 */     this.pins.add(new InputPin(5, "1D"));
/* 54 */     this.pins.add(new PowerPin(7, "GND"));
/* 55 */     this.pins.add(new InputPin(9, "2A"));
/* 56 */     this.pins.add(new InputPin(10, "2B"));
/* 57 */     this.pins.add(new NotConnectedPin(11, "NC"));
/* 58 */     this.pins.add(new InputPin(12, "2C"));
/* 59 */     this.pins.add(new InputPin(13, "2D"));
/* 60 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String C, String D, String Y) throws InvalidPinException
/*    */   {
/* 65 */     if ((isHigh(A)) && (isHigh(B)) && (isHigh(C)) && (isHigh(D))) {
/* 66 */       setPin(Y, Pin.PinState.LOW);
/*    */     }
/*    */     else
/* 69 */       setPin(Y, Pin.PinState.HIGH);
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/*    */     try {
/* 79 */       if (isPowered()) {
/* 80 */         updateGate("1A", "1B", "1C", "1D", "1Y");
/* 81 */         updateGate("2A", "2B", "2C", "2D", "2Y");
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
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7420
 * JD-Core Version:    0.6.2
 */