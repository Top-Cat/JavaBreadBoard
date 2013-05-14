/*     */ package integratedCircuits.ttl.decoder;
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
/*     */ public class Gen74139 extends IntegratedCircuit
/*     */ {
/*     */   public Gen74139()
/*     */   {
/*  24 */     initialise();
/*     */ 
/*  26 */     this.pins.add(new OutputPin(4, "1Y0"));
/*  27 */     this.pins.add(new OutputPin(5, "1Y1"));
/*  28 */     this.pins.add(new OutputPin(6, "1Y2"));
/*  29 */     this.pins.add(new OutputPin(7, "1Y3"));
/*  30 */     this.pins.add(new OutputPin(12, "2Y0"));
/*  31 */     this.pins.add(new OutputPin(11, "2Y1"));
/*  32 */     this.pins.add(new OutputPin(10, "2Y2"));
/*  33 */     this.pins.add(new OutputPin(9, "2Y3"));
/*     */   }
/*     */ 
/*     */   public Gen74139(int tplh, int tphl) {
/*  37 */     initialise();
/*     */ 
/*  39 */     this.pins.add(new OutputPin(4, "1Y0", tplh, tphl));
/*  40 */     this.pins.add(new OutputPin(5, "1Y1", tplh, tphl));
/*  41 */     this.pins.add(new OutputPin(6, "1Y2", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(7, "1Y3", tplh, tphl));
/*  43 */     this.pins.add(new OutputPin(12, "2Y0", tplh, tphl));
/*  44 */     this.pins.add(new OutputPin(11, "2Y1", tplh, tphl));
/*  45 */     this.pins.add(new OutputPin(10, "2Y2", tplh, tphl));
/*  46 */     this.pins.add(new OutputPin(9, "2Y3", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  54 */     this.description = "3-Line to 8-Line Decoder / Demultiplexers";
/*  55 */     this.manufacturer = "Generic TTL gate";
/*  56 */     this.diagram = ("images" + File.separator + "74139.gif");
/*  57 */     this.wide = false;
/*     */ 
/*  59 */     this.pins.add(new InputPin(1, "1nG"));
/*  60 */     this.pins.add(new InputPin(2, "1A"));
/*  61 */     this.pins.add(new InputPin(3, "1B"));
/*  62 */     this.pins.add(new PowerPin(8, "GND"));
/*  63 */     this.pins.add(new InputPin(15, "2nG"));
/*  64 */     this.pins.add(new InputPin(14, "2A"));
/*  65 */     this.pins.add(new InputPin(13, "2B"));
/*  66 */     this.pins.add(new PowerPin(16, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String A, String B, String G, String Y0, String Y1, String Y2, String Y3) throws InvalidPinException {
/*  70 */     if (isHigh(G)) {
/*  71 */       resetGate(Y0, Y1, Y2, Y3);
/*     */     }
/*     */     else {
/*  74 */       int count = 0;
/*  75 */       if (isHigh(B)) {
/*  76 */         count += 2;
/*     */       }
/*  78 */       else if (isHigh(A)) {
/*  79 */         count += 1;
/*     */       }
/*  81 */       switch (count) {
/*     */       case 0:
/*  83 */         setPin(Y0, Pin.PinState.LOW);
/*  84 */         setPin(Y1, Pin.PinState.HIGH);
/*  85 */         setPin(Y2, Pin.PinState.HIGH);
/*  86 */         setPin(Y3, Pin.PinState.HIGH);
/*  87 */         break;
/*     */       case 1:
/*  89 */         setPin(Y0, Pin.PinState.HIGH);
/*  90 */         setPin(Y1, Pin.PinState.LOW);
/*  91 */         setPin(Y2, Pin.PinState.HIGH);
/*  92 */         setPin(Y3, Pin.PinState.HIGH);
/*  93 */         break;
/*     */       case 2:
/*  95 */         setPin(Y0, Pin.PinState.HIGH);
/*  96 */         setPin(Y1, Pin.PinState.HIGH);
/*  97 */         setPin(Y2, Pin.PinState.LOW);
/*  98 */         setPin(Y3, Pin.PinState.HIGH);
/*  99 */         break;
/*     */       case 3:
/* 101 */         setPin(Y0, Pin.PinState.HIGH);
/* 102 */         setPin(Y1, Pin.PinState.HIGH);
/* 103 */         setPin(Y2, Pin.PinState.HIGH);
/* 104 */         setPin(Y3, Pin.PinState.LOW);
/* 105 */         break;
/*     */       default:
/* 107 */         resetGate(Y0, Y1, Y2, Y3);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void resetGate(String Y0, String Y1, String Y2, String Y3) throws InvalidPinException
/*     */   {
/* 114 */     setPin(Y0, Pin.PinState.HIGH);
/* 115 */     setPin(Y1, Pin.PinState.HIGH);
/* 116 */     setPin(Y2, Pin.PinState.HIGH);
/* 117 */     setPin(Y3, Pin.PinState.HIGH);
/*     */   }
/*     */ 
/*     */   public void reset() {
/*     */     try {
/* 122 */       resetGate("1Y0", "1Y1", "1Y2", "1Y3");
/* 123 */       resetGate("2Y0", "2Y1", "2Y2", "2Y3");
/*     */     } catch (InvalidPinException e1) {
/* 125 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 131 */         updateGate("1A", "1B", "1nG", "1Y0", "1Y1", "1Y2", "1Y3");
/* 132 */         updateGate("2A", "2B", "2nG", "2Y0", "2Y1", "2Y2", "2Y3");
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
 * Qualified Name:     integratedCircuits.ttl.decoder.Gen74139
 * JD-Core Version:    0.6.2
 */