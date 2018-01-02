package model;

public class CellValue {

	private String pdp;
	private Integer ruleNumber;
	
	
	public CellValue() {
		
	}
	public CellValue(String pdp, Integer ruleNumber) {
		super();
		this.pdp = pdp;
		this.ruleNumber = ruleNumber;
	}
	public String getPdp() {
		return pdp;
	}
	public void setPdp(String pdp) {
		this.pdp = pdp;
	}
	public Integer getRuleNumber() {
		return ruleNumber;
	}
	public void setRuleNumber(Integer ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	@Override
	public String toString() {
		return "(" + pdp + ", " + ruleNumber + ")";
	}
	
	
	
	
}
