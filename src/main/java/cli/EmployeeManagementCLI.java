
import java.util.*;
import service.*;
import model.*;


public class EmployeeManagementCLI {

    private static EmployeeService employeeService = new EmployeeService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\n--- Employee Records Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. View All Employees");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    deleteEmployee(scanner);
                    break;
                case 3:
                    viewAllEmployees();
                    break;
                case 4:
                    searchEmployee(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Method to add a new employee
    private static void addEmployee(Scanner scanner) {
        System.out.println("\nEnter employee details:");
        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Employee First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Employee Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Employee Department: ");
        String department = scanner.nextLine();
        System.out.print("Employee Position: ");
        String position = scanner.nextLine();
        System.out.print("Employee Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();  // Consume newline character

        Employee employee = new Employee(employeeId, firstName, lastName, department, position, salary);
        employeeService.addEmployee(employee);
        System.out.println("Employee added successfully!");
    }

    // Method to delete an employee
    private static void deleteEmployee(Scanner scanner) {
        System.out.print("\nEnter the employee ID to delete: ");
        String employeeId = scanner.nextLine();
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee deleted successfully!");
    }

    // Method to view all employees
    private static void viewAllEmployees() {
        System.out.println("\nEmployee List:");
        for (Employee employee : employeeService.getAllEmployees()) {
            System.out.println(employee);
        }
    }

    // Method to search for an employee
    private static void searchEmployee(Scanner scanner) {
        System.out.print("\nEnter employee ID to search: ");
        String employeeId = scanner.nextLine();
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            System.out.println("Employee Found: " + employee);
        } else {
            System.out.println("Employee not found.");
        }
    }
}
