package app;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import metier.ChiffresRomains;

public class App {
	static final Logger LOGGER = LoggerFactory.getLogger(ChiffresRomains.class);
	public static void main(String[] args){
		try {
		    if (args.length == 1) {
		    	Map<?, ?> IntToRoman=ChiffresRomains.lireFichier(args[0]);
		    	LOGGER.info("Nom du fichier en entree {} ", args[0]);
		    } else {
			throw new Exception("From file expected");
		    }
		    System.exit(0);
		} catch (Exception e) {
			LOGGER.info("{}", e.getMessage());
		    System.exit(1);
		}
	}
}