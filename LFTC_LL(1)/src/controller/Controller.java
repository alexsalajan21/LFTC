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
					if(terminalList.contains(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0))) && !firstList.contains(String.valueOf(productionList.get(i).getPdp().get(0).charAt(0)))){
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
					//if( table.get(production.getPsp(),atom) == null && (terminalList.contains(String.valueOf(production.getPdp().get(0).charAt(0))) || nonterminalList.contains(String.valueOf(production.getPdp().get(0).charAt(0))))) {
					if( table.get(production.getPsp(),atom) == null && (atom.equals(String.valueOf(production.getPdp().get(0).charAt(0))) || nonterminalList.contains(String.valueOf(production.getPdp().get(0).charAt(0))))) {
						table.put(production.getPsp(),atom, value);
					}
					else if(table.get(production.getPsp(), atom) != null && atom.equals(String.valueOf(production.getPdp().get(0).charAt(0))) ){
						System.out.println("The Grammar is not of type LL(1)!");
						System.exit(0);
					}
					
					
				}
				else if(atom.charAt(0) == '0' && production.getPdp().get(0).charAt(0) == '0') {
					List<String> followList = follow(production.getPsp(),grammar).get(production.getPsp());
					for(String follow:followList) {
						CellValue value = new CellValue(production.getPdp().get(0),mapRuleNumber.get(production));
						if(table.get(production.getPsp(),follow) == null) {
							table.put(production.getPsp(),follow, value);
						}
						else {
							System.out.println("The grammar is not of type LL(1).");
							System.exit(0);
							
						}
						
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
				else if(!row.equals(column) && table.get(row, column)== null) {
					CellValue val = new CellValue("err",0);
					table.put(row, column, val);
				}
					
			}
		}
		
		
		return table;
		
		
	}

	public String analSintLL1(Table<String,String,CellValue> table, Map<Production,Integer> ruleNumbers, String sequence) {
		String alpha = sequence + "$"; //column
		String beta = "S$";   //row
		String pi = "0";
		boolean go = true;
		String flag = " ";
		while(go) {
			CellValue value = table.get(String.valueOf(beta.charAt(0)), String.valueOf(alpha.charAt(0)));
			if(value.getPdp() !="err" && value.getPdp()!="acc" && value.getPdp()!="pop") {
				beta = removeCharAt(beta,0);
				StringBuilder sBuilder = new StringBuilder(beta);
				if(!value.getPdp().equals("0"))
					beta=String.valueOf(sBuilder.insert(0,value.getPdp()));
				pi = pi + String.valueOf(value.getRuleNumber());
				
			}
			else {
				if(value.getPdp() == "pop") {
					beta = removeCharAt(beta,0);
					alpha = removeCharAt(alpha,0);
				}
				else {
					if(value.getPdp() == "acc") {
						go = false;
						flag = "acc";
					}
					else {
						go = false;
						flag = "err";
					}
				}
			}
		}
		if( flag == "acc") {
			System.out.println("Sequence " + sequence +" accepted!");
			
		}
		else {
			System.out.println("Sequence "+sequence+ " not accepted!");
			
		}
		return pi;
	}
	
	public static String removeCharAt(String s, int pos) {

		   StringBuffer buf = new StringBuffer( s.length() - 1 );
		   buf.append( s.substring(0,pos) ).append( s.substring(pos+1) );
		   return buf.toString();
		}
	
	public List<String> parsingTree(String pi, Map<Production,Integer> ruleNumbers,String startSymbol){
		
		String parsingTree = startSymbol;
		List<String> parsingTreeList = new ArrayList<String>();
		for(int i=1; i<pi.length(); i++) {
			for(Map.Entry<Production,Integer> entry: ruleNumbers.entrySet()) {
				if(String.valueOf(entry.getValue()).equals(String.valueOf(pi.charAt(i)))) {
					if(!entry.getKey().getPdp().get(0).equals("0")) {
						parsingTree = parsingTree.replaceFirst(entry.getKey().getPsp(), entry.getKey().getPdp().get(0));
						parsingTreeList.add(parsingTree);
						
					}
					else{
						parsingTree = parsingTree.replaceFirst(entry.getKey().getPsp(), "");
						parsingTreeList.add(parsingTree);
					}
				}
				
				
				
			}
			
		}
		return parsingTreeList;
	}

}
