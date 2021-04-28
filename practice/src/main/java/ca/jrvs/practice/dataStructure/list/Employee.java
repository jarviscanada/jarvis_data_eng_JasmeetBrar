package ca.jrvs.practice.dataStructure.list;

public class Employee implements Comparable<Employee> {

    private int id;
    private String name;
    private int age;
    private long salary;

    public Employee() {
    }

    public Employee(int id, String name, int age, long salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee o) {
        if(this == o || this.age == o.age && this.salary == o.salary) {
            return 0;
        }

        if(this.age == o.age) {
            return this.salary > o.salary ? 1 : -1;
        }

        return this.age > o.age ? 1 : -1;
    }
}