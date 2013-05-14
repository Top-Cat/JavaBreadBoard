/*     */ package jBreadBoard;
/*     */ 
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.net.URL;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class JBreadBoard extends JApplet
/*     */ {
/*     */   private JFrame jframe;
/*  33 */   private Circuit circuit = new Circuit(this);
/*  34 */   private JLabel statusbar = new JLabel(" ");
/*  35 */   private JLabel probebar = new JLabel(" ");
/*  36 */   private JSlider speedslider = new JSlider(0, 90, 30);
/*  37 */   private JBreadBoardActionListener jbbal = new JBreadBoardActionListener(this);
/*  38 */   private SelectPane selectpane = new SelectPane(this);
/*     */   private String mode;
/*  40 */   private static boolean inApplet = false;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  51 */     JBreadBoard jBreadBoard = new JBreadBoard();
/*  52 */     jBreadBoard.jframe = jBreadBoard.createFrame();
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  59 */     inApplet = true;
/*     */ 
/*  61 */     JLabel label = new JLabel("Java BreadBoard Simulator");
/*  62 */     label.setHorizontalAlignment(0);
/*  63 */     getContentPane().add(label, "North");
/*     */ 
/*  65 */     JButton button = new JButton("Start Program");
/*     */ 
/*  67 */     button.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  69 */         if (JBreadBoard.this.jframe == null)
/*  70 */           JBreadBoard.this.jframe = JBreadBoard.this.createFrame();
/*     */       }
/*     */     });
/*  74 */     getContentPane().add(button, "Center");
/*     */   }
/*     */ 
/*     */   public static boolean inApplet()
/*     */   {
/*  86 */     return inApplet;
/*     */   }
/*     */ 
/*     */   public void exit()
/*     */   {
/*  93 */     if (inApplet) {
/*  94 */       this.jframe.dispose();
/*  95 */       this.jframe = null; } else {
/*  96 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void newcircuit()
/*     */   {
/* 103 */     if (this.circuit.getComponentCount() > 0) {
/* 104 */       Object[] options = { "Yes", "No" };
/* 105 */       int n = JOptionPane.showOptionDialog(this.jframe, "The Circuit is not empty.\nAre sure you want to Erase it?", "WARNING", 0, 2, null, options, options[1]);
/*     */ 
/* 115 */       if (n == 0) {
/* 116 */         this.circuit.removeAll();
/* 117 */         this.circuit.resetSimulation();
/* 118 */         this.circuit.repaint();
/*     */       }
/*     */     }
/* 121 */     setMode("move");
/*     */   }
/*     */ 
/*     */   public void save()
/*     */   {
/* 128 */     FileDialogs.save(this.jframe, this.circuit.toString());
/*     */   }
/*     */ 
/*     */   public void load()
/*     */   {
/* 135 */     newcircuit();
/* 136 */     if (this.circuit.getComponentCount() != 0) return;
/* 137 */     AdvChip.insertCircuit(this.jframe, this.circuit, 0, 0);
/*     */   }
/*     */ 
/*     */   public void insertCircuit()
/*     */   {
/* 144 */     AdvChip.insertCircuit(this.jframe, this.circuit, 0, AdvChip.lowestBoard(this.circuit));
/*     */   }
/*     */ 
/*     */   public Circuit getCircuit()
/*     */   {
/* 152 */     return this.circuit;
/*     */   }
/*     */ 
/*     */   public JFrame getJFrame()
/*     */   {
/* 159 */     return this.jframe;
/*     */   }
/*     */ 
/*     */   protected URL getURL(String filename)
/*     */   {
/* 166 */     URL url = ClassLoader.getSystemResource(filename);
/*     */ 
/* 168 */     return url;
/*     */   }
/*     */ 
/*     */   public ImageIcon getImageIcon(String s)
/*     */   {
/* 175 */     if (s == null) return null;
/*     */ 
/* 177 */     if (inApplet)
/*     */     {
/* 182 */       URL iconURL = getURL(s);
/* 183 */       if (iconURL != null) return new ImageIcon(iconURL); 
/*     */     }
/* 185 */     else { return new ImageIcon(s); }
/*     */ 
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMessage(String message)
/*     */   {
/* 194 */     this.statusbar.setText(message);
/* 195 */     this.statusbar.repaint();
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 202 */     return this.statusbar.getText();
/*     */   }
/*     */ 
/*     */   public void setMode(String modename)
/*     */   {
/* 209 */     setMode(modename, "OK");
/*     */   }
/*     */ 
/*     */   public String getMode()
/*     */   {
/* 216 */     if (this.mode == null) return "";
/* 217 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public int getSimSpeed()
/*     */   {
/* 225 */     return (int)Math.pow(10.0D, this.speedslider.getValue() / 10.0F);
/*     */   }
/*     */ 
/*     */   public SelectPane getSelectPane()
/*     */   {
/* 232 */     return this.selectpane;
/*     */   }
/*     */ 
/*     */   void setMode(String modename, String modetxt)
/*     */   {
/* 242 */     if ((this.mode != null) && (this.mode.equals("drawingwire")) && (WireDrawer.current != null)) {
/* 243 */       Wire w = WireDrawer.current;
/* 244 */       WireDrawer.current = null;
/* 245 */       WireDrawer.last = null;
/* 246 */       w.delete();
/*     */     }
/*     */ 
/* 250 */     if ((this.mode != null) && (this.mode.equals("sim")) && (modename != null) && (!modename.equals("sim"))) {
/* 251 */       this.circuit.resetSimulation();
/*     */     }
/*     */ 
/* 254 */     this.mode = modename;
/* 255 */     setMessage(modetxt);
/*     */   }
/*     */ 
/*     */   void setProbebar(String s)
/*     */   {
/* 263 */     this.probebar.setText(s);
/* 264 */     if (s.equals("HIGH")) this.probebar.setForeground(Color.red);
/* 265 */     else if (s.equals("LOW")) this.probebar.setForeground(new Color(41, 127, 55)); else
/* 266 */       this.probebar.setForeground(null);
/*     */   }
/*     */ 
/*     */   public static boolean implementsInterface(ChipModel chipname, String inter)
/*     */   {
/* 274 */     boolean result = false;
/* 275 */     Class class_name = chipname.getClass();
/*     */ 
/* 278 */     while (!class_name.getSimpleName().equalsIgnoreCase("Object")) {
/* 279 */       Class[] interfaces = class_name.getInterfaces();
/*     */ 
/* 283 */       for (int i = 0; i < interfaces.length; i++)
/*     */       {
/* 285 */         if (interfaces[i].getName().equals(inter)) {
/* 286 */           result = true;
/*     */         }
/*     */       }
/* 289 */       class_name = class_name.getSuperclass();
/*     */     }
/*     */ 
/* 292 */     return result;
/*     */   }
/*     */ 
/*     */   public static boolean implementsClass(ChipModel chipname, String className)
/*     */   {
/* 297 */     boolean result = false;
/* 298 */     Class class_name = chipname.getClass();
/*     */ 
/* 301 */     while (!class_name.getSimpleName().equalsIgnoreCase("Object")) {
/* 302 */       if (class_name.getSimpleName().equalsIgnoreCase(className))
/* 303 */         result = true;
/* 304 */       class_name = class_name.getSuperclass();
/*     */     }
/*     */ 
/* 307 */     return result;
/*     */   }
/*     */ 
/*     */   public static ChipModel getChipModel(String s)
/*     */   {
/* 318 */     Class chipModel = null;
/*     */     try
/*     */     {
/* 322 */       chipModel = Class.forName(s);
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 325 */       JOptionPane.showMessageDialog(null, "Error  JBreadBoard.004\n" + e.getMessage(), "Error ", 0);
/*     */     }
/*     */     catch (NoClassDefFoundError e)
/*     */     {
/* 332 */       JOptionPane.showMessageDialog(null, "Error  JBreadBoard.005\n" + e.getMessage(), "Error", 0);
/*     */     }
/*     */ 
/* 340 */     if (chipModel != null) {
/*     */       try
/*     */       {
/* 343 */         Object object = chipModel.newInstance();
/* 344 */         if (implementsInterface((ChipModel)object, "jBreadBoard.v1_00.ChipModel"))
/*     */         {
/* 346 */           return (ChipModel)object;
/*     */         }
/*     */ 
/* 349 */         JOptionPane.showMessageDialog(null, "Error  JBreadBoard.006\n" + s + ".class" + "does not implement jBreadBoard.v1_00.ChipModel.", "Error: ", 0);
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/* 358 */         JOptionPane.showMessageDialog(null, "Error  \n" + e.getMessage(), "Error  JBreadBoard.007", 0);
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 365 */         JOptionPane.showMessageDialog(null, "Error.\n" + e.getMessage(), "Error  JBreadBoard.008", 0);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 372 */     return null;
/*     */   }
/*     */ 
/*     */   public JFrame createFrame()
/*     */   {
/* 387 */     JFrame frame = new JFrame("Java BreadBoard Simulator");
/*     */ 
/* 390 */     this.circuit = new Circuit(this);
/* 391 */     setMode("move");
/*     */ 
/* 395 */     frame.setJMenuBar(buildMenu());
/*     */ 
/* 397 */     this.statusbar.setBorder(BorderFactory.createLoweredBevelBorder());
/* 398 */     this.probebar.setBorder(BorderFactory.createLoweredBevelBorder());
/* 399 */     JPanel statuspane = new JPanel();
/* 400 */     GridBagLayout gridbag = new GridBagLayout();
/* 401 */     GridBagConstraints c = new GridBagConstraints();
/* 402 */     statuspane.setLayout(gridbag);
/* 403 */     frame.getContentPane().add(statuspane, "South");
/*     */ 
/* 405 */     c.gridx = 0;
/* 406 */     c.gridy = 0;
/* 407 */     c.weightx = 4.0D;
/* 408 */     c.fill = 1;
/* 409 */     gridbag.setConstraints(this.statusbar, c);
/* 410 */     statuspane.add(this.statusbar);
/*     */ 
/* 412 */     c.gridx = 1;
/* 413 */     c.weightx = 1.0D;
/* 414 */     gridbag.setConstraints(this.probebar, c);
/* 415 */     statuspane.add(this.probebar);
/*     */ 
/* 418 */     JPanel inter1 = new JPanel(new BorderLayout());
/* 419 */     frame.getContentPane().add(inter1, "Center");
/*     */ 
/* 421 */     inter1.add(buildToolBar(), "North");
/*     */ 
/* 423 */     JPanel inter2 = new JPanel(new BorderLayout());
/* 424 */     inter1.add(inter2, "Center");
/*     */ 
/* 426 */     inter2.add(this.selectpane, "West");
/*     */ 
/* 428 */     JScrollPane scroller = new JScrollPane(this.circuit);
/* 429 */     inter2.add(scroller, "Center");
/*     */ 
/* 432 */     frame.addWindowListener(new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent e) {
/* 434 */         JBreadBoard.this.exit();
/*     */       }
/*     */     });
/* 439 */     frame.pack();
/* 440 */     frame.setVisible(true);
/*     */ 
/* 442 */     return frame;
/*     */   }
/*     */ 
/*     */   private JMenuBar buildMenu()
/*     */   {
/* 451 */     JMenuBar jMenuBar = new JMenuBar();
/*     */ 
/* 456 */     JMenu menu = new JMenu("File");
/* 457 */     menu.setMnemonic('F');
/* 458 */     jMenuBar.add(menu);
/*     */ 
/* 460 */     JMenuItem menuItem = new JMenuItem("New", 78);
/* 461 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2));
/* 462 */     menuItem.setActionCommand("new");
/* 463 */     menuItem.addActionListener(this.jbbal);
/* 464 */     menu.add(menuItem);
/*     */ 
/* 467 */     menuItem = new JMenuItem("Open", 79);
/* 468 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(79, 2));
/* 469 */     menuItem.setActionCommand("load");
/* 470 */     menuItem.addActionListener(this.jbbal);
/* 471 */     menu.add(menuItem);
/*     */ 
/* 474 */     menuItem = new JMenuItem("Insert Circuit", 73);
/* 475 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(73, 2));
/* 476 */     menuItem.setActionCommand("insertcircuit");
/* 477 */     menuItem.addActionListener(this.jbbal);
/* 478 */     menu.add(menuItem);
/*     */ 
/* 481 */     menuItem = new JMenuItem("Save", 83);
/* 482 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
/* 483 */     menuItem.setActionCommand("save");
/* 484 */     menuItem.addActionListener(this.jbbal);
/* 485 */     menu.add(menuItem);
/*     */ 
/* 488 */     menu.addSeparator();
/*     */ 
/* 491 */     menuItem = new JMenuItem("Exit", 120);
/* 492 */     menuItem.setActionCommand("exit");
/* 493 */     menuItem.addActionListener(this.jbbal);
/* 494 */     menu.add(menuItem);
/*     */ 
/* 497 */     menu = new JMenu("Edit");
/* 498 */     menu.setMnemonic('E');
/* 499 */     jMenuBar.add(menu);
/*     */ 
/* 501 */     menuItem = new JMenuItem("Selection Mode", 115);
/* 502 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(155, 0));
/* 503 */     menuItem.setActionCommand("select");
/* 504 */     menuItem.addActionListener(this.jbbal);
/* 505 */     menu.add(menuItem);
/*     */ 
/* 507 */     menuItem = new JMenuItem("Delete", 68);
/* 508 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(127, 0));
/* 509 */     menuItem.setActionCommand("delete");
/* 510 */     menuItem.addActionListener(this.jbbal);
/* 511 */     menu.add(menuItem);
/*     */ 
/* 514 */     menu = new JMenu("Insert");
/* 515 */     menu.setMnemonic('I');
/* 516 */     jMenuBar.add(menu);
/*     */ 
/* 518 */     menuItem = new JMenuItem("BreadBoard", 66);
/* 519 */     menuItem.setActionCommand("breadboard");
/* 520 */     menuItem.addActionListener(this.jbbal);
/* 521 */     menu.add(menuItem);
/*     */ 
/* 523 */     menuItem = new JMenuItem("Chip", 67);
/* 524 */     menuItem.setActionCommand("chip");
/* 525 */     menuItem.addActionListener(this.jbbal);
/* 526 */     menu.add(menuItem);
/*     */ 
/* 528 */     JMenu submenu = new JMenu("Dip Switches");
/* 529 */     submenu.setMnemonic('D');
/* 530 */     menu.add(submenu);
/*     */ 
/* 532 */     menuItem = new JMenuItem("Single", 83);
/* 533 */     menuItem.setActionCommand("dip1");
/* 534 */     menuItem.addActionListener(this.jbbal);
/* 535 */     submenu.add(menuItem);
/*     */ 
/* 537 */     menuItem = new JMenuItem("Double", 68);
/* 538 */     menuItem.setActionCommand("dip2");
/* 539 */     menuItem.addActionListener(this.jbbal);
/* 540 */     submenu.add(menuItem);
/*     */ 
/* 542 */     menuItem = new JMenuItem("Treble", 84);
/* 543 */     menuItem.setActionCommand("dip3");
/* 544 */     menuItem.addActionListener(this.jbbal);
/* 545 */     submenu.add(menuItem);
/*     */ 
/* 547 */     menuItem = new JMenuItem("Quad", 81);
/* 548 */     menuItem.setActionCommand("dip4");
/* 549 */     menuItem.addActionListener(this.jbbal);
/* 550 */     submenu.add(menuItem);
/*     */ 
/* 552 */     submenu = new JMenu("LED");
/* 553 */     submenu.setMnemonic('L');
/* 554 */     menu.add(submenu);
/*     */ 
/* 556 */     menuItem = new JMenuItem("Red", 82);
/* 557 */     menuItem.setActionCommand("rled");
/* 558 */     menuItem.addActionListener(this.jbbal);
/* 559 */     submenu.add(menuItem);
/*     */ 
/* 561 */     menuItem = new JMenuItem("Yellow", 89);
/* 562 */     menuItem.setActionCommand("yled");
/* 563 */     menuItem.addActionListener(this.jbbal);
/* 564 */     submenu.add(menuItem);
/*     */ 
/* 566 */     menuItem = new JMenuItem("Green", 71);
/* 567 */     menuItem.setActionCommand("gled");
/* 568 */     menuItem.addActionListener(this.jbbal);
/* 569 */     submenu.add(menuItem);
/*     */ 
/* 572 */     menu = new JMenu("Wire");
/* 573 */     menu.setMnemonic('W');
/* 574 */     jMenuBar.add(menu);
/*     */ 
/* 576 */     menuItem = new JMenuItem("Add Wires", 65);
/* 577 */     menuItem.setActionCommand("wire");
/* 578 */     menuItem.addActionListener(this.jbbal);
/* 579 */     menu.add(menuItem);
/*     */ 
/* 581 */     menuItem = new JMenuItem("Cancel Wire Segment", 67);
/* 582 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(27, 0));
/* 583 */     menuItem.setActionCommand("cancelsegment");
/* 584 */     menuItem.addActionListener(this.jbbal);
/* 585 */     menu.add(menuItem);
/*     */ 
/* 587 */     menu.addSeparator();
/*     */ 
/* 589 */     menuItem = new JMenuItem("Hide Wires", 72);
/* 590 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(72, 2));
/* 591 */     menuItem.setActionCommand("hidewires");
/* 592 */     menuItem.addActionListener(this.jbbal);
/* 593 */     menu.add(menuItem);
/*     */ 
/* 595 */     menuItem = new JMenuItem("Unhide Wires", 85);
/* 596 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(85, 2));
/* 597 */     menuItem.setActionCommand("unhidewires");
/* 598 */     menuItem.addActionListener(this.jbbal);
/* 599 */     menu.add(menuItem);
/*     */ 
/* 601 */     menu.addSeparator();
/*     */ 
/* 606 */     ButtonGroup group = new ButtonGroup();
/* 607 */     ActionListener colorActionListener = new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 609 */         String command = e.getActionCommand();
/* 610 */         if (command == "Custom") {
/* 611 */           final JColorChooser colorChooser = new JColorChooser();
/* 612 */           colorChooser.setColor(WireDrawer.color);
/* 613 */           JDialog dialog = JColorChooser.createDialog(null, "Pick a Colour", true, colorChooser, new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e)
/*     */             {
/* 621 */               WireDrawer.color = colorChooser.getColor();
/*     */             }
/*     */           }
/*     */           , null);
/*     */ 
/* 627 */           dialog.setVisible(true);
/*     */         }
/* 629 */         else if (command == "White") { WireDrawer.color = Color.white;
/* 630 */         } else if (command == "Black") { WireDrawer.color = Color.black;
/* 631 */         } else if (command == "Red") { WireDrawer.color = Color.red;
/* 632 */         } else if (command == "Orange") { WireDrawer.color = Color.orange;
/* 633 */         } else if (command == "Yellow") { WireDrawer.color = Color.yellow;
/* 634 */         } else if (command == "Green") { WireDrawer.color = Color.green;
/* 635 */         } else if (command == "Blue") { WireDrawer.color = Color.blue; }
/*     */ 
/*     */       }
/*     */     };
/* 638 */     JRadioButtonMenuItem radio = new JRadioButtonMenuItem("White");
/* 639 */     radio.addActionListener(colorActionListener);
/* 640 */     menu.add(radio);
/* 641 */     group.add(radio);
/* 642 */     radio = new JRadioButtonMenuItem("Black");
/* 643 */     radio.addActionListener(colorActionListener);
/* 644 */     menu.add(radio);
/* 645 */     group.add(radio);
/* 646 */     radio = new JRadioButtonMenuItem("Red", true);
/* 647 */     radio.addActionListener(colorActionListener);
/* 648 */     menu.add(radio);
/* 649 */     group.add(radio);
/* 650 */     radio = new JRadioButtonMenuItem("Orange");
/* 651 */     radio.addActionListener(colorActionListener);
/* 652 */     menu.add(radio);
/* 653 */     group.add(radio);
/* 654 */     radio = new JRadioButtonMenuItem("Yellow");
/* 655 */     radio.addActionListener(colorActionListener);
/* 656 */     menu.add(radio);
/* 657 */     group.add(radio);
/* 658 */     radio = new JRadioButtonMenuItem("Green");
/* 659 */     radio.addActionListener(colorActionListener);
/* 660 */     menu.add(radio);
/* 661 */     group.add(radio);
/* 662 */     radio = new JRadioButtonMenuItem("Blue");
/* 663 */     radio.addActionListener(colorActionListener);
/* 664 */     menu.add(radio);
/* 665 */     group.add(radio);
/*     */ 
/* 667 */     menu.addSeparator();
/* 668 */     radio = new JRadioButtonMenuItem("Custom");
/* 669 */     radio.addActionListener(colorActionListener);
/* 670 */     menu.add(radio);
/* 671 */     group.add(radio);
/*     */ 
/* 675 */     menu = new JMenu("Simulation");
/* 676 */     menu.setMnemonic('S');
/* 677 */     jMenuBar.add(menu);
/*     */ 
/* 679 */     menuItem = new JMenuItem("Reset Simulation", 82);
/* 680 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(8, 0));
/* 681 */     menuItem.setActionCommand("reset");
/* 682 */     menuItem.addActionListener(this.jbbal);
/* 683 */     menu.add(menuItem);
/*     */ 
/* 686 */     menuItem = new JMenuItem("Pause", 80);
/* 687 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 2));
/* 688 */     menuItem.setActionCommand("pause");
/* 689 */     menuItem.addActionListener(this.jbbal);
/* 690 */     menu.add(menuItem);
/*     */ 
/* 693 */     menuItem = new JMenuItem("Step Simulation", 83);
/* 694 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(10, 0));
/* 695 */     menuItem.setActionCommand("step");
/* 696 */     menuItem.addActionListener(this.jbbal);
/* 697 */     menu.add(menuItem);
/*     */ 
/* 700 */     menuItem = new JMenuItem("Run", 82);
/* 701 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(82, 2));
/* 702 */     menuItem.setActionCommand("animate");
/* 703 */     menuItem.addActionListener(this.jbbal);
/* 704 */     menu.add(menuItem);
/*     */ 
/* 707 */     menu = new JMenu("Trace");
/* 708 */     menu.setMnemonic('T');
/* 709 */     jMenuBar.add(menu);
/*     */ 
/* 711 */     menuItem = new JMenuItem("Insert Probe", 80);
/* 712 */     menuItem.setActionCommand("addprobe");
/* 713 */     menuItem.addActionListener(this.jbbal);
/* 714 */     menu.add(menuItem);
/*     */ 
/* 717 */     menuItem = new JMenuItem("Save Trace", 83);
/* 718 */     menuItem.setActionCommand("savetrace");
/* 719 */     menuItem.addActionListener(this.jbbal);
/* 720 */     menu.add(menuItem);
/*     */ 
/* 722 */     menu.addSeparator();
/*     */ 
/* 725 */     JCheckBoxMenuItem checkitem = new JCheckBoxMenuItem("Dual data at Time", Trace.duplicate);
/* 726 */     checkitem.setActionCommand("toggledualdata");
/* 727 */     checkitem.addActionListener(this.jbbal);
/* 728 */     menu.add(checkitem);
/*     */ 
/* 731 */     menu = new JMenu("Tools");
/* 732 */     menu.setMnemonic('T');
/* 733 */     jMenuBar.add(menu);
/*     */ 
/* 735 */     String[] p = getToolList();
/*     */ 
/* 739 */     for (String s : p)
/*     */     {
/* 742 */       String tmp = s.substring(0, s.lastIndexOf(".class"));
/* 743 */       menuItem = new JMenuItem(tmp);
/*     */ 
/* 746 */       menuItem.setActionCommand("tool:" + s);
/* 747 */       menuItem.addActionListener(this.jbbal);
/*     */ 
/* 750 */       menu.add(menuItem);
/*     */     }
/*     */ 
/* 754 */     menu = new JMenu("Help");
/* 755 */     menu.setMnemonic('H');
/* 756 */     jMenuBar.add(menu);
/*     */ 
/* 758 */     menuItem = new JMenuItem("User Guide", 85);
/* 759 */     menuItem.setActionCommand("userguide");
/* 760 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(112, 0));
/* 761 */     menuItem.addActionListener(this.jbbal);
/* 762 */     menu.add(menuItem);
/*     */ 
/* 765 */     menuItem = new JMenuItem("Loading and Saving", 76);
/* 766 */     menuItem.setActionCommand("loadsave");
/* 767 */     menuItem.addActionListener(this.jbbal);
/* 768 */     menu.add(menuItem);
/*     */ 
/* 770 */     menu.addSeparator();
/*     */ 
/* 772 */     menuItem = new JMenuItem("About", 65);
/* 773 */     menuItem.setActionCommand("about");
/* 774 */     menuItem.addActionListener(this.jbbal);
/* 775 */     menu.add(menuItem);
/*     */ 
/* 777 */     return jMenuBar;
/*     */   }
/*     */ 
/*     */   private JToolBar buildToolBar()
/*     */   {
/* 786 */     JToolBar toolbar = new JToolBar();
/*     */ 
/* 790 */     JButton button = new JButton(getImageIcon("images/new.gif"));
/* 791 */     button.setToolTipText("New");
/* 792 */     button.setActionCommand("new");
/* 793 */     button.addActionListener(this.jbbal);
/* 794 */     toolbar.add(button);
/*     */ 
/* 797 */     button = new JButton(getImageIcon("images/open.gif"));
/* 798 */     button.setToolTipText("Open");
/* 799 */     button.setActionCommand("load");
/* 800 */     button.addActionListener(this.jbbal);
/* 801 */     toolbar.add(button);
/*     */ 
/* 804 */     button = new JButton(getImageIcon("images/save.gif"));
/* 805 */     button.setToolTipText("Save");
/* 806 */     button.setActionCommand("save");
/* 807 */     button.addActionListener(this.jbbal);
/* 808 */     toolbar.add(button);
/*     */ 
/* 810 */     toolbar.addSeparator();
/*     */ 
/* 813 */     button = new JButton(getImageIcon("images/select.gif"));
/* 814 */     button.setToolTipText("Selector");
/* 815 */     button.setActionCommand("select");
/* 816 */     button.addActionListener(this.jbbal);
/* 817 */     toolbar.add(button);
/*     */ 
/* 820 */     button = new JButton(getImageIcon("images/del.gif"));
/* 821 */     button.setToolTipText("Delete Object");
/* 822 */     button.setActionCommand("delete");
/* 823 */     button.addActionListener(this.jbbal);
/* 824 */     toolbar.add(button);
/*     */ 
/* 826 */     toolbar.addSeparator();
/*     */ 
/* 829 */     button = new JButton(getImageIcon("images/newb.gif"));
/* 830 */     button.setToolTipText("Insert BreadBoard");
/* 831 */     button.setActionCommand("breadboard");
/* 832 */     button.addActionListener(this.jbbal);
/* 833 */     toolbar.add(button);
/*     */ 
/* 836 */     button = new JButton(getImageIcon("images/chip.gif"));
/* 837 */     button.setToolTipText("Add Chip");
/* 838 */     button.setActionCommand("defaultchip");
/* 839 */     button.addActionListener(this.jbbal);
/* 840 */     toolbar.add(button);
/*     */ 
/* 843 */     button = new JButton(getImageIcon("images/chipq.gif"));
/* 844 */     button.setToolTipText("Select and Add Chip");
/* 845 */     button.setActionCommand("chip");
/* 846 */     button.addActionListener(this.jbbal);
/* 847 */     toolbar.add(button);
/*     */ 
/* 850 */     button = new JButton(getImageIcon("images/wire.gif"));
/* 851 */     button.setToolTipText("Wiring Mode");
/* 852 */     button.setActionCommand("wire");
/* 853 */     button.addActionListener(this.jbbal);
/* 854 */     toolbar.add(button);
/*     */ 
/* 856 */     toolbar.addSeparator();
/*     */ 
/* 859 */     button = new JButton(getImageIcon("images/led.gif"));
/* 860 */     button.setToolTipText("Add LED");
/* 861 */     button.setActionCommand("led");
/* 862 */     button.addActionListener(this.jbbal);
/* 863 */     toolbar.add(button);
/*     */ 
/* 866 */     button = new JButton(getImageIcon("images/dip.gif"));
/* 867 */     button.setToolTipText("Add Dip Switches");
/* 868 */     button.setActionCommand("dip");
/* 869 */     button.addActionListener(this.jbbal);
/* 870 */     toolbar.add(button);
/*     */ 
/* 872 */     toolbar.addSeparator();
/*     */ 
/* 875 */     button = new JButton(getImageIcon("images/reset.gif"));
/* 876 */     button.setToolTipText("Reset Simulation");
/* 877 */     button.setActionCommand("reset");
/* 878 */     button.addActionListener(this.jbbal);
/* 879 */     toolbar.add(button);
/*     */ 
/* 882 */     button = new JButton(getImageIcon("images/pause.gif"));
/* 883 */     button.setToolTipText("Pause Simulation");
/* 884 */     button.setActionCommand("pause");
/* 885 */     button.addActionListener(this.jbbal);
/* 886 */     toolbar.add(button);
/*     */ 
/* 889 */     button = new JButton(getImageIcon("images/step.gif"));
/* 890 */     button.setToolTipText("Step Simulation");
/* 891 */     button.setActionCommand("step");
/* 892 */     button.addActionListener(this.jbbal);
/* 893 */     toolbar.add(button);
/*     */ 
/* 896 */     button = new JButton(getImageIcon("images/run.gif"));
/* 897 */     button.setToolTipText("Run Simulation");
/* 898 */     button.setActionCommand("animate");
/* 899 */     button.addActionListener(this.jbbal);
/* 900 */     toolbar.add(button);
/*     */ 
/* 902 */     toolbar.addSeparator();
/*     */ 
/* 904 */     JLabel label = new JLabel("Sim Speed: ");
/* 905 */     toolbar.add(label);
/*     */ 
/* 907 */     this.speedslider.setPaintTicks(true);
/* 908 */     this.speedslider.setMajorTickSpacing(10);
/* 909 */     toolbar.add(this.speedslider);
/*     */ 
/* 911 */     return toolbar;
/*     */   }
/*     */ 
/*     */   public String[] getToolList() {
/* 915 */     URL url = ClassLoader.getSystemResource("tools");
/*     */ 
/* 917 */     String fileName = url.getFile();
/*     */ 
/* 919 */     File dir = new File(fileName);
/*     */ 
/* 921 */     if (!dir.exists()) return new String[] { "Tool Directory error" };
/*     */ 
/* 925 */     String[] tools = dir.list(new FilenameFilter() {
/*     */       public boolean accept(File dir, String name) {
/* 927 */         return name.endsWith(".class");
/*     */       }
/*     */     });
/* 932 */     if (tools.equals(null)) return new String[] { "a", "b", "c" };
/* 933 */     return tools;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.JBreadBoard
 * JD-Core Version:    0.6.2
 */