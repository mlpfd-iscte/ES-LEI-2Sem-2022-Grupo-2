package org.jfree.chart.plot;


import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class PlotProduct3 implements Serializable, PublicCloneable {
	private transient PlotProduct plotProduct = new PlotProduct();

	public PlotProduct getPlotProduct() {
		return plotProduct;
	}

	public void setPlotProduct(PlotProduct plotProduct) {
		this.plotProduct = plotProduct;
	}

	/**
	* Sets a flag that controls whether or not listeners receive {@link PlotChangeEvent}  notifications.
	* @param notify   a boolean.
	* @see #isNotify()
	*/
	public void setNotify(boolean notify, Plot plot) {
		plot.setNotify2(notify);
		if (notify) {
			notifyListeners(new PlotChangeEvent(plot), plot);
		}
	}

	/**
	* Notifies all registered listeners that the plot has been modified.
	* @param event   information about the change event.
	*/
	public void notifyListeners(PlotChangeEvent event, Plot plot) {
		if (!plot.isNotify()) {
			return;
		}
		Object[] listeners = this.plotProduct.getListenerList().getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PlotChangeListener.class) {
				((PlotChangeListener) listeners[i + 1]).plotChanged(event);
			}
		}
	}

	public Object clone() throws CloneNotSupportedException {
		return (PlotProduct3) super.clone();
	}
}