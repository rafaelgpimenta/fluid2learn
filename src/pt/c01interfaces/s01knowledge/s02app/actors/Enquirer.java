/*Trabalho de Java
 * Autores:
 * 	Rafael Gois Pimenta RA:157055
 * 	Jose Pedro V. do Nascimento Filho RA:155981
 * */
package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;
import java.util.ArrayList;
import java.util.List;


public class Enquirer implements IEnquirer
{
	IObjetoConhecimento obj;

	public Enquirer()
	{
	}


	@Override
	public void connect(IResponder responder)
	{
		/*A lista de String's servira como um banco de dados das perguntas
		 * ja realizadas pelo entrevistador
		 * */
		List<String> armazenaPergunta = new ArrayList<String>();
		List<String> armazenaResposta = new ArrayList<String>();
		
		IBaseConhecimento bc = new BaseConhecimento();
		IDeclaracao decl = null;
		
		/*pegando os animais da base de conhecimento*/
		String[] bichos = bc.listaNomes();
		int i = -1;
		
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

			
		}while(i < bichos.length && decl != null);
		
		/*verifica se o animal foi encontrado*/		
		boolean acertei = responder.finalAnswer(bichos[i]);

		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
