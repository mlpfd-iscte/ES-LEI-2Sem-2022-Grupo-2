/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * --------------
 * MeterPlot.java
 * --------------
 * (C) Copyright 2000-2021, by Hari and Contributors.
 *
 * Original Author:  Hari (ourhari@hotmail.com);
 * Contributor(s):   David Gilbert;
 *                   Bob Orchard;
 *                   Arnaud Lelievre;
 *                   Nicolas Brodu;
 *                   David Bastend;
 *
 */

package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.ValueDataset;

/**
 * A plot that displays a single value in the form of a needle on a dial.
 * Defined ranges (for example, 'normal', 'warning' and 'critical') can be
 * highlighted on the dial.
 */
public class MeterPlot extends Plot implements Serializable, Cloneable {

    private MeterPlotProduct2 meterPlotProduct2 = new MeterPlotProduct2();

	private MeterPlotProduct meterPlotProduct = new MeterPlotProduct();

	/** For serialization. */
    private static final long serialVersionUID = 2987472457734470962L;

    /** The default background paint. */
    static final Paint DEFAULT_DIAL_BACKGROUND_PAINT = Color.BLACK;

    /** The default needle paint. */
    static final Paint DEFAULT_NEEDLE_PAINT = Color.GREEN;

    /** The default value font. */
    static final Font DEFAULT_VALUE_FONT = new Font("SansSerif", Font.BOLD, 12);

    /** The default value paint. */
    static final Paint DEFAULT_VALUE_PAINT = Color.YELLOW;

    /** The default meter angle. */
    public static final int DEFAULT_METER_ANGLE = 270;

    /** The default border size. */
    public static final float DEFAULT_BORDER_SIZE = 3f;

    /** The default circle size. */
    public static final float DEFAULT_CIRCLE_SIZE = 10f;

