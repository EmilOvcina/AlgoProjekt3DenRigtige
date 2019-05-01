
public class Tree 
{	
	//Laver et nyt træ-objekt, som tager vesntre og højre barn som parameter
	public Tree(Element left, Element right)
	{
		this.left = left;
		this.right = right;
	}
	private Element left;
	private Element right;
	
	//Getters for børnene
	public Element getLeft() {
		return left;
	}
	public Element getRight() {
		return right;
	} 	
}