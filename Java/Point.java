
public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	/***
	 * finds the distance between two points
	 * @param other, another point
	 * @return the distance b/w the points
	 */
	public double distance(Point other)
	{
		double xSquare = Math.pow(this.x - other.x,2);
		double ySquare = Math.pow(this.x - other.x,2);
		return Math.sqrt(xSquare + ySquare);
	}
	/***
	 * checks to see if two points are next to each other
	 * @param other, another point
	 * @return true if two points are next to each other; otherwise false
	 */
	public boolean adjacent(Point other)
	{
		
		if(this.x == other.x && (Math.abs(this.y-other.y) == 1))
			return true;
		if(this.y == other.y && (Math.abs(this.x-other.x) == 1))
			return true;
		return false;
	}

}
