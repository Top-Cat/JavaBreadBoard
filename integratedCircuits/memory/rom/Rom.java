/*    */ package integratedCircuits.memory.rom;
/*    */ 
/*    */ import integratedCircuits.InputPin;
/*    */ import integratedCircuits.InvalidPinException;
import integratedCircuits.Pin;
/*    */ import integratedCircuits.Pin.PinState;
/*    */ import integratedCircuits.memory.MemSpace;
/*    */ import integratedCircuits.memory.Memory;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Rom extends Memory
/*    */ {
/*    */   public Rom()
/*    */   {
/* 25 */     setup();
/*    */   }
/*    */ 
/*    */   public Rom(int addrBits) {
/* 29 */     this.addrBits = addrBits;
/* 30 */     setup();
/*    */   }
/*    */ 
/*    */   private void setup()
/*    */   {
/* 38 */     System.out.println("ROM : setup");
/*    */ 
/* 40 */     this.description = "Programmable Memory";
/* 41 */     this.manufacturer = "Generic ROM 256x8";
/* 42 */     this.diagram = ("images" + File.separator + "rom.gif");
/* 43 */     this.wide = true;
/*    */ 
/* 45 */     this.pins.add(new InputPin(1, "CS"));
/* 46 */     this.pins.add(new InputPin(2, "OE"));
/*    */ 
/* 48 */     this.cntlBits = 2;
/* 49 */     initialise();
/*    */   }
/*    */ 
/*    */   protected void updateGate() throws InvalidPinException
/*    */   {
/* 54 */     int addr = 0;
/* 55 */     boolean addrValid = true;
/*    */ 
/* 59 */     for (int i = 0; i < this.addrBits; i++) {
/* 60 */       String name = "A" + i;
/* 61 */       if (isHigh(name)) {
/* 62 */         addr += (int)Math.pow(2.0D, i);
/*    */       }
/* 66 */       else if (!isLow(name))
/*    */       {
/* 70 */         addrValid = false;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 75 */     if (isHigh("CS"))
/*    */     {
/* 77 */       if (isHigh("OE"))
/*    */       {
/* 79 */         if (addrValid)
/*    */         {
/* 81 */           int memoryLocationData = this.memory.getDatum(addr);
/* 82 */           for (int i = 0; i < this.dataBits; i++) {
/* 83 */             String name = "D" + i;
/* 84 */             if ((memoryLocationData & (int)Math.pow(2.0D, i)) != 0) {
/* 85 */               setPin(name, Pin.PinState.HIGH);
/*    */             }
/*    */             else {
/* 88 */               setPin(name, Pin.PinState.LOW);
/*    */             }
/*    */           }
/*    */         }
/*    */         else
/*    */         {
/* 94 */           for (int i = 0; i < this.dataBits; i++) {
/* 95 */             String name = "D" + i;
/* 96 */             setPin(name, Pin.PinState.LOW);
/*    */           }
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 102 */         for (int i = 0; i < this.dataBits; i++) {
/* 103 */           String name = "D" + i;
/* 104 */           setPin(name, Pin.PinState.HIGH_IMPEDANCE);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.rom.Rom
 * JD-Core Version:    0.6.2
 */