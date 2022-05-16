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
 * ----------------------
 * StandardDialScale.java
 * ----------------------
 * (C) Copyright 2006-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;

/**
 * A scale for a {@link DialPlot}.
 */
public class StandardDialScale extends AbstractDialLayer implements DialScale,
        Cloneable, PublicCloneable, Serializable {

    private StandardDialScaleProduct2 standardDialScaleProduct2 = new StandardDialScaleProduct2();

	private StandardDialScaleProduct standardDialScaleProduct = new StandardDialScaleProduct();

	/** For serialization. */
    static final long serialVersionUID = 3715644629665918516L;

    /**
     * Creates a new instance of DialScale.
     */
    public StandardDialScale() {
        this(0.0, 100.0, 175, -170, 10.0, 4);
    }

    /**
     * Creates a new instance.
     *
     * @param lowerBound  the lower bound of the scale.
     * @param upperBound  the upper bound of the scale.
     * @param startAngle  the start angle (in degrees, using the same
     *     orientation as Java's {@code Arc2D} class).
     * @param extent  the extent (in degrees, counter-clockwise).
     * @param majorTickIncrement  the interval between major tick marks (must
     *     be &gt; 0).
     * @param minorTickCount  the number of minor ticks between major tick
     *          marks.
     */
    public StandardDialScale(double lowerBound, double upperBound,
            double startAngle, double extent, double majorTickIncrement,
            int minorTickCount) {
        if (majorTickIncrement <= 0.0) {
            throw new IllegalArgumentException(
                    "Requires 'majorTickIncrement' > 0.");
        }
        standardDialScaleProduct.setStartAngle2(startAngle);
        standardDialScaleProduct.setExtent2(extent);
        standardDialScaleProduct.setLowerBound2(lowerBound);
        standardDialScaleProduct.setUpperBound2(upperBound);
        standardDialScaleProduct.setTickRadius2(0.70);
        standardDialScaleProduct.setTickLabelsVisible2(true);
        standardDialScaleProduct2.setTickLabelFormatter2(new DecimalFormat("0.0"));
        standardDialScaleProduct2.setFirstTickLabelVisible2(true);
        standardDialScaleProduct.setTickLabelFont2(new Font("Dialog", Font.BOLD, 16));
        standardDialScaleProduct.setTickLabelPaint2(Color.BLUE);
        standardDialScaleProduct.setTickLabelOffset2(0.10);
        standardDialScaleProduct.setMajorTickIncrement2(majorTickIncrement);
        standardDialScaleProduct.setMajorTickLength2(0.04);
        standardDialScaleProduct.setMajorTickPaint2(Color.BLACK);
        standardDialScaleProduct.setMajorTickStroke2(new BasicStroke(3.0f));
        standardDialScaleProduct.setMinorTickCount2(minorTickCount);
        standardDialScaleProduct.setMinorTickLength2(0.02);
        standardDialScaleProduct.setMinorTickPaint2(Color.BLACK);
        standardDialScaleProduct.setMinorTickStroke2(new BasicStroke(1.0f));
    }

    /**
     * Returns the lower bound for the scale.
     *
     * @return The lower bound for the scale.
     *
     * @see #setLowerBound(double)
     */
    public double getLowerBound() {
        return this.standardDialScaleProduct.getLowerBound();
    }

    /**
     * Sets the lower bound for the scale and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param lower  the lower bound.
     *
     * @see #getLowerBound()
     */
    public void setLowerBound(double lower) {
        standardDialScaleProduct.setLowerBound(lower, this);
    }

    /**
     * Returns the upper bound for the scale.
     *
     * @return The upper bound for the scale.
     *
     * @see #setUpperBound(double)
     */
    public double getUpperBound() {
        return this.standardDialScaleProduct.getUpperBound();
    }

    /**
     * Sets the upper bound for the scale and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param upper  the upper bound.
     *
     * @see #getUpperBound()
     */
    public void setUpperBound(double upper) {
        standardDialScaleProduct.setUpperBound(upper, this);
    }

    /**
     * Returns the start angle for the scale (in degrees using the same
     * orientation as Java's {@code Arc2D} class).
     *
     * @return The start angle.
     *
     * @see #setStartAngle(double)
     */
    public double getStartAngle() {
        return this.standardDialScaleProduct.getStartAngle();
    }

    /**
     * Sets the start angle for the scale and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param angle  the angle (in degrees).
     *
     * @see #getStartAngle()
     */
    public void setStartAngle(double angle) {
        standardDialScaleProduct.setStartAngle(angle, this);
    }

    /**
     * Returns the extent.
     *
     * @return The extent.
     *
     * @see #setExtent(double)
     */
    public double getExtent() {
        return this.standardDialScaleProduct.getExtent();
    }

    /**
     * Sets the extent and sends a {@link DialLayerChangeEvent} to all
     * registered listeners.
     *
     * @param extent  the extent.
     *
     * @see #getExtent()
     */
    public void setExtent(double extent) {
        standardDialScaleProduct.setExtent(extent, this);
    }

    /**
     * Returns the radius (as a percentage of the maximum space available) of
     * the outer limit of the tick marks.
     *
     * @return The tick radius.
     *
     * @see #setTickRadius(double)
     */
    public double getTickRadius() {
        return this.standardDialScaleProduct.getTickRadius();
    }

    /**
     * Sets the tick radius and sends a {@link DialLayerChangeEvent} to all
     * registered listeners.
     *
     * @param radius  the radius.
     *
     * @see #getTickRadius()
     */
    public void setTickRadius(double radius) {
        standardDialScaleProduct.setTickRadius(radius, this);
    }

    /**
     * Returns the increment (in data units) between major tick labels.
     *
     * @return The increment between major tick labels.
     *
     * @see #setMajorTickIncrement(double)
     */
    public double getMajorTickIncrement() {
        return this.standardDialScaleProduct.getMajorTickIncrement();
    }

    /**
     * Sets the increment (in data units) between major tick labels and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param increment  the increment (must be &gt; 0).
     *
     * @see #getMajorTickIncrement()
     */
    public void setMajorTickIncrement(double increment) {
        standardDialScaleProduct.setMajorTickIncrement(increment, this);
    }

    /**
     * Returns the length factor for the major tick marks.  The value is
     * subtracted from the tick radius to determine the inner starting point
     * for the tick marks.
     *
     * @return The length factor.
     *
     * @see #setMajorTickLength(double)
     */
    public double getMajorTickLength() {
        return this.standardDialScaleProduct.getMajorTickLength();
    }

    /**
     * Sets the length factor for the major tick marks and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param length  the length.
     *
     * @see #getMajorTickLength()
     */
    public void setMajorTickLength(double length) {
        standardDialScaleProduct.setMajorTickLength(length, this);
    }

    /**
     * Returns the major tick paint.
     *
     * @return The major tick paint (never {@code null}).
     *
     * @see #setMajorTickPaint(Paint)
     */
    public Paint getMajorTickPaint() {
        return this.standardDialScaleProduct.getMajorTickPaint();
    }

    /**
     * Sets the major tick paint and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getMajorTickPaint()
     */
    public void setMajorTickPaint(Paint paint) {
        standardDialScaleProduct.setMajorTickPaint(paint, this);
    }

    /**
     * Returns the stroke used to draw the major tick marks.
     *
     * @return The stroke (never {@code null}).
     *
     * @see #setMajorTickStroke(Stroke)
     */
    public Stroke getMajorTickStroke() {
        return this.standardDialScaleProduct.getMajorTickStroke();
    }

    /**
     * Sets the stroke used to draw the major tick marks and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getMajorTickStroke()
     */
    public void setMajorTickStroke(Stroke stroke) {
        standardDialScaleProduct.setMajorTickStroke(stroke, this);
    }

    /**
     * Returns the number of minor tick marks between major tick marks.
     *
     * @return The number of minor tick marks between major tick marks.
     *
     * @see #setMinorTickCount(int)
     */
    public int getMinorTickCount() {
        return this.standardDialScaleProduct.getMinorTickCount();
    }

    /**
     * Sets the number of minor tick marks between major tick marks and sends
     * a {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param count  the count.
     *
     * @see #getMinorTickCount()
     */
    public void setMinorTickCount(int count) {
        standardDialScaleProduct.setMinorTickCount(count, this);
    }

    /**
     * Returns the length factor for the minor tick marks.  The value is
     * subtracted from the tick radius to determine the inner starting point
     * for the tick marks.
     *
     * @return The length factor.
     *
     * @see #setMinorTickLength(double)
     */
    public double getMinorTickLength() {
        return this.standardDialScaleProduct.getMinorTickLength();
    }

    /**
     * Sets the length factor for the minor tick marks and sends
     * a {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param length  the length.
     *
     * @see #getMinorTickLength()
     */
    public void setMinorTickLength(double length) {
        standardDialScaleProduct.setMinorTickLength(length, this);
    }

    /**
     * Returns the paint used to draw the minor tick marks.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setMinorTickPaint(Paint)
     */
    public Paint getMinorTickPaint() {
        return this.standardDialScaleProduct.getMinorTickPaint();
    }

    /**
     * Sets the paint used to draw the minor tick marks and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getMinorTickPaint()
     */
    public void setMinorTickPaint(Paint paint) {
        standardDialScaleProduct.setMinorTickPaint(paint, this);
    }

    /**
     * Returns the stroke used to draw the minor tick marks.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setMinorTickStroke(Stroke)
     */
    public Stroke getMinorTickStroke() {
        return this.standardDialScaleProduct.getMinorTickStroke();
    }

    /**
     * Sets the stroke used to draw the minor tick marks and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getMinorTickStroke()
     */
    public void setMinorTickStroke(Stroke stroke) {
        standardDialScaleProduct.setMinorTickStroke(stroke, this);
    }

    /**
     * Returns the tick label offset.
     *
     * @return The tick label offset.
     *
     * @see #setTickLabelOffset(double)
     */
    public double getTickLabelOffset() {
        return this.standardDialScaleProduct.getTickLabelOffset();
    }

    /**
     * Sets the tick label offset and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param offset  the offset.
     *
     * @see #getTickLabelOffset()
     */
    public void setTickLabelOffset(double offset) {
        standardDialScaleProduct.setTickLabelOffset(offset, this);
    }

    /**
     * Returns the font used to draw the tick labels.
     *
     * @return The font (never {@code null}).
     *
     * @see #setTickLabelFont(Font)
     */
    public Font getTickLabelFont() {
        return this.standardDialScaleProduct.getTickLabelFont();
    }

    /**
     * Sets the font used to display the tick labels and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getTickLabelFont()
     */
    public void setTickLabelFont(Font font) {
        standardDialScaleProduct.setTickLabelFont(font, this);
    }

    /**
     * Returns the paint used to draw the tick labels.
     *
     * @return The paint ({@code null} not permitted).
     *
     * @see #setTickLabelPaint(Paint)
     */
    public Paint getTickLabelPaint() {
        return this.standardDialScaleProduct.getTickLabelPaint();
    }

    /**
     * Sets the paint used to draw the tick labels and sends a
     * {@link DialLayerChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setTickLabelPaint(Paint paint) {
        standardDialScaleProduct.setTickLabelPaint(paint, this);
    }

    /**
     * Returns {@code true} if the tick labels should be displayed,
     * and {@code false} otherwise.
     *
     * @return A boolean.
     *
     * @see #setTickLabelsVisible(boolean)
     */
    public boolean getTickLabelsVisible() {
        return this.standardDialScaleProduct.getTickLabelsVisible();
    }

    /**
     * Sets the flag that controls whether or not the tick labels are
     * displayed, and sends a {@link DialLayerChangeEvent} to all registered
     * listeners.
     *
     * @param visible  the new flag value.
     *
     * @see #getTickLabelsVisible()
     */
    public void setTickLabelsVisible(boolean visible) {
        standardDialScaleProduct.setTickLabelsVisible(visible, this);
    }

    /**
     * Returns the number formatter used to convert the tick label values to
     * strings.
     *
     * @return The formatter (never {@code null}).
     *
     * @see #setTickLabelFormatter(NumberFormat)
     */
    public NumberFormat getTickLabelFormatter() {
        return this.standardDialScaleProduct2.getTickLabelFormatter();
    }

    /**
     * Sets the number formatter used to convert the tick label values to
     * strings, and sends a {@link DialLayerChangeEvent} to all registered
     * listeners.
     *
     * @param formatter  the formatter ({@code null} not permitted).
     *
     * @see #getTickLabelFormatter()
     */
    public void setTickLabelFormatter(NumberFormat formatter) {
        standardDialScaleProduct2.setTickLabelFormatter(formatter, this);
    }

    /**
     * Returns a flag that controls whether or not the first tick label is
     * visible.
     *
     * @return A boolean.
     *
     * @see #setFirstTickLabelVisible(boolean)
     */
    public boolean getFirstTickLabelVisible() {
        return this.standardDialScaleProduct2.getFirstTickLabelVisible();
    }

    /**
     * Sets a flag that controls whether or not the first tick label is
     * visible, and sends a {@link DialLayerChangeEvent} to all registered
     * listeners.
     *
     * @param visible  the new flag value.
     *
     * @see #getFirstTickLabelVisible()
     */
    public void setFirstTickLabelVisible(boolean visible) {
        standardDialScaleProduct2.setFirstTickLabelVisible(visible, this);
    }

    /**
     * Returns {@code true} to indicate that this layer should be
     * clipped within the dial window.
     *
     * @return {@code true}.
     */
    @Override
    public boolean isClippedToWindow() {
        return true;
    }

    /**
     * Draws the scale on the dial plot.
     *
     * @param g2  the graphics target ({@code null} not permitted).
     * @param plot  the dial plot ({@code null} not permitted).
     * @param frame  the reference frame that is used to construct the
     *     geometry of the plot ({@code null} not permitted).
     * @param view  the visible part of the plot ({@code null} not
     *     permitted).
     */
    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame,
            Rectangle2D view) {

        Rectangle2D arcRect = DialPlot.rectangleByRadius(frame,
                this.standardDialScaleProduct.getTickRadius(), this.standardDialScaleProduct.getTickRadius());
        Rectangle2D arcRectMajor = DialPlot.rectangleByRadius(frame,
                this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getMajorTickLength(),
                this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getMajorTickLength());
        Rectangle2D arcRectMinor = arcRect;
        if (this.standardDialScaleProduct.getMinorTickCount() > 0 && this.standardDialScaleProduct.getMinorTickLength() > 0.0) {
            arcRectMinor = DialPlot.rectangleByRadius(frame,
                    this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getMinorTickLength(),
                    this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getMinorTickLength());
        }
        Rectangle2D arcRectForLabels = DialPlot.rectangleByRadius(frame,
                this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getTickLabelOffset(),
                this.standardDialScaleProduct.getTickRadius() - this.standardDialScaleProduct.getTickLabelOffset());

        boolean firstLabel = true;

        Arc2D arc = new Arc2D.Double();
        Line2D workingLine = new Line2D.Double();
        for (double v = this.standardDialScaleProduct.getLowerBound(); v <= this.standardDialScaleProduct.getUpperBound();
                v += this.standardDialScaleProduct.getMajorTickIncrement()) {
            arc.setArc(arcRect, this.standardDialScaleProduct.getStartAngle(), valueToAngle(v)
                    - this.standardDialScaleProduct.getStartAngle(), Arc2D.OPEN);
            Point2D pt0 = arc.getEndPoint();
            arc.setArc(arcRectMajor, this.standardDialScaleProduct.getStartAngle(), valueToAngle(v)
                    - this.standardDialScaleProduct.getStartAngle(), Arc2D.OPEN);
            Point2D pt1 = arc.getEndPoint();
            g2.setPaint(this.standardDialScaleProduct.getMajorTickPaint());
            g2.setStroke(this.standardDialScaleProduct.getMajorTickStroke());
            workingLine.setLine(pt0, pt1);
            g2.draw(workingLine);
            arc.setArc(arcRectForLabels, this.standardDialScaleProduct.getStartAngle(), valueToAngle(v)
                    - this.standardDialScaleProduct.getStartAngle(), Arc2D.OPEN);
            Point2D pt2 = arc.getEndPoint();

            if (this.standardDialScaleProduct.getTickLabelsVisible()) {
                if (!firstLabel || this.standardDialScaleProduct2.getFirstTickLabelVisible()) {
                    g2.setFont(this.standardDialScaleProduct.getTickLabelFont());
                    g2.setPaint(this.standardDialScaleProduct.getTickLabelPaint());
                    TextUtils.drawAlignedString(
                            this.standardDialScaleProduct2.getTickLabelFormatter().format(v), g2,
                            (float) pt2.getX(), (float) pt2.getY(),
                            TextAnchor.CENTER);
                }
            }
            firstLabel = false;

            // now do the minor tick marks
            if (this.standardDialScaleProduct.getMinorTickCount() > 0 && this.standardDialScaleProduct.getMinorTickLength() > 0.0) {
                double minorTickIncrement = this.standardDialScaleProduct.getMajorTickIncrement()
                        / (this.standardDialScaleProduct.getMinorTickCount() + 1);
                for (int i = 0; i < this.standardDialScaleProduct.getMinorTickCount(); i++) {
                    double vv = v + ((i + 1) * minorTickIncrement);
                    if (vv >= this.standardDialScaleProduct.getUpperBound()) {
                        break;
                    }
                    double angle = valueToAngle(vv);

                    arc.setArc(arcRect, this.standardDialScaleProduct.getStartAngle(), angle
                            - this.standardDialScaleProduct.getStartAngle(), Arc2D.OPEN);
                    pt0 = arc.getEndPoint();
                    arc.setArc(arcRectMinor, this.standardDialScaleProduct.getStartAngle(), angle
                            - this.standardDialScaleProduct.getStartAngle(), Arc2D.OPEN);
                    Point2D pt3 = arc.getEndPoint();
                    g2.setStroke(this.standardDialScaleProduct.getMinorTickStroke());
                    g2.setPaint(this.standardDialScaleProduct.getMinorTickPaint());
                    workingLine.setLine(pt0, pt3);
                    g2.draw(workingLine);
                }
            }

        }
    }

    /**
     * Converts a data value to an angle against this scale.
     *
     * @param value  the data value.
     *
     * @return The angle (in degrees, using the same specification as Java's
     *     Arc2D class).
     *
     * @see #angleToValue(double)
     */
    @Override
    public double valueToAngle(double value) {
        double range = this.standardDialScaleProduct.getUpperBound() - this.standardDialScaleProduct.getLowerBound();
        double unit = this.standardDialScaleProduct.getExtent() / range;
        return this.standardDialScaleProduct.getStartAngle() + unit * (value - this.standardDialScaleProduct.getLowerBound());
    }

    /**
     * Converts the given angle to a data value, based on this scale.
     *
     * @param angle  the angle (in degrees).
     *
     * @return The data value.
     *
     * @see #valueToAngle(double)
     */
    @Override
    public double angleToValue(double angle) {
        double range = this.standardDialScaleProduct.getUpperBound() - this.standardDialScaleProduct.getLowerBound();
        double unit = range / this.standardDialScaleProduct.getExtent();
        return (angle - this.standardDialScaleProduct.getStartAngle()) * unit;
    }

    /**
     * Tests this {@code StandardDialScale} for equality with an arbitrary
     * object.
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
        if (!(obj instanceof StandardDialScale)) {
            return false;
        }
        StandardDialScale that = (StandardDialScale) obj;
        if (this.standardDialScaleProduct.getLowerBound() != that.standardDialScaleProduct.getLowerBound()) {
            return false;
        }
        if (this.standardDialScaleProduct.getUpperBound() != that.standardDialScaleProduct.getUpperBound()) {
            return false;
        }
        if (this.standardDialScaleProduct.getStartAngle() != that.standardDialScaleProduct.getStartAngle()) {
            return false;
        }
        if (this.standardDialScaleProduct.getExtent() != that.standardDialScaleProduct.getExtent()) {
            return false;
        }
        if (this.standardDialScaleProduct.getTickRadius() != that.standardDialScaleProduct.getTickRadius()) {
            return false;
        }
        if (this.standardDialScaleProduct.getMajorTickIncrement() != that.standardDialScaleProduct.getMajorTickIncrement()) {
            return false;
        }
        if (this.standardDialScaleProduct.getMajorTickLength() != that.standardDialScaleProduct.getMajorTickLength()) {
            return false;
        }
        if (!PaintUtils.equal(this.standardDialScaleProduct.getMajorTickPaint(), that.standardDialScaleProduct.getMajorTickPaint())) {
            return false;
        }
        if (!this.standardDialScaleProduct.getMajorTickStroke().equals(that.standardDialScaleProduct.getMajorTickStroke())) {
            return false;
        }
        if (this.standardDialScaleProduct.getMinorTickCount() != that.standardDialScaleProduct.getMinorTickCount()) {
            return false;
        }
        if (this.standardDialScaleProduct.getMinorTickLength() != that.standardDialScaleProduct.getMinorTickLength()) {
            return false;
        }
        if (!PaintUtils.equal(this.standardDialScaleProduct.getMinorTickPaint(), that.standardDialScaleProduct.getMinorTickPaint())) {
            return false;
        }
        if (!this.standardDialScaleProduct.getMinorTickStroke().equals(that.standardDialScaleProduct.getMinorTickStroke())) {
            return false;
        }
        if (this.standardDialScaleProduct.getTickLabelsVisible() != that.standardDialScaleProduct.getTickLabelsVisible()) {
            return false;
        }
        if (this.standardDialScaleProduct.getTickLabelOffset() != that.standardDialScaleProduct.getTickLabelOffset()) {
            return false;
        }
        if (!this.standardDialScaleProduct.getTickLabelFont().equals(that.standardDialScaleProduct.getTickLabelFont())) {
            return false;
        }
        if (!PaintUtils.equal(this.standardDialScaleProduct.getTickLabelPaint(), that.standardDialScaleProduct.getTickLabelPaint())) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;
        // lowerBound
        long temp = Double.doubleToLongBits(this.standardDialScaleProduct.getLowerBound());
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        // upperBound
        temp = Double.doubleToLongBits(this.standardDialScaleProduct.getUpperBound());
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        // startAngle
        temp = Double.doubleToLongBits(this.standardDialScaleProduct.getStartAngle());
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        // extent
        temp = Double.doubleToLongBits(this.standardDialScaleProduct.getExtent());
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        // tickRadius
        temp = Double.doubleToLongBits(this.standardDialScaleProduct.getTickRadius());
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        // majorTickIncrement
        // majorTickLength
        // majorTickPaint
        // majorTickStroke
        // minorTickCount
        // minorTickLength
        // minorTickPaint
        // minorTickStroke
        // tickLabelOffset
        // tickLabelFont
        // tickLabelsVisible
        // tickLabelFormatter
        // firstTickLabelsVisible
        return result;
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if this instance is not cloneable.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        SerialUtils.writePaint(this.standardDialScaleProduct.getMajorTickPaint(), stream);
        SerialUtils.writeStroke(this.standardDialScaleProduct.getMajorTickStroke(), stream);
        SerialUtils.writePaint(this.standardDialScaleProduct.getMinorTickPaint(), stream);
        SerialUtils.writeStroke(this.standardDialScaleProduct.getMinorTickStroke(), stream);
        SerialUtils.writePaint(this.standardDialScaleProduct.getTickLabelPaint(), stream);
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
        stream.defaultReadObject();
        standardDialScaleProduct.setMajorTickPaint2(SerialUtils.readPaint(stream));
        standardDialScaleProduct.setMajorTickStroke2(SerialUtils.readStroke(stream));
        standardDialScaleProduct.setMinorTickPaint2(SerialUtils.readPaint(stream));
        standardDialScaleProduct.setMinorTickStroke2(SerialUtils.readStroke(stream));
        standardDialScaleProduct.setTickLabelPaint2(SerialUtils.readPaint(stream));
    }

}
