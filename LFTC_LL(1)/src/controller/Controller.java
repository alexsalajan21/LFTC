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
	LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
	
	public Repository getRepo() {
		return repo;
	}

	public void setRepo(Repository repo) {
		this.repo = repo;
	}

	
	
	public Controllerasd(){
		
	}
	
	//String= X Nonterminal, List<String> First(X)
	public LinkedHashMap<String,List<String>> first(String nonterminal,Grammar grammar){
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
	
	
	
//	public Map<Integer,Production> numProductions(Grammar gr){
//		Map<Integer,Production> mapProductions  = new HashMap<Integer,Production>();
//		Integer productionNumber=1;
//		for(Production prod: gr.getProductii()){
//			mapProductions.put(productionNumber, prod);
//			productionNumber++;
//		}
//		
//		return mapProductions;
//		
//	}
	
	
//	public List<ValueFirstFollow> tableFirstFollow(Production production,Map<Integer,Production> mapProductions) throws IOException{
//		Grammar gr = repo.citireGramFisier();
//		List<ValueFirstFollow> valueFirstList = new ArrayList<ValueFirstFollow>();
//		List<String> terminalList = gr.getTerminali();
//		List<String> nonterminalList = gr.getNeterminali();
//		List<String> firstList = new ArrayList<>();
//		List<Production> productionList = gr.getProductii();
//		List<String> startSymbolList = new ArrayList<String>();
//		for(int i=0; i<mapProductions.size(); i++){
//			String startSymbol = mapProductions.get(i).getPsp(); //"S"->BA
//			startSymbolList.add(startSymbol);
//			if(nonterminalList.contains(mapProductions.get(i).getPdp().get(0))){//is nonterminal
//				
//				productionList = findProductionBySymbol(mapProductions.get(i).getPdp().get(0),mapProductions);
//				for(Production prod :productionList){
//					tableFirstFollow(prod,mapProductions);
//				}
//			
//			}
//			else{
//				
//				String first = mapProductions.get(i).getPdp().get(0);
//				firstList.add(first);
//			}
//				ValueFirstFollow valueFirst = new ValueFirstFollow(startSymbolList.get(0),firstList,null);
//				startSymbolList.clear();
//				valueFirstList.add(valueFirst);
//			}
//			
//		
//		
//		
//		return null;
//		
//	}
//	public List<Production> findProductionBySymbol(String symbol,Map<Integer,Production> map){
//		List<Production> productionList = new ArrayList<Production>();
//		for(int i=0; i<map.size(); i++){
//			if(map.get(i).getPsp().equals(symbol)){
//				productionList.add(map.get(i));
//			}
//		}
//		
//		
//		return productionList;
//	}


}
