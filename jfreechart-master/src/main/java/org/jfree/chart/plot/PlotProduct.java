package org.jfree.chart.plot;


import javax.swing.event.EventListenerList;
import org.jfree.chart.event.PlotChangeListener;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class PlotProduct implements Serializable, PublicCloneable {
	private transient EventListenerList listenerList;

	public EventListenerList getListenerList() {
		return listenerList;
	}

	public void setListenerList(EventListenerList listenerList) {
		this.listenerList = listenerList;
	}

	/**
	* Registers an object for notification of changes to the plot.
	* @param listener   the object to be registered.
	* @see #removeChangeListener(PlotChangeListener)
	*/
	public void addChangeListener(PlotChangeListener listener) {
		this.listenerList.add(PlotChangeListener.class, listener);
	}

	/**
	* Unregisters an object for notification of changes to the plot.
	* @param listener   the object to be unregistered.
	* @see #addChangeListener(PlotChangeListener)
	*/
	public void removeChangeListener(PlotChangeListener listener) {
		this.listenerList.remove(PlotChangeListener.class, listener);
	}

	public Object clone() throws CloneNotSupportedException {
		return (PlotProduct) super.clone();
	}
}