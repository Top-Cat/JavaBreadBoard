/*    */ package tools;
/*    */ 
/*    */ import designTools.StateTableFrame;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ public class StateTableEditor extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/* 25 */     JFrame frame = null;
/*    */ 
/* 27 */     frame = new StateTableFrame(true);
/*    */ 
/* 31 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*    */ 
/* 33 */     Dimension frameSize = frame.getSize();
/*    */ 
/* 35 */     if (frameSize.height > screenSize.height) {
/* 36 */       frameSize.height = screenSize.height;
/*    */     }
/* 38 */     if (frameSize.width > screenSize.width) {
/* 39 */       frameSize.width = screenSize.width;
/*    */     }
/* 41 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 42 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     tools.StateTableEditor
 * JD-Core Version:    0.6.2
 */