package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChiffreRomainException extends RuntimeException {
	/**
	 * Id Serializable.
	 */
	private static final long serialVersionUID = -255354334768672945L;
	Logger log = LoggerFactory.getLogger(ChiffreRomainException.class);
	
	public ChiffreRomainException() {
		super();
	}

	public ChiffreRomainException(String message) {
		super(message);
		log.error(message);
	}

}
