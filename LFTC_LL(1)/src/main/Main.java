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
		//System.out.println(grammar.toString());
		Map<String,List<String>> mapForFirst = ctrl.first("X", grammar);
		for (Map.Entry<String,List<String>> entry : mapForFirst.entrySet()) {
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
}
