#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN         49
#define SS_PIN          53

/*======================/
*  pin layout for mega:
*
*    RST       = 49
*    SDA (SS)  = 53
*    MoSi      = 51
*    MiSo      = 50
*    SCK       = 52
*
*======================*/

MFRC522 mfrc522(SS_PIN, RST_PIN);	// maak instantie van rfid reader

void initRfid() {                              
  SPI.begin();		// start SPI
  mfrc522.PCD_Init();	// start MFRC522 card
}

void getRfid() {

  // bereid key voor
  MFRC522::MIFARE_Key key;
  for (byte i = 0; i < 6; i++) key.keyByte[i] = 0xFF;

  // nodige variabelen
  byte block;
  byte len;
  MFRC522::StatusCode status;

  // efficiency voor het wel of niet checken van de card
  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    return;
  }

  // Selecteer een kaart
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  len = 18;

  // lees blok 4 van de kaart af
  block = 4;
  byte buffer1[18];
  
  status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, 4, &key, &(mfrc522.uid)); 
  if (status != MFRC522::STATUS_OK) {
    return; // stop geen auth
  }

  status = mfrc522.MIFARE_Read(block, buffer1, &len);
  if (status != MFRC522::STATUS_OK) {
    return; // stop onleesbaar
  }
  
  // lees blok 5 van de kaart af
  block = 5;
  byte buffer2[18];

  status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, 5, &key, &(mfrc522.uid)); 
  if (status != MFRC522::STATUS_OK) {
    return; // stop geen auth
  }

  status = mfrc522.MIFARE_Read(block, buffer2, &len);
  if (status != MFRC522::STATUS_OK) {
    return; // stop onleesbaar
  }
  for (int i = 0; i < 10; i++) buffer1[i+8] = buffer2[i];

  String toSend = "R";
  for (int i = 0; i < mfrc522.uid.size; i++) {
    toSend += ((mfrc522.uid.uidByte[i] < 16) ? "0" : "") + String(mfrc522.uid.uidByte[i], HEX);
  }
  for (uint8_t i = 0; i < 18; i++)
  {
    if (buffer1[i] != 32)
    {
      toSend += (char) buffer1[i];
    }
  }
  Serial.print(toSend);

  delay(100); // Niet direct verder lezen

  mfrc522.PICC_HaltA();
  mfrc522.PCD_StopCrypto1();
}

