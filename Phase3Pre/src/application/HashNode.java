package application;

public class HashNode {
	 String date;
	char flag; // E: empty, F: full, D: deleted
	AVLTree MartyrsAVLTree;

	public HashNode() {
	}

	HashNode(String date, char flag) {
		this.date = date;
		this.flag = flag;
		this.MartyrsAVLTree = new AVLTree();
	}

	public HashNode(String dateString, char c, AVLTree avlTree) {
		date=dateString;
		flag=c;
		MartyrsAVLTree=avlTree;
	}
}
