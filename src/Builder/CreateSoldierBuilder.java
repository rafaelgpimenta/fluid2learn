package Builder;

public class CreateSoldierBuilder implements CreateCharacterInterface {
	private static final String tipo = "soldier";
	private StringBuilder character = new StringBuilder();
	
	@Override
	public void criarHead() {		
		String file = CreateCharacterApp.root_dir + tipo + "/head.txt";	
		character.append(CreateCharacterApp.getFileContent(file));		
	}

	@Override
	public void criarBody() {
		String file = CreateCharacterApp.root_dir + tipo + "/body.txt";	
		character.append(CreateCharacterApp.getFileContent(file));
		
	}

	@Override
	public void criarLegs() {
		String file = CreateCharacterApp.root_dir + tipo + "/legs.txt";	
		character.append(CreateCharacterApp.getFileContent(file));
		
	}
	
	public StringBuilder getResult(){
		
		return character;
	}
	
}
