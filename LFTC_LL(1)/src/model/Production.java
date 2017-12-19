package model;

import java.util.List;

public class Production {
	private String psp;
	private List<String> pdp;
	
	public Production(){
		
	}
	
	
	
	

	

	public Production(String psp, List<String> pdp) {
		super();
		this.psp = psp;
		this.pdp = pdp;
	}
	
	







	public String getPsp() {
		return psp;
	}







	public void setPsp(String psp) {
		this.psp = psp;
	}







	public List<String> getPdp() {
		return pdp;
	}







	public void setPdp(List<String> pdp) {
		this.pdp = pdp;
	}







	public String toString(){
		String concat = "";
		int size = pdp.size();
		if(size>1){
			for(int i=0; i<size; i++){
				concat = concat+pdp.get(i)+"|";
			}
			return psp + "->" + concat.substring(0,concat.length()-1);// last | is ignored
		}
		else{
			return psp + "->" + pdp;
		}
		
		
	}
}

