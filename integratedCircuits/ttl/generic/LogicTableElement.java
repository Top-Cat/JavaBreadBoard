/*     */ package integratedCircuits.ttl.generic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class LogicTableElement
/*     */ {
/*     */   protected List<Boolean> dataBits;
/*  19 */   protected int delay = 0;
/*  20 */   protected int size = 0;
/*     */ 
/*     */   public LogicTableElement(int size, int data)
/*     */   {
/*  27 */     initialise(size, data, 1);
/*     */   }
/*     */ 
/*     */   public LogicTableElement(int size, int data, int delay) {
/*  31 */     initialise(size, data, delay);
/*     */   }
/*     */ 
/*     */   protected void initialise(int size, int data, int delay)
/*     */   {
/*  39 */     this.size = size;
/*  40 */     this.delay = delay;
/*  41 */     this.dataBits = new ArrayList(size);
/*     */ 
/*  43 */     for (int i = 0; i < size; i++) {
/*  44 */       this.dataBits.add(Boolean.FALSE);
/*     */     }
/*     */ 
/*  47 */     if (data > (int)Math.pow(2.0D, size) - 1) {
/*  48 */       data = (int)Math.pow(2.0D, size) - 1;
/*     */     }
/*     */ 
/*  52 */     int mask = 1;
/*  53 */     for (int i = 0; i < size; i++) {
/*  54 */       if ((data & mask) != 0)
/*  55 */         this.dataBits.set(i, Boolean.TRUE);
/*     */       else
/*  57 */         this.dataBits.set(i, Boolean.FALSE);
/*  58 */       mask *= 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBit(int offset, Boolean bit)
/*     */   {
/*  64 */     this.dataBits.set(offset, bit);
/*     */   }
/*     */ 
/*     */   public void setData(int data) {
/*  68 */     if (data > (int)Math.pow(2.0D, this.size) - 1) {
/*  69 */       data = (int)Math.pow(2.0D, this.size) - 1;
/*     */     }
/*  71 */     int mask = 1;
/*  72 */     for (int i = 0; i < this.dataBits.size(); i++) {
/*  73 */       if ((data & mask) != 0)
/*  74 */         this.dataBits.set(i, Boolean.TRUE);
/*     */       else
/*  76 */         this.dataBits.set(i, Boolean.FALSE);
/*  77 */       mask *= 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDelay(int delay) {
/*  82 */     this.delay = delay;
/*     */   }
/*     */ 
/*     */   public void clearData() {
/*  86 */     for (int i = 0; i < this.dataBits.size(); i++)
/*  87 */       this.dataBits.set(i, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   public Boolean getBit(int offset)
/*     */   {
/*  92 */     return (Boolean)this.dataBits.get(offset);
/*     */   }
/*     */ 
/*     */   public List<Boolean> getData() {
/*  96 */     return this.dataBits;
/*     */   }
/*     */ 
/*     */   public int getDelay() {
/* 100 */     return this.delay;
/*     */   }
/*     */ 
/*     */   public int getSize() {
/* 104 */     return this.size;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 108 */     String message = "";
/* 109 */     for (int i = 0; i < this.dataBits.size(); i++) {
/* 110 */       message.concat("    - " + this.dataBits.get(i) + "\n");
/*     */     }
/*     */ 
/* 113 */     return message;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.generic.LogicTableElement
 * JD-Core Version:    0.6.2
 */