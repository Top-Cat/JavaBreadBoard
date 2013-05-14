/*    */ package designTools;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ class URLMaker
/*    */ {
/*    */   private static URL cBase;
/*    */ 
/*    */   public URLMaker(URL codeBase)
/*    */   {
/* 25 */     cBase = codeBase;
/*    */   }
/*    */ 
/*    */   public static URL getURL(String s)
/*    */   {
/* 35 */     URL url = null;
/*    */     try {
/* 37 */       if (cBase == null) {
/* 38 */         url = new URL("file:" + s);
/*    */       }
/*    */       else
/* 41 */         url = new URL(cBase + s);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 45 */       e.printStackTrace();
/*    */     }
/* 47 */     return url;
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.URLMaker
 * JD-Core Version:    0.6.2
 */