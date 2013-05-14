/*    */ package integratedCircuits.memory;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.StringTokenizer;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class MemSpace
/*    */ {
/*    */   private byte[] data;
/*    */   private int length;
/*    */ 
/*    */   public MemSpace(int x)
/*    */   {
/* 26 */     this.data = new byte[x];
/* 27 */     this.length = x;
/* 28 */     for (int i = 0; i < x; i++)
/* 29 */       setDatum(i, 0);
/*    */   }
/*    */ 
/*    */   private String removeSpaces(String s)
/*    */   {
/* 37 */     StringTokenizer st = new StringTokenizer(s, " ", false);
/* 38 */     String t = new String("");
/* 39 */     while (st.hasMoreElements()) t = t + st.nextElement();
/* 40 */     return t;
/*    */   }
/*    */ 
/*    */   public int getDatum(int addr)
/*    */   {
/* 45 */     return this.data[addr];
/*    */   }
/*    */ 
/*    */   public void setDatum(int addr, int val)
/*    */   {
/* 50 */     this.data[addr] = ((byte)val);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 55 */     return this.length;
/*    */   }
/*    */ 
/*    */   public String getAll()
/*    */   {
/* 60 */     Vector truthTable = new Vector();
/* 61 */     for (int i = 0; i < this.length; i++) {
/* 62 */       truthTable.add(Integer.valueOf(getDatum(i)).toString());
/*    */     }
/*    */ 
/* 65 */     String data = new String(truthTable.toString());
/* 66 */     data = removeSpaces(data.replace('[', ' '));
/* 67 */     data = removeSpaces(data.replace(']', ' '));
/* 68 */     return data;
/*    */   }
/*    */ 
/*    */   public boolean setAll(String input)
/*    */   {
/* 73 */     int len = input.length();
/* 74 */     if (len > getLength()) {
/* 75 */       System.err.println("Input too large, truncating");
/* 76 */       len = getLength();
/*    */     }
/*    */ 
/* 79 */     if (len > 0) {
/* 80 */       for (int i = 0; i < len; i++) {
/* 81 */         setDatum(i, input.charAt(i));
/*    */       }
/* 83 */       return true;
/*    */     }
/*    */ 
/* 86 */     System.err.println("Empty Input");
/*    */ 
/* 88 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.MemSpace
 * JD-Core Version:    0.6.2
 */