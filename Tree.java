
public class Tree 
{	
	public Tree(Element left, Element right)
	{
		this.left = left;
		this.right = right;
	}
	private Element left;
	private Element right;
	
	public Element getLeft() {
		return left;
	}
	public Element getRight() {
		return right;
	} 	
}