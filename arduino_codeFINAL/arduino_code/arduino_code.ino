#include "keypad.h"
#include "rfid.h"
#include "receipt_printer.h"
#include "money_dispensers.h"

void setup() {
  Serial.begin(460800);
  Serial.setTimeout(50);
  pinMode(13, OUTPUT);
  digitalWrite(13, 0);

  initRfid();
  initPrinter();
  initDispensers();
}

void loop() {

  getRfid();
  getKeypad();

  runBoxes();
  clearBuffer();
  if (Serial.available() > 0) {
    Serial.readBytesUntil(127, buffer, commLenght);
    if (buffer[0] == 'P') {
      printReceipt();
    } else if (buffer[0] == 'B') {
      handleInput();
    }
  }

  //
}