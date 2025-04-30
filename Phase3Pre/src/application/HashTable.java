package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTable {
	private final int initialSize = 11;
	private int size; // Current size of the hash table
	private HashNode[] table; // Array to store hash nodes

	// Constructor to initialize the hash table
	public HashTable() {
		this.size = initialSize;
		this.table = new HashNode[size];
		// Fill the hash table with empty nodes
		Arrays.fill(this.table, new HashNode(null, 'E'));
	}

	public int hash(String key, int tableSize) {
		int hashValue = 0;
		for (int i = 0; i < key.length(); i++) {
			hashValue += key.charAt(i) * Math.pow(27, i);
		}
		return hashValue % tableSize;
	}

	private void rehash() {
		int newSize = getNextPrime(size * 2);
		HashNode[] oldTable = table;
		size = newSize;
		table = new HashNode[size];

		Arrays.fill(table, null);// Initialize the new table with null values

		for (HashNode node : oldTable) {
			if (node != null && node.flag == 'F') {
				reinsert(node);
			}
		}
	}

	private void reinsert(HashNode node) {
		int hash = hash(node.date, table.length);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % table.length;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during reinsert: " + quadraticProbe);
			}
			if (table[quadraticProbe] == null || table[quadraticProbe].flag == 'E') {
				table[quadraticProbe] = node;
				break;
			}
			if (table[quadraticProbe].date.equals(node.date)) {
				// If the node's date matches the date of the node in the table,
				// insert into the AVL tree of the existing node
				table[quadraticProbe].MartyrsAVLTree.insert(node.MartyrsAVLTree.root.data);
				return;
			}
			i++;
			if (i > table.length) { // Prevent infinite loops in case of a full table
				throw new IllegalStateException("Unable to reinsert node: " + node.date);
			}
		}
	}

	// Method to insert a martyr into the hash table
	public void insert(String date, Martyr martyr) {
		// rehash the table if it becomes half full
		if ((double) countFilledSlots() / size > 0.5) {
			rehash();
		}

		int hash = hash(date, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during insert: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E' || node.flag == 'D') {
				// Create a new node if the current slot is empty or deleted
				HashNode newNode = new HashNode(date, 'F');
				newNode.MartyrsAVLTree = new AVLTree();
				newNode.MartyrsAVLTree.insert(martyr);
				table[quadraticProbe] = newNode;
				return;
			} else if (node.date.equals(date)) {
				// If the date matches, insert the martyr into the existing AVLTree
				node.MartyrsAVLTree.insert(martyr);
				return;
			}
			i++;
		}
	}

	// Method to insert an AVL tree into the hash table
	public void insert(String date, AVLTree martyrs) {
		// rehash the table if it becomes half full
		if ((double) countFilledSlots() / size > 0.5) {
			rehash();
		}

		int hash = hash(date, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during insert: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node.flag == 'E' || node.flag == 'D') {
				node.date = date;
				node.flag = 'F';
				node.MartyrsAVLTree = martyrs;
				return;
			} else if (node.date.equals(date)) {
				node.MartyrsAVLTree = martyrs;
				return;
			}
			i++;
		}
	}

	// Method to find the AVL tree associated with a date
	public AVLTree find(String date) {
		int hash = hash(date, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during find: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				return null; // If the slot is empty or the node is null, the item is not in the table
			} else if (node.flag == 'D') {
				// If the node is marked as deleted, continue probing
				i++;
				if (i > size) { // Prevent infinite loops in case of a full table
					return null;
				}
				continue; // Skip the rest of the loop and continue probing
			} else if (node.date != null && node.date.equals(date) && node.flag == 'F') {
				return node.MartyrsAVLTree; // Return the AVLTree if the date matches and the slot is filled
			}
			i++;
			if (i > size) { // Prevent infinite loops in case of a full table
				return null;
			}
		}
	}

	public boolean findDate(String date) {
		int hash = hash(date, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during find: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				return false; // If the slot is empty or the node is null, the item is not in the table
			} else if (node.flag == 'D') {
				// If the node is marked as deleted, continue probing
				i++;
				if (i > size) { // Prevent infinite loops in case of a full table
					return false;
				}
				continue; // Skip the rest of the loop and continue probing
			} else if (node.date != null && node.date.equals(date) && node.flag == 'F') {
				return true; // Return true if the date matches and the slot is filled
			}
			i++;
			if (i > size) { // Prevent infinite loops in case of a full table
				return false;
			}
		}
	}

	public boolean isExists(String date) {
		for (int i = 0; i < size; i++) {
			HashNode node = table[i];
			if (node != null && node.date == date) {
				return true;
			}

		}
		return false;
	}

	public void printAllTable() {
		System.out.println("The size is: " + size);
		for (int i = 0; i < size; i++) {
			HashNode node = table[i];
			if (node == null || node.date == null) {
				System.out.println(i + ": null");
			} else {
				System.out.println(i + ": " + node.date);
			}
		}
	}

	// Method to get the next prime number
	private int getNextPrime(int n) {
		while (!isPrime(n)) {
			n++;
		}
		return n;
	}

	// Method to check if a number is prime
	public static boolean isPrime(int num) {
		if (num <= 1) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	// Method to count the number of filled slots in the hash table
	public int countFilledSlots() {
		int count = 0;
		for (HashNode node : table) {
			if (node != null && node.flag == 'F') {
				count++;
			}
		}
		return count;
	}

	// Method to display the contents of the hash table
	public void displayHashTableWithAVLTree() {
		for (int i = 0; i < size; i++) {
			HashNode node = table[i];
			if (node != null && node.flag == 'F') {
				System.out.print(i + ": " + node.date + " ----> ");
				node.MartyrsAVLTree.preorder();
				System.out.println();
			}
		}
	}

	// Method to insert a martyr into the hash table
	public void insert(LocalDate date, Martyr martyr) {
		String dateString = formatDate(date);
		if ((double) countFilledSlots() / size > 0.5) {
			rehash();
		}

		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during insert: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E' || node.flag == 'D') {
				HashNode newNode = new HashNode(dateString, 'F');
				newNode.MartyrsAVLTree = new AVLTree();
				newNode.MartyrsAVLTree.insert(martyr);
				table[quadraticProbe] = newNode;
				return;
			} else if (node.date.equals(dateString)) {
				node.MartyrsAVLTree.insert(martyr);
				return;
			}
			i++;
		}
	}

	// Method to insert an AVL tree into the hash table
	public void insert(LocalDate date, AVLTree martyrs) {
		String dateString = formatDate(date);
		if ((double) countFilledSlots() / size > 0.5) {
			rehash();
		}

		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during insert: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E' || node.flag == 'D') {
				// Create a new node if the current slot is empty or deleted
				HashNode newNode = new HashNode(dateString, 'F');
				newNode.MartyrsAVLTree = martyrs;
				table[quadraticProbe] = newNode;
				return;
			} else if (node.date.equals(dateString)) {
				// If the date matches, update the AVLTree
				node.MartyrsAVLTree = martyrs;
				return;
			}
			i++;
		}
	}

	// Method to find the AVL tree associated with a date
	public AVLTree find(LocalDate date) {
		String dateString = formatDate(date);
		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during find: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				return null; // If the slot is empty or the node is null, the item is not in the table
			} else if (node.flag == 'D') {
				// If the node is marked as deleted, continue probing
				i++;
				if (i > size) { // Prevent infinite loops in case of a full table
					return null;
				}
				continue; // Skip the rest of the loop and continue probing
			} else if (node.date != null && node.date.equals(dateString) && node.flag == 'F') {
				return node.MartyrsAVLTree; // Return the AVLTree if the date matches and the slot is filled
			}
			i++;
			if (i > size) { // Prevent infinite loops in case of a full table
				return null;
			}
		}
	}

	public String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
		return date.format(formatter);
	}

	public boolean findDate(LocalDate date) {
		String dateString = formatDate(date);
		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during find: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				return false; // If the slot is empty or the node is null, the date is not in the table
			} else if (node.flag == 'D') {
				// If the node is marked as deleted, continue probing
				i++;
				if (i > size) { // Prevent infinite loops in case of a full table
					return false;
				}
				continue; // Skip the rest of the loop and continue probing
			} else if (node.date != null && node.date.equals(dateString) && node.flag == 'F') {
				return true; // Return true if the date matches and the slot is filled
			}
			i++;
			if (i > size) { // Prevent infinite loops in case of a full table
				return false;
			}
		}
	}

	public void delete(String date) {
		int hash = hash(date, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during delete: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				// If the slot is empty or the node is null, the item is not in the table
				return; // Node not found for deletion
			} else if (node.date != null && node.date.equals(date) && node.flag == 'F') {
				// Found the node to delete
				node.flag = 'D'; // Mark the node as deleted
				node.MartyrsAVLTree = null;
				return; // Node deleted successfully
			}
			i++;
			if (i > size) {
				// Prevent infinite loops in case of a full table
				return; // Node not found for deletion
			}
		}
	}

	public void delete(LocalDate date) {
		String dateString = formatDate(date);
		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during delete: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E') {
				// If the slot is empty or the node is null, the item is not in the table
				return; // Node not found for deletion
			} else if (node.date != null && node.date.equals(dateString) && node.flag == 'F') {
				// Found the node to delete
				node.flag = 'D'; // Mark the node as deleted
				node.MartyrsAVLTree = null;// Mark the avl as null
				return; // Node deleted successfully
			}
			i++;
			if (i > size) {
				// Prevent infinite loops in case of a full table
				return; // Node not found for deletion
			}
		}
	}

	public void insertDate(LocalDate date) {
		String dateString = formatDate(date);
		if ((double) countFilledSlots() / size > 0.5) {
			rehash();
		}

		int hash = hash(dateString, size);
		int i = 0;
		int quadraticProbe;

		while (true) {
			quadraticProbe = (hash + i * i) % size;
			if (quadraticProbe < 0) {
				throw new IllegalStateException("Negative index during insert: " + quadraticProbe);
			}
			HashNode node = table[quadraticProbe];
			if (node == null || node.flag == 'E' || node.flag == 'D') {
				// Create a new node if the current slot is empty or deleted
				HashNode newNode = new HashNode(dateString, 'F');
				table[quadraticProbe] = newNode;
				return;
			} else if (node.date.equals(dateString)) {
				// If the date already exists in the table, return without inserting
				return;
			}
			i++;
		}
	}

	public void update(LocalDate oldDate, LocalDate newDate) {
		// Check if the old date exists in the hash table
		if (findDate(oldDate)) {
			// Find the AVLTree associated with the old date
			AVLTree martyrs = find(oldDate);

			// If martyrs is null, handle this case
			if (martyrs == null) {
				throw new IllegalStateException("No AVLTree found for the old date: " + oldDate);
			}

			// Check if the new date already exists in the hash table
			if (findDate(newDate)) {
				// If the new date exists, merge the AVL trees
				AVLTree existingMartyrs = find(newDate);
				updateMartyrDates(martyrs.root, oldDate, newDate); // Update the dates of all martyrs to the new date
				mergeAVLTrees(existingMartyrs, martyrs);
			} else {
				updateMartyrDates(martyrs.root, oldDate, newDate); // Update the dates of all martyrs to the new date
				insertDate(newDate);// Insert the new date into the hash table if it doesn't exist
				insert(newDate, martyrs);// Insert the AVLTree associated with the new date into the hash table
			}

			// Delete the old date from the hash table
			delete(oldDate);
		} else {
			throw new IllegalStateException("Old date not found in the hash table: " + oldDate);
		}
	}

	private void mergeAVLTrees(AVLTree targetTree, AVLTree sourceTree) {
		// Traverse the source tree and insert each martyr into the target tree
		mergeNodes(targetTree, sourceTree.root);
	}

	private void mergeNodes(AVLTree targetTree, TNode node) {
		if (node != null) {
			targetTree.insert(node.data); // Insert the current node's data into the target tree
			mergeNodes(targetTree, node.left); // Recursively merge the left subtree
			mergeNodes(targetTree, node.right); // Recursively merge the right subtree
		}
	}

	private void updateMartyrDates(TNode node, LocalDate oldDate, LocalDate newDate) {
		if (node != null) {
			// Update the date of the martyr to the new date
			node.data.setDateOfDeath(newDate.toString());

			// Recursively update the dates in the left and right subtrees
			updateMartyrDates(node.left, oldDate, newDate);
			updateMartyrDates(node.right, oldDate, newDate);
		}
	}

	public void printMartyrsByDate(LocalDate date) {
		AVLTree martyrsAVLTree = find(date);
		if (martyrsAVLTree != null) {
			martyrsAVLTree.preorder();
		} else {
			System.out.println("No martyrs found for the date: " + formatDate(date));
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public HashNode[] getTable() {
		return table;
	}

	public void setTable(HashNode[] table) {
		this.table = table;
	}

	public int getNumberOfMartyrs(LocalDate date) {
		// Find the AVL tree associated with the given date
		AVLTree martyrsAVLTree = find(date);

		// If AVL tree is found, return the size (number of martyrs)
		if (martyrsAVLTree != null) {
			return martyrsAVLTree.size();
		} else {
			// If AVL tree is not found, return 0
			return 0;
		}
	}

	public String maxMartyrsDistrictOnDate(String date) {
		// Find the AVL tree associated with the given date
		AVLTree martyrsAVLTree = find(LocalDate.parse(date));

		if (martyrsAVLTree == null) {
			// If no martyrs are found for the given date, return null
			return null;
		}

		// Initialize variables to store the maximum number of martyrs and the
		// corresponding district
		int maxMartyrs = 0;
		String maxDistrict = null;

		// traverse the AVL tree
		TNode current = martyrsAVLTree.root;
		while (current != null) {
			// Check if the current node's district has more martyrs than the current
			// maximum
			int districtMartyrs = countMartyrsInDistrict(martyrsAVLTree, current.data.getDistrict());
			if (districtMartyrs > maxMartyrs) {
				maxMartyrs = districtMartyrs;
				maxDistrict = current.data.getDistrict();
			}

			// Move to the next node
			current = successor(martyrsAVLTree.root, current);
		}

		return maxDistrict;
	}

	public int countMartyrsInDistrict(AVLTree tree, String district) {
		return countMartyrsInDistrict(tree.root, district);
	}

	private int countMartyrsInDistrict(TNode node, String district) {
		if (node == null) {
			return 0;
		}

		// Check if the current node's district matches the given district
		int count = node.data.getDistrict().equals(district) ? 1 : 0;

		// Recursively count martyrs in the left and right subtrees
		count += countMartyrsInDistrict(node.left, district);
		count += countMartyrsInDistrict(node.right, district);

		return count;
	}

	public TNode successor(TNode root, TNode node) {
		if (node == null) {
			return null;
		}
		// If the right subtree of the node is not null, return the minimum node in the
		// right subtree
		if (node.right != null) {
			return minValueNode(node.right);
		}
		// If the right subtree is null, traverse up the tree to find the successor
		TNode successor = null;
		while (root != null) {
			if (node.data.getDateOfDeath().compareTo(root.data.getDateOfDeath()) < 0) {
				successor = root;
				root = root.left;
			} else if (node.data.getDateOfDeath().compareTo(root.data.getDateOfDeath()) > 0) {
				root = root.right;
			} else {
				break;
			}
		}
		return successor;
	}

	private TNode minValueNode(TNode node) {
		TNode current = node;
		// Find the leftmost node in the subtree
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	public String maxMartyrsLocationOnDate(String date) {
		// Find the AVL tree associated with the given date
		AVLTree martyrsAVLTree = find(LocalDate.parse(date));

		if (martyrsAVLTree == null) {
			// If no martyrs are found for the given date, return null
			return null;
		}

		// Initialize variables to store the maximum number of martyrs and the
		// corresponding location
		int maxMartyrs = 0;
		String maxLocation = null;

		// traverse the AVL tree
		TNode current = martyrsAVLTree.root;
		while (current != null) {
			// Check if the current node's location has more martyrs than the current
			// maximum
			int locationMartyrs = countMartyrsInLocation(martyrsAVLTree, current.data.getLocation());
			if (locationMartyrs > maxMartyrs) {
				maxMartyrs = locationMartyrs;
				maxLocation = current.data.getLocation();
			}

			// Move to the next node
			current = successor(martyrsAVLTree.root, current);
		}

		return maxLocation;
	}

	public int countMartyrsInLocation(AVLTree tree, String location) {
		return countMartyrsInLocation(tree.root, location);
	}

	private int countMartyrsInLocation(TNode node, String location) {
		if (node == null) {
			return 0;
		}

		// Check if the current node's location matches the given location
		int count = node.data.getLocation().equals(location) ? 1 : 0;

		// Recursively count martyrs in the left and right subtrees
		count += countMartyrsInLocation(node.left, location);
		count += countMartyrsInLocation(node.right, location);

		return count;
	}

	public List<String> getAllDistricts() {
		List<String> districts = new ArrayList<>();

		// Iterate through each node in the table
		for (HashNode node : table) {
			addDistrictsFromNode(node, districts);
		}

		return districts;
	}

	// Method to add districts from a node to the list
	private void addDistrictsFromNode(HashNode node, List<String> districts) {
		if (node != null && node.flag == 'F') {
			// Get the AVL tree associated with the current node
			AVLTree avlTree = node.MartyrsAVLTree;
			// If AVL tree is not null, add its districts to the list
			if (avlTree != null) {
				addDistrictsFromAVLTree(avlTree.root, districts);
			}
		}
	}

	// Method to add districts from an AVL tree to the list
	private void addDistrictsFromAVLTree(TNode node, List<String> districts) {
		if (node != null) {
			// Recursively add districts from the left subtree
			addDistrictsFromAVLTree(node.left, districts);

			// Add the district of the current node if it's not already in the list
			String district = node.data.getDistrict();
			if (!districts.contains(district)) {
				districts.add(district);
			}

			// Recursively add districts from the right subtree
			addDistrictsFromAVLTree(node.right, districts);
		}
	}

	public List<String> getAllLocationsForDistrict(String districtName) {
		List<String> locations = new ArrayList<>();
		// Add the district name once at the beginning
		locations.add(districtName);

		// Iterate through each element in the hash table
		for (HashNode node : table) {
			// Check if the node is not null and its flag is 'F'
			if (node != null && node.flag == 'F') {
				// Get the AVL tree associated with the current node
				AVLTree avlTree = node.MartyrsAVLTree;
				// If AVL tree is not null, collect locations for the given district
				if (avlTree != null) {
					// Get the root of the AVL tree using the getRoot() method
					TNode root = avlTree.getRoot();
					collectLocationsForDistrict(root, districtName, locations);
				}
			}
		}

		return locations;
	}

	private void collectLocationsForDistrict(TNode node, String districtName, List<String> locations) {
		if (node == null) {
			return;
		}
		collectLocationsForDistrict(node.left, districtName, locations);

		if (node.data.getDistrict().equals(districtName)) {
			String location = node.data.getLocation();
			// Check if the location is not already in the list
			if (!locations.contains(location)) {
				locations.add(location);
			}
		}
		collectLocationsForDistrict(node.right, districtName, locations);
	}

}