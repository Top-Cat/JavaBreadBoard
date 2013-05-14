/*     */ package integratedCircuits.cpu.jx_york_ac_uk.j1;
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
/*     */ public class Adder8bit extends IntegratedCircuit
/*     */ {
/*  37 */   private int width = 8;
/*     */ 
/*     */   public Adder8bit()
/*     */   {
/*  44 */     initialise();
/*     */ 
/*  46 */     this.pins.add(new OutputPin(27, "COUT"));
/*  47 */     this.pins.add(new OutputPin(26, "S7"));
/*  48 */     this.pins.add(new OutputPin(25, "S6"));
/*  49 */     this.pins.add(new OutputPin(24, "S5"));
/*  50 */     this.pins.add(new OutputPin(23, "S4"));
/*  51 */     this.pins.add(new OutputPin(22, "S3"));
/*  52 */     this.pins.add(new OutputPin(21, "S2"));
/*  53 */     this.pins.add(new OutputPin(20, "S1"));
/*  54 */     this.pins.add(new OutputPin(19, "S0"));
/*     */   }
/*     */ 
/*     */   public Adder8bit(int tplh, int tphl) {
/*  58 */     initialise();
/*     */ 
/*  60 */     this.pins.add(new OutputPin(27, "COUT", tplh, tphl));
/*  61 */     this.pins.add(new OutputPin(26, "S7", tplh, tphl));
/*  62 */     this.pins.add(new OutputPin(25, "S6", tplh, tphl));
/*  63 */     this.pins.add(new OutputPin(24, "S5", tplh, tphl));
/*  64 */     this.pins.add(new OutputPin(23, "S4", tplh, tphl));
/*  65 */     this.pins.add(new OutputPin(22, "S3", tplh, tphl));
/*  66 */     this.pins.add(new OutputPin(21, "S2", tplh, tphl));
/*  67 */     this.pins.add(new OutputPin(20, "S1", tplh, tphl));
/*  68 */     this.pins.add(new OutputPin(19, "S0", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  76 */     this.description = "Eight bit Adder with Cin and Cout";
/*  77 */     this.manufacturer = "Generic TTL gate";
/*  78 */     this.diagram = ("images" + File.separator + "Adder8bit.gif");
/*  79 */     this.wide = false;
/*     */ 
/*  81 */     this.pins.add(new InputPin(1, "CIN"));
/*  82 */     this.pins.add(new InputPin(2, "A0"));
/*  83 */     this.pins.add(new InputPin(3, "A1"));
/*  84 */     this.pins.add(new InputPin(4, "A2"));
/*  85 */     this.pins.add(new InputPin(5, "A3"));
/*  86 */     this.pins.add(new InputPin(6, "A4"));
/*  87 */     this.pins.add(new InputPin(7, "A5"));
/*  88 */     this.pins.add(new InputPin(8, "A6"));
/*  89 */     this.pins.add(new InputPin(9, "A7"));
/*  90 */     this.pins.add(new InputPin(10, "B0"));
/*  91 */     this.pins.add(new InputPin(11, "B1"));
/*  92 */     this.pins.add(new InputPin(12, "B2"));
/*  93 */     this.pins.add(new InputPin(13, "B3"));
/*  94 */     this.pins.add(new PowerPin(14, "GND"));
/*  95 */     this.pins.add(new InputPin(15, "B4"));
/*  96 */     this.pins.add(new InputPin(16, "B5"));
/*  97 */     this.pins.add(new InputPin(17, "B6"));
/*  98 */     this.pins.add(new InputPin(18, "B7"));
/*  99 */     this.pins.add(new PowerPin(28, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate() throws InvalidPinException {
/* 103 */     int dataA = 0;
/* 104 */     int dataB = 0;
/* 105 */     int total = 0;
/* 106 */     int sum = 0;
/*     */ 
/* 108 */     boolean dataAValid = true;
/* 109 */     boolean dataBValid = true;
/*     */ 
/* 113 */     for (int i = 0; i < this.width; i++) {
/* 114 */       String name = "A" + i;
/* 115 */       if (isHigh(name)) {
/* 116 */         dataA += (int)Math.pow(2.0D, i);
/*     */       }
/* 120 */       else if (!isLow(name))
/*     */       {
/* 124 */         dataAValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 129 */     for (int i = 0; i < this.width; i++) {
/* 130 */       String name = "B" + i;
/* 131 */       if (isHigh(name)) {
/* 132 */         dataB += (int)Math.pow(2.0D, i);
/*     */       }
/* 136 */       else if (!isLow(name))
/*     */       {
/* 140 */         dataBValid = false;
/*     */       }
/*     */     }
/* 143 */     if ((dataAValid) && (dataBValid)) {
/* 144 */       total = dataA + dataB;
/* 145 */       if (isHigh("CIN")) {
/* 146 */         total++;
/*     */       }
/* 148 */       sum = total & (int)Math.pow(2.0D, this.width) - 1;
/*     */ 
/* 152 */       for (int i = 0; i < this.width; i++) {
/* 153 */         String name = "S" + i;
/* 154 */         if ((sum & (int)Math.pow(2.0D, i)) != 0) {
/* 155 */           setPin(name, Pin.PinState.HIGH);
/*     */         }
/*     */         else {
/* 158 */           setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/* 161 */       if (total > (int)Math.pow(2.0D, this.width) - 1)
/* 162 */         setPin("COUT", Pin.PinState.HIGH);
/*     */       else
/* 164 */         setPin("COUT", Pin.PinState.LOW);
/*     */     }
/*     */     else {
/* 167 */       System.out.println("Invalid data");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset() {
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try {
/* 176 */       if (isPowered()) {
/* 177 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 181 */         for (Pin pin : this.pins)
/* 182 */           if (isPinDriven(pin.getPinName()))
/* 183 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 188 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.cpu.jx_york_ac_uk.j1.Adder8bit
 * JD-Core Version:    0.6.2
 */