/*     */ package integratedCircuits.ttl.flipFlop;
/*     */ 
/*     */ import integratedCircuits.InputPin;
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.OutputPin;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Gen7473 extends IntegratedCircuit
/*     */ {
/*     */   public Gen7473()
/*     */   {
/*  19 */     initialise();
/*     */ 
/*  21 */     this.pins.add(new OutputPin(12, "1Q"));
/*  22 */     this.pins.add(new OutputPin(13, "1NQ"));
/*  23 */     this.pins.add(new OutputPin(9, "2Q"));
/*  24 */     this.pins.add(new OutputPin(8, "2NQ"));
/*     */   }
/*     */ 
/*     */   public Gen7473(int tplh, int tphl)
/*     */   {
/*  29 */     initialise();
/*     */ 
/*  31 */     this.pins.add(new OutputPin(12, "1Q", tplh, tphl));
/*  32 */     this.pins.add(new OutputPin(13, "1NQ", tplh, tphl));
/*  33 */     this.pins.add(new OutputPin(9, "2Q", tplh, tphl));
/*  34 */     this.pins.add(new OutputPin(8, "2NQ", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  43 */     this.description = "Dual J-K Flip-Flops with clear";
/*  44 */     this.manufacturer = "Generic TTL gate";
/*  45 */     this.diagram = ("images" + File.separator + "7473.gif");
/*  46 */     this.wide = false;
/*     */ 
/*  48 */     this.pins.add(new InputPin(1, "1CLK"));
/*  49 */     this.pins.add(new InputPin(2, "1CLR"));
/*  50 */     this.pins.add(new InputPin(3, "1K"));
/*  51 */     this.pins.add(new PowerPin(4, "VCC"));
/*  52 */     this.pins.add(new InputPin(5, "2CLK"));
/*  53 */     this.pins.add(new InputPin(6, "2CLR"));
/*  54 */     this.pins.add(new InputPin(7, "2J"));
/*  55 */     this.pins.add(new InputPin(10, "2K"));
/*  56 */     this.pins.add(new PowerPin(11, "GND"));
/*  57 */     this.pins.add(new InputPin(14, "1J"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String CLR, String J, String K, String Q, String NQ) throws InvalidPinException
/*     */   {
/*  62 */     if (isLow(CLR)) {
/*  63 */       setPin(Q, Pin.PinState.LOW);
/*  64 */       setPin(NQ, Pin.PinState.HIGH);
/*     */     }
/*  67 */     else if (isRisingEdge(CLK)) {
/*  68 */       if ((isLow(J)) && (isHigh(K))) {
/*  69 */         setPin(Q, Pin.PinState.LOW);
/*  70 */         setPin(NQ, Pin.PinState.HIGH);
/*     */       }
/*  73 */       else if ((isHigh(J)) && (isLow(K))) {
/*  74 */         setPin(Q, Pin.PinState.HIGH);
/*  75 */         setPin(NQ, Pin.PinState.LOW);
/*     */       }
/*  78 */       else if ((isHigh(J)) && (isHigh(K))) {
/*  79 */         if (isStateHigh(Q)) {
/*  80 */           setPin(Q, Pin.PinState.LOW);
/*  81 */           setPin(NQ, Pin.PinState.HIGH);
/*     */         }
/*     */         else {
/*  84 */           setPin(Q, Pin.PinState.HIGH);
/*  85 */           setPin(NQ, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try
/*     */     {
/*  96 */       for (Pin pin : this.pins)
/*  97 */         if (isPinDriven(pin.getPinName()))
/*  98 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 101 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 107 */         updateGate("1CLK", "1CLR", "1J", "1K", "1Q", "1NQ");
/* 108 */         updateGate("2CLK", "2CLR", "2J", "2K", "2Q", "2NQ");
/*     */       }
/*     */       else
/*     */       {
/* 112 */         for (Pin pin : this.pins)
/* 113 */           if (isPinDriven(pin.getPinName()))
/* 114 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 119 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.flipFlop.Gen7473
 * JD-Core Version:    0.6.2
 */