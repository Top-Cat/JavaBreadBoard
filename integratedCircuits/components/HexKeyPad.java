/*     */ package integratedCircuits.components;
/*     */ 
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.OutputPin;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import jBreadBoard.v1_10.DblClick;
/*     */ import java.awt.Container;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import javax.swing.JFrame;
/*     */ 
/*     */ public class HexKeyPad extends IntegratedCircuit
/*     */   implements DblClick
/*     */ {
/*  27 */   private JFrame frame = new JFrame("KeyPad");
/*  28 */   private KeyPad panel = new KeyPad(this);
/*     */ 
/*     */   public HexKeyPad()
/*     */   {
/*  35 */     initialise();
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  44 */     this.description = "Hexidecimal keypad";
/*  45 */     this.manufacturer = "Generic input device";
/*  46 */     this.diagram = ("images" + File.separator + "keyPad.gif");
/*  47 */     this.wide = false;
/*     */ 
/*  49 */     this.pins.add(new OutputPin(1, "OUT0"));
/*  50 */     this.pins.add(new OutputPin(2, "OUT1"));
/*  51 */     this.pins.add(new PowerPin(3, "GND"));
/*  52 */     this.pins.add(new OutputPin(4, "OUT2"));
/*  53 */     this.pins.add(new OutputPin(5, "OUT3"));
/*  54 */     this.pins.add(new PowerPin(6, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate(String A, String B, String C, String D) throws InvalidPinException {
/*  58 */     if ((this.panel.getValue() & 0x1) == 1)
/*  59 */       setPin(A, Pin.PinState.HIGH);
/*     */     else {
/*  61 */       setPin(A, Pin.PinState.LOW);
/*     */     }
/*  63 */     if ((this.panel.getValue() & 0x2) == 2)
/*  64 */       setPin(B, Pin.PinState.HIGH);
/*     */     else {
/*  66 */       setPin(B, Pin.PinState.LOW);
/*     */     }
/*  68 */     if ((this.panel.getValue() & 0x4) == 4)
/*  69 */       setPin(C, Pin.PinState.HIGH);
/*     */     else {
/*  71 */       setPin(C, Pin.PinState.LOW);
/*     */     }
/*  73 */     if ((this.panel.getValue() & 0x8) == 8)
/*  74 */       setPin(D, Pin.PinState.HIGH);
/*     */     else
/*  76 */       setPin(D, Pin.PinState.LOW);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try {
/*  85 */       if (isPowered()) {
/*  86 */         updateGate("OUT0", "OUT1", "OUT2", "OUT3");
/*     */       }
/*     */       else
/*     */       {
/*  90 */         for (Pin pin : this.pins)
/*  91 */           if (isPinDriven(pin.getPinName()))
/*  92 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/*  97 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void DblClicked() {
/* 102 */     this.frame.setDefaultCloseOperation(2);
/* 103 */     this.frame.getContentPane().add(this.panel);
/* 104 */     this.frame.pack();
/* 105 */     this.frame.setVisible(true);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.components.HexKeyPad
 * JD-Core Version:    0.6.2
 */