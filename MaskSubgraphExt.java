import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.graph.MaskSubgraph;

public class MaskSubgraphExt extends MaskSubgraph<String,DefaultEdge> {

	public MaskSubgraphExt(Graph<String,DefaultEdge> base, MaskFunctor<String,DefaultEdge> mask) {
		super(base, mask);
	}

	public MultigraphExt toGraph(){
		MultigraphExt g;
		g= new MultigraphExt();
		Set<DefaultEdge> edges=this.edgeSet();
		Set<String> vert=this.vertexSet();
		for(String v:vert){
			g.addVertex(v);
			}
		for(DefaultEdge e:edges){
		g.addEdge(this.getEdgeSource(e),this.getEdgeTarget(e));
		}
		
		return g;
	}


}
