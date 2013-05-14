/*     */ package integratedCircuits.cpu.jx_york_ac_uk.j3;
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
/*     */ public class Stack8x8bit extends IntegratedCircuit
/*     */ {
/*  39 */   private int width = 8;
/*  40 */   private int depth = 8;
/*  41 */   private int[] stack = new int[this.depth];
/*  42 */   private int data_in = 0;
/*     */ 
/*     */   public Stack8x8bit()
/*     */   {
/*  49 */     initialise();
/*     */ 
/*  51 */     this.pins.add(new OutputPin(30, "ZERO"));
/*  52 */     this.pins.add(new OutputPin(29, "QB7"));
/*  53 */     this.pins.add(new OutputPin(28, "QB6"));
/*  54 */     this.pins.add(new OutputPin(27, "QB5"));
/*  55 */     this.pins.add(new OutputPin(26, "QB4"));
/*  56 */     this.pins.add(new OutputPin(25, "QB3"));
/*  57 */     this.pins.add(new OutputPin(24, "QB2"));
/*  58 */     this.pins.add(new OutputPin(23, "QB1"));
/*  59 */     this.pins.add(new OutputPin(22, "QB0"));
/*  60 */     this.pins.add(new OutputPin(21, "QA7"));
/*  61 */     this.pins.add(new OutputPin(20, "QA6"));
/*  62 */     this.pins.add(new OutputPin(19, "QA5"));
/*  63 */     this.pins.add(new OutputPin(18, "QA4"));
/*  64 */     this.pins.add(new OutputPin(17, "QA3"));
/*  65 */     this.pins.add(new OutputPin(15, "QA2"));
/*  66 */     this.pins.add(new OutputPin(14, "QA1"));
/*  67 */     this.pins.add(new OutputPin(13, "QA0"));
/*     */   }
/*     */ 
/*     */   public Stack8x8bit(int tplh, int tphl) {
/*  71 */     initialise();
/*     */ 
/*  73 */     this.pins.add(new OutputPin(30, "ZERO", tplh, tphl));
/*  74 */     this.pins.add(new OutputPin(29, "QB7", tplh, tphl));
/*  75 */     this.pins.add(new OutputPin(28, "QB6", tplh, tphl));
/*  76 */     this.pins.add(new OutputPin(27, "QB5", tplh, tphl));
/*  77 */     this.pins.add(new OutputPin(26, "QB4", tplh, tphl));
/*  78 */     this.pins.add(new OutputPin(25, "QB3", tplh, tphl));
/*  79 */     this.pins.add(new OutputPin(24, "QB2", tplh, tphl));
/*  80 */     this.pins.add(new OutputPin(23, "QB1", tplh, tphl));
/*  81 */     this.pins.add(new OutputPin(22, "QB0", tplh, tphl));
/*  82 */     this.pins.add(new OutputPin(21, "QA7", tplh, tphl));
/*  83 */     this.pins.add(new OutputPin(20, "QA6", tplh, tphl));
/*  84 */     this.pins.add(new OutputPin(19, "QA5", tplh, tphl));
/*  85 */     this.pins.add(new OutputPin(18, "QA4", tplh, tphl));
/*  86 */     this.pins.add(new OutputPin(17, "QA3", tplh, tphl));
/*  87 */     this.pins.add(new OutputPin(15, "QA2", tplh, tphl));
/*  88 */     this.pins.add(new OutputPin(14, "QA1", tplh, tphl));
/*  89 */     this.pins.add(new OutputPin(13, "QA0", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  97 */     this.description = "Eight bit,  eight entry statck with En, Rst and Zero";
/*  98 */     this.manufacturer = "Generic TTL gate";
/*  99 */     this.diagram = ("images" + File.separator + "Stack8x8bit.gif");
/* 100 */     this.wide = false;
/*     */ 
/* 102 */     this.pins.add(new InputPin(1, "CLK"));
/* 103 */     this.pins.add(new InputPin(2, "WR"));
/* 104 */     this.pins.add(new InputPin(3, "PSH"));
/* 105 */     this.pins.add(new InputPin(4, "POP"));
/* 106 */     this.pins.add(new InputPin(5, "D0"));
/* 107 */     this.pins.add(new InputPin(6, "D1"));
/* 108 */     this.pins.add(new InputPin(7, "D2"));
/* 109 */     this.pins.add(new InputPin(8, "D3"));
/* 110 */     this.pins.add(new InputPin(9, "D4"));
/* 111 */     this.pins.add(new InputPin(10, "D5"));
/* 112 */     this.pins.add(new InputPin(11, "D6"));
/* 113 */     this.pins.add(new InputPin(12, "D7"));
/* 114 */     this.pins.add(new PowerPin(16, "GND"));
/* 115 */     this.pins.add(new InputPin(31, "RST"));
/* 116 */     this.pins.add(new PowerPin(32, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate()
/*     */     throws InvalidPinException
/*     */   {
/* 125 */     if (isHigh("RST"))
/*     */     {
/* 127 */       for (int i = 0; i < this.width; i++) {
/* 128 */         String output = "Q" + i;
/* 129 */         setPin(output, Pin.PinState.LOW);
/*     */       }
/* 131 */       for (int i = 0; i < this.depth; i++) {
/* 132 */         this.stack[i] = 0;
/*     */       }
/*     */ 
/*     */     }
/* 136 */     else if (isRisingEdge("CLK")) {
/* 137 */       boolean zero = true;
/* 138 */       this.data_in = 0;
/*     */ 
/* 141 */       for (int i = 0; i < this.width; i++) {
/* 142 */         String input = "D" + i;
/* 143 */         if (isHigh(input))
/*     */         {
/* 145 */           this.data_in += (int)Math.pow(2.0D, i);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 151 */       if (isHigh("PSH"))
/*     */       {
/* 154 */         for (int i = 0; i < this.depth - 1; i++) {
/* 155 */           this.stack[(this.depth - 1 - i)] = this.stack[(this.depth - 2 - i)];
/*     */         }
/*     */ 
/*     */       }
/* 159 */       else if (isHigh("POP"))
/*     */       {
/* 162 */         for (int i = 0; i < this.depth - 1; i++) {
/* 163 */           this.stack[i] = this.stack[(i + 1)];
/*     */         }
/* 165 */         this.stack[(this.depth - 1)] = 0;
/*     */       }
/*     */ 
/* 168 */       if (isHigh("WR"))
/*     */       {
/* 170 */         this.stack[0] = this.data_in;
/*     */       }
/*     */ 
/* 176 */       for (int i = 0; i < this.width; i++) {
/* 177 */         String output = "QA" + i;
/* 178 */         if (((int)Math.pow(2.0D, i) & this.stack[0]) != 0)
/*     */         {
/* 180 */           setPin(output, Pin.PinState.HIGH);
/* 181 */           zero = false;
/*     */         }
/*     */         else
/*     */         {
/* 185 */           setPin(output, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */ 
/* 189 */       for (int i = 0; i < this.width; i++) {
/* 190 */         String output = "QB" + i;
/* 191 */         if (((int)Math.pow(2.0D, i) & this.stack[1]) != 0)
/*     */         {
/* 193 */           setPin(output, Pin.PinState.HIGH);
/*     */         }
/*     */         else
/*     */         {
/* 197 */           setPin(output, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */ 
/* 201 */       if (zero)
/* 202 */         setPin("ZERO", Pin.PinState.HIGH);
/*     */       else {
/* 204 */         setPin("ZERO", Pin.PinState.LOW);
/*     */       }
/* 206 */       System.out.print("Stack ");
/* 207 */       if (isHigh("PSH"))
/* 208 */         System.out.print("PSH ");
/* 209 */       if (isHigh("POP"))
/* 210 */         System.out.print("POP ");
/* 211 */       if (isHigh("WR"))
/* 212 */         System.out.print("WR ");
/* 213 */       System.out.print(": ");
/*     */ 
/* 215 */       for (int i = 0; i < this.depth; i++) {
/* 216 */         System.out.print(this.stack[i] + " ");
/*     */       }
/* 218 */       System.out.println(" : ");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */     try {
/* 225 */       for (Pin pin : this.pins) {
/* 226 */         if (isPinDriven(pin.getPinName())) {
/* 227 */           setPin(pin.getPinName(), Pin.PinState.LOW);
/*     */         }
/*     */       }
/* 230 */       for (int i = 0; i < this.depth; i++)
/* 231 */         this.stack[i] = 0;
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 234 */       System.out.println("OPPS: InvalidPinException " + e1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try { if (isPowered()) {
/* 240 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 244 */         for (Pin pin : this.pins)
/* 245 */           if (isPinDriven(pin.getPinName()))
/* 246 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 251 */       System.out.println("OPPS: InvalidPinException " + e1);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.cpu.jx_york_ac_uk.j3.Stack8x8bit
 * JD-Core Version:    0.6.2
 */