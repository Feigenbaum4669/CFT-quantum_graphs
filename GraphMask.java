
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.graph.*;

public class GraphMask implements MaskFunctor<String,DefaultEdge> {

	private Set<String> maskedVertices;
	private EdgeExSet maskedEdges;
	private MultigraphExt graph;
	
	public GraphMask(MultigraphExt g){
		maskedVertices=new HashSet<String>(); 
		maskedEdges=new EdgeExSet();
		graph=g;
	}
	

	public GraphMask(GraphMask source){
		maskedVertices=new HashSet<String>(); 
		this.maskVertexSet(source.getMaskedVertices());
		maskedEdges=new EdgeExSet();
		this.maskEdgeSet(source.getMaskedEdges());
		graph=(MultigraphExt) source.graph.clone();
	}
	
	
	@Override
	public boolean isEdgeMasked(DefaultEdge edge) {
		boolean res=false;
		if(maskedEdges.contains(new EdgeEx(edge,graph))){
			res=true;
		}
		return res;
	}

	@Override
	public boolean isVertexMasked(String vertex) {
		boolean res=false;
		if(maskedVertices.contains(vertex)){
			res=true;
		}
		return res;
	}
	/*
	public void maskEdgeWithAdjVert(Multigraph<String,DefaultEdge> gg,DefaultEdge e){
		DefaultEdge se=new DefaultEdge(e,gg);
		String V1=se.getSource();
		String V2=se.getTarget();
		if(!maskedVertices.contains(V1)){
			maskedVertices.add(V1);
		}
		if(!maskedVertices.contains(V2)){
			maskedVertices.add(V2);
		}
		
		if(!maskedEdges.contains(e)){
			maskedEdges.add(e);
		}
	}*/
	
	public void setGraph(MultigraphExt g){
		graph=g;
	}
	
	public void maskEdge(EdgeEx e){
		if(!maskedEdges.contains(e)){
			//System.out.println("adding edge!");
			maskedEdges.add(e);
		}
	}
	
	public boolean maskEdgeNoLoops(EdgeEx e){
		boolean res=false;
		if((e!=null)&&(!maskedEdges.contains(e))&&(!e.getSource().equals(e.getTarget()))){
			//System.out.println("adding edge!");
			maskedEdges.add(e);
			res=true;
		}
		return res;
	}
	
	public void maskEdgePair(EdgeExPair ep){
		maskEdge(ep.getE1());
		maskEdge(ep.getE2());
	}
	/*
	public void maskEdgePairWithAdjVert(Multigraph<String,DefaultEdge> gg,DefaultEdgePair ep){
		maskEdgeWithAdjVert(gg,ep.getE1());
		maskEdgeWithAdjVert(gg,ep.getE2());
	}*/
	
	public void maskEdgeSet(EdgeExSet es){
		for(EdgeEx e:es){
			maskEdge(e);
		}
	}
	/*
	public void maskEdgeSetWithAdjVert(Multigraph<String,DefaultEdge> gg,Set<DefaultEdge> es){
		for(DefaultEdge e:es){
			maskEdgeWithAdjVert(gg,e);
		}
	}*/
	
	private void maskVertex(String v){
		if(!maskedVertices.contains(v)){
			maskedVertices.add(v);
		}
	}
	
	private void maskVertexSet(Set<String> vs){
		for(String v:vs){
			maskVertex(v);
		}
	}
	
	public void maskVertexWithAdjEdges(String v){
		maskVertex(v);
		Set<DefaultEdge> es=graph.edgeSet();
		for(DefaultEdge e:es){
			EdgeEx ex=new EdgeEx(e,graph);
			if(ex.getSource().equals(v)||ex.getTarget().equals(v)){
				maskedEdges.add(ex);
			}
		}
	}
	
	
	public void maskVertexSetWithAdjEdges(Set<String> vs){
		for(String v:vs){
			maskVertexWithAdjEdges(v);
		}
	}
	
	public EdgeExSet getMaskedEdges(){
		return maskedEdges;
	}
	
	public Set<String> getMaskedVertices(){
		return maskedVertices;
	}
	/*
	private boolean containsEdge(DefaultEdge e){
	boolean res=false;
	String V1=e.getSource();
	String V2=e.getTarget();
	for(SuperEdge ed:maskedEdges){
		if((ed.getSource().equals(V1)&&ed.getTarget().equals(V2))||(ed.getSource().equals(V2)&&ed.getTarget().equals(V1))){
			res=true;
			break;
		}
	}
	return res;
	}*/

}
