
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Decomposer {
	
	public static void manager(MultigraphExt gg,Boolean rand){
		DecompTree tree;
		if(rand){
			try {
				tree=randomLookup(gg);
				System.out.println(tree.getGraph().edgeSet());
			} catch (SomeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
			
		}
		
	}
	
	private static void decomposeRand(List<EdgeExPair> splittingPairs,MultigraphExt gg,GraphMask mask,DecompTree tree,String root,RandomPairGen rpg) throws SomeException{
	EdgeExPair splittingPair;	
	System.out.println("Splitting pairs:");
	for(EdgeExPair ep:splittingPairs){
		ep.getE1().display();
		System.out.print(" ");
		ep.getE2().display();
		System.out.println(" ");
	}
	System.out.println("DECOMPOSE:");
	if(splittingPairs.size()==0){
		System.out.println("END:");
		return;
	}else{
	
	int range=splittingPairs.size()-1;
	int k=rpg.getRandPair(range);
	splittingPair=splittingPairs.get(k);
	System.out.println("Range: "+range+" ,got: "+k);
	
	
	MaskSubgraphExt initGraph=new MaskSubgraphExt(gg, mask);
	System.out.println("Input:");
	initGraph.toGraph().display();
	System.out.println("ROOT:");
	System.out.println(root);
	//System.out.println("E3"+splittingPairs.get(3).getE1()+" "+splittingPairs.get(3).getE2());
	mask.maskEdgePair(splittingPair);
	MaskSubgraphExt subGraph=new MaskSubgraphExt(gg, mask);
	System.out.print("Split: ");
	splittingPair.getE1().display();
	System.out.print(" ");
	splittingPair.getE2().display();
	System.out.println(" ");
	System.out.println("After split: ");
	subGraph.toGraph().display();
	
	/*
	if(!gg.containsEdge(splittingPairs.get(3).getE1())){
		System.out.println("E1 masked");	
	}else{
		System.out.println("E1 not masked");	
	}
	
	if(!gg.containsEdge(splittingPairs.get(3).getE2())){
		System.out.println("E2 masked");	
	}else{
		System.out.println("E2 not masked");	
	}*/
	ConnectivityInspector<String,DefaultEdge> ci=new ConnectivityInspector<String,DefaultEdge>(subGraph.toGraph());
	
	/*
	if(ci.isGraphConnected()){
	System.out.println("Is connected!");	
	}*/
	List<Set<String>> subGraphs=ci.connectedSets();
	
	if(subGraphs.size()!=2){
		throw new SomeException("Splitting error!");
	}
	System.out.println("Sets:");
	System.out.println(subGraphs.get(0));
	System.out.println(subGraphs.get(1));
	
	VertexPair newRoots=tree.fork(root);
	
	//finding splitting Vertices
	String V1s,V1t,V2s,V2t;
	V1s=splittingPair.getE1().getSource();
	V1t=splittingPair.getE1().getTarget();
	V2s=splittingPair.getE2().getSource();
	V2t=splittingPair.getE2().getTarget();
    
    String sourceVertex1;
    String targetVertex1;
    String sourceVertex2;
    String targetVertex2;
	//fix by closing a loop
	if(subGraphs.get(0).contains(V1s)){
		if(subGraphs.get(0).contains(V2s)){
			sourceVertex1=V1s;
			targetVertex1=V2s;
			sourceVertex2=V1t;
			targetVertex2=V2t;				
		}else{
			sourceVertex1=V1s;
			targetVertex1=V2t;
			sourceVertex2=V1t;
			targetVertex2=V2s;
		}
	}else{
		if(subGraphs.get(0).contains(V2s)){
			sourceVertex1=V1t;
			targetVertex1=V2s;
			sourceVertex2=V1s;
			targetVertex2=V2t;
		}else{
			sourceVertex1=V1t;
			targetVertex1=V2t;
			sourceVertex2=V1s;
			targetVertex2=V2s;
		}
		
	}
	EdgeEx e1=null;
    EdgeEx e2=null;
    //System.out.println("Edges added to gg:");
	if(gg.addEdgeNoLoops(sourceVertex1,targetVertex1)){
		e1=new EdgeEx(sourceVertex1,targetVertex1);
		//e1.display();
		//System.out.println(" ");
	}
	if(gg.addEdgeNoLoops(sourceVertex2,targetVertex2)){
		e2=new EdgeEx(sourceVertex2,targetVertex2);
		//e2.display();
		//System.out.println(" ");
	}
	
	/*
	Set<DefaultEdge> edges1 = new HashSet<DefaultEdge>();
	for(String V:subGraphs.get(0)){
	Set<DefaultEdge> edgesofV=subGraph.edgesOf(V);
	edges1.addAll(edgesofV);
	edges1.add(e1);
	}
		
	Set<DefaultEdge> edges2 = new HashSet<DefaultEdge>();
	for(String V:subGraphs.get(1)){
	Set<DefaultEdge> edgesofV=subGraph.edgesOf(V);
	edges2.addAll(edgesofV);
	edges2.add(e2);
	}*/
	

			
	//preparing new subGraphs
	GraphMask mask1=new GraphMask(mask);
	mask1.maskVertexSetWithAdjEdges(subGraphs.get(0));
	mask1.maskEdgeNoLoops(e1);
	GraphMask mask2=new GraphMask(mask);
	mask2.maskVertexSetWithAdjEdges(subGraphs.get(1));
	mask2.maskEdgeNoLoops(e2);
	MaskSubgraphExt subGraph1=new MaskSubgraphExt(gg, mask1);
	MaskSubgraphExt subGraph2=new MaskSubgraphExt(gg, mask2);
	
	System.out.println("SubGraph1:");
	subGraph1.toGraph().display();
	
	System.out.println("SubGraph2:");
	subGraph2.toGraph().display();
	
	//build new splitting pairs
	List<EdgeExPair> newSplittingPairs1=new ArrayList<EdgeExPair>();
	List<EdgeExPair> newSplittingPairs2=new ArrayList<EdgeExPair>();
	//TODO: not sure!
	for(EdgeExPair pair:splittingPairs){
		if(!pair.intersects(splittingPair)){
			/*
			System.out.println(" ");
			System.out.println("Valid:");
			pair.getE1().display();
			System.out.print(" ");
			pair.getE2().display();
			System.out.println(" ");
			splittingPair.getE1().display();
			System.out.print(" ");
			splittingPair.getE2().display();*/
			if(subGraph1.containsEdge(pair.getE1().getSource(),pair.getE1().getTarget())&&subGraph1.containsEdge(pair.getE2().getSource(),pair.getE2().getTarget())){
				newSplittingPairs1.add(pair);
			}else{
				newSplittingPairs2.add(pair);
			}
			//TODO: error handling
		}
		
	}
	System.out.println("Splitting Pairs1:");
	
	for(EdgeExPair ep:newSplittingPairs1){
		ep.getE1().display();
		System.out.print(" ");
		ep.getE2().display();
		System.out.println(" ");
	}
	
	System.out.println("Splitting Pairs2:");
	for(EdgeExPair ep:newSplittingPairs2){
		ep.getE1().display();
		System.out.print(" ");
		ep.getE2().display();
		System.out.println(" ");
	}
	String root1,root2;
	decomposeRand(newSplittingPairs1,gg,mask1,tree,newRoots.getV1(),rpg);
	decomposeRand(newSplittingPairs2,gg,mask2,tree,newRoots.getV2(),rpg);
	}
}
	/*
	private static boolean areAdjacent(DefaultEdge e1,DefaultEdge e2,MultigraphExt gg){
		boolean res=false;
		if(gg.getEdgeSource(e1).equals(gg.getEdgeSource(e2))||gg.getEdgeSource(e1).equals(gg.getEdgeTarget(e2))||gg.getEdgeTarget(e1).equals(gg.getEdgeSource(e2))||gg.getEdgeTarget(e1).equals(gg.getEdgeTarget(e2))){
		res=true;
		}
		
		return res;
	}*/
	
	    static void checkGraphCorrectness(MultigraphExt gg) throws SomeException{
		//boolean res=false;
		ConnectivityInspector<String, DefaultEdge> ci;
		EdgeEx eex;
		ci=new ConnectivityInspector<String, DefaultEdge>(gg);
		if(ci.isGraphConnected()){
		List<DefaultEdge> es = new ArrayList<DefaultEdge>(gg.edgeSet());
		for(DefaultEdge e:es){
		eex=new EdgeEx(e,gg);
		gg.removeEdge(eex.getSource(),eex.getTarget());
		ci=new ConnectivityInspector<String, DefaultEdge>(gg);
		if(!ci.isGraphConnected()){
			gg.addEdge(eex.getSource(),eex.getTarget());
			throw new SomeException("ERROR: Wrong graph structure!");
		}
		gg.addEdge(eex.getSource(),eex.getTarget());
			
		
		}
		}
		return;
	}
	    
	static DecompTree randomLookup(MultigraphExt gg)throws SomeException{

		DecompTree tree=new DecompTree();
		GraphMask mask=new GraphMask(gg);
		RandomPairGen rpg=new RandomPairGen();
		decomposeRand(fildSplittingPairs(gg),gg,mask,tree,"1",rpg);
		
		//System.out.println("Lookup ended");
		//System.out.println("Decomposition tree:");
		//System.out.println(tree.getGraph().edgeSet());
		return tree;
	}
	
	static List<EdgeExPair>  fildSplittingPairs(MultigraphExt gg) throws SomeException{
	List<EdgeExPair> splittingPairs=new ArrayList<EdgeExPair>();
	List<DefaultEdge> allEdges = new ArrayList<DefaultEdge>(gg.edgeSet());
	
	
	ConnectivityInspector<String, DefaultEdge> ci;
 
	//MultigraphExt<String,DefaultEdge> gCopy=new MultigraphExt<String,DefaultEdge>(gg);
	int noofEdges=allEdges.size();
	for(int i=0;i<noofEdges-1;i++){
		for(int j=i+1;j<noofEdges;j++){
			DefaultEdge e1=allEdges.get(i);
			DefaultEdge e2=allEdges.get(j);
			
			gg.removeEdgePair(new EdgeExPair(new EdgeEx(e1,gg),new EdgeEx(e2,gg)));
			ci=new ConnectivityInspector<String, DefaultEdge>(gg);
			if(!ci.isGraphConnected()){
			splittingPairs.add(new EdgeExPair(new EdgeEx(e1,gg),new EdgeEx(e2,gg)));	
			}
			gg.addEdgeSmart(e1);
			gg.addEdgeSmart(e2);
		}
	}
	
	return splittingPairs;
			
	}

	

}
