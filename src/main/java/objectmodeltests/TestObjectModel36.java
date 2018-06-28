package objectmodeltests;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TestObjectModel36 {

    public static void main(String[] args) throws FileNotFoundException {

        int numEntriesInMap = Integer.parseInt(args[0]);
        Boolean benchmarkMode = (args[1].equals("true"));
        if (benchmarkMode) {
            System.out.println("In benchmarkMode:");
        }

        long startTime = System.currentTimeMillis();

        Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();

        for (int i = 0; i < numEntriesInMap; i++) {
            myMap.put(i, i+120);
        }

        if (benchmarkMode) {
            for (int i = 0; i < numEntriesInMap; i++) {
                System.out.println(myMap.get(i));
            }
            System.out.println("myMap finished\n");
        }

        myMap = null;

        Map<String, Integer> myOtherMap = new HashMap<String, Integer>();
        for (int i = 0; i < numEntriesInMap; i++) {
            myOtherMap.put(Integer.toString(i) + " is my number", i);
        }

        if (benchmarkMode) {
            for (int i = 0; i < numEntriesInMap; i++) {
                System.out.println(myOtherMap.get(Integer.toString(i) + " is my number"));
            }
            System.out.println("myOtherMap finished\n");
        }

        myOtherMap = null;

        HashMap<String, Employee> anotherMap = new HashMap<String, Employee>();

        for (int i = 0; i < numEntriesInMap; i++) {
            String empName = Integer.toString(i) + " is my number";
            Employee myEmp = new Employee("Joe Johnston " + Integer.toString(i), i);
            anotherMap.put(empName, myEmp);
        }

        if (benchmarkMode) {
            for (int i = 0; i < numEntriesInMap; i++) {
                System.out.println(anotherMap.get(Integer.toString(i) + " is my number"));
            }
            System.out.println("anotherMap finished\n");
        }

        // Write out anotherMap
        Kryo kryo = new Kryo();
        kryo.register(Employee.class);
        kryo.register(String.class);
        kryo.register(HashMap.class);
        Output output = new Output(new FileOutputStream("testfile3", false));
        kryo.writeObject(output, anotherMap);
        output.flush();
        output.close();

        // Read in anotherMap
        Input input = new Input(new FileInputStream("testfile3"));
        anotherMap = (HashMap<String, Employee>) kryo.readObject(input, HashMap.class);

        if (benchmarkMode) {
            for (int i = 0; i < numEntriesInMap; i++) {
                System.out.println(anotherMap.get(Integer.toString(i) + " is my number"));
            }
            System.out.println("anotherMap after writing and reading finished\n");
        }

        // Perform DeepCopy
        Map<String, Employee> aFinalMap = copy(anotherMap);
        anotherMap = null;

        if (benchmarkMode) {
            for (int i = 0; i < numEntriesInMap; i++) {
                System.out.println(aFinalMap.get(Integer.toString(i) + " is my number"));
            }
            System.out.println("anotherMap after deep copying finished\n");
        }

        aFinalMap = null;

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        System.out.println("Duration to write the objects to a file: " + elapsedTime + " seconds.");
    }

    public static HashMap<String, Employee> copy(
            HashMap<String, Employee> original)
    {
        HashMap<String, Employee> copy = new HashMap<String, Employee>();
        for (Map.Entry<String, Employee> entry : original.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }
}
