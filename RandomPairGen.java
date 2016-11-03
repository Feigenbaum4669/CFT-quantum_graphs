import java.sql.Date;
import java.util.Random;

public class RandomPairGen {
    Random randnum;

    public RandomPairGen() {
        randnum = new Random();
        randnum.setSeed(System.currentTimeMillis());
    }

    public int getRandPair(int i){
    	int res;
    	if(i==0){
    	res=0;
    	}else{
    		res=randnum.nextInt(i+1);
    	}
 
        return res;
    }
}
