package objectmodeltests;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestObjectModel2 {
    // Actually corresponds to TestObjectModel3
    public static void main(String[] args) throws FileNotFoundException {
        int[] intArgs = new int[args.length];

        for (int i : intArgs) {
            try {
                intArgs[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.err.println("Failed trying to parse a non-numeric argument, " + args[i]);
            }
        }

        long startTime = System.currentTimeMillis();

        // Read
        List<Supervisor> supers = null;
        Kryo kryo = new Kryo();
        kryo.register(ArrayList.class);
        kryo.register(Employee.class);
        kryo.register(Supervisor.class);
        kryo.register(Arrays.asList().getClass(), new CollectionSerializer() {
            protected Collection create (Kryo kryo, Input input, Class<Collection> type) {
                return new ArrayList();
            }
        });
        Input input = new Input(new FileInputStream("testfile"));
        supers=(ArrayList<Supervisor>)kryo.readObject(input, ArrayList.class);
        input.close();

        // and loop through it, copying over some employees
        int numSupers = supers.size();
        System.out.println(numSupers);
        List<Employee> result = new ArrayList<Employee>();
        for (int i = 0; i < numSupers; i++) {
            result.add(supers.get(i).getEmp(i % 10));
        }

        // Write out
        Output output = new Output(new FileOutputStream("testfile2", false));
        kryo.writeObject(output, result);
        output.close();

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        System.out.println("Duration to do the copies and then write the new objects: " + elapsedTime + " seconds.");

        for (int i = 0; i < numSupers; i += 1000) {
            result.get(i).print();
        }
    }
}
