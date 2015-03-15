package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;
import java.util.List;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

	IResponder responder;

	public void connect(IResponder responder) {
		this.responder = responder;
	}

	public void volta(int linha, int coluna,int linhaAnterior,int colunaAnterior){
		if(linha == linhaAnterior && coluna > colunaAnterior)
			responder.move("oeste");
		else if(linha == linhaAnterior && coluna < colunaAnterior)
			responder.move("leste");
		else if(linha > linhaAnterior)
			responder.move("norte");
		else
			responder.move("sul");
	}
	public boolean recursao(int linha, int coluna, List<String> posicoesPassadas, boolean resposta,
			int linhaAnterior,int colunaAnterior) {

		String posicao = responder.ask("aqui");
		String aux;
		int direcao;
		boolean entrou = false;
		
		if (posicao.equals("saida")){
			resposta = true;
			return resposta;
		}
		
		direcao = coluna + 1;
		aux = responder.ask("leste");
		
		if ((aux.equals("passagem") || aux.equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(linha) + "," + String.valueOf(direcao))
				&& !resposta){
			posicoesPassadas.add(String.valueOf(linha) + "," + String.valueOf(direcao));
			responder.move("leste");			
			resposta = recursao(linha,direcao,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		direcao = coluna - 1;
		aux = responder.ask("oeste");
		if ((aux.equals("passagem") || aux.equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(linha) + "," + String.valueOf(direcao))
				&& !resposta){
			posicoesPassadas.add(String.valueOf(linha) + "," + String.valueOf(direcao));
			responder.move("oeste");
			resposta = recursao(linha,direcao,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		direcao = linha + 1;
		if ((responder.ask("sul").equals("passagem") || responder.ask("sul").equals("saida")) 
				&& !posicoesPassadas.contains(String.valueOf(direcao) + "," + String.valueOf(coluna))
				&& !resposta){
			posicoesPassadas.add(String.valueOf(direcao) + "," + String.valueOf(coluna));
			responder.move("sul");			
			resposta = recursao(direcao,coluna,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		direcao = linha - 1;
		if ((responder.ask("norte").equals("passagem") || responder.ask("norte").equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(direcao) + "," + String.valueOf(coluna))
				&& !resposta){
			posicoesPassadas.add(String.valueOf(direcao) + "," + String.valueOf(coluna));
			responder.move("norte");			
			resposta = recursao(direcao,coluna,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		
		if(!entrou){
			volta(linha,coluna, linhaAnterior, colunaAnterior);
		}
		
		return resposta;
	}

	public boolean discover() {
		List<String> posicoesPassadas = new ArrayList<String>();
		posicoesPassadas.add("0,0");		

		return recursao(0,0,posicoesPassadas,false,0,0);
	}

}
