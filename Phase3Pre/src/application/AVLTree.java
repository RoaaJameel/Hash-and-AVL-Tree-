package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AVLTree {
	TNode root;
	int size; // Add a size variable to keep track of the number of nodes

	public AVLTree() {
		this.root = null;
		this.size = 0;
	}

	public int size() {
		return size(root);
	}

	private int size(TNode node) {
		if (node == null) {
			return 0;
		} else {
			return size(node.left) + size(node.right) + 1;
		}
	}

	private TNode rotateRight(TNode y) {
		TNode x = y.left;
		TNode T2 = x.right;

		x.right = y;
		y.left = T2;

		updateHeight(y);
		updateHeight(x);

		return x;
	}

	private TNode rotateLeft(TNode x) {
		TNode y = x.right;
		TNode T2 = y.left;

		y.left = x;
		x.right = T2;

		updateHeight(x);
		updateHeight(y);

		return y;
	}

	private int height(TNode n) {
		return (n == null) ? 0 : n.height;
	}

	private void updateHeight(TNode n) {
		n.height = 1 + Math.max(height(n.left), height(n.right));
	}

	public int getBalance(TNode n) {
		return (n == null) ? 0 : height(n.left) - height(n.right);
	}

	private TNode rebalance(TNode node) {
		updateHeight(node);
		int balance = getBalance(node);

		if (balance > 1) {
			if (getBalance(node.left) < 0) {
				node.left = rotateLeft(node.left);
			}
			return rotateRight(node);
		}

		if (balance < -1) {
			if (getBalance(node.right) > 0) {
				node.right = rotateRight(node.right);
			}
			return rotateLeft(node);
		}

		return node;
	}

	public void insert(Martyr data) {
		root = insert(root, data);
		size++; // Increment the size after insertion
	}

	private TNode insert(TNode node, Martyr data) {
		if (node == null) {
			return new TNode(data);
		}

		int comparison = data.compareTo(node.data);
		if (comparison < 0) {
			node.left = insert(node.left, data);
		} else if (comparison > 0) {
			node.right = insert(node.right, data);
		} else {
			return node; // Duplicate data is not allowed
		}

		return rebalance(node);
	}

	public void delete(Martyr data) {
		if (root == null)
			return;
		root = delete(root, data);
	}

	private TNode delete(TNode root, Martyr data) {
		if (root == null)
			return null;

		int compareResult = data.compareTo(root.data);
		if (compareResult < 0) {
			root.left = delete(root.left, data);
		} else if (compareResult > 0) {
			root.right = delete(root.right, data);
		} else {
			if (root.left == null) {
				return root.right;
			} else if (root.right == null) {
				return root.left;
			}

			TNode temp = findMin(root.right);
			root.data = temp.data;
			root.right = delete(root.right, temp.data);
		}

		return rebalance(root);
	}

	private TNode findMin(TNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public Martyr find(Object data) {
		return find(root, (Martyr) data);
	}

	private Martyr find(TNode node, Martyr data) {
		if (node == null || data.compareTo(node.data) == 0) {
			if (node == null) {
				return null;
			} else {
				return node.data;
			}
		}
		if (data.compareTo(node.data) < 0) {
			return find(node.left, data);
		} else {
			return find(node.right, data);
		}
	}

	public TNode getRoot() {
		return root;
	}

	public int height() {
		return height(root);
	}

	public void preorder(TNode root) {
		if (root != null) {
			System.out.print(root.data + " ");
			preorder(root.left);
			preorder(root.right);
		}
	}

	public void preorder() {
		preorder(root);

	}

	public String preOrder(TNode root) {
		StringBuilder result = new StringBuilder();
		if (root != null) {
			result.append(root.data).append(" ");
			result.append("                   ");
			result.append(preOrder(root.left));
			result.append(preOrder(root.right));
		}
		return result.toString();
	}

	public String preOrder() {
		return preOrder(root);
	}

	@Override
	public String toString() {
		return "AVLTree [root=" + root + "]";
	}

	public Stack<Martyr> getAllMartyrs() {
		Stack<Martyr> martyrsStack = new Stack<>();
		getAllMartyrs(root, martyrsStack);
		return martyrsStack;
	}

	private void getAllMartyrs(TNode node, Stack<Martyr> martyrsStack) {
		if (node == null) {
			return;
		}

		// Traverse left subtree
		getAllMartyrs(node.left, martyrsStack);

		// Push current node's data onto the stack
		martyrsStack.push(node.data);

		// Traverse right subtree
		getAllMartyrs(node.right, martyrsStack);
	}

	public List<String> getAllDistricts() {
		List<String> districts = new ArrayList<>();
		collectDistricts(root, districts);
		return districts;
	}

	private void collectDistricts(TNode node, List<String> districts) {
		if (node == null) {
			return;
		}
		collectDistricts(node.left, districts);
		if (!districts.contains(node.data.getDistrict())) {
			districts.add(node.data.getDistrict());
		}
		collectDistricts(node.right, districts);
	}

	public List<String> getAllLocationsForDistrict(String districtName) {
		List<String> locations = new ArrayList<>();
		collectLocationsForDistrict(root, districtName, locations);
		return locations;
	}

	private void collectLocationsForDistrict(TNode node, String districtName, List<String> locations) {
		if (node == null) {
			return;
		}
		collectLocationsForDistrict(node.left, districtName, locations);
		if (node.data.getDistrict().equals(districtName)) {
			locations.add(node.data.getLocation());
		}
		collectLocationsForDistrict(node.right, districtName, locations);
	}

	// Method to check if the tree is empty
	public boolean isEmpty() {
		return root == null;
	}

}
