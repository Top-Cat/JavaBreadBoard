/*     */ package jBreadBoard;
/*     */ 
/*     */ import integratedCircuits.ttl.generic.InvalidStateException;
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import jBreadBoard.v1_10.LoadSave;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.io.StringReader;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class AdvChip
/*     */ {
/*     */   public static String[] getInterfaces(Class chipModel)
/*     */   {
/*  25 */     Class[] interfaces = chipModel.getInterfaces();
/*  26 */     String[] retVal = new String[interfaces.length];
/*     */ 
/*  28 */     for (int i = 0; i < interfaces.length; i++) {
/*  29 */       retVal[i] = interfaces[i].getName();
/*     */     }
/*  31 */     return retVal;
/*     */   }
/*     */ 
/*     */   public static Circuit insertCircuit(JFrame jframe, Circuit circuit, int xoffset, int yoffset)
/*     */   {
/*  40 */     String file = FileDialogs.load(jframe);
/*     */ 
/*  44 */     if (file != null)
/*     */     {
/*  46 */       StringReader sr = new StringReader(file);
/*  47 */       StreamTokenizer st = new StreamTokenizer(sr);
/*  48 */       st.parseNumbers();
/*  49 */       st.wordChars(47, 47);
/*  50 */       st.wordChars(95, 95);
/*     */       try
/*     */       {
/*  53 */         String modelname = "";
/*  54 */         int derivative = 0;
/*  55 */         int packageno = 0;
/*  56 */         int xpos = 0;
/*  57 */         int ypos = 0;
/*  58 */         boolean wiring = false;
/*     */ 
/*  60 */         st.nextToken();
/*  61 */         if (st.sval.equals("Circuit")) {
/*  62 */           while (st.nextToken() != -1) {
/*  63 */             if (st.ttype == -3) {
/*  64 */               if ((!wiring) && (st.sval.equals("Board"))) {
/*  65 */                 if (st.nextToken() == -3) {
/*  66 */                   String classname = st.sval;
/*  67 */                   st.nextToken();
/*  68 */                   xpos = (int)st.nval;
/*  69 */                   st.nextToken();
/*  70 */                   ypos = (int)st.nval;
/*     */ 
/*  75 */                   if (classname.equals("jBreadBoard.BreadBoard")) {
/*  76 */                     BreadBoard bb = new BreadBoard(circuit);
/*  77 */                     circuit.add(bb, Circuit.BoardLayer);
/*  78 */                     bb.setLocation(xpos + xoffset, ypos + yoffset);
/*  79 */                     circuit.select(bb);
/*     */                   }
/*     */                 }
/*  82 */               } else if ((!wiring) && (st.sval.equals("Probe"))) {
/*  83 */                 if (st.nextToken() == 34) {
/*  84 */                   String label = st.sval;
/*  85 */                   st.nextToken();
/*  86 */                   xpos = (int)st.nval;
/*  87 */                   st.nextToken();
/*  88 */                   ypos = (int)st.nval;
/*     */ 
/*  90 */                   Probe probe = new Probe(circuit);
/*  91 */                   probe.setLabel(label);
/*  92 */                   probe.setLocation(xpos, ypos);
/*     */ 
/*  94 */                   circuit.BBselect.add(probe);
/*  95 */                   probe.updateConnections();
/*     */                 }
/*  97 */               } else if ((!wiring) && (st.sval.equals("Device"))) {
/*  98 */                 if (st.nextToken() == -3) {
/*  99 */                   String classname = st.sval;
/*     */ 
/* 101 */                   if (st.nextToken() == -3) {
/* 102 */                     modelname = st.sval;
/* 103 */                     st.nextToken();
/* 104 */                     derivative = (int)st.nval;
/* 105 */                     st.nextToken();
/* 106 */                     packageno = (int)st.nval;
/* 107 */                     st.nextToken();
/*     */                   }
/*     */ 
/* 110 */                   xpos = (int)st.nval;
/* 111 */                   st.nextToken();
/* 112 */                   ypos = (int)st.nval;
/*     */ 
/* 117 */                   if (classname.equals("jBreadBoard.Chip")) {
/* 118 */                     ChipModel model = JBreadBoard.getChipModel(modelname);
/*     */ 
/* 123 */                     if (JBreadBoard.implementsInterface(model, "jBreadBoard.v1_10.LoadSave")) {
/* 124 */                       st.nextToken();
/* 125 */                       String filename = st.sval;
/*     */                       try {
/* 127 */                         ((LoadSave)model).initialiseState(filename);
/*     */                       }
/*     */                       catch (InvalidStateException e1) {
/*     */                       }
/*     */                     }
/* 132 */                     Chip chip = new Chip(circuit, model);
/* 133 */                     circuit.BBselect.add(chip);
/*     */ 
/* 135 */                     chip.setLocation(xpos, ypos);
/*     */ 
/* 137 */                     chip.updateConnections();
/*     */                   }
/* 140 */                   else if (classname.equals("jBreadBoard.LED")) {
/* 141 */                     st.nextToken();
/* 142 */                     int color = (int)st.nval;
/*     */ 
/* 144 */                     LED l = new LED(circuit, color);
/* 145 */                     circuit.BBselect.add(l);
/* 146 */                     l.setLocation(xpos, ypos);
/* 147 */                     l.updateConnections();
/* 148 */                   } else if (classname.equals("jBreadBoard.Dipswitch")) {
/* 149 */                     st.nextToken();
/* 150 */                     int pins = (int)st.nval;
/*     */ 
/* 152 */                     Dipswitch d = new Dipswitch(circuit, pins);
/* 153 */                     circuit.BBselect.add(d);
/* 154 */                     d.setLocation(xpos, ypos);
/* 155 */                     d.updateConnections();
/*     */                   }
/*     */                 }
/* 158 */               } else if (st.sval.equals("Wire")) {
/* 159 */                 wiring = true;
/*     */ 
/* 169 */                 st.nextToken(); int red = (int)st.nval;
/* 170 */                 st.nextToken(); int green = (int)st.nval;
/* 171 */                 st.nextToken(); int blue = (int)st.nval;
/*     */ 
/* 173 */                 Color color = new Color(red, green, blue);
/*     */ 
/* 175 */                 st.nextToken(); int x = xoffset + (int)st.nval;
/* 176 */                 st.nextToken(); int y = yoffset + (int)st.nval;
/* 177 */                 st.nextToken(); int x2 = xoffset + (int)st.nval;
/* 178 */                 st.nextToken(); int y2 = yoffset + (int)st.nval;
/*     */ 
/* 181 */                 Wire wire = circuit.drawWire(color, x, y, x2, y2);
/* 182 */                 boolean end = false;
/*     */ 
/* 184 */                 while (!end) {
/* 185 */                   x = x2;
/* 186 */                   y = x2;
/* 187 */                   if (st.nextToken() == -2) {
/* 188 */                     x2 = xoffset + (int)st.nval;
/* 189 */                     if (st.nextToken() == -2) {
/* 190 */                       y2 = yoffset + (int)st.nval;
/* 191 */                       wire = circuit.extendWire(wire, color, x, y, x2, y2); } else {
/* 192 */                       end = true;
/*     */                     } } else { end = true; }
/*     */ 
/*     */                 }
/*     */ 
/* 197 */                 st.pushBack();
/*     */ 
/* 201 */                 wire.fixate();
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 208 */         int maxx = 0; int maxy = 0;
/* 209 */         Component[] components = circuit.getComponentsInLayer(Circuit.BoardLayer);
/*     */ 
/* 211 */         for (int i = 0; i < components.length; i++) {
/* 212 */           int x2 = components[i].getX() + components[i].getWidth();
/* 213 */           int y2 = components[i].getY() + components[i].getHeight();
/* 214 */           if (x2 > maxx) maxx = x2;
/* 215 */           if (y2 > maxy) maxy = y2;
/*     */         }
/*     */ 
/* 218 */         circuit.setPreferredSize(new Dimension(maxx, maxy));
/* 219 */         circuit.revalidate();
/*     */ 
/* 221 */         sr.close();
/*     */       }
/*     */       catch (IOException e) {
/* 224 */         JOptionPane.showMessageDialog(jframe, "Error Reading Circuit File.\n" + e.getMessage(), "Error AdvChip.001", 0);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 231 */     return circuit;
/*     */   }
/*     */ 
/*     */   static int lowestBoard(Circuit circuit)
/*     */   {
/* 240 */     int ypos = 0;
/*     */ 
/* 242 */     StringReader sr = new StringReader(circuit.toString());
/* 243 */     StreamTokenizer st = new StreamTokenizer(sr);
/* 244 */     st.parseNumbers();
/*     */     try
/*     */     {
/* 247 */       while (st.nextToken() != -1)
/* 248 */         if ((st.ttype == -3) && 
/* 249 */           (st.sval.equals("Board")) && 
/* 250 */           (st.nextToken() == -3)) {
/* 251 */           st.nextToken();
/* 252 */           st.nextToken();
/* 253 */           int current = (int)st.nval;
/* 254 */           if (current > ypos) ypos = current;
/*     */         }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 259 */       System.err.println("Um.. something bad happened AdvChip.lowestBoard.. input:" + circuit);
/*     */     }
/* 261 */     return ypos + 168;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.AdvChip
 * JD-Core Version:    0.6.2
 */