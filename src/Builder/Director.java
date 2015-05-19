package Builder;

public class Director {
	public void construct(CreateCharacterInterface character)
	{
		character.criarHead();	
		character.criarBody();	
		character.criarLegs();	
	}
}
