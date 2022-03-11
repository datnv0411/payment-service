package vn.cmc.du21.paymentservice.common;

import java.util.Random;

public class GetRandomNumber {
    private static Random rand = new Random();

    private GetRandomNumber() {
        super();
    }

    public static String getRandomNumber() {
        System.out.println("");
        int upperbound = 100000000;
        //generate random values from 0-99999999
        int intRandom = rand.nextInt(upperbound);

        return String.format("%08d", intRandom);
    }
}
