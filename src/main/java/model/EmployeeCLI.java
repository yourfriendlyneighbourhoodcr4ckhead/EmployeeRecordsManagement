package model;

import service.EmployeeService;
import java.util.Scanner;
import java.util.List;

public class EmployeeCLI {
    private static EmployeeService employeeService = new EmployeeService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Get Employee by ID");
            System.out.println("3. Get All Employees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    getEmployeeById(scanner);
                    break;
                case 3:
                    getAllEmployees();
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    // Method to add a new employee
    private static void addEmployee(Scanner scanner) {
        System.out.println("\nEnter Employee Details:");

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Position: ");
        String position = scanner.nextLine();

        System.out.print("Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        Employee employee = new Employee(employeeId, firstName, lastName, department, position, salary);
        employeeService.addEmployee(employee);

        System.out.println("Employee added successfully!");
    }

    // Method to get an employee by ID
    private static void getEmployeeById(Scanner scanner) {
        System.out.print("Enter Employee ID to search: ");
        String employeeId = scanner.nextLine();

        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            System.out.println("\nEmployee Details:");
            System.out.println(employee);
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
        }
    }

    // Method to get all employees
    private static void getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\nAll Employees:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }
}
