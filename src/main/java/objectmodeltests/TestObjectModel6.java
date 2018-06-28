package objectmodeltests;


import java.io.FileNotFoundException;

public class TestObjectModel6 {

    public static void main(String[] args) throws FileNotFoundException {
        int[] intArgs = new int[args.length];

        for (int i : intArgs) {
            try {
                intArgs[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.err.println("Failed trying to parse a non-numeric argument, " + args[i]);
            }
        }
        int numOfString = intArgs[0];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numOfString; i++) {
            String s = "This is an object big enough to force flushing soon. This is an object big enough to " +
                    "force flushing soon. This is an object big enough to force flushing soon. This is an " +
                    "object big enough to force flushing soon. This is an object big enough to force " +
                    "flushing soon. This is an object big enough to force flushing soon. This is an object " +
                    "big enough to force flushing soon. This is an object big enough to force flushing " +
                    "soon. This is an object big enough to force flushing  soon. It has a total of 512 " +
                    "bytes to test. This is an object big enough to force flushing soon. This is an object " +
                    "big enough to force flushing soon. This is an object big enough to force flushing " +
                    "soon. This is an object big enough to force flushing soon. This is an object big " +
                    "enough to force flushing.." + Integer.toString(i);
        }

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        System.out.println("Duration to create all of the String objects:  " + elapsedTime + " seconds.");
    }
}
