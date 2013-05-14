/*    */ package integratedCircuits.memory;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.Document;
/*    */ import javax.swing.text.PlainDocument;
/*    */ 
/*    */ public class ByteTextField extends JTextField
/*    */ {
/*    */   private Toolkit toolkit;
/*    */   private NumberFormat ByteFormat;
/*    */   private ByteNumberDocument ByteNumDoc;
/*    */   public int number;
/*    */ 
/*    */   public ByteTextField(int columns)
/*    */   {
/* 26 */     super(columns);
/* 27 */     this.toolkit = Toolkit.getDefaultToolkit();
/* 28 */     this.ByteFormat = NumberFormat.getNumberInstance(Locale.UK);
/* 29 */     this.ByteFormat.setParseIntegerOnly(false);
/*    */   }
/*    */ 
/*    */   public void setHex()
/*    */   {
/* 34 */     this.ByteNumDoc.setHex();
/*    */   }
/*    */ 
/*    */   public void setDecimal()
/*    */   {
/* 39 */     this.ByteNumDoc.setDecimal();
/*    */   }
/*    */ 
/*    */   protected Document createDefaultModel()
/*    */   {
/* 44 */     this.ByteNumDoc = new ByteNumberDocument();
/* 45 */     return this.ByteNumDoc;
/*    */   }
/*    */ 
/*    */   protected class ByteNumberDocument extends PlainDocument
/*    */   {
/* 50 */     private String type = "Hex";
/*    */ 
/*    */     protected ByteNumberDocument() {
/*    */     }
/* 54 */     public void setHex() { this.type = "Hex"; }
/*    */ 
/*    */ 
/*    */     public void setDecimal()
/*    */     {
/* 59 */       this.type = "Decimal";
/*    */     }
/*    */ 
/*    */     public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
/*    */     {
/* 64 */       char[] source = str.toCharArray();
/* 65 */       char[] result = new char[source.length];
/*    */ 
/* 67 */       int j = 0;
/*    */ 
/* 69 */       String currentText = getText(0, getLength());
/*    */ 
/* 71 */       char[] Text = currentText.toCharArray();
/*    */ 
/* 73 */       for (int i = 0; i < result.length; i++) {
/* 74 */         if (this.type == "Hex") {
/* 75 */           if ((Character.isDigit(source[i])) || ((source[i] >= 'A') && (source[i] <= 'F')) || ((source[i] >= 'a') && (source[i] <= 'f')))
/*    */           {
/* 78 */             result[(j++)] = source[i];
/*    */           } else {
/* 80 */             ByteTextField.this.toolkit.beep();
/* 81 */             System.err.println("insertString: " + source[i]);
/*    */           }
/*    */         }
/* 84 */         else if (this.type == "Decimal")
/*    */         {
/* 86 */           if (Character.isDigit(source[i])) {
/* 87 */             result[(j++)] = source[i];
/*    */           } else {
/* 89 */             ByteTextField.this.toolkit.beep();
/* 90 */             System.err.println("insertString: " + source[i]);
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 95 */       super.insertString(offs, new String(result, 0, j), a);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.ByteTextField
 * JD-Core Version:    0.6.2
 */