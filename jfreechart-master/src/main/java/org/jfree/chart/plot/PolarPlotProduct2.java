package org.jfree.chart.plot;


import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.renderer.PolarItemRenderer;
import org.jfree.chart.internal.Args;
import java.io.Serializable;

public class PolarPlotProduct2 implements Serializable, Cloneable {
	private Map<Integer, PolarItemRenderer> renderers;

	public Map<Integer, PolarItemRenderer> getRenderers() {
		return renderers;
	}

	public void setRenderers(Map<Integer, PolarItemRenderer> renderers) {
		this.renderers = renderers;
	}

	/**
	* Returns the renderer at the specified index, if there is one.
	* @param index   the renderer index.
	* @return  The renderer (possibly  {@code  null} ).
	* @see #setRenderer(int,PolarItemRenderer)
	*/
	public PolarItemRenderer getRenderer(int index) {
		return this.renderers.get(index);
	}

	/**
	* Returns the index of the specified renderer, or  {@code  -1}  if the renderer is not assigned to this plot.
	* @param renderer   the renderer ( {@code  null}  not permitted).
	* @return  The renderer index.
	*/
	public int getIndexOf(PolarItemRenderer renderer) {
		Args.nullNotPermitted(renderer, "renderer");
		for (Entry<Integer, PolarItemRenderer> entry : this.renderers.entrySet()) {
			if (renderer.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return -1;
	}

	/**
	* Sets a renderer and, if requested, sends a  {@link PlotChangeEvent}  to all registered listeners.
	* @param index   the index.
	* @param renderer   the renderer.
	* @param notify   notify listeners?
	* @see #getRenderer(int)
	*/
	public void setRenderer(int index, PolarItemRenderer renderer, boolean notify, PolarPlot polarPlot) {
		PolarItemRenderer existing = getRenderer(index);
		if (existing != null) {
			existing.removeChangeListener(polarPlot);
		}
		this.renderers.put(index, renderer);
		if (renderer != null) {
			renderer.setPlot(polarPlot);
			renderer.addChangeListener(polarPlot);
		}
		if (notify) {
			polarPlot.fireChangeEvent();
		}
	}

	public Object clone() throws CloneNotSupportedException {
		return (PolarPlotProduct2) super.clone();
	}
}