package oop_Bauernhof;

public class KeinPlatzException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	KeinPlatzException(){
		super();
	}
	KeinPlatzException(String message){
		super(message);
	}
}
