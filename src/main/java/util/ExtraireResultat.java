package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraireResultat {
	static final Logger LOGGER = LoggerFactory.getLogger(ExtraireResultat.class);
	public static void	extraire(List<String>resultat) {
		LOGGER.info("Debut de l'extraction");
		// Ecriture du fichier
		File ecriture = new File(
				PropertiesUtil.getProperty(("destinationFile"))+PropertiesUtil.getProperty("nomFichier")+timestampDeLextract()+".csv");
		FileWriter fw=null;
		try {
			fw = new FileWriter(ecriture);
			LOGGER.info("Resultat de la conversion {} ", resultat);
			fw.write(String.valueOf(resultat));
			LOGGER.info("Fin de l'extraction");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			// Il se pourrait que l'ouverture du flux 
			// ait échoué et que ce writer n'ait pas été initialisé
			if (fw!=null) {
				try{
					fw.close();
				}catch (IOException e){
					e.getMessage();
				}
			}
		}
	}
	
	/**
	 * Heure de nommage du fichier en sortie du csv. 
	 * @return
	 */
	private static String timestampDeLextract() {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);

		String annee=strDate.substring(0, 4);
		String mois=strDate.substring(5, 7);
		String jour=strDate.substring(8, 10);

		String heure=strDate.substring(11, 13);
		String minutes=strDate.substring(14, 16);
		String secondes=strDate.substring(17, 19);

		String heureExtractDate,heureExtractTemps,heureNommageFichier;
		heureExtractDate=annee+mois+jour;

		heureExtractTemps=heure+minutes+secondes;
		heureNommageFichier=heureExtractDate+"_"+heureExtractTemps;
		return heureNommageFichier;
	}

}
