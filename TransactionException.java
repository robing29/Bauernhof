package oop_Bauernhof;

public class TransactionException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TransactionException(){
		super();
	}
	TransactionException(String message){
		super(message);
	}
}
