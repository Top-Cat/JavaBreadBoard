/*     */ package designTools;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.net.URL;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class StandardMenu
/*     */ {
/*  21 */   protected JMenuBar menuBar = new JMenuBar();
/*     */   private JMenuItem saveItem;
/*     */   private JMenuItem exitItem;
/*     */   private JMenuItem newItem;
/*     */   protected JMenu helpMenu;
/*     */ 
/*     */   public StandardMenu()
/*     */   {
/*  29 */     JMenu fileMenu = new JMenu("File");
/*  30 */     fileMenu.setMnemonic('F');
/*  31 */     this.menuBar.add(fileMenu);
/*  32 */     this.helpMenu = new JMenu("Help");
/*  33 */     this.helpMenu.setMnemonic('H');
/*  34 */     this.menuBar.add(this.helpMenu);
/*  35 */     this.newItem = new JMenuItem("New", 78);
/*  36 */     this.newItem.setAccelerator(KeyStroke.getKeyStroke(78, 2));
/*  37 */     fileMenu.add(this.newItem);
/*  38 */     this.saveItem = new JMenuItem("Create Chip File", 67);
/*  39 */     this.saveItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
/*  40 */     fileMenu.add(this.saveItem);
/*  41 */     fileMenu.addSeparator();
/*  42 */     this.exitItem = new JMenuItem("Exit", 120);
/*  43 */     fileMenu.add(this.exitItem);
/*  44 */     JMenuItem helpItem = new JMenuItem("User Guide", 85);
/*  45 */     helpItem.setAccelerator(KeyStroke.getKeyStroke(112, 0));
/*  46 */     this.helpMenu.add(helpItem);
/*  47 */     JMenuItem loadingItem = new JMenuItem("Loading and Saving", 76);
/*  48 */     this.helpMenu.add(loadingItem);
/*  49 */     this.helpMenu.addSeparator();
/*  50 */     JMenuItem aboutItem = new JMenuItem("About", 65);
/*  51 */     this.helpMenu.add(aboutItem);
/*     */ 
/*  54 */     aboutItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event)
/*     */       {
/*  58 */         JOptionPane.showMessageDialog(null, "Chip Design Tools for the Java Breadboard Simulator\n\nStephen Halstead\n2004-2005\nUniversity of York\nComputer Science Department", "Chip Design Tools", 1);
/*     */       }
/*     */     });
/*  64 */     loadingItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event)
/*     */       {
/*  68 */         URL loadingPage = null;
/*     */ 
/*  70 */         loadingPage = URLMaker.getURL("loadsave.html");
/*  71 */         HTMLViewer helpBrowser = new HTMLViewer(loadingPage, "Enabling Loading and Saving");
/*  72 */         helpBrowser.setVisible(true);
/*     */       }
/*     */     });
/*  76 */     helpItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent event) {
/*  79 */         URL indexPage = null;
/*     */ 
/*  82 */         indexPage = URLMaker.getURL("designTools/doc/index.htm");
/*  83 */         HTMLViewer helpBrowser = new HTMLViewer(indexPage, "Chip Design Tools Help");
/*  84 */         helpBrowser.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public JMenuBar getMenuBar()
/*     */   {
/*  92 */     return this.menuBar;
/*     */   }
/*     */ 
/*     */   public JMenuItem getSaveMenuItem()
/*     */   {
/*  97 */     return this.saveItem;
/*     */   }
/*     */ 
/*     */   public JMenuItem getExitMenuItem()
/*     */   {
/* 102 */     return this.exitItem;
/*     */   }
/*     */ 
/*     */   public JMenuItem getNewMenuItem()
/*     */   {
/* 107 */     return this.newItem;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.StandardMenu
 * JD-Core Version:    0.6.2
 */