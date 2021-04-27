package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSort implements Comparator<Employee> {

    public static void main(String[] args) {
        Employee e1 = new Employee(1, "Bob", 25, 35L);
        Employee e2 = new Employee(2, "Bill", 35, 35L);
        Employee e3 = new Employee(3, "Anne", 25, 350L);

        Employee[] employees = new Employee[]{e1, e2, e3};
        Employee[] employees2 = Arrays.copyOf(employees, 3);

        List<Employee> employeeList = Arrays.asList(employees);
        List<Employee> employeeList2 = Arrays.asList(employees2);
        Collections.sort(employeeList);

        EmployeeSort sorter = new EmployeeSort();
        employeeList2.sort(sorter);

        System.out.println(employeeList);
        System.out.println(employeeList2);
    }

    @Override
    public int compare(Employee o1, Employee o2) {
        if(o1 == o2 || o1.getAge() == o2.getAge() && o1.getSalary() == o2.getSalary()) {
            return 0;
        }

        if(o1.getAge() == o2.getAge()) {
            return o1.getSalary() > o2.getSalary() ? 1 : -1;
        }

        return o1.getAge() > o2.getAge() ? 1 : -1;
    }
}
