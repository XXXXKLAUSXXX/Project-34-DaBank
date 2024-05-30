import java.io.IOException;
import java.util.*;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Simple application that is part of an tutorial.
 * The tutorial shows how to establish a serial connection between a Java and Arduino program.
 *
 * @author Michael Schoeffler (www.mschoeffler.de)
 */
public class tarn {
    SerialPort serp;
    Scanner sinput;

    public static void main(String[] args) throws IOException, InterruptedException {
        SerialPort sp = SerialPort.getCommPort("COM6"); // device name TODO: must be changed
        sp.setComPortParameters(460800, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
        Scanner input = new Scanner(System.in);


        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        //        System.out.println(Arrays.toString(output));
set3(sp, input);

        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }


    }// end main

    static void set1(SerialPort sp, Scanner input) throws IOException, InterruptedException {

        byte[] output = "T0!".getBytes();

        byte[] output1 = {'B', 1,1,1,1,127}; // expect 60
        byte[] output2 = {'B', 2,2,2,2,127}; // expect 120
        byte[] output3 = {88, 2,2,2,2,127}; // expect 400
        // 33 total
        // 34 specific
        // 35 update

        String soutput = new String(output);


        while (input.nextLine() != "") {
            ;
        }
        System.out.println(Arrays.toString(output1));
        sp.getOutputStream().write(output1);
        sp.getOutputStream().flush();

        while (input.nextLine() != "") {
            ;
        }
        System.out.println(Arrays.toString(output2));
        sp.getOutputStream().write(output2);
        sp.getOutputStream().flush();

        while (input.nextLine() != "") {
            ;
        }
        System.out.println(Arrays.toString(output3));
        sp.getOutputStream().write(output3);
        sp.getOutputStream().flush();
    }

    static void set2(SerialPort sp, Scanner input) throws IOException, InterruptedException {

        ArrayList<byte[]> inputs = new ArrayList<byte[]>();
        byte i = 1;
        byte[] output1 = {'i', i++, 0, 1, 2, 3, 127}; //lenght is 7, signed bytes so max is 127( so that's the terminator) because 127 is an empty value in utf 8
        byte[] output2 = {33, i++, 2, 3, 2, 3, 127}; // amount: 36, demands 1,1,1,1
        byte[] output3 = {34, i++, 1, 2, 3, 4, 127}; // amount: 121, demands 1,2,3,4
        byte[] output4 = {35, i++, 40, 88, 10, 14, 127}; // storage[i][0]: 40,88,10,14
        byte[] output5 = {36, i++, 1, 1, 1, 1, 127}; // storage[i][0]: 41,89,11,15
        byte[] output6 = {33, i++, 37, 1, 1, 1, 127}; // amount: 37, demands 2,1,1,1, storage[i][0]: 38,88,10,14
        byte[] output7 = {34, i++, 20, 5, 5, 2, 127}; // amount: 135, demands 20,5,5,2
        inputs.add(output1);
        inputs.add(output2);
        inputs.add(output3);
        inputs.add(output4);
        inputs.add(output5);
        inputs.add(output6);
        inputs.add(output7);
i=0;
        for (byte[] j : inputs) {
            while (input.nextLine() != "") {
                ;
            }
            System.out.println("Input: " + (++i)+ "  " +Arrays.toString(j));
            sp.getOutputStream().write(j);
            sp.getOutputStream().flush();
        }
    }
    static void set3(SerialPort sp, Scanner input) throws IOException, InterruptedException {

        ArrayList<byte[]> inputs = new ArrayList<byte[]>();
        byte i = 1;
        byte[] output1 = {1,1,1,1,'B', 127}; //lenght is 7, signed bytes so max is 127( so that's the terminator) because 127 is an empty value in utf 8
        byte[] output2 = {4,2,3,1,'B', 127}; // amount: 36, demands 1,1,1,1
        byte[] output3 = {'B', 2,2,2,2,127}; // amount: 121, demands 1,2,3,4
        byte[] output4 = {'B', 3,4,2,1,127}; // storage[i][0]: 40,88,10,14
        byte[] output5 = {'B', 1,2,3,1, 2,127}; // storage[i][0]: 41,89,11,15
        byte[] output6 = {33, i++, 37, 1, 1, 1, 127}; // amount: 37, demands 2,1,1,1, storage[i][0]: 38,88,10,14
        byte[] output7 = {34, i++, 20, 5, 5, 2, 127}; // amount: 135, demands 20,5,5,2
        inputs.add(output1);
        inputs.add(output2);
        inputs.add(output3);
        inputs.add(output4);
        inputs.add(output5);
        inputs.add(output6);
        inputs.add(output7);
        i=0;
        for (byte[] j : inputs) {
            while (input.nextLine() != "") {
                ;
            }
            System.out.println("Input: " + (++i)+ "  " +Arrays.toString(j));
            sp.getOutputStream().write(j);
            sp.getOutputStream().flush();
        }
    }
}