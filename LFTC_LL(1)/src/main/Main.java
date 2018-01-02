package main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import controller.Controller;
import model.CellValue;
import model.Grammar;
import model.Production;

public class Main {
	static Controller ctrl = new Controller();
	public static void main(String[] args) throws IOException {
		
		Grammar grammar = ctrl.getRepo().citireGramFisier();
		Map<String,List<String>> mapForFirst;
		Map<String,List<String>> mapForFollow;
		List<String> nonterminalList = grammar.getNeterminali();
		Map<Production,Integer> mapRuleNumber;
		
		System.out.println("First:\n");
		
		
		for(String nonterminal: nonterminalList) {
			
			mapForFirst = ctrl.first(nonterminal, grammar);
			
			
			for (Map.Entry<String,List<String>> entry : mapForFirst.entrySet()) {
				
			    System.out.println(entry.getKey() + ", " + entry.getValue());
			    
			}
			
		}
		System.out.println("\nFollow:\n");
		for (String  nonterminal: nonterminalList) {
			mapForFollow = ctrl.follow(nonterminal, grammar);
			
			for(Map.Entry<String, List<String>> entry: mapForFollow.entrySet()) {
				
				System.out.println(entry.getKey() + ", "+ entry.getValue());
			}
		}
		
		System.out.println("\nRulenumber Productions:\n");
		mapRuleNumber = ctrl.numberRuleAppliedToProduction(grammar);
		for(Map.Entry<Production, Integer> entry: mapRuleNumber.entrySet()) {
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
		
		System.out.println("\nTable\n");
		Table<String, String, CellValue> table = ctrl.createTable(grammar);
	/*	Set<String> rowSet = table.rowKeySet();
		Set<String> columnSet = table.columnKeySet();*/
	/*	for(String column: columnSet) {
			System.out.print(" "+column+" ");
		}
		System.out.println();
		for(String row: rowSet) {
			System.out.println(row+ " ");
			
		}*/
		
		for(Cell<String, String, CellValue> entry: table.cellSet() ) {
			
			System.out.println( entry.getRowKey() + " "+ entry.getColumnKey()+" "+ entry.getValue() );
		}
		
	}
}
