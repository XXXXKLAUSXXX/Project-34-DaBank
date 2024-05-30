#include <Keypad.h>

const byte ROWS = 4; 
const byte COLS = 4; 

char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'D'},
  {'4', '5', '6', 'C'},
  {'7', '8', '9', 'K'},
  {'/', '0', '/', '/'}
};

byte rowPins[ROWS] = {3, 4, 5, 6}; 
byte colPins[COLS] = {8, 9, 10, 11}; 

Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

void getKeypad() {
	String customKey = "N";
  customKey += customKeypad.getKey();
  
	if (customKey != "N\0" && customKey != "N/") {
		Serial.print(customKey);
	}
}