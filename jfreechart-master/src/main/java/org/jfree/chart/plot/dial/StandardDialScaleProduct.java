package org.jfree.chart.plot.dial;


import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Font;
import org.jfree.chart.internal.Args;
import java.io.Serializable;

public class StandardDialScaleProduct implements Serializable, Cloneable {
	private double lowerBound;
	private double upperBound;
	private double startAngle;
	private double extent;
	private double tickRadius;
	private double majorTickIncrement;
	private double majorTickLength;
	private transient Paint majorTickPaint;
	private transient Stroke majorTickStroke;
	private int minorTickCount;
	private double minorTickLength;
	private transient Paint minorTickPaint;
	private transient Stroke minorTickStroke;
	private double tickLabelOffset;
	private Font tickLabelFont;
	private boolean tickLabelsVisible;
	private transient Paint tickLabelPaint;

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound2(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound2(double upperBound) {
		this.upperBound = upperBound;
	}

	public double getStartAngle() {
		return startAngle;
	}

	public void setStartAngle2(double startAngle) {
		this.startAngle = startAngle;
	}

	public double getExtent() {
		return extent;
	}

	public void setExtent2(double extent) {
		this.extent = extent;
	}

	public double getTickRadius() {
		return tickRadius;
	}

	public void setTickRadius2(double tickRadius) {
		this.tickRadius = tickRadius;
	}

	public double getMajorTickIncrement() {
		return majorTickIncrement;
	}

	public void setMajorTickIncrement2(double majorTickIncrement) {
		this.majorTickIncrement = majorTickIncrement;
	}

	public double getMajorTickLength() {
		return majorTickLength;
	}

	public void setMajorTickLength2(double majorTickLength) {
		this.majorTickLength = majorTickLength;
	}

	public Paint getMajorTickPaint() {
		return majorTickPaint;
	}

	public void setMajorTickPaint2(Paint majorTickPaint) {
		this.majorTickPaint = majorTickPaint;
	}

	public Stroke getMajorTickStroke() {
		return majorTickStroke;
	}

	public void setMajorTickStroke2(Stroke majorTickStroke) {
		this.majorTickStroke = majorTickStroke;
	}

	public int getMinorTickCount() {
		return minorTickCount;
	}

	public void setMinorTickCount2(int minorTickCount) {
		this.minorTickCount = minorTickCount;
	}

	public double getMinorTickLength() {
		return minorTickLength;
	}

	public void setMinorTickLength2(double minorTickLength) {
		this.minorTickLength = minorTickLength;
	}

	public Paint getMinorTickPaint() {
		return minorTickPaint;
	}

	public void setMinorTickPaint2(Paint minorTickPaint) {
		this.minorTickPaint = minorTickPaint;
	}

	public Stroke getMinorTickStroke() {
		return minorTickStroke;
	}

	public void setMinorTickStroke2(Stroke minorTickStroke) {
		this.minorTickStroke = minorTickStroke;
	}

	public double getTickLabelOffset() {
		return tickLabelOffset;
	}

	public void setTickLabelOffset2(double tickLabelOffset) {
		this.tickLabelOffset = tickLabelOffset;
	}

	public Font getTickLabelFont() {
		return tickLabelFont;
	}

	public void setTickLabelFont2(Font tickLabelFont) {
		this.tickLabelFont = tickLabelFont;
	}

	public boolean getTickLabelsVisible() {
		return tickLabelsVisible;
	}

	public void setTickLabelsVisible2(boolean tickLabelsVisible) {
		this.tickLabelsVisible = tickLabelsVisible;
	}

	public Paint getTickLabelPaint() {
		return tickLabelPaint;
	}

	public void setTickLabelPaint2(Paint tickLabelPaint) {
		this.tickLabelPaint = tickLabelPaint;
	}

	/**
	* Sets the tick radius and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param radius   the radius.
	* @see #getTickRadius()
	*/
	public void setTickRadius(double radius, StandardDialScale standardDialScale) {
		if (radius <= 0.0) {
			throw new IllegalArgumentException("The 'radius' must be positive.");
		}
		this.tickRadius = radius;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the increment (in data units) between major tick labels and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param increment   the increment (must be &gt; 0).
	* @see #getMajorTickIncrement()
	*/
	public void setMajorTickIncrement(double increment, StandardDialScale standardDialScale) {
		if (increment <= 0.0) {
			throw new IllegalArgumentException("The 'increment' must be positive.");
		}
		this.majorTickIncrement = increment;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the length factor for the major tick marks and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param length   the length.
	* @see #getMajorTickLength()
	*/
	public void setMajorTickLength(double length, StandardDialScale standardDialScale) {
		if (length < 0.0) {
			throw new IllegalArgumentException("Negative 'length' argument.");
		}
		this.majorTickLength = length;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the number of minor tick marks between major tick marks and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param count   the count.
	* @see #getMinorTickCount()
	*/
	public void setMinorTickCount(int count, StandardDialScale standardDialScale) {
		if (count < 0) {
			throw new IllegalArgumentException("The 'count' cannot be negative.");
		}
		this.minorTickCount = count;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the length factor for the minor tick marks and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param length   the length.
	* @see #getMinorTickLength()
	*/
	public void setMinorTickLength(double length, StandardDialScale standardDialScale) {
		if (length < 0.0) {
			throw new IllegalArgumentException("Negative 'length' argument.");
		}
		this.minorTickLength = length;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the tick label offset and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param offset   the offset.
	* @see #getTickLabelOffset()
	*/
	public void setTickLabelOffset(double offset, StandardDialScale standardDialScale) {
		this.tickLabelOffset = offset;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the font used to display the tick labels and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param font   the font ( {@code  null}  not permitted).
	* @see #getTickLabelFont()
	*/
	public void setTickLabelFont(Font font, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(font, "font");
		this.tickLabelFont = font;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the flag that controls whether or not the tick labels are displayed, and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param visible   the new flag value.
	* @see #getTickLabelsVisible()
	*/
	public void setTickLabelsVisible(boolean visible, StandardDialScale standardDialScale) {
		this.tickLabelsVisible = visible;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the extent and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param extent   the extent.
	* @see #getExtent()
	*/
	public void setExtent(double extent, StandardDialScale standardDialScale) {
		this.extent = extent;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the major tick paint and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param paint   the paint ( {@code  null}  not permitted).
	* @see #getMajorTickPaint()
	*/
	public void setMajorTickPaint(Paint paint, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(paint, "paint");
		this.majorTickPaint = paint;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the stroke used to draw the major tick marks and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param stroke   the stroke ( {@code  null}  not permitted).
	* @see #getMajorTickStroke()
	*/
	public void setMajorTickStroke(Stroke stroke, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(stroke, "stroke");
		this.majorTickStroke = stroke;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the paint used to draw the minor tick marks and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param paint   the paint ( {@code  null}  not permitted).
	* @see #getMinorTickPaint()
	*/
	public void setMinorTickPaint(Paint paint, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(paint, "paint");
		this.minorTickPaint = paint;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the stroke used to draw the minor tick marks and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param stroke   the stroke ( {@code  null}  not permitted).
	* @see #getMinorTickStroke()
	*/
	public void setMinorTickStroke(Stroke stroke, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(stroke, "stroke");
		this.minorTickStroke = stroke;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the paint used to draw the tick labels and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param paint   the paint ( {@code  null}  not permitted).
	*/
	public void setTickLabelPaint(Paint paint, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(paint, "paint");
		this.tickLabelPaint = paint;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the lower bound for the scale and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param lower   the lower bound.
	* @see #getLowerBound()
	*/
	public void setLowerBound(double lower, StandardDialScale standardDialScale) {
		this.lowerBound = lower;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the upper bound for the scale and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param upper   the upper bound.
	* @see #getUpperBound()
	*/
	public void setUpperBound(double upper, StandardDialScale standardDialScale) {
		this.upperBound = upper;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets the start angle for the scale and sends a {@link DialLayerChangeEvent}  to all registered listeners.
	* @param angle   the angle (in degrees).
	* @see #getStartAngle()
	*/
	public void setStartAngle(double angle, StandardDialScale standardDialScale) {
		this.startAngle = angle;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	public Object clone() throws CloneNotSupportedException {
		return (StandardDialScaleProduct) super.clone();
	}
}