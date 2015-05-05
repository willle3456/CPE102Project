
public class OCCGrid {
	/*public enum OccupancyValue 
	{
		EMPTY, GATHERER, GENERATOR, RESOURCE;
	}*/
	private int height, width;
	private Entities[][] cells = new Entities[width][height];

	public OCCGrid(int width, int height, Entities occupancy_value)
	{
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				cells[i][j] = occupancy_value;
			}
		}
	}
	
	public Entities getCell(Point pt)
	{
		return this.cells[(int)pt.getX()][(int)pt.getY()];
	}
	
	public void setCell(Point pt, Entities value)
	{
		this.cells[(int)pt.getX()][(int)pt.getY()] = value;
	}

}
