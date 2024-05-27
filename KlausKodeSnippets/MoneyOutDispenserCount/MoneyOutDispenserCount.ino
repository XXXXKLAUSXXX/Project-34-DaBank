// variables
const int rundelay = 0;
const int testdelay = 250;  //used before the body code of the testbox function
bool flagt = false;

const int cycledelay = 0;
//the components
#define countBoxes 4
const int CB = countBoxes;  //CountBoxes

int ir[countBoxes] = { 4, 5, 6, 7 };
int motor[countBoxes] = { 8, 9, 10, 11 };

int demands[countBoxes];  // filled in setup, auto increments
bool flag[countBoxes];    // filled in setup, filled with 0/false
int expelling = 0;

int storage[countBoxes][2] = {
  //{Amount of cards, denomination},
  { 0, 1 },   // box 0
  { 0, 5 },   // box 1
  { 0, 10 },  // box 2
  { 0, 20 }   // box 3
};

// Serial variables
const int commLenght = (countBoxes + 3);
#define commL commLenght  // expected characters = (an amount of control bytes equal to the amount of boxes) + 1 indicator, 1 secbyte and 1 terminator
int offset;

byte buffer[commL] = {};
unsigned long amount = 0;

//security features
char secPass[] = "ImplementLater";
int secInc = 1;

//testing features
bool tflag = 0;

void setup() {
  // setup the connection to the boxes.
  for (int i; i < CB; i++) {
    pinMode(ir[i], INPUT);
  }
  for (int i; i < CB; i++) {
    pinMode(motor[i], OUTPUT);
  }

  //fill the flags with zero, and the demands with an auto increment.
  for (int i = 0; i < CB; i++) {
    // demands[i] = ++i;
    demands[i] = 0;
    flag[i] = 0;
  }

  Serial.begin(9600);
  Serial.println("TXT: \"Setup complete\"");
  //tell the server that you reset and secInc = 0
  Serial.println(commLenght);

  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);
}



void loop() {

  readSerial();

  handleInput();

  runBoxes();

}


void runBoxes() {  // with a for loop cycle through all availible boxes.

  for (int i = 0; i < CB; i++) {

    //start procedure
    if (digitalRead(ir[i]) == 0) {  // When a card is blocking the IR sensor


      if (demands[i] > 0) {            // And we are demanding a card from B0
        digitalWrite(motor[i], HIGH);  // tell the motor to push

        if (flag[i] == 0) {  // when a card starts being expelled we run this while the flag is low, then raise the flag.
          // decrement the storage and demands
          storage[i][0]--;
          demands[i]--;

          flag[i] = 1;
        }

      } else {
        digitalWrite(motor[i], LOW);  // if we are not demanding a card do not expel one.

        // Serial.println("card infront of sensor, cease");
      }



    } else {                         // when there is no card infront of the IR
      digitalWrite(motor[i], HIGH);  // push a front of it

      // Serial.print(i);

      if (flag[i] != 0){
        flag[i] = 0;
      }
        //  Serial.print("lowering flag");


          // when no card is infront of the IR sensor we lower the flag

      // Serial.println("pushing card to sensor");
    }
  }
  // Serial.println("cycle");
  return;
}

//



void readSerial() {
  if (Serial.available() > 0) {
    Serial.readBytesUntil(127, buffer, commL);

    if (secFeature(buffer[1])) {
      return;
    }


    clearBuffer();
  }
  return;
}

void handleInput() {
  switch (buffer[0]) {

    case 33:  //total

      amount = 1;
      for (int i = 0; i < CB; i++) {
        if (buffer[i + offset] == 0) { break; }
        amount *= buffer[i + offset];
      }

      expelling = amount;
      for (int i = CB - 1; i >= 0; i--) {  // decrements through the boxes, taking large portions first

        demands[i] = amount / storage[i][1];
        amount = amount % storage[i][1];  //[3][1] = 20, so demands will be 0,0,0,4
      }



      break;
    case 34:  //specific
      amount = 0;
      for (int i = 0; i < CB; i++) {

        amount += buffer[i + offset] * storage[i][1];  //this one is faulty
        demands[i] = buffer[i + offset];               //not this
      }
      expelling = amount;
      break;
    case 35:  //update set
      for (int i = 0; i < CB; i++) {
        storage[i][0] = buffer[i + offset];
      }
      break;
    case 36:  //update adding


      for (int i = 0; i < CB; i++) {

        storage[i][0] += buffer[i + offset];

      }

      break;
    default:  //totalreadfail, will happen, don't know why can't avoid it though

      break;
  }
  return;
}


//
void serialInfo(String password) {
  if (password == secPass) {
    //print countboxes, sec inc
  }
  return;
}
//
void serialSend(String msg) {
  //whatwhat
  return;
}
//
bool secFeature(int inc) {
  if (inc = secInc) {
    secInc++;
    return (true);  //passes
  }
  return (false);  //fails
}

int liveDemands() {
  int count = 0;
  for (int i = 0; i < CB; i++) {
    count += demands[i];
  }
  return (count);
}

void clearBuffer() {
  for (int i = 0; i < commL; i++) {
    buffer[i] = 0;
  }
  return;
}


void testHardware(){
  for(int i = 0; i < 4; i++){
  Serial.print(ir[i]);
  Serial.print(".");
  }
  Serial.println();
}
