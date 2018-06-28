package objectmodeltests;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestObjectModel1 {

    public static void main(String[] args) throws FileNotFoundException {

        int[] intArgs = new int[args.length];

        for (int i : intArgs) {
            try {
                intArgs[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.err.println("Failed trying to parse a non-numeric argument, " + args[i]);
            }
        }
        int numOfSupervisor = intArgs[0];
        int numOfEmployee = 10;

        long startTime = System.currentTimeMillis();

        List<Supervisor> supers = new ArrayList<Supervisor>();
        for (int i = 0; i < numOfSupervisor; i++) {
            supers.add(new Supervisor("Joe Johnson" + Integer.toString(i), 20 + (i % 29)));
            for(int j =0; j < numOfEmployee; j++) {
                supers.get(i).addEmp(new Employee("Steve Stevens" + Integer.toString(i) + Integer.toString(j), 20 + ((i + j) % 29)));
            }
        }
        System.out.println(supers.get(0).myGuys.size());

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        System.out.println("Duration to create all of the objects: " + elapsedTime + " seconds.");

        startTime = System.currentTimeMillis();

        Kryo kryo = new Kryo();
        kryo.register(ArrayList.class);
        kryo.register(Employee.class);
        kryo.register(Supervisor.class);
        kryo.register(supers.getClass());

        Output output = new Output(new FileOutputStream("testfile", false));
        kryo.writeObject(output, supers);
        kryo.reset();
        output.flush();
        output.close();

        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime) / 1000.0;
        System.out.println("Duration to write the objects to a file: " + elapsedTime + " seconds.");
    }
}
