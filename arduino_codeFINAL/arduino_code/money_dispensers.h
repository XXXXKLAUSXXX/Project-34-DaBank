#include "HardwareSerial.h"
#include "Arduino.h"
// Proper order of the functions


//the components
#define countBoxes 4  //CountBoxes, the amount of boxes
const int CB = countBoxes;
const int ir[countBoxes] = { 30, 32, 34, 36 };     //the physical pins of the sensor output
const int motor[countBoxes] = { 31, 35, 33, 37 };  //the physical pin of the motor output, in out circuit we have it connected to a driver



int demands[countBoxes] = { 0, 0, 0, 0 };  // filled in setup, auto increments
int flag[countBoxes] = { 0, 0, 0, 0 };     // filled in setup, filled with 0/false


#define commL countBoxes
const int commLenght = commL + 2;  //one byte per box, 2 control byte(s)
byte buffer[commLenght];

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

    if (digitalRead(ir[i]) && 1) {  //_once_ there is no card infront of the ir sensor
      demands[i] -= flag[i];        //decrement the amount we still need to expel
      flag[i] = 0;                  //lower the flag
    } else {
      flag[i] = 1;  //if there is a card infron of the ir sensor raise the flag.
    }
    //
  }
  return;
}
//
void testBoxes() {
  for (int i = 0; i < CB; i++) {
    //
    digitalWrite(motor[i], !digitalRead(ir[i]));  //If we want a card, run the motor
  }
  return;
}
//

void handleInput() {
  for (int i = 0; i < CB; i++) {
    demands[i] = buffer[i + 1];
  }
}

void readSerial() {
  Serial.readBytesUntil(127, buffer, commL);
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

void spitMoney() {

  handleInput();
}