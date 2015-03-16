package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;


public class OrchestratorInit {

	private static Scanner ler;

	public static void main(String[] args) {
		
		System.out.println("Qual tipo de desafio você quer: Animals ou Maze ?");
		ler = new Scanner(System.in);
		String desafio = ler.next();
		ler.nextLine();
		if (desafio.equalsIgnoreCase("Animals")) {
			System.out.println("Qual o animal a ser adivinahdo ?");
			String animal = ler.nextLine();
			
			IEnquirer enq;
			IResponder resp;
			IStatistics stat;
			System.out.println("Enquirer com " + animal + "...");
			stat = new Statistics();
			resp = new ResponderAnimals(stat, animal);
			enq = new EnquirerAnimals();
			enq.connect(resp);
			enq.discover();
			System.out.println("----------------------------------------------------------------------------------------\n");

			
		} else {
			System.out.println("Qual o nome do labirinto a ser usado ?");
			String labirinto = ler.nextLine();
			
			IEnquirer enq;
			IResponder resp;
			IStatistics stat;
			System.out.println("Enquirer com " + labirinto + "...");
			stat = new Statistics();
			resp = new ResponderMaze(stat, labirinto);
			enq = new EnquirerMaze();
			enq.connect(resp);
			enq.discover();
			System.out.println("----------------------------------------------------------------------------------------\n");

		}
			

	}

}