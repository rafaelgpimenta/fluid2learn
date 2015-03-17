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
	// voltando ate uma posicao em que o responder tenha outra possibilidade de movimento alem da que ele ja executou
	public void volta(int linha, int coluna,int linhaAnterior,int colunaAnterior){
		// se o responder tinha se movimentado a leste
		if(linha == linhaAnterior && coluna > colunaAnterior)
			// volta para oeste
			responder.move("oeste");
		// se ele tinha se movimentado a oeste
		else if(linha == linhaAnterior && coluna < colunaAnterior)
			//volta a leste
			responder.move("leste");
		// se ele tinha se movimentado ao sul
		else if(linha > linhaAnterior)
			// volta ao norte
			responder.move("norte");
		else
			// se nao, ele tinha se movimentado ao norte e volta ao sul
			responder.move("sul");
	}
	public boolean recursao(int linha, int coluna, List<String> posicoesPassadas, boolean resposta,
			int linhaAnterior,int colunaAnterior) {
		// descobrindo o que ha na posicao atual do responder
		String posicao = responder.ask("aqui");
		int direcao;
		boolean entrou = false;
		
		// se o responder ja encontrou a saida
		if (posicao.equals("saida")){
			resposta = true;
			return resposta;
		}
		
		direcao = coluna + 1;
		// descobrindo o que tem a leste do responder
		// se a leste o responder tenha uma passagem ou a saida e no caso de passagem ele nao tenha visitado esta posicao a leste
		// e tambem sem que ele ja tenha encontrado a saida
		if ((responder.ask("leste").equals("passagem") || responder.ask("leste").equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(linha) + "," + String.valueOf(direcao))
				&& !resposta){
			// adicionando a posicao do movimento na lista
			posicoesPassadas.add(String.valueOf(linha) + "," + String.valueOf(direcao));
			// movendo o responder para o leste
			responder.move("leste");
			// encontrando novas posicoes possiveis para o responder andar depois do movimento executado
			resposta = recursao(linha,direcao,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		direcao = coluna - 1;
		//  se a oeste o responder tenha uma passagem ou a saida e no caso de passagem ele nao tenha visitado esta posicao a oeste
		// e tambem sem que ele ja tenha encontrado a saida
		if ((responder.ask("oeste").equals("passagem") || responder.ask("oeste").equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(linha) + "," + String.valueOf(direcao))
				&& !resposta){
			// adicionando a posicao do movimento na lista
			posicoesPassadas.add(String.valueOf(linha) + "," + String.valueOf(direcao));
			// movendo o responder para o oeste
			responder.move("oeste");
			// encontrando novas posicoes possiveis para o responder andar depois do movimento executado
			resposta = recursao(linha,direcao,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		//  se ao sul o responder tenha uma passagem ou a saida e no caso de passagem ele nao tenha visitado esta posicao ao sul
		// e tambem sem que ele ja tenha encontrado a saida
		direcao = linha + 1;
		if ((responder.ask("sul").equals("passagem") || responder.ask("sul").equals("saida")) 
				&& !posicoesPassadas.contains(String.valueOf(direcao) + "," + String.valueOf(coluna))
				&& !resposta){
			// adicionando a posicao do movimento na lista
			posicoesPassadas.add(String.valueOf(direcao) + "," + String.valueOf(coluna));
			// movendo o responder para o sul
			responder.move("sul");
			// encontrando novas posicoes possiveis para o responder andar depois do movimento executado
			resposta = recursao(direcao,coluna,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		direcao = linha - 1;
		//  se ao norte o responder tenha uma passagem ou a saida e no caso de passagem ele nao tenha visitado esta posicao ao norte
		// e tambem sem que ele ja tenha encontrado a saida
		if ((responder.ask("norte").equals("passagem") || responder.ask("norte").equals("saida"))
				&& !posicoesPassadas.contains(String.valueOf(direcao) + "," + String.valueOf(coluna))
				&& !resposta){
			// adicionando a posicao do movimento na lista
			posicoesPassadas.add(String.valueOf(direcao) + "," + String.valueOf(coluna));
			// movendo o responder para o norte
			responder.move("norte");
			// encontrando novas posicoes possiveis para o responder andar depois do movimento executado
			resposta = recursao(direcao,coluna,posicoesPassadas,resposta,linha,coluna);
			entrou = resposta;
		}
		// caso responder nao achou a saida pelo caminho executado
		if(!entrou){
			// voltando as posicoes passadas pelo responder ate uma que ele tenha outra possibilidade de movimento 
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
