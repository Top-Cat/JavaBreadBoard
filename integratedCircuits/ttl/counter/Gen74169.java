/*     */ package integratedCircuits.ttl.counter;
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
/*     */ public class Gen74169 extends IntegratedCircuit
/*     */ {
/*  18 */   protected int count = 0;
/*     */ 
/*     */   public Gen74169()
/*     */   {
/*  25 */     initialise();
/*     */ 
/*  27 */     this.pins.add(new OutputPin(15, "nRCO"));
/*  28 */     this.pins.add(new OutputPin(14, "QA"));
/*  29 */     this.pins.add(new OutputPin(13, "QB"));
/*  30 */     this.pins.add(new OutputPin(12, "QC"));
/*  31 */     this.pins.add(new OutputPin(11, "QD"));
/*     */   }
/*     */ 
/*     */   public Gen74169(int tplh, int tphl)
/*     */   {
/*  36 */     initialise();
/*     */ 
/*  38 */     this.pins.add(new OutputPin(15, "nRCO", tplh, tphl));
/*  39 */     this.pins.add(new OutputPin(14, "QA", tplh, tphl));
/*  40 */     this.pins.add(new OutputPin(13, "QB", tplh, tphl));
/*  41 */     this.pins.add(new OutputPin(12, "QC", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(11, "QD", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  51 */     this.description = "Synchronous 4-Bit Counter";
/*  52 */     this.manufacturer = "Generic TTL gate";
/*  53 */     this.diagram = ("images" + File.separator + "74169.gif");
/*  54 */     this.wide = false;
/*     */ 
/*  56 */     this.pins.add(new InputPin(1, "U/nD"));
/*  57 */     this.pins.add(new InputPin(2, "CLK"));
/*  58 */     this.pins.add(new InputPin(3, "A"));
/*  59 */     this.pins.add(new InputPin(4, "B"));
/*  60 */     this.pins.add(new InputPin(5, "C"));
/*  61 */     this.pins.add(new InputPin(6, "D"));
/*  62 */     this.pins.add(new InputPin(7, "nENP"));
/*  63 */     this.pins.add(new PowerPin(8, "GND"));
/*  64 */     this.pins.add(new InputPin(9, "nLOAD"));
/*  65 */     this.pins.add(new InputPin(10, "nENT"));
/*  66 */     this.pins.add(new PowerPin(16, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String CLK, String UD, String ENP, String ENT, String LOAD, String A, String B, String C, String D, String QA, String QB, String QC, String QD, String RCO)
/*     */     throws InvalidPinException
/*     */   {
/*  72 */     if (isRisingEdge(CLK)) {
/*  73 */       if (isLow(LOAD)) {
/*  74 */         setPin(QA, getPinState(A));
/*  75 */         setPin(QB, getPinState(B));
/*  76 */         setPin(QC, getPinState(C));
/*  77 */         setPin(QD, getPinState(D));
/*     */ 
/*  79 */         this.count = 0;
/*  80 */         if (isHigh(A))
/*  81 */           this.count += 1;
/*  82 */         if (isHigh(B))
/*  83 */           this.count += 2;
/*  84 */         if (isHigh(C))
/*  85 */           this.count += 4;
/*  86 */         if (isHigh(D)) {
/*  87 */           this.count += 8;
/*     */         }
/*     */       }
/*  90 */       else if ((isRisingEdge(CLK)) && (isLow(ENP)) && (isLow(ENT))) {
/*  91 */         if (isHigh(UD)) {
/*  92 */           if (this.count == 16)
/*  93 */             this.count = 0;
/*     */           else {
/*  95 */             this.count += 1;
/*     */           }
/*     */         }
/*  98 */         else if (this.count == 0)
/*  99 */           this.count = 15;
/*     */         else {
/* 101 */           this.count -= 1;
/*     */         }
/*     */ 
/* 104 */         switch (this.count) {
/*     */         case 0:
/* 106 */           setPin(QA, Pin.PinState.LOW);
/* 107 */           setPin(QB, Pin.PinState.LOW);
/* 108 */           setPin(QC, Pin.PinState.LOW);
/* 109 */           setPin(QD, Pin.PinState.LOW);
/* 110 */           break;
/*     */         case 1:
/* 112 */           setPin(QA, Pin.PinState.HIGH);
/* 113 */           setPin(QB, Pin.PinState.LOW);
/* 114 */           setPin(QC, Pin.PinState.LOW);
/* 115 */           setPin(QD, Pin.PinState.LOW);
/* 116 */           break;
/*     */         case 2:
/* 118 */           setPin(QA, Pin.PinState.LOW);
/* 119 */           setPin(QB, Pin.PinState.HIGH);
/* 120 */           setPin(QC, Pin.PinState.LOW);
/* 121 */           setPin(QD, Pin.PinState.LOW);
/* 122 */           break;
/*     */         case 3:
/* 124 */           setPin(QA, Pin.PinState.HIGH);
/* 125 */           setPin(QB, Pin.PinState.HIGH);
/* 126 */           setPin(QC, Pin.PinState.LOW);
/* 127 */           setPin(QD, Pin.PinState.LOW);
/* 128 */           break;
/*     */         case 4:
/* 130 */           setPin(QA, Pin.PinState.LOW);
/* 131 */           setPin(QB, Pin.PinState.LOW);
/* 132 */           setPin(QC, Pin.PinState.HIGH);
/* 133 */           setPin(QD, Pin.PinState.LOW);
/* 134 */           break;
/*     */         case 5:
/* 136 */           setPin(QA, Pin.PinState.HIGH);
/* 137 */           setPin(QB, Pin.PinState.LOW);
/* 138 */           setPin(QC, Pin.PinState.HIGH);
/* 139 */           setPin(QD, Pin.PinState.LOW);
/* 140 */           break;
/*     */         case 6:
/* 142 */           setPin(QA, Pin.PinState.LOW);
/* 143 */           setPin(QB, Pin.PinState.HIGH);
/* 144 */           setPin(QC, Pin.PinState.HIGH);
/* 145 */           setPin(QD, Pin.PinState.LOW);
/* 146 */           break;
/*     */         case 7:
/* 148 */           setPin(QA, Pin.PinState.HIGH);
/* 149 */           setPin(QB, Pin.PinState.HIGH);
/* 150 */           setPin(QC, Pin.PinState.HIGH);
/* 151 */           setPin(QD, Pin.PinState.LOW);
/* 152 */           break;
/*     */         case 8:
/* 154 */           setPin(QA, Pin.PinState.LOW);
/* 155 */           setPin(QB, Pin.PinState.LOW);
/* 156 */           setPin(QC, Pin.PinState.LOW);
/* 157 */           setPin(QD, Pin.PinState.HIGH);
/* 158 */           break;
/*     */         case 9:
/* 160 */           setPin(QA, Pin.PinState.HIGH);
/* 161 */           setPin(QB, Pin.PinState.LOW);
/* 162 */           setPin(QC, Pin.PinState.LOW);
/* 163 */           setPin(QD, Pin.PinState.HIGH);
/* 164 */           break;
/*     */         case 10:
/* 166 */           setPin(QA, Pin.PinState.LOW);
/* 167 */           setPin(QB, Pin.PinState.HIGH);
/* 168 */           setPin(QC, Pin.PinState.LOW);
/* 169 */           setPin(QD, Pin.PinState.HIGH);
/* 170 */           break;
/*     */         case 11:
/* 172 */           setPin(QA, Pin.PinState.HIGH);
/* 173 */           setPin(QB, Pin.PinState.HIGH);
/* 174 */           setPin(QC, Pin.PinState.LOW);
/* 175 */           setPin(QD, Pin.PinState.HIGH);
/* 176 */           break;
/*     */         case 12:
/* 178 */           setPin(QA, Pin.PinState.LOW);
/* 179 */           setPin(QB, Pin.PinState.LOW);
/* 180 */           setPin(QC, Pin.PinState.HIGH);
/* 181 */           setPin(QD, Pin.PinState.HIGH);
/* 182 */           break;
/*     */         case 13:
/* 184 */           setPin(QA, Pin.PinState.HIGH);
/* 185 */           setPin(QB, Pin.PinState.LOW);
/* 186 */           setPin(QC, Pin.PinState.HIGH);
/* 187 */           setPin(QD, Pin.PinState.HIGH);
/* 188 */           break;
/*     */         case 14:
/* 190 */           setPin(QA, Pin.PinState.LOW);
/* 191 */           setPin(QB, Pin.PinState.HIGH);
/* 192 */           setPin(QC, Pin.PinState.HIGH);
/* 193 */           setPin(QD, Pin.PinState.HIGH);
/* 194 */           break;
/*     */         case 15:
/* 196 */           setPin(QA, Pin.PinState.HIGH);
/* 197 */           setPin(QB, Pin.PinState.HIGH);
/* 198 */           setPin(QC, Pin.PinState.HIGH);
/* 199 */           setPin(QD, Pin.PinState.HIGH);
/* 200 */           break;
/*     */         default:
/* 202 */           setPin(QA, Pin.PinState.LOW);
/* 203 */           setPin(QB, Pin.PinState.LOW);
/* 204 */           setPin(QC, Pin.PinState.LOW);
/* 205 */           setPin(QD, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 211 */     if ((this.count == 15) && (isLow(ENT)))
/* 212 */       setPin(RCO, Pin.PinState.LOW);
/*     */     else
/* 214 */       setPin(RCO, Pin.PinState.HIGH);
/*     */   }
/*     */ 
/*     */   public void reset() {
/*     */     try {
/* 219 */       for (Pin pin : this.pins)
/* 220 */         if (isPinDriven(pin.getPinName()))
/* 221 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 224 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 230 */         updateGate("CLK", "U/nD", "nENP", "nENT", "nLOAD", "A", "B", "C", "D", "QA", "QB", "QC", "QD", "nRCO");
/*     */       }
/*     */       else
/*     */       {
/* 234 */         for (Pin pin : this.pins)
/* 235 */           if (isPinDriven(pin.getPinName()))
/* 236 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 241 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.counter.Gen74169
 * JD-Core Version:    0.6.2
 */