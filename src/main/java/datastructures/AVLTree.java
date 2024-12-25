package datastructures;

import model.Employee;

public class AVLTree {

    // Node class to represent each node in the AVL Tree
    public class Node {
        public Employee employee;  // Changed to public to allow access
        public Node left, right;   // Changed to public to allow access
        public int height;

        public Node(Employee employee) {
            this.employee = employee;
            this.height = 1;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
    }

    // Get height of the node
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Get balance factor of the node
    private int getBalanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Update the height of the node
    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // Right rotate the subtree rooted with y
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotate the subtree rooted with x
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Insert a new employee into the AVL tree
    public void insert(Employee employee) {
        root = insertRec(root, employee);
    }

    private Node insertRec(Node node, Employee employee) {
        if (node == null) {
            return new Node(employee);
        }

        // Compare employee ID and navigate
        int comparison = employee.getEmployeeId().compareTo(node.employee.getEmployeeId());

        if (comparison < 0) {
            node.left = insertRec(node.left, employee);
        } else if (comparison > 0) {
            node.right = insertRec(node.right, employee);
        } else {
            return node; // Duplicate employee ID
        }

        updateHeight(node);

        // Balance the node
        int balance = getBalanceFactor(node);

        // Left heavy situation
        if (balance > 1 && employee.getEmployeeId().compareTo(node.left.employee.getEmployeeId()) < 0) {
            return rightRotate(node);
        }

        // Right heavy situation
        if (balance < -1 && employee.getEmployeeId().compareTo(node.right.employee.getEmployeeId()) > 0) {
            return leftRotate(node);
        }

        // Left-right heavy situation
        if (balance > 1 && employee.getEmployeeId().compareTo(node.left.employee.getEmployeeId()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right-left heavy situation
        if (balance < -1 && employee.getEmployeeId().compareTo(node.right.employee.getEmployeeId()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // In-order traversal to get all employees in sorted order
    public void inOrderTraversal(java.util.function.Consumer<Employee> action) {
        inOrderTraversalRec(root, action);
    }

    private void inOrderTraversalRec(Node node, java.util.function.Consumer<Employee> action) {
        if (node != null) {
            inOrderTraversalRec(node.left, action);
            action.accept(node.employee);
            inOrderTraversalRec(node.right, action);
        }
    }

    // Delete an employee by ID
    public void delete(Employee employee) {
        root = deleteRec(root, employee);
    }

    private Node deleteRec(Node root, Employee employee) {
        if (root == null) {
            return root;
        }

        // Compare employee ID and navigate
        int comparison = employee.getEmployeeId().compareTo(root.employee.getEmployeeId());

        if (comparison < 0) {
            root.left = deleteRec(root.left, employee);
        } else if (comparison > 0) {
            root.right = deleteRec(root.right, employee);
        } else {
            // Node with the employee found
            if (root.left == null || root.right == null) {
                Node temp = root.left != null ? root.left : root.right;
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.employee = temp.employee;
                root.right = deleteRec(root.right, temp.employee);
            }
        }

        if (root == null) {
            return root;
        }

        updateHeight(root);

        // Balance the node
        int balance = getBalanceFactor(root);

        // Left heavy situation
        if (balance > 1 && getBalanceFactor(root.left) >= 0) {
            return rightRotate(root);
        }

        // Right heavy situation
        if (balance < -1 && getBalanceFactor(root.right) <= 0) {
            return leftRotate(root);
        }

        // Left-right heavy situation
        if (balance > 1 && getBalanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right-left heavy situation
        if (balance < -1 && getBalanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Get the node with minimum value (for deleting nodes)
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Get the root of the tree
    public Node getRoot() {
        return root;
    }
}
