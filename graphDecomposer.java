
public class graphDecomposer {

	
	public static void main(String[] args){
		
	MultigraphExt g;
		
	g= new MultigraphExt();
	
	//loadInput(args[0],g);
	IOData.loadInput("input.txt",g);
	try{
	Decomposer.checkGraphCorrectness(g);
	Decomposer.manager(g,true);
	}catch(SomeException e){
		System.out.println(e);
	}
		
	}	
	
	
}
