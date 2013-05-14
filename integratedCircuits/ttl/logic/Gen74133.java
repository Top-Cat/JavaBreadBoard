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
/*    */ public class Gen74133 extends IntegratedCircuit
/*    */ {
/*    */   public Gen74133()
/*    */   {
/* 24 */     initialise();
/*    */ 
/* 26 */     this.pins.add(new OutputPin(9, "Y"));
/*    */   }
/*    */ 
/*    */   public Gen74133(int tplh, int tphl)
/*    */   {
/* 31 */     initialise();
/*    */ 
/* 33 */     this.pins.add(new OutputPin(9, "Y", tplh, tphl));
/*    */   }
/*    */ 
/*    */   private void initialise()
/*    */   {
/* 42 */     this.description = "13-Input Positive-NAND Gate";
/* 43 */     this.manufacturer = "Generic TTL gate";
/* 44 */     this.diagram = ("images" + File.separator + "74133.gif");
/* 45 */     this.wide = false;
/*    */ 
/* 47 */     this.pins.add(new InputPin(1, "A"));
/* 48 */     this.pins.add(new InputPin(2, "B"));
/* 49 */     this.pins.add(new InputPin(3, "C"));
/* 50 */     this.pins.add(new InputPin(4, "D"));
/* 51 */     this.pins.add(new InputPin(5, "E"));
/* 52 */     this.pins.add(new InputPin(6, "F"));
/* 53 */     this.pins.add(new InputPin(7, "G"));
/* 54 */     this.pins.add(new PowerPin(8, "GND"));
/* 55 */     this.pins.add(new InputPin(10, "H"));
/* 56 */     this.pins.add(new InputPin(11, "I"));
/* 57 */     this.pins.add(new InputPin(12, "J"));
/* 58 */     this.pins.add(new InputPin(13, "K"));
/* 59 */     this.pins.add(new InputPin(14, "L"));
/* 60 */     this.pins.add(new InputPin(15, "M"));
/* 61 */     this.pins.add(new PowerPin(16, "VCC"));
/*    */   }
/*    */ 
/*    */   private void updateGate(String A, String B, String C, String D, String E, String F, String G, String H, String I, String J, String K, String L, String M, String Y) throws InvalidPinException
/*    */   {
/* 66 */     if ((isHigh(A)) && (isHigh(B)) && (isHigh(C)) && (isHigh(D)) && (isHigh(E)) && (isHigh(F)) && (isHigh(G)) && (isHigh(H)) && (isHigh(I)) && (isHigh(J)) && (isHigh(K)) && (isHigh(L)) && (isHigh(M)))
/*    */     {
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
/* 82 */         updateGate("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "Y");
/*    */       }
/*    */       else
/*    */       {
/* 86 */         for (Pin pin : this.pins)
/* 87 */           if (isPinDriven(pin.getPinName()))
/* 88 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*    */       }
/*    */     }
/*    */     catch (InvalidPinException e1)
/*    */     {
/* 93 */       System.out.println("OPPS: InvalidPinException");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.logic.Gen74133
 * JD-Core Version:    0.6.2
 */