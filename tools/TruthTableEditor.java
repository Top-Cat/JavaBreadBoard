/*    */ package tools;
/*    */ 
/*    */ import designTools.TruthTableFrame;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ public class TruthTableEditor extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/* 23 */     JFrame frame = null;
/*    */ 
/* 25 */     frame = new TruthTableFrame(true);
/*    */ 
/* 29 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*    */ 
/* 31 */     Dimension frameSize = frame.getSize();
/*    */ 
/* 33 */     if (frameSize.height > screenSize.height) {
/* 34 */       frameSize.height = screenSize.height;
/*    */     }
/* 36 */     if (frameSize.width > screenSize.width) {
/* 37 */       frameSize.width = screenSize.width;
/*    */     }
/* 39 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 40 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     tools.TruthTableEditor
 * JD-Core Version:    0.6.2
 */