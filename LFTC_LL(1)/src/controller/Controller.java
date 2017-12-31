package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Grammar;
import model.Production;
import repo.Repository;

public class Controller {
	private Repository repo  = new Repository();
	List<String> firstList = new ArrayList<String>();
	List<String> followList = new ArrayList<String>();
	
	
	public Repository getRepo() {
		return repo;
	}

	public void setRepo(Repository repo) {
		this.repo = repo;
	}

	
	
	public Controller(){
		
	}
	
	//String= X Nonterminal, List<String> First(X)
	public LinkedHashMap<String,List<String>> first(String nonterminal,Grammar grammar){
		firstList.clear();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		
		List<Production> productionList = grammar.getProductii();
		List<String> terminalList = grammar.getTerminali();
		for(int i=0; i<productionList.size(); i++){
			if(productionList.get(i).getPsp().equals(nonterminal)){
					if(terminalList.contains(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)))){
						firstList.add(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)));
					}
					else if(!terminalList.contains(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0))) && !productionList.get(i).getPdp().get(0).equals("0")){
						first(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)),grammar);
					}
					else if(productionList.get(i).getPdp().get(0).equals("0")){
						firstList.add("0"); // stands for epsilon
					}
					}
				}
			
			
			
			
		
		map.put(nonterminal, firstList);
		
		return map;
	}
	
	public LinkedHashMap<String,List<String>> follow(String nonterminal, Grammar grammar){
		followList.clear();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		List<Production> productionList = grammar.getProductii();
		List<String> terminalList = grammar.getTerminali();
		if(nonterminal.equals(grammar.getSimbolStart())) {
			followList.add("$");
		}
		for(int i=0; i<productionList.size(); i++) {
			
			for(int j=0; j<productionList.get(i).getPdp().size(); j++) {
				if(productionList.get(i).getPdp().get(j).contains(nonterminal)) {
					//&& String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)).equals("")
					int indexOfNonterminal = productionList.get(i).getPdp().get(j).indexOf(nonterminal);
					if(indexOfNonterminal+1 == productionList.get(i).getPdp().get(j).length() && !productionList.get(i).getPsp().equals(nonterminal) ){
						followList = follow(productionList.get(i).getPsp(),grammar).get(productionList.get(i).getPsp());
					}
					else if(indexOfNonterminal+1 < productionList.get(i).getPdp().get(j).length() && terminalList.contains(String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)))) {
						followList.add(String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)));
					}
					else if(indexOfNonterminal+1 < productionList.get(i).getPdp().get(j).length() && !terminalList.contains(String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)))) {
						followList = follow(productionList.get(i).getPsp(),grammar).get(productionList.get(i).getPsp());
						List<String> firsts = first(String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)),grammar).get(String.valueOf(productionList.get(i).getPdp().get(j).charAt(indexOfNonterminal+1)));
						for(String first: firsts) {
							if(!first.equals("0")) {
								followList.add(first);
							}
						}
					}
				}
				
			}
		}
		map.put(nonterminal,followList);
		return map;
	}
	
	


}
