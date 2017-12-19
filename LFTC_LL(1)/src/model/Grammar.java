package model;

import java.util.List;

public class Grammar {
	private String simbolStart;
	private List<String> neterminali;
	private List<String> terminali;
	private List<Production> productii;
	
	public Grammar(){
		
	}
	public Grammar(String simbolStart,List<String> neterminali, List<String> terminali, List<Production> productii) {
		super();
		this.neterminali = neterminali;
		this.terminali = terminali;
		this.productii = productii;
		this.simbolStart = simbolStart;
	}
	
	
	public String getSimbolStart() {
		return simbolStart;
	}
	public void setSimbolStart(String simbolStart) {
		this.simbolStart = simbolStart;
	}
	public List<String> getNeterminali() {
		return neterminali;
	}
	public void setNeterminali(List<String> neterminali) {
		this.neterminali = neterminali;
	}
	public List<String> getTerminali() {
		return terminali;
	}
	public void setTerminali(List<String> terminali) {
		this.terminali = terminali;
	}
	public List<Production> getProductii() {
		return productii;
	}
	public void setProductii(List<Production> productii) {
		this.productii = productii;
	}
	@Override
	public String toString() {
		return "Grammar [start=" + simbolStart + ",neterminali=" + neterminali + ", terminali=" + terminali + ", productii=" + productii.toString() + "]";
	}
	
	

	
}


