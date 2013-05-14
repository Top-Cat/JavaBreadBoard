/*     */ package designTools;
/*     */ 
/*     */ import jBreadBoard.TextFilter;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ public class FileOperations
/*     */ {
/*     */   public static boolean hasFileAccess()
/*     */   {
/*     */     try
/*     */     {
/*  30 */       SecurityManager sm = System.getSecurityManager();
/*  31 */       if (sm != null) {
/*  32 */         sm.checkRead("test");
/*  33 */         sm.checkWrite("test");
/*  34 */         sm.checkPropertyAccess("user.home");
/*  35 */         sm.checkPermission(new RuntimePermission("modifyThread"));
/*     */       }
/*  37 */       return true;
/*     */     } catch (SecurityException e) {
/*     */     }
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean hasClipboardAccess()
/*     */   {
/*     */     try
/*     */     {
/*  51 */       SecurityManager sm = System.getSecurityManager();
/*  52 */       if (sm != null) {
/*  53 */         sm.checkSystemClipboardAccess();
/*     */       }
/*  55 */       return true;
/*     */     } catch (SecurityException e) {
/*     */     }
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   public static File getFileSelection(String extension)
/*     */   {
/*  69 */     return getFileSelection(extension, "Choose Filename");
/*     */   }
/*     */ 
/*     */   public static File getFileSelection(String extension, String DialogTitle)
/*     */   {
/*  74 */     URL url = ClassLoader.getSystemResource("configFiles");
/*  75 */     String urlName = url.getFile().substring(1);
/*     */ 
/*  78 */     File selectedFile = null;
/*     */ 
/*  81 */     if (!hasFileAccess()) {
/*  82 */       JOptionPane.showMessageDialog(null, "Insufficient priviliges to save.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
/*     */     }
/*     */     else
/*     */     {
/*  93 */       JFileChooser saveFileChooser = new JFileChooser(urlName);
/*  94 */       saveFileChooser.setFileFilter(new TextFilter(extension));
/*     */ 
/*  96 */       int result = saveFileChooser.showDialog(null, DialogTitle);
/*     */ 
/*  98 */       if (result == 0)
/*     */       {
/* 100 */         selectedFile = saveFileChooser.getSelectedFile();
/*     */ 
/* 104 */         String filename = selectedFile.getPath();
/*     */ 
/* 106 */         if (!filename.toLowerCase().endsWith("." + extension))
/*     */         {
/* 108 */           filename = filename + "." + extension;
/*     */         }
/*     */ 
/* 111 */         selectedFile = new File(filename);
/*     */       }
/*     */     }
/*     */ 
/* 115 */     return selectedFile;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     designTools.FileOperations
 * JD-Core Version:    0.6.2
 */