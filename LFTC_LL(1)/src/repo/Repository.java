package repo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import model.Grammar;

import model.Production;


public class Repository {
	
	public Repository(){
		
	}
	
	public Grammar citireGramFisier() throws IOException{
		Reader reader = new FileReader("Gramatica.txt");
		BufferedReader br = new BufferedReader(reader);
		List<String> lines = br.lines().collect(Collectors.toList());
		String start = lines.get(0).split(" ")[0];
		List<Production> productionList = new ArrayList<Production>();
		List<String> neterminali = new ArrayList<String>();
		List<String> terminali = new ArrayList<String>();
		
		for(String line:lines){
			List<String> pdp = new ArrayList<String>();
			String [] splits = line.split(" ");
			String psp = splits[0]; // partea stanga a productiei
			if(!psp.equals("$") && !neterminali.contains(psp))
				neterminali.add(psp);
			String[] array =splits[1].split(","); // partea dreapta a productiei
			for(String a:array){
				if(!pdp.contains(a))
					pdp.add(a);
				
				//"^" stands for begining of string  
				//"?" stands for zero or one character
				
				String[] splits1 = a.split("[^a-z+\\(\\)*]?");  
				for(String sp : splits1){
					if(!terminali.contains(sp) && !sp.equals(""))
						terminali.add(sp);
					
				}
				
			}
			Production prod = new Production(psp,pdp);
			
			
//			productionList.add(prod);
			if(!productionList.contains(prod.getPsp()) && !productionList.contains(prod.getPdp())){
				productionList.add(prod);
			}
			
		}	
				
			
			
		
		Grammar gr = new Grammar(start,neterminali,terminali,productionList);
		
		br.close();
	
		return gr;
	}

	public Grammar readMlGrammar() throws IOException {
		Reader reader = new FileReader("MiniLanguageGrammar.txt");
		BufferedReader br = new BufferedReader(reader);
		List<String> lines = br.lines().collect(Collectors.toList());
		String start = lines.get(0).split("->")[0];
		List<Production> productionList = new ArrayList<Production>();
		List<String> neterminali = new ArrayList<String>();
		List<String> terminali = new ArrayList<String>();
		terminali = readSpecialTerminalsFromFile();
		for(String line:lines){
			List<String> pdp = new ArrayList<String>();
			String [] splits = line.split("->");
			String psp = splits[0]; // partea stanga a productiei
			if(!psp.equals("$") && !neterminali.contains(psp))
				neterminali.add(psp);
			String[] array = splits[1].split("\\|"); // partea dreapta a productiei
			for(String a:array){
				if(!pdp.contains(a))
					pdp.add(a);
				
				//"^" stands for begining of string  
				//"?" stands for zero or one character
				Matcher matcher;
				//matcher = Pattern.compile("(?U)\\w+|[.,<\\(\\[\\..\\:=]+|'([^']+)'|([^;])|([^\\)])|([^\\]])").matcher(a);
				matcher = Pattern.compile("(?U)\\w|[.,<>\\(\\[\\..\\:=]+|([^'])|([^;])|([^\\)])|([^\\]])").matcher(a);
				while(matcher.find()) {
					if(!terminali.contains(matcher.group() ) && !matcher.group().equals(" ") && !Character.isUpperCase(matcher.group().charAt(0))) {
						terminali.add(matcher.group());
					}
					
					
				}
				
			}
			Production prod = new Production(psp,pdp);
			
			
//			productionList.add(prod);
			if(!productionList.contains(prod.getPsp()) && !productionList.contains(prod.getPdp())){
				productionList.add(prod);
			}
			
	}
		Grammar gr = new Grammar(start,neterminali,terminali,productionList);
		
		br.close();
	
		return gr;
}


	public List<String> readSpecialTerminalsFromFile() throws FileNotFoundException {
		List<String> specialTerminalList = new ArrayList<String>();
	
		Reader reader = new FileReader("SpecialTerminals.txt");
		BufferedReader br = new BufferedReader(reader);
		List<String> lines = br.lines().collect(Collectors.toList());
		for(String line:lines) {
			String[] splits = line.split(",");
			for(String terminal: splits) {
				specialTerminalList.add(terminal);
			}
		}
		
		return specialTerminalList;
	}

}

