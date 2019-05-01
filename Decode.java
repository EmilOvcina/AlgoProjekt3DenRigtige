import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decode 
{
	/*
	*	Main-metoden
	*/
	public static void main(String[] args)
	{
		//Instans af decode klassen
		Decode de = new Decode();
		
		//Henter hyppighedstabellen fra filen og gemmer i et int array
		FileInputStream stream = null;
		int[] hyppighedsArray = new int[256];
		try {
			stream = new FileInputStream(args[0]);
			BitInputStream in = new BitInputStream(stream);
			
			for(int i = 0; i < 256; i++)
			{
				hyppighedsArray[i] = in.readInt();
			}
			
			//Laver huffmantræet ud fra hyppighedsarreyet 
			Element root = de.huffManAlgorithm(hyppighedsArray);
			int sumOfFreq = de.sumOfFreq(hyppighedsArray);
			
			//Skriver til outputfilen 
			FileOutputStream out = new FileOutputStream(args[1]);
			while(sumOfFreq > 0)
			{
				out.write(de.readFromTree(in, root));
				sumOfFreq--;
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	*	Rekursiv metode som går igennem træet ud fra om input læser 1 eller 0
	* 	Indtil et blad bliver ramt. Returnerer en ASCII værdi
	*/
	public int readFromTree(BitInputStream in, Element e) throws IOException
	{
		if(!(e.getData() instanceof Tree))
			return (int) (e.getData());
		else {
			Tree t = (Tree) e.getData();
			return in.readBit() == 1 ? readFromTree(in, t.getRight()) : readFromTree(in, t.getLeft());
		}
	}
	
	/*
	* HuffMan algoritmen som bruger prioritetskøen til at lave huffmantræet 
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
	*	Summerer frekvenserne sammen fra arrayet, så antal encodet karakterer kendes
	*/
	public int sumOfFreq(int[] array)
	{
		int result = 0;
		for(int i : array)
			result+=i;
		return result;
	}
}