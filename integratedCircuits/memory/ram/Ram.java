/*    */ package integratedCircuits.memory.ram;
/*    */ 
/*    */ import integratedCircuits.InputPin;
/*    */ import integratedCircuits.InvalidPinException;
import integratedCircuits.Pin;
/*    */ import integratedCircuits.Pin.PinState;
/*    */ import integratedCircuits.memory.MemSpace;
/*    */ import integratedCircuits.memory.Memory;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Ram extends Memory
/*    */ {
/*    */   public Ram()
/*    */   {
/* 25 */     setup();
/*    */   }
/*    */ 
/*    */   public Ram(int addrBits) {
/* 29 */     this.addrBits = addrBits;
/* 30 */     setup();
/*    */   }
/*    */ 
/*    */   private void setup()
/*    */   {
/* 40 */     this.description = "Programmable Memory";
/* 41 */     this.manufacturer = "Generic RAM 256x8";
/* 42 */     this.diagram = ("images" + File.separator + "ram.gif");
/* 43 */     this.wide = true;
/*    */ 
/* 45 */     this.pins.add(new InputPin(1, "CS"));
/* 46 */     this.pins.add(new InputPin(2, "OE"));
/* 47 */     this.pins.add(new InputPin(3, "WE"));
/*    */ 
/* 49 */     this.cntlBits = 3;
/* 50 */     initialise();
/*    */   }
/*    */ 
/*    */   protected void updateGate() throws InvalidPinException
/*    */   {
/* 55 */     int addr = 0;
/* 56 */     int data = 0;
/*    */ 
/* 58 */     boolean addrValid = true;
/* 59 */     boolean dataValid = true;
/*    */ 
/* 63 */     for (int i = 0; i < this.addrBits; i++) {
/* 64 */       String name = "A" + i;
/* 65 */       if (isHigh(name)) {
/* 66 */         addr += (int)Math.pow(2.0D, i);
/*    */       }
/* 70 */       else if (!isLow(name))
/*    */       {
/* 74 */         addrValid = false;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 79 */     for (int i = 0; i < this.dataBits; i++) {
/* 80 */       String name = "D" + i;
/* 81 */       if (isHigh(name)) {
/* 82 */         data += (int)Math.pow(2.0D, i);
/*    */       }
/* 86 */       else if (!isLow(name))
/*    */       {
/* 90 */         dataValid = false;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 96 */     if (isHigh("CS"))
/*    */     {
/* 98 */       if (isHigh("OE"))
/*    */       {
/* 100 */         if (addrValid)
/*    */         {
/* 102 */           int memoryLocationData = this.memory.getDatum(addr);
/* 103 */           for (int i = 0; i < this.dataBits; i++) {
/* 104 */             String name = "D" + i;
/* 105 */             if ((memoryLocationData & (int)Math.pow(2.0D, i)) != 0) {
/* 106 */               setPin(name, Pin.PinState.HIGH);
/*    */             }
/*    */             else {
/* 109 */               setPin(name, Pin.PinState.LOW);
/*    */             }
/*    */           }
/*    */         }
/*    */         else
/*    */         {
/* 115 */           for (int i = 0; i < this.dataBits; i++) {
/* 116 */             String name = "D" + i;
/* 117 */             setPin(name, Pin.PinState.LOW);
/*    */           }
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 123 */         for (int i = 0; i < this.dataBits; i++) {
/* 124 */           String name = "D" + i;
/* 125 */           setPin(name, Pin.PinState.HIGH_IMPEDANCE);
/*    */         }
/* 127 */         if (isHigh("WE"))
/* 128 */           this.memory.setDatum(addr, data);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     integratedCircuits.memory.ram.Ram
 * JD-Core Version:    0.6.2
 */