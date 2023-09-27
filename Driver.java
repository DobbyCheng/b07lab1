import java.io.*;

public class Driver {
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1_c = {5,6};
		int [] c1_e = {3,0};
		Polynomial p1 = new Polynomial(c1_c,c1_e);
		double [] c2_c = {-9,-2};
		int [] c2_e = {4,1};
		Polynomial p2 = new Polynomial(c2_c,c2_e);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		double [] c3_c = {-4.8,2,4};
		int [] c3_e = {2,1,5};
		Polynomial p3 = new Polynomial(c3_c,c3_e);
		double [] c4_c = {3,2,4};
		int [] c4_e = {0,1,4};
		Polynomial p4 = new Polynomial(c4_c,c4_e);
		Polynomial prod = p1.multiply(p3);
		System.out.println("prod:");
		for(int i=0;i<prod.coeff.length;++i) {
			System.out.println("coeff expo: "+prod.coeff[i]+" "+prod.expo[i]);
		}
		System.out.println();
		System.out.println("prod:");
		prod = p3.multiply(p4);
		for(int i=0;i<prod.coeff.length;++i) {
			System.out.println("coeff expo: "+prod.coeff[i]+" "+prod.expo[i]);
		}
		System.out.println();
		
		File f=new File("/Users/yuchengcheng/b07lab1/input.txt");
		Polynomial p5=new Polynomial(f);
		System.out.println("p5:");
		for(int i=0;i<p5.coeff.length;++i) {
			System.out.println("coeff expo: "+p5.coeff[i]+" "+p5.expo[i]);
		}
		System.out.println();
		
		p5.saveToFile("/Users/yuchengcheng/b07lab1/output.txt");
	}
}