    /** The default label font. */
    public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif",
            Font.BOLD, 10);

    /** The dataset (contains a single value). */
    private ValueDataset dataset;

    /** The tick size. */
    private double tickSize;

    /** A flag that controls whether or not the border is drawn. */
    private boolean drawBorder;

    /** The tick label format. */
    private NumberFormat tickLabelFormat;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            = ResourceBundle.getBundle("org.jfree.chart.plot.LocalizationBundle");

    /**
     * A (possibly empty) list of the {@link MeterInterval}s to be highlighted
     * on the dial.
     */
    private List<MeterInterval> intervals;

    /**
     * Creates a new plot with a default range of {@code 0} to {@code 100} and 
     * no value to display.
     */
    public MeterPlot() {
        this(null);
    }

    /**
     * Creates a new plot that displays the value from the supplied dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     */
    public MeterPlot(ValueDataset dataset) {
        super();
        getMeterPlotProduct2().setShape(DialShape.CIRCLE);
        getMeterPlotProduct2().setMeterAngle2(DEFAULT_METER_ANGLE);
        getMeterPlotProduct2().setRange2(new Range(0.0, 100.0));
        this.tickSize = 10.0;
        meterPlotProduct.setTickPaint2(Color.WHITE);
        meterPlotProduct.setUnits2("Units");
        meterPlotProduct.setNeedlePaint2(MeterPlot.DEFAULT_NEEDLE_PAINT);
        meterPlotProduct.setTickLabelsVisible2(true);
        meterPlotProduct.setTickLabelFont2(MeterPlot.DEFAULT_LABEL_FONT);
        meterPlotProduct.setTickLabelPaint2(Color.BLACK);
        this.tickLabelFormat = NumberFormat.getInstance();
        meterPlotProduct.setValueFont2(MeterPlot.DEFAULT_VALUE_FONT);
        meterPlotProduct.setValuePaint2(MeterPlot.DEFAULT_VALUE_PAINT);
        meterPlotProduct.setDialBackgroundPaint2(MeterPlot.DEFAULT_DIAL_BACKGROUND_PAINT);
        this.intervals = new ArrayList<>();
        setDataset(dataset);
    }

    /**
     * Returns the dial shape.  The default is {@link DialShape#CIRCLE}).
     *
     * @return The dial shape (never {@code null}).
     *
     * @see #setDialShape(DialShape)
     */
    public DialShape getDialShape() {
        return this.getMeterPlotProduct2().getShape();
    }

    /**
     * Sets the dial shape and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param shape  the shape ({@code null} not permitted).
     *
     * @see #getDialShape()
     */
    public void setDialShape(DialShape shape) {
        getMeterPlotProduct2().setDialShape(shape, this);
    }

    /**
     * Returns the meter angle in degrees.  This defines, in part, the shape
     * of the dial.  The default is 270 degrees.
     *
     * @return The meter angle (in degrees).
     *
     * @see #setMeterAngle(int)
     */
    public int getMeterAngle() {
        return this.getMeterPlotProduct2().getMeterAngle();
    }

    /**
     * Sets the angle (in degrees) for the whole range of the dial and sends
     * a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param angle  the angle (in degrees, in the range 1-360).
     *
     * @see #getMeterAngle()
     */
    public void setMeterAngle(int angle) {
        getMeterPlotProduct2().setMeterAngle(angle, this);
    }

    /**
     * Returns the overall range for the dial.
     *
     * @return The overall range (never {@code null}).
     *
     * @see #setRange(Range)
     */
    public Range getRange() {
        return this.getMeterPlotProduct2().getRange();
    }

    /**
     * Sets the range for the dial and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param range  the range ({@code null} not permitted and zero-length
     *               ranges not permitted).
     *
     * @see #getRange()
     */
    public void setRange(Range range) {
        getMeterPlotProduct2().setRange(range, this);
    }

    /**
     * Returns the tick size (the interval between ticks on the dial).
     *
     * @return The tick size.
     *
     * @see #setTickSize(double)
     */
    public double getTickSize() {
        return this.tickSize;
    }

    /**
     * Sets the tick size and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param size  the tick size (must be &gt; 0).
     *
     * @see #getTickSize()
     */
    public void setTickSize(double size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Requires 'size' > 0.");
        }
        this.tickSize = size;
        fireChangeEvent();
    }

    /**
     * Returns the paint used to draw the ticks around the dial.
     *
     * @return The paint used to draw the ticks around the dial (never
     *         {@code null}).
     *
     * @see #setTickPaint(Paint)
     */
    public Paint getTickPaint() {
        return this.meterPlotProduct.getTickPaint();
    }

    /**
     * Sets the paint used to draw the tick labels around the dial and sends
     * a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getTickPaint()
     */
    public void setTickPaint(Paint paint) {
        meterPlotProduct.setTickPaint(paint, this);
    }

    /**
     * Returns a string describing the units for the dial.
     *
     * @return The units (possibly {@code null}).
     *
     * @see #setUnits(String)
     */
    public String getUnits() {
        return this.meterPlotProduct.getUnits();
    }

    /**
     * Sets the units for the dial and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param units  the units ({@code null} permitted).
     *
     * @see #getUnits()
     */
    public void setUnits(String units) {
        meterPlotProduct.setUnits(units, this);
    }

    /**
     * Returns the paint for the needle.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setNeedlePaint(Paint)
     */
    public Paint getNeedlePaint() {
        return this.meterPlotProduct.getNeedlePaint();
    }

    /**
     * Sets the paint used to display the needle and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getNeedlePaint()
     */
    public void setNeedlePaint(Paint paint) {
        meterPlotProduct.setNeedlePaint(paint, this);
    }

    /**
     * Returns the flag that determines whether or not tick labels are visible.
     *
     * @return The flag.
     *
     * @see #setTickLabelsVisible(boolean)
     */
    public boolean getTickLabelsVisible() {
        return this.meterPlotProduct.getTickLabelsVisible();
    }

    /**
     * Sets the flag that controls whether or not the tick labels are visible
     * and sends a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param visible  the flag.
     *
     * @see #getTickLabelsVisible()
     */
    public void setTickLabelsVisible(boolean visible) {
        meterPlotProduct.setTickLabelsVisible(visible, this);
    }

    /**
     * Returns the tick label font.
     *
     * @return The font (never {@code null}).
     *
     * @see #setTickLabelFont(Font)
     */
    public Font getTickLabelFont() {
        return this.meterPlotProduct.getTickLabelFont();
    }

    /**
     * Sets the tick label font and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getTickLabelFont()
     */
    public void setTickLabelFont(Font font) {
        meterPlotProduct.setTickLabelFont(font, this);
    }

    /**
     * Returns the tick label paint.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setTickLabelPaint(Paint)
     */
    public Paint getTickLabelPaint() {
        return this.meterPlotProduct.getTickLabelPaint();
    }

    /**
     * Sets the tick label paint and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getTickLabelPaint()
     */
    public void setTickLabelPaint(Paint paint) {
        meterPlotProduct.setTickLabelPaint(paint, this);
    }

    /**
     * Returns the flag that controls whether or not the value is visible.
     * The default value is {@code true}.
     *
     * @return A flag.
     *
     * @see #setValueVisible
     * @since 1.5.4
     */
    public boolean isValueVisible() {
        return meterPlotProduct.getValueVisible();
    }

    /**
     *  Sets the flag that controls whether or not the value is visible
     *  and sends a change event to all registered listeners.
     *
     * @param valueVisible  the new flag value.
     *
     * @see #isValueVisible()
     * @since 1.5.4
     */
    public void setValueVisible(boolean valueVisible) {
        meterPlotProduct.setValueVisible(valueVisible, this);
    }

    /**
     * Returns the tick label format.
     *
     * @return The tick label format (never {@code null}).
     *
     * @see #setTickLabelFormat(NumberFormat)
     */
    public NumberFormat getTickLabelFormat() {
        return this.tickLabelFormat;
    }

    /**
     * Sets the format for the tick labels and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @param format  the format ({@code null} not permitted).
     *
     * @see #getTickLabelFormat()
     */
    public void setTickLabelFormat(NumberFormat format) {
        Args.nullNotPermitted(format, "format");
        this.tickLabelFormat = format;
        fireChangeEvent();
    }

    /**
     * Returns the font for the value label.
     *
     * @return The font (never {@code null}).
     *
     * @see #setValueFont(Font)
     */
    public Font getValueFont() {
        return this.meterPlotProduct.getValueFont();
    }

    /**
     * Sets the font used to display the value label and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getValueFont()
     */
    public void setValueFont(Font font) {
        meterPlotProduct.setValueFont(font, this);
    }

    /**
     * Returns the paint for the value label.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setValuePaint(Paint)
     */
    public Paint getValuePaint() {
        return this.meterPlotProduct.getValuePaint();
    }

    /**
     * Sets the paint used to display the value label and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getValuePaint()
     */
    public void setValuePaint(Paint paint) {
        meterPlotProduct.setValuePaint(paint, this);
    }

    /**
     * Returns the paint for the dial background.
     *
     * @return The paint (possibly {@code null}).
     *
     * @see #setDialBackgroundPaint(Paint)
     */
    public Paint getDialBackgroundPaint() {
        return this.meterPlotProduct.getDialBackgroundPaint();
    }

    /**
     * Sets the paint used to fill the dial background.  Set this to
     * {@code null} for no background.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getDialBackgroundPaint()
     */
    public void setDialBackgroundPaint(Paint paint) {
        meterPlotProduct.setDialBackgroundPaint(paint, this);
    }

    /**
     * Returns a flag that controls whether or not a rectangular border is
     * drawn around the plot area.
     *
     * @return A flag.
     *
     * @see #setDrawBorder(boolean)
     */
    public boolean getDrawBorder() {
        return this.drawBorder;
    }

    /**
     * Sets the flag that controls whether or not a rectangular border is drawn
     * around the plot area and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param draw  the flag.
     *
     * @see #getDrawBorder()
     */
    public void setDrawBorder(boolean draw) {
        // TODO: fix output when this flag is set to true
        this.drawBorder = draw;
        fireChangeEvent();
    }

    /**
     * Returns the dial outline paint.
     *
     * @return The paint.
     *
     * @see #setDialOutlinePaint(Paint)
     */
    public Paint getDialOutlinePaint() {
        return this.meterPlotProduct.getDialOutlinePaint();
    }

    /**
     * Sets the dial outline paint and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param paint  the paint.
     *
     * @see #getDialOutlinePaint()
     */
    public void setDialOutlinePaint(Paint paint) {
        meterPlotProduct.setDialOutlinePaint(paint, this);
    }

    /**
     * Returns the dataset for the plot.
     *
     * @return The dataset (possibly {@code null}).
     *
     * @see #setDataset(ValueDataset)
     */
    public ValueDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset for the plot, replacing the existing dataset if there
     * is one, and triggers a {@link PlotChangeEvent}.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @see #getDataset()
     */
    public void setDataset(ValueDataset dataset) {

        // if there is an existing dataset, remove the plot from the list of
        // change listeners...
        ValueDataset existing = this.dataset;
        if (existing != null) {
            existing.removeChangeListener(this);
        }

        // set the new dataset, and register the chart as a change listener...
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        // send a dataset change event to self...
        DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
        datasetChanged(event);

    }

    /**
     * Returns an unmodifiable list of the intervals for the plot.
     *
     * @return A list.
     *
     * @see #addInterval(MeterInterval)
     */
    public List<MeterInterval> getIntervals() {
        return Collections.unmodifiableList(intervals);
    }

    /**
     * Adds an interval and sends a {@link PlotChangeEvent} to all registered
     * listeners.
     *
     * @param interval  the interval ({@code null} not permitted).
     *
     * @see #getIntervals()
     * @see #clearIntervals()
     */
    public void addInterval(MeterInterval interval) {
        Args.nullNotPermitted(interval, "interval");
        intervals.add(interval);
        fireChangeEvent();
    }

    /**
     * Clears the intervals for the plot and sends a {@link PlotChangeEvent} to
     * all registered listeners.
     *
     * @see #addInterval(MeterInterval)
     */
    public void clearIntervals() {
        intervals.clear();
        fireChangeEvent();
    }

    /**
     * Returns an item for each interval.
     *
     * @return A collection of legend items.
     */
    @Override
    public LegendItemCollection getLegendItems() {
        LegendItemCollection result = new LegendItemCollection();
        for (MeterInterval mi : intervals) {
            Paint color = mi.getBackgroundPaint();
            if (color == null) {
                color = mi.getOutlinePaint();
            }
            LegendItem item = new LegendItem(mi.getLabel(), mi.getLabel(),
                    null, null, new Rectangle2D.Double(-4.0, -4.0, 8.0, 8.0),
                    color);
            item.setDataset(getDataset());
            result.add(item);
        }
        return result;
    }

    /**
     * Draws the plot on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device.
     * @param area  the area within which the plot should be drawn.
     * @param anchor  the anchor point ({@code null} permitted).
     * @param parentState  the state from the parent plot, if there is one.
     * @param info  collects info about the drawing.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
                     PlotState parentState, PlotRenderingInfo info) {

        if (info != null) {
            info.setPlotArea(area);
        }

        // adjust for insets...
        RectangleInsets insets = getInsets();
        insets.trim(area);

        area.setRect(area.getX() + 4, area.getY() + 4, area.getWidth() - 8,
                area.getHeight() - 8);

        // draw the background
        if (this.drawBorder) {
            drawBackground(g2, area);
        }

        Rectangle2D meterArea = meterArea(area);
		Rectangle2D.Double originalArea = new Rectangle2D.Double(
                meterArea.getX() - 4, meterArea.getY() - 4,
                meterArea.getWidth() + 8, meterArea.getHeight() + 8);

        double meterMiddleX = meterArea.getCenterX();
        double meterMiddleY = meterArea.getCenterY();

        // plot the data (unless the dataset is null)...
        ValueDataset data = getDataset();
        if (data != null) {
            double dataMin = this.getMeterPlotProduct2().getRange().getLowerBound();
            double dataMax = this.getMeterPlotProduct2().getRange().getUpperBound();

            Shape savedClip = g2.getClip();
            g2.clip(originalArea);
            Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    getForegroundAlpha()));

            if (this.meterPlotProduct.getDialBackgroundPaint() != null) {
                getMeterPlotProduct2().fillArc(g2, originalArea, dataMin, dataMax,
                        this.meterPlotProduct.getDialBackgroundPaint(), true);
            }
            drawTicks(g2, meterArea, dataMin, dataMax);
            MeterInterval.drawArcForInterval(g2, meterArea, new MeterInterval("", this.getMeterPlotProduct2().getRange(),
                    this.meterPlotProduct.getDialOutlinePaint(), new BasicStroke(1.0f), null), this);

            for (MeterInterval interval : this.intervals) {
            	MeterInterval.drawArcForInterval(g2, meterArea, interval, this);
            }

            Number n = data.getValue();
            if (n != null) {
                double value = n.doubleValue();
                drawValueLabel(g2, meterArea);

                if (this.getMeterPlotProduct2().getRange().contains(value)) {
                    g2.setPaint(this.meterPlotProduct.getNeedlePaint());
                    g2.setStroke(new BasicStroke(2.0f));

                    Polygon arrow = arrow(meterArea, meterMiddleX, meterMiddleY, value);
					g2.fill(arrow);

                    Ellipse2D circle = new Ellipse2D.Double(meterMiddleX
                            - DEFAULT_CIRCLE_SIZE / 2, meterMiddleY
                            - DEFAULT_CIRCLE_SIZE / 2, DEFAULT_CIRCLE_SIZE,
                            DEFAULT_CIRCLE_SIZE);
                    g2.fill(circle);
                }
            }

            g2.setClip(savedClip);
            g2.setComposite(originalComposite);

        }
        if (this.drawBorder) {
            drawOutline(g2, area);
        }

    }

	private Rectangle2D meterArea(Rectangle2D area) {
		double gapHorizontal = (2 * DEFAULT_BORDER_SIZE);
		double gapVertical = (2 * DEFAULT_BORDER_SIZE);
		double meterX = area.getX() + gapHorizontal / 2;
		double meterY = area.getY() + gapVertical / 2;
		double meterW = area.getWidth() - gapHorizontal;
		double meterH = area.getHeight() - gapVertical + ((this.getMeterPlotProduct2().getMeterAngle() <= 180)
				&& (this.getMeterPlotProduct2().getShape() != DialShape.CIRCLE) ? area.getHeight() / 1.25 : 0);
		double min = Math.min(meterW, meterH) / 2;
		meterX = (meterX + meterX + meterW) / 2 - min;
		meterY = (meterY + meterY + meterH) / 2 - min;
		meterW = 2 * min;
		meterH = 2 * min;
		Rectangle2D meterArea = new Rectangle2D.Double(meterX, meterY, meterW, meterH);
		return meterArea;
	}

	private Polygon arrow(Rectangle2D meterArea, double meterMiddleX, double meterMiddleY, double value) {
		double radius = (meterArea.getWidth() / 2) + DEFAULT_BORDER_SIZE + 15;
		double valueAngle = getMeterPlotProduct2().valueToAngle(value);
		double valueP1 = meterMiddleX + (radius * Math.cos(Math.PI * (valueAngle / 180)));
		double valueP2 = meterMiddleY - (radius * Math.sin(Math.PI * (valueAngle / 180)));
		Polygon arrow = new Polygon();
		if ((valueAngle > 135 && valueAngle < 225) || (valueAngle < 45 && valueAngle > -45)) {
			double valueP3 = (meterMiddleY - DEFAULT_CIRCLE_SIZE / 4);
			double valueP4 = (meterMiddleY + DEFAULT_CIRCLE_SIZE / 4);
			arrow.addPoint((int) meterMiddleX, (int) valueP3);
			arrow.addPoint((int) meterMiddleX, (int) valueP4);
		} else {
			arrow.addPoint((int) (meterMiddleX - DEFAULT_CIRCLE_SIZE / 4), (int) meterMiddleY);
			arrow.addPoint((int) (meterMiddleX + DEFAULT_CIRCLE_SIZE / 4), (int) meterMiddleY);
		}
		arrow.addPoint((int) valueP1, (int) valueP2);
		return arrow;
	}

    /**
     * Draws an arc.
     *
     * @param g2  the graphics device.
     * @param area  the plot area.
     * @param minValue  the minimum value.
     * @param maxValue  the maximum value.
     * @param paint  the paint.
     * @param stroke  the stroke.
     */
    void drawArc(Graphics2D g2, Rectangle2D area, double minValue,
                           double maxValue, Paint paint, Stroke stroke) {

        double startAngle = getMeterPlotProduct2().valueToAngle(maxValue);
        double endAngle = getMeterPlotProduct2().valueToAngle(minValue);
        double extent = endAngle - startAngle;

        double x = area.getX();
        double y = area.getY();
        double w = area.getWidth();
        double h = area.getHeight();
        g2.setPaint(paint);
        g2.setStroke(stroke);

        if (paint != null && stroke != null) {
            Arc2D.Double arc = new Arc2D.Double(x, y, w, h, startAngle,
                    extent, Arc2D.OPEN);
            g2.setPaint(paint);
            g2.setStroke(stroke);
            g2.draw(arc);
        }

    }

    /**
     * Fills an arc on the dial between the given values.
     *
     * @param g2  the graphics device.
     * @param area  the plot area.
     * @param minValue  the minimum data value.
     * @param maxValue  the maximum data value.
     * @param paint  the background paint ({@code null} not permitted).
     * @param dial  a flag that indicates whether the arc represents the whole
     *              dial.
     */
    protected void fillArc(Graphics2D g2, Rectangle2D area,
            double minValue, double maxValue, Paint paint, boolean dial) {

        getMeterPlotProduct2().fillArc(g2, area, minValue, maxValue, paint, dial);
    }

    /**
     * Translates a data value to an angle on the dial.
     *
     * @param value  the value.
     *
     * @return The angle on the dial.
     */
    public double valueToAngle(double value) {
        return getMeterPlotProduct2().valueToAngle(value);
    }

    /**
     * Draws the ticks that subdivide the overall range.
     *
     * @param g2  the graphics device.
     * @param meterArea  the meter area.
     * @param minValue  the minimum value.
     * @param maxValue  the maximum value.
     */
    protected void drawTicks(Graphics2D g2, Rectangle2D meterArea,
                             double minValue, double maxValue) {
        for (double v = minValue; v <= maxValue; v += this.tickSize) {
            drawTick(g2, meterArea, v);
        }
    }

    /**
     * Draws a tick.
     *
     * @param g2  the graphics device.
     * @param meterArea  the meter area.
     * @param value  the value.
     */
    protected void drawTick(Graphics2D g2, Rectangle2D meterArea,
            double value) {
        drawTick(g2, meterArea, value, false);
    }

    /**
     * Draws a tick on the dial.
     *
     * @param g2  the graphics device.
     * @param meterArea  the meter area.
     * @param value  the tick value.
     * @param label  a flag that controls whether or not a value label is drawn.
     */
    protected void drawTick(Graphics2D g2, Rectangle2D meterArea,
                            double value, boolean label) {

        double valueAngle = getMeterPlotProduct2().valueToAngle(value);

        double meterMiddleX = meterArea.getCenterX();
        double meterMiddleY = meterArea.getCenterY();

        g2.setPaint(this.meterPlotProduct.getTickPaint());
        g2.setStroke(new BasicStroke(2.0f));

        double valueP2X;
        double valueP2Y;

        double radius = (meterArea.getWidth() / 2) + DEFAULT_BORDER_SIZE;
        double radius1 = radius - 15;

        double valueP1X = meterMiddleX
                + (radius * Math.cos(Math.PI * (valueAngle / 180)));
        double valueP1Y = meterMiddleY
                - (radius * Math.sin(Math.PI * (valueAngle / 180)));

        valueP2X = meterMiddleX
                + (radius1 * Math.cos(Math.PI * (valueAngle / 180)));
        valueP2Y = meterMiddleY
                - (radius1 * Math.sin(Math.PI * (valueAngle / 180)));

        Line2D.Double line = new Line2D.Double(valueP1X, valueP1Y, valueP2X,
                valueP2Y);
        g2.draw(line);

        if (this.meterPlotProduct.getTickLabelsVisible() && label) {

            String tickLabel =  this.tickLabelFormat.format(value);
            g2.setFont(this.meterPlotProduct.getTickLabelFont());
            g2.setPaint(this.meterPlotProduct.getTickLabelPaint());

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D tickLabelBounds
                = TextUtils.getTextBounds(tickLabel, g2, fm);

            double x = valueP2X;
            double y = valueP2Y;
            if (valueAngle == 90 || valueAngle == 270) {
                x = x - tickLabelBounds.getWidth() / 2;
            }
            else if (valueAngle < 90 || valueAngle > 270) {
                x = x - tickLabelBounds.getWidth();
            }
            if ((valueAngle > 135 && valueAngle < 225)
                    || valueAngle > 315 || valueAngle < 45) {
                y = y - tickLabelBounds.getHeight() / 2;
            }
            else {
                y = y + tickLabelBounds.getHeight() / 2;
            }
            g2.drawString(tickLabel, (float) x, (float) y);
        }
    }

    /**
     * Draws the value label just below the center of the dial.
     *
     * @param g2  the graphics device.
     * @param area  the plot area.
     */
    protected void drawValueLabel(Graphics2D g2, Rectangle2D area) {
        if (meterPlotProduct.getValueVisible()) {
            g2.setFont(this.meterPlotProduct.getValueFont());
            g2.setPaint(this.meterPlotProduct.getValuePaint());
            String valueStr = "No value";
            if (this.dataset != null) {
                Number n = this.dataset.getValue();
                if (n != null) {
                    valueStr = this.tickLabelFormat.format(n.doubleValue()) + " "
                        + this.meterPlotProduct.getUnits();
                }
            }
            float x = (float) area.getCenterX();
            float y = (float) area.getCenterY() + DEFAULT_CIRCLE_SIZE;
            TextUtils.drawAlignedString(valueStr, g2, x, y,
                TextAnchor.TOP_CENTER);
        }
    }

    /**
     * Returns a short string describing the type of plot.
     *
     * @return A string describing the type of plot.
     */
    @Override
    public String getPlotType() {
        return localizationResources.getString("Meter_Plot");
    }

    /**
     * A zoom method that does nothing.  Plots are required to support the
     * zoom operation.  In the case of a meter plot, it doesn't make sense to
     * zoom in or out, so the method is empty.
     *
     * @param percent   The zoom percentage.
     */
    @Override
    public void zoom(double percent) {
        // intentionally blank
    }

    /**
     * Tests the plot for equality with an arbitrary object.  Note that the
     * dataset is ignored for the purposes of testing equality.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MeterPlot)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        MeterPlot that = (MeterPlot) obj;
        if (!Objects.equals(this.meterPlotProduct.getUnits(), that.meterPlotProduct.getUnits())) {
            return false;
        }
        if (!Objects.equals(this.getMeterPlotProduct2().getRange(), that.getMeterPlotProduct2().getRange())) {
            return false;
        }
        if (!Objects.equals(this.intervals, that.intervals)) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getDialOutlinePaint(),
                that.meterPlotProduct.getDialOutlinePaint())) {
            return false;
        }
        if (this.getMeterPlotProduct2().getShape() != that.getMeterPlotProduct2().getShape()) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getDialBackgroundPaint(),
                that.meterPlotProduct.getDialBackgroundPaint())) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getNeedlePaint(), that.meterPlotProduct.getNeedlePaint())) {
            return false;
        }
        if (this.meterPlotProduct.getValueVisible() != that.meterPlotProduct.getValueVisible()) {
            return false;
        }
        if (!Objects.equals(this.meterPlotProduct.getValueFont(), that.meterPlotProduct.getValueFont())) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getValuePaint(), that.meterPlotProduct.getValuePaint())) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getTickPaint(), that.meterPlotProduct.getTickPaint())) {
            return false;
        }
        if (this.tickSize != that.tickSize) {
            return false;
        }
        if (this.meterPlotProduct.getTickLabelsVisible() != that.meterPlotProduct.getTickLabelsVisible()) {
            return false;
        }
        if (!Objects.equals(this.meterPlotProduct.getTickLabelFont(), that.meterPlotProduct.getTickLabelFont())) {
            return false;
        }
        if (!PaintUtils.equal(this.meterPlotProduct.getTickLabelPaint(), that.meterPlotProduct.getTickLabelPaint())) {
            return false;
        }
        if (!Objects.equals(this.tickLabelFormat, that.tickLabelFormat)) {
            return false;
        }
        if (this.drawBorder != that.drawBorder) {
            return false;
        }
        if (this.getMeterPlotProduct2().getMeterAngle() != that.getMeterPlotProduct2().getMeterAngle()) {
            return false;
        }
        return true;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.meterPlotProduct.getDialBackgroundPaint(), stream);
        SerialUtils.writePaint(this.meterPlotProduct.getDialOutlinePaint(), stream);
        SerialUtils.writePaint(this.meterPlotProduct.getNeedlePaint(), stream);
        SerialUtils.writePaint(this.meterPlotProduct.getValuePaint(), stream);
        SerialUtils.writePaint(this.meterPlotProduct.getTickPaint(), stream);
        SerialUtils.writePaint(this.meterPlotProduct.getTickLabelPaint(), stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream(stream);
		if (this.dataset != null) {
            this.dataset.addChangeListener(this);
        }
    }

	private void stream(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		meterPlotProduct.setDialBackgroundPaint2(SerialUtils.readPaint(stream));
		meterPlotProduct.setDialOutlinePaint2(SerialUtils.readPaint(stream));
		meterPlotProduct.setNeedlePaint2(SerialUtils.readPaint(stream));
		meterPlotProduct.setValuePaint2(SerialUtils.readPaint(stream));
		meterPlotProduct.setTickPaint2(SerialUtils.readPaint(stream));
		meterPlotProduct.setTickLabelPaint2(SerialUtils.readPaint(stream));
	}

    /**
     * Returns an independent copy (clone) of the plot.  The dataset is NOT
     * cloned - both the original and the clone will have a reference to the
     * same dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if some component of the plot cannot
     *         be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        MeterPlot clone = (MeterPlot) super.clone();
		clone.setMeterPlotProduct2((MeterPlotProduct2) this.getMeterPlotProduct2().clone());
		clone.meterPlotProduct = (MeterPlotProduct) this.meterPlotProduct.clone();
        clone.tickLabelFormat = (NumberFormat) this.tickLabelFormat.clone();
        // the following relies on the fact that the intervals are immutable
        clone.intervals = new ArrayList<>(this.intervals);
        if (clone.dataset != null) {
            clone.dataset.addChangeListener(clone);
        }
        return clone;
    }

	public MeterPlotProduct2 getMeterPlotProduct2() {
		return meterPlotProduct2;
	}

	public void setMeterPlotProduct2(MeterPlotProduct2 meterPlotProduct2) {
		this.meterPlotProduct2 = meterPlotProduct2;
	}

}
