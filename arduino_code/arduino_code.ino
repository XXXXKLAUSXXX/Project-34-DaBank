#include "keypad.h"
#include "rfid.h"
#include "receipt_printer.h"

void setup() {
  Serial.begin(460800);
  Serial.setTimeout(50);

  initRfid();
  initPrinter();
  initDispensers();
}

void loop() {
  getRfid();
  getKeypad();

  printReceipt();
  spitMoney();
}