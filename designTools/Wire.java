/*     */ package designTools;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class Wire
/*     */ {
/*  47 */   private ArrayList pointList = new ArrayList();
/*     */   private Point sketchPoint;
/*  49 */   private CircuitComponent sourceComponent = null;
/*  50 */   private CircuitComponent targetComponent = null;
/*     */   private Workspace workSpace;
/*  52 */   private ArrayList nodeList = new ArrayList();
/*     */   private Color wireColor;
/*     */   private boolean isSelected;
/*  55 */   private ConnectedWires connectedWires = new ConnectedWires();
/*     */ 
/*     */   public Wire(Point startPoint, Color colour, Workspace workspace)
/*     */   {
/*  65 */     this.workSpace = workspace;
/*  66 */     this.wireColor = colour;
/*     */ 
/*  68 */     this.pointList.add(startPoint);
/*  69 */     this.sketchPoint = startPoint;
/*  70 */     this.connectedWires.add(this);
/*     */   }
/*     */ 
/*     */   public void connect(Point point)
/*     */   {
/*  80 */     Component comp = this.workSpace.getComponentAt((int)point.getX() - Workspace.getGridSize(), (int)point.getY());
/*  81 */     if (comp.getClass().equals(new CircuitComponent(0).getClass())) {
/*  82 */       CircuitComponent circuitComp = (CircuitComponent)comp;
/*  83 */       if (point.equals(circuitComp.getOutputLocation()))
/*     */       {
/*  85 */         if (this.connectedWires.getSourceComponent() != null)
/*     */         {
/*  87 */           this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
/*  88 */           showErrorMessage();
/*     */         }
/*     */         else {
/*  91 */           this.nodeList.add(point);
/*  92 */           this.sourceComponent = circuitComp;
/*  93 */           this.connectedWires.setSourceComponent(circuitComp);
/*     */ 
/*  95 */           for (int i = 0; i < this.connectedWires.size(); i++) {
/*  96 */             if (((Wire)this.connectedWires.get(i)).targetComponent != null) {
/*  97 */               ((Wire)this.connectedWires.get(i)).targetComponent.connectInput(this.sourceComponent);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     comp = this.workSpace.getComponentAt(point);
/* 106 */     if (comp.getClass().equals(new CircuitComponent(0).getClass())) {
/* 107 */       CircuitComponent circuitComp = (CircuitComponent)comp;
/* 108 */       for (int i = 0; i < circuitComp.getInputLocations().length; i++) {
/* 109 */         if (point.equals(circuitComp.getInputLocations()[i]))
/*     */         {
/* 111 */           boolean alreadyConnected = false;
/* 112 */           for (int j = 0; j < this.workSpace.getWires().size() - 1; j++) {
/* 113 */             if (((Wire)this.workSpace.getWires().get(j)).isWirePoint(point)) alreadyConnected = true;
/*     */           }
/* 115 */           if (alreadyConnected)
/*     */           {
/* 117 */             this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
/* 118 */             showErrorMessage();
/*     */           }
/*     */           else {
/* 121 */             this.nodeList.add(point);
/* 122 */             this.targetComponent = circuitComp;
/*     */ 
/* 125 */             if (this.sourceComponent != null) {
/* 126 */               this.targetComponent.connectInput(this.sourceComponent);
/*     */             }
/* 129 */             else if (this.connectedWires.getSourceComponent() != null) {
/* 130 */               this.targetComponent.connectInput(this.connectedWires.getSourceComponent());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 147 */       ArrayList wireList = this.workSpace.getWires();
/*     */ 
/* 150 */       for (int i = 0; i < wireList.size() - 1; i++) {
/* 151 */         Wire wire = (Wire)wireList.get(i);
/* 152 */         if (wire.isWirePoint(point))
/*     */         {
/* 154 */           if ((wire.getConnectedWires().getSourceComponent() != null) && (this.connectedWires.getSourceComponent() != null) && (!wire.getConnectedWires().getSourceComponent().equals(this.connectedWires.getSourceComponent())))
/*     */           {
/* 159 */             this.workSpace.getWires().remove(this.workSpace.getWires().size() - 1);
/* 160 */             showErrorMessage();
/*     */           }
/*     */           else
/*     */           {
/* 164 */             for (int j = 0; j < this.connectedWires.size(); j++) {
/* 165 */               wire.getConnectedWires().addExclusive((Wire)this.connectedWires.get(j));
/*     */             }
/*     */ 
/* 168 */             this.connectedWires = wire.getConnectedWires();
/*     */ 
/* 171 */             if (this.connectedWires.getSourceComponent() == null) this.connectedWires.setSourceComponent(this.sourceComponent);
/*     */ 
/* 174 */             if (this.connectedWires.getSourceComponent() != null) {
/* 175 */               for (int j = 0; j < this.connectedWires.size(); j++) {
/* 176 */                 Wire w = (Wire)this.connectedWires.get(j);
/* 177 */                 if (w.targetComponent != null)
/*     */                 {
/* 179 */                   boolean needToUpdate = true;
/* 180 */                   for (int k = 0; k < w.targetComponent.getConnectedInputs().size(); k++) {
/* 181 */                     if (this.connectedWires.getSourceComponent().equals(w.targetComponent.getConnectedInputs().get(k))) {
/* 182 */                       needToUpdate = false;
/*     */                     }
/*     */                   }
/* 185 */                   if (needToUpdate) w.targetComponent.connectInput(this.connectedWires.getSourceComponent());
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 190 */             this.nodeList.add(point);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showErrorMessage()
/*     */   {
/* 200 */     this.workSpace.getStatusBar().setText(" WARNING:- Wire removed. That wire potentially directly connected outputs");
/*     */   }
/*     */ 
/*     */   public ConnectedWires getConnectedWires()
/*     */   {
/* 206 */     return this.connectedWires;
/*     */   }
/*     */ 
/*     */   public void lineTo(Point bendPoint)
/*     */   {
/* 214 */     Point newPoint = toHorizontalOrVertical(bendPoint);
/* 215 */     this.pointList.add(newPoint);
/*     */   }
/*     */ 
/*     */   public void sketchLineTo(Point mousePoint)
/*     */   {
/* 225 */     this.sketchPoint = toHorizontalOrVertical(mousePoint);
/*     */   }
/*     */ 
/*     */   private Point toHorizontalOrVertical(Point mousePoint)
/*     */   {
/* 236 */     Point previousPoint = (Point)this.pointList.get(this.pointList.size() - 1);
/*     */ 
/* 238 */     double xlength = mousePoint.getX() - previousPoint.getX();
/* 239 */     double ylength = mousePoint.getY() - previousPoint.getY();
/* 240 */     if (xlength < 0.0D) xlength *= -1.0D;
/* 241 */     if (ylength < 0.0D) ylength *= -1.0D;
/*     */     Point returnPoint;
/* 243 */     if (xlength >= ylength)
/*     */     {
/* 245 */       returnPoint = new Point((int)mousePoint.getX(), (int)previousPoint.getY());
/*     */     }
/*     */     else
/*     */     {
/* 249 */       returnPoint = new Point((int)previousPoint.getX(), (int)mousePoint.getY());
/*     */     }
/* 251 */     return returnPoint;
/*     */   }
/*     */ 
/*     */   public void removePoint(Point bendPoint)
/*     */   {
/* 259 */     this.pointList.remove(bendPoint);
/*     */   }
/*     */ 
/*     */   public void removeLastPoint()
/*     */   {
/* 265 */     if (this.pointList.size() > 1) {
/* 266 */       this.pointList.remove(this.pointList.size() - 1);
/*     */     }
/* 268 */     this.sketchPoint = ((Point)this.pointList.get(this.pointList.size() - 1));
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean selected)
/*     */   {
/* 275 */     this.isSelected = selected;
/*     */   }
/*     */ 
/*     */   public int getPointCount()
/*     */   {
/* 281 */     return this.pointList.size();
/*     */   }
/*     */ 
/*     */   public boolean isWirePoint(Point point)
/*     */   {
/* 290 */     boolean isOnWire = false;
/*     */ 
/* 293 */     for (int i = 0; i < this.pointList.size() - 1; i++) {
/* 294 */       Point currentPoint = (Point)this.pointList.get(i);
/* 295 */       Point nextPoint = (Point)this.pointList.get(i + 1);
/*     */ 
/* 297 */       if (currentPoint.getX() == point.getX()) {
/* 298 */         if ((nextPoint.getY() >= currentPoint.getY()) && (nextPoint.getY() >= point.getY()) && (point.getY() >= currentPoint.getY()))
/*     */         {
/* 301 */           isOnWire = true;
/*     */         }
/* 303 */         else if ((currentPoint.getY() > nextPoint.getY()) && (currentPoint.getY() >= point.getY()) && (point.getY() >= nextPoint.getY()))
/*     */         {
/* 306 */           isOnWire = true;
/*     */         }
/*     */ 
/*     */       }
/* 310 */       else if (currentPoint.getY() == point.getY()) {
/* 311 */         if ((nextPoint.getX() >= currentPoint.getX()) && (nextPoint.getX() >= point.getX()) && (point.getX() >= currentPoint.getX()))
/*     */         {
/* 314 */           isOnWire = true;
/*     */         }
/* 316 */         else if ((currentPoint.getX() > nextPoint.getX()) && (currentPoint.getX() >= point.getX()) && (point.getX() >= nextPoint.getX()))
/*     */         {
/* 319 */           isOnWire = true;
/*     */         }
/*     */       }
/*     */     }
/* 323 */     return isOnWire;
/*     */   }
/*     */ 
/*     */   public void draw(Graphics2D g)
/*     */   {
/* 332 */     Stroke previousStroke = g.getStroke();
/* 333 */     Color previousColor = g.getColor();
/*     */ 
/* 335 */     if (this.isSelected) g.setStroke(new BasicStroke(4.0F)); else
/* 336 */       g.setStroke(new BasicStroke(2.0F));
/* 337 */     g.setColor(this.wireColor);
/* 338 */     GeneralPath path = new GeneralPath();
/* 339 */     path.moveTo((float)((Point)this.pointList.get(0)).getX(), (float)((Point)this.pointList.get(0)).getY());
/* 340 */     for (int i = 1; i < this.pointList.size(); i++) {
/* 341 */       path.lineTo((float)((Point)this.pointList.get(i)).getX(), (float)((Point)this.pointList.get(i)).getY());
/*     */     }
/* 343 */     path.lineTo((float)this.sketchPoint.getX(), (float)this.sketchPoint.getY());
/* 344 */     g.draw(path);
/*     */ 
/* 346 */     g.setColor(previousColor);
/*     */ 
/* 348 */     g.setStroke(new BasicStroke(6.0F));
/* 349 */     for (int i = 0; i < this.nodeList.size(); i++) {
/* 350 */       Point p = (Point)this.nodeList.get(i);
/* 351 */       g.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
/*     */     }
/*     */ 
/* 354 */     g.setStroke(previousStroke);
/*     */   }
/*     */ 
/*     */   public void removeWire()
/*     */   {
/* 361 */     for (int i = 0; i < this.connectedWires.size(); i++)
/*     */     {
/* 363 */       if ((this.connectedWires.getSourceComponent() != null) && (((Wire)this.connectedWires.get(i)).targetComponent != null)) {
/* 364 */         ((Wire)this.connectedWires.get(i)).targetComponent.disconnectInput(this.connectedWires.getSourceComponent());
/*     */       }
/*     */ 
/* 367 */       this.workSpace.getWires().remove(this.connectedWires.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean disconnect(CircuitComponent comp)
/*     */   {
/* 377 */     boolean returnValue = false;
/*     */ 
/* 379 */     for (int i = 0; i < comp.getInputLocations().length; i++) {
/* 380 */       for (int j = 0; j < this.pointList.size(); j++) {
/* 381 */         if (comp.getInputLocations()[i].equals(this.pointList.get(j)))
/*     */         {
/* 383 */           returnValue = true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 388 */     for (int j = 0; j < this.pointList.size(); j++) {
/* 389 */       if ((comp.getOutputLocation() != null) && (comp.getOutputLocation().equals(this.pointList.get(j)))) {
/* 390 */         returnValue = true;
/* 391 */         if (this.targetComponent != null) this.targetComponent.disconnectInput(comp);
/*     */       }
/*     */     }
/* 394 */     return returnValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.Wire
 * JD-Core Version:    0.6.2
 */