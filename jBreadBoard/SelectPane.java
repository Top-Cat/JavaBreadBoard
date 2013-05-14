/*    */ package jBreadBoard;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class SelectPane extends JPanel
/*    */ {
/*    */   private JBreadBoard jBreadBoard;
/*    */   private CircuitSelection selection;
/*    */   private ImageIcon imageicon;
/* 28 */   private JPanel imagepanel = new JPanel(new BorderLayout());
/* 29 */   private JTextArea infotext = new JTextArea();
/*    */ 
/*    */   public void select(CircuitSelection c)
/*    */   {
/* 38 */     this.selection = c;
/* 39 */     if (c == null) {
/* 40 */       this.imageicon = null;
/* 41 */       remove(this.infotext);
/*    */     } else {
/* 43 */       this.imageicon = this.jBreadBoard.getImageIcon(c.getDiagram());
/* 44 */       remove(this.infotext);
/* 45 */       this.infotext = new JTextArea(c.getInfo(), 0, 10);
/* 46 */       this.infotext.setOpaque(false);
/* 47 */       this.infotext.setMargin(new Insets(0, 0, 0, 0));
/* 48 */       this.infotext.setEditable(false);
/* 49 */       this.infotext.setLineWrap(true);
/* 50 */       this.infotext.setWrapStyleWord(true);
/* 51 */       this.infotext.setRows(this.infotext.getLineCount() + 2);
/* 52 */       this.infotext.setPreferredSize(new Dimension(0, 0));
/* 53 */       add(this.infotext, "Center");
/*    */     }
/* 55 */     revalidate();
/* 56 */     repaint();
/*    */   }
/*    */ 
/*    */   public SelectPane(JBreadBoard j)
/*    */   {
/* 63 */     this.jBreadBoard = j;
/*    */ 
/* 65 */     setLayout(new BorderLayout());
/*    */ 
/* 68 */     setBorder(BorderFactory.createEtchedBorder());
/*    */ 
/* 70 */     this.imagepanel.setBackground(Color.white);
/*    */ 
/* 72 */     JComponent c = new JComponent() {
/*    */       public void paintComponent(Graphics g) {
/* 74 */         super.paintComponent(g);
/* 75 */         if (SelectPane.this.selection == null) g.drawString("Nothing Selected", 50, 100);
/* 76 */         else if ((SelectPane.this.selection instanceof Wire))
/* 77 */           g.drawString("Wire Selected", 10, 70);
/* 78 */         else if ((SelectPane.this.selection instanceof Probe))
/* 79 */           g.drawString("Probe Selected", 10, 70);
/* 80 */         else if (SelectPane.this.imageicon != null)
/* 81 */           SelectPane.this.imageicon.paintIcon(this, g, (getWidth() - SelectPane.this.imageicon.getIconWidth()) / 2, (getHeight() - SelectPane.this.imageicon.getIconHeight()) / 2);
/*    */       }
/*    */     };
/* 88 */     c.setPreferredSize(new Dimension(200, 210));
/* 89 */     c.setMaximumSize(new Dimension(200, 210));
/*    */ 
/* 91 */     c.setBorder(BorderFactory.createEtchedBorder());
/*    */ 
/* 93 */     this.imagepanel.add(c, "Center");
/*    */ 
/* 95 */     add(this.imagepanel, "North");
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.SelectPane
 * JD-Core Version:    0.6.2
 */