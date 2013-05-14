/*     */ package integratedCircuits.ttl.shiftRegister;
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
/*     */ public class Gen74166 extends IntegratedCircuit
/*     */ {
/*  18 */   protected Pin.PinState a = Pin.PinState.LOW;
/*  19 */   protected Pin.PinState b = Pin.PinState.LOW;
/*  20 */   protected Pin.PinState c = Pin.PinState.LOW;
/*  21 */   protected Pin.PinState d = Pin.PinState.LOW;
/*  22 */   protected Pin.PinState e = Pin.PinState.LOW;
/*  23 */   protected Pin.PinState f = Pin.PinState.LOW;
/*  24 */   protected Pin.PinState g = Pin.PinState.LOW;
/*     */ 
/*     */   public Gen74166()
/*     */   {
/*  31 */     initialise();
/*     */ 
/*  33 */     this.pins.add(new OutputPin(13, "QH"));
/*     */   }
/*     */ 
/*     */   public Gen74166(int tplh, int tphl)
/*     */   {
/*  38 */     initialise();
/*     */ 
/*  40 */     this.pins.add(new OutputPin(13, "QH", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  49 */     this.description = "Parallel-load 8-Bit Shift Register";
/*  50 */     this.manufacturer = "Generic TTL gate";
/*  51 */     this.diagram = ("images" + File.separator + "74166.gif");
/*  52 */     this.wide = false;
/*     */ 
/*  54 */     this.pins.add(new InputPin(1, "SER"));
/*  55 */     this.pins.add(new InputPin(2, "A"));
/*  56 */     this.pins.add(new InputPin(3, "B"));
/*  57 */     this.pins.add(new InputPin(4, "C"));
/*  58 */     this.pins.add(new InputPin(5, "D"));
/*  59 */     this.pins.add(new InputPin(6, "CLKINH"));
/*  60 */     this.pins.add(new InputPin(7, "CLK"));
/*  61 */     this.pins.add(new PowerPin(8, "GND"));
/*  62 */     this.pins.add(new InputPin(9, "CLR"));
/*  63 */     this.pins.add(new InputPin(10, "E"));
/*  64 */     this.pins.add(new InputPin(11, "F"));
/*  65 */     this.pins.add(new InputPin(12, "G"));
/*  66 */     this.pins.add(new InputPin(14, "H"));
/*  67 */     this.pins.add(new InputPin(15, "SH/nLD"));
/*  68 */     this.pins.add(new PowerPin(16, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String CLR, String SHLD, String CLKINH, String SER, String A, String B, String C, String D, String E, String F, String G, String H, String QH)
/*     */     throws InvalidPinException
/*     */   {
/*  75 */     if (isLow(CLR)) {
/*  76 */       this.a = Pin.PinState.LOW;
/*  77 */       this.b = Pin.PinState.LOW;
/*  78 */       this.c = Pin.PinState.LOW;
/*  79 */       this.d = Pin.PinState.LOW;
/*  80 */       this.e = Pin.PinState.LOW;
/*  81 */       this.f = Pin.PinState.LOW;
/*  82 */       this.g = Pin.PinState.LOW;
/*  83 */       setPin(QH, Pin.PinState.LOW);
/*     */     }
/*  86 */     else if (isLow(SHLD)) {
/*  87 */       this.a = getPinState(A);
/*  88 */       this.b = getPinState(B);
/*  89 */       this.c = getPinState(C);
/*  90 */       this.d = getPinState(D);
/*  91 */       this.e = getPinState(E);
/*  92 */       this.f = getPinState(F);
/*  93 */       this.g = getPinState(G);
/*     */ 
/*  95 */       if (isHigh(H))
/*  96 */         setPin(QH, Pin.PinState.HIGH);
/*     */       else {
/*  98 */         setPin(QH, Pin.PinState.LOW);
/*     */       }
/*     */     }
/* 101 */     else if ((isRisingEdge(CLK)) && (isLow(CLKINH))) {
/* 102 */       if (this.g.equals(Pin.PinState.HIGH))
/* 103 */         setPin(QH, Pin.PinState.HIGH);
/*     */       else {
/* 105 */         setPin(QH, Pin.PinState.LOW);
/*     */       }
/* 107 */       this.g = this.f;
/* 108 */       this.f = this.e;
/* 109 */       this.e = this.d;
/* 110 */       this.d = this.c;
/* 111 */       this.c = this.b;
/* 112 */       this.b = this.a;
/* 113 */       this.a = getPinState(SER);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try
/*     */     {
/* 121 */       for (Pin pin : this.pins)
/* 122 */         if (isPinDriven(pin.getPinName()))
/* 123 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 126 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 132 */         updateGate("CLK", "CLR", "SH/nLD", "CLKINH", "SER", "A", "B", "C", "D", "E", "F", "G", "H", "QH");
/*     */       }
/*     */       else
/*     */       {
/* 136 */         for (Pin pin : this.pins)
/* 137 */           if (isPinDriven(pin.getPinName()))
/* 138 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 143 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.shiftRegister.Gen74166
 * JD-Core Version:    0.6.2
 */