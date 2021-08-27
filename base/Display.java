package base;

public abstract class Display {
	public abstract void drawRect(Location p, int w, int h);
	public abstract void drawCircle(Location c, int rad);
	public abstract void drawText(Location p, String text);
	public abstract void drawLine(Location start, Location end);
}
