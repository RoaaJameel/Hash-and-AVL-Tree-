package application;

public class TNode {
	Martyr data;
	TNode left;
	TNode right;
	int height;

	public TNode(Martyr data) {
		this.data = data;
		this.height = 1; // height of a new node is 1
	}

	public TNode() {
		this.height = 1;
	}

	public void setData(Martyr data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public TNode getLeft() {
		return left;
	}

	public void setLeft(TNode left) {
		this.left = left;
	}

	public TNode getRight() {
		return right;
	}

	public void setRight(TNode right) {
		this.right = right;
	}

	public boolean isLeaf() {
		return (left == null && right == null);
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	public int getHeight() {
		return height;
	}

	public boolean isEmpty() {
		return left == null && right == null;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
