/*     */ package jBreadBoard;
/*     */ 
/*     */ public class JBreadBoardEventSimulation
/*     */ {
/*     */   private Circuit circuit;
/*  29 */   private JBreadBoardEvent head = null;
/*  30 */   private long currentTime = 0L;
/*     */ 
/*     */   public JBreadBoardEventSimulation(Circuit c)
/*     */   {
/*  36 */     this.circuit = c;
/*     */   }
/*     */ 
/*     */   public long getCurrentTime()
/*     */   {
/*  43 */     return this.currentTime;
/*     */   }
/*     */ 
/*     */   private void queueEvent(JBreadBoardEvent e)
/*     */   {
/*  56 */     if (this.head != null)
/*     */     {
/*  58 */       while ((this.head != null) && (this.head.getNode() == e.getNode()) && (this.head.getTime() >= e.getTime())) {
/*  59 */         this.head = this.head.next;
/*     */       }
/*     */ 
/*  62 */       JBreadBoardEvent temp = this.head;
/*     */ 
/*  65 */       while (temp != null)
/*     */       {
/*  67 */         while ((temp.next != null) && (temp.next.getNode() == e.getNode()) && (temp.next.getTime() >= e.getTime()))
/*     */         {
/*  69 */           temp.next = temp.next.next;
/*     */         }
/*  71 */         temp = temp.next;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  76 */     if (this.head == null) { this.head = e; }
/*  78 */     else if (e.getTime() < this.head.getTime()) {
/*  79 */       e.next = this.head;
/*  80 */       this.head = e;
/*     */     } else {
/*  82 */       JBreadBoardEvent temp = this.head;
/*     */ 
/*  84 */       while ((temp.next != null) && (temp.next.getTime() < e.getTime())) {
/*  85 */         temp = temp.next;
/*     */       }
/*  87 */       e.next = temp.next;
/*  88 */       temp.next = e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addEvent(Node n, int delay)
/*     */   {
/*  97 */     if ((n != null) && (delay >= 0))
/*  98 */       queueEvent(new JBreadBoardEvent(n, this.currentTime + delay));
/*     */   }
/*     */ 
/*     */   public void addEvent(Node n, int delay, String value)
/*     */   {
/* 106 */     if ((n != null) && (delay >= 0))
/* 107 */       queueEvent(new JBreadBoardChangeEvent(n, this.currentTime + delay, value));
/*     */   }
/*     */ 
/*     */   private JBreadBoardEvent takeEvent()
/*     */   {
/* 115 */     JBreadBoardEvent e = this.head;
/*     */ 
/* 117 */     if (this.head != null) this.head = this.head.next;
/* 118 */     return e;
/*     */   }
/*     */ 
/*     */   public void runFor(int duration)
/*     */   {
/* 129 */     boolean interrupt = false;
/* 130 */     long newtime = this.currentTime + duration;
/*     */ 
/* 132 */     long starttime = System.currentTimeMillis();
/*     */ 
/* 134 */     while ((!interrupt) && (this.head != null) && (this.head.getTime() <= newtime)) {
/* 135 */       stepSimulation();
/* 136 */       if (System.currentTimeMillis() - starttime > 1L) interrupt = true;
/*     */     }
/*     */ 
/* 139 */     if (!interrupt) this.currentTime = newtime;
/*     */   }
/*     */ 
/*     */   public void stepSimulation()
/*     */   {
/* 147 */     DeviceSet set = null;
/* 148 */     DeviceList list = null;
/*     */ 
/* 151 */     if (this.head != null) {
/* 152 */       if (this.head.getTime() != this.currentTime) {
/* 153 */         this.currentTime = this.head.getTime();
/*     */       }
/*     */ 
/*     */       do
/*     */       {
/* 158 */         JBreadBoardEvent e = takeEvent();
/* 159 */         set = e.process(set);
/* 160 */       }while ((this.head != null) && (this.head.getTime() <= this.currentTime));
/*     */ 
/* 163 */       if (set != null) list = set.toList();
/* 164 */       while (list != null) {
/* 165 */         list.device.simulate();
/* 166 */         list = list.next;
/*     */       }
/*     */     }
/* 169 */     this.currentTime += 1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.JBreadBoardEventSimulation
 * JD-Core Version:    0.6.2
 */