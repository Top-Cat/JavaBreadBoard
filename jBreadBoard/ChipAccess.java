/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class ChipAccess
/*     */ {
/*     */   private Chip chip;
/*     */ 
/*     */   public ChipAccess(Chip c)
/*     */   {
/*  30 */     this.chip = c;
/*     */   }
/*     */ 
/*     */   public String save(String filestring)
/*     */   {
/*  51 */     return FileDialogs.save(this.chip, filestring);
/*     */   }
/*     */ 
/*     */   public String load()
/*     */   {
/*  67 */     return FileDialogs.load(this.chip);
/*     */   }
/*     */ 
/*     */   public long getSimTime()
/*     */   {
/*  78 */     return this.chip.getCircuit().getSimTime();
/*     */   }
/*     */ 
/*     */   public String readTTL(int pin)
/*     */   {
/*  86 */     return this.chip.getConnections()[pin].getPotential().toTTL();
/*     */   }
/*     */ 
/*     */   public void write(int pin, String value, int delay)
/*     */   {
/* 100 */     if ((value == null) || ((value != null) && (!value.equals("HIGH")) && (!value.equals("LOW")) && (!value.equals("NC"))))
/*     */     {
/* 105 */       JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to use an invalid output value: " + value, "Error ChipAccess.001 in Chip Model", 0);
/*     */ 
/* 113 */       this.chip.getCircuit().pause();
/* 114 */       this.chip.getCircuit().resetSimulation();
/*     */     }
/* 116 */     else if (delay < 0) {
/* 117 */       JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to use a negative delay: " + delay, "Error ChipAccess.002 in Chip Model", 0);
/*     */ 
/* 125 */       this.chip.getCircuit().pause();
/* 126 */       this.chip.getCircuit().resetSimulation();
/*     */     }
/* 128 */     else if ((pin < 0) || (pin >= this.chip.getNumberOfPins() * 2)) {
/* 129 */       JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to write to a pin that did not exist " + pin, "Error ChipAccess.003 in Chip Model", 0);
/*     */ 
/* 137 */       this.chip.getCircuit().pause();
/* 138 */       this.chip.getCircuit().resetSimulation();
/*     */     }
/* 140 */     else if ((this.chip.getPinType(pin).equals("IN")) || (this.chip.getPinType(pin).equals("NC"))) {
/* 141 */       JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this.chip), "Error in chip model: " + this.chip.getModelName() + ".\n" + this.chip.getChipText() + " tried to write to pin " + pin + " which is declared type " + this.chip.getPinType(pin) + ".", "Error ChipAccess.004 in Chip Model", 0);
/*     */ 
/* 149 */       this.chip.getCircuit().pause();
/* 150 */       this.chip.getCircuit().resetSimulation();
/*     */     }
/*     */     else {
/* 153 */       this.chip.getCircuit().getSimulation().addEvent(this.chip.getConnections()[pin], delay, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFile(String filename)
/*     */   {
/*     */     try
/*     */     {
/* 161 */       File file = new File(filename);
/* 162 */       FileInputStream fr = new FileInputStream(file);
/* 163 */       InputStreamReader ir = new InputStreamReader(fr, "ISO-8859-1");
/* 164 */       BufferedReader in = new BufferedReader(ir);
/*     */ 
/* 166 */       String input = in.readLine();
/*     */       String line;
/* 169 */       while ((line = in.readLine()) != null) {
/* 170 */         input = input + "\n" + line;
/*     */       }
/*     */ 
/* 173 */       in.close();
/* 174 */       fr.close();
/*     */ 
/* 176 */       return input;
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */ 
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 195 */     return this.chip.getWidth();
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 199 */     return this.chip.getHeight();
/*     */   }
/*     */   public void repaint() {
/* 202 */     this.chip.repaint();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.ChipAccess
 * JD-Core Version:    0.6.2
 */