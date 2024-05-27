package hardware.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;

public class ArduinoSerial {
    private final SerialPort serialPort;
    private ArduinoHandler arduinoHandler;
    private static ArduinoSerial arduino;
    public ArduinoSerial(String portName) throws SerialPortInvalidPortException {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setComPortParameters(460800,8,1,0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 6);

        if(!serialPort.openPort()) {
            System.out.println("\nPort NOT available\n");
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(serialPort::closePort));

        arduinoHandler = new ArduinoHandler();

        serialPort.addDataListener(arduinoHandler);
    }
    public static void initArduino() {
        try {
            arduino = new ArduinoSerial("/dev/ttyACM0");
            System.out.println("Serial port opened");
        } catch (SerialPortInvalidPortException e) {
            System.out.println("Serial port not found");
        }
    }
    public static void sendSerial(byte[] data) {
        arduino.serialPort.writeBytes(data,data.length);
    }
}
