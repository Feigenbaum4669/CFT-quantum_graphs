import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.graph.*;

public class EdgeExSet implements Iterable<EdgeEx>  {
	
	private Set<EdgeEx> edgeSet;
	
	public EdgeExSet(){
		edgeSet=new HashSet<EdgeEx>(); 
	}
	
	public EdgeExSet(Set<DefaultEdge> des,MultigraphExt graph){
		for(DefaultEdge de:des){
			//System.out.println(de);
			edgeSet.add(new EdgeEx(de,graph));
		}
	}
	
	public void add(EdgeEx e){
		edgeSet.add(e);
	}
	
	public boolean contains(EdgeEx em){
		boolean res=false;
		
		for(EdgeEx e:edgeSet){
			if((e.getSource().equals(em.getSource())&&e.getTarget().equals(em.getTarget()))||(e.getSource().equals(em.getTarget())&&e.getTarget().equals(em.getSource()))){
				res=true;
				break;
			}
		}
		
		return res;
	}

	@Override
    public Iterator<EdgeEx> iterator() {
        return edgeSet.iterator();
    }
	
	public int size(){
		return edgeSet.size();
	}

}
