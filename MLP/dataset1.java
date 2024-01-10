import java.util.Random;
import java.io.FileWriter; 
import java.io.IOException; 

class dataset1{
	private static int size=8000;
	private static float x1[]=new float[size];
	private static float x2[]=new float[size];
	private static int categories[]=new int[size];
	
	static Random r = new Random();

	public static void createRandomPoints(){
		for(int i=0;i<size;i++){
			x1[i]=r.nextFloat() * (2)-1;
			x2[i]=r.nextFloat() * (2)-1;
			categories[i]=categorize(x1[i],x2[i]);
		}	
	}

	public static int categorize(float x1,float x2){
        int flag = 0;
        //1
		if ((Math.pow(x1-0.5,2)+Math.pow(x2-0.5,2))<0.2 && (x1>0.5)){
			flag = 1;
            return 1;
		}//2
        if ((Math.pow(x1-0.5,2)+Math.pow(x2-0.5,2))<0.2 && (x1<0.5)){
			flag = 1;
            return 2;
		}//3
        if ((Math.pow(x1+0.5,2)+Math.pow(x2+0.5,2))<0.2 && (x1>-0.5)){
			flag = 1;
            return 1;
		}//4
        if ((Math.pow(x1+0.5,2)+Math.pow(x2+0.5,2))<0.2 && (x1<-0.5)){
			flag = 1;
            return 2;
		}//5
        if ((Math.pow(x1-0.5,2)+Math.pow(x2+0.5,2))<0.2 && (x1>0.5) ){
			flag = 1;
            return 1;
		}//6
        if ((Math.pow(x1-0.5,2)+Math.pow(x2+0.5,2))<0.2 && (x1<0.5) ){
			flag = 1;
            return 2;
		}//7
        if ((Math.pow(x1+0.5,2)+Math.pow(x2-0.5,2))<0.2 && (x1>-0.5) ){
			flag = 1;
            return 1;
		}//8
        if ((Math.pow(x1+0.5,2)+Math.pow(x2-0.5,2))<0.2 && (x1<-0.5) ){
			flag = 1;
            return 2;
		}//9
        if (flag==0 && (x1>0) ){
			return 3;
		}//10
        if (flag==0 && (x1<0) ){
			return 4;
		}
        return 0;
	}
	public static void writeToFile(){
		try {
      		FileWriter trainingWriter = new FileWriter("training_set.csv");
      		FileWriter testWriter = new FileWriter("test_set.csv");
			for(int i=0;i<size;i++){
				// float choice=r.nextFloat();
				if(i < size/2){
					trainingWriter.write(x1[i]+","+x2[i]+","+categories[i]+"\n");
				}
				else{
					testWriter.write(x1[i]+","+x2[i]+","+categories[i]+"\n");	
				}
			}
			trainingWriter.close();
			testWriter.close();
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}		
	}
	public static void main(String[] args) {
		createRandomPoints();
		writeToFile();
	}
}



