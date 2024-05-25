#include "HardwareSerial.h"
#include <Adafruit_Thermal.h>
#include <SoftwareSerial.h>

#define TX_PIN 41 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 40 // Arduino receive   GREEN WIRE   labeled TX on printer
#define SG_PIN 7  // Adafruit IotP for spare grounding

typedef struct printinfo {
  String amount;
  String iban;
  String date;
  String time;
};

printinfo toPrintinfo(String input);

SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

void initPrinter() {
  pinMode(SG_PIN, OUTPUT); digitalWrite(7, LOW);

  mySerial.begin(19200);  // Initialize SoftwareSerial
  printer.begin();        // Init printer (same regardless of serial type)
}

void printReceipt() {
  printinfo info;

  if (Serial.available() > 0) {
    char identifier = (char) Serial.read();
    String input = Serial.readString();
    if (identifier == 'P') {
      info = toPrintinfo(input);
      Serial.println(info.amount);
      Serial.println(info.iban);
      Serial.println(info.date);
      Serial.println(info.time);
    }
    else return;
  }
  else return;

  printer.setFont('A');
  printer.justify('C');
  printer.setSize('L');
  printer.boldOn();
  printer.println(F("\n  Isle of Man    Da Bank\n"));
  printer.boldOff();

  printer.setSize('S');
  printer.println(F("--------------------------------"));
  printer.justify('L');
  printer.println("Opgenomen bedrag: " + info.amount);
  printer.println("IBAN: " + info.iban);
  printer.println("Datum: " + info.date);
  printer.println("Tijd: " + info.time);
  printer.justify('C');
  printer.println(F("--------------------------------"));
  printer.println(F("Wijnhaven 107, Rotterdam"));
  printer.setSize('L');
  printer.boldOn();
  printer.doubleHeightOn();
  printer.println(F(" \nBedankt!\n Tot ziens! \n\n\n"));

  printer.sleep();      // Tell printer to sleep
  delay(100L);         // Sleep for 3 seconds
  printer.wake();       // MUST wake() before printing again, even if reset
  printer.setDefault(); // Restore printer to defaults
}

String substr(String input, String *output, char separator) {
  int colId = input.indexOf(separator); // get index of first seperator

  *output = input.substring(0,colId);   // get the first part of string without seperator

  return input.substring(colId+1);      // update the inputstring without seperator
}

printinfo toPrintinfo(String input) {
  printinfo info;
  char separator = '|';

  input = substr(input, &info.amount, separator); // get amount
  input = substr(input, &info.iban, separator);
  input = substr(input, &info.date, separator);
  input = substr(input, &info.time, separator);
  
  return info;
}