String test = "fuckitall";
const int countBoxes = 4;
#define commL (countBoxes + 3)  // expected characters = (an amount of control bytes equal to the amount of boxes) + 1 indicator, 1 secbyte and 1 terminator
byte buffer[commL] = {};
int help = 0;
int offset = 2;
long amount = 0;
int CB = countBoxes;
int demands[countBoxes];
bool flag36 = 0;

int storage[countBoxes][2] = {
  //{Amount of cards, denomination},
  { 0, 1 },   // box 0
  { 0, 5 },   // box 1
  { 0, 10 },  // box 2
  { 0, 20 }   // box 3
};

//TEST VARIABLES AND SETS HERE

#define AMT 5
int led[] = { 13, 8, 9, 10, 11 };
bool flag[] = { 0, 0, 0, 0, 0 };
int expelling = 0;
//test set 1
// byte[] output1 = {'T', 10, 20, 3, 0, 2, '!'}; // expect 60
// byte[] output2 = {33, 12, 20, 3, 1, 2, '!'}; // expect 120
// byte[] output3 = {34, 12, 20, 10, 2, 0, '!'}; // expect 400

void setup() {

  for (int i = 0; i < CB; i++) {
    // demands[i] = ++i;
    demands[i] = 0;
  }

  for (int i = 0; i < AMT; i++) {
    pinMode(led[i], OUTPUT);
  }

  Serial.begin(9600);
  while (!Serial) {
    ;  // wait for serial port to connect.
  }
  flag[0] = 0;

  Serial.println("TXT: \"Setup complete\"");
}


void loop() {

  UL();

  readSerial();

  DS();
  clearBuffer();
}



void test6() {  //uses set 2, pretests for secbit on java end


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
        test += buffer[i + offset];
      }
      
      break;
    default:  //totalreadfail, will happen, don't know why can't avoid it though

      break;
  }
//

///testing stuff

  switch (buffer[1]) {
    case 1:
      flag[1] = 1;
      flag[2] = 0;
      flag[3] = 0;
      flag[4] = 0;
      break;
    case 2:
      flag[1] = 1;
      flag[2] = (expelling == 36);
      flag[3] = (demands[0] == 1 && demands[1] == 1 && demands[2] == 1 && demands[3] == 1);
      flag[4] = 0;
      break;
    case 3:
      flag[1] = 1;
      flag[2] = (expelling == 121);
      flag[3] = (demands[0] == 1 && demands[1] == 2 && demands[2] == 3 && demands[3] == 4);
      flag[4] = 0;
      break;
    case 4:
      flag[1] = 1;
      flag[2] = (storage[0][0] == 40 && storage[1][0] == 88 && storage[2][0] == 10 && storage[3][0] == 14);
      flag[3] = 0;
      flag[4] = 0;
      break;
    case 5:
      flag[1] = 1;
      flag[2] = (storage[0][0] == 41 && storage[1][0] == 89 && storage[2][0] == 11 && storage[3][0] == 15);
      flag[3] = (buffer[2] == 1 && buffer[3] == 1 && buffer[4] == 1 && buffer[5] == 1);
      flag[4] = 0;
      break;
    case 6:
      flag[1] = 1;
      flag[2] = (expelling == 37);
      flag[3] = (demands[0] == 2 && demands[1] == 1 && demands[2] == 1 && demands[3] == 1);
      DS();
      flag[4] = (storage[0][0] == 39 && storage[1][0] == 88 && storage[2][0] == 10 && storage[3][0] == 14);

      break;

    case 7:
      flag[1] = 1;
      flag[2] = (expelling == 135);
      flag[3] = (demands[0] == 20 && demands[1] == 5 && demands[2] == 5 && demands[3] == 2);
      DS();
      flag[4] = (storage[0][0] == 19 && storage[1][0] == 83 && storage[2][0] == 5 && storage[3][0] == 12);

      break;
  }



  return;
}

void test5() {  // uses set 1, if difficult to read: get gud. THis one passed first try, divine is my super
  switch (buffer[0]) {
    case 'T':  //fail, goodread tho
      flag[1] = (buffer[2] + buffer[3] + buffer[4] + buffer[5] == 25);
      break;
    case 33:  //total

      amount = 1;
      for (int i = 0; i < CB; i++) {
        if (buffer[i + offset] == 0) { break; }
        amount *= buffer[i + offset];  // amounts should be 120
      }

      for (int i = CB - 1; i >= 0; i--) {  // decrements through the boxes, taking large portions first
        demands[i] = amount / storage[i][1];
        amount = amount % storage[i][1];  //[3][1] = 20, so demands will be 0,0,0,6
      }

      flag[2] = (demands[3] == 6);

      break;
    case 34:  //specific

      for (int i = 0; i < CB; i++) {

        amount += buffer[i + offset] * storage[i][1];
        demands[i] = buffer[i + offset];
      }

      flag[3] = (demands[0] == 20 && demands[1] == 10 && demands[2] == 2 && demands[3] == 0);
      break;
    case 35:  //update

      break;
    default:  //totalreadfail, will happen, don't know why can't avoid it though
      flag[4] = 1;
      break;
  }


  return;
}

void test4() {  // passed
  amount = 1;
  for (int i = 0; i < CB; i++) {
    if (buffer[i + offset] == 0) { break; }
    amount *= buffer[i + offset];
  }
  flag[1] = (amount == 60);
  flag[2] = (amount == 120);
  flag[3] = (amount == 400);
  flag[4] = (amount == 0);  // womp womp
  return;
}

void test3() {                  // passed
  flag[1] = (buffer[2] == 20);  // always true with set 1
  flag[2] = (buffer[4] == 1);
  flag[3] = (buffer[3] == 10);
  return;
}

void test2() {  // passed
  switch (buffer[0]) {
    case 'T':
      flag[1] = 1;
      break;
    case 33:
      flag[2] = 1;
      break;
    case 34:
      flag[3] = 1;
      break;
    default:
      flag[4] = !flag[4];  // checks for flush?
      break;
  }
  return;
}

void test1() {  // passed
  flag[1] = (buffer[0] == 33);
  flag[2] = (buffer[0] == 34);
  flag[3] = (buffer[0] == 84);
  flag[4] = (buffer[0] == 'T');
  return;
}

void flicker(int x) {
  for (int i = 0; i < x; i++) {
    digitalWrite(13, 1);
    delay(90);
    digitalWrite(13, 2);
  }
}

void testHardware() {
  delay(1000);
  for (int i = 0; i < AMT; i++) {
    flag[i] = 1;
    delay(200);
    UL();
  }

  delay(1000);

  for (int i = 0; i < AMT; i++) {
    flag[i] = 0;
    UL();
    delay(200);
  }
}

void UL() {

  for (int i = 0; i < AMT; i++) {
    digitalWrite(led[i], flag[i]);
  }
  return;
}

void FS() {  //reset flags
  for (int i = 1; i < AMT; i++) {
    flag[i] = 0;
  }
}

void DS() {  //reset demands
  for (int i = 0; i < CB; i++) {
    storage[i][0] -= demands[i];
    demands[i] = 0;
  }
}

void readSerial() {



  if (Serial.available() > 0) {
    Serial.readBytesUntil(127, buffer, commL);

  }

  test6();
}

int liveDemands() {
  int count = 0;
  for (int i = 0; i < CB; i++) {
    count += demands[i];
  }
  return (count);
}

void clearBuffer(){
for(int i = 0; i < commL; i++){
  buffer[i] = 0;
}
return;
}