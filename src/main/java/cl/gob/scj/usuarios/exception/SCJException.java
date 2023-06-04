package cl.gob.scj.usuarios.exception;

public class SCJException extends Exception {

    public SCJException(String message, Throwable cause) {
		super(message, cause);	
	}

	public SCJException(String message) {
		super(message);		
	}

	public SCJException(Throwable cause) {
		super(cause);	
	}	
    
}