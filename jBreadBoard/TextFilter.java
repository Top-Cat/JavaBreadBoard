/*    */ package jBreadBoard;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class TextFilter extends FileFilter
/*    */ {
/* 13 */   private String extension = new String("");
/*    */ 
/*    */   public TextFilter() {
/*    */   }
/*    */   public TextFilter(String extension) {
/* 18 */     this.extension = extension;
/*    */   }
/*    */ 
/*    */   public boolean accept(File f) {
/* 22 */     return (f.getName().toLowerCase().endsWith("." + this.extension)) || (f.isDirectory());
/*    */   }
/*    */   public String getDescription() {
/* 25 */     return this.extension + " Files";
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.TextFilter
 * JD-Core Version:    0.6.2
 */