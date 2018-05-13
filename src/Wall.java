import java.util.Random;
public class Wall
{
	int roomNo;
	int start;
	int end;
	int at;
	boolean vertical;

	public Wall(int start, int end, int at, boolean vertical)
			throws  IllegalArgumentException
	{
		if(start > end )
		{
			throw new IllegalArgumentException("Start must be less than end.");
		}

		this.start = start;
		this.end = end;
		this.at = at;
		this.vertical = vertical;


	}
	public boolean isVertical()
	{
		return vertical;
	}
	public int[] getRandomWallCoordinate()
	{
		Random random = new Random();
		int randomRange = end - start;
		int randomOffset = random.nextInt(randomRange);
		int randomCoordinate = start + randomOffset;
		int [] returnVal = new int[2];
		if(vertical)
		{
			returnVal[0] = at;
			returnVal[1] = randomCoordinate;
		}
		else
		{
			returnVal[0] = randomCoordinate;
			returnVal[1] = at;
		}
		return  returnVal;
	}

}
