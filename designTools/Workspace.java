/*     */ package designTools;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ 
/*     */ public class Workspace extends JPanel
/*     */ {
/*  21 */   private CircuitComponent movingComponent = null;
/*     */   private static final int GRID_SIZE = 10;
/*     */   private JScrollPane parentPane;
/*  24 */   private boolean wiringMode = false;
/*  25 */   private boolean drawingWire = false;
/*  26 */   private ArrayList wireList = new ArrayList();
/*  27 */   private ArrayList outputPinList = new ArrayList();
/*  28 */   private ArrayList inputPinList = new ArrayList();
/*     */   private CircuitComponent selectedCircuitComponent;
/*     */   private JLabel lblStatus;
/*  31 */   private Color wireColor = Color.BLACK;
/*     */   private CircuitComponent draggedComponent;
/*  33 */   private int selectedWireIndex = -1;
/*     */ 
/*     */   public Workspace(JScrollPane container, JLabel statusBar)
/*     */   {
/*  43 */     this.lblStatus = statusBar;
/*  44 */     this.parentPane = container;
/*  45 */     setLayout(null);
/*  46 */     setBackground(Color.white);
/*  47 */     addMouseListener(new Workspace_mouseAdapter(this));
/*  48 */     addMouseMotionListener(new Workspace_mouseMotionAdapter(this));
/*     */   }
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/*  56 */     super.paintComponent(g);
/*     */ 
/*  58 */     Graphics2D g2 = (Graphics2D)g;
/*  59 */     for (int i = 0; i <= getWidth(); i += 10) {
/*  60 */       for (int j = 0; j <= getHeight(); j += 10) {
/*  61 */         if (i + j > 0) {
/*  62 */           g2.drawLine(i, j, i + 1, j);
/*     */         }
/*     */       }
/*     */     }
/*  66 */     for (int i = 0; i < this.wireList.size(); i++)
/*  67 */       ((Wire)this.wireList.get(i)).draw(g2);
/*     */   }
/*     */ 
/*     */   public void addCircuitComponent(int type)
/*     */   {
/*  77 */     unselectComponent();
/*  78 */     setWiringMode(false);
/*  79 */     this.movingComponent = new CircuitComponent(type);
/*  80 */     add(this.movingComponent, null);
/*  81 */     this.lblStatus.setText("Click on the workspace to place the component");
/*     */   }
/*     */ 
/*     */   public void createLabel()
/*     */   {
/*  87 */     unselectComponent();
/*     */ 
/*  89 */     setWiringMode(false);
/*  90 */     String text = JOptionPane.showInputDialog(null, "Insert Label Text", "Label Text", -1);
/*  91 */     if (text != null) {
/*  92 */       this.movingComponent = new CircuitComponent(text);
/*  93 */       add(this.movingComponent, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Point snapToGrid(Point mousePosition)
/*     */   {
/* 104 */     double mouseX = mousePosition.getX();
/* 105 */     double mouseY = mousePosition.getY();
/* 106 */     double offset = mouseX % 10.0D;
/* 107 */     if (offset <= 5.0D) mouseX -= offset; else
/* 108 */       mouseX += 10.0D - offset;
/* 109 */     offset = mouseY % 10.0D;
/* 110 */     if (offset <= 5.0D) mouseY -= offset; else
/* 111 */       mouseY += 10.0D - offset;
/* 112 */     return new Point((int)mouseX, (int)mouseY);
/*     */   }
/*     */ 
/*     */   void workspace_mouseMoved(MouseEvent e)
/*     */   {
/* 122 */     Point gridPos = snapToGrid(e.getPoint());
/*     */ 
/* 124 */     if (this.movingComponent != null) {
/* 125 */       this.movingComponent.setLocation(gridPos);
/* 126 */       repaint();
/*     */     }
/* 129 */     else if (this.drawingWire)
/*     */     {
/* 132 */       ((Wire)this.wireList.get(this.wireList.size() - 1)).sketchLineTo(gridPos);
/* 133 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void placeComponentAt(Point gridPos)
/*     */   {
/* 142 */     if (gridPos.getX() >= getWidth() - 100)
/*     */     {
/* 145 */       this.parentPane.getViewport().remove(this);
/* 146 */       setPreferredSize(new Dimension(getWidth() + 100, getHeight()));
/* 147 */       this.parentPane.getViewport().add(this, null);
/* 148 */       repaint();
/*     */     }
/* 150 */     if (gridPos.getY() >= getHeight() - 80) {
/* 151 */       this.parentPane.getViewport().remove(this);
/* 152 */       setPreferredSize(new Dimension(getWidth(), getHeight() + 100));
/* 153 */       this.parentPane.getViewport().add(this, null);
/* 154 */       repaint();
/*     */     }
/*     */ 
/* 157 */     if (this.movingComponent.getType() == 3) this.inputPinList.add(this.movingComponent);
/* 158 */     else if (this.movingComponent.getType() == 4) this.outputPinList.add(this.movingComponent);
/* 159 */     this.lblStatus.setText(" ");
/*     */   }
/*     */ 
/*     */   void workspace_mouseClicked(MouseEvent e)
/*     */   {
/* 170 */     Point gridPos = snapToGrid(e.getPoint());
/*     */ 
/* 172 */     if (e.getButton() == 1)
/*     */     {
/* 174 */       unselectComponent();
/*     */ 
/* 176 */       if (this.movingComponent != null) {
/* 177 */         placeComponentAt(gridPos);
/*     */       }
/* 180 */       else if (this.wiringMode) {
/* 181 */         showWiringModeText();
/* 182 */         if (this.drawingWire)
/*     */         {
/* 184 */           ((Wire)this.wireList.get(this.wireList.size() - 1)).lineTo(gridPos);
/* 185 */           if (e.getClickCount() > 1) {
/* 186 */             this.drawingWire = false;
/* 187 */             ((Wire)this.wireList.get(this.wireList.size() - 1)).connect(gridPos);
/*     */           }
/*     */         }
/*     */         else {
/* 191 */           this.drawingWire = true;
/* 192 */           this.wireList.add(new Wire(gridPos, this.wireColor, this));
/* 193 */           ((Wire)this.wireList.get(this.wireList.size() - 1)).connect(gridPos);
/*     */         }
/*     */ 
/*     */       }
/* 197 */       else if (getComponentAt(e.getPoint()).getClass().equals(new CircuitComponent(0).getClass())) {
/* 198 */         this.selectedCircuitComponent = ((CircuitComponent)getComponentAt(e.getPoint()));
/* 199 */         this.selectedCircuitComponent.setSelected(true);
/*     */       }
/*     */       else
/*     */       {
/* 203 */         for (int i = 0; i < this.wireList.size(); i++) {
/* 204 */           if (((Wire)this.wireList.get(i)).isWirePoint(gridPos)) {
/* 205 */             this.selectedWireIndex = i;
/* 206 */             ((Wire)this.wireList.get(i)).setSelected(true);
/* 207 */             i = this.wireList.size();
/*     */           }
/*     */         }
/*     */       }
/* 211 */       repaint();
/* 212 */       this.movingComponent = null;
/*     */     }
/* 215 */     else if (e.getButton() != 3);
/*     */   }
/*     */ 
/*     */   void workspace_mouseDragged(MouseEvent e)
/*     */   {
/* 227 */     if (this.draggedComponent != null) {
/* 228 */       disconnectComponent(this.draggedComponent);
/* 229 */       this.draggedComponent.setLocation(snapToGrid(e.getPoint()));
/* 230 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   void workspace_mousePressed(MouseEvent e)
/*     */   {
/* 238 */     if (!this.wiringMode)
/*     */     {
/* 240 */       Component comp = getComponentAt(e.getPoint());
/* 241 */       if (comp.getClass().equals(new CircuitComponent(0).getClass()))
/* 242 */         this.draggedComponent = ((CircuitComponent)comp);
/*     */     }
/*     */   }
/*     */ 
/*     */   void workspace_mouseReleased(MouseEvent e)
/*     */   {
/* 251 */     this.draggedComponent = null;
/*     */   }
/*     */ 
/*     */   public void unselectComponent()
/*     */   {
/* 257 */     if (this.selectedCircuitComponent != null) this.selectedCircuitComponent.setSelected(false);
/* 258 */     this.selectedCircuitComponent = null;
/* 259 */     if (this.selectedWireIndex != -1) ((Wire)this.wireList.get(this.selectedWireIndex)).setSelected(false);
/* 260 */     this.selectedWireIndex = -1;
/*     */   }
/*     */ 
/*     */   public void selectComponent(CircuitComponent comp) {
/* 264 */     this.selectedCircuitComponent = comp;
/* 265 */     comp.setSelected(true);
/*     */   }
/*     */ 
/*     */   private void showWiringModeText()
/*     */   {
/* 271 */     this.lblStatus.setText("Wiring Mode: Click to start wire; click to bend wire; double-click to end wire; click pointer icon to exit wiring mode");
/*     */   }
/*     */ 
/*     */   public void setWiringMode(boolean mode)
/*     */   {
/* 280 */     this.wiringMode = mode;
/* 281 */     this.drawingWire = false;
/* 282 */     unselectComponent();
/*     */ 
/* 284 */     if (mode) {
/* 285 */       showWiringModeText();
/*     */     }
/*     */     else
/* 288 */       this.lblStatus.setText(" ");
/*     */   }
/*     */ 
/*     */   public void exitPlacementMode()
/*     */   {
/* 297 */     if (this.movingComponent != null) {
/* 298 */       remove(this.movingComponent);
/*     */     }
/* 300 */     if (this.drawingWire) {
/* 301 */       this.wireList.remove(this.wireList.size() - 1);
/*     */     }
/* 303 */     this.movingComponent = null;
/* 304 */     setWiringMode(false);
/* 305 */     unselectComponent();
/* 306 */     repaint();
/*     */   }
/*     */ 
/*     */   public void disconnectComponent(CircuitComponent comp)
/*     */   {
/* 315 */     for (int i = this.wireList.size() - 1; i >= 0; i--)
/*     */     {
/* 318 */       if ((i < this.wireList.size()) && 
/* 319 */         (((Wire)this.wireList.get(i)).disconnect(comp))) {
/* 320 */         ((Wire)this.wireList.get(i)).removeWire();
/*     */       }
/*     */ 
/* 323 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getGridSize()
/*     */   {
/* 330 */     return 10;
/*     */   }
/*     */ 
/*     */   public ArrayList getOutputs()
/*     */   {
/* 335 */     return this.outputPinList;
/*     */   }
/*     */ 
/*     */   public ArrayList getInputs() {
/* 339 */     return this.inputPinList;
/*     */   }
/*     */ 
/*     */   public ArrayList getWires()
/*     */   {
/* 344 */     return this.wireList;
/*     */   }
/*     */ 
/*     */   public JLabel getStatusBar()
/*     */   {
/* 349 */     return this.lblStatus;
/*     */   }
/*     */ 
/*     */   public void deleteSelectedComponent()
/*     */   {
/* 354 */     if (this.selectedCircuitComponent != null) {
/* 355 */       disconnectComponent(this.selectedCircuitComponent);
/* 356 */       remove(this.selectedCircuitComponent);
/* 357 */       this.outputPinList.remove(this.selectedCircuitComponent);
/* 358 */       this.inputPinList.remove(this.selectedCircuitComponent);
/* 359 */       unselectComponent();
/* 360 */       repaint();
/*     */     }
/* 362 */     else if (this.selectedWireIndex > -1) {
/* 363 */       ((Wire)this.wireList.get(this.selectedWireIndex)).removeWire();
/* 364 */       this.selectedWireIndex = -1;
/* 365 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void moveSelectedComponent() {
/* 370 */     if (this.selectedCircuitComponent != null) {
/* 371 */       disconnectComponent(this.selectedCircuitComponent);
/* 372 */       remove(this.selectedCircuitComponent);
/* 373 */       this.movingComponent = this.selectedCircuitComponent;
/* 374 */       add(this.movingComponent, null);
/* 375 */       unselectComponent();
/* 376 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void renameSelectedComponent() {
/* 381 */     if (this.selectedCircuitComponent != null) {
/* 382 */       int componentType = this.selectedCircuitComponent.getType();
/* 383 */       if ((componentType == 3) || (componentType == 4) || (componentType == 7))
/*     */       {
/* 386 */         if (componentType == 3) disconnectComponent(this.selectedCircuitComponent);
/* 387 */         this.selectedCircuitComponent.updateText();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setWireColor(Color colour)
/*     */   {
/* 395 */     this.wireColor = colour;
/*     */   }
/*     */ 
/*     */   public void cancelWireSegment()
/*     */   {
/* 400 */     if (this.drawingWire) {
/* 401 */       Wire lastWire = (Wire)this.wireList.get(this.wireList.size() - 1);
/* 402 */       lastWire.removeLastPoint();
/* 403 */       if (lastWire.getPointCount() < 2) {
/* 404 */         this.wireList.remove(lastWire);
/* 405 */         this.drawingWire = false;
/*     */       }
/* 407 */       repaint();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.Workspace
 * JD-Core Version:    0.6.2
 */