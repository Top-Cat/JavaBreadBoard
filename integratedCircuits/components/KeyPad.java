/*     */ package integratedCircuits.components;
/*     */ 
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class KeyPad extends JPanel
/*     */   implements ActionListener
/*     */ {
/*  19 */   private int Value = 0;
/*     */   private ChipModel chipModel;
/*     */ 
/*     */   public KeyPad(ChipModel model)
/*     */   {
/*  28 */     this.chipModel = model;
/*     */ 
/*  32 */     setLayout(new GridLayout(4, 4));
/*     */ 
/*  34 */     JButton b0 = new JButton("0");
/*  35 */     b0.addActionListener(this);
/*  36 */     add(b0);
/*     */ 
/*  38 */     JButton b1 = new JButton("1");
/*  39 */     b1.addActionListener(this);
/*  40 */     add(b1);
/*     */ 
/*  42 */     JButton b2 = new JButton("2");
/*  43 */     b2.addActionListener(this);
/*  44 */     add(b2);
/*     */ 
/*  46 */     JButton b3 = new JButton("3");
/*  47 */     b3.addActionListener(this);
/*  48 */     add(b3);
/*     */ 
/*  50 */     JButton b4 = new JButton("4");
/*  51 */     b4.addActionListener(this);
/*  52 */     add(b4);
/*     */ 
/*  54 */     JButton b5 = new JButton("5");
/*  55 */     b5.addActionListener(this);
/*  56 */     add(b5);
/*     */ 
/*  58 */     JButton b6 = new JButton("6");
/*  59 */     b6.addActionListener(this);
/*  60 */     add(b6);
/*     */ 
/*  62 */     JButton b7 = new JButton("7");
/*  63 */     b7.addActionListener(this);
/*  64 */     add(b7);
/*     */ 
/*  66 */     JButton b8 = new JButton("8");
/*  67 */     b8.addActionListener(this);
/*  68 */     add(b8);
/*     */ 
/*  70 */     JButton b9 = new JButton("9");
/*  71 */     b9.addActionListener(this);
/*  72 */     add(b9);
/*     */ 
/*  74 */     JButton bA = new JButton("A");
/*  75 */     bA.addActionListener(this);
/*  76 */     add(bA);
/*     */ 
/*  78 */     JButton bB = new JButton("B");
/*  79 */     bB.addActionListener(this);
/*  80 */     add(bB);
/*     */ 
/*  82 */     JButton bC = new JButton("C");
/*  83 */     bC.addActionListener(this);
/*  84 */     add(bC);
/*     */ 
/*  86 */     JButton bD = new JButton("D");
/*  87 */     bD.addActionListener(this);
/*  88 */     add(bD);
/*     */ 
/*  90 */     JButton bE = new JButton("E");
/*  91 */     bE.addActionListener(this);
/*  92 */     add(bE);
/*     */ 
/*  94 */     JButton bF = new JButton("F");
/*  95 */     bF.addActionListener(this);
/*  96 */     add(bF);
/*     */   }
/*     */ 
/*     */   public int getValue()
/*     */   {
/* 105 */     return this.Value;
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent evt)
/*     */   {
/* 112 */     switch (evt.getActionCommand().charAt(0)) { case '0':
/* 113 */       this.Value = 0; break;
/*     */     case '1':
/* 114 */       this.Value = 1; break;
/*     */     case '2':
/* 115 */       this.Value = 2; break;
/*     */     case '3':
/* 116 */       this.Value = 3; break;
/*     */     case '4':
/* 117 */       this.Value = 4; break;
/*     */     case '5':
/* 118 */       this.Value = 5; break;
/*     */     case '6':
/* 119 */       this.Value = 6; break;
/*     */     case '7':
/* 120 */       this.Value = 7; break;
/*     */     case '8':
/* 121 */       this.Value = 8; break;
/*     */     case '9':
/* 122 */       this.Value = 9; break;
/*     */     case 'A':
/* 123 */       this.Value = 10; break;
/*     */     case 'B':
/* 124 */       this.Value = 11; break;
/*     */     case 'C':
/* 125 */       this.Value = 12; break;
/*     */     case 'D':
/* 126 */       this.Value = 13; break;
/*     */     case 'E':
/* 127 */       this.Value = 14; break;
/*     */     case 'F':
/* 128 */       this.Value = 15; break;
/*     */     case ':':
/*     */     case ';':
/*     */     case '<':
/*     */     case '=':
/*     */     case '>':
/*     */     case '?':
/*     */     case '@':
/*     */     default:
/* 129 */       this.Value = 0;
/*     */     }
/* 131 */     this.chipModel.simulate();
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.components.KeyPad
 * JD-Core Version:    0.6.2
 */