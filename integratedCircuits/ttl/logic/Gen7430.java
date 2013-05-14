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
/*    */ public class Gen7430 extends IntegratedCircuit
/*    */ {
/*    */   public Gen7430()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(8, "Y"));
/*    */   }
/*    */ 
/*    */   public Gen7430(int tplh, int tphl)
/*    */   {
/* 31 */     initialise();
/*    */ 
/* 33 */     this.pins.add(new OutputPin(8, "Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 42 */     this.description = "8-Input Positive-NAND Gates";
/* 43 */     this.manufacturer = "Generic TTL gate";
/* 44 */     this.diagram = ("images" + File.separator + "7430.gif");
/* 45 */     this.wide = false;
/*    */ 
/* 47 */     this.pins.add(new InputPin(1, "A"));
/* 48 */     this.pins.add(new InputPin(2, "B"));
/* 49 */     this.pins.add(new InputPin(3, "C"));
/* 50 */     this.pins.add(new InputPin(4, "D"));
/* 51 */     this.pins.add(new InputPin(5, "E"));
/* 52 */     this.pins.add(new InputPin(6, "F"));
/* 53 */     this.pins.add(new PowerPin(7, "GND"));
/* 54 */     this.pins.add(new InputPin(9, "NC"));
/* 55 */     this.pins.add(new InputPin(10, "NC"));
/* 56 */     this.pins.add(new InputPin(11, "G"));
/* 57 */     this.pins.add(new InputPin(12, "H"));
/* 58 */     this.pins.add(new InputPin(13, "NC"));
/* 59 */     this.pins.add(new PowerPin(14, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String C, String D, String E, String F, String G, String H, String Y) throws InvalidPinException
/*    */   {
/* 64 */     if ((isHigh(A)) && (isHigh(B)) && (isHigh(C)) && (isHigh(D)) && (isHigh(E)) && (isHigh(F)) && (isHigh(G)) && (isHigh(H))) {
/* 65 */       setPin(Y, Pin.PinState.LOW);
/*    */     }
/*    */     else
/* 68 */       setPin(Y, Pin.PinState.HIGH);
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
/* 79 */         updateGate("A", "B", "C", "D", "E", "F", "G", "H", "Y");
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
 * Qualified Name:     integratedCircuits.ttl.logic.Gen7430
 * JD-Core Version:    0.6.2
 */