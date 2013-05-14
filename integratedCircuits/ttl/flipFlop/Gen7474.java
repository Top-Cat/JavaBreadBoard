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
/*     */ public class Gen7474 extends IntegratedCircuit
/*     */ {
/*     */   public Gen7474()
/*     */   {
/*  19 */     initialise();
/*     */ 
/*  21 */     this.pins.add(new OutputPin(5, "1Q"));
/*  22 */     this.pins.add(new OutputPin(6, "1nQ"));
/*  23 */     this.pins.add(new OutputPin(9, "2Q"));
/*  24 */     this.pins.add(new OutputPin(8, "2nQ"));
/*     */   }
/*     */ 
/*     */   public Gen7474(int tplh, int tphl)
/*     */   {
/*  29 */     initialise();
/*     */ 
/*  31 */     this.pins.add(new OutputPin(5, "1Q", tplh, tphl));
/*  32 */     this.pins.add(new OutputPin(6, "1nQ", tplh, tphl));
/*  33 */     this.pins.add(new OutputPin(9, "2Q", tplh, tphl));
/*  34 */     this.pins.add(new OutputPin(8, "2nQ", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  43 */     this.description = "Dual D Flip-Flops with preset and clear";
/*  44 */     this.manufacturer = "Generic TTL gate";
/*  45 */     this.diagram = ("images" + File.separator + "7474.gif");
/*  46 */     this.wide = false;
/*     */ 
/*  48 */     this.pins.add(new InputPin(1, "1nCLR"));
/*  49 */     this.pins.add(new InputPin(2, "1D"));
/*  50 */     this.pins.add(new InputPin(3, "1CLK"));
/*  51 */     this.pins.add(new InputPin(4, "1nPRE"));
/*  52 */     this.pins.add(new PowerPin(7, "GND"));
/*  53 */     this.pins.add(new InputPin(10, "2nPRE"));
/*  54 */     this.pins.add(new InputPin(11, "2CLK"));
/*  55 */     this.pins.add(new InputPin(12, "2D"));
/*  56 */     this.pins.add(new InputPin(13, "2nCLR"));
/*  57 */     this.pins.add(new PowerPin(14, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String CLR, String PRE, String D, String Q, String NQ)
/*     */     throws InvalidPinException
/*     */   {
/*  63 */     if (isLow(CLR))
/*     */     {
/*  65 */       setPin(Q, Pin.PinState.LOW);
/*  66 */       setPin(NQ, Pin.PinState.HIGH);
/*     */     }
/*  69 */     else if (isLow(PRE))
/*     */     {
/*  71 */       setPin(Q, Pin.PinState.HIGH);
/*  72 */       setPin(NQ, Pin.PinState.LOW);
/*     */     }
/*  75 */     else if (isRisingEdge(CLK)) {
/*  76 */       if (isLow(D))
/*     */       {
/*  78 */         setPin(Q, Pin.PinState.LOW);
/*  79 */         setPin(NQ, Pin.PinState.HIGH);
/*     */       }
/*     */       else
/*     */       {
/*  83 */         setPin(Q, Pin.PinState.HIGH);
/*  84 */         setPin(NQ, Pin.PinState.LOW);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try
/*     */     {
/*  94 */       for (Pin pin : this.pins)
/*  95 */         if (isPinDriven(pin.getPinName()))
/*  96 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/*  99 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 105 */         updateGate("1CLK", "1nCLR", "1nPRE", "1D", "1Q", "1nQ");
/* 106 */         updateGate("2CLK", "2nCLR", "2nPRE", "2D", "2Q", "2nQ");
/*     */       }
/*     */       else
/*     */       {
/* 110 */         for (Pin pin : this.pins)
/* 111 */           if (isPinDriven(pin.getPinName()))
/* 112 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 117 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.flipFlop.Gen7474
 * JD-Core Version:    0.6.2
 */