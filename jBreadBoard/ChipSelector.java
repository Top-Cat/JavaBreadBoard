/*     */ package jBreadBoard;
/*     */ 
/*     */ import designTools.FileOperations;
/*     */ import integratedCircuits.ttl.generic.InvalidStateException;
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import jBreadBoard.v1_10.LoadSave;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class ChipSelector extends JPanel
/*     */ {
/*  30 */   public static String defaultchip = "integratedCircuits.ttl.logic.Gen7400";
/*     */   private JBreadBoard jBreadBoard;
/*  34 */   private JList componentList = new JList();
/*  35 */   private JLabel chipname = new JLabel();
/*  36 */   private JTextArea chipdescription = new JTextArea();
/*  37 */   private JLabel chipmanufacturer = new JLabel();
/*  38 */   private JComboBox chipderivatives = new JComboBox();
/*     */   private ImageIcon chipdiagram;
/*  41 */   private String defaultDirectory = "integratedCircuits";
/*  42 */   private String currentDirectory = "integratedCircuits";
/*     */ 
/*  45 */   private Hashtable directoryClasses = new Hashtable();
/*     */ 
/*  47 */   private char fileSeparatorChar = File.separatorChar;
/*  48 */   private String fileSeparatorString = File.separator;
/*     */ 
/*  50 */   private char urlSeparatorChar = '/';
/*  51 */   private String urlSeparatorString = "/";
/*     */ 
/*  91 */   private JPanel imagepanel = new JPanel() {
/*     */     public void paintComponent(Graphics g) {
/*  93 */       super.paintComponent(g);
/*  94 */       if (ChipSelector.this.chipdiagram != null)
/*  95 */         ChipSelector.this.chipdiagram.paintIcon(this, g, (getWidth() - ChipSelector.this.chipdiagram.getIconWidth()) / 2, (getHeight() - ChipSelector.this.chipdiagram.getIconHeight()) / 2);
/*     */     }
/*  91 */   };
/*     */ 
/*     */   public static void addChipDialog(Component c, JBreadBoard j)
/*     */   {
/* 108 */     final JBreadBoard jBreadBoard = j;
/* 109 */     ChipSelector pane = new ChipSelector(j);
/* 110 */     final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(c), "Select a Chip", true);
/*     */ 
/* 112 */     dialog.getContentPane().setLayout(new BorderLayout());
/* 113 */     dialog.getContentPane().add(pane, "Center");
/*     */ 
/* 115 */     JPanel buttonPane = new JPanel();
/* 116 */     buttonPane.setLayout(new FlowLayout(1));
/* 117 */     dialog.getContentPane().add(buttonPane, "South");
/*     */ 
/* 121 */     JButton okButton = new JButton("OK");
/* 122 */     dialog.getRootPane().setDefaultButton(okButton);
/* 123 */     buttonPane.add(okButton);
/*     */ 
/* 125 */     okButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 127 */         dialog.setVisible(false);
/*     */ 
/* 130 */         ChipModel chipModel = ChipSelector.getDefaultModel();
/*     */ 
/* 133 */         if (JBreadBoard.implementsInterface(chipModel, "jBreadBoard.v1_10.LoadSave"))
/*     */         {
/* 137 */           String extension = ((LoadSave)chipModel).getFileExtension();
/*     */ 
/* 139 */           File file = FileOperations.getFileSelection(extension, "Load Config File ?");
/*     */ 
/* 142 */           if (file == null) {
/* 143 */             if (((LoadSave)chipModel).instantiateBlank())
/*     */             {
/* 145 */               Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
/* 146 */               jBreadBoard.getCircuit().BBselect.add(chip);
/*     */             }
/*     */             else
/*     */             {
/* 150 */               JOptionPane.showMessageDialog(null, "Error : need config file", "Error: ", 0);
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 157 */             String fileName = file.toString();
/* 158 */             fileName = fileName.replace('\\', '/');
/*     */             try
/*     */             {
/* 161 */               ((LoadSave)chipModel).initialiseState(fileName);
/* 162 */               Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
/* 163 */               jBreadBoard.getCircuit().BBselect.add(chip);
/*     */             }
/*     */             catch (InvalidStateException e1) {
/* 166 */               JOptionPane.showMessageDialog(null, "Error : InvalidTStateException", "Error: ", 0);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 174 */           Chip chip = new Chip(jBreadBoard.getCircuit(), chipModel);
/* 175 */           jBreadBoard.getCircuit().BBselect.add(chip);
/*     */         }
/*     */       }
/*     */     });
/* 182 */     JButton cancelButton = new JButton("Cancel");
/* 183 */     buttonPane.add(cancelButton);
/* 184 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 186 */         dialog.setVisible(false);
/*     */       }
/*     */     });
/* 190 */     dialog.setLocationRelativeTo(c);
/* 191 */     dialog.pack();
/* 192 */     dialog.setVisible(true);
/*     */   }
/*     */ 
/*     */   private void refreshDirectoryList(Direction direction, String selectedDirectory)
/*     */   {
/* 208 */     Vector listNames = new Vector();
/* 209 */     Vector directoryNames = new Vector();
/* 210 */     Vector fileNames = new Vector();
/* 211 */     Vector classNames = new Vector();
/*     */ 
/* 213 */     this.directoryClasses.clear();
/*     */     URL url;
/* 215 */     switch (direction.ordinal()) {
/*     */     case 1:
/* 217 */       url = ClassLoader.getSystemResource(this.currentDirectory);
/* 218 */       break;
/*     */     case 2:
/* 220 */       if (this.currentDirectory.equals(this.defaultDirectory))
/* 221 */         url = ClassLoader.getSystemResource(this.currentDirectory);
/*     */       else {
/* 223 */         url = ClassLoader.getSystemResource(this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf(this.urlSeparatorChar)));
/*     */       }
/* 225 */       break;
/*     */     case 3:
/* 227 */       url = ClassLoader.getSystemResource(this.currentDirectory + this.urlSeparatorChar + selectedDirectory);
/* 228 */       break;
/*     */     default:
/* 230 */       url = ClassLoader.getSystemResource(this.currentDirectory);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 237 */       String fileName = url.getFile();
/* 238 */       File directory = new File(fileName);
/*     */ 
/* 240 */       if (directory.isDirectory())
/* 241 */         switch (direction.ordinal()) {
/*     */         case 1:
/* 243 */           this.currentDirectory = this.defaultDirectory;
/* 244 */           break;
/*     */         case 2:
/* 246 */           if (!this.currentDirectory.equals(this.defaultDirectory))
/* 247 */             this.currentDirectory = this.currentDirectory.substring(0, this.currentDirectory.lastIndexOf(this.urlSeparatorChar));
/*     */           else
/* 249 */             this.currentDirectory = this.defaultDirectory;
/* 250 */           break;
/*     */         case 3:
/* 252 */           this.currentDirectory = (this.currentDirectory + this.urlSeparatorChar + selectedDirectory);
/* 253 */           break;
/*     */         default:
/* 255 */           this.currentDirectory = this.defaultDirectory;
/* 256 */           System.out.println("currentDirectory ERROR");
/*     */         }
/*     */     }
/*     */     catch (NullPointerException e1)
/*     */     {
/* 261 */       String selectedItem = (String)this.componentList.getSelectedValue();
/* 262 */       System.out.println("Directory does not exist : " + selectedItem);
/*     */     }
/*     */ 
/* 265 */     url = ClassLoader.getSystemResource(this.currentDirectory);
/* 266 */     String fileName = url.getFile();
/* 267 */     File directory = new File(fileName);
/* 268 */     String[] contents = directory.list();
/*     */ 
/* 270 */     for (int i = 0; i < contents.length; i++) {
/* 271 */       File item = new File(directory + this.fileSeparatorString + contents[i]);
/*     */ 
/* 275 */       if (item.isDirectory()) {
/* 276 */         if (!contents[i].equals("images")) {
/* 277 */           directoryNames.add(contents[i]);
/*     */         }
/*     */ 
/*     */       }
/* 281 */       else if (contents[i].endsWith(".class")) {
/* 282 */         String fullClassName = item.toString();
/* 283 */         fullClassName = fullClassName.substring(fullClassName.indexOf(this.defaultDirectory), fullClassName.length() - 6).replace(this.fileSeparatorChar, '.');
/*     */ 
/* 285 */         fileNames.add(fullClassName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 290 */     for (int i = 0; i < directoryNames.size(); i++)
/*     */     {
/* 292 */       listNames.add(directoryNames.get(i));
/*     */     }
/*     */ 
/* 295 */     if (!this.currentDirectory.equals(this.defaultDirectory))
/*     */     {
/* 297 */       for (int i = 0; i < fileNames.size(); i++) {
/* 298 */         String key = fileNames.get(i).toString().substring(fileNames.get(i).toString().lastIndexOf('.') + 1);
/*     */         try
/*     */         {
/* 301 */           this.directoryClasses.put(key, new ClassRecord(Class.forName((String)fileNames.get(i))));
/*     */         } catch (ClassNotFoundException e1) {
/* 303 */           System.out.println("ClassNotFoundException Error ");
/*     */         }
/*     */       }
/*     */ 
/* 307 */       for (int i = 0; i < fileNames.size(); i++) {
/*     */         try {
/* 309 */           Class c = Class.forName((String)fileNames.get(i));
/*     */ 
/* 311 */           for (int j = 0; j < fileNames.size(); j++)
/*     */             try {
/* 313 */               Object o = Class.forName((String)fileNames.get(j)).newInstance();
/*     */ 
/* 315 */               if (c.isInstance(o)) {
/* 316 */                 String classKey = fileNames.get(i).toString().substring(fileNames.get(i).toString().lastIndexOf('.') + 1);
/* 317 */                 String objectKey = fileNames.get(j).toString().substring(fileNames.get(j).toString().lastIndexOf('.') + 1);
/*     */ 
/* 320 */                 ((ClassRecord)this.directoryClasses.get(classKey)).addClass(o.getClass());
/* 321 */                 ((ClassRecord)this.directoryClasses.get(objectKey)).incReferencedCount();
/*     */               }
/*     */ 
/*     */             }
/*     */             catch (InstantiationException e1)
/*     */             {
/* 327 */               System.out.println("InstantiationException Error " + e1); } catch (IllegalAccessException e1) {
/* 328 */               System.out.println("IllegalAccessException Error " + e1);
/*     */             }
/*     */         } catch (ClassNotFoundException e1) {
/* 331 */           System.out.println("ClassNotFoundException Error " + e1);
/*     */         }
/*     */       }
/* 334 */       Enumeration keys = this.directoryClasses.keys();
/*     */ 
/* 336 */       while (keys.hasMoreElements()) {
/* 337 */         String key = (String)keys.nextElement();
/* 338 */         ClassRecord classSet = (ClassRecord)this.directoryClasses.get(key);
/*     */ 
/* 346 */         if (classSet.getReferencedCount() == 1)
/*     */         {
/* 354 */           classNames.add(key);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 362 */     Collections.sort(classNames);
/* 363 */     listNames.addAll(classNames);
/*     */ 
/* 365 */     this.componentList.setForeground(Color.blue);
/* 366 */     this.componentList.setListData(listNames);
/*     */   }
/*     */ 
/*     */   public static ChipModel getDefaultModel()
/*     */   {
/* 377 */     ChipModel model = JBreadBoard.getChipModel(defaultchip);
/* 378 */     return model;
/*     */   }
/*     */ 
/*     */   private void updateChipDetails(ChipModel ic)
/*     */   {
/* 386 */     this.chipdescription.setText(ic.getDescription());
/* 387 */     this.chipmanufacturer.setText(ic.getManufacturer());
/* 388 */     this.chipdiagram = this.jBreadBoard.getImageIcon("build" + this.urlSeparatorString + "classes" + this.urlSeparatorString + this.currentDirectory + this.urlSeparatorString + ic.getDiagram());
/* 389 */     this.imagepanel.repaint();
/*     */   }
/*     */ 
/*     */   private void updateSelectedChip(String selectedItem)
/*     */   {
/*     */     try
/*     */     {
/* 424 */       if (!selectedItem.isEmpty())
/*     */       {
/* 426 */         ClassRecord classSet = (ClassRecord)this.directoryClasses.get(selectedItem);
/*     */ 
/* 428 */         String className = classSet.getBaseClass().toString().substring(classSet.getBaseClass().toString().lastIndexOf(' ') + 1);
/*     */ 
/* 430 */         String componentName = className.substring(className.lastIndexOf('.') + 1);
/*     */         try
/*     */         {
/* 434 */           ChipModel ic = (ChipModel)Class.forName(className).newInstance();
/* 435 */           updateChipDetails(ic);
/*     */ 
/* 438 */           this.chipderivatives.removeAllItems();
/* 439 */           this.chipderivatives.addItem(componentName);
/* 440 */           Class[] classArray = classSet.getClasses();
/* 441 */           for (int i = 0; i < classArray.length; i++)
/*     */           {
/* 443 */             if (!componentName.equals(classArray[i].getSimpleName()))
/* 444 */               this.chipderivatives.addItem(classArray[i].getSimpleName());
/*     */           }
/*     */         } catch (InstantiationException e1) {
/* 447 */           System.out.println("InstantiationException Error "); } catch (IllegalAccessException e1) {
/* 448 */           System.out.println("IllegalAccessException Error "); } catch (ClassNotFoundException e1) {
/* 449 */           System.out.println("ClassNotFoundException Error ");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (NullPointerException e1)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public ChipSelector(JBreadBoard j)
/*     */   {
/* 514 */     this.jBreadBoard = j;
/*     */ 
/* 516 */     refreshDirectoryList(Direction.CURRENT, "");
/*     */ 
/* 520 */     GridBagLayout gridbag = new GridBagLayout();
/* 521 */     GridBagConstraints c = new GridBagConstraints();
/*     */ 
/* 524 */     setLayout(gridbag);
/* 525 */     setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*     */ 
/* 527 */     this.chipname.setForeground(Color.black);
/*     */ 
/* 529 */     this.chipmanufacturer.setForeground(Color.black);
/* 530 */     this.chipmanufacturer.setBackground(Color.white);
/* 531 */     this.chipmanufacturer.setOpaque(true);
/* 532 */     this.chipmanufacturer.setBorder(BorderFactory.createEtchedBorder());
/*     */ 
/* 534 */     this.chipdescription.setColumns(20);
/* 535 */     this.chipdescription.setLineWrap(true);
/* 536 */     this.chipdescription.setWrapStyleWord(true);
/* 537 */     this.chipdescription.setEditable(false);
/* 538 */     this.chipdescription.setForeground(Color.black);
/* 539 */     this.chipdescription.setBackground(Color.white);
/* 540 */     this.chipdescription.setOpaque(true);
/* 541 */     this.chipdescription.setBorder(BorderFactory.createEtchedBorder());
/*     */ 
/* 543 */     this.imagepanel.setBackground(Color.white);
/* 544 */     this.imagepanel.setOpaque(true);
/* 545 */     this.imagepanel.setPreferredSize(new Dimension(128, 128));
/* 546 */     this.imagepanel.setMinimumSize(new Dimension(128, 128));
/* 547 */     this.imagepanel.setMaximumSize(new Dimension(128, 128));
/* 548 */     this.imagepanel.setSize(new Dimension(128, 128));
/* 549 */     this.imagepanel.setBorder(BorderFactory.createEtchedBorder());
/*     */ 
/* 551 */     c.gridx = 0;
/* 552 */     c.gridy = 0;
/* 553 */     c.gridwidth = 2;
/* 554 */     c.gridheight = 9;
/* 555 */     c.weighty = 2.0D;
/* 556 */     c.weightx = 1.0D;
/* 557 */     c.fill = 1;
/* 558 */     JScrollPane scrolllist = new JScrollPane(this.componentList);
/* 559 */     this.componentList.addMouseListener(new ChipSelectorClicked());
/* 560 */     this.componentList.addListSelectionListener(new ChipSelectorUpdate());
/* 561 */     gridbag.setConstraints(scrolllist, c);
/* 562 */     add(scrolllist);
/*     */ 
/* 564 */     c.gridx = 2;
/* 565 */     c.gridwidth = 3;
/* 566 */     c.gridheight = 1;
/* 567 */     c.weighty = 0.0D;
/* 568 */     c.weightx = 0.0D;
/* 569 */     c.fill = 0;
/* 570 */     gridbag.setConstraints(this.chipname, c);
/* 571 */     add(this.chipname);
/*     */ 
/* 573 */     c.gridy = 2;
/* 574 */     c.gridwidth = 1;
/* 575 */     c.anchor = 13;
/* 576 */     JLabel jlabel = new JLabel("Derivatives: ");
/* 577 */     jlabel.setForeground(Color.black);
/* 578 */     gridbag.setConstraints(jlabel, c);
/* 579 */     add(jlabel);
/*     */ 
/* 581 */     c.gridx = 3;
/* 582 */     c.anchor = 17;
/* 583 */     gridbag.setConstraints(this.chipderivatives, c);
/* 584 */     add(this.chipderivatives);
/* 585 */     this.chipderivatives.addActionListener(new ChipDerivativesAction());
/*     */ 
/* 587 */     c.gridy = 3;
/* 588 */     c.gridx = 2;
/* 589 */     c.insets = new Insets(2, 1, 2, 1);
/* 590 */     c.anchor = 13;
/* 591 */     jlabel = new JLabel("Manufacturer: ");
/* 592 */     jlabel.setForeground(Color.black);
/* 593 */     gridbag.setConstraints(jlabel, c);
/* 594 */     add(jlabel);
/*     */ 
/* 596 */     c.gridx = 3;
/* 597 */     c.gridwidth = 2;
/* 598 */     c.fill = 2;
/* 599 */     c.anchor = 17;
/* 600 */     gridbag.setConstraints(this.chipmanufacturer, c);
/* 601 */     add(this.chipmanufacturer);
/*     */ 
/* 603 */     c.gridy = 4;
/* 604 */     c.gridx = 2;
/* 605 */     c.gridwidth = 1;
/* 606 */     c.fill = 2;
/* 607 */     c.anchor = 13;
/* 608 */     jlabel = new JLabel("Description: ");
/* 609 */     jlabel.setForeground(Color.black);
/* 610 */     gridbag.setConstraints(jlabel, c);
/* 611 */     add(jlabel);
/*     */ 
/* 613 */     c.gridx = 3;
/* 614 */     c.gridwidth = 2;
/* 615 */     c.gridheight = 3;
/* 616 */     c.anchor = 17;
/* 617 */     gridbag.setConstraints(this.chipdescription, c);
/* 618 */     add(this.chipdescription);
/*     */ 
/* 620 */     c.gridx = 2;
/* 621 */     c.gridy = 7;
/* 622 */     c.gridwidth = 1;
/* 623 */     c.gridheight = 1;
/* 624 */     c.fill = 0;
/* 625 */     c.anchor = 13;
/* 626 */     jlabel = new JLabel("Pin Diagram: ");
/* 627 */     jlabel.setForeground(Color.black);
/* 628 */     gridbag.setConstraints(jlabel, c);
/* 629 */     add(jlabel);
/*     */ 
/* 631 */     c.gridx = 2;
/* 632 */     c.gridy = 8;
/* 633 */     c.gridwidth = 3;
/* 634 */     c.fill = 1;
/* 635 */     c.anchor = 11;
/* 636 */     gridbag.setConstraints(this.imagepanel, c);
/* 637 */     add(this.imagepanel);
/*     */ 
/* 639 */     setPreferredSize(new Dimension(500, 330));
/*     */   }
/*     */ 
/*     */   private class ChipSelectorClicked
/*     */     implements MouseListener
/*     */   {
/*     */     private ChipSelectorClicked()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 479 */       int numberOfClicks = e.getClickCount();
/* 480 */       int buttonClicked = e.getButton();
/*     */ 
/* 482 */       String selectedItem = (String)ChipSelector.this.componentList.getSelectedValue();
/*     */ 
/* 484 */       if ((numberOfClicks == 1) && 
/* 485 */         (buttonClicked == 1)) {
/* 486 */         ChipSelector.this.updateSelectedChip(selectedItem);
/*     */       }
/*     */ 
/* 490 */       if (numberOfClicks == 2)
/* 491 */         if (buttonClicked == 1) {
/* 492 */           ChipSelector.this.refreshDirectoryList(ChipSelector.Direction.DOWN, selectedItem);
/*     */         }
/*     */         else
/* 495 */           ChipSelector.this.refreshDirectoryList(ChipSelector.Direction.UP, selectedItem);
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ChipSelectorUpdate
/*     */     implements ListSelectionListener
/*     */   {
/*     */     private ChipSelectorUpdate()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void valueChanged(ListSelectionEvent e)
/*     */     {
/* 466 */       String selectedItem = (String)ChipSelector.this.componentList.getSelectedValue();
/*     */ 
/* 468 */       ChipSelector.this.updateSelectedChip(selectedItem);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ChipDerivativesAction
/*     */     implements ActionListener
/*     */   {
/*     */     private ChipDerivativesAction()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 398 */       JComboBox cb = (JComboBox)e.getSource();
/* 399 */       String name = (String)cb.getSelectedItem();
/*     */ 
/* 401 */       if (name != null) {
/* 402 */         ChipSelector.ClassRecord classSet = (ChipSelector.ClassRecord)ChipSelector.this.directoryClasses.get(name);
/* 403 */         String className = classSet.getBaseClass().toString().substring(classSet.getBaseClass().toString().lastIndexOf(' ') + 1);
/*     */         try
/*     */         {
/* 406 */           ChipModel ic = (ChipModel)Class.forName(className).newInstance();
/* 407 */           ChipSelector.defaultchip = className;
/*     */         }
/*     */         catch (InstantiationException e1) {
/* 410 */           System.out.println("InstantiationException Error "); } catch (IllegalAccessException e1) {
/* 411 */           System.out.println("IllegalAccessException Error "); } catch (ClassNotFoundException e1) {
/* 412 */           System.out.println("ClassNotFoundException Error ");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ClassRecord
/*     */   {
/*     */     private HashSet<Class> implementsClass;
/*     */     private int referencedCount;
/*     */     private Class baseClass;
/*     */ 
/*     */     ClassRecord(Class base)
/*     */     {
/*  69 */       this.implementsClass = new HashSet();
/*  70 */       this.referencedCount = 0;
/*  71 */       this.baseClass = base;
/*     */     }
/*     */     public void addClass(Class c) {
/*  74 */       this.implementsClass.add(c);
/*     */     }
/*  76 */     public Class[] getClasses() { return (Class[])this.implementsClass.toArray(new Class[this.implementsClass.size()]); } 
/*     */     public Class getBaseClass() {
/*  78 */       return this.baseClass;
/*     */     }
/*  80 */     public int getSize() { return this.implementsClass.size(); } 
/*     */     public void incReferencedCount() {
/*  82 */       this.referencedCount += 1;
/*     */     }
/*  84 */     public int getReferencedCount() { return this.referencedCount; }
/*     */ 
/*     */   }
/*     */ 
/*     */   private static enum Direction
/*     */   {
/*  54 */     UP, 
/*  55 */     DOWN, 
/*  56 */     CURRENT;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.ChipSelector
 * JD-Core Version:    0.6.2
 */