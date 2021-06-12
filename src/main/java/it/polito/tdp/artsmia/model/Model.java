package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap; 
	private Graph<ArtObject, DefaultWeightedEdge> sottoGrafo;
	
	public Map<Integer, ArtObject> getIdMap() {
		return idMap;
	}

	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Adiacenza a: dao.getAdiacenze()) {
				Graphs.addEdge(grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
		}
		
		/*System.out.println("GRAFO CREATO!\n"
				+ "\t#VERTICI: "+grafo.vertexSet().size()+"\n"
				+ "\t#ARCHI: "+grafo.edgeSet().size());*/
	}
	
	public void componenteConnessa(int id){
		
		sottoGrafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		sottoGrafo.addVertex(idMap.get(id));
		
		BreadthFirstIterator<ArtObject, DefaultWeightedEdge> it = new BreadthFirstIterator<>(grafo, idMap.get(id));
		
		it.addTraversalListener(new TraversalListener<ArtObject, DefaultWeightedEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<ArtObject> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<ArtObject> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
				// TODO Auto-generated method stub
				DefaultWeightedEdge edge = e.getEdge();
				ArtObject source = grafo.getEdgeSource(edge);
				ArtObject target = grafo.getEdgeTarget(edge);
				
				sottoGrafo.addVertex(source);
				sottoGrafo.addVertex(target);

				Graphs.addEdge(sottoGrafo, source, target, grafo.getEdgeWeight(edge));
				
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		while(it.hasNext())
			it.next();
		
		/*System.out.println("\nSOTTOGRAFO CREATO!\n"
				+ "\t#VERTICI: "+sottoGrafo.vertexSet().size()+"\n"
				+ "\t#ARCHI: "+sottoGrafo.edgeSet().size());*/		
		
		
	}

	public Graph<ArtObject, DefaultWeightedEdge> getSottoGrafo() {
		return sottoGrafo;
	}

}
