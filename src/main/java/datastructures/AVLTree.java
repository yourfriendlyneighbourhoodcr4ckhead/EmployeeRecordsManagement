package datastructures;

import model.Employee;

public class AVLTree {
    private class Node {
        Employee employee;
        Node left, right;
        int height;

        public Node(Employee employee) {
            this.employee = employee;
            this.height = 1;
        }
    }

    private Node root;

    // Utility method to get the height of a node
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Utility method to get the balance factor of a node
    private int getBalanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Right rotation
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotation
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a new employee into the AVL tree
    public void insert(Employee employee) {
        root = insert(root, employee);
    }

    private Node insert(Node node, Employee employee) {
        if (node == null) {
            return new Node(employee);
        }

        if (employee.getEmployeeId().compareTo(node.employee.getEmployeeId()) < 0) {
            node.left = insert(node.left, employee);
        } else if (employee.getEmployeeId().compareTo(node.employee.getEmployeeId()) > 0) {
            node.right = insert(node.right, employee);
        } else {
            return node; // Duplicate employee IDs are not allowed
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalanceFactor(node);

        // Left-Left Case
        if (balance > 1 && employee.getEmployeeId().compareTo(node.left.employee.getEmployeeId()) < 0) {
            return rightRotate(node);
        }

        // Right-Right Case
        if (balance < -1 && employee.getEmployeeId().compareTo(node.right.employee.getEmployeeId()) > 0) {
            return leftRotate(node);
        }

        // Left-Right Case
        if (balance > 1 && employee.getEmployeeId().compareTo(node.left.employee.getEmployeeId()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right-Left Case
        if (balance < -1 && employee.getEmployeeId().compareTo(node.right.employee.getEmployeeId()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Search for an employee by ID
    public Employee search(String employeeId) {
        Node node = search(root, employeeId);
        return node == null ? null : node.employee;
    }

    private Node search(Node node, String employeeId) {
        if (node == null || node.employee.getEmployeeId().equals(employeeId)) {
            return node;
        }

        if (employeeId.compareTo(node.employee.getEmployeeId()) < 0) {
            return search(node.left, employeeId);
        } else {
            return search(node.right, employeeId);
        }
    }

    // In-order traversal to display employees sorted by employeeId
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.employee);
            inOrderTraversal(node.right);
        }
    }

    // Delete an employee from the AVL tree
    public void delete(String employeeId) {
        root = delete(root, employeeId);
    }

    private Node delete(Node node, String employeeId) {
        if (node == null) {
            return node;
        }

        if (employeeId.compareTo(node.employee.getEmployeeId()) < 0) {
            node.left = delete(node.left, employeeId);
        } else if (employeeId.compareTo(node.employee.getEmployeeId()) > 0) {
            node.right = delete(node.right, employeeId);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node temp = getMinNode(node.right);
                node.employee = temp.employee;
                node.right = delete(node.right, temp.employee.getEmployeeId());
            }
        }

        if (node == null) {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalanceFactor(node);

        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node getMinNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Main method for testing
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        Employee e1 = new Employee("1001", "John", "Doe", "HR", "Manager", 50000);
        Employee e2 = new Employee("1002", "Jane", "Smith", "IT", "Developer", 60000);
        Employee e3 = new Employee("1003", "Jim", "Brown", "Sales", "Executive", 45000);

        tree.insert(e1);
        tree.insert(e2);
        tree.insert(e3);

        System.out.println("In-order traversal:");
        tree.inOrderTraversal();

        System.out.println("\nSearching for Employee with ID 1002:");
        System.out.println(tree.search("1002"));

        System.out.println("\nDeleting Employee with ID 1001:");
        tree.delete("1001");

        System.out.println("\nIn-order traversal after deletion:");
        tree.inOrderTraversal();
    }
}
