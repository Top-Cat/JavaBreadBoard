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
/*     */ public class Register8bit extends IntegratedCircuit
/*     */ {
/*  34 */   private int width = 8;
/*     */ 
/*     */   public Register8bit()
/*     */   {
/*  41 */     initialise();
/*     */ 
/*  43 */     this.pins.add(new OutputPin(20, "ZERO"));
/*  44 */     this.pins.add(new OutputPin(19, "Q0"));
/*  45 */     this.pins.add(new OutputPin(18, "Q1"));
/*  46 */     this.pins.add(new OutputPin(17, "Q2"));
/*  47 */     this.pins.add(new OutputPin(16, "Q3"));
/*  48 */     this.pins.add(new OutputPin(15, "Q4"));
/*  49 */     this.pins.add(new OutputPin(14, "Q5"));
/*  50 */     this.pins.add(new OutputPin(13, "Q6"));
/*  51 */     this.pins.add(new OutputPin(12, "Q7"));
/*     */   }
/*     */ 
/*     */   public Register8bit(int tplh, int tphl) {
/*  55 */     initialise();
/*     */ 
/*  57 */     this.pins.add(new OutputPin(20, "ZERO", tplh, tphl));
/*  58 */     this.pins.add(new OutputPin(19, "Q0", tplh, tphl));
/*  59 */     this.pins.add(new OutputPin(18, "Q1", tplh, tphl));
/*  60 */     this.pins.add(new OutputPin(17, "Q2", tplh, tphl));
/*  61 */     this.pins.add(new OutputPin(16, "Q3", tplh, tphl));
/*  62 */     this.pins.add(new OutputPin(15, "Q4", tplh, tphl));
/*  63 */     this.pins.add(new OutputPin(14, "Q5", tplh, tphl));
/*  64 */     this.pins.add(new OutputPin(13, "Q6", tplh, tphl));
/*  65 */     this.pins.add(new OutputPin(12, "Q7", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  73 */     this.description = "Eight bit Register with En, Clr and Zero";
/*  74 */     this.manufacturer = "Generic TTL gate";
/*  75 */     this.diagram = ("images" + File.separator + "Register8bit.gif");
/*  76 */     this.wide = false;
/*     */ 
/*  78 */     this.pins.add(new InputPin(1, "CLK"));
/*  79 */     this.pins.add(new InputPin(2, "EN"));
/*  80 */     this.pins.add(new InputPin(3, "D0"));
/*  81 */     this.pins.add(new InputPin(4, "D1"));
/*  82 */     this.pins.add(new InputPin(5, "D2"));
/*  83 */     this.pins.add(new InputPin(6, "D3"));
/*  84 */     this.pins.add(new InputPin(7, "D4"));
/*  85 */     this.pins.add(new InputPin(8, "D5"));
/*  86 */     this.pins.add(new InputPin(9, "D6"));
/*  87 */     this.pins.add(new InputPin(10, "D7"));
/*  88 */     this.pins.add(new PowerPin(11, "GND"));
/*  89 */     this.pins.add(new InputPin(21, "CLR"));
/*  90 */     this.pins.add(new PowerPin(22, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate() throws InvalidPinException
/*     */   {
/*  95 */     if (isHigh("CLR"))
/*     */     {
/*  97 */       for (int i = 0; i < this.width; i++) {
/*  98 */         String output = "Q" + i;
/*  99 */         setPin(output, Pin.PinState.LOW);
/*     */       }
/*     */ 
/*     */     }
/* 103 */     else if ((isRisingEdge("CLK")) && (isHigh("EN"))) {
/* 104 */       boolean zero = true;
/*     */ 
/* 106 */       for (int i = 0; i < this.width; i++) {
/* 107 */         String input = "D" + i;
/* 108 */         String output = "Q" + i;
/* 109 */         if (isHigh(input))
/*     */         {
/* 111 */           setPin(output, Pin.PinState.HIGH);
/* 112 */           zero = false;
/*     */         }
/* 115 */         else if (isLow(input))
/*     */         {
/* 117 */           setPin(output, Pin.PinState.LOW);
/*     */         }
/*     */         else {
/* 120 */           System.out.println("Input not HIGH or LOW");
/*     */         }
/*     */       }
/*     */ 
/* 124 */       if (zero)
/* 125 */         setPin("ZERO", Pin.PinState.HIGH);
/*     */       else
/* 127 */         setPin("ZERO", Pin.PinState.LOW);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try {
/* 134 */       for (Pin pin : this.pins)
/* 135 */         if (isPinDriven(pin.getPinName()))
/* 136 */           setPin(pin.getPinName(), Pin.PinState.LOW);
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 139 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 145 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 149 */         for (Pin pin : this.pins)
/* 150 */           if (isPinDriven(pin.getPinName()))
/* 151 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 156 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.cpu.jx_york_ac_uk.j1.Register8bit
 * JD-Core Version:    0.6.2
 */