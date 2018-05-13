import java.util.HashSet;
/**
 * Genererar oriktade grafer med n hörn och n kanter.
 */
public class GraphCalculations
{
	private int largestComponent;

	public GraphCalculations()
	{
		largestComponent = 0;
	}

	/**
	 * Returnerar antalet komponenter i grafen.
	 * som sido effekt sparar den största komponenten i fältet
	 * largestComponent,
	 * så den kan returneras av largestComponent.
	 */
	public int components(Graph graph)
	{
		int components = 0;
		int vertexes = graph.numVertices();
		HashSet<Integer> unfounds = new HashSet();
		for (int i = 0; i < vertexes; i++)
		{
			unfounds.add(i);
		}

		int currentVertex = 0;
		while (unfounds.isEmpty() == false)
		{
			components++;
			int sizeOfThisComponent = 1;
			currentVertex = unfounds.iterator().next();
			VertexIterator neighbours = graph.neighbors(currentVertex);
			unfounds.remove(currentVertex);
			while (neighbours.hasNext())
			{
				int nextVertex = neighbours.next();
				sizeOfThisComponent = strike(graph, unfounds, nextVertex,
						sizeOfThisComponent + 1);
			}
			if (sizeOfThisComponent > largestComponent)
			{
				largestComponent = sizeOfThisComponent;
			}
		}
		return components;
	}

	//Stryker alla grannar till hörnet.
	private int strike(Graph graph,
					   HashSet<Integer> unfounds, int vertex,
					   int sizeOfThisComponent)
	{
		if (unfounds.contains(vertex) == false)
		{
			return sizeOfThisComponent;
		}
		VertexIterator neighbours = graph.neighbors(vertex);
		unfounds.remove(vertex);
		while (neighbours.hasNext())
		{
			int currentVertex = neighbours.next();
			sizeOfThisComponent =
					strike(graph, unfounds, currentVertex, sizeOfThisComponent + 1);
		}
		return sizeOfThisComponent;
	}

	/**
	 * Returnerar största komponenten, men bara om components körts först.
	 */
	public int largestComponent()
	{
		return largestComponent;
	}
}