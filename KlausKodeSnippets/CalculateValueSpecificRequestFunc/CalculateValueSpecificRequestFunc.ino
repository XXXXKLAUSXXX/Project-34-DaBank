#define countBoxes 4
int storage[countBoxes][2] = {
  //{Amount of cards, denomination},
  { 87, 1 },   // box 0
  { 88, 5 },   // box 1
  { 89, 10 },  // box 2
  { 90, 20 }   // box 3
};
byte buffer[] = { 36, 5, 1, 1, 1, 1 };
int offset = 2;
int demands[countBoxes] = { 0, 0, 0, 0 };

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  while (!Serial) {
    ;  // wait for serial port to connect.
  }
  int amount = 0;
  for (int i = 0; i < countBoxes; i++) {
    amount += demands[i] * storage[i][1];
  }
  Serial.println();
  Serial.println(amount);

  byte test = 0;
  test += 1;
  test += 1;
  test += 1;
  test += 1;
  Serial.println(test);

  for (int i = 0; i < countBoxes; i++) {
    storage[i][0] += buffer[i + offset];
    test += buffer[i + offset];
  
  Serial.println(storage[i][0]);
  }
  Serial.println(test);
}

void loop() {
  // put your main code here, to run repeatedly:
}
