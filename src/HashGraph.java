import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 *
 * @author Olof Gren
 * @version 170222
 */
public class HashGraph implements Graph {
	/**
	 * The map edges[v] contains the key-value pair (w, c) if there is an edge
	 * from v to w; c is the cost assigned to this edge. The maps may be null
	 * and are allocated only when needed.
	 */
	private final Map<Integer, Integer>[] edges;
	private final static int INITIAL_MAP_SIZE = 4;

	/** Number of edges in the graph. */
	private int numEdges;

	/**
	 * Constructs a HashGraph with n vertices and no edges. Time complexity:
	 * O(n)
	 *
	 * @throws IllegalArgumentException
	 *             if n < 0
	 */
	public HashGraph(int n)
	{
		if (n < 0)
			throw new IllegalArgumentException("n = " + n);

		// The array will contain only Map<Integer, Integer> instances created
		// in addEdge(). This is sufficient to ensure type safety.
		@SuppressWarnings("unchecked") Map<Integer, Integer>[] a = new HashMap[n];
		edges = a;
	}

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(int from, int to, int cost) {
		if (edges[from] == null)
			edges[from] = new HashMap<Integer, Integer>(INITIAL_MAP_SIZE);
		if (edges[from].put(to, cost) == null)
			numEdges++;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numVertices() {
		return edges.length;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numEdges() {
		return numEdges;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int degree(int v) throws IllegalArgumentException {
		if (fromOK(v) == false)
		{
			throw new IllegalArgumentException();
		}
		if(edges[v] == null)
		{
			return 0;
		}
		int edgesFound = edges[v].size();
		return edgesFound;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public VertexIterator neighbors(int v) {
		int from = v;
		return new NeighborIterator(from);
	}

	private class NeighborIterator implements VertexIterator
	{
		private int currentVertex;
		private LinkedList<Integer> neighbours;

		NeighborIterator(int v)
		{
			currentVertex = v;
			if (edges[v] != null)
			{
				neighbours = new LinkedList(edges[v].keySet());
			}
			else
			{
				neighbours = new LinkedList();
			}   }

		@Override
		public boolean hasNext()
		{
			if (neighbours == null)
			{
				return false;
			}
			if (neighbours.size() == 0)
			{
				return false;
			}
			else
			{
				return true;
			}   }

		@Override
		public int next() throws NoSuchElementException
		{
			if (neighbours == null || neighbours.size() == 0)
			{
				throw new NoSuchElementException();
			}

			int elementToReturn = neighbours.get(0);
			neighbours.remove(0);
			return elementToReturn;
		}
	}
	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public boolean hasEdge(int from, int to) throws IllegalArgumentException
	{
		if (fromOK(from) == false || fromOK(to) == false)
		{
			throw new IllegalArgumentException();
		}
		if (edges[from] == null)
		{
			return false;
		}
		if (edges[from].containsKey(to))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int cost(int from, int to) throws IllegalArgumentException {
		if (fromOK(from) == false || fromOK(to) == false)
		{
			throw new IllegalArgumentException();
		}
		if (this.hasEdge(from, to))
		{
			return edges[from].get(to);
		}
		else
		{
			return NO_COST;
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to) throws IllegalArgumentException
	{
		if (fromOK(from) == false || fromOK(to) == false)
		{
			throw new IllegalArgumentException();
		}
		try
		{
			this.addEdge(from, to, NO_COST);
		}
		catch (IllegalArgumentException l)
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to, int c)
	{
		if (fromOK(from) == false || fromOK(to) == false)
		{
			throw new IllegalArgumentException();
		}
		if(c < 0)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			this.addEdge(from, to, c);
		}
	}
	private boolean fromOK(int from)
	{
		if (from < 0 || from >= edges.length)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w) throws IllegalArgumentException {

		if (fromOK(v) == false || fromOK(w) == false)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			this.addEdge(v, w, NO_COST);
			this.addEdge(w, v, NO_COST);
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w, int c) throws IllegalArgumentException
	{
		if (fromOK(v) == false || fromOK(w) == false)
		{
			throw new IllegalArgumentException();
		}
		if (c < 0)
		{
			throw new IllegalArgumentException();
		}

		this.addEdge(v, w, c);
		this.addEdge(w, v, c);
	}


	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void remove(int from, int to) throws IllegalArgumentException
	{

		if (fromOK(from) == false || fromOK(to) == false)
		{
			throw new IllegalArgumentException();
		}
		if (this.hasEdge(from, to) == false)
		{
			return;
		}
		numEdges-- ;
		edges[from].remove(to);
		return;

	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void removeBi(int v, int w) throws IllegalArgumentException {
		try
		{
			this.remove(v, w);
			this.remove(w, v);
		}
		catch (IllegalArgumentException l)
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns a string representation of this graph.
	 *
	 * Should represent the graph in terms of edges (order does not matter).
	 * For example, if a graph has edges (2,3) and (1,0) with NO_COST, the
	 * representaiton should be:
	 *
	 * "{(1,0), (2,3)}" or "{(2,3), (1,0)}"
	 *
	 * If an edge has a cost (say, 5), it is added as a third value, like so:
	 *
	 * "{(1,0,5)}"
	 *
	 * An empty graph is just braces:
	 *
	 * "{}"
	 *
	 * @return a String representation of this graph
	 */
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("{");

		Set<Integer> theNeighbours = new HashSet();
		int currentVertex;
		for (int from = 0; from < this.numVertices(); from++)
		{
			if (edges[from] != null)
			{
				theNeighbours = edges[from].keySet();
				for (int to: theNeighbours)
				{
					Integer fromInteger = new Integer(from);
					Integer toInteger = new Integer(to);

					s.append("(");
					s.append(fromInteger.toString());
					s.append(",");
					s.append(toInteger.toString());
					if (this.cost(from, to) > 0)
					{
						Integer costInteger = new Integer(this.cost(from, to));
						s.append(",");
						s.append(costInteger);
					}
					s.append("), ");
				}   }
		}
		int lengthOfString = s.length();
		if (lengthOfString == 1)
		{
			s.append("}");
			return s.toString();
		}
		// ta bort den sista ", " från StringBuildern, stäng spetsparantesen,
		// och returnera!
		else
		{
			s.replace(lengthOfString - 2, lengthOfString, "}");
			return s.toString();
		}
	}
}