#include "keypad.h"
#include "rfid.h"
#include "receipt_printer.h"
#include "money_dispensers.h"

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

  if (Serial.available() > 0) {
    char identifier = (char) Serial.read();
    if (identifier == 'P') {
      printReceipt();
    }
    else if (identifier == 'B') {
      spitMoney();
      digitalWrite(13,HIGH);
    }
  }
}