/*     */ package designTools;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.Stack;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.event.HyperlinkEvent;
/*     */ import javax.swing.event.HyperlinkEvent.EventType;
/*     */ 
/*     */ public class HTMLViewer extends JFrame
/*     */ {
/*  25 */   JScrollPane scrollPane = new JScrollPane();
/*  26 */   JEditorPane htmlPane = new JEditorPane();
/*  27 */   JButton cmdClose = new JButton();
/*  28 */   JButton cmdBack = new JButton();
/*     */ 
/*  31 */   private Stack pageStack = new Stack();
/*  32 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*     */ 
/*     */   public HTMLViewer(URL page, String title)
/*     */   {
/*     */     try
/*     */     {
/*  40 */       jbInit();
/*  41 */       displayPage(page);
/*  42 */       setTitle(title);
/*     */     }
/*     */     catch (Exception ex) {
/*  45 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void jbInit() throws Exception {
/*  50 */     getContentPane().setLayout(this.gridBagLayout1);
/*  51 */     this.htmlPane.addHyperlinkListener(new HTMLViewer_htmlPane_hyperlinkAdapter(this));
/*  52 */     this.htmlPane.setEditable(false);
/*  53 */     this.htmlPane.setContentType("text/html");
/*  54 */     this.cmdClose.setText("Close");
/*  55 */     this.cmdClose.addMouseListener(new HTMLViewer_cmdClose_mouseAdapter(this));
/*  56 */     this.cmdBack.setText("Back");
/*  57 */     this.cmdBack.addMouseListener(new HTMLViewer_cmdBack_mouseAdapter(this));
/*  58 */     setSize(new Dimension(628, 440));
/*  59 */     getContentPane().add(this.scrollPane, new GridBagConstraints(0, 0, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(10, 8, 0, 11), 0, 353));
/*     */ 
/*  61 */     getContentPane().add(this.cmdClose, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(11, 9, 17, 11), 14, 0));
/*     */ 
/*  63 */     getContentPane().add(this.cmdBack, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(11, 441, 17, 0), 20, 0));
/*     */ 
/*  65 */     this.scrollPane.getViewport().add(this.htmlPane, null);
/*     */ 
/*  68 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  69 */     Dimension frameSize = getSize();
/*  70 */     if (frameSize.height > screenSize.height) {
/*  71 */       frameSize.height = screenSize.height;
/*     */     }
/*  73 */     if (frameSize.width > screenSize.width) {
/*  74 */       frameSize.width = screenSize.width;
/*     */     }
/*  76 */     setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/*     */   }
/*     */ 
/*     */   void cmdClose_mouseClicked(MouseEvent e)
/*     */   {
/*  82 */     dispose();
/*     */   }
/*     */ 
/*     */   public void displayPage(URL page)
/*     */   {
/*     */     try
/*     */     {
/*  91 */       this.pageStack.push(page.toString());
/*     */ 
/*  93 */       this.htmlPane.setPage(page);
/*     */     }
/*     */     catch (IOException ex) {
/*  96 */       this.htmlPane.setText("Exception: " + ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   void htmlPane_hyperlinkUpdate(HyperlinkEvent e)
/*     */   {
/* 103 */     if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
/* 104 */       displayPage(e.getURL());
/*     */   }
/*     */ 
/*     */   void cmdBack_mouseClicked(MouseEvent e)
/*     */   {
/* 111 */     if (this.pageStack.size() > 1)
/*     */       try
/*     */       {
/* 114 */         this.pageStack.pop();
/*     */ 
/* 116 */         String pageString = (String)this.pageStack.peek();
/* 117 */         this.htmlPane.setPage(pageString);
/*     */       }
/*     */       catch (IOException ex) {
/* 120 */         this.htmlPane.setText("Exception: " + ex);
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.HTMLViewer
 * JD-Core Version:    0.6.2
 */