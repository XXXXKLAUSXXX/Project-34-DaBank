// // Proper order of the functions
//   readSerial(); // reads the serial, the terminator value is 127 which is a non character in utf-8
//   handleInput(); // sets demands based of the content of the buffer.
//   runBoxes();

//the components
#define countBoxes 4  //CountBoxes, the amount of boxes
const int CB = countBoxes;
const int ir[countBoxes] = { 4, 5, 6, 7 };       //the physical pins of the sensor output
const int motor[countBoxes] = { 8, 9, 10, 11 };  //the physical pin of the motor output, in out circuit we have it connected to a driver



int demands[countBoxes] = { 0, 6, 0, 0 };  // filled in setup, auto increments
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
    runBoxes();


}

void runBoxes() {
  static long delay[countBoxes];
  for (int i = 0; i < CB; i++) {
    //
    digitalWrite(motor[i], (demands[i] > 0));  //If we want a card, run the motor


    //
    ////this code counts every card that leaves the canister

    if (digitalRead(ir[i])) {  //_once_ there is no card infront of the ir sensor
      demands[i] -= flag[i];   //decrement the amount we still need to expel
      flag[i] = 0;             //lower the flag
      delay[i] = 0;
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
// depricated zone//


void handleInputDEP() {                                    // depricated, beautifull
  const int denomination[countBoxes] = { 5, 10, 20, 50 };  //
  const int offset = 2;                                    //the old read serial depended on more instruction bytes located at the start of the byte array, this offset accounts for that
  switch (buffer[0]) {

    case 33:  //total

      int amount = 1;
      for (int i = 0; i < CB; i++) {
        if (buffer[i + offset] == 0) { break; }
        amount *= buffer[i + offset];
      }


      for (int i = CB - 1; i >= 0; i--) {  // decrements through the boxes, taking large portions first

        demands[i] = amount / denomination[i];
        amount = amount % denomination[i];  //[3][1] = 20, so demands will be 0,0,0,4
      }

      break;
    case 34:  //specific

      for (int i = 0; i < CB; i++) {

        demands[i] = buffer[i + offset];  //not this
      }
      break;
    default:  //totalreadfail, will happen, don't know why can't avoid it though

      break;
  }
  return;
}
//