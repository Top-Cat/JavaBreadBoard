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
/*     */ public class Gen74164 extends IntegratedCircuit
/*     */ {
/*     */   public Gen74164()
/*     */   {
/*  19 */     initialise();
/*     */ 
/*  21 */     this.pins.add(new OutputPin(3, "QA"));
/*  22 */     this.pins.add(new OutputPin(4, "QB"));
/*  23 */     this.pins.add(new OutputPin(5, "QC"));
/*  24 */     this.pins.add(new OutputPin(6, "QD"));
/*  25 */     this.pins.add(new OutputPin(10, "QE"));
/*  26 */     this.pins.add(new OutputPin(11, "QF"));
/*  27 */     this.pins.add(new OutputPin(12, "QG"));
/*  28 */     this.pins.add(new OutputPin(13, "QH"));
/*     */   }
/*     */ 
/*     */   public Gen74164(int tplh, int tphl)
/*     */   {
/*  33 */     initialise();
/*     */ 
/*  35 */     this.pins.add(new OutputPin(3, "QA", tplh, tphl));
/*  36 */     this.pins.add(new OutputPin(4, "QB", tplh, tphl));
/*  37 */     this.pins.add(new OutputPin(5, "QC", tplh, tphl));
/*  38 */     this.pins.add(new OutputPin(6, "QD", tplh, tphl));
/*  39 */     this.pins.add(new OutputPin(10, "QE", tplh, tphl));
/*  40 */     this.pins.add(new OutputPin(11, "QF", tplh, tphl));
/*  41 */     this.pins.add(new OutputPin(12, "QG", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(13, "QH", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  51 */     this.description = "8-Bit Parallel-out Serial Shift Register";
/*  52 */     this.manufacturer = "Generic TTL gate";
/*  53 */     this.diagram = ("images" + File.separator + "74164.gif");
/*  54 */     this.wide = false;
/*     */ 
/*  56 */     this.pins.add(new InputPin(1, "A"));
/*  57 */     this.pins.add(new InputPin(2, "B"));
/*  58 */     this.pins.add(new PowerPin(7, "GND"));
/*  59 */     this.pins.add(new InputPin(8, "CLK"));
/*  60 */     this.pins.add(new InputPin(9, "nCLR"));
/*  61 */     this.pins.add(new PowerPin(14, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String CLR, String A, String B, String QA, String QB, String QC, String QD, String QE, String QF, String QG, String QH)
/*     */     throws InvalidPinException
/*     */   {
/*  67 */     if (isLow(CLR)) {
/*  68 */       setPin(QA, Pin.PinState.LOW);
/*  69 */       setPin(QB, Pin.PinState.LOW);
/*  70 */       setPin(QC, Pin.PinState.LOW);
/*  71 */       setPin(QD, Pin.PinState.LOW);
/*  72 */       setPin(QE, Pin.PinState.LOW);
/*  73 */       setPin(QF, Pin.PinState.LOW);
/*  74 */       setPin(QG, Pin.PinState.LOW);
/*  75 */       setPin(QH, Pin.PinState.LOW);
/*     */     }
/*  78 */     else if (isRisingEdge(CLK)) {
/*  79 */       setPin(QH, getPinState(QG));
/*  80 */       setPin(QG, getPinState(QF));
/*  81 */       setPin(QF, getPinState(QE));
/*  82 */       setPin(QE, getPinState(QD));
/*  83 */       setPin(QD, getPinState(QC));
/*  84 */       setPin(QC, getPinState(QB));
/*  85 */       setPin(QB, getPinState(QA));
/*     */ 
/*  87 */       if ((isHigh(A)) && (isHigh(B)))
/*  88 */         setPin(QA, Pin.PinState.HIGH);
/*     */       else
/*  90 */         setPin(QA, Pin.PinState.LOW);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try {
/*  97 */       for (Pin pin : this.pins)
/*  98 */         if (isPinDriven(pin.getPinName()))
/*  99 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 102 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 108 */         updateGate("CLK", "nCLR", "A", "B", "QA", "QB", "QC", "QD", "QE", "QF", "QG", "QH");
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
 * Qualified Name:     integratedCircuits.ttl.shiftRegister.Gen74164
 * JD-Core Version:    0.6.2
 */