#include "keypad.h"
#include "rfid.h"
#include "receipt_printer.h"
#include "money_dispensers.h"

void setup() {
  Serial.begin(460800);
  // Serial.setTimeout(50);

   initRfid();
   initPrinter();
  initDispensers();
}

void loop() {
      digitalWrite(13,ir[1]);
  // getRfid();
  // getKeypad();
  // runBoxes();
  if (Serial.available() > 0) {
 Serial.readBytesUntil(127, buffer, commL);
    if (buffer[0] == 'P') {
      // runprintReceipt();
    }
    else if (buffer[0] == 'B') {
     
      handleInput();

    }
  }
//
    int test = 1;
    switch (test){
      case 0:
       digitalWrite(13, (buffer[1] == 3 && buffer[2] == 4 && buffer[3] == 2 && buffer[4] == 1));
      break;
      default:
      break;
    }

//
}