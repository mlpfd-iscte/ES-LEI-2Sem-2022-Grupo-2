package org.jfree.chart.plot.dial;


import java.text.NumberFormat;
import org.jfree.chart.internal.Args;
import java.io.Serializable;

public class StandardDialScaleProduct2 implements Serializable, Cloneable {
	private NumberFormat tickLabelFormatter;
	private boolean firstTickLabelVisible;

	public NumberFormat getTickLabelFormatter() {
		return tickLabelFormatter;
	}

	public void setTickLabelFormatter2(NumberFormat tickLabelFormatter) {
		this.tickLabelFormatter = tickLabelFormatter;
	}

	public boolean getFirstTickLabelVisible() {
		return firstTickLabelVisible;
	}

	public void setFirstTickLabelVisible2(boolean firstTickLabelVisible) {
		this.firstTickLabelVisible = firstTickLabelVisible;
	}

	/**
	* Sets the number formatter used to convert the tick label values to strings, and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param formatter   the formatter ( {@code  null}  not permitted).
	* @see #getTickLabelFormatter()
	*/
	public void setTickLabelFormatter(NumberFormat formatter, StandardDialScale standardDialScale) {
		Args.nullNotPermitted(formatter, "formatter");
		this.tickLabelFormatter = formatter;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	/**
	* Sets a flag that controls whether or not the first tick label is visible, and sends a  {@link DialLayerChangeEvent}  to all registered listeners.
	* @param visible   the new flag value.
	* @see #getFirstTickLabelVisible()
	*/
	public void setFirstTickLabelVisible(boolean visible, StandardDialScale standardDialScale) {
		this.firstTickLabelVisible = visible;
		standardDialScale.notifyListeners(new DialLayerChangeEvent(standardDialScale));
	}

	public Object clone() throws CloneNotSupportedException {
		return (StandardDialScaleProduct2) super.clone();
	}
}