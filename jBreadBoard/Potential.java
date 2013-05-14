/*     */ package jBreadBoard;
/*     */ 
/*     */ public class Potential
/*     */ {
/*     */   private Node node1;
/*     */   private Node node2;
/*  26 */   private NodeType type = new NodeType();
/*     */   private Potential parent1;
/*     */   private Potential parent2;
/*     */   private Potential child;
/*  33 */   private String value = "NC";
/*  34 */   private long modifiedtime = -1L;
/*     */ 
/*     */   public Potential(Node n1, Node n2)
/*     */   {
/*  41 */     this.node1 = n1;
/*  42 */     this.node2 = n2;
/*  43 */     update();
/*     */   }
/*     */ 
/*     */   public Node getNode1()
/*     */   {
/*  50 */     return this.node1;
/*     */   }
/*     */ 
/*     */   public Node getNode2()
/*     */   {
/*  57 */     return this.node2;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  67 */     if (this.parent1 != null) this.parent1.child = null;
/*  68 */     if (this.parent2 != null) this.parent2.child = null;
/*     */ 
/*  70 */     if (this.node1 != null) this.parent1 = this.node1.getPotential();
/*  71 */     if (this.node2 != null) this.parent2 = this.node2.getPotential();
/*     */ 
/*  73 */     if (this.parent1 == this) this.parent1 = null;
/*  74 */     if (this.parent2 == this) this.parent2 = null;
/*     */ 
/*  76 */     if (this.parent1 != null) this.parent1.child = this;
/*  77 */     if (this.parent2 != null) this.parent2.child = this;
/*     */ 
/*  80 */     Potential oldchild = this.child;
/*  81 */     this.child = null;
/*     */ 
/*  83 */     setType();
/*     */ 
/*  85 */     setValue("NC", this.modifiedtime);
/*     */ 
/*  88 */     if (oldchild != null) oldchild.update();
/*     */   }
/*     */ 
/*     */   public void delete()
/*     */   {
/*  96 */     this.parent1.child = null;
/*  97 */     this.parent2.child = null;
/*  98 */     if (this.child != null)
/*  99 */       this.child.update();
/*     */   }
/*     */ 
/*     */   public DeviceSet getDevicesWithInputs(DeviceSet initial)
/*     */   {
/* 109 */     if (initial == null) initial = new DeviceSet();
/*     */ 
/* 111 */     if ((this.parent1 == null) && (this.parent2 == null)) {
/* 112 */       if ((this.node1 != null) && (
/* 113 */         (getType().equals("IN")) || (getType().equals("IO")) || (getType().equals("CLO")))) {
/* 114 */         initial.add(this.node1.getDevice());
/*     */       }
/*     */ 
/* 117 */       if ((this.node2 != null) && (
/* 118 */         (getType().equals("IN")) || (getType().equals("IO")) || (getType().equals("CLO"))))
/* 119 */         initial.add(this.node2.getDevice());
/*     */     }
/*     */     else {
/* 122 */       if (this.parent1 != null) initial = this.parent1.getDevicesWithInputs(initial);
/* 123 */       if (this.parent2 != null) initial = this.parent2.getDevicesWithInputs(initial);
/*     */     }
/* 125 */     return initial;
/*     */   }
/*     */ 
/*     */   public Potential getPotential()
/*     */   {
/* 132 */     if (this.child == null) return this;
/* 133 */     return this.child.getPotential();
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 140 */     return this.type.getType();
/*     */   }
/*     */ 
/*     */   public void setType()
/*     */   {
/* 147 */     String type1 = ""; String type2 = "";
/*     */ 
/* 149 */     if ((this.parent1 != null) || (this.parent2 != null)) {
/* 150 */       if (this.parent1 != null) type1 = this.parent1.getType();
/* 151 */       if (this.parent2 != null) type2 = this.parent2.getType();
/*     */ 
/* 154 */       if ((type1.equals("OUT")) || (type2.equals("OUT"))) this.type.setType("OUT");
/* 155 */       else if ((type1.equals("CLO")) || (type2.equals("CLO"))) this.type.setType("CLO");
/* 156 */       else if ((type1.equals("OCO")) || (type2.equals("OCO"))) this.type.setType("OCO");
/* 157 */       else if ((type1.equals("IO")) || (type2.equals("IO"))) this.type.setType("IO");
/* 158 */       else if ((type1.equals("IN")) || (type2.equals("IN"))) this.type.setType("IN"); else
/* 159 */         this.type.setType("NC");
/*     */     } else {
/* 161 */       this.type.setType("NC");
/*     */     }
/*     */ 
/* 164 */     if (this.child != null) this.child.setType();
/*     */   }
/*     */ 
/*     */   public void setType(String s)
/*     */   {
/* 172 */     if ((this.parent1 == null) && (this.parent2 == null))
/* 173 */       this.type.setType(s);
/* 174 */     else setType();
/*     */ 
/* 176 */     if (this.child != null) this.child.setType();
/*     */   }
/*     */ 
/*     */   public String toTTL()
/*     */   {
/* 183 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String v, long time)
/*     */   {
/* 197 */     if ((!v.equals("HIGH")) && (!v.equals("LOW")) && (!v.equals("NC"))) return;
/*     */ 
/* 199 */     this.modifiedtime = time;
/*     */ 
/* 202 */     if ((this.parent1 != null) && (this.parent2 != null)) {
/* 203 */       String p1type = this.parent1.getType();
/* 204 */       String p2type = this.parent2.getType();
/*     */ 
/* 206 */       if (this.parent1.modifiedtime > this.modifiedtime) this.modifiedtime = this.parent1.modifiedtime;
/* 207 */       if (this.parent2.modifiedtime > this.modifiedtime) this.modifiedtime = this.parent2.modifiedtime;
/*     */ 
/* 210 */       if ((getType().equals("NC")) || (getType().equals("IN"))) this.value = "NC";
/* 211 */       else if ((this.parent1.toTTL().equals("NC")) && (this.parent2.toTTL().equals("NC"))) {
/* 212 */         this.value = "NC";
/*     */       }
/* 215 */       else if (getType().equals("OCO")) {
/* 216 */         if (this.parent1.toTTL().equals("HIGH")) this.value = "HIGH";
/* 217 */         else if (this.parent2.toTTL().equals("HIGH")) this.value = "HIGH"; else {
/* 218 */           this.value = "LOW";
/*     */         }
/*     */ 
/*     */       }
/* 222 */       else if (getType().equals("IO")) {
/* 223 */         if (this.parent1.modifiedtime > this.parent2.modifiedtime) this.value = this.parent1.toTTL(); else {
/* 224 */           this.value = this.parent2.toTTL();
/*     */         }
/*     */ 
/*     */       }
/* 230 */       else if ((getType().equals("OUT")) || (getType().equals("CLO")))
/* 231 */         if ((p1type.equals("OUT")) || (p1type.equals("CLO"))) this.value = this.parent1.toTTL(); else
/* 232 */           this.value = this.parent2.toTTL();
/*     */     }
/* 234 */     else if (this.parent1 != null) {
/* 235 */       this.value = this.parent1.toTTL();
/* 236 */       if (this.parent1.modifiedtime > this.modifiedtime) this.modifiedtime = this.parent1.modifiedtime; 
/*     */     }
/* 237 */     else if (this.parent2 != null) {
/* 238 */       this.value = this.parent2.toTTL();
/* 239 */       if (this.parent2.modifiedtime > this.modifiedtime) this.modifiedtime = this.parent2.modifiedtime; 
/*     */     } else { this.value = v; }
/*     */ 
/*     */ 
/* 243 */     if (this.child != null) this.child.setValue(this.value, this.modifiedtime);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Potential
 * JD-Core Version:    0.6.2
 */