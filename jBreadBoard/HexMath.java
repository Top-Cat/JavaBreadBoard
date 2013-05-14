/*    */ package jBreadBoard;
/*    */ 
/*    */ public class HexMath
/*    */ {
/*    */   public static int HexChartoInt(char hex)
/*    */   {
/* 13 */     if (hex == '0') return 0;
/* 14 */     if (hex == '1') return 1;
/* 15 */     if (hex == '2') return 2;
/* 16 */     if (hex == '3') return 3;
/* 17 */     if (hex == '4') return 4;
/* 18 */     if (hex == '5') return 5;
/* 19 */     if (hex == '6') return 6;
/* 20 */     if (hex == '7') return 7;
/* 21 */     if (hex == '8') return 8;
/* 22 */     if (hex == '9') return 9;
/* 23 */     if (hex == 'A') return 10;
/* 24 */     if (hex == 'B') return 11;
/* 25 */     if (hex == 'C') return 12;
/* 26 */     if (hex == 'D') return 13;
/* 27 */     if (hex == 'E') return 14;
/* 28 */     if (hex == 'F') return 15;
/* 29 */     if (hex == 'a') return 10;
/* 30 */     if (hex == 'b') return 11;
/* 31 */     if (hex == 'c') return 12;
/* 32 */     if (hex == 'd') return 13;
/* 33 */     if (hex == 'e') return 14;
/* 34 */     if (hex == 'f') return 15;
/*    */ 
/* 37 */     return -1;
/*    */   }
/*    */ 
/*    */   public static int HexStringtoInt(String hex)
/*    */   {
/* 42 */     int length = hex.length();
/* 43 */     int retVal = 0;
/*    */ 
/* 45 */     for (int i = 0; i < length; i++) {
/* 46 */       retVal = (int)(retVal + HexChartoInt(hex.charAt(i)) * Math.pow(16.0D, length - i - 1));
/*    */     }
/* 48 */     return retVal;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.HexMath
 * JD-Core Version:    0.6.2
 */