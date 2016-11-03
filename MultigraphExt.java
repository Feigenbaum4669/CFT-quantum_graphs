
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

public class MultigraphExt extends Multigraph<String,DefaultEdge> {
	
	private static final long serialVersionUID = 1L;

	public MultigraphExt() {
		super(DefaultEdge.class);
	}
	
	public void addEdgeSmart(String sourceVertex, String targetVertex) {
	    if (!this.containsVertex(sourceVertex)) {
	        this.addVertex(sourceVertex);
	    }
	    if (!this.containsVertex(targetVertex)) {
	        this.addVertex(targetVertex);
	    }
	    
	    this.addEdge(sourceVertex, targetVertex);
	    
	}
	
	public void addEdgeSmart(DefaultEdge e) {
		String sourceVertex=this.getEdgeSource(e);
		String targetVertex=this.getEdgeTarget(e);
	    if (!this.containsVertex(sourceVertex)) {
	        this.addVertex(sourceVertex);
	    }
	    if (!this.containsVertex(targetVertex)) {
	        this.addVertex(targetVertex);
	    }
	    
	    this.addEdge(sourceVertex, targetVertex);
	    
	}
	
	public boolean addEdgeNoLoops(String sourceVertex, String targetVertex){
		boolean res=false;
		if(!sourceVertex.equals(targetVertex)){
		this.addEdge(sourceVertex, targetVertex);
		res=true;
		}
		return res;
	}
	
	public void removeEdgePair(EdgeExPair  ep) {
		this.removeEdge(ep.getE1().getSource(),ep.getE1().getTarget());
		this.removeEdge(ep.getE2().getSource(),ep.getE2().getTarget());    
	}
	
	public void display(){
		System.out.print("<");
		for(DefaultEdge e:this.edgeSet()){
			System.out.print(e+" ");
		}
		System.out.println(">");
	}
	
	public String getSource(){
		return getSource();
	}
	
	public String getTarget(){
		return getTarget();
	}
	
	
	

}
