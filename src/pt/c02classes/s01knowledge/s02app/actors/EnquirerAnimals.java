package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;
import java.util.List;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	IObjetoConhecimento obj;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		/*A lista de String's servira como um banco de dados das perguntas
		 * ja realizadas pelo entrevistador
		 * */
		List<String> armazenaPergunta = new ArrayList<String>();
		List<String> armazenaResposta = new ArrayList<String>();
		
		IBaseConhecimento bc = new BaseConhecimento();
		bc.setScenario("animals");
		IDeclaracao decl = null;
		
		/*pegando os animais da base de conhecimento*/
		String[] bichos = bc.listaNomes();
		int i = -1, numBichos = bichos.length;
		
		/*laco de repeticao que serve para indicar o animal que o entrevistador
		 * "tem em mente" para fazer as perguntas
		 * */
		do{
			i++;
			
			/*indica o animal esperado pelo entrevistador*/
			obj = bc.recuperaObjeto(bichos[i]);
			decl = obj.primeira();
			
			boolean animalEsperado = true;
			
			while (decl != null && animalEsperado) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();
				
				/*analiza se o entrevistador ja fez a pergunta em questao*/
				if(!armazenaPergunta.contains(pergunta)){
					String resposta = responder.ask(pergunta);
					
					/*adiciona a pergunta e a resposta correspondente dentro da
					 * lista para analises futuras*/
					armazenaPergunta.add(pergunta);
					armazenaResposta.add(resposta);
					
					/*analisa se a resposta obtida do entrevistado bate com a
					 * resposta do animal esperado, passando para a proxima
					 * pergunta caso coincida as respostas*/
					if (resposta.equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
				else{
					/*busca o index da resposta a qual se encontra no index correspondente
					 * da pergunta, uma vez que sao adicionados simultaneamente na lista*/
					int indexResposta = armazenaPergunta.indexOf(pergunta);
					
					if (armazenaResposta.get(indexResposta).equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
			}

			
		}while(i < numBichos && decl != null);
		
		/*verifica se o animal foi encontrado*/		
		boolean acertei = responder.finalAnswer(bichos[i]);

		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;
	}

}
