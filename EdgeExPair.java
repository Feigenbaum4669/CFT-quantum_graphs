
public class EdgeExPair {
	
private EdgeEx e1;
private EdgeEx e2;

public EdgeExPair (EdgeEx edge1,EdgeEx edge2){
	e1=edge1;
	e2=edge2;
	
}

public EdgeEx getE1(){
return e1;
}

public EdgeEx getE2(){
return e2;	
}

public boolean intersects(EdgeExPair ep){
boolean res=false;
if(ep.getE1().equals(e1)||ep.getE2().equals(e2)||ep.getE1().equals(e2)||ep.getE2().equals(e1)){
res=true;
}
return res;
}

}



