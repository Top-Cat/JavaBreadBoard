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
/*     */ public class Gen74393 extends IntegratedCircuit
/*     */ {
/*  18 */   protected int count = 0;
/*     */ 
/*     */   public Gen74393()
/*     */   {
/*  25 */     initialise();
/*     */ 
/*  27 */     this.pins.add(new OutputPin(3, "1QA"));
/*  28 */     this.pins.add(new OutputPin(4, "1QB"));
/*  29 */     this.pins.add(new OutputPin(5, "1QC"));
/*  30 */     this.pins.add(new OutputPin(6, "1QD"));
/*  31 */     this.pins.add(new OutputPin(11, "2QA"));
/*  32 */     this.pins.add(new OutputPin(10, "2QB"));
/*  33 */     this.pins.add(new OutputPin(9, "2QC"));
/*  34 */     this.pins.add(new OutputPin(8, "2QD"));
/*     */   }
/*     */ 
/*     */   public Gen74393(int tplh, int tphl)
/*     */   {
/*  39 */     initialise();
/*     */ 
/*  41 */     this.pins.add(new OutputPin(3, "1QA", tplh, tphl));
/*  42 */     this.pins.add(new OutputPin(4, "1QB", tplh, tphl));
/*  43 */     this.pins.add(new OutputPin(5, "1QC", tplh, tphl));
/*  44 */     this.pins.add(new OutputPin(6, "1QD", tplh, tphl));
/*  45 */     this.pins.add(new OutputPin(11, "2QA", tplh, tphl));
/*  46 */     this.pins.add(new OutputPin(10, "2QB", tplh, tphl));
/*  47 */     this.pins.add(new OutputPin(9, "2QC", tplh, tphl));
/*  48 */     this.pins.add(new OutputPin(8, "2QD", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  57 */     this.description = "Dual 4-Bit Binary Counter";
/*  58 */     this.manufacturer = "Generic TTL gate";
/*  59 */     this.diagram = ("images" + File.separator + "74393.gif");
/*  60 */     this.wide = false;
/*     */ 
/*  62 */     this.pins.add(new InputPin(1, "1A"));
/*  63 */     this.pins.add(new InputPin(2, "1CLR"));
/*  64 */     this.pins.add(new PowerPin(7, "GND"));
/*  65 */     this.pins.add(new InputPin(13, "2A"));
/*  66 */     this.pins.add(new InputPin(12, "2CLR"));
/*  67 */     this.pins.add(new PowerPin(14, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String A, String CLR, String QA, String QB, String QC, String QD) throws InvalidPinException
/*     */   {
/*  72 */     if (isHigh(CLR)) {
/*  73 */       setPin(QA, Pin.PinState.LOW);
/*  74 */       setPin(QB, Pin.PinState.LOW);
/*  75 */       setPin(QC, Pin.PinState.LOW);
/*  76 */       setPin(QD, Pin.PinState.LOW);
/*     */     }
/*  79 */     else if (isFallingEdge(A)) {
/*  80 */       if (this.count == 16)
/*  81 */         this.count = 0;
/*     */       else {
/*  83 */         this.count += 1;
/*     */       }
/*  85 */       switch (this.count) {
/*     */       case 0:
/*  87 */         setPin(QA, Pin.PinState.LOW);
/*  88 */         setPin(QB, Pin.PinState.LOW);
/*  89 */         setPin(QC, Pin.PinState.LOW);
/*  90 */         setPin(QD, Pin.PinState.LOW);
/*  91 */         break;
/*     */       case 1:
/*  93 */         setPin(QA, Pin.PinState.HIGH);
/*  94 */         setPin(QB, Pin.PinState.LOW);
/*  95 */         setPin(QC, Pin.PinState.LOW);
/*  96 */         setPin(QD, Pin.PinState.LOW);
/*  97 */         break;
/*     */       case 2:
/*  99 */         setPin(QA, Pin.PinState.LOW);
/* 100 */         setPin(QB, Pin.PinState.HIGH);
/* 101 */         setPin(QC, Pin.PinState.LOW);
/* 102 */         setPin(QD, Pin.PinState.LOW);
/* 103 */         break;
/*     */       case 3:
/* 105 */         setPin(QA, Pin.PinState.HIGH);
/* 106 */         setPin(QB, Pin.PinState.HIGH);
/* 107 */         setPin(QC, Pin.PinState.LOW);
/* 108 */         setPin(QD, Pin.PinState.LOW);
/* 109 */         break;
/*     */       case 4:
/* 111 */         setPin(QA, Pin.PinState.LOW);
/* 112 */         setPin(QB, Pin.PinState.LOW);
/* 113 */         setPin(QC, Pin.PinState.HIGH);
/* 114 */         setPin(QD, Pin.PinState.LOW);
/* 115 */         break;
/*     */       case 5:
/* 117 */         setPin(QA, Pin.PinState.HIGH);
/* 118 */         setPin(QB, Pin.PinState.LOW);
/* 119 */         setPin(QC, Pin.PinState.HIGH);
/* 120 */         setPin(QD, Pin.PinState.LOW);
/* 121 */         break;
/*     */       case 6:
/* 123 */         setPin(QA, Pin.PinState.LOW);
/* 124 */         setPin(QB, Pin.PinState.HIGH);
/* 125 */         setPin(QC, Pin.PinState.HIGH);
/* 126 */         setPin(QD, Pin.PinState.LOW);
/* 127 */         break;
/*     */       case 7:
/* 129 */         setPin(QA, Pin.PinState.HIGH);
/* 130 */         setPin(QB, Pin.PinState.HIGH);
/* 131 */         setPin(QC, Pin.PinState.HIGH);
/* 132 */         setPin(QD, Pin.PinState.LOW);
/* 133 */         break;
/*     */       case 8:
/* 135 */         setPin(QA, Pin.PinState.LOW);
/* 136 */         setPin(QB, Pin.PinState.LOW);
/* 137 */         setPin(QC, Pin.PinState.LOW);
/* 138 */         setPin(QD, Pin.PinState.HIGH);
/* 139 */         break;
/*     */       case 9:
/* 141 */         setPin(QA, Pin.PinState.HIGH);
/* 142 */         setPin(QB, Pin.PinState.LOW);
/* 143 */         setPin(QC, Pin.PinState.LOW);
/* 144 */         setPin(QD, Pin.PinState.HIGH);
/* 145 */         break;
/*     */       case 10:
/* 147 */         setPin(QA, Pin.PinState.LOW);
/* 148 */         setPin(QB, Pin.PinState.HIGH);
/* 149 */         setPin(QC, Pin.PinState.LOW);
/* 150 */         setPin(QD, Pin.PinState.HIGH);
/* 151 */         break;
/*     */       case 11:
/* 153 */         setPin(QA, Pin.PinState.HIGH);
/* 154 */         setPin(QB, Pin.PinState.HIGH);
/* 155 */         setPin(QC, Pin.PinState.LOW);
/* 156 */         setPin(QD, Pin.PinState.HIGH);
/* 157 */         break;
/*     */       case 12:
/* 159 */         setPin(QA, Pin.PinState.LOW);
/* 160 */         setPin(QB, Pin.PinState.LOW);
/* 161 */         setPin(QC, Pin.PinState.HIGH);
/* 162 */         setPin(QD, Pin.PinState.HIGH);
/* 163 */         break;
/*     */       case 13:
/* 165 */         setPin(QA, Pin.PinState.HIGH);
/* 166 */         setPin(QB, Pin.PinState.LOW);
/* 167 */         setPin(QC, Pin.PinState.HIGH);
/* 168 */         setPin(QD, Pin.PinState.HIGH);
/* 169 */         break;
/*     */       case 14:
/* 171 */         setPin(QA, Pin.PinState.LOW);
/* 172 */         setPin(QB, Pin.PinState.HIGH);
/* 173 */         setPin(QC, Pin.PinState.HIGH);
/* 174 */         setPin(QD, Pin.PinState.HIGH);
/* 175 */         break;
/*     */       case 15:
/* 177 */         setPin(QA, Pin.PinState.HIGH);
/* 178 */         setPin(QB, Pin.PinState.HIGH);
/* 179 */         setPin(QC, Pin.PinState.HIGH);
/* 180 */         setPin(QD, Pin.PinState.HIGH);
/* 181 */         break;
/*     */       default:
/* 183 */         setPin(QA, Pin.PinState.LOW);
/* 184 */         setPin(QB, Pin.PinState.LOW);
/* 185 */         setPin(QC, Pin.PinState.LOW);
/* 186 */         setPin(QD, Pin.PinState.LOW);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try
/*     */     {
/* 195 */       for (Pin pin : this.pins)
/* 196 */         if (isPinDriven(pin.getPinName()))
/* 197 */           setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 200 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 206 */         updateGate("1A", "1CLR", "1QA", "1QB", "1QC", "1QD");
/* 207 */         updateGate("2A", "2CLR", "2QA", "2QB", "2QC", "2QD");
/*     */       }
/*     */       else
/*     */       {
/* 211 */         for (Pin pin : this.pins)
/* 212 */           if (isPinDriven(pin.getPinName()))
/* 213 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 218 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.counter.Gen74393
 * JD-Core Version:    0.6.2
 */