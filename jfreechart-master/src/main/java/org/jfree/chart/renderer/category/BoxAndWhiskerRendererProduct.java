package org.jfree.chart.renderer.category;


import java.io.Serializable;

public class BoxAndWhiskerRendererProduct implements Serializable, Cloneable {
	private boolean maxOutlierVisible;
	private boolean minOutlierVisible;

	public boolean getMaxOutlierVisible() {
		return maxOutlierVisible;
	}

	public void setMaxOutlierVisible2(boolean maxOutlierVisible) {
		this.maxOutlierVisible = maxOutlierVisible;
	}

	public boolean getMinOutlierVisible() {
		return minOutlierVisible;
	}

	public void setMinOutlierVisible2(boolean minOutlierVisible) {
		this.minOutlierVisible = minOutlierVisible;
	}

	/**
	* Sets the flag that controls whether or not the maximum outlier is drawn for each item, and sends a  {@link RendererChangeEvent}  to all registered listeners.
	* @param visible   the new flag value.
	* @see #isMaxOutlierVisible()
	* @since  1.5.2
	*/
	public void setMaxOutlierVisible(boolean visible, BoxAndWhiskerRenderer boxAndWhiskerRenderer) {
		if (this.maxOutlierVisible == visible) {
			return;
		}
		this.maxOutlierVisible = visible;
		boxAndWhiskerRenderer.fireChangeEvent();
	}

	/**
	* Sets the flag that controls whether or not the minimum outlier is drawn for each item, and sends a  {@link RendererChangeEvent}  to all registered listeners.
	* @param visible   the new flag value.
	* @see #isMinOutlierVisible()
	* @since  1.5.2
	*/
	public void setMinOutlierVisible(boolean visible, BoxAndWhiskerRenderer boxAndWhiskerRenderer) {
		if (this.minOutlierVisible == visible) {
			return;
		}
		this.minOutlierVisible = visible;
		boxAndWhiskerRenderer.fireChangeEvent();
	}

	public Object clone() throws CloneNotSupportedException {
		return (BoxAndWhiskerRendererProduct) super.clone();
	}
}