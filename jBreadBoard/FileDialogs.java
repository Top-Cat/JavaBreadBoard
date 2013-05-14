/*     */ package jBreadBoard;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
import java.awt.FileDialog;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringReader;
/*     */ import java.net.URL;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ 
/*     */ public class FileDialogs
/*     */ {
/*     */   private static JFileChooser fc;
/*     */ 
/*     */   private static boolean hasFileAccess()
/*     */   {
/*     */     try
/*     */     {
/*  34 */       SecurityManager sm = System.getSecurityManager();
/*  35 */       if (sm != null) {
/*  36 */         sm.checkRead("test");
/*  37 */         sm.checkWrite("test");
/*  38 */         sm.checkPropertyAccess("user.home");
/*  39 */         sm.checkPermission(new RuntimePermission("modifyThread"));
/*     */       }
/*  41 */       return true; } catch (SecurityException e) {
/*     */     }
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean hasClipboardAccess()
/*     */   {
/*     */     try
/*     */     {
/*  52 */       SecurityManager sm = System.getSecurityManager();
/*  53 */       if (sm != null) {
/*  54 */         sm.checkSystemClipboardAccess();
/*     */       }
/*  56 */       return true; } catch (SecurityException e) {
/*     */     }
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   public static String save(Component parent, String filestring)
/*     */   {
/*  71 */     if (hasFileAccess())
/*     */     {
/*  74 */       URL url = ClassLoader.getSystemResource("circuits");
/*  75 */       String urlName = url.getFile().substring(1);
/*     */ 
/*  78 */       if (fc == null) fc = new JFileChooser(urlName);
/*  79 */       fc.setFileFilter(new TextFilter("cir"));
/*     */ 
/*  81 */       int returnVal = fc.showSaveDialog(parent);
/*     */ 
/*  83 */       if (returnVal == 0) {
/*     */         try {
/*  85 */           File file = fc.getSelectedFile();
/*  86 */           FileOutputStream fw = new FileOutputStream(file);
/*  87 */           OutputStreamWriter ow = new OutputStreamWriter(fw, "ISO-8859-1");
/*  88 */           BufferedWriter bw = new BufferedWriter(ow);
/*     */ 
/*  90 */           StringReader sr = new StringReader(filestring);
/*  91 */           BufferedReader br = new BufferedReader(sr);
/*     */           String line;
/*  95 */           while ((line = br.readLine()) != null) {
/*  96 */             bw.write(line);
/*  97 */             bw.newLine();
/*     */           }
/*     */ 
/* 100 */           bw.close();
/* 101 */           fw.close();
/* 102 */           ow.close();
/*     */ 
/* 104 */           sr.close();
/* 105 */           br.close();
/*     */ 
/* 107 */           return file.getCanonicalPath();
/*     */         }
/*     */         catch (IOException e) {
/* 110 */           JOptionPane.showMessageDialog(parent, "Error Saving File.\n" + e.getMessage(), "Error FileDialogs.001", 0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 117 */     else if (hasClipboardAccess())
/*     */     {
/* 119 */       final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(parent), "Save", true);
/*     */ 
/* 121 */       dialog.getContentPane().setLayout(new BorderLayout());
/*     */ 
/* 123 */       JPanel pane = new JPanel(new BorderLayout());
/* 124 */       JLabel label = new JLabel("To Save copy text into a file");
/*     */ 
/* 126 */       label.setForeground(Color.black);
/*     */ 
/* 128 */       final JTextArea savearea = new JTextArea(filestring);
/* 129 */       savearea.setEditable(false);
/* 130 */       savearea.selectAll();
/* 131 */       savearea.setPreferredSize(new Dimension(200, 300));
/*     */ 
/* 134 */       JScrollPane scroller = new JScrollPane(savearea);
/* 135 */       pane.add(scroller, "Center");
/*     */ 
/* 137 */       dialog.getContentPane().add(label, "North");
/* 138 */       dialog.getContentPane().add(pane, "Center");
/*     */ 
/* 140 */       JPanel buttonPane = new JPanel();
/* 141 */       buttonPane.setLayout(new FlowLayout(1));
/* 142 */       dialog.getContentPane().add(buttonPane, "South");
/*     */ 
/* 145 */       JButton copyButton = new JButton("Copy");
/* 146 */       buttonPane.add(copyButton);
/* 147 */       copyButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 149 */           savearea.selectAll();
/* 150 */           savearea.copy();
/*     */         }
/*     */       });
/* 156 */       JButton okButton = new JButton("Close");
/* 157 */       dialog.getRootPane().setDefaultButton(okButton);
/* 158 */       buttonPane.add(okButton);
/* 159 */       okButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e) {
/* 162 */           dialog.setVisible(false);
/*     */         }
/*     */       });
/* 166 */       dialog.setLocationRelativeTo(parent);
/* 167 */       dialog.pack();
/*     */ 
/* 169 */       dialog.setVisible(false);
/*     */     }
/*     */     else {
/* 172 */       JOptionPane.showMessageDialog(parent, "Insufficient privilages to save.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
/*     */     }
/*     */ 
/* 178 */     return "";
/*     */   }
/*     */ 
/*     */   public static String load(Component parent)
/*     */   {
/* 190 */     if (hasFileAccess())
/*     */     {
/* 192 */       URL url = ClassLoader.getSystemResource("circuits");
/* 193 */       String urlName = url.getFile().substring(1);
/*     */ 
/* 197 */       if (fc == null) fc = new JFileChooser(urlName);
/* 198 */       fc.setFileFilter(new TextFilter("cir"));
/*     */ 
/* 200 */       int returnVal = fc.showOpenDialog(parent);
/*     */ 
/* 202 */       if (returnVal == 0) {
/*     */         try
/*     */         {
/* 205 */           File file = fc.getSelectedFile();
/*     */ 
/* 209 */           FileInputStream fr = new FileInputStream(file);
/* 210 */           InputStreamReader ir = new InputStreamReader(fr, "ISO-8859-1");
/* 211 */           BufferedReader in = new BufferedReader(ir);
/*     */ 
/* 217 */           String input = in.readLine();
/*     */           String line;
/* 220 */           while ((line = in.readLine()) != null) {
/* 221 */             input = input + "\n" + line;
/*     */           }
/*     */ 
/* 224 */           in.close();
/* 225 */           fr.close();
/*     */ 
/* 227 */           return input;
/*     */         } catch (FileNotFoundException e) {
/* 229 */           JOptionPane.showMessageDialog(parent, "Error Loading File.\n" + e.getMessage(), "Error  FileDialogs.002", 0);
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 235 */           JOptionPane.showMessageDialog(parent, "Error Loading File.\n" + e.getMessage(), "Error  FileDialogs.003", 0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 243 */       return null;
/* 244 */     }if (hasClipboardAccess())
/*     */     {
/* 246 */       final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(parent), "Load", true);
/*     */ 
/* 248 */       dialog.getContentPane().setLayout(new BorderLayout());
/*     */ 
/* 250 */       JPanel pane = new JPanel(new BorderLayout());
/* 251 */       JLabel label = new JLabel("To Load paste contents of file here");
/*     */ 
/* 253 */       label.setForeground(Color.black);
/*     */ 
/* 255 */       final JTextArea loadarea = new JTextArea();
/* 256 */       loadarea.setEditable(true);
/* 257 */       loadarea.setPreferredSize(new Dimension(200, 300));
/*     */ 
/* 259 */       JScrollPane scroller = new JScrollPane(loadarea);
/* 260 */       pane.add(scroller, "Center");
/*     */ 
/* 262 */       dialog.getContentPane().add(label, "North");
/* 263 */       dialog.getContentPane().add(pane, "Center");
/*     */ 
/* 265 */       JPanel buttonPane = new JPanel();
/* 266 */       buttonPane.setLayout(new FlowLayout(1));
/* 267 */       dialog.getContentPane().add(buttonPane, "South");
/*     */ 
/* 270 */       JButton pasteButton = new JButton("Paste");
/* 271 */       buttonPane.add(pasteButton);
/* 272 */       pasteButton.addActionListener(new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/*     */           loadarea.selectAll();
/* 275 */           loadarea.replaceSelection("");
/* 276 */           loadarea.paste();
/*     */         }
/*     */       });
/* 281 */       JButton okButton = new JButton("OK");
/* 282 */       dialog.getRootPane().setDefaultButton(okButton);
/* 283 */       buttonPane.add(okButton);
/* 284 */       okButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e) {
/* 287 */           dialog.setVisible(false);
/*     */         }
/*     */       });
/* 291 */       JButton cancelButton = new JButton("Cancel");
/* 292 */       dialog.getRootPane().setDefaultButton(cancelButton);
/* 293 */       buttonPane.add(cancelButton);
/* 294 */       cancelButton.addActionListener(new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent e) {
/* 297 */           dialog.setVisible(false);
/* 298 */           loadarea.selectAll();
/* 299 */           loadarea.replaceSelection("");
/*     */         }
/*     */       });
/* 303 */       dialog.setLocationRelativeTo(parent);
/* 304 */       dialog.pack();
/*     */ 
/* 306 */       dialog.setVisible(true);
/*     */ 
/* 308 */       loadarea.selectAll();
/* 309 */       return loadarea.getSelectedText();
/*     */     }
/*     */ 
/* 312 */     JOptionPane.showMessageDialog(parent, "Insufficient privilages to load.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
/*     */ 
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   public static String selectDir(Component parent)
/*     */   {
/* 323 */     if (hasFileAccess())
/*     */     {
/* 325 */       if (fc == null) fc = new JFileChooser();
/* 326 */       fc.setFileSelectionMode(1);
/*     */ 
/* 328 */       int returnVal = fc.showOpenDialog(parent);
/*     */ 
/* 330 */       if (returnVal == 0)
/*     */       {
/* 332 */         return fc.getSelectedFile().getName();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 337 */       JOptionPane.showMessageDialog(parent, "Insufficient privilages to load.\nSee Help-Loading and Saving or the Webpage for details of how to enable loading and saving.", "Loading and Saving Disabled", 2);
/*     */     }
/*     */ 
/* 343 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.FileDialogs
 * JD-Core Version:    0.6.2
 */