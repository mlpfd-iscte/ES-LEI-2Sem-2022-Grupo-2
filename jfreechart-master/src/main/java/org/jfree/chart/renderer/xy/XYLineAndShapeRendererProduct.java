package org.jfree.chart.renderer.xy;


import java.util.Map;
import java.io.Serializable;

public class XYLineAndShapeRendererProduct implements Serializable, Cloneable {
	private Map<Integer, Boolean> seriesLinesVisibleMap;
	private boolean defaultLinesVisible;
	private Map<Integer, Boolean> seriesShapesVisibleMap;
	private boolean defaultShapesVisible;
	private Map<Integer, Boolean> seriesShapesFilledMap;
	private boolean defaultShapesFilled;
	private boolean drawOutlines;
	private boolean useFillPaint;
	private boolean useOutlinePaint;
	private boolean drawSeriesLineAsPath;

	public Map<Integer, Boolean> getSeriesLinesVisibleMap() {
		return seriesLinesVisibleMap;
	}

	public void setSeriesLinesVisibleMap(Map<Integer, Boolean> seriesLinesVisibleMap) {
		this.seriesLinesVisibleMap = seriesLinesVisibleMap;
	}

	public boolean getDefaultLinesVisible() {
		return defaultLinesVisible;
	}

	public void setDefaultLinesVisible2(boolean defaultLinesVisible) {
		this.defaultLinesVisible = defaultLinesVisible;
	}

	public Map<Integer, Boolean> getSeriesShapesVisibleMap() {
		return seriesShapesVisibleMap;
	}

	public void setSeriesShapesVisibleMap(Map<Integer, Boolean> seriesShapesVisibleMap) {
		this.seriesShapesVisibleMap = seriesShapesVisibleMap;
	}

	public boolean getDefaultShapesVisible() {
		return defaultShapesVisible;
	}

	public void setDefaultShapesVisible2(boolean defaultShapesVisible) {
		this.defaultShapesVisible = defaultShapesVisible;
	}

	public Map<Integer, Boolean> getSeriesShapesFilledMap() {
		return seriesShapesFilledMap;
	}

	public void setSeriesShapesFilledMap(Map<Integer, Boolean> seriesShapesFilledMap) {
		this.seriesShapesFilledMap = seriesShapesFilledMap;
	}

	public boolean getDefaultShapesFilled() {
		return defaultShapesFilled;
	}

	public void setDefaultShapesFilled2(boolean defaultShapesFilled) {
		this.defaultShapesFilled = defaultShapesFilled;
	}

	public boolean getDrawOutlines() {
		return drawOutlines;
	}

	public void setDrawOutlines2(boolean drawOutlines) {
		this.drawOutlines = drawOutlines;
	}

	public boolean getUseFillPaint() {
		return useFillPaint;
	}

	public void setUseFillPaint2(boolean useFillPaint) {
		this.useFillPaint = useFillPaint;
	}

	public boolean getUseOutlinePaint() {
		return useOutlinePaint;
	}

	public void setUseOutlinePaint2(boolean useOutlinePaint) {
		this.useOutlinePaint = useOutlinePaint;
	}

	public boolean getDrawSeriesLineAsPath() {
		return drawSeriesLineAsPath;
	}

	public void setDrawSeriesLineAsPath2(boolean drawSeriesLineAsPath) {
		this.drawSeriesLineAsPath = drawSeriesLineAsPath;
	}

	/**
	* Returns the flag used to control whether or not the lines for a series are visible.
	* @param series   the series index (zero-based).
	* @return  The flag (possibly  {@code  null} ).
	* @see #setSeriesLinesVisible(int,Boolean)
	*/
	public Boolean getSeriesLinesVisible(int series) {
		return this.seriesLinesVisibleMap.get(series);
	}

	/**
	* Returns the flag used to control whether or not the shapes for a series are visible.
	* @param series   the series index (zero-based).
	* @return  A boolean.
	* @see #setSeriesShapesVisible(int,Boolean)
	*/
	public Boolean getSeriesShapesVisible(int series) {
		return this.seriesShapesVisibleMap.get(series);
	}

	/**
	* Returns the flag used to control whether or not the shapes for a series are filled.
	* @param series   the series index (zero-based).
	* @return  A boolean.
	* @see #setSeriesShapesFilled(int,Boolean)
	*/
	public Boolean getSeriesShapesFilled(int series) {
		return this.seriesShapesFilledMap.get(series);
	}

