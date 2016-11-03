
import org.jgrapht.graph.*;

public class EdgeEx {
	
private String Vs;
private String Vt;

public void display(){
	System.out.print("("+Vs+" : "+Vt+")");
}

public EdgeEx(String Vsource,String Vtarget){
	Vs=Vsource;
	Vt=Vtarget;
}

public EdgeEx(DefaultEdge e,MultigraphExt g){
	Vs=g.getEdgeSource(e);
	Vt=g.getEdgeTarget(e);
}



public String  getSource(){
	return Vs;
}

public String  getTarget(){
	return Vt;
}

public boolean equals(EdgeEx e){
boolean res=false;
if((e.getSource().equals(Vs)&&e.getTarget().equals(Vt))||(e.getSource().equals(Vt)&&e.getTarget().equals(Vs))){
	res=true;	
}
return res;
}
}
