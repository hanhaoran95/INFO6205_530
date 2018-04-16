package genetics;


public class GA {
	
	private int ChrNum = 1000;	//number_of_population
	private  String[] ipop = new String[ChrNum];	 	//one generation
	private int generation = 0; 	//number of generation
	public static final int GENE = 60; 		//number of gene
	private double bestfitness = 0;
	private int bestgenerations;   	//the best solution's generation
	private String beststr;   		//the string of best solution
	private static final double survial_fraction = 0.5;    
	
	
	
	// init a chromosome（shown in binary）
	public String initChr() {
		String res = "";
		for (int i = 0; i < GENE; i++) {
			if (Math.random() > 0.5) {
				res += "0";
			} else {
				res += "1";
			}
		}
		return res;
	}

	
	
	
	// init one generation's population
	public String[] initPop() {
		String[] ipop = new String[ChrNum];
		for (int i = 0; i < ChrNum; i++) {
			ipop[i] = initChr();
		}
		return ipop;
	}
	
	

	
	// transfer the genotype to phenotype
	public double[] calculatefitnessvalue(String str) {	
		int a = Integer.parseInt(str.substring(0, 5), 2);      
		int b = Integer.parseInt(str.substring(6, 11), 2);
		int c = Integer.parseInt(str.substring(12, 17), 2);
		int d = Integer.parseInt(str.substring(18, 23), 2);
		int e = Integer.parseInt(str.substring(24, 29), 2);
		int f = Integer.parseInt(str.substring(30, 35), 2);
		int g = Integer.parseInt(str.substring(36, 41), 2);
		int h = Integer.parseInt(str.substring(42, 47), 2);
		int i = Integer.parseInt(str.substring(48, 53), 2);
		int j = Integer.parseInt(str.substring(54, 59), 2);

		double x1 =  a * (6.0 - 0) / (Math.pow(2, 6) - 1);    //gene of x
		double x2 =  b * (6.0 - 0) / (Math.pow(2, 6) - 1);    //gene of y
		double x3 =  c * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x4 =  d * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x5 =  e * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x6 =  f * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x7 =  g * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x8 =  h * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x9 =  i * (6.0 - 0) / (Math.pow(2, 6) - 1);
		double x10 =  j * (6.0 - 0) / (Math.pow(2, 6) - 1);
		
		//the Objective function
		double fitness = 3 - Math.sin(2 * x1) * Math.sin(2 * x1) 
				+ Math.sin(2 * x2) * Math.sin(2 * x2)
				- Math.sin(2 * x3) * Math.sin(2 * x3)
				+ Math.sin(2 * x4) * Math.sin(2 * x4)
				- Math.sin(2 * x5) * Math.sin(2 * x5)
				+ Math.sin(2 * x6) * Math.sin(2 * x6)
				- Math.sin(2 * x7) * Math.sin(2 * x7)
				+ Math.sin(2 * x8) * Math.sin(2 * x8)
				- Math.sin(2 * x9) * Math.sin(2 * x9)
				+ Math.sin(2 * x10) * Math.sin(2 * x10);
		
		double[] returns = { x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, fitness };
		return returns;
	}

	
	
	
	// Selection
	public void select(String[] pop) {
		IndexMaxPQ<Double> ipq = new IndexMaxPQ<Double>(ChrNum); 
		double evals[] = new double[ChrNum];    
		for(int i = 0; i< ChrNum; i++) 
		{
			evals[i] = calculatefitnessvalue(pop[i])[10];
		    if (evals[i] > bestfitness){  // take note the best
			bestfitness = evals[i];
			bestgenerations = generation;
			beststr = pop[i];
			}
		    ipq.insert(i,evals[i]);
		}
		int survial_number = (int) (ChrNum*survial_fraction);
		String[] aux = new String[survial_number];
		for(int i = 0; i < survial_number; i++) {
			int choosen = ipq.delMax();
			aux[i] = pop[choosen];
		}
		for(int i=0; i < survial_number; i++) {
			pop[i] = aux[i];
		}
		
		for(int i = survial_number;i < ChrNum;i++ ) {
			pop[i]=pop[i-survial_number];
		}
	}
	

	//crossover
	//fraction of crossover is 60%
	private void cross(String[] pop) {
		String temp1, temp2;
		for (int i = (int) (ChrNum*survial_fraction); i < ChrNum; i+=2) {
			if (Math.random() < 0.60) {
				int pos = (int)(Math.random()*GENE)+1;     
				temp1 = pop[i].substring(0, pos) + pop[i + 1].substring(pos); 
				temp2 = pop[i + 1].substring(0, pos) + pop[i].substring(pos);
				pop[i] = temp1;
				pop[i + 1] = temp2;
			}
		}
	}

	
	//mutation   mutation fraction is 1%
	private void mutation() {
		for (int i = 0; i < 4; i++) {
			int num = (int) (Math.random() * GENE * ChrNum + 1);
			int chromosomeNum = (int) (num / GENE) + 1;

			int mutationNum = num - (chromosomeNum - 1) * GENE; 
			if (mutationNum == 0) 
				mutationNum = 1;
			chromosomeNum = chromosomeNum - 1;
			if (chromosomeNum >= ChrNum)
				chromosomeNum = 9;
			String temp;
			String a;   
			if (ipop[chromosomeNum].charAt(mutationNum - 1) == '0') {    
                a = "1";
			} else {   
				a = "0";
			}
			
			if (mutationNum == 1) {
				temp = a + ipop[chromosomeNum].substring(mutationNum);
			} else {
				if (mutationNum != GENE) {
					temp = ipop[chromosomeNum].substring(0, mutationNum -1) + a 
							+ ipop[chromosomeNum].substring(mutationNum);
				} else {
					temp = ipop[chromosomeNum].substring(0, mutationNum - 1) + a;
				}
			}
         	ipop[chromosomeNum] = temp;
		}
	}

	
	
	public static void main(String args[]) {

		GA Tryer = new GA();
		Tryer.ipop = Tryer.initPop(); //产生初始种群
		String str = "";
		
		//System.out.println(Tryer.beststr);
		
		//迭代次数
		for (int i = 0; i < 10000; i++) {
			Tryer.generation = i;
			Tryer.select(Tryer.ipop);
			Tryer.cross(Tryer.ipop);
			Tryer.mutation();	
		}
		
		System.out.println(Tryer.beststr);
		
		double[] x = Tryer.calculatefitnessvalue(Tryer.beststr);

		str = "maxmum" + Tryer.bestfitness + '\n' 
		        + Tryer.bestgenerations +"th"+ "generation:<" + Tryer.beststr + ">" + '\n' ;
		
	    System.out.println(str);
	    for(int i = 0; i<10; i++) {
	    	
		    	double res = x[i];
		    	String s=String.format("%.1f",res);
		    	System.out.println("x"+(i+1)+"="+s);
	    }
		
		

	}

}
