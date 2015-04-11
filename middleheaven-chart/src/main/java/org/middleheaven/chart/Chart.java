package org.middleheaven.chart;

import java.io.Serializable;

/**
 * Represents a chart in a technology non depended form.
 * The real chart object generated by the underlying <code>ChartEngine</code>
 * is obtained from this object using <code>getChartObject()</code>
 * 
 * @author Sergio Taborda
 *
 */
public class Chart {

	private String title;
    private Serializable reportObject;
    
    
	public Chart(String title, Serializable reportObject) {
		this.title = title;
		this.reportObject = reportObject;
	}
	
	public String getTitle() {
		return title;
	}
	public Serializable getChartObject() {
		return reportObject;
	}
    
    
	
}