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
/*     */ public class Gen74165 extends IntegratedCircuit
/*     */ {
/*  18 */   protected Pin.PinState a = Pin.PinState.LOW;
/*  19 */   protected Pin.PinState b = Pin.PinState.LOW;
/*  20 */   protected Pin.PinState c = Pin.PinState.LOW;
/*  21 */   protected Pin.PinState d = Pin.PinState.LOW;
/*  22 */   protected Pin.PinState e = Pin.PinState.LOW;
/*  23 */   protected Pin.PinState f = Pin.PinState.LOW;
/*  24 */   protected Pin.PinState g = Pin.PinState.LOW;
/*     */ 
/*     */   public Gen74165()
/*     */   {
/*  31 */     initialise();
/*     */ 
/*  33 */     this.pins.add(new OutputPin(9, "QH"));
/*  34 */     this.pins.add(new OutputPin(7, "nQH"));
/*     */   }
/*     */ 
/*     */   public Gen74165(int tplh, int tphl)
/*     */   {
/*  39 */     initialise();
/*     */ 
/*  41 */     this.pins.add(new OutputPin(9, "QH", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(7, "nQH", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  51 */     this.description = "Parallel-load 8-Bit Shift Register";
/*  52 */     this.manufacturer = "Generic TTL gate";
/*  53 */     this.diagram = ("images" + File.separator + "74165.gif");
/*  54 */     this.wide = false;
/*     */ 
/*  56 */     this.pins.add(new InputPin(1, "SH/nLD"));
/*  57 */     this.pins.add(new InputPin(2, "CLK"));
/*  58 */     this.pins.add(new InputPin(3, "E"));
/*  59 */     this.pins.add(new InputPin(4, "F"));
/*  60 */     this.pins.add(new InputPin(5, "G"));
/*  61 */     this.pins.add(new InputPin(6, "H"));
/*  62 */     this.pins.add(new PowerPin(8, "GND"));
/*  63 */     this.pins.add(new InputPin(10, "SER"));
/*  64 */     this.pins.add(new InputPin(11, "A"));
/*  65 */     this.pins.add(new InputPin(12, "B"));
/*  66 */     this.pins.add(new InputPin(13, "C"));
/*  67 */     this.pins.add(new InputPin(14, "D"));
/*  68 */     this.pins.add(new InputPin(15, "CLKINH"));
/*  69 */     this.pins.add(new PowerPin(16, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String SHLD, String CLKINH, String SER, String A, String B, String C, String D, String E, String F, String G, String H, String QH, String nQH)
/*     */     throws InvalidPinException
/*     */   {
/*  76 */     if (isLow(SHLD)) {
/*  77 */       this.a = getPinState(A);
/*  78 */       this.b = getPinState(B);
/*  79 */       this.c = getPinState(C);
/*  80 */       this.d = getPinState(D);
/*  81 */       this.e = getPinState(E);
/*  82 */       this.f = getPinState(F);
/*  83 */       this.g = getPinState(G);
/*     */ 
/*  85 */       if (isHigh(H)) {
/*  86 */         setPin(nQH, Pin.PinState.LOW);
/*  87 */         setPin(QH, Pin.PinState.HIGH);
/*     */       }
/*     */       else {
/*  90 */         setPin(nQH, Pin.PinState.HIGH);
/*  91 */         setPin(QH, Pin.PinState.LOW);
/*     */       }
/*     */ 
/*     */     }
/*  95 */     else if ((isRisingEdge(CLK)) && (isLow(CLKINH))) {
/*  96 */       if (this.g.equals(Pin.PinState.HIGH)) {
/*  97 */         setPin(QH, Pin.PinState.HIGH);
/*  98 */         setPin(nQH, Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 101 */         setPin(QH, Pin.PinState.LOW);
/* 102 */         setPin(nQH, Pin.PinState.HIGH);
/*     */       }
/*     */ 
/* 105 */       this.g = this.f;
/* 106 */       this.f = this.e;
/* 107 */       this.e = this.d;
/* 108 */       this.d = this.c;
/* 109 */       this.c = this.b;
/* 110 */       this.b = this.a;
/* 111 */       this.a = getPinState(SER);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try
/*     */     {
/* 119 */       for (Pin pin : this.pins)
/* 120 */         if (isPinDriven(pin.getPinName()))
/* 121 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 124 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 130 */         updateGate("CLK", "SH/nLD", "CLKINH", "SER", "A", "B", "C", "D", "E", "F", "G", "H", "QH", "nQH");
/*     */       }
/*     */       else
/*     */       {
/* 134 */         for (Pin pin : this.pins)
/* 135 */           if (isPinDriven(pin.getPinName()))
/* 136 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 141 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.shiftRegister.Gen74165
 * JD-Core Version:    0.6.2
 */