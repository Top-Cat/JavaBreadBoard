/*    */ package jBreadBoard;
/*    */ 
/*    */ public class Trace
/*    */ {
/*    */   private Circuit circuit;
/* 29 */   private long time = -1L;
/* 30 */   private StringBuffer trace = new StringBuffer("Time");
/* 31 */   public static boolean duplicate = false;
/* 32 */   private boolean currentduplicate = duplicate;
/*    */ 
/* 34 */   private DeviceSet probeset = new DeviceSet();
/*    */ 
/*    */   public void addProbe(Probe p)
/*    */   {
/* 39 */     this.probeset.add(p);
/*    */   }
/*    */ 
/*    */   public void removeProbe(Probe p) {
/* 43 */     this.probeset.remove(p);
/*    */   }
/*    */ 
/*    */   public String getTrace()
/*    */   {
/* 49 */     return this.trace.toString();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 55 */     return this.trace.toString();
/*    */   }
/*    */ 
/*    */   public Trace(Circuit c)
/*    */   {
/* 61 */     if (c != null) this.circuit = c;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/* 68 */     DeviceList probelist = this.probeset.toList();
/* 69 */     this.trace = new StringBuffer("Time");
/*    */ 
/* 71 */     while (probelist != null) {
/* 72 */       Probe probe = (Probe)probelist.device;
/* 73 */       this.trace.append("," + probe.getLabel());
/* 74 */       probelist = probelist.next;
/*    */     }
/*    */ 
/* 77 */     this.time = -1L;
/* 78 */     this.currentduplicate = duplicate;
/*    */   }
/*    */ 
/*    */   public void simulate()
/*    */   {
/* 86 */     if (this.circuit.getSimTime() != this.time) {
/* 87 */       DeviceList probelist = this.probeset.toList();
/*    */ 
/* 89 */       this.time = this.circuit.getSimTime();
/* 90 */       this.trace.append(",\n" + this.time);
/*    */ 
/* 92 */       if (this.currentduplicate) {
/* 93 */         while (probelist != null) {
/* 94 */           Probe probe = (Probe)probelist.device;
/* 95 */           probe.simulate();
/* 96 */           this.trace.append("," + probe.getOldValue());
/* 97 */           probelist = probelist.next;
/*    */         }
/* 99 */         this.trace.append(",\n" + this.time);
/* 100 */         probelist = this.probeset.toList();
/*    */       }
/*    */ 
/* 103 */       while (probelist != null) {
/* 104 */         Probe probe = (Probe)probelist.device;
/* 105 */         probe.simulate();
/* 106 */         this.trace.append("," + probe.getValue());
/* 107 */         probelist = probelist.next;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.Trace
 * JD-Core Version:    0.6.2
 */