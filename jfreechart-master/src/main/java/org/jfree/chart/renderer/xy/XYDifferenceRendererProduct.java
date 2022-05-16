package org.jfree.chart.renderer.xy;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.plot.CrosshairState;
import java.util.LinkedList;
import java.util.Collections;
import java.io.Serializable;
import org.jfree.chart.api.PublicCloneable;

public class XYDifferenceRendererProduct implements Serializable, PublicCloneable {
	/**
	* Draws the visual representation of a single data item, first pass.
	* @param x_graphics   the graphics device.
	* @param x_dataArea   the area within which the data is being drawn.
	* @param x_info   collects information about the drawing.
	* @param x_plot   the plot (can be used to obtain standard color information etc).
	* @param x_domainAxis   the domain (horizontal) axis.
	* @param x_rangeAxis   the range (vertical) axis.
	* @param x_dataset   the dataset.
	* @param x_series   the series index (zero-based).
	* @param x_item   the item index (zero-based).
	* @param x_crosshairState   crosshair information for the plot ( {@code  null}  permitted).
	*/
	public void drawItemPass0(Graphics2D x_graphics, Rectangle2D x_dataArea, PlotRenderingInfo x_info, XYPlot x_plot,
			ValueAxis x_domainAxis, ValueAxis x_rangeAxis, XYDataset x_dataset, int x_series, int x_item,
			CrosshairState x_crosshairState, XYDifferenceRenderer xYDifferenceRenderer) {
		if (!((0 == x_series) && (0 == x_item))) {
			return;
		}
		boolean b_impliedZeroSubtrahend = (1 == x_dataset.getSeriesCount());
		if (isEitherSeriesDegenerate(x_dataset, b_impliedZeroSubtrahend)) {
			return;
		}
		if (!b_impliedZeroSubtrahend && areSeriesDisjoint(x_dataset)) {
			return;
		}
		LinkedList l_minuendXs = new LinkedList();
		LinkedList l_minuendYs = new LinkedList();
		LinkedList l_subtrahendXs = new LinkedList();
		LinkedList l_subtrahendYs = new LinkedList();
		LinkedList l_polygonXs = new LinkedList();
		LinkedList l_polygonYs = new LinkedList();
		int l_minuendItem = 0;
		int l_minuendItemCount = x_dataset.getItemCount(0);
		Double l_minuendCurX = null;
		Double l_minuendNextX = null;
		Double l_minuendCurY = null;
		Double l_minuendNextY = null;
		double l_minuendMaxY = Double.NEGATIVE_INFINITY;
		double l_minuendMinY = Double.POSITIVE_INFINITY;
		int l_subtrahendItem = 0;
		int l_subtrahendItemCount = 0;
		Double l_subtrahendCurX = null;
		Double l_subtrahendNextX = null;
		Double l_subtrahendCurY = null;
		Double l_subtrahendNextY = null;
		double l_subtrahendMaxY = Double.NEGATIVE_INFINITY;
		double l_subtrahendMinY = Double.POSITIVE_INFINITY;
		if (b_impliedZeroSubtrahend) {
			l_subtrahendItem = 0;
			l_subtrahendItemCount = 2;
			l_subtrahendCurX = x_dataset.getXValue(0, 0);
			l_subtrahendNextX = x_dataset.getXValue(0, (l_minuendItemCount - 1));
			l_subtrahendCurY = 0.0;
			l_subtrahendNextY = 0.0;
			l_subtrahendMaxY = 0.0;
			l_subtrahendMinY = 0.0;
			l_subtrahendXs.add(l_subtrahendCurX);
			l_subtrahendYs.add(l_subtrahendCurY);
		} else {
			l_subtrahendItemCount = x_dataset.getItemCount(1);
		}
		boolean b_minuendDone = false;
		boolean b_minuendAdvanced = true;
		boolean b_minuendAtIntersect = false;
		boolean b_minuendFastForward = false;
		boolean b_subtrahendDone = false;
		boolean b_subtrahendAdvanced = true;
		boolean b_subtrahendAtIntersect = false;
		boolean b_subtrahendFastForward = false;
		boolean b_colinear = false;
		boolean b_positive;
		double l_x1 = 0.0, l_y1 = 0.0;
		double l_x2 = 0.0, l_y2 = 0.0;
		double l_x3 = 0.0, l_y3 = 0.0;
		double l_x4 = 0.0, l_y4 = 0.0;
		boolean b_fastForwardDone = false;
		while (!b_fastForwardDone) {
			l_x1 = x_dataset.getXValue(0, l_minuendItem);
			l_y1 = x_dataset.getYValue(0, l_minuendItem);
			l_x2 = x_dataset.getXValue(0, l_minuendItem + 1);
			l_y2 = x_dataset.getYValue(0, l_minuendItem + 1);
			l_minuendCurX = l_x1;
			l_minuendCurY = l_y1;
			l_minuendNextX = l_x2;
			l_minuendNextY = l_y2;
			if (b_impliedZeroSubtrahend) {
				l_x3 = l_subtrahendCurX;
				l_y3 = l_subtrahendCurY;
				l_x4 = l_subtrahendNextX;
				l_y4 = l_subtrahendNextY;
			} else {
				l_x3 = x_dataset.getXValue(1, l_subtrahendItem);
				l_y3 = x_dataset.getYValue(1, l_subtrahendItem);
				l_x4 = x_dataset.getXValue(1, l_subtrahendItem + 1);
				l_y4 = x_dataset.getYValue(1, l_subtrahendItem + 1);
				l_subtrahendCurX = l_x3;
				l_subtrahendCurY = l_y3;
				l_subtrahendNextX = l_x4;
				l_subtrahendNextY = l_y4;
			}
			if (l_x2 <= l_x3) {
				l_minuendItem++;
				b_minuendFastForward = true;
				continue;
			}
			if (l_x4 <= l_x1) {
				l_subtrahendItem++;
				b_subtrahendFastForward = true;
				continue;
			}
			if ((l_x3 < l_x1) && (l_x1 < l_x4)) {
				double l_slope = (l_y4 - l_y3) / (l_x4 - l_x3);
				l_subtrahendCurX = l_minuendCurX;
				l_subtrahendCurY = (l_slope * l_x1) + (l_y3 - (l_slope * l_x3));
				l_subtrahendXs.add(l_subtrahendCurX);
				l_subtrahendYs.add(l_subtrahendCurY);
			}
			if ((l_x1 < l_x3) && (l_x3 < l_x2)) {
				double l_slope = (l_y2 - l_y1) / (l_x2 - l_x1);
				l_minuendCurX = l_subtrahendCurX;
				l_minuendCurY = (l_slope * l_x3) + (l_y1 - (l_slope * l_x1));
				l_minuendXs.add(l_minuendCurX);
				l_minuendYs.add(l_minuendCurY);
			}
			l_minuendMaxY = l_minuendCurY;
			l_minuendMinY = l_minuendCurY;
			l_subtrahendMaxY = l_subtrahendCurY;
			l_subtrahendMinY = l_subtrahendCurY;
			b_fastForwardDone = true;
		}
		while (!b_minuendDone && !b_subtrahendDone) {
			if (!b_minuendDone && !b_minuendFastForward && b_minuendAdvanced) {
				l_x1 = x_dataset.getXValue(0, l_minuendItem);
				l_y1 = x_dataset.getYValue(0, l_minuendItem);
				l_minuendCurX = l_x1;
				l_minuendCurY = l_y1;
				if (!b_minuendAtIntersect) {
					l_minuendXs.add(l_minuendCurX);
					l_minuendYs.add(l_minuendCurY);
				}
				l_minuendMaxY = Math.max(l_minuendMaxY, l_y1);
				l_minuendMinY = Math.min(l_minuendMinY, l_y1);
				l_x2 = x_dataset.getXValue(0, l_minuendItem + 1);
				l_y2 = x_dataset.getYValue(0, l_minuendItem + 1);
				l_minuendNextX = l_x2;
				l_minuendNextY = l_y2;
			}
			if (!b_impliedZeroSubtrahend && !b_subtrahendDone && !b_subtrahendFastForward && b_subtrahendAdvanced) {
				l_x3 = x_dataset.getXValue(1, l_subtrahendItem);
				l_y3 = x_dataset.getYValue(1, l_subtrahendItem);
				l_subtrahendCurX = l_x3;
				l_subtrahendCurY = l_y3;
				if (!b_subtrahendAtIntersect) {
					l_subtrahendXs.add(l_subtrahendCurX);
					l_subtrahendYs.add(l_subtrahendCurY);
				}
				l_subtrahendMaxY = Math.max(l_subtrahendMaxY, l_y3);
				l_subtrahendMinY = Math.min(l_subtrahendMinY, l_y3);
				l_x4 = x_dataset.getXValue(1, l_subtrahendItem + 1);
				l_y4 = x_dataset.getYValue(1, l_subtrahendItem + 1);
				l_subtrahendNextX = l_x4;
				l_subtrahendNextY = l_y4;
			}
			b_minuendFastForward = false;
			b_subtrahendFastForward = false;
			Double l_intersectX = null;
			Double l_intersectY = null;
			boolean b_intersect = false;
			b_minuendAtIntersect = false;
			b_subtrahendAtIntersect = false;
			if ((l_x2 == l_x4) && (l_y2 == l_y4)) {
				if ((l_x1 == l_x3) && (l_y1 == l_y3)) {
					b_colinear = true;
				} else {
					l_intersectX = l_x2;
					l_intersectY = l_y2;
					b_intersect = true;
					b_minuendAtIntersect = true;
					b_subtrahendAtIntersect = true;
				}
			} else {
				double l_denominator = ((l_y4 - l_y3) * (l_x2 - l_x1)) - ((l_x4 - l_x3) * (l_y2 - l_y1));
				double l_deltaY = l_y1 - l_y3;
				double l_deltaX = l_x1 - l_x3;
				double l_numeratorA = ((l_x4 - l_x3) * l_deltaY) - ((l_y4 - l_y3) * l_deltaX);
				double l_numeratorB = ((l_x2 - l_x1) * l_deltaY) - ((l_y2 - l_y1) * l_deltaX);
				if ((0 == l_numeratorA) && (0 == l_numeratorB) && (0 == l_denominator)) {
					b_colinear = true;
				} else {
					if (b_colinear) {
						l_minuendXs.clear();
						l_minuendYs.clear();
						l_subtrahendXs.clear();
						l_subtrahendYs.clear();
						l_polygonXs.clear();
						l_polygonYs.clear();
						b_colinear = false;
						boolean b_useMinuend = ((l_x3 <= l_x1) && (l_x1 <= l_x4));
						l_polygonXs.add(b_useMinuend ? l_minuendCurX : l_subtrahendCurX);
						l_polygonYs.add(b_useMinuend ? l_minuendCurY : l_subtrahendCurY);
					}
				}
				double l_slopeA = l_numeratorA / l_denominator;
				double l_slopeB = l_numeratorB / l_denominator;
				boolean b_vertical = (l_x1 == l_x2) && (l_x3 == l_x4) && (l_x2 == l_x4);
				if (((0 < l_slopeA) && (l_slopeA <= 1) && (0 < l_slopeB) && (l_slopeB <= 1)) || b_vertical) {
					double l_xi;
					double l_yi;
					if (b_vertical) {
						b_colinear = false;
						l_xi = l_x2;
						l_yi = l_x4;
					} else {
						l_xi = l_x1 + (l_slopeA * (l_x2 - l_x1));
						l_yi = l_y1 + (l_slopeA * (l_y2 - l_y1));
					}
					l_intersectX = l_xi;
					l_intersectY = l_yi;
					b_intersect = true;
					b_minuendAtIntersect = ((l_xi == l_x2) && (l_yi == l_y2));
					b_subtrahendAtIntersect = ((l_xi == l_x4) && (l_yi == l_y4));
					l_minuendCurX = l_intersectX;
					l_minuendCurY = l_intersectY;
					l_subtrahendCurX = l_intersectX;
					l_subtrahendCurY = l_intersectY;
				}
			}
			if (b_intersect) {
				l_polygonXs.addAll(l_minuendXs);
				l_polygonYs.addAll(l_minuendYs);
				l_polygonXs.add(l_intersectX);
				l_polygonYs.add(l_intersectY);
				Collections.reverse(l_subtrahendXs);
				Collections.reverse(l_subtrahendYs);
				l_polygonXs.addAll(l_subtrahendXs);
				l_polygonYs.addAll(l_subtrahendYs);
				b_positive = (l_subtrahendMaxY <= l_minuendMaxY) && (l_subtrahendMinY <= l_minuendMinY);
				xYDifferenceRenderer.createPolygon(x_graphics, x_dataArea, x_plot, x_domainAxis, x_rangeAxis,
						b_positive, l_polygonXs, l_polygonYs);
				l_minuendXs.clear();
				l_minuendYs.clear();
				l_subtrahendXs.clear();
				l_subtrahendYs.clear();
				l_polygonXs.clear();
				l_polygonYs.clear();
				double l_y = l_intersectY;
				l_minuendMaxY = l_y;
				l_subtrahendMaxY = l_y;
				l_minuendMinY = l_y;
				l_subtrahendMinY = l_y;
				l_polygonXs.add(l_intersectX);
				l_polygonYs.add(l_intersectY);
			}
			if (l_x2 <= l_x4) {
				l_minuendItem++;
				b_minuendAdvanced = true;
			} else {
				b_minuendAdvanced = false;
			}
			if (l_x4 <= l_x2) {
				l_subtrahendItem++;
				b_subtrahendAdvanced = true;
			} else {
				b_subtrahendAdvanced = false;
			}
			b_minuendDone = (l_minuendItem == (l_minuendItemCount - 1));
			b_subtrahendDone = (l_subtrahendItem == (l_subtrahendItemCount - 1));
		}
		if (b_minuendDone && (l_x3 < l_x2) && (l_x2 < l_x4)) {
			double l_slope = (l_y4 - l_y3) / (l_x4 - l_x3);
			l_subtrahendNextX = l_minuendNextX;
			l_subtrahendNextY = (l_slope * l_x2) + (l_y3 - (l_slope * l_x3));
		}
		if (b_subtrahendDone && (l_x1 < l_x4) && (l_x4 < l_x2)) {
			double l_slope = (l_y2 - l_y1) / (l_x2 - l_x1);
			l_minuendNextX = l_subtrahendNextX;
			l_minuendNextY = (l_slope * l_x4) + (l_y1 - (l_slope * l_x1));
		}
		l_minuendMaxY = Math.max(l_minuendMaxY, l_minuendNextY);
		l_subtrahendMaxY = Math.max(l_subtrahendMaxY, l_subtrahendNextY);
		l_minuendMinY = Math.min(l_minuendMinY, l_minuendNextY);
		l_subtrahendMinY = Math.min(l_subtrahendMinY, l_subtrahendNextY);
		l_minuendXs.add(l_minuendNextX);
		l_minuendYs.add(l_minuendNextY);
		l_subtrahendXs.add(l_subtrahendNextX);
		l_subtrahendYs.add(l_subtrahendNextY);
		l_polygonXs.addAll(l_minuendXs);
		l_polygonYs.addAll(l_minuendYs);
		Collections.reverse(l_subtrahendXs);
		Collections.reverse(l_subtrahendYs);
		l_polygonXs.addAll(l_subtrahendXs);
		l_polygonYs.addAll(l_subtrahendYs);
		b_positive = (l_subtrahendMaxY <= l_minuendMaxY) && (l_subtrahendMinY <= l_minuendMinY);
		xYDifferenceRenderer.createPolygon(x_graphics, x_dataArea, x_plot, x_domainAxis, x_rangeAxis, b_positive,
				l_polygonXs, l_polygonYs);
	}

