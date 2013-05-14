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
/*     */ public class StateMachine extends IntegratedCircuit
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
/*  32 */   private int numberOfStates = 0;
/*  33 */   private int initialState = 0;
/*     */ 
/*  35 */   private int presentState = 0;
/*  36 */   private int nextState = 0;
/*     */ 
/*  38 */   private Boolean chipWide = Boolean.valueOf(false);
/*  39 */   private List<String> chipInputs = new ArrayList();
/*  40 */   private List<String> chipOutputs = new ArrayList();
/*     */   private List<List> stateTable;
/*  43 */   private Boolean mode = Boolean.valueOf(true);
/*  44 */   private int lookUpTableRows = 0;
/*  45 */   private int pinOffset = 0;
/*     */ 
/*     */   public StateMachine()
/*     */   {
/*  53 */     this.description = "General purpose state machine chip configured via .chp file";
/*  54 */     this.manufacturer = "Generic";
/*  55 */     this.diagram = ("images" + File.separator + "statemachine.gif");
/*  56 */     this.wide = false;
/*     */   }
/*     */ 
/*     */   public StateMachine(String filename) throws InvalidStateException {
/*  60 */     this.chipFilename = filename;
/*  61 */     initialise();
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  69 */     return '"' + this.chipFilename + '"';
/*     */   }
/*     */ 
/*     */   public void setFileName(String filename) {
/*  73 */     this.chipFilename = filename;
/*     */   }
/*     */ 
/*     */   public String getFileExtension() {
/*  77 */     return this.chipExtension;
/*     */   }
/*     */ 
/*     */   public boolean instantiateBlank() {
/*  81 */     return this.chipInstantiateBlank;
/*     */   }
/*     */ 
/*     */   public void initialiseState(String filename) throws InvalidStateException {
/*  85 */     if ((filename.isEmpty()) || (filename.equalsIgnoreCase("null"))) {
/*  86 */       System.out.println("NULL File Name");
/*     */     }
/*     */     else {
/*  89 */       this.chipFilename = filename;
/*  90 */       initialise();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initialise() throws InvalidStateException {
/*  95 */     String newFilename = updateConfigFilePath(this.chipFilename);
/*     */     try {
/*  97 */       FileInputStream istream = new FileInputStream(newFilename);
/*  98 */       Scanner fileScanner = new Scanner(istream);
/*     */ 
/* 100 */       while ((this.mode.booleanValue()) && (fileScanner.hasNextLine()))
/*     */       {
/* 102 */         String line = fileScanner.nextLine();
/*     */ 
/* 104 */         Scanner lineScanner = new Scanner(line);
/* 105 */         lineScanner.useDelimiter("=");
/*     */ 
/* 107 */         if (lineScanner.findInLine("Chip Text=") != null) {
/*     */           try {
/* 109 */             this.chipText = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1) {
/*     */           }
/*     */         }
/* 114 */         else if (lineScanner.findInLine("Description=") != null) {
/*     */           try {
/* 116 */             this.chipDescription = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1) {
/*     */           }
/*     */         }
/* 121 */         else if (lineScanner.findInLine("Manufacturer=") != null) {
/*     */           try {
/* 123 */             this.chipManufacturer = lineScanner.next();
/*     */           }
/*     */           catch (NoSuchElementException e1) {
/*     */           }
/*     */         }
/* 128 */         else if (lineScanner.findInLine("Wide Chip=") != null) {
/*     */           try {
/* 130 */             String chipWideText = lineScanner.next();
/* 131 */             if (chipWideText.equals("False"))
/* 132 */               this.chipWide = Boolean.valueOf(false);
/*     */             else
/* 134 */               this.chipWide = Boolean.valueOf(true);
/*     */           }
/*     */           catch (NoSuchElementException e1) {
/*     */           }
/*     */         }
/* 139 */         else if (lineScanner.findInLine("Clocked Chip=") != null) {
/*     */           try {
/* 141 */             String chipClockedText = lineScanner.next();
/* 142 */             if (chipClockedText.equals("False"))
/* 143 */               throw new InvalidStateException("Chip is not clocked exit");
/*     */           } catch (NoSuchElementException e1) {
/*     */           }
/*     */         }
/*     */         else {
/* 148 */           if (lineScanner.findInLine("Input Pins=") != null) {
/* 149 */             lineScanner.useDelimiter(";");
/* 150 */             while (lineScanner.hasNext())
/*     */               try {
/* 152 */                 this.chipInputs.add(lineScanner.next());
/*     */               }
/*     */               catch (NoSuchElementException e1)
/*     */               {
/*     */               }
/*     */           }
/* 158 */           if (lineScanner.findInLine("Output Pins=") != null) {
/* 159 */             lineScanner.useDelimiter(";");
/* 160 */             while (lineScanner.hasNext())
/*     */               try {
/* 162 */                 this.chipOutputs.add(lineScanner.next());
/*     */               }
/*     */               catch (NoSuchElementException e1) {
/*     */               }
/* 166 */             if (this.chipOutputs.size() == 0) {
/* 167 */               throw new InvalidStateException("Chip has no outputs exit");
/*     */             }
/*     */           }
/* 170 */           else if (lineScanner.findInLine("Number of States=") != null) {
/*     */             try {
/* 172 */               this.numberOfStates = Integer.parseInt(lineScanner.next(), 10);
/*     */             }
/*     */             catch (NoSuchElementException e1) {
/*     */             }
/*     */           }
/* 177 */           else if (lineScanner.findInLine("Initial State=") != null) {
/*     */             try {
/* 179 */               this.initialState = Integer.parseInt(lineScanner.next(), 10);
/* 180 */               this.mode = Boolean.valueOf(false);
/*     */             } catch (NoSuchElementException e1) {
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 186 */       if (this.mode.booleanValue()) {
/* 187 */         throw new InvalidStateException("No Initial State detected exit");
/*     */       }
/* 189 */       this.numberOfAddressBits = this.chipInputs.size();
/* 190 */       this.numberOfDataBits = this.chipOutputs.size();
/* 191 */       this.stateTable = new ArrayList(this.numberOfStates);
/*     */ 
/* 194 */       for (int j = 0; j < this.numberOfStates; j++) {
/* 195 */         this.stateTable.add(new ArrayList((int)Math.pow(2.0D, this.numberOfAddressBits)));
/* 196 */         for (int i = 0; i < (int)Math.pow(2.0D, this.numberOfAddressBits); i++)
/*     */         {
/* 198 */           ((List)this.stateTable.get(j)).add(new StateTableElement(this.numberOfDataBits, 10));
/*     */         }
/*     */       }
/*     */ 
/* 202 */       while ((!this.mode.booleanValue()) && (fileScanner.hasNextLine())) {
/* 203 */         String line = fileScanner.nextLine();
/*     */ 
/* 205 */         Scanner lineScanner = new Scanner(line);
/*     */         try
/*     */         {
/* 208 */           if (lineScanner.hasNext(Pattern.compile("^[01].*")))
/*     */           {
/* 212 */             lineScanner.useDelimiter(";");
/* 213 */             int address = Integer.parseInt(lineScanner.next(), 2);
/* 214 */             int presentState = Integer.parseInt(lineScanner.next(), 10);
/* 215 */             int data = Integer.parseInt(lineScanner.next(), 2);
/* 216 */             int nextState = Integer.parseInt(lineScanner.next(), 10);
/* 217 */             int delay = Integer.parseInt(lineScanner.next(), 10);
/*     */ 
/* 219 */             ((StateTableElement)((List)this.stateTable.get(presentState)).get(address)).setData(data);
/* 220 */             ((StateTableElement)((List)this.stateTable.get(presentState)).get(address)).setDelay(delay);
/* 221 */             ((StateTableElement)((List)this.stateTable.get(presentState)).get(address)).setState(nextState);
/*     */ 
/* 223 */             this.lookUpTableRows += 1;
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
/* 245 */       System.out.print(e1);
/* 246 */       throw new InvalidStateException("No File found exit");
/*     */     }
/*     */ 
/* 249 */     if (this.lookUpTableRows == 0) {
/* 250 */       throw new InvalidStateException("No truth table data exit");
/*     */     }
/* 252 */     this.numberOfPins = 1;
/*     */ 
/* 254 */     this.pins.add(new InputPin(this.numberOfPins, "CLK"));
/* 255 */     this.numberOfPins += 1;
/*     */ 
/* 258 */     this.pins.add(new InputPin(this.numberOfPins, "CLR"));
/* 259 */     this.numberOfPins += 1;
/*     */ 
/* 261 */     this.pinOffset = this.numberOfPins;
/* 262 */     for (int i = 0; i < this.numberOfAddressBits; i++) {
/* 263 */       this.pins.add(new InputPin(i + this.pinOffset, (String)this.chipInputs.get(i)));
/*     */ 
/* 265 */       this.numberOfPins += 1;
/*     */     }
/*     */ 
/* 268 */     if (this.numberOfDataBits - (this.numberOfAddressBits + 2) > 0) {
/* 269 */       this.pinOffset = this.numberOfPins;
/* 270 */       for (int i = 0; i < this.numberOfDataBits - (this.numberOfAddressBits + 2); i++) {
/* 271 */         this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));
/*     */ 
/* 273 */         this.numberOfPins += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 278 */     this.pins.add(new PowerPin(this.numberOfPins, "GND"));
/* 279 */     this.numberOfPins += 1;
/*     */ 
/* 281 */     this.pinOffset = this.numberOfPins;
/* 282 */     for (int i = 0; i < this.numberOfDataBits; i++) {
/* 283 */       this.pins.add(new OutputPin(i + this.pinOffset, (String)this.chipOutputs.get(i), 0));
/*     */ 
/* 285 */       this.numberOfPins += 1;
/*     */     }
/*     */ 
/* 288 */     if (this.numberOfDataBits - (this.numberOfAddressBits + 2) < 0) {
/* 289 */       this.pinOffset = this.numberOfPins;
/* 290 */       for (int i = 0; i < Math.abs(this.numberOfDataBits - (this.numberOfAddressBits + 2)); i++) {
/* 291 */         this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));
/*     */ 
/* 293 */         this.numberOfPins += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 298 */     this.pins.add(new PowerPin(this.numberOfPins, "VCC"));
/*     */ 
/* 300 */     this.name = this.chipText;
/*     */ 
/* 302 */     if (this.chipDescription.isEmpty())
/* 303 */       this.description = "State Machine \n";
/*     */     else {
/* 305 */       this.description = (this.chipDescription + "\n");
/*     */     }
/* 307 */     this.description = this.description.concat(this.chipInputs.toString() + "\n");
/* 308 */     this.description = this.description.concat(this.chipOutputs.toString());
/*     */ 
/* 310 */     this.manufacturer = this.chipManufacturer;
/* 311 */     this.diagram = ("images" + File.separator + "statemachine.gif");
/* 312 */     this.wide = this.chipWide.booleanValue();
/*     */   }
/*     */ 
/*     */   private void updateGate() throws InvalidPinException {
/* 316 */     int address = 0;
/* 317 */     int offset = 1;
/* 318 */     for (int i = 0; i < this.chipInputs.size(); i++) {
/* 319 */       if (isHigh((String)this.chipInputs.get(i))) {
/* 320 */         address += offset;
/*     */       }
/* 322 */       offset *= 2;
/*     */     }
/*     */ 
/* 326 */     if (isRisingEdge("CLK")) {
/* 327 */       if (isHigh("CLR")) {
/* 328 */         this.presentState = this.initialState;
/*     */       }
/* 330 */       this.nextState = ((StateTableElement)((List)this.stateTable.get(this.presentState)).get(address)).getState();
/*     */ 
/* 332 */       List data = ((StateTableElement)((List)this.stateTable.get(this.presentState)).get(address)).getData();
/* 333 */       int delay = ((StateTableElement)((List)this.stateTable.get(this.presentState)).get(address)).getDelay();
/*     */ 
/* 335 */       for (int i = 0; i < this.chipOutputs.size(); i++) {
/* 336 */         if (((Boolean)data.get(i)).booleanValue())
/* 337 */           setPin((String)this.chipOutputs.get(i), Pin.PinState.HIGH, delay);
/*     */         else
/* 339 */           setPin((String)this.chipOutputs.get(i), Pin.PinState.LOW, delay);
/*     */       }
/* 341 */       this.presentState = this.nextState;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 347 */     this.presentState = this.initialState;
/*     */   }
/*     */ 
/*     */   public void simulate()
/*     */   {
/*     */     try {
/* 353 */       if (isPowered())
/*     */       {
/* 355 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 359 */         for (Pin pin : this.pins)
/* 360 */           if (isPinDriven(pin.getPinName()))
/* 361 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 366 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.ttl.generic.StateMachine
 * JD-Core Version:    0.6.2
 */