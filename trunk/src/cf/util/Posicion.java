package cf.util;

public class Posicion {
	
	private int ejeX;
	private int ejeY;
	
	public Posicion(int ejeX,int ejeY){
		
		this.ejeX = ejeX;
		this.ejeY = ejeY;
	}
	
	public int getEjeX(){
		return ejeX;
	}
	
	public int getEjeY(){
		return ejeY;
	}
	
	public boolean equals(Posicion pos){
		
		return (ejeX == pos.getEjeX() && ejeY == pos.getEjeY());
		
	}
}
