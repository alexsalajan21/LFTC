package main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import controller.Controller;
import model.Grammar;

public class Main {
	static Controller ctrl = new Controller();
	public static void main(String[] args) throws IOException {
		
		Grammar grammar = ctrl.getRepo().citireGramFisier();
		Map<String,List<String>> mapForFirst;
		Map<String,List<String>> mapForFollow;
		List<String> nonterminalList = grammar.getNeterminali();
		/*for(String nonterminal: nonterminalList) {
			//mapForFirst.clear();
			mapForFirst = ctrl.first(nonterminal, grammar);
			for (Map.Entry<String,List<String>> entry : mapForFirst.entrySet()) {
			    System.out.println(entry.getKey() + ", " + entry.getValue());
			    
			}
		*/	
		for(String nonterminal: nonterminalList) {
			mapForFollow = ctrl.follow(nonterminal, grammar);
			for(Map.Entry<String, List<String>> entry: mapForFollow.entrySet()) {
				System.out.println(entry.getKey() + ", "+ entry.getValue());
			}
		}
		
	}
}
