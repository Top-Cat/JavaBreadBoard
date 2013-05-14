/*     */ package designTools;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class CircuitComponent extends JLabel
/*     */ {
/*     */   static final int COMPONENT_COUNT = 8;
/*     */   static final int AND = 0;
/*     */   static final int OR = 1;
/*     */   static final int NOT = 2;
/*     */   static final int INPUT_PIN = 3;
/*     */   static final int OUTPUT_PIN = 4;
/*     */   static final int GND = 5;
/*     */   static final int VCC = 6;
/*     */   static final int LABEL = 7;
/*  35 */   private static final String[] imageFilenames = { "build/classes/designTools/icons/and.gif", "build/classes/designTools/icons/or.gif", "build/classes/designTools/icons/not.gif", "build/classes/designTools/icons/in.gif", "build/classes/designTools/icons/out.gif", "build/classes/designTools/icons/gnd.gif", "build/classes/designTools/icons/vcc.gif", null };
/*     */ 
/*  47 */   private static final String[] menuIconFilenames = { "build/classes/designTools/icons/and_menu.gif", "build/classes/designTools/icons/or_menu.gif", "build/classes/designTools/icons/not_menu.gif", "build/classes/designTools/icons/in_menu.gif", "build/classes/designTools/icons/out_menu.gif", "build/classes/designTools/icons/gnd_menu.gif", "build/classes/designTools/icons/vcc_menu.gif", null };
/*     */ 
/*  60 */   private Point[][][] pinOffsets = { { { new Point(0, 20), new Point(0, 40) }, { new Point(60, 30) } }, { { new Point(0, 20), new Point(0, 40) }, { new Point(60, 30) } }, { { new Point(0, 20) }, { new Point(60, 20) } }, { new Point[0], { new Point(60, 20) } }, { { new Point(0, 20) }, new Point[0] }, { new Point[0], { new Point(40, 20) } }, { new Point[0], { new Point(40, 20) } }, { new Point[0], new Point[0] } };
/*     */   private int ctype;
/*     */   private BufferedImage image;
/*  73 */   private ArrayList inList = new ArrayList();
/*  74 */   private boolean state = false;
/*     */ 
/*     */   public CircuitComponent(int type)
/*     */   {
/*  80 */     this.ctype = type;
/*  81 */     if ((type == 3) || (type == 4))
/*     */     {
/*  84 */       String pinLabel = JOptionPane.showInputDialog(null, "Please give a name for this pin\n(maximum 12 characters)", "Pin Label", -1);
/*     */ 
/*  86 */       if (pinLabel == null) pinLabel = "";
/*     */ 
/*  88 */       if (pinLabel.length() > 12) pinLabel = pinLabel.substring(0, 12);
/*     */ 
/*  90 */       setText(pinLabel);
/*     */ 
/*  92 */       if (this.ctype == 3) setHorizontalTextPosition(2);
/*     */     }
/*     */ 
/*  95 */     if (imageFilenames[type] != null) {
/*     */       try {
/*  97 */         URL imageURL = URLMaker.getURL(imageFilenames[type]);
/*  98 */         this.image = ImageIO.read(imageURL);
/*     */       }
/*     */       catch (Exception e) {
/* 101 */         e.printStackTrace();
/*     */       }
/* 103 */       setIcon(new ImageIcon(this.image));
/* 104 */       setBounds(new Rectangle(0, 0, this.image.getWidth() + getTextSize(), this.image.getHeight()));
/*     */     }
/*     */     else {
/* 107 */       setBounds(new Rectangle(0, 0, getTextSize(), 20));
/*     */     }
/*     */ 
/* 110 */     if (type == 3) {
/* 111 */       updatePinOffset();
/*     */     }
/*     */ 
/* 114 */     this.state = (type == 6);
/* 115 */     setOpaque(false);
/*     */   }
/*     */ 
/*     */   private int getTextSize()
/*     */   {
/* 121 */     int additionalWidth = 0;
/* 122 */     String label = getText();
/* 123 */     if (label.length() > 0) {
/* 124 */       int stringWidth = getFontMetrics(getFont()).stringWidth(label);
/* 125 */       int iconTextGap = Workspace.getGridSize() - stringWidth % Workspace.getGridSize();
/* 126 */       setIconTextGap(iconTextGap);
/* 127 */       additionalWidth = stringWidth + iconTextGap;
/*     */     }
/* 129 */     return additionalWidth;
/*     */   }
/*     */ 
/*     */   private void updatePinOffset()
/*     */   {
/* 135 */     this.pinOffsets[3][1][0] = new Point(getWidth(), (int)this.pinOffsets[3][1][0].getY());
/*     */   }
/*     */ 
/*     */   private void recalculateSize()
/*     */   {
/* 141 */     if (this.image != null) {
/* 142 */       setSize(this.image.getWidth() + getTextSize(), this.image.getHeight());
/*     */     }
/*     */     else {
/* 145 */       setSize(getTextSize(), 20);
/*     */     }
/* 147 */     if (this.ctype == 3)
/* 148 */       updatePinOffset();
/*     */   }
/*     */ 
/*     */   public CircuitComponent(String label)
/*     */   {
/* 157 */     super(label);
/* 158 */     this.ctype = 7;
/* 159 */     setBounds(new Rectangle(0, 0, getTextSize(), 20));
/*     */   }
/*     */ 
/*     */   public static String getImageIcon(int componentType)
/*     */   {
/* 166 */     return imageFilenames[componentType];
/*     */   }
/*     */ 
/*     */   public static String getToolbarIcon(int componentType)
/*     */   {
/* 174 */     return menuIconFilenames[componentType];
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 180 */     return this.ctype;
/*     */   }
/*     */ 
/*     */   public Point[] getInputLocations()
/*     */   {
/* 186 */     Point[] inPositions = (Point[])this.pinOffsets[this.ctype][0].clone();
/* 187 */     for (int i = 0; i < inPositions.length; i++) {
/* 188 */       Point inPosition = (Point)inPositions[i].clone();
/* 189 */       inPosition.setLocation(inPosition.getX() + getX(), inPositions[i].getY() + getY());
/* 190 */       inPositions[i] = inPosition;
/*     */     }
/* 192 */     return inPositions;
/*     */   }
/*     */ 
/*     */   public Point getOutputLocation()
/*     */   {
/*     */     Point outPosition;
/* 199 */     if (this.pinOffsets[this.ctype][1].length > 0) {
/* 200 */       outPosition = (Point)this.pinOffsets[this.ctype][1][0].clone();
/* 201 */       outPosition.setLocation(outPosition.getX() + getX(), outPosition.getY() + getY());
/*     */     } else {
/* 203 */       outPosition = null;
/* 204 */     }return outPosition;
/*     */   }
/*     */ 
/*     */   public void connectInput(CircuitComponent inComponent)
/*     */   {
/* 212 */     this.inList.add(inComponent);
/*     */   }
/*     */ 
/*     */   public void disconnectInput(CircuitComponent inComponent)
/*     */   {
/* 219 */     for (int i = 0; i < this.inList.size(); i++)
/* 220 */       if (this.inList.get(i).equals(inComponent))
/* 221 */         this.inList.remove(i);
/*     */   }
/*     */ 
/*     */   public ArrayList getConnectedInputs()
/*     */   {
/* 228 */     return this.inList;
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean selected)
/*     */   {
/* 235 */     if (selected) {
/* 236 */       setBorder(BorderFactory.createEtchedBorder());
/*     */     }
/*     */     else {
/* 239 */       setBorder(null);
/*     */     }
/* 241 */     repaint();
/*     */   }
/*     */ 
/*     */   public void updateText()
/*     */   {
/* 246 */     String newText = JOptionPane.showInputDialog(null, "Insert new text", "Update Label", -1);
/*     */ 
/* 248 */     if (newText != null) {
/* 249 */       if ((this.ctype == 3) || (this.ctype == 4))
/*     */       {
/* 251 */         if (newText.length() > 12) newText = newText.substring(0, 12);
/*     */       }
/*     */ 
/* 254 */       setText(newText);
/*     */ 
/* 256 */       recalculateSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean simulate()
/*     */   {
/* 265 */     boolean returnValue = true;
/*     */ 
/* 267 */     switch (this.ctype) {
/*     */     case 0:
/* 269 */       returnValue = simulateAndGate();
/* 270 */       break;
/*     */     case 1:
/* 272 */       returnValue = simulateOrGate();
/* 273 */       break;
/*     */     case 2:
/* 275 */       returnValue = simulateNotGate();
/* 276 */       break;
/*     */     case 3:
/* 278 */       returnValue = simulateInputPin();
/* 279 */       break;
/*     */     case 4:
/* 281 */       returnValue = simulateOutputPin();
/* 282 */       break;
/*     */     case 5:
/* 284 */       returnValue = simulateGND();
/* 285 */       break;
/*     */     case 6:
/* 287 */       returnValue = simulateVCC();
/*     */     }
/*     */ 
/* 290 */     return returnValue;
/*     */   }
/*     */ 
/*     */   public void setState(boolean digitalValue)
/*     */   {
/* 300 */     this.state = digitalValue;
/*     */   }
/*     */ 
/*     */   private boolean simulateInputPin() {
/* 304 */     return this.state;
/*     */   }
/*     */ 
/*     */   private boolean simulateOutputPin()
/*     */   {
/*     */     boolean returnValue;
/* 309 */     if (this.inList.size() < 1) {
/* 310 */       returnValue = true;
/*     */     }
/*     */     else
/*     */     {
/* 312 */       if (this.inList.size() == 1) {
/* 313 */         returnValue = ((CircuitComponent)this.inList.get(0)).simulate();
/*     */       }
/*     */       else {
/* 316 */         System.out.println("Warning: Multiple wires connected to the output pin.\nOnly simulating first connection. Do not connect outputs");
/*     */ 
/* 318 */         returnValue = ((CircuitComponent)this.inList.get(0)).simulate();
/*     */       }
/*     */     }
/* 320 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private boolean simulateAndGate()
/*     */   {
/*     */     boolean returnValue;
/* 325 */     if (this.inList.size() < 1) {
/* 326 */       returnValue = true;
/*     */     }
/*     */     else
/*     */     {
/* 328 */       if (this.inList.size() == 1) {
/* 329 */         returnValue = true & ((CircuitComponent)this.inList.get(0)).simulate();
/*     */       }
/*     */       else
/*     */       {
/* 331 */         if (this.inList.size() == 2) {
/* 332 */           returnValue = ((CircuitComponent)this.inList.get(0)).simulate() & ((CircuitComponent)this.inList.get(1)).simulate();
/*     */         }
/*     */         else {
/* 335 */           System.out.println("Warning: Too many input connections found to AND gate.\nOnly simulating first two connections. Do not connect outputs");
/*     */ 
/* 337 */           returnValue = ((CircuitComponent)this.inList.get(0)).simulate() & ((CircuitComponent)this.inList.get(1)).simulate();
/*     */         }
/*     */       }
/*     */     }
/* 339 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private boolean simulateOrGate()
/*     */   {
/*     */     boolean returnValue;
/* 344 */     if (this.inList.size() < 2) {
/* 345 */       returnValue = true;
/*     */     }
/*     */     else
/*     */     {
/* 347 */       if (this.inList.size() == 2) {
/* 348 */         returnValue = ((CircuitComponent)this.inList.get(0)).simulate() | ((CircuitComponent)this.inList.get(1)).simulate();
/*     */       }
/*     */       else {
/* 351 */         System.out.println("Warning: Too many input connections found to OR gate.\nOnly simulating first two connections. Do not connect outputs");
/*     */ 
/* 353 */         returnValue = ((CircuitComponent)this.inList.get(0)).simulate() | ((CircuitComponent)this.inList.get(1)).simulate();
/*     */       }
/*     */     }
/* 355 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private boolean simulateNotGate()
/*     */   {
/*     */     boolean returnValue;
/* 360 */     if (this.inList.size() < 1) {
/* 361 */       returnValue = false;
/*     */     }
/*     */     else
/*     */     {
/* 363 */       if (this.inList.size() == 1) {
/* 364 */         returnValue = !((CircuitComponent)this.inList.get(0)).simulate();
/*     */       }
/*     */       else {
/* 367 */         System.out.println("Warning: Multiple wires connected to the NOT gate.\nOnly simulating first connection. Do not connect outputs");
/*     */ 
/* 369 */         returnValue = !((CircuitComponent)this.inList.get(0)).simulate();
/*     */       }
/*     */     }
/* 371 */     return returnValue;
/*     */   }
/*     */ 
/*     */   private boolean simulateGND() {
/* 375 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean simulateVCC() {
/* 379 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.CircuitComponent
 * JD-Core Version:    0.6.2
 */