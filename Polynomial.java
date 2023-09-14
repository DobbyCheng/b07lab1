public class Polynomial {
	double[] a;
	public Polynomial() {
		a=new double[1];
	}
	public Polynomial(double[] b) {
		int b_le=b.length;
		a=new double[b_le];
		for(int i=0;i<b_le;i++){
			a[i]=b[i];
		}
	}
	public Polynomial add(Polynomial b){
		int b_le=b.a.length,a_le=a.length;
		Polynomial mx,mn;
		if(a_le>=b_le){
			mx=new Polynomial(a);
			mn=new Polynomial(b.a);
		}
		else{
			mx=new Polynomial(b.a);
			mn=new Polynomial(a);
		}
		for(int i=0;i<mn.a.length;i++)
			mx.a[i]+=mn.a[i];
		return mx;
	}
	public double evaluate(double x){
		int a_le=a.length;
		double res=0;
		for(int i=0;i<a_le;++i){
			//System.out.println(a[i]);
			res+=a[i]*Math.pow(x,i);
		}
		return res;
	}
	public boolean hasRoot(double x){
		return evaluate(x)==0.0;
	}
}