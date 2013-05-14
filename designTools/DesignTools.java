/*    */ package designTools;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JApplet;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ public class DesignTools extends JApplet
/*    */ {
/* 24 */   private boolean inApplet = false;
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 32 */     DesignTools designTools = new DesignTools();
/* 33 */     designTools.startEditors();
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 40 */     this.inApplet = true;
/*    */ 
/* 42 */     JLabel label = new JLabel("JBB Chip Design Tools");
/* 43 */     label.setHorizontalAlignment(0);
/* 44 */     getContentPane().add(label, "North");
/*    */ 
/* 46 */     JButton button = new JButton("Start Program");
/*    */ 
/* 48 */     button.addMouseListener(new MouseAdapter() {
/*    */       public void mouseClicked(MouseEvent e) {
/* 50 */         DesignTools.this.startEditors();
/*    */       }
/*    */     });
/* 53 */     getContentPane().add(button, "Center");
/*    */   }
/*    */ 
/*    */   private void startEditors()
/*    */   {
/* 59 */     JFrame frame = null;
/*    */     URLMaker urlMaker;
/* 63 */     if (this.inApplet) urlMaker = new URLMaker(getCodeBase()); else {
/* 64 */       urlMaker = new URLMaker(null);
/*    */     }
/*    */ 
/* 68 */     Object[] supportedTools = { "State Table", "Truth Table", "Circuit Diagram" };
/* 69 */     Object defaultTool = "State Table";
/* 70 */     int result = JOptionPane.showOptionDialog(null, "Please select a chip entry tool", "Design Tool Selector", 2, 3, null, supportedTools, defaultTool);
/*    */ 
/* 74 */     if (result == 0) frame = new StateTableFrame(this.inApplet);
/* 75 */     else if (result == 1) frame = new TruthTableFrame(this.inApplet); else {
/* 76 */       frame = new CircuitDiagramFrame(this.inApplet);
/*    */     }
/*    */ 
/* 79 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 80 */     Dimension frameSize = frame.getSize();
/* 81 */     if (frameSize.height > screenSize.height) {
/* 82 */       frameSize.height = screenSize.height;
/*    */     }
/* 84 */     if (frameSize.width > screenSize.width) {
/* 85 */       frameSize.width = screenSize.width;
/*    */     }
/* 87 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/* 88 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.DesignTools
 * JD-Core Version:    0.6.2
 */