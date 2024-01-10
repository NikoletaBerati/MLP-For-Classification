public class Point {
	private double x1;
	private double x2;
	private int category;

	public Point(double x1, double x2, int category){
		this.x1 = x1;
		this.x2 = x2;
		this.category = category;
	}

	public double getX1(){
		return x1;
	}

	public double getX2(){
		return x2;
	}

	public int getCategory(){
		return category;
	}

}