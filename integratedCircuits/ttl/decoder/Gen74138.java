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
/*     */ public class Gen74138 extends IntegratedCircuit
/*     */ {
/*     */   public Gen74138()
/*     */   {
/*  24 */     initialise();
/*     */ 
/*  26 */     this.pins.add(new OutputPin(15, "Y0"));
/*  27 */     this.pins.add(new OutputPin(14, "Y1"));
/*  28 */     this.pins.add(new OutputPin(13, "Y2"));
/*  29 */     this.pins.add(new OutputPin(12, "Y3"));
/*  30 */     this.pins.add(new OutputPin(11, "Y4"));
/*  31 */     this.pins.add(new OutputPin(10, "Y5"));
/*  32 */     this.pins.add(new OutputPin(9, "Y6"));
/*  33 */     this.pins.add(new OutputPin(7, "Y7"));
/*     */   }
/*     */ 
/*     */   public Gen74138(int tplh, int tphl) {
/*  37 */     initialise();
/*     */ 
/*  39 */     this.pins.add(new OutputPin(15, "Y0", tplh, tphl));
/*  40 */     this.pins.add(new OutputPin(14, "Y1", tplh, tphl));
/*  41 */     this.pins.add(new OutputPin(13, "Y2", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(12, "Y3", tplh, tphl));
/*  43 */     this.pins.add(new OutputPin(11, "Y4", tplh, tphl));
/*  44 */     this.pins.add(new OutputPin(10, "Y5", tplh, tphl));
/*  45 */     this.pins.add(new OutputPin(9, "Y6", tplh, tphl));
/*  46 */     this.pins.add(new OutputPin(7, "Y7", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  54 */     this.description = "3-Line to 8-Line Decoder / Demultiplexers";
/*  55 */     this.manufacturer = "Generic TTL gate";
/*  56 */     this.diagram = ("images" + File.separator + "74138.gif");
/*  57 */     this.wide = false;
/*     */ 
/*  59 */     this.pins.add(new InputPin(1, "A"));
/*  60 */     this.pins.add(new InputPin(2, "B"));
/*  61 */     this.pins.add(new InputPin(3, "C"));
/*  62 */     this.pins.add(new InputPin(4, "nG2A"));
/*  63 */     this.pins.add(new InputPin(5, "nG2B"));
/*  64 */     this.pins.add(new InputPin(6, "G1"));
/*  65 */     this.pins.add(new PowerPin(8, "GND"));
/*  66 */     this.pins.add(new PowerPin(16, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String A, String B, String C, String nG2A, String nG2B, String G1, String Y0, String Y1, String Y2, String Y3, String Y4, String Y5, String Y6, String Y7) throws InvalidPinException
/*     */   {
/*  71 */     if ((isHigh(nG2A)) || (isHigh(nG2B)) || (isLow(G1))) {
/*  72 */       resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
/*     */     }
/*     */     else {
/*  75 */       int count = 0;
/*  76 */       if (isHigh(C)) {
/*  77 */         count += 4;
/*     */       }
/*  79 */       else if (isHigh(B)) {
/*  80 */         count += 2;
/*     */       }
/*  82 */       else if (isHigh(A)) {
/*  83 */         count += 1;
/*     */       }
/*  85 */       switch (count) {
/*     */       case 0:
/*  87 */         setPin(Y0, Pin.PinState.LOW);
/*  88 */         setPin(Y1, Pin.PinState.HIGH);
/*  89 */         setPin(Y2, Pin.PinState.HIGH);
/*  90 */         setPin(Y3, Pin.PinState.HIGH);
/*  91 */         setPin(Y4, Pin.PinState.HIGH);
/*  92 */         setPin(Y5, Pin.PinState.HIGH);
/*  93 */         setPin(Y6, Pin.PinState.HIGH);
/*  94 */         setPin(Y7, Pin.PinState.HIGH);
/*  95 */         break;
/*     */       case 1:
/*  97 */         setPin(Y0, Pin.PinState.HIGH);
/*  98 */         setPin(Y1, Pin.PinState.LOW);
/*  99 */         setPin(Y2, Pin.PinState.HIGH);
/* 100 */         setPin(Y3, Pin.PinState.HIGH);
/* 101 */         setPin(Y4, Pin.PinState.HIGH);
/* 102 */         setPin(Y5, Pin.PinState.HIGH);
/* 103 */         setPin(Y6, Pin.PinState.HIGH);
/* 104 */         setPin(Y7, Pin.PinState.HIGH);
/* 105 */         break;
/*     */       case 2:
/* 107 */         setPin(Y0, Pin.PinState.HIGH);
/* 108 */         setPin(Y1, Pin.PinState.HIGH);
/* 109 */         setPin(Y2, Pin.PinState.LOW);
/* 110 */         setPin(Y3, Pin.PinState.HIGH);
/* 111 */         setPin(Y4, Pin.PinState.HIGH);
/* 112 */         setPin(Y5, Pin.PinState.HIGH);
/* 113 */         setPin(Y6, Pin.PinState.HIGH);
/* 114 */         setPin(Y7, Pin.PinState.HIGH);
/* 115 */         break;
/*     */       case 3:
/* 117 */         setPin(Y0, Pin.PinState.HIGH);
/* 118 */         setPin(Y1, Pin.PinState.HIGH);
/* 119 */         setPin(Y2, Pin.PinState.HIGH);
/* 120 */         setPin(Y3, Pin.PinState.LOW);
/* 121 */         setPin(Y4, Pin.PinState.HIGH);
/* 122 */         setPin(Y5, Pin.PinState.HIGH);
/* 123 */         setPin(Y6, Pin.PinState.HIGH);
/* 124 */         setPin(Y7, Pin.PinState.HIGH);
/* 125 */         break;
/*     */       case 4:
/* 127 */         setPin(Y0, Pin.PinState.HIGH);
/* 128 */         setPin(Y1, Pin.PinState.HIGH);
/* 129 */         setPin(Y2, Pin.PinState.HIGH);
/* 130 */         setPin(Y3, Pin.PinState.HIGH);
/* 131 */         setPin(Y4, Pin.PinState.LOW);
/* 132 */         setPin(Y5, Pin.PinState.HIGH);
/* 133 */         setPin(Y6, Pin.PinState.HIGH);
/* 134 */         setPin(Y7, Pin.PinState.HIGH);
/* 135 */         break;
/*     */       case 5:
/* 137 */         setPin(Y0, Pin.PinState.HIGH);
/* 138 */         setPin(Y1, Pin.PinState.HIGH);
/* 139 */         setPin(Y2, Pin.PinState.HIGH);
/* 140 */         setPin(Y3, Pin.PinState.HIGH);
/* 141 */         setPin(Y4, Pin.PinState.HIGH);
/* 142 */         setPin(Y5, Pin.PinState.LOW);
/* 143 */         setPin(Y6, Pin.PinState.HIGH);
/* 144 */         setPin(Y7, Pin.PinState.HIGH);
/* 145 */         break;
/*     */       case 6:
/* 147 */         setPin(Y0, Pin.PinState.HIGH);
/* 148 */         setPin(Y1, Pin.PinState.HIGH);
/* 149 */         setPin(Y2, Pin.PinState.HIGH);
/* 150 */         setPin(Y3, Pin.PinState.HIGH);
/* 151 */         setPin(Y4, Pin.PinState.HIGH);
/* 152 */         setPin(Y5, Pin.PinState.HIGH);
/* 153 */         setPin(Y6, Pin.PinState.LOW);
/* 154 */         setPin(Y7, Pin.PinState.HIGH);
/* 155 */         break;
/*     */       case 7:
/* 157 */         setPin(Y0, Pin.PinState.HIGH);
/* 158 */         setPin(Y1, Pin.PinState.HIGH);
/* 159 */         setPin(Y2, Pin.PinState.HIGH);
/* 160 */         setPin(Y3, Pin.PinState.HIGH);
/* 161 */         setPin(Y4, Pin.PinState.HIGH);
/* 162 */         setPin(Y5, Pin.PinState.HIGH);
/* 163 */         setPin(Y6, Pin.PinState.HIGH);
/* 164 */         setPin(Y7, Pin.PinState.LOW);
/* 165 */         break;
/*     */       default:
/* 167 */         resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void resetGate(String Y0, String Y1, String Y2, String Y3, String Y4, String Y5, String Y6, String Y7) throws InvalidPinException
/*     */   {
/* 174 */     setPin(Y0, Pin.PinState.HIGH);
/* 175 */     setPin(Y1, Pin.PinState.HIGH);
/* 176 */     setPin(Y2, Pin.PinState.HIGH);
/* 177 */     setPin(Y3, Pin.PinState.HIGH);
/* 178 */     setPin(Y4, Pin.PinState.HIGH);
/* 179 */     setPin(Y5, Pin.PinState.HIGH);
/* 180 */     setPin(Y6, Pin.PinState.HIGH);
/* 181 */     setPin(Y7, Pin.PinState.HIGH);
/*     */   }
/*     */ 
/*     */   public void reset() {
/*     */     try {
/* 186 */       resetGate("Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
/*     */     } catch (InvalidPinException e1) {
/* 188 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 194 */         updateGate("A", "B", "C", "nG2A", "nG2B", "G1", "Y0", "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7");
/*     */       }
/*     */       else
/*     */       {
/* 198 */         for (Pin pin : this.pins)
/* 199 */           if (isPinDriven(pin.getPinName()))
/* 200 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 205 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.decoder.Gen74138
 * JD-Core Version:    0.6.2
 */