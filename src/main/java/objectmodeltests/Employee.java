package objectmodeltests;

import java.io.Serializable;

public class Employee implements Serializable{

    String name;
    int age;

    public Employee(String nameIn, int ageIn) {
        name = nameIn;
        age = ageIn;
    }
    public Employee() {}

    @Override
    public String toString() {
        return ("name is: " + name + " age is: " + age);
    }

    void print() {
        System.out.println(toString());
    }

    Employee copy() {
        return new Employee(name, age);
    }

}
