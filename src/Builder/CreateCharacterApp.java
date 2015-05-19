package Builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateCharacterApp {

public static final String root_dir = "bin/textFiles/";
	
	public static StringBuilder getFileContent(String file_path)
	{
		StringBuilder contents = new StringBuilder();

		try {
			BufferedReader input =  new BufferedReader(new FileReader(file_path));
			try {
				String line = null;
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return contents;
	}

	public static void main(String[] args) 
	{
		Director dir = new Director();
		
		CreateSoldierBuilder soldier = new CreateSoldierBuilder();		
		dir.construct(soldier);
		System.out.println(soldier.getResult());
		
		CreateStudentBuilder student = new CreateStudentBuilder();		
		dir.construct(student);
		System.out.println(student.getResult());
		
		CreateThiefBuilder thief = new CreateThiefBuilder();		
		dir.construct(thief);
		System.out.println(thief.getResult());
		
	}
}
