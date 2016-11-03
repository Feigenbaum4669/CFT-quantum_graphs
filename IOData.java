import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOData {

public static void loadInput(String fileName,MultigraphExt gg){
		
		try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+fileName)))
		{

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] splittedLine = sCurrentLine.split(" ");
				if(splittedLine.length!=2){
					throw new SomeException("ERROR: Invalid data format!");
				}
				//Integer[] intarray=new Integer[2];
			    //intarray[0]=Integer.parseInt(splittedLine[0]);
			    //intarray[1]=Integer.parseInt(splittedLine[1]);
				
				String v1=splittedLine[0];
				String v2=splittedLine[1];
			    gg.addEdgeSmart(v1,v2);      
			    
			}

		} catch (IOException | SomeException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		System.out.println("Data loaded successfully");
		
	}
}
