#include "Arduino.h"
// Proper order of the functions
  

//the components
#define countBoxes 4  //CountBoxes, the amount of boxes
const int CB = countBoxes;
const int ir[countBoxes] = { 30, 31, 32, 33 };       //the physical pins of the sensor output
const int motor[countBoxes] = { 34, 35, 36, 37 };  //the physical pin of the motor output, in out circuit we have it connected to a driver



int demands[countBoxes] = { 0, 0, 0, 0 };  // filled in setup, auto increments
bool flag[countBoxes] = { 0, 0, 0, 0 };    // filled in setup, filled with 0/false


#define commL countBoxes
const int commLenght = commL;  //one byte per box
byte buffer[commLenght];

void spitMoney() {
  if (readSerial() ) { // reads the serial, the terminator value is 127 which is a non character in utf-8
    handleInput(); // sets demands based of the content of the buffer.
    while (liveDemands() > 0) {
      runBoxes();
    }
  }
}

void initDispensers() {
  for (int i = 0; i < CB; i++) {
    pinMode(ir[i], INPUT);
    pinMode(motor[i], OUTPUT);
  }
}

void runBoxes() {
  for (int i = 0; i < CB; i++) {
    //
    digitalWrite(motor[i], (demands[i] > 0));  //If we want a card, run the motor


    //
    ////this code counts every card that leaves the canister

    if (digitalRead(ir[i])) {  //_once_ there is no card infront of the ir sensor
      demands[i] -= flag[i];   //decrement the amount we still need to expel
      flag[i] = 0;             //lower the flag
    } else {
      flag[i] = 1;  //if there is a card infron of the ir sensor raise the flag.
    }
    //
  }
  return;
}
//

void handleInput() {
  for (int i = 0; i < CB; i++) {
    demands[i] = buffer[i];
    buffer[i] = 0;
  }
}

bool readSerial() {
  if (Serial.available() > 0) {
    char identifier = (char)Serial.read();
    if (identifier == 'B') {
      Serial.readBytesUntil(127, buffer, commL);
      return true;
    } else false;
  } else false;
}


int liveDemands() {
  int count = 0;
  for (int i = 0; i < CB; i++) {
    count += demands[i];
  }
  return (count);
}

void clearBuffer() {
  for (int i = 0; i < commLenght; i++) {
    buffer[i] = 0;
  }
}