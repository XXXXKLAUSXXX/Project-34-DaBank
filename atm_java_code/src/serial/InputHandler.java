package serial;

public abstract class InputHandler {
    private static char keyPress = '/';
    private static String rfid = "";
    private static volatile boolean isNewKey;
    private static volatile boolean isNewRfid;
    protected static void handleInput(byte[] input) {
        switch ((char) input[0]) {
            case 'N':
                keyPress = (char) input[1];
                isNewKey = true;
                break;
            case 'R':
                rfid = "";
                if (input.length < 26) return;
                for (int i = 1; i < input.length; i++) {
                    rfid += (char) input[i];
                }
                isNewRfid = true;
            default:
        }
    }

    public static void clearRfid() {
        rfid = "";
    }
    public static String getRfid() {
        return rfid;
    }

    public static char getKeyPress() {
        return keyPress;
    }

    public static void setKey(boolean isNew) {
        InputHandler.isNewKey = isNew;
    }
    public static void setRfid(boolean isNew) {
        InputHandler.isNewRfid = isNew;
    }
    public static boolean isNewKey() {
        return isNewKey;
    }
    public static boolean isNewRfid() {
        return isNewRfid;
    }
}
