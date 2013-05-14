/*    */ package tools;
/*    */ 
/*    */ import designTools.CircuitDiagramFrame;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import javax.swing.JFrame;
/*    */ 
/*    */ public class SchematicEditor extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/* 27 */     JFrame frame = null;
/*    */ 
/* 29 */     frame = new CircuitDiagramFrame(true);
/*    */ 
/* 33 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*    */ 
/* 35 */     Dimension frameSize = frame.getSize();
/*    */ 
/* 37 */     if (frameSize.height > screenSize.height) {
/* 38 */       frameSize.height = screenSize.height;
/*    */     }
/* 40 */     if (frameSize.width > screenSize.width) {
/* 41 */       frameSize.width = screenSize.width;
/*    */     }
/* 43 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 44 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     tools.SchematicEditor
 * JD-Core Version:    0.6.2
 */