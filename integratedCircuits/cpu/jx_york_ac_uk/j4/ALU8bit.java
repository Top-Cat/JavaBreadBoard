/*     */ package integratedCircuits.cpu.jx_york_ac_uk.j4;
/*     */ 
/*     */ import integratedCircuits.InputPin;
/*     */ import integratedCircuits.IntegratedCircuit;
/*     */ import integratedCircuits.InvalidPinException;
/*     */ import integratedCircuits.OutputPin;
/*     */ import integratedCircuits.Pin;
/*     */ import integratedCircuits.Pin.PinState;
/*     */ import integratedCircuits.PowerPin;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ALU8bit extends IntegratedCircuit
/*     */ {
/*  39 */   private int width = 8;
/*     */ 
/*     */   public ALU8bit()
/*     */   {
/*  46 */     initialise();
/*     */ 
/*  48 */     this.pins.add(new OutputPin(31, "COUT"));
/*  49 */     this.pins.add(new OutputPin(30, "ZERO"));
/*  50 */     this.pins.add(new OutputPin(29, "S7"));
/*  51 */     this.pins.add(new OutputPin(28, "S6"));
/*  52 */     this.pins.add(new OutputPin(27, "S5"));
/*  53 */     this.pins.add(new OutputPin(26, "S4"));
/*  54 */     this.pins.add(new OutputPin(25, "S3"));
/*  55 */     this.pins.add(new OutputPin(24, "S2"));
/*  56 */     this.pins.add(new OutputPin(23, "S1"));
/*  57 */     this.pins.add(new OutputPin(22, "S0"));
/*     */   }
/*     */ 
/*     */   public ALU8bit(int tplh, int tphl) {
/*  61 */     initialise();
/*     */ 
/*  63 */     this.pins.add(new OutputPin(31, "COUT", tplh, tphl));
/*  64 */     this.pins.add(new OutputPin(30, "ZERO", tplh, tphl));
/*  65 */     this.pins.add(new OutputPin(29, "S7", tplh, tphl));
/*  66 */     this.pins.add(new OutputPin(28, "S6", tplh, tphl));
/*  67 */     this.pins.add(new OutputPin(27, "S5", tplh, tphl));
/*  68 */     this.pins.add(new OutputPin(26, "S4", tplh, tphl));
/*  69 */     this.pins.add(new OutputPin(25, "S3", tplh, tphl));
/*  70 */     this.pins.add(new OutputPin(24, "S2", tplh, tphl));
/*  71 */     this.pins.add(new OutputPin(23, "S1", tplh, tphl));
/*  72 */     this.pins.add(new OutputPin(22, "S0", tplh, tphl));
/*     */   }
/*     */ 
/*     */   private void initialise()
/*     */   {
/*  80 */     this.description = "Eight bit Adder with Cin and Cout";
/*  81 */     this.manufacturer = "Generic TTL gate";
/*  82 */     this.diagram = ("images" + File.separator + "ALU8bit.gif");
/*  83 */     this.wide = false;
/*     */ 
/*  85 */     this.pins.add(new InputPin(1, "X0"));
/*  86 */     this.pins.add(new InputPin(2, "X1"));
/*  87 */     this.pins.add(new InputPin(3, "X2"));
/*  88 */     this.pins.add(new InputPin(4, "X3"));
/*  89 */     this.pins.add(new InputPin(5, "A0"));
/*  90 */     this.pins.add(new InputPin(6, "A1"));
/*  91 */     this.pins.add(new InputPin(7, "A2"));
/*  92 */     this.pins.add(new InputPin(8, "A3"));
/*  93 */     this.pins.add(new InputPin(9, "A4"));
/*  94 */     this.pins.add(new InputPin(10, "A5"));
/*  95 */     this.pins.add(new InputPin(11, "A6"));
/*  96 */     this.pins.add(new InputPin(12, "A7"));
/*  97 */     this.pins.add(new InputPin(13, "B0"));
/*  98 */     this.pins.add(new InputPin(14, "B1"));
/*  99 */     this.pins.add(new InputPin(15, "B2"));
/* 100 */     this.pins.add(new PowerPin(16, "GND"));
/* 101 */     this.pins.add(new InputPin(17, "B3"));
/* 102 */     this.pins.add(new InputPin(18, "B4"));
/* 103 */     this.pins.add(new InputPin(19, "B5"));
/* 104 */     this.pins.add(new InputPin(20, "B6"));
/* 105 */     this.pins.add(new InputPin(21, "B7"));
/* 106 */     this.pins.add(new PowerPin(32, "VCC"));
/*     */   }
/*     */ 
/*     */   private void updateGate() throws InvalidPinException {
/* 110 */     int command = 0;
/* 111 */     int dataA = 0;
/* 112 */     int dataB = 0;
/* 113 */     int total = 0;
/* 114 */     int sum = 0;
/*     */ 
/* 116 */     boolean dataAValid = true;
/* 117 */     boolean dataBValid = true;
/*     */ 
/* 121 */     for (int i = 0; i < this.width; i++) {
/* 122 */       String name = "A" + i;
/* 123 */       if (isHigh(name)) {
/* 124 */         dataA += (int)Math.pow(2.0D, i);
/*     */       }
/* 128 */       else if (!isLow(name))
/*     */       {
/* 132 */         dataAValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 137 */     for (int i = 0; i < this.width; i++) {
/* 138 */       String name = "B" + i;
/* 139 */       if (isHigh(name)) {
/* 140 */         dataB += (int)Math.pow(2.0D, i);
/*     */       }
/* 144 */       else if (!isLow(name))
/*     */       {
/* 148 */         dataBValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 153 */     for (int i = 0; i < 4; i++) {
/* 154 */       String name = "X" + i;
/* 155 */       if (isHigh(name)) {
/* 156 */         command += (int)Math.pow(2.0D, i);
/*     */       }
/* 160 */       else if (!isLow(name))
/*     */       {
/* 164 */         dataBValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 189 */     switch (command)
/*     */     {
/*     */     case 0:
/* 192 */       if ((dataAValid) && (dataBValid)) {
/* 193 */         total = dataA + dataB;
/* 194 */         sum = total & (int)Math.pow(2.0D, this.width) - 1;
/*     */ 
/* 196 */         System.out.println("ADD " + dataA + " " + dataB + " " + total + "  " + sum);
/*     */ 
/* 198 */         for (int i = 0; i < this.width; i++) {
/* 199 */           String name = "S" + i;
/* 200 */           if ((sum & (int)Math.pow(2.0D, i)) != 0) {
/* 201 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 204 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 207 */         if (total > (int)Math.pow(2.0D, this.width) - 1)
/* 208 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 210 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 213 */         System.out.println("Invalid data");
/*     */       }
/* 215 */       break;
/*     */     case 1:
/* 219 */       if ((dataAValid) && (dataBValid)) {
/* 220 */         total = dataA - dataB;
/*     */ 
/* 222 */         sum = total & (int)Math.pow(2.0D, this.width) - 1;
/*     */ 
/* 224 */         System.out.println("SUB " + dataA + " " + dataB + " " + total + "  " + sum);
/*     */ 
/* 226 */         for (int i = 0; i < this.width; i++) {
/* 227 */           String name = "S" + i;
/* 228 */           if ((sum & (int)Math.pow(2.0D, i)) != 0) {
/* 229 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 232 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 235 */         if (total > (int)Math.pow(2.0D, this.width) - 1)
/* 236 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 238 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 241 */         System.out.println("Invalid data");
/*     */       }
/* 243 */       break;
/*     */     case 2:
/* 247 */       if (dataAValid) {
/* 248 */         total = dataA + 1;
/* 249 */         sum = total & (int)Math.pow(2.0D, this.width) - 1;
/*     */ 
/* 251 */         System.out.println("INC " + dataA + " " + dataB + " " + total + "  " + sum);
/*     */ 
/* 253 */         for (int i = 0; i < this.width; i++) {
/* 254 */           String name = "S" + i;
/* 255 */           if ((sum & (int)Math.pow(2.0D, i)) != 0) {
/* 256 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 259 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 262 */         if (total > (int)Math.pow(2.0D, this.width) - 1)
/* 263 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 265 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 268 */         System.out.println("Invalid data");
/*     */       }
/* 270 */       break;
/*     */     case 3:
/* 274 */       if (dataAValid) {
/* 275 */         total = dataA - 1;
/* 276 */         sum = total & (int)Math.pow(2.0D, this.width) - 1;
/*     */ 
/* 278 */         System.out.println("DEC " + dataA + " " + dataB + " " + total + "  " + sum);
/*     */ 
/* 280 */         for (int i = 0; i < this.width; i++) {
/* 281 */           String name = "S" + i;
/* 282 */           if ((sum & (int)Math.pow(2.0D, i)) != 0) {
/* 283 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 286 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 289 */         if (total > (int)Math.pow(2.0D, this.width) - 1)
/* 290 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 292 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 295 */         System.out.println("Invalid data");
/*     */       }
/* 297 */       break;
/*     */     case 4:
/* 301 */       if ((dataAValid) && (dataBValid)) {
/* 302 */         total = dataA & dataB;
/*     */ 
/* 304 */         System.out.println("AND " + dataA + " " + dataB + " " + total);
/*     */ 
/* 306 */         for (int i = 0; i < this.width; i++) {
/* 307 */           String name = "S" + i;
/* 308 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 309 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 312 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 317 */         System.out.println("Invalid data");
/*     */       }
/* 319 */       break;
/*     */     case 5:
/* 323 */       if ((dataAValid) && (dataBValid)) {
/* 324 */         total = dataA | dataB;
/*     */ 
/* 326 */         System.out.println("OR " + dataA + " " + dataB + " " + total);
/*     */ 
/* 328 */         for (int i = 0; i < this.width; i++) {
/* 329 */           String name = "S" + i;
/* 330 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 331 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 334 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 339 */         System.out.println("Invalid data");
/*     */       }
/* 341 */       break;
/*     */     case 6:
/* 345 */       if ((dataAValid) && (dataBValid)) {
/* 346 */         total = dataA ^ dataB;
/*     */ 
/* 348 */         System.out.println("XOR " + dataA + " " + dataB + " " + total);
/*     */ 
/* 350 */         for (int i = 0; i < this.width; i++) {
/* 351 */           String name = "S" + i;
/* 352 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 353 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 356 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 361 */         System.out.println("Invalid data");
/*     */       }
/* 363 */       break;
/*     */     case 7:
/* 367 */       if ((dataAValid) && (dataBValid)) {
/* 368 */         total = dataA & dataB ^ 0xFFFFFFFF;
/*     */ 
/* 370 */         System.out.println("NAND " + dataA + " " + dataB + " " + total);
/*     */ 
/* 372 */         for (int i = 0; i < this.width; i++) {
/* 373 */           String name = "S" + i;
/* 374 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 375 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 378 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 383 */         System.out.println("Invalid data");
/*     */       }
/* 385 */       break;
/*     */     case 8:
/* 389 */       if ((dataAValid) && (dataBValid)) {
/* 390 */         total = (dataA | dataB) ^ 0xFFFFFFFF;
/*     */ 
/* 392 */         System.out.println("NOR " + dataA + " " + dataB + " " + total);
/*     */ 
/* 394 */         for (int i = 0; i < this.width; i++) {
/* 395 */           String name = "S" + i;
/* 396 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 397 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 400 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 405 */         System.out.println("Invalid data");
/*     */       }
/* 407 */       break;
/*     */     case 9:
/* 411 */       if ((dataAValid) && (dataBValid)) {
/* 412 */         total = dataA ^ dataB ^ 0xFFFFFFFF;
/*     */ 
/* 414 */         System.out.println("XOR " + dataA + " " + dataB + " " + total);
/*     */ 
/* 416 */         for (int i = 0; i < this.width; i++) {
/* 417 */           String name = "S" + i;
/* 418 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 419 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 422 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 427 */         System.out.println("Invalid data");
/*     */       }
/* 429 */       break;
/*     */     case 10:
/* 433 */       if (dataAValid) {
/* 434 */         total = dataA ^ 0xFFFFFFFF;
/*     */ 
/* 436 */         System.out.println("NOT " + dataA + " " + total);
/*     */ 
/* 438 */         for (int i = 0; i < this.width; i++) {
/* 439 */           String name = "S" + i;
/* 440 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 441 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else
/* 444 */             setPin(name, Pin.PinState.LOW);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 449 */         System.out.println("Invalid data");
/*     */       }
/* 451 */       break;
/*     */     case 11:
/* 455 */       if (dataAValid) {
/* 456 */         total = dataA >> 1;
/*     */ 
/* 458 */         System.out.println("SR0 " + dataA + " " + total);
/*     */ 
/* 460 */         for (int i = 0; i < this.width; i++) {
/* 461 */           String name = "S" + i;
/* 462 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 463 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 466 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 469 */         if ((dataA & 0x1) == 1)
/* 470 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 472 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 475 */         System.out.println("Invalid data");
/*     */       }
/* 477 */       break;
/*     */     case 12:
/* 481 */       if (dataAValid) {
/* 482 */         total = dataA >> 1 | 0x80;
/*     */ 
/* 484 */         System.out.println("SR1 " + dataA + " " + total);
/*     */ 
/* 486 */         for (int i = 0; i < this.width; i++) {
/* 487 */           String name = "S" + i;
/* 488 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 489 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 492 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 495 */         if ((dataA & 0x1) == 1)
/* 496 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 498 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 501 */         System.out.println("Invalid data");
/*     */       }
/* 503 */       break;
/*     */     case 13:
/* 507 */       if (dataAValid) {
/* 508 */         total = dataA << 1;
/*     */ 
/* 510 */         System.out.println("SL0 " + dataA + " " + total);
/*     */ 
/* 512 */         for (int i = 0; i < this.width; i++) {
/* 513 */           String name = "S" + i;
/* 514 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 515 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 518 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 521 */         if ((dataA & 0x80) == 1)
/* 522 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 524 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 527 */         System.out.println("Invalid data");
/*     */       }
/* 529 */       break;
/*     */     case 14:
/* 533 */       if (dataAValid) {
/* 534 */         total = dataA << 1 | 0x1;
/*     */ 
/* 536 */         System.out.println("SL1 " + dataA + " " + total);
/*     */ 
/* 538 */         for (int i = 0; i < this.width; i++) {
/* 539 */           String name = "S" + i;
/* 540 */           if ((total & (int)Math.pow(2.0D, i)) != 0) {
/* 541 */             setPin(name, Pin.PinState.HIGH);
/*     */           }
/*     */           else {
/* 544 */             setPin(name, Pin.PinState.LOW);
/*     */           }
/*     */         }
/* 547 */         if ((dataA & 0x80) == 1)
/* 548 */           setPin("COUT", Pin.PinState.HIGH);
/*     */         else
/* 550 */           setPin("COUT", Pin.PinState.LOW);
/*     */       }
/*     */       else {
/* 553 */         System.out.println("Invalid data");
/*     */       }
/* 555 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void simulate()
/*     */   {
/*     */     try
/*     */     {
/* 567 */       if (isPowered()) {
/* 568 */         updateGate();
/*     */       }
/*     */       else
/*     */       {
/* 572 */         for (Pin pin : this.pins)
/* 573 */           if (isPinDriven(pin.getPinName()))
/* 574 */             setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
/*     */       }
/*     */     }
/*     */     catch (InvalidPinException e1)
/*     */     {
/* 579 */       System.out.println("OPPS: InvalidPinException");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.cpu.jx_york_ac_uk.j4.ALU8bit
 * JD-Core Version:    0.6.2
 */