package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		Stack<String> possibilidades = new Stack<String>();
		Stack<String> movExecutado = new Stack<String>();
		List<Integer[][]> posicoesPassdas = new ArrayList<Integer[][]>();

		String posicao = responder.ask("norte");	
		String movimento;
		String verifica = "inicio";
				
		while(!posicao.equals("saida")){
			
			if((responder.ask("leste").equals("passagem") ||responder.ask("leste").equals("saida"))
					&& !verifica.equals("leste"))
				possibilidades.push("leste");			
			if((responder.ask("oeste").equals("passagem") || responder.ask("oeste").equals("saida"))
					&& !verifica.equals("oeste"))
				possibilidades.push("oeste");
			if((responder.ask("sul").equals("passagem") ||responder.ask("sul").equals("saida"))
					&& !verifica.equals("sul"))
				possibilidades.push("sul");			
			if((responder.ask("norte").equals("passagem") ||responder.ask("norte").equals("saida"))
					&& !verifica.equals("norte"))
				possibilidades.push("norte");
			
			movimento = possibilidades.pop();
			responder.move(movimento);
			
			switch (movimento) {
				case "norte": movExecutado.push("sul"); break;
				case "sul":   movExecutado.push("norte"); break;
				case "leste": movExecutado.push("oeste"); break;
				case "oeste": movExecutado.push("leste"); break;
			}
			
			posicao = responder.ask("aqui");
			verifica = movExecutado.peek();
		}
		
		
		return true;
	}
	
}
