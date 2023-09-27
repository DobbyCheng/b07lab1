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
		int max_expo=0;
		for(int i:expo)max_expo=Math.max(max_expo,i);
		for(int i:a.expo)max_expo=Math.max(max_expo,i);
		double[] b=new double[max_expo+5];
		for(int i=0;i<expo.length;++i)b[expo[i]]+=coeff[i];
		for(int i=0;i<a.expo.length;++i)b[a.expo[i]]+=a.coeff[i];
		int size_c=0;
		for(int i=0;i<=max_expo;++i)if(!chk(b[i]))++size_c;
		Polynomial c=new Polynomial();
		c.coeff=new double[size_c];
		c.expo=new int[size_c];
		size_c=0;
		for(int i=0;i<=max_expo;++i) {
			if(!chk(b[i])) {
				c.coeff[size_c]=b[i];
				c.expo[size_c]=i;
				++size_c;
			}
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
		int max_exp=0,max_a_exp=0;
		for(int i:expo)max_exp=Math.max(max_exp,i);
		for(int i:a.expo)max_a_exp=Math.max(max_a_exp,i);
		int max_expo=max_exp+max_a_exp;
		double[] b=new double[max_expo+5];
		for(int i=0;i<=max_expo;++i)b[i]=0;
		for(int i=0;i<coeff.length;++i) {
			for(int j=0;j<a.coeff.length;++j) {
				b[expo[i]+a.expo[j]]+=coeff[i]*a.coeff[j];
			}
		}
		int size_c=0;
		for(int i=0;i<=max_expo;++i)if(!chk(b[i]))size_c+=1;
		Polynomial c=new Polynomial();
		c.coeff=new double[size_c];
		c.expo=new int[size_c];
		size_c=0;
		for(int i=0;i<=max_expo;++i) {
			if(!chk(b[i])) {
				c.coeff[size_c]=b[i];
				c.expo[size_c]=i;
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
	public int count_op(String s) {
		int tot=0;
		for(int i=0;i<s.length();++i) {
			if(s.charAt(i)=='+' || s.charAt(i)=='-')++tot;
		}
		return tot;
	}
	public Polynomial(File f) throws IOException{
		BufferedReader b=new BufferedReader(new FileReader(f));
		String Line=b.readLine();
		String[] s=Line.split("x");
		Polynomial a=new Polynomial();
		a.coeff=new double[s.length+5];
		a.expo=new int[s.length+5];
		int size=0;
		for(int i=0;i<s.length;++i) {
			int op_tot=count_op(s[i]),op_pos=find_op(s[i]);
			if(op_tot==0) {
				if(i==0) {
					a.coeff[size]=Double.parseDouble(s[i]);
				}else {
					a.expo[size]=Integer.parseInt(s[i]);
					++size;
				}
			}else if(op_tot==1) {
				if(i==0) {
					a.coeff[size]=Double.parseDouble(s[i]);
				}else {
					a.expo[size]=Integer.parseInt(s[i].substring(0,op_pos));
					++size;
					a.coeff[size]=Double.parseDouble(s[i].substring(op_pos));
				}
			}else {
				int op_pos_2=op_pos+1;
				while(s[i].charAt(op_pos_2)!='+' && s[i].charAt(op_pos_2)!='-')
					++op_pos_2;
				a.expo[size]=Integer.parseInt(s[i].substring(0,op_pos));
				++size;
				a.coeff[size]=Double.parseDouble(s[i].substring(op_pos,op_pos_2));
				a.expo[size]=0;
				++size;
				a.coeff[size]=Double.parseDouble(s[i].substring(op_pos_2));
			}
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
		boolean first_op=true;
		for(int i=0;i<coeff.length;++i) {
			if(expo[i]==0) {
				if(!first_op && coeff[i]>0)s+="+";
				first_op=false;
				s+=coeff[i];
				continue;
			}
			if(!first_op && coeff[i]>0)s+="+";
			first_op=false;
			s+=coeff[i];
			s+="x";
			s+=expo[i];
		}
		FileWriter f=new FileWriter(path);
		f.write(s);
		f.close();
	}
}