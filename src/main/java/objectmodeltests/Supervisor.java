package objectmodeltests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Supervisor implements Serializable {
    Employee me;
    List<Employee> myGuys;

    public Supervisor(String name, int age) {
        me = new Employee(name, age);
        myGuys = new ArrayList<Employee>();
    }

    public Supervisor() {}

    void addEmp(Employee addMe) {
        myGuys.add(addMe);
    }

    Employee getEmp(int who) {
        return myGuys.get(who);
    }

}
