package org.middleheaven.graph;

import java.util.Collection;
import java.util.LinkedList;

import org.middleheaven.graph.Graph.Edge;
import org.middleheaven.graph.Graph.Vertex;
import org.middleheaven.graph.VertextInfoManager.VertexInfo;

/**
 * Transverses a graph in topologic order, meaning from the vertex with less edges to the one with more edges.
 * 
 */
public class TopologicOrderTransversor extends AbstractGraphTransversor {

	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @param graph the graph to transverse.
	 * @param startVertex not used. you can pass <code>null</code>.
	 */
	@Override
	public <E, V> void transverse(Graph<E, V> graph, V startVertex) {
		Collection<Vertex<V, E>> all = graph.getVertices();
		
		VertextInfoManager manager = new VertextInfoManager();
		
		if (!all.isEmpty()){

		
			LinkedList<Vertex<V, E>> q = new LinkedList<Vertex<V, E>>();

			// compute ingree

			for (Vertex<V, E> v: all ){
				for (Edge<V, E> e : v.getOutjacentEdges()){
					manager.info(e.getTargetVertex()).scratch++;
				}
			}
			
			// enqueue those with ingree zero
			for (Vertex<V, E> v : all){
				if (manager.info(v).scratch == 0){
					q.add(v);
				}
			}

			GraphTranverseListener broadcastEvent = this.getListenerSet().broadcastEvent();
			
			int iterations;
			for (iterations = 0; !q.isEmpty(); iterations++ ){
				
				Vertex<V, E> v = q.remove();
				
				broadcastEvent.beginVertex(new VertexTraversalEvent<V, E>(v));
			
				for (Edge<V, E> e : v.getOutjacentEdges() ){
					
					broadcastEvent.beginEdgeTraversed(new EdgeTraversalEvent(e));
					
					Vertex<V, E> w = e.getTargetVertex();
					double cvw = e.getCost();
					
					VertexInfo infoW = manager.info(w);
					
					if ( --infoW.scratch == 0 ) {
						q.add(w);
					}
						
					VertexInfo infoV = manager.info(v);
					
					broadcastEvent.endEdgeTraversed(new EdgeTraversalEvent(e));
					
					if (Double.compare(infoV.dist, Double.MAX_VALUE) == 0){
						continue;
					}
					if (Double.compare(infoW.dist, infoV.dist + cvw) > 0){
						infoW.dist = infoV.dist + cvw;
						infoW.prev = v;
						infoW.connectingEdge = e;
					}
				}
				
				broadcastEvent.endVertex(new VertexTraversalEvent(v));
			}
			
		
			if (iterations != all.size()){
				throw new IllegalStateException("Graph as a cycle");
			}

	
		}
		
		
		
	}


}
