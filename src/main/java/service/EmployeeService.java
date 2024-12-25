package service;

import model.Employee;
import datastructures.AVLTree;

import java.util.List;
import java.util.ArrayList;

public class EmployeeService {
    private AVLTree employeeTree;

    public EmployeeService() {
        this.employeeTree = new AVLTree();
    }

    public void addEmployee(Employee employee) {
        employeeTree.insert(employee);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeTree.inOrderTraversal(employee -> employeeList.add(employee));
        return employeeList;
    }

    public Employee getEmployeeById(String employeeId) {
        return findEmployeeById(employeeId, employeeTree);
    }

    private Employee findEmployeeById(String employeeId, AVLTree tree) {
        return findEmployeeByIdRec(employeeId, tree.getRoot());
    }

    private Employee findEmployeeByIdRec(String employeeId, AVLTree.Node node) {
        if (node == null) {
            return null;
        }

        int comparison = employeeId.compareTo(node.employee.getEmployeeId());

        if (comparison < 0) {
            return findEmployeeByIdRec(employeeId, node.left);
        } else if (comparison > 0) {
            return findEmployeeByIdRec(employeeId, node.right);
        } else {
            return node.employee;
        }
    }

    public boolean updateEmployee(String employeeId, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(employeeId);
        if (existingEmployee != null) {
            deleteEmployee(employeeId);
            addEmployee(updatedEmployee);
            return true;
        }
        return false;
    }

    public boolean deleteEmployee(String employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee != null) {
            employeeTree.delete(employee);
            return true;
        }
        return false;
    }

    public int getEmployeeCount() {
        return getAllEmployees().size();
    }

    public String getSystemSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Total Employees: ").append(getEmployeeCount()).append("\n");
        for (Employee employee : getAllEmployees()) {
            summary.append(employee.toString()).append("\n");
        }
        return summary.toString();
    }
}
