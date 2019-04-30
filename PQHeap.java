/*
*	@authors 
*		Emil Ovcina - emovc18
*		Markus Jens Tang-Jensen - mtang18
*		Tobias Bruun Rasch - tokja13 
*/
public class PQHeap implements PQ 
{
	/*
	*	Klassens attributter.
	*/
    public Element[] list;
    private int elementCount = 0;

    /*
    *	Constructor. parameter: max antal elementer i heapen.
    */
    public PQHeap(int maxElms)
    { 
    	//Vi starter vores array på index 1 for at gøre det nemmere at implementere pseudokoden 
        list = new Element[maxElms + 1];
        list[0] = null;
    }

    /**
    *	Rekursiv metode som sørger for at et træ er en min-heap 
    */
    public void minHeapify(int index)
    {
    	int l = index * 2;
    	int r = index * 2 + 1;

    	int smallest = index;

    	if(l <= elementCount && list[l].getKey() < list[index].getKey())
    		smallest = l;
    	else 
    		smallest = index;
    	if(r <= elementCount && list[r].getKey() < list[smallest].getKey())
    		smallest = r;
    	if(smallest != index)
    	{
    		swap(index, smallest);
    		minHeapify(smallest);
    	}
    }

    /**
    *	Bytter rundt på to elementer i listen.
    */
    private void swap(int i1, int i2)
    {
    	Element tmp = list[i1];
    	Element tmp2 = list[i2];
		list[i1] = tmp2;
		list[i2] = tmp;
    }

    /**
    *	Overskriver metoden fra interfacen. 
    */
    @Override 
    public Element extractMin() {
    	Element e = list[1];
    	swap(1, elementCount);
    	elementCount--;
    	minHeapify(1);
        return e;
    }

	/**
    *	Overskriver metoden fra interfacen.  Indsætter et element ind i heapen.
    */
    @Override
    public void insert(Element e) 
    {
    	elementCount++;
    	list[elementCount] = e;

    	int i = elementCount;
    	while (i > 1 && list[i / 2].getKey() > list[i].getKey())
    	{
    		swap(i, i / 2);
    		i /= 2;
    	}
    }

    /*
    *	Getter for antallet af elementer.
    */
    public int getElementCount()
    {
    	return elementCount;
    }
}