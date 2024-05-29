package hardware;

public class Bills {
    private static int[] bills = {15,0,15,15};
    private static final int[] multiplier = {5,10,20,50};

    public static void reduce(int bill, int amount) {
        bills[bill] -= amount;
    }

    public static int check(int bill) {
        return bills[bill];
    }

    public static int[] getMultiplier() {return multiplier;}

    public static int getTotal() {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            total += bills[i] * multiplier[i];
        }
        return total;
    }
}