	/**
	* Sets the 'lines visible' flag for a series and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param series   the series index (zero-based).
	* @param flag   the flag ( {@code  null}  permitted).
	* @see #getSeriesLinesVisible(int)
	*/
	public void setSeriesLinesVisible(int series, Boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.seriesLinesVisibleMap.put(series, flag);
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Sets the 'shapes visible' flag for a series and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param series   the series index (zero-based).
	* @param flag   the flag.
	* @see #getSeriesShapesVisible(int)
	*/
	public void setSeriesShapesVisible(int series, Boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.seriesShapesVisibleMap.put(series, flag);
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Sets the 'shapes filled' flag for a series and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param series   the series index (zero-based).
	* @param flag   the flag.
	* @see #getSeriesShapesFilled(int)
	*/
	public void setSeriesShapesFilled(int series, Boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.seriesShapesFilledMap.put(series, flag);
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Returns the flag used to control whether or not the shape for an item is visible.
	* @param series   the series index (zero-based).
	* @param item   the item index (zero-based).
	* @return  A boolean.
	*/
	public boolean getItemLineVisible(int series, int item) {
		Boolean flag = getSeriesLinesVisible(series);
		if (flag != null) {
			return flag;
		}
		return this.defaultLinesVisible;
	}

	/**
	* Sets the default 'lines visible' flag and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param flag   the flag.
	* @see #getDefaultLinesVisible()
	*/
	public void setDefaultLinesVisible(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.defaultLinesVisible = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Returns the flag used to control whether or not the shape for an item is visible. <p> The default implementation passes control to the {@code  getSeriesShapesVisible()}  method. You can override this method if you require different behaviour.
	* @param series   the series index (zero-based).
	* @param item   the item index (zero-based).
	* @return  A boolean.
	*/
	public boolean getItemShapeVisible(int series, int item) {
		Boolean flag = getSeriesShapesVisible(series);
		if (flag != null) {
			return flag;
		}
		return this.defaultShapesVisible;
	}

	/**
	* Sets the default 'shapes visible' flag and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param flag   the flag.
	* @see #getDefaultShapesVisible()
	*/
	public void setDefaultShapesVisible(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.defaultShapesVisible = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Returns the flag used to control whether or not the shape for an item is filled. <p> The default implementation passes control to the {@code  getSeriesShapesFilled}  method. You can override this method if you require different behaviour.
	* @param series   the series index (zero-based).
	* @param item   the item index (zero-based).
	* @return  A boolean.
	*/
	public boolean getItemShapeFilled(int series, int item) {
		Boolean flag = getSeriesShapesFilled(series);
		if (flag != null) {
			return flag;
		}
		return this.defaultShapesFilled;
	}

	/**
	* Sets the default 'shapes filled' flag and sends a {@link RendererChangeEvent}  to all registered listeners.
	* @param flag   the flag.
	* @see #getDefaultShapesFilled()
	*/
	public void setDefaultShapesFilled(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.defaultShapesFilled = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Sets the flag that controls whether or not each series is drawn as a single path and sends a  {@link RendererChangeEvent}  to all registered listeners.
	* @param flag   the flag.
	* @see #getDrawSeriesLineAsPath()
	*/
	public void setDrawSeriesLineAsPath(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		if (this.drawSeriesLineAsPath != flag) {
			this.drawSeriesLineAsPath = flag;
			xYLineAndShapeRenderer.fireChangeEvent();
		}
	}

	/**
	* Sets the flag that controls whether the fill paint is used to fill shapes, and sends a  {@link RendererChangeEvent}  to all registered listeners.
	* @param flag   the flag.
	* @see #getUseFillPaint()
	*/
	public void setUseFillPaint(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.useFillPaint = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Sets the flag that controls whether the outline paint is used to draw shape outlines, and sends a  {@link RendererChangeEvent}  to all registered listeners. <p> Refer to  {@code  XYLineAndShapeRendererDemo2.java}  to see the effect of this flag.
	* @param flag   the flag.
	* @see #getUseOutlinePaint()
	*/
	public void setUseOutlinePaint(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.useOutlinePaint = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	/**
	* Sets the flag that controls whether outlines are drawn for shapes, and sends a  {@link RendererChangeEvent}  to all registered listeners. <P> In some cases, shapes look better if they do NOT have an outline, but this flag allows you to set your own preference.
	* @param flag   the flag.
	* @see #getDrawOutlines()
	*/
	public void setDrawOutlines(boolean flag, XYLineAndShapeRenderer xYLineAndShapeRenderer) {
		this.drawOutlines = flag;
		xYLineAndShapeRenderer.fireChangeEvent();
	}

	public Object clone() throws CloneNotSupportedException {
		return (XYLineAndShapeRendererProduct) super.clone();
	}
}