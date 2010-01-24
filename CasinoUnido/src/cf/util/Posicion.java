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

        public void setEjeX(int ejeX){
            this.ejeX = ejeX;
        }

        public void setEjeY(int ejeY){
            this.ejeY = ejeY;
        }

	public boolean equals(Posicion pos){
		
		return (ejeX == pos.getEjeX() && ejeY == pos.getEjeY());
		
	}

    @Override
        public String toString(){

                return "El eje X es " + ejeX + " y el eje Y es " + ejeY;
        }
}
