/**
 * 
 */
package org.middleheaven.graph;

/**
 * 
 */
public class CycleFoundException extends GraphException {


	private static final long serialVersionUID = -2809107183322751179L;

	
	public CycleFoundException (){
		super("Cycle found in graph");
	}
}
