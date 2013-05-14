/*     */ package integratedCircuits.ttl.generic;
/*     */ 
/*     */ import integratedCircuits.InputPin;
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.NotConnectedPin;
/*     */ import integratedCircuits.OutputPin;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import jBreadBoard.v1_10.LoadSave;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Scanner;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class Logic extends IntegratedCircuit
/*     */   implements LoadSave
/*     */ {
/*  21 */   private String chipText = "";
/*  22 */   private String chipDescription = "";
/*  23 */   private String chipManufacturer = "";
/*  24 */   private String chipFilename = "";
/*     */ 
/*  26 */   private String chipExtension = "chp";
/*  27 */   private boolean chipInstantiateBlank = false;
/*     */ 
/*  29 */   private int numberOfAddressBits = 0;
/*  30 */   private int numberOfDataBits = 0;
/*  31 */   private int numberOfPins = 0;
/*     */ 
/*  33 */   private Boolean chipWide = Boolean.valueOf(false);
/*  34 */   private List<String> chipInputs = new ArrayList();
/*  35 */   private List<String> chipOutputs = new ArrayList();
/*     */   private List<LogicTableElement> lookUpTable;
/*  38 */   private Boolean mode = Boolean.valueOf(true);
/*  39 */   private int lookUpTableRows = 0;
/*  40 */   private int pinOffset = 0;
/*     */ 
/*     */   public Logic()
/*     */   {
/*  48 */     this.description = "General purpose logic chip configured via .chp file";
/*  49 */     this.manufacturer = "Generic";
/*  50 */     this.diagram = ("images" + File.separator + "logic.gif");
/*  51 */     this.wide = false;
/*     */   }
/*     */ 
/*     */   public Logic(String filename) throws InvalidStateException {
/*  55 */     this.chipFilename = filename;
/*  56 */     initialise();
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  64 */     return '"' + this.chipFilename + '"';
/*     */   }
/*     */ 
/*     */   public void setFileName(String filename) {
/*  68 */     this.chipFilename = filename;
/*     */   }
/*     */ 
/*     */   public String getFileExtension() {
/*  72 */     return this.chipExtension;
/*     */   }
/*     */ 
/*     */   public boolean instantiateBlank() {
/*  76 */     return this.chipInstantiateBlank;
/*     */   }
/*     */ 
/*     */   public void initialiseState(String filename) throws InvalidStateException {
/*  80 */     if ((filename.isEmpty()) || (filename.equalsIgnoreCase("null"))) {
/*  81 */       System.out.println("NULL File Name");
/*     */     }
/*     */     else {
/*  84 */       this.chipFilename = filename;
/*  85 */       initialise();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initialise() throws InvalidStateException {
/*  90 */     String newFilename = updateConfigFilePath(this.chipFilename);
/*     */     try {
/*  92 */       FileInputStream istream = new FileInputStream(newFilename);
/*  93 */       Scanner fileScanner = new Scanner(istream);
/*     */ 
/*  95 */       while ((this.mode.booleanValue()) && (fileScanner.hasNextLine()))
/*     */       {
/*  97 */         String line = fileScanner.nextLine();
/*     */ 
/*  99 */         Scanner lineScanner = new Scanner(line);
/* 100 */         lineScanner.useDelimiter("=");
/*     */ 
/* 102 */         if (lineScanner.findInLine("Chip Text=") != null)
/*     */           try {
/* 104 */             this.chipText = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1)
/*     */           {
/*     */           }
/* 109 */         else if (lineScanner.findInLine("Description=") != null)
/*     */           try {
/* 111 */             this.chipDescription = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1)
/*     */           {
/*     */           }
/* 116 */         else if (lineScanner.findInLine("Manufacturer=") != null)
/*     */           try {
/* 118 */             this.chipManufacturer = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1)
/*     */           {
/*     */           }
/* 123 */         else if (lineScanner.findInLine("Wide Chip=") != null)
/*     */           try {
/* 125 */             String chipWideText = lineScanner.next();
/* 126 */             if (chipWideText.equals("False"))
/* 127 */               this.chipWide = Boolean.valueOf(false);
/*     */             else
/* 129 */               this.chipWide = Boolean.valueOf(true);
/*     */           }
/*     */           catch (NoSuchElementException e1)
/*     */           {
/*     */           }
/* 134 */         else if (lineScanner.findInLine("Clocked Chip=") != null)
/*     */           try {
/* 136 */             String chipClockedText = lineScanner.next();
/* 137 */             if (chipClockedText.equals("True"))
/* 138 */               throw new InvalidStateException("Chip is clocked exit");
/*     */           }
/*     */           catch (NoSuchElementException e1)
/*     */           {
/*     */           }
/* 143 */         if (lineScanner.findInLine("Input Pins=") != null) {
/* 144 */           lineScanner.useDelimiter(";");
/* 145 */           while (lineScanner.hasNext())
/*     */             try {
/* 147 */               this.chipInputs.add(lineScanner.next());
/*     */             }
/*     */             catch (NoSuchElementException e1)
/*     */             {
/*     */             }
/*     */         }
/* 153 */         if (lineScanner.findInLine("Output Pins=") != null) {
/* 154 */           lineScanner.useDelimiter(";");
/* 155 */           while (lineScanner.hasNext())
/*     */             try {
/* 157 */               this.chipOutputs.add(lineScanner.next());
/* 158 */               this.mode = Boolean.valueOf(false);
/*     */             }
/*     */             catch (NoSuchElementException e1)
/*     */             {
/*     */             }
/*     */         }
/*     */       }
/* 165 */       if (this.mode.booleanValue()) {
/* 166 */         throw new InvalidStateException("No outputs detected exit");
/*     */       }
/* 168 */       this.numberOfAddressBits = this.chipInputs.size();
/* 169 */       this.numberOfDataBits = this.chipOutputs.size();
/* 170 */       this.lookUpTable = new ArrayList((int)Math.pow(2.0D, this.numberOfAddressBits));
/*     */ 
/* 175 */       for (int i = 0; i < (int)Math.pow(2.0D, this.numberOfAddressBits); i++)
/*     */       {
/* 177 */         this.lookUpTable.add(new LogicTableElement(this.numberOfDataBits, 10));
/*     */       }
/*     */ 
/* 180 */       while ((!this.mode.booleanValue()) && (fileScanner.hasNextLine())) {
/* 181 */         String line = fileScanner.nextLine();
/*     */ 
/* 183 */         Scanner lineScanner = new Scanner(line);
/*     */         try
/*     */         {
/* 186 */           if (lineScanner.hasNext(Pattern.compile("^[01].*")))
/*     */           {
/* 190 */             lineScanner.useDelimiter(";");
/* 191 */             int address = Integer.parseInt(lineScanner.next(), 2);
/* 192 */             String presentState = lineScanner.next();
/* 193 */             int data = Integer.parseInt(lineScanner.next(), 2);
/* 194 */             String nextState = lineScanner.next();
/* 195 */             int delay = Integer.parseInt(lineScanner.next(), 10);
/*     */ 
/* 197 */             ((LogicTableElement)this.lookUpTable.get(address)).setData(data);
/* 198 */             ((LogicTableElement)this.lookUpTable.get(address)).setDelay(delay);
/* 199 */             this.lookUpTableRows += 1;
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (NoSuchElementException e1)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e1)
/*     */     {
/* 219 */       System.out.print(e1);
/* 220 */       throw new InvalidStateException("No File found exit");
/*     */     }
/*     */ 
/* 223 */     if (this.lookUpTableRows == 0) {
/* 224 */       throw new InvalidStateException("No truth table data exit");
/*     */     }
/* 226 */     this.numberOfPins = 1;
/* 227 */     this.pinOffset = this.numberOfPins;
/* 228 */     for (int i = 0; i < this.numberOfAddressBits; i++) {
/* 229 */       this.pins.add(new InputPin(i + this.pinOffset, (String)this.chipInputs.get(i)));
/*     */ 
/* 231 */       this.numberOfPins += 1;
/*     */     }
/*     */ 
/* 234 */     if (this.numberOfDataBits - this.numberOfAddressBits > 0) {
/* 235 */       this.pinOffset = this.numberOfPins;
/* 236 */       for (int i = 0; i < this.numberOfDataBits - this.numberOfAddressBits; i++) {
/* 237 */         this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));
/*     */ 
/* 239 */         this.numberOfPins += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 244 */     this.pins.add(new PowerPin(this.numberOfPins, "GND"));
/* 245 */     this.numberOfPins += 1;
/*     */ 
/* 247 */     this.pinOffset = this.numberOfPins;
/* 248 */     for (int i = 0; i < this.numberOfDataBits; i++) {
/* 249 */       this.pins.add(new OutputPin(i + this.pinOffset, (String)this.chipOutputs.get(i), 0));
/*     */ 
/* 251 */       this.numberOfPins += 1;
/*     */     }
/*     */ 
/* 254 */     if (this.numberOfDataBits - this.numberOfAddressBits < 0) {
/* 255 */       this.pinOffset = this.numberOfPins;
/* 256 */       for (int i = 0; i < Math.abs(this.numberOfDataBits - this.numberOfAddressBits); i++) {
/* 257 */         this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));
/*     */ 
/* 259 */         this.numberOfPins += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 264 */     this.pins.add(new PowerPin(this.numberOfPins, "VCC"));
/*     */ 
/* 266 */     this.name = this.chipText;
/*     */ 
/* 268 */     if (this.chipDescription.isEmpty())
/* 269 */       this.description = "Logic \n";
/*     */     else {
/* 271 */       this.description = (this.chipDescription + "\n");
/*     */     }
/* 273 */     this.description = this.description.concat(this.chipInputs.toString() + "\n");
/* 274 */     this.description = this.description.concat(this.chipOutputs.toString());
/*     */ 
/* 276 */     this.manufacturer = this.chipManufacturer;
/* 277 */     this.diagram = ("images" + File.separator + "logic.gif");
/* 278 */     this.wide = this.chipWide.booleanValue();
/*     */   }
/*     */ 
/*     */   private void updateGate() throws InvalidPinException {
/* 282 */     int address = 0;
/* 283 */     int offset = 1;
/* 284 */     for (int i = 0; i < this.chipInputs.size(); i++) {
/* 285 */       if (isHigh((String)this.chipInputs.get(i))) {
/* 286 */         address += offset;
/*     */       }
/* 288 */       offset *= 2;
/*     */     }
/*     */ 
/* 292 */     List data = ((LogicTableElement)this.lookUpTable.get(address)).getData();
/* 293 */     int delay = ((LogicTableElement)this.lookUpTable.get(address)).getDelay();
/*     */ 
/* 295 */     for (int i = 0; i < this.chipOutputs.size(); i++)
/* 296 */       if (((Boolean)data.get(i)).booleanValue())
/* 297 */         setPin((String)this.chipOutputs.get(i), Pin.PinState.HIGH, delay);
/*     */       else
/* 299 */         setPin((String)this.chipOutputs.get(i), Pin.PinState.LOW, delay);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void simulate()
/*     */   {
/*     */     try {
/* 309 */       if (isPowered()) {
/* 310 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 314 */         for (Pin pin : this.pins)
/* 315 */           if (isPinDriven(pin.getPinName()))
/* 316 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 321 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.generic.Logic
 * JD-Core Version:    0.6.2
 */