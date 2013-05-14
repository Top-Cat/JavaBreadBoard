/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class JBreadBoardActionListener
/*     */   implements ActionListener
/*     */ {
/*     */   private JBreadBoard jBreadBoard;
/*  32 */   private int defaultdip = 3;
/*     */ 
/*     */   public JBreadBoardActionListener(JBreadBoard j)
/*     */   {
/*  38 */     this.jBreadBoard = j;
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  45 */     String command = e.getActionCommand();
/*  46 */     Circuit circuit = this.jBreadBoard.getCircuit();
/*     */ 
/*  49 */     circuit.pause();
/*     */ 
/*  51 */     if ((this.jBreadBoard.getMode().equals("drawingwire")) && (WireDrawer.current != null))
/*     */     {
/*  53 */       if ((command != null) && (command.equals("cancelsegment"))) {
/*  54 */         Wire wire = WireDrawer.current;
/*  55 */         if (wire.prev == null)
/*     */         {
/*  57 */           this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");
/*     */         } else {
/*  59 */           wire = wire.prev;
/*  60 */           WireDrawer.last = wire.prev;
/*  61 */           WireDrawer.current = wire;
/*     */ 
/*  63 */           circuit.remove(wire.next);
/*  64 */           wire.next = null;
/*  65 */           circuit.repaint();
/*     */         }
/*     */       }
/*     */       else {
/*  69 */         this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");
/*     */ 
/*  71 */         if ((command != null) && (command.equals("delete"))) return;
/*     */       }
/*     */     }
/*     */ 
/*  75 */     if (command == null) return;
/*     */ 
/*  78 */     if (command.equals("new")) { this.jBreadBoard.newcircuit();
/*  79 */     } else if (command.equals("load")) { this.jBreadBoard.load();
/*  80 */     } else if (command.equals("insertcircuit")) { this.jBreadBoard.insertCircuit();
/*  81 */     } else if (command.equals("save")) { this.jBreadBoard.save();
/*  82 */     } else if (command.equals("exit")) { this.jBreadBoard.exit(); }
/*  85 */     else if (command.equals("select")) { this.jBreadBoard.setMode("move");
/*  86 */     } else if (command.equals("delete")) {
/*  87 */       if (!this.jBreadBoard.getMode().equals("sim")) circuit.delete(); else {
/*  88 */         this.jBreadBoard.setMessage("You can not remove components during a simulation.");
/*     */       }
/*     */ 
/*     */     }
/*  92 */     else if (command.equals("breadboard")) {
/*  93 */       BreadBoard b = new BreadBoard(circuit);
/*  94 */       circuit.place(b);
/*  95 */       this.jBreadBoard.setMode("move");
/*     */     }
/*  97 */     else if (command.equals("chip")) {
/*  98 */       if (circuit.BBselect != null) {
/*  99 */         ChipSelector.addChipDialog(this.jBreadBoard.getJFrame(), this.jBreadBoard);
/* 100 */         this.jBreadBoard.setMode("move");
/*     */       }
/*     */     }
/* 103 */     else if (command.equals("defaultchip")) {
/* 104 */       if (circuit.BBselect != null) {
/* 105 */         Chip chip = new Chip(circuit, ChipSelector.getDefaultModel());
/* 106 */         circuit.BBselect.add(chip);
/* 107 */         this.jBreadBoard.setMode("move");
/*     */       }
/*     */     }
/* 110 */     else if (command.equals("dip")) { addDip(0);
/* 111 */     } else if (command.equals("dip1")) { addDip(1);
/* 112 */     } else if (command.equals("dip2")) { addDip(2);
/* 113 */     } else if (command.equals("dip3")) { addDip(3);
/* 114 */     } else if (command.equals("dip4")) { addDip(4); }
/* 116 */     else if (command.equals("led")) {
/* 117 */       if (circuit.BBselect != null) {
/* 118 */         LED l = new LED(circuit);
/* 119 */         circuit.BBselect.add(l);
/* 120 */         this.jBreadBoard.setMode("move");
/*     */       }
/*     */     }
/* 123 */     else if (command.equals("rled")) { addLED(0);
/* 124 */     } else if (command.equals("gled")) { addLED(2);
/* 125 */     } else if (command.equals("yled")) { addLED(1); }
/* 128 */     else if (command.equals("wire")) {
/* 129 */       circuit.unhideWires();
/* 130 */       this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");
/*     */     }
/* 132 */     else if (command.equals("hidewires")) { circuit.hideWires();
/* 133 */     } else if (command.equals("unhidewires")) { circuit.unhideWires(); }
/* 136 */     else if (command.equals("animate")) { circuit.run();
/* 137 */     } else if (command.equals("pause")) { circuit.pause();
/* 138 */     } else if (command.equals("step")) { circuit.stepSimulation();
/* 139 */     } else if (command.equals("reset")) {
/* 140 */       circuit.resetSimulation();
/* 141 */       this.jBreadBoard.setMode("move");
/*     */     }
/* 145 */     else if (command.equals("addprobe")) {
/* 146 */       if (circuit.BBselect != null) {
/* 147 */         Probe p = new Probe(circuit);
/* 148 */         circuit.BBselect.add(p);
/* 149 */         this.jBreadBoard.setMode("move");
/*     */       }
/*     */     }
/* 152 */     else if (command.equals("savetrace")) {
/* 153 */       circuit.updateTrace();
/* 154 */       FileDialogs.save(this.jBreadBoard, circuit.getTrace().getTrace());
/*     */     }
/* 156 */     else if (command.equals("toggledualdata")) {
/* 157 */       Trace.duplicate = !Trace.duplicate;
/* 158 */       circuit.resetSimulation();
/*     */     }
/* 161 */     else if (command.equals("userguide")) {
/* 162 */       HTMLDialog.addHTMLDialog(this.jBreadBoard.getURL("guide.html"), "User Guide");
/* 163 */     } else if (command.equals("loadsave")) {
/* 164 */       HTMLDialog.addHTMLDialog(this.jBreadBoard.getURL("loadsave.html"), "Enabling Loading and Saving");
/* 165 */     } else if (command.equals("about")) {
/* 166 */       JOptionPane.showMessageDialog(this.jBreadBoard.getJFrame(), "Java BreadBoard Simulator version 1.11 (01 July 2010)\n\nOriginally Created by Nicholas Glass 2001-2002\nfor the University of York\nComputer Science Department\nUpdated by:\nStephen Halstead\nRobert Page\nMichael Freeman\nChris Bailey", "About Java BreadBoard Simulator", 1, this.jBreadBoard.getImageIcon("images/iboard.gif"));
/*     */     }
/* 182 */     else if (command.startsWith("tool:"))
/*     */     {
/* 184 */       JFrame frame = null;
/* 185 */       String s = command.substring(5, command.lastIndexOf("."));
/*     */       try
/*     */       {
/* 192 */         Class SS = Class.forName("tools." + s);
/*     */         try
/*     */         {
/* 200 */           Thread t = (Thread)SS.newInstance();
/* 201 */           t.start();
/*     */         }
/*     */         catch (IllegalAccessException ex)
/*     */         {
/* 205 */           ex.printStackTrace();
/*     */         }
/*     */         catch (InstantiationException ex)
/*     */         {
/* 209 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (ClassNotFoundException g)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addLED(int color)
/*     */   {
/* 227 */     Circuit circuit = this.jBreadBoard.getCircuit();
/*     */ 
/* 229 */     LED.setDefaultColor(color);
/* 230 */     if (circuit.BBselect != null) {
/* 231 */       LED l = new LED(circuit);
/* 232 */       circuit.BBselect.add(l);
/* 233 */       this.jBreadBoard.setMode("move");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addDip(int pins)
/*     */   {
/* 243 */     Circuit circuit = this.jBreadBoard.getCircuit();
/*     */ 
/* 245 */     if (pins <= 0) pins = this.defaultdip;
/* 246 */     this.defaultdip = pins;
/* 247 */     if (this.jBreadBoard.getCircuit().BBselect != null) {
/* 248 */       circuit.BBselect.add(new Dipswitch(circuit, pins));
/* 249 */       this.jBreadBoard.setMode("move");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.JBreadBoardActionListener
 * JD-Core Version:    0.6.2
 */