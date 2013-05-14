/*     */ package designTools;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.text.NumberFormat;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JViewport;
/*     */ 
/*     */ public class ChipPropertiesPanel extends JPanel
/*     */ {
/*  19 */   JLabel lblChipText = new JLabel();
/*  20 */   JTextField txtChipText = new JTextField();
/*  21 */   JLabel lblManufacturer = new JLabel();
/*  22 */   JTextField txtManufacturer = new JTextField();
/*  23 */   JLabel lblWide = new JLabel();
/*  24 */   ButtonGroup booleanGroup = new ButtonGroup();
/*  25 */   JRadioButton radYes = new JRadioButton();
/*  26 */   JRadioButton radNo = new JRadioButton();
/*  27 */   JPanel jPanel1 = new JPanel();
/*  28 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  29 */   JPanel jPanel2 = new JPanel();
/*  30 */   JPanel jPanel3 = new JPanel();
/*  31 */   JLabel lblDescription = new JLabel();
/*  32 */   JTextArea txtDescription = new JTextArea();
/*  33 */   JScrollPane descriptionPanel = new JScrollPane();
/*  34 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  35 */   JLabel lblDelay = new JLabel();
/*  36 */   JFormattedTextField txtDelay = new JFormattedTextField(NumberFormat.getIntegerInstance());
/*     */ 
/*     */   public ChipPropertiesPanel() {
/*     */     try { jbInit();
/*     */     } catch (Exception e)
/*     */     {
/*  42 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*  46 */   private void jbInit() throws Exception { this.lblChipText.setToolTipText("Text which will appear on the chip on the breadboard");
/*  47 */     this.lblChipText.setText("Chip Label");
/*  48 */     setLayout(null);
/*  49 */     this.txtChipText.setToolTipText("Text which will appear on the chip on the breadboard");
/*  50 */     this.txtChipText.setText("Chip");
/*  51 */     this.lblManufacturer.setToolTipText("If the chip is modelled on an existing chip the manufacturer can be given here");
/*     */ 
/*  53 */     this.lblManufacturer.setText("Manufacturer");
/*  54 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*  55 */     this.txtManufacturer.setToolTipText("If the chip is modelled on an existing chip the manufacturer can be given here");
/*     */ 
/*  57 */     this.txtManufacturer.setText("Manufacturer");
/*  58 */     this.lblWide.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");
/*     */ 
/*  60 */     this.lblWide.setHorizontalAlignment(2);
/*  61 */     this.lblWide.setText("Wide Chip");
/*  62 */     this.lblWide.setBounds(new Rectangle(12, 13, 67, 15));
/*  63 */     this.radYes.setFont(new Font("Dialog", 0, 12));
/*  64 */     this.radYes.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");
/*     */ 
/*  66 */     this.radYes.setText("Yes");
/*  67 */     this.radYes.setBounds(new Rectangle(88, 10, 48, 23));
/*  68 */     this.radNo.setText("No");
/*  69 */     this.radNo.setBounds(new Rectangle(139, 10, 43, 23));
/*  70 */     this.radNo.setFont(new Font("Dialog", 0, 12));
/*  71 */     this.radNo.setToolTipText("Should the chip be represented as a standard sized chip (eg. a flip-flop) or a wide chip (eg. an EPROM)?");
/*     */ 
/*  73 */     this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
/*  74 */     this.jPanel1.setBounds(new Rectangle(8, 7, 238, 86));
/*  75 */     this.jPanel1.setLayout(this.gridBagLayout1);
/*  76 */     this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
/*  77 */     this.jPanel2.setBounds(new Rectangle(253, 7, 201, 86));
/*  78 */     this.jPanel2.setLayout(null);
/*  79 */     this.jPanel3.setBorder(BorderFactory.createEtchedBorder());
/*  80 */     this.jPanel3.setBounds(new Rectangle(463, 7, 268, 86));
/*  81 */     this.jPanel3.setLayout(this.gridBagLayout2);
/*  82 */     this.lblDescription.setToolTipText("Optional, brief description of the chip");
/*  83 */     this.lblDescription.setText("Chip Description");
/*  84 */     this.txtDescription.setBorder(null);
/*  85 */     this.txtDescription.setToolTipText("Optional, brief description of the chip");
/*  86 */     this.txtDescription.setText("");
/*  87 */     this.txtDescription.setLineWrap(true);
/*  88 */     this.txtDescription.setWrapStyleWord(true);
/*  89 */     this.lblDelay.setText("Chip Delay / ns");
/*  90 */     this.lblDelay.setBounds(new Rectangle(12, 52, 105, 15));
/*  91 */     this.txtDelay.setHorizontalAlignment(11);
/*  92 */     this.txtDelay.setValue(new Integer(50));
/*  93 */     this.txtDelay.setBounds(new Rectangle(120, 50, 60, 20));
/*  94 */     add(this.jPanel1, null);
/*  95 */     this.jPanel1.add(this.txtManufacturer, new GridBagConstraints(1, 1, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(15, 0, 10, 13), 42, 6));
/*     */ 
/*  97 */     this.jPanel1.add(this.txtChipText, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(7, 0, 0, 13), 92, 6));
/*     */ 
/*  99 */     this.jPanel1.add(this.lblChipText, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(7, 11, 7, 21), 8, 0));
/*     */ 
/* 101 */     this.jPanel1.add(this.lblManufacturer, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(18, 11, 17, 0), 14, 0));
/*     */ 
/* 103 */     this.jPanel2.add(this.lblWide, null);
/* 104 */     this.jPanel2.add(this.lblDelay, null);
/* 105 */     this.jPanel2.add(this.txtDelay, null);
/* 106 */     this.jPanel2.add(this.radNo, null);
/* 107 */     this.jPanel2.add(this.radYes, null);
/* 108 */     add(this.jPanel3, null);
/* 109 */     this.jPanel3.add(this.descriptionPanel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(6, 9, 12, 10), 0, 25));
/*     */ 
/* 111 */     this.jPanel3.add(this.lblDescription, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 9, 0, 140), 42, 0));
/*     */ 
/* 113 */     this.descriptionPanel.getViewport().add(this.txtDescription, null);
/* 114 */     add(this.jPanel2, null);
/* 115 */     this.booleanGroup.add(this.radYes);
/* 116 */     this.booleanGroup.add(this.radNo);
/* 117 */     this.radNo.setSelected(true);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.ChipPropertiesPanel
 * JD-Core Version:    0.6.2
 */