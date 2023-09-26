import java.io.*;

public class Polynomial {
	double[] coeff;
	int[] expo;
	public Polynomial() {
		coeff=new double[1];
		expo=new int[1];
	}
	public Polynomial(double[] a,int[] b) {
		coeff=new double[a.length];
		for(int i=0;i<a.length;i++){
			coeff[i]=a[i];
		}
		expo=new int[b.length];
		for(int i=0;i<b.length;i++){
			expo[i]=b[i];
		}
	}
	public boolean chk(double x) {
		return Math.abs(x)<1e-8;
	}
	public Polynomial add(Polynomial a){
		int max_expo=Math.max(expo[expo.length-1],a.expo[a.expo.length-1]);
		Polynomial b=new Polynomial();
		b.coeff=new double[max_expo+5];
		b.expo=new int[max_expo+5];
		int pos=0,pos_a=0,size_b=0;
		for(int i=0;i<=max_expo;++i) {
			double val=0;
			if(pos<expo.length && expo[pos]==i) {
				val+=coeff[pos];++pos;
			}
			if(pos_a<a.expo.length && a.expo[pos_a]==i) {
				val+=a.coeff[pos_a];++pos_a;
			}
			if(!chk(val)) {
				b.coeff[size_b]=val;
				b.expo[size_b]=i;
				size_b+=1;
			}
		}
		Polynomial c=new Polynomial();
		c.coeff=new double[size_b];
		c.expo=new int[size_b];
		for(int i=0;i<size_b;++i) {
			c.coeff[i]=b.coeff[i];
			c.expo[i]=b.expo[i];
		}
		return c;
	}
	public double evaluate(double x){
		double res=0;
		for(int i=0;i<coeff.length;++i){
			res+=coeff[i]*Math.pow(x,expo[i]);
		}
		return res;
	}
	public boolean hasRoot(double x){
		return chk(evaluate(x));
	}
	public Polynomial multiply(Polynomial a) {
		int max_expo=expo[expo.length-1]+a.expo[a.expo.length-1];
		Polynomial b=new Polynomial();
		b.coeff=new double[max_expo+5];
		b.expo=new int[max_expo+5];
		for(int i=0;i<=max_expo;++i) {
			b.coeff[i]=0;
			b.expo[i]=i;
		}
		for(int i=0;i<coeff.length;++i) {
			for(int j=0;j<a.coeff.length;++j) {
				b.coeff[expo[i]+a.expo[j]]+=coeff[i]*a.coeff[j];
			}
		}
		int size_c=0;
		for(int i=0;i<=max_expo;++i)if(!chk(b.coeff[i]))size_c+=1;
		Polynomial c=new Polynomial();
		c.coeff=new double[size_c];
		c.expo=new int[size_c];
		size_c=0;
		for(int i=0;i<=max_expo;++i) {
			if(!chk(b.coeff[i])) {
				c.coeff[size_c]=b.coeff[i];
				c.expo[size_c]=b.expo[i];
				size_c+=1;
			}
		}
		return c;
	}
	public int find_op(String s) {
		for(int i=0;i<s.length();++i) {
			if(s.charAt(i)=='+' || s.charAt(i)=='-')return i;
		}
		return -1;
	}
	public Polynomial(File f) throws IOException{
		BufferedReader b=new BufferedReader(new FileReader(f));
		String Line=b.readLine();
		String[] s=Line.split("x");
		Polynomial a=new Polynomial();
		a.coeff=new double[s.length+5];
		a.expo=new int[s.length+5];
		int size=0,op_pos=find_op(s[0]);
		if(op_pos==-1 || op_pos==0) {
			a.coeff[size]=Double.parseDouble(s[0]);
		}else {
			a.coeff[size]=Double.parseDouble(s[0].substring(0,op_pos));
			a.expo[size]=0;
			++size;
			a.coeff[size]=Double.parseDouble(s[0].substring(op_pos));
		}
		for(int i=1;i<s.length;++i) {
			op_pos=find_op(s[i]);
			if(op_pos==-1) {
				a.expo[size]=Integer.parseInt(s[i]);
				++size;
			}else if(op_pos==0){
				a.expo[size]=1;
				++size;
				a.coeff[size]=Double.parseDouble(s[i]);
			}else {
				a.expo[size]=Integer.parseInt(s[i].substring(0,op_pos));
				++size;
				a.coeff[size]=Double.parseDouble(s[i].substring(op_pos));
			}
		}
		if(Line.charAt(Line.length()-1)=='x') {
			a.expo[size]=1;
			++size;
		}
		coeff=new double[size];
		expo=new int[size];
		for(int i=0;i<size;++i) {
			coeff[i]=a.coeff[i];
			expo[i]=a.expo[i];
		}
	}
	public void saveToFile(String path) throws IOException {
		String s="";
		for(int i=0;i<coeff.length;++i) {
			if(expo[i]==0) {
				s+=coeff[i];
				continue;
			}
			s+=coeff[i];
			s+="x";
			s+=expo[i];
		}
		FileWriter f=new FileWriter(path);
		f.write(s);
	}
}