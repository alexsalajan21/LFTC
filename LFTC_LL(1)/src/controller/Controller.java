package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import model.CellValue;
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
						firstList = first(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)),grammar).get(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)));
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
	
	public Map<Production,Integer> numberRuleAppliedToProduction(Grammar grammar){
		List<Production> productionList = grammar.getProductii();
		LinkedHashMap<Production,Integer> map = new LinkedHashMap<Production,Integer>();
		int  number = 1;
		for(Production prod: productionList) {
			map.put(prod, number);
			number++;
		}
		return map;
		
	}
	public Table<String,String,CellValue> createTable(Grammar grammar) {
		List<String> nonterminalList = grammar.getNeterminali();
		List<String> terminalList = grammar.getTerminali();
	
		List<String> columnTable = new ArrayList<String>();
		for(String terminal: terminalList) {
			columnTable.add(terminal);
		}
		columnTable.add("$");
		
		List<String> rowTable = new ArrayList<String>();
		for(String nonterminal: nonterminalList) {
			rowTable.add(nonterminal);
		}
		for(String terminal: columnTable) {
			rowTable.add(terminal);
		}
		Table<String,String,CellValue> table = ArrayTable.create(rowTable,columnTable);
		
		Map<String,Integer> valueMap = new LinkedHashMap<String,Integer>();
		Map<Production,Integer> mapRuleNumber = numberRuleAppliedToProduction(grammar);
		List<Production> productionList = grammar.getProductii();
		for(Production  production: productionList) {
			firstList.clear();
			followList.clear();
			List<String> firstList = first(production.getPsp(),grammar).get(production.getPsp());
			
			for(String atom:firstList) {
				//valueMap.put(production.getPdp().get(0), mapRuleNumber.get(production));
				if(atom.charAt(0)!= '0' && production.getPdp().get(0).charAt(0) != '0' ) {
					CellValue value = new CellValue(production.getPdp().get(0),mapRuleNumber.get(production));
					if(atom.equals(String.valueOf(production.getPdp().get(0).charAt(0))) || nonterminalList.contains(String.valueOf(production.getPdp().get(0).charAt(0)))) {
						table.put(production.getPsp(),atom, value);
					}
					
					
					
				}
				else if(atom.charAt(0) == '0' && production.getPdp().get(0).charAt(0) == '0') {
					List<String> followList = follow(production.getPsp(),grammar).get(production.getPsp());
					for(String follow:followList) {
						CellValue value = new CellValue(production.getPdp().get(0),mapRuleNumber.get(production));
						table.put(production.getPsp(),follow, value);
					}
					
				}
			
				
			}
			
			
		}
		for(String row: rowTable) {
			for(String column: columnTable) {
				if(row.equals(column)) {
					if(!row.equals("$")) {
						CellValue value = new CellValue("pop",0); 
						table.put(row, column,value);
					}
					else {
						CellValue val = new CellValue("acc",0);
						table.put(row, column,val);
					}
					
				}
				
			}
		}
		
		
		return table;
		
		
	}

	
	
	


}
