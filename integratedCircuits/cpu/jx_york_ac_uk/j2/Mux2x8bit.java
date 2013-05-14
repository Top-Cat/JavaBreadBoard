/*     */ package integratedCircuits.cpu.jx_york_ac_uk.j2;
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
/*     */ public class Mux2x8bit extends IntegratedCircuit
/*     */ {
/*  38 */   private int width = 8;
/*     */ 
/*     */   public Mux2x8bit()
/*     */   {
/*  45 */     initialise();
/*     */ 
/*  47 */     this.pins.add(new OutputPin(20, "S0"));
/*  48 */     this.pins.add(new OutputPin(21, "S1"));
/*  49 */     this.pins.add(new OutputPin(22, "S2"));
/*  50 */     this.pins.add(new OutputPin(23, "S3"));
/*  51 */     this.pins.add(new OutputPin(24, "S4"));
/*  52 */     this.pins.add(new OutputPin(25, "S5"));
/*  53 */     this.pins.add(new OutputPin(26, "S6"));
/*  54 */     this.pins.add(new OutputPin(27, "S7"));
/*     */   }
/*     */ 
/*     */   public Mux2x8bit(int tplh, int tphl) {
/*  58 */     initialise();
/*     */ 
/*  60 */     this.pins.add(new OutputPin(20, "S0", tplh, tphl));
/*  61 */     this.pins.add(new OutputPin(21, "S1", tplh, tphl));
/*  62 */     this.pins.add(new OutputPin(22, "S2", tplh, tphl));
/*  63 */     this.pins.add(new OutputPin(23, "S3", tplh, tphl));
/*  64 */     this.pins.add(new OutputPin(24, "S4", tplh, tphl));
/*  65 */     this.pins.add(new OutputPin(25, "S5", tplh, tphl));
/*  66 */     this.pins.add(new OutputPin(26, "S6", tplh, tphl));
/*  67 */     this.pins.add(new OutputPin(27, "S7", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  75 */     this.description = "Two channel 8bit MUX, ENA active low, ENB active high";
/*  76 */     this.manufacturer = "Generic TTL gate";
/*  77 */     this.diagram = ("images" + File.separator + "Mux2x8bit.gif");
/*  78 */     this.wide = false;
/*     */ 
/*  80 */     this.pins.add(new InputPin(1, "ENA"));
/*  81 */     this.pins.add(new InputPin(2, "ENB"));
/*  82 */     this.pins.add(new InputPin(3, "A0"));
/*  83 */     this.pins.add(new InputPin(4, "A1"));
/*  84 */     this.pins.add(new InputPin(5, "A2"));
/*  85 */     this.pins.add(new InputPin(6, "A3"));
/*  86 */     this.pins.add(new InputPin(7, "A4"));
/*  87 */     this.pins.add(new InputPin(8, "A5"));
/*  88 */     this.pins.add(new InputPin(9, "A6"));
/*  89 */     this.pins.add(new InputPin(10, "A7"));
/*  90 */     this.pins.add(new InputPin(11, "B0"));
/*  91 */     this.pins.add(new InputPin(12, "B1"));
/*  92 */     this.pins.add(new InputPin(13, "B2"));
/*  93 */     this.pins.add(new PowerPin(14, "GND"));
/*  94 */     this.pins.add(new InputPin(15, "B3"));
/*  95 */     this.pins.add(new InputPin(16, "B4"));
/*  96 */     this.pins.add(new InputPin(17, "B5"));
/*  97 */     this.pins.add(new InputPin(18, "B6"));
/*  98 */     this.pins.add(new InputPin(19, "B7"));
/*  99 */     this.pins.add(new PowerPin(28, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateOutput(String channel) throws InvalidPinException
/*     */   {
/* 104 */     for (int i = 0; i < this.width; i++) {
/* 105 */       String input = channel + i;
/* 106 */       String output = "S" + i;
/* 107 */       if (isHigh(input))
/*     */       {
/* 109 */         setPin(output, Pin.PinState.HIGH);
/*     */       }
/* 112 */       else if (isLow(input))
/*     */       {
/* 114 */         setPin(output, Pin.PinState.LOW);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateGate()
/*     */     throws InvalidPinException
/*     */   {
/* 133 */     if ((isLow("ENA")) && (isHigh("ENB"))) {
/* 134 */       updateOutput("B");
/*     */     }
/*     */     else
/* 137 */       updateOutput("A");
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try {
/* 143 */       for (Pin pin : this.pins)
/* 144 */         if (isPinDriven(pin.getPinName()))
/* 145 */           setPin(pin.getPinName(), Pin.PinState.LOW);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 148 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 154 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 158 */         for (Pin pin : this.pins)
/* 159 */           if (isPinDriven(pin.getPinName()))
/* 160 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     } catch (InvalidPinException e1)
/*     */     {
/* 164 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.cpu.jx_york_ac_uk.j2.Mux2x8bit
 * JD-Core Version:    0.6.2
 */