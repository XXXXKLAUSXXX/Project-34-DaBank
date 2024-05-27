const int BOX_COUNT = 4;
const int OUT_PINS[] = {30,31,32,33};
const int IN_PINS[] = {34,35,36,37};

void reset(int boxId) {
  if (digitalRead(IN_PINS[i])) {

      digitalWrite(OUT_PINS[boxId], HIGH);

      while (digitalRead(IN_PINS[i])) {
        micros(1);
      }

      digitalWrite(OUT_PINS[boxId], LOW);
    }
}

void spit(int boxId, int amount) {
  for (int i = 0; i < amount; i++) {
    if (!digitalRead(IN_PINS[boxId])) {

      digitalWrite(OUT_PINS[boxId], HIGH);

      while (!digitalRead(IN_PINS[boxId])) {
        delayMicroseconds(1);
      }

      digitalWrite(OUT_PINS[boxId], LOW);
      
      reset();
    }
  }
}

void checkForSpit() {
  if (Serial.available() > 0) {
    char identifier = (char) Serial.read();
    byte input[] = Serial.readBytesUntil();
    if (identifier == 'S') {
      Serial.println("Spit");
      spit(0,3);
      spit(1,5);
      spit(2,1);
      spit(3,0);
    }
  }
}




