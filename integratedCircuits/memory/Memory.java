/*     */ package integratedCircuits.memory;
/*     */ 
/*     */ import integratedCircuits.InputOutputPin;
/*     */ import integratedCircuits.InputPin;
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.NotConnectedPin;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import integratedCircuits.ttl.generic.InvalidStateException;
/*     */ import jBreadBoard.v1_00.ChipModel;
/*     */ import jBreadBoard.v1_10.DblClick;
/*     */ import jBreadBoard.v1_10.LoadSave;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Scanner;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public abstract class Memory extends IntegratedCircuit
/*     */   implements ChipModel, DblClick, LoadSave
/*     */ {
/*  24 */   protected int cntlBits = 0;
/*  25 */   protected int addrBits = 8;
/*  26 */   protected int dataBits = 8;
/*     */   protected MemSpace memory;
/*     */   protected DataEntryFrame frame;
/*     */   private String filename;
/*  33 */   private String chipExtension = "mem";
/*  34 */   private boolean chipInstantiateBlank = true;
/*     */ 
/*     */   protected void initialise()
/*     */   {
/*  50 */     this.memory = new MemSpace((int)Math.pow(2.0D, this.addrBits));
/*     */ 
/*  52 */     for (int i = 0; i < this.addrBits; i++) {
/*  53 */       String name = "A" + i;
/*  54 */       this.pins.add(new InputPin(this.cntlBits + 1 + i, name));
/*     */     }
/*     */ 
/*  58 */     if (this.dataBits - (this.addrBits + this.cntlBits) == 0)
/*     */     {
/*  60 */       this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1, "GND"));
/*  61 */       this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
/*     */     }
/*  67 */     else if (this.dataBits - (this.addrBits + this.cntlBits) > 0)
/*     */     {
/*  69 */       for (int i = 0; i < this.dataBits - (this.addrBits + this.cntlBits); i++) {
/*  70 */         this.pins.add(new NotConnectedPin(i + (this.addrBits + this.cntlBits + 1), "NC"));
/*     */       }
/*     */ 
/*  73 */       for (int i = 0; i < this.dataBits; i++) {
/*  74 */         String name = "D" + i;
/*  75 */         this.pins.add(new InputOutputPin(this.addrBits + (this.dataBits - (this.addrBits + this.cntlBits)) + this.cntlBits + 2 + i, name));
/*     */       }
/*     */ 
/*  79 */       this.pins.add(new PowerPin(this.addrBits + (this.dataBits - (this.addrBits + this.cntlBits)) + this.cntlBits + 1, "GND"));
/*  80 */       this.pins.add(new PowerPin(this.addrBits + (this.dataBits - (this.addrBits + this.cntlBits)) + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
/*     */     }
/*     */     else
/*     */     {
/*  87 */       for (int i = 0; i < this.dataBits; i++) {
/*  88 */         String name = "D" + i;
/*  89 */         this.pins.add(new InputOutputPin(this.addrBits + this.cntlBits + 2 + i, name));
/*     */       }
/*     */ 
/*  93 */       for (int i = 0; i < this.addrBits + this.cntlBits - this.dataBits; i++) {
/*  94 */         this.pins.add(new NotConnectedPin(i + (this.addrBits + this.dataBits + this.cntlBits + 2), "NC"));
/*     */       }
/*     */ 
/*  99 */       this.pins.add(new PowerPin(this.addrBits + this.cntlBits + 1, "GND"));
/* 100 */       this.pins.add(new PowerPin(this.addrBits + (this.addrBits + this.cntlBits - this.dataBits) + this.cntlBits + 1 + this.dataBits + 1, "VCC"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initialiseState(String filename)
/*     */     throws InvalidStateException
/*     */   {
/* 109 */     this.filename = filename;
/*     */ 
/* 112 */     if ((this.filename.isEmpty()) || (this.filename.equalsIgnoreCase("null"))) {
/* 113 */       System.out.println("NULL File Name");
/*     */     }
/*     */     else {
/* 116 */       String newFilename = updateConfigFilePath(this.filename);
/*     */       try {
/* 118 */         System.out.println("Open File " + newFilename);
/* 119 */         FileInputStream istream = new FileInputStream(newFilename);
/* 120 */         if (istream == null) {
/* 121 */           System.out.println("Invalid File");
/*     */         }
/*     */         else {
/* 124 */           Scanner fileScanner = new Scanner(istream);
/*     */ 
/* 126 */           while (fileScanner.hasNextLine()) {
/* 127 */             String line = fileScanner.nextLine();
/*     */ 
/* 129 */             Scanner lineScanner = new Scanner(line);
/*     */             try
/*     */             {
/* 132 */               if (lineScanner.hasNext(Pattern.compile("^[0123456789].*")))
/*     */               {
/* 136 */                 lineScanner.useDelimiter(",");
/* 137 */                 int count = 0;
/* 138 */                 int data = 0;
/* 139 */                 while (lineScanner.hasNext())
/*     */                 {
/* 141 */                   String value = lineScanner.next();
/* 142 */                   if (value != null)
/*     */                   {
/* 144 */                     data = Integer.valueOf(value).intValue();
/* 145 */                     this.memory.setDatum(count, data);
/* 146 */                     count++;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             } catch (NoSuchElementException e1) {
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (FileNotFoundException e1) {
/* 156 */         System.out.print(e1);
/* 157 */         throw new InvalidStateException("No File found exit");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void updateGate() throws InvalidPinException;
/*     */ 
/*     */   public void reset() {
/*     */   }
/*     */ 
/*     */   public void simulate() {
/*     */     try {
/* 169 */       if (isPowered()) {
/* 170 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 174 */         for (Pin pin : this.pins)
/* 175 */           if (isPinDriven(pin.getPinName()))
/* 176 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1) {
/* 180 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFileName(String file) {
/* 185 */     this.filename = file;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 191 */     return '"' + this.filename + '"';
/*     */   }
/*     */ 
/*     */   public String getFileExtension() {
/* 195 */     return this.chipExtension;
/*     */   }
/*     */ 
/*     */   public boolean instantiateBlank() {
/* 199 */     return this.chipInstantiateBlank;
/*     */   }
/*     */ 
/*     */   public void DblClicked() {
/* 203 */     this.frame = new DataEntryFrame(this.memory, this);
/* 204 */     this.frame.setVisible(true);
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.Memory
 * JD-Core Version:    0.6.2
 */