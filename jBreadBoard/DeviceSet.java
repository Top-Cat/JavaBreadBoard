/*    */ package jBreadBoard;
/*    */ 
/*    */ public class DeviceSet
/*    */ {
/* 24 */   private DeviceList head = null;
/*    */ 
/*    */   public void add(Device d)
/*    */   {
/* 31 */     if (d == null) return;
/*    */ 
/* 33 */     DeviceList newlist = new DeviceList(d);
/* 34 */     if (this.head == null) { this.head = newlist;
/*    */     } else {
/* 36 */       DeviceList temp = this.head;
/* 37 */       boolean notInSet = true;
/*    */ 
/* 39 */       if (this.head.device == d) notInSet = false;
/*    */ 
/* 41 */       while ((temp.next != null) && (notInSet)) {
/* 42 */         if (temp.next.device == d) notInSet = false;
/* 43 */         temp = temp.next;
/*    */       }
/*    */ 
/* 46 */       if (notInSet)
/* 47 */         temp.next = newlist;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void remove(Device d)
/*    */   {
/* 55 */     DeviceList temp = this.head;
/*    */ 
/* 58 */     if (d == null) return;
/*    */ 
/* 60 */     if (this.head != null)
/* 61 */       if (this.head.device == d) { this.head = this.head.next;
/*    */       } else {
/* 63 */         boolean removed = false;
/* 64 */         while ((temp != null) && (!removed)) {
/* 65 */           if (temp.next.device == d) {
/* 66 */             temp.next = temp.next.next;
/* 67 */             removed = true;
/*    */           }
/* 69 */           temp = temp.next;
/*    */         }
/*    */       }
/*    */   }
/*    */ 
/*    */   public DeviceList toList()
/*    */   {
/* 81 */     return this.head;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.DeviceSet
 * JD-Core Version:    0.6.2
 */