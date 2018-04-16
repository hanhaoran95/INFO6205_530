package test;
import org.junit.Test;
import genetics.GA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class uniTest {
    private static GA ga;
    private String[] ipop = new String[1000];	
   
	public static void setGa() {
		ga = new GA();
	}

	 
	@Test
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    int index = 0;
	    while ((index = srcText.indexOf(findText, index)) != -1) {
	        index = index + findText.length();
	        count++;
	    }
	    return count;
	}
	public void testInitChr() {
        for (int i = 0; i < 100; i++) {
        	    String test = ga.initChr();
        	    int number_0 = appearNumber(test,"0");
        	    int number_1 = appearNumber(test,"1");
        	    assertTrue(number_0!=0);
        	    assertTrue(number_1!=0);
         }
    }
	
	@Test
	public void testInitPopulation() {
		Boolean  test_gene = true;
		for(int i=0 ; i<100; i++) {
		     String[] result = ga.initPop();
		     int size = result.length;
		     for(int j =0; j<size;j++) {
		    	      if(result[0].charAt(j)!=0  && result[0].charAt(j)!=1)
		    	    	  test_gene = false;
		     }
		     assertTrue(size == 1000);
		     assertTrue(test_gene);
		}
	}
	
	@Test
	public void testCalculateFitness() 
	{
		String test = "000000000000000000000000000000000000000000000000000000000000";
		double[] result = ga.calculatefitnessvalue(test);
		assertTrue(result[10] == 3);
	}
	
	@Test
	public void testSelection() {
		String[] test = ga.initPop();
		ga.select(test);
		double re1 = ga.calculatefitnessvalue(test[0])[10];
		double re2 = ga.calculatefitnessvalue(test[1])[10];
		assertTrue(re1>=re2);
	}
	
	@Test
	public void testCrossover() {
		int pos = 29;
		String[] test = new String[2];
		test[0]="111111111111111111111111111111000000000000000000000000000000";
		test[1]="000000000000000000000000000000111111111111111111111111111111";
		String temp1 = test[0].substring(0, pos) + test[1].substring(pos); 
		String temp2 = test[1].substring(0, pos) + test[0].substring(pos);
		int re1 = appearNumber(temp1,"1");
		int re2 = appearNumber(temp2,"0");
		assertTrue(re1==re2);
	}
	

}