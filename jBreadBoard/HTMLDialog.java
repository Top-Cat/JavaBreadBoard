/*    */ package jBreadBoard;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JEditorPane;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JRootPane;
/*    */ import javax.swing.JScrollPane;
/*    */ 
/*    */ public class HTMLDialog
/*    */ {
/*    */   public static void addHTMLDialog(URL u, String title)
/*    */   {
/* 33 */     final JDialog dialog = new JDialog((JFrame)null, title, false);
/* 34 */     JEditorPane editorPane = new JEditorPane();
/* 35 */     JScrollPane scroller = new JScrollPane(editorPane);
/*    */ 
/* 37 */     editorPane.setEditable(false);
/*    */     try {
/* 39 */       editorPane.setPage(u);
/*    */ 
/* 41 */       dialog.getContentPane().setLayout(new BorderLayout());
/*    */ 
/* 43 */       dialog.getContentPane().add(scroller, "Center");
/*    */ 
/* 45 */       JPanel buttonPane = new JPanel();
/* 46 */       buttonPane.setLayout(new FlowLayout(1));
/* 47 */       dialog.getContentPane().add(buttonPane, "South");
/*    */ 
/* 50 */       JButton closeButton = new JButton("Close");
/* 51 */       dialog.getRootPane().setDefaultButton(closeButton);
/* 52 */       buttonPane.add(closeButton);
/* 53 */       closeButton.addActionListener(new ActionListener() {
/*    */         public void actionPerformed(ActionEvent e) {
/* 55 */           dialog.dispose();
/*    */         }
/*    */       });
/* 59 */       dialog.pack();
/* 60 */       dialog.setSize(new Dimension(600, 400));
/*    */ 
/* 62 */       dialog.setVisible(true);
/*    */     }
/*    */     catch (IOException exception)
/*    */     {
/* 66 */       JOptionPane.showMessageDialog(null, "File Not Found", "Error HTMLDialog.001", 2);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.HTMLDialog
 * JD-Core Version:    0.6.2
 */