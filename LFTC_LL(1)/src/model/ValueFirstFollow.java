package model;

import java.util.List;

public class ValueFirstFollow {
	private String nonterminal;
	private List<String> first;
	private List<String> follow;
	
	
	public ValueFirstFollow(String nonterminal, List<String> first, List<String> follow) {
		super();
		this.nonterminal = nonterminal;
		this.first = first;
		this.follow = follow;
	}
	public ValueFirstFollow(){
		
	}
	public String getNonterminal() {
		return nonterminal;
	}
	public void setNonterminal(String nonterminal) {
		this.nonterminal = nonterminal;
	}
	public List<String> getFirst() {
		return first;
	}
	public void setFirst(List<String> first) {
		this.first = first;
	}
	public List<String> getFollow() {
		return follow;
	}
	public void setFollow(List<String> follow) {
		this.follow = follow;
	}
	@Override
	public String toString() {
		return "TabelFirstFollow [nonterminal=" + nonterminal + ", first=" + first + ", follow=" + follow + "]";
	}
	
	
}
