package game;


public class MidWindow extends Main {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
	}
	public MidWindow(){
		
	}
	public int[] Location(){
		int [] LocSize = new int [4];
		Main main = new Main() ;
		
		LocSize[0] = main.getHeight()/3;
		LocSize[1] = main.getHeight()/3;
				
		LocSize[3] = main.getWidth()/2 - LocSize[3] ; 
		LocSize[4] = main.getHeight()/2 - LocSize[4] ;
		
		 
		return LocSize ;
		
				
	}
	
	 
	
}