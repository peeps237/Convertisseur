package test;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;
import org.junit.Assert;
import metier.ChiffresRomains;

public class ConversionRomainTest {
	String fileName = "test.txt";
	InputStream is = this.getClass().getResourceAsStream(fileName);
	
	/**
	 * Test qui verifie la presence
	 * du fichier.
	 */
	@Test
	public void testGetResource() {
		final File file = new File(fileName);
		Assert.assertTrue(file.exists());
	}
	
	/**
	 * Test qui permet de verifier la bonne
	 * valeur romaine de conversion.
	 */
	@Test
	public void testReturnGoodRomanValue() {
		int nombre = 10;
		String result = ChiffresRomains.conversionEntierRomain(nombre);
		Assert.assertEquals(result.length(), 1);
		Assert.assertEquals(result, "X");
	}
}