
import org.jgrapht.graph.*;
public class DecompTree {
	
private SimpleGraph<String,DefaultEdge> sg;
private int noofVertices;
public DecompTree(){
	sg=new SimpleGraph<String,DefaultEdge>(DefaultEdge.class);
	sg.addVertex("1");
	noofVertices=1;
}
/*public VertexPair fork(String root){
	String V1,V2;
	if(noofVertices==1){
		V1="1";
		V2="2";
		sg.addVertex(V2);
		sg.addEdge(V1,V2);
		noofVertices+=1;
		
	}else{
		V1=Integer.toString(noofVertices+1);
		V2=Integer.toString(noofVertices+2);
		sg.addVertex(V1);
		sg.addVertex(V2);
		sg.addEdge(root,V1);
		sg.addEdge(root,V2);
		noofVertices+=1;
	}	
	return new VertexPair(V1,V2);
}*/

public VertexPair fork(String root){
String V;
V=Integer.toString(noofVertices+1);
	System.out.println("FORK");
	System.out.println("< "+root+" "+V+">");
	sg.addVertex(V);
	sg.addEdge(root,V);
	noofVertices+=1;



return new VertexPair(root,V);


}

public SimpleGraph<String,DefaultEdge> getGraph(){

	return sg;
}

}
