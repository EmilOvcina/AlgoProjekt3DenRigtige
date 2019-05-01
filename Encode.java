import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encode 
{		
	private static int[] array;
	
	/**
	*	Main-metoden
	*/
	public static void main(String[] args)
	{
		//Instans af encode klassen
		Encode en = new Encode();
		
		//Fylder hyppighedsarrayet op med ASCII værdierne fra inputfilen
		array = new int[256];
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(args[0]);
			
			while(stream.available() > 0)
			{
				int r = stream.read();
				array[r]++;
			}
			
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Laver arrayet over huffman koderne
		String[] stringArray = new String[256];
		
		Element root = en.huffManAlgorithm(array);
		en.treeWalk(root, "", stringArray);
		
		//Skriver både hyppighedstabellen og huffmankoderne til outputfilen
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream(args[1]));
			stream = new FileInputStream(args[0]);
			
			for(int i = 0; i < 256; i++)
			{
				out.writeInt(array[i]);
			}
			
			while(stream.available() > 0)
			{
				int r = stream.read();
				
				for(int i = 0; i < stringArray[r].length(); i++)
				{
					int c = Integer.parseInt(""+stringArray[r].charAt(i));
					out.writeBit(c);
				}
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	/*
	*	HuffMan algoritmen som bruger prioritetskøen til at lave huffmantræet 
	*/
	public Element huffManAlgorithm(int[] inputArray)
	{
		PQHeap heap = new PQHeap(256);
		for(int i = 0; i < inputArray.length; i++)
		{
			heap.insert(new Element(inputArray[i], i));
		}
		
		int n = inputArray.length;
		for(int i = 0; i < n-1; i++)
		{
			Element x = heap.extractMin();
			Element y = heap.extractMin();
			Element z = new Element(x.getKey() + y.getKey(), new Tree(x,y));
			heap.insert(z);
		}
			
		return heap.extractMin();
	}
	
	/*
	* Inorder tree walk algoritmen som bruges til at gå igennem hoffmantræet og lægge det rigtige bit til arrayet af strings
	*/
	public void treeWalk(Element el, String string, String[] array)
	{
		if(el.getData() != null) {
			if(el.getData() instanceof Tree)
			{
				Tree tree = (Tree) el.getData();
				treeWalk(tree.getLeft(), string+"0", array);
				treeWalk(tree.getRight(), string+"1", array);	
			} else {
				array[(int)el.getData()] = string;
			}
		}
	}
}