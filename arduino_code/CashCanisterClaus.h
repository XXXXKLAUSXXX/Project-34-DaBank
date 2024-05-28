// // Proper order of the functions
//   readSerial(); // reads the serial, the terminator value is 127 which is a non character in utf-8
//   handleInput(); // sets demands based of the content of the buffer.
//   runBoxes();

//the components
#define countBoxes 4  //CountBoxes, the amount of boxes
const int CB = countBoxes;
const int ir[countBoxes] = { 4, 5, 6, 7 };       //the physical pins of the sensor output
const int motor[countBoxes] = { 8, 9, 10, 11 };  //the physical pin of the motor output, in out circuit we have it connected to a driver



int demands[countBoxes] = { 3, 3, 3, 3 };  // filled in setup, auto increments
bool flag[countBoxes] = { 0, 0, 0, 0 };    // filled in setup, filled with 0/false


#define commL countBoxes
const int commLenght = commL;  //one byte per box
byte buffer[commLenght];


void setup() {

  Serial.begin(460800);
  Serial.println("TXT: \"Setup complete\"");
}

void loop() {
  // testHardware();
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

void readSerial() {

  if (Serial.available() > 0) {
    char identifier = (char)Serial.read();
    if (identifier == 'B') {
      Serial.readBytesUntil(127, buffer, commL);
    } else return;
  } else return;
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


void testHardware() {

  for (int i = 0; i < CB; i++) {
    digitalWrite(motor[i], 1);
  }
  delay(2000);
  for (int i = 0; i < CB; i++) {
    digitalWrite(motor[i], 0);
  }
  delay(2000);
  for (int i = 0; i < CB; i++) {
    digitalWrite(motor[i], 1);
    delay(1000);
  }

  delay(2000);
  for (int i = 0; i < CB; i++) {
    digitalWrite(motor[i], 0);
    delay(1000);
  }
}