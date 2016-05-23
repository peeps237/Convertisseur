package metier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.ChiffreRomainException;
import util.ExtraireResultat;


/**
 * Classe de conversion de chiffres romains
 * en entier et vice versa.
 * @author samuel
 */

public class ChiffresRomains {

	private static Logger LOGGER = LoggerFactory.getLogger(ChiffresRomains.class);
	static int compteur = 0;
	private final static String[] caracteresRomainsValables={"I","V","X","L","C","D","M"};

	private final static String[] tableau1 = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
	private final static String[] tableau2 = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
	private final static String[] tableau3 = {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
	private final static String[] tableau4 = {"","M","MM","MMM","","","","","",""};


	/**
	 * Intervalle de conversion.
	 * @param val
	 * @return
	 */
	public static boolean chiffreRomainsCheck(int val) {
		if ((val>0)&&(val<=39999)) return true;
		return false;
	}

	/**
	 * Correspondance des nombres
	 * romain et entier.
	 * @param c
	 * @return
	 */
	public static int chiffreRomainsConversion(char c) {
		switch (c) {
		case 'I': return 1;
		case 'V': return 5;
		case 'X': return 10;
		case 'L': return 50;
		case 'C': return 100;
		case 'D': return 500;
		case 'M': return 1000;
		}
		return 0;
	}

	/**
	 * Verification de la chaine à convertir.
	 * @param i l'entier à convertir
	 * @return le chiffre romain
	 */
	public static boolean chiffreRomainsCheck(String valeur) {
		boolean valeurActuelle;

		for (int i=0;i<valeur.length();i++) {
			valeurActuelle=false;
			for (int j=0;j<caracteresRomainsValables.length;j++)
				if (valeur.substring(i, i+1).equals(caracteresRomainsValables[j])) valeurActuelle=true;
			if (valeurActuelle==false) return false;
		}
		return true;
	}

	/**
	 * Conversion d'un entier en chiffre romain
	 * @return l'entier converti
	 */
	public static String conversionEntierRomain(int i) {
		if (chiffreRomainsCheck(i)) {
			if (i>=0 && i<=9) return tableau1[i];
			if (i>=10 && i<=99) return tableau2[i / 10]+ tableau1[i % 10];
			if (i>=100 && i<=999) return tableau3[i / 100]+ tableau2[(i / 10) % 10]+ tableau1[i % 10];
			if (i>=1000 && i<=3999) return tableau4[i / 1000]+tableau3[(i / 100) % 10]+tableau2[(i / 10) % 10]+tableau1[i % 10];

		}else{
			try {
				throw new Exception("Verifier la valeur saisie");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	/**
	 * Conversion d'un chiffre romain en entier
	 * @param chiffreAconvertir
	 * @return
	 */
	public static int conversionRomainEnEntier(String chiffreAconvertir) {
		int debut,chiffreRomain1,chiffreRomain2=0,resultat=0;

		if (chiffreRomainsCheck(chiffreAconvertir)) {
			for (debut=0;debut<chiffreAconvertir.length();debut++) {
				chiffreRomain1 = chiffreRomainsConversion(chiffreAconvertir.toCharArray()[debut]);
				if (chiffreRomain1 > chiffreRomain2) {
					resultat+=chiffreRomain1-(chiffreRomain2<<1);
				}else {
					resultat+=chiffreRomain1;
				}
				chiffreRomain2=chiffreRomain1;
			}

		}else{
			// on ne prend que les caracteres valides
			LOGGER.info("Veuiller modifier le caractere {} de la ligne {} dans le fichier en entree du programme", chiffreAconvertir, compteur);
			throw new ChiffreRomainException("Le caractere "+chiffreAconvertir+" est invalide pour une conversion");
		}
		return resultat;
	}

	/**
	 * Methode qui permet l'association du chiffre avec sa valeur convertie
	 * apres lecture  ligne par ligne du fichier en entree.
	 * @param fichier: Le fichier en input
	 * @return/ Le resultat de la conversion selon le format attendu.
	 */
	public static Map<?, ?> lireFichier (String fichier) {

		BufferedReader br=null;
		List<String> liste = new ArrayList<String>();
		Map<?, ?> resultat=null;
		FileReader fr = null;
		try {
			fr = new FileReader(fichier);
		} catch (FileNotFoundException e1) {
			LOGGER.info("{} ",e1.getMessage());
			System.exit(1);
		}
		br = new BufferedReader(fr);
		while(true){
			String ligne=null;
			try{
				ligne=br.readLine();
				String regex = "[0-9]+";
				if(ligne!=null){
					// test qui permet de savoir si on manipule
					// en entrée du fichier des chiffres romains
					// ou des nombres entiers positifs
					if(ligne.matches(regex)){
						LOGGER.info("La ligne {} a comme valeur {} ", compteur, ligne);
						resultat = new HashMap<Integer, String>();
						resultat = calculEntierARomain(liste, ligne);
					}else{
						LOGGER.info("La ligne {} a comme valeur {} ", compteur, ligne);
						resultat = new HashMap<String,Integer>();
						resultat = calculRomainAEntier(liste, ligne);
					}
					compteur++;
				}else {
					LOGGER.debug("Aucune valeur détectée dans le fichier");
					break;
				}
			}catch(IOException e){
				LOGGER.debug("{} ",e.getMessage());
				System.exit(1);
			}
		}

		try {
			br.close();fr.close();
		} catch (IOException e) {
			LOGGER.debug("{} ",e.getMessage());
			System.exit(1);
		}
		LOGGER.info("Nombre de chiffres à convertir: {}", liste.size());
		List<String> listeDef = finalList(liste);
		
		ExtraireResultat.extraire(listeDef);
		return resultat;
	}

	private static Map<Integer, String> calculEntierARomain(List<String> liste,
			String ligne) {
		Map<Integer, String> resultat= new HashMap<Integer, String>();
		String romanMapping=conversionEntierRomain(Integer.valueOf(ligne));
		Integer nombreSaisi=Integer.valueOf(ligne);
		resultat.put(nombreSaisi, romanMapping);
		liste.add(resultat.toString());
		return resultat;
	}

	private static Map<String, Integer> calculRomainAEntier(List<String> liste,
			String ligne) {
		Map<String, Integer> resultat= new HashMap<String, Integer>();
		int entierMapping=conversionRomainEnEntier(ligne);
		resultat.put(ligne, entierMapping);
		liste.add(resultat.toString());
		return resultat;
	}

	private static List<String> finalList(List<String> liste) {
		List<String> listeDef = new ArrayList<String>();
		for (int i=0; i<liste.size(); i++) {			
			String val=liste.get(i);
			String[] tri = val.split("=");
			String tr1 = tri[0].substring(1);
			String tr2=  tri[1].substring(0,tri[1].length()-1);
			// Format de sortie voulue
			String res="("+tr1+","+tr2+")";
			listeDef.add(res);
		}
		LOGGER.debug("valeur de la liste à extraire "+listeDef);
		return listeDef;
	}
}