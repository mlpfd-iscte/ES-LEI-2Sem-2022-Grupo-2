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
 * --------------------
 * ChartMouseEvent.java
 * --------------------
 * (C) Copyright 2002-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Alex Weber;
 *
 */

package org.jfree.chart.swing;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.entity.ChartEntity;

/**
 * A mouse event for a chart that is displayed in a {@link ChartPanel}.
 *
 * @see ChartMouseListener
 */
public class ChartMouseEvent extends EventObject implements Serializable {
    
    /** For serialization. */
    private static final long serialVersionUID = -682393837314562149L;

    /** The chart that the mouse event relates to. */
    private final JFreeChart chart;

    /** The Java mouse event that triggered this event. */
    private final MouseEvent trigger;

    /** The chart entity (if any). */
    private final ChartEntity entity;

    /**
     * Constructs a new event.
     *
     * @param chart  the source chart ({@code null} not permitted).
     * @param trigger  the mouse event that triggered this event
     *                 ({@code null} not permitted).
     * @param entity  the chart entity (if any) under the mouse point
     *                ({@code null} permitted).
     */
    public ChartMouseEvent(JFreeChart chart, MouseEvent trigger,
                           ChartEntity entity) {
        super(chart);
        this.chart = chart;
        this.trigger = trigger;
        this.entity = entity;
    }
    
    public static void checkChart(ChartPanel cp, MouseEvent e, ChartEntity entity, Object[] listeners) {
    	if (cp.chart != null) {
            ChartMouseEvent event = new ChartMouseEvent(cp.getChart(), e, entity);
            for (int i = listeners.length - 1; i >= 0; i -= 1) {
                ((ChartMouseListener) listeners[i]).chartMouseMoved(event);
            }
        }
    }
    
}
