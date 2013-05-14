/*     */ package integratedCircuits.components;
/*     */ 
/*     */ import integratedCircuits.InputPin;
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import jBreadBoard.ChipAccess;
/*     */ import jBreadBoard.v1_10.Graphical;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HexDisplay extends IntegratedCircuit
/*     */   implements Graphical
/*     */ {
/*  23 */   private int val = 8;
/*     */ 
/*     */   public HexDisplay()
/*     */   {
/*  30 */     initialise();
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  39 */     this.description = "Seven segment LED display";
/*  40 */     this.manufacturer = "Generic display component";
/*  41 */     this.diagram = ("images" + File.separator + "7Seg.gif");
/*  42 */     this.wide = true;
/*     */ 
/*  44 */     this.pins.add(new InputPin(1, "IN0"));
/*  45 */     this.pins.add(new InputPin(2, "IN1"));
/*  46 */     this.pins.add(new PowerPin(3, "GND"));
/*  47 */     this.pins.add(new InputPin(4, "IN2"));
/*  48 */     this.pins.add(new InputPin(5, "IN3"));
/*  49 */     this.pins.add(new PowerPin(6, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateDisplay(String A, String B, String C, String D) throws InvalidPinException {
/*  53 */     this.val = 0;
/*  54 */     if (isHigh(A)) this.val += 1;
/*  55 */     if (isHigh(B)) this.val += 2;
/*  56 */     if (isHigh(C)) this.val += 4;
/*  57 */     if (isHigh(D)) this.val += 8;
/*  58 */     this.chip.repaint();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try {
/*  67 */       if (isPowered()) {
/*  68 */         updateDisplay("IN0", "IN1", "IN2", "IN3");
/*     */       }
/*     */       else
/*     */       {
/*  72 */         for (Pin pin : this.pins)
/*  73 */           if (isPinDriven(pin.getPinName()))
/*  74 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/*  79 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g, boolean selected)
/*     */   {
/*  86 */     int[][] led = { { 1, 1, 1, 0, 1, 1, 1 }, { 0, 0, 1, 0, 0, 1, 0 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 0, 1, 1 }, { 0, 1, 1, 1, 0, 1, 0 }, { 1, 1, 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 1, 0, 0, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 0, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 0, 1 }, { 0, 0, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 0, 1 }, { 1, 1, 0, 1, 1, 0, 0 } };
/*     */ 
/* 104 */     Color chipcolor = Color.black;
/* 105 */     Color pincolor = Color.lightGray;
/* 106 */     Color ledcolor = Color.red;
/*     */ 
/* 109 */     g.setColor(chipcolor);
/* 110 */     g.fillRect(0, 4, this.chip.getWidth() - 1, this.chip.getHeight() - 9);
/*     */ 
/* 113 */     g.setColor(ledcolor);
/*     */ 
/* 116 */     if (led[this.val][0] == 1) g.fillRect(2, 8, this.chip.getWidth() - 4, 4);
/* 117 */     if (led[this.val][3] == 1) g.fillRect(2, (this.chip.getHeight() - 8) / 2, this.chip.getWidth() - 4, 4);
/* 118 */     if (led[this.val][6] == 1) g.fillRect(2, this.chip.getHeight() - 16, this.chip.getWidth() - 4, 4);
/*     */ 
/* 121 */     if (led[this.val][1] == 1) g.fillRect(2, 8, 4, this.chip.getHeight() / 2 - 8);
/* 122 */     if (led[this.val][4] == 1) g.fillRect(2, (this.chip.getHeight() - 8) / 2, 4, this.chip.getHeight() / 2 - 8);
/* 123 */     if (led[this.val][2] == 1) g.fillRect(this.chip.getWidth() - 6, 8, 4, this.chip.getHeight() / 2 - 8);
/* 124 */     if (led[this.val][5] == 1) g.fillRect(this.chip.getWidth() - 6, (this.chip.getHeight() - 8) / 2, 4, this.chip.getHeight() / 2 - 8);
/*     */ 
/* 128 */     g.setColor(pincolor);
/* 129 */     for (int i = 0; i < getNumberOfPins(); i++) {
/* 130 */       g.fillRect(i * 8 + 1, 2, 5, 2);
/* 131 */       g.fillRect(i * 8 + 1, this.chip.getHeight() - 5, 5, 2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.components.HexDisplay
 * JD-Core Version:    0.6.2
 */