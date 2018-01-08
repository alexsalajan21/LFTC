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
			ctrl.getFollowList().clear();
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
	
		
		for(Cell<String, String, CellValue> entry: table.cellSet() ) {
			
			System.out.println( entry.getRowKey() + " "+ entry.getColumnKey()+" "+ entry.getValue() );
		}
		System.out.println("\nSintactic Analizor LL(1)\n");
		String sequence = "(a+a)*a";
		String pi = ctrl.analSintLL1(table,mapRuleNumber,sequence);
		
		
		System.out.println("\nMinilanguage Grammar:\n");
		Grammar gr = ctrl.getRepo().readMlGrammar();
		System.out.println(gr.toString());
		
System.out.println("First for Minilanguage Grammar:\n");
		Map<String,List<String>> firstForMLGrammar;
		Grammar grammarML = ctrl.getRepo().readMlGrammar();
		List<String> nonterminalsForMLGrammar = grammarML.getNeterminali();
		for(String nonterminal: nonterminalsForMLGrammar) {
			ctrl.getHelpList().clear();
			firstForMLGrammar = ctrl.firstForMLGrammar(nonterminal,grammarML);
			
			
			for (Map.Entry<String,List<String>> entry : firstForMLGrammar.entrySet()) {
				
			    System.out.println(entry.getKey() + ", " + entry.getValue());
			    
			}
			
		}
	
		System.out.println("\nFollow For Minilanguage Grammar:\n");
		Map<String,List<String>> followForMLGrammar;
		for(String nonterminal: nonterminalsForMLGrammar) {
			ctrl.getHelpList().clear();
			ctrl.getHelpListForFollow().clear();
			followForMLGrammar = ctrl.followForMLGrammar(nonterminal, grammarML);
			
			for(Map.Entry<String, List<String>> entry:followForMLGrammar.entrySet() ) {
				System.out.println(entry.getKey()+ ","+entry.getValue());
			}
		}
		System.out.println("\nNumbered Productions Minilanguage Grammar: \n");
		Map<Production,Integer> numberedProductionML = ctrl.numberRuleAppliedToProduction(grammarML);
		for(Map.Entry<Production, Integer> entry: numberedProductionML.entrySet()) {
			System.out.println(entry.getKey()+ ","+entry.getValue());
		}
		System.out.println("\nTable Minilanguage Grammar:\n");
		Table<String,String,CellValue> tableMLGrammar = ctrl.createTableForMLGrammar(grammarML);
		for(Cell<String, String, CellValue> entry: tableMLGrammar.cellSet() ) {
			if(!entry.getValue().getPdp().equals("err"))
				System.out.println( entry.getRowKey() + " "+ entry.getColumnKey()+" "+ entry.getValue() );
		}
		
		System.out.println("/nSintactic Analysor for ML Grammar:/n");
		String sequenceForMlGrammar = "var nr1, nr2, result : integer;\r\n" + 
				"begin\r\n" + 
				"write('Introduceti cele 2 numere');\r\n" + 
				"readln(nr1,nr2);\r\n" + 
				"result:=nr1+nr2;\r\n" + 
				"writeln;\r\n" + 
				"write('suma numerelor este:');\r\n" + 
				"write(result);\r\n" + 
				"end.";
		
		String picaso = ctrl.analSintLL1ForMLGrammar(tableMLGrammar, numberedProductionML, sequenceForMlGrammar);
	}

}