	/**
	* Determines if the two (2) series are disjoint. Disjoint series do not overlap in the domain space.
	* @param x_dataset   the dataset.
	* @return  true if the dataset is degenerate.
	*/
	public boolean areSeriesDisjoint(XYDataset x_dataset) {
		int l_minuendItemCount = x_dataset.getItemCount(0);
		double l_minuendFirst = x_dataset.getXValue(0, 0);
		double l_minuendLast = x_dataset.getXValue(0, l_minuendItemCount - 1);
		int l_subtrahendItemCount = x_dataset.getItemCount(1);
		double l_subtrahendFirst = x_dataset.getXValue(1, 0);
		double l_subtrahendLast = x_dataset.getXValue(1, l_subtrahendItemCount - 1);
		return ((l_minuendLast < l_subtrahendFirst) || (l_subtrahendLast < l_minuendFirst));
	}

	/**
	* Determines if a dataset is degenerate.  A degenerate dataset is a dataset where either series has less than two (2) points.
	* @param x_dataset   the dataset.
	* @param x_impliedZeroSubtrahend   if false, do not check the subtrahend
	* @return  true if the dataset is degenerate.
	*/
	public boolean isEitherSeriesDegenerate(XYDataset x_dataset, boolean x_impliedZeroSubtrahend) {
		if (x_impliedZeroSubtrahend) {
			return (x_dataset.getItemCount(0) < 2);
		}
		return ((x_dataset.getItemCount(0) < 2) || (x_dataset.getItemCount(1) < 2));
	}

	public Object clone() throws CloneNotSupportedException {
		return (XYDifferenceRendererProduct) super.clone();
	}
}