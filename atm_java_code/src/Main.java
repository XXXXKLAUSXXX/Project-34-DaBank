import gui.*;
import hardware.serial.ArduinoSerial;

public class Main {
    public static void main(String[] args) {
        ArduinoSerial.initArduino();
        GUI.makeGUI();
    }
}