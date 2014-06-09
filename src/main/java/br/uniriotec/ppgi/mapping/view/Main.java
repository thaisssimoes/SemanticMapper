package br.uniriotec.ppgi.mapping.view;

import java.io.IOException;
import java.util.Scanner;

import br.uniriotec.ppgi.mapping.controller.EvaluationRunner;


/**
 * This is the first class called when the application runs.
 * It prompts the user for a confirmation for dropping the
 * database schema, if it confirms the continuation the class
 * calls the controller layer to start the mapping procedure.
 * 
 * @author felipe
 *
 */
public class Main {
	private static Scanner scan = new Scanner(System.in);
    private static String userInput = null;
    
    
    /**
     * The main method of the application
     * 
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {
		
		/*
		 * Warns the user that the Database schema will be dropped and
		 * all current data will be lost. Aske if He/She wishes to proceed.
		 */
		System.out.println("===============================================");
		System.out.println("==    Supersense to Semantic Type Mapper     ==");
		System.out.println("===============================================");
		System.out.println("**ATENTION** If currently there is a database schema created and populated IT WILL BE DROPPED! ");
		System.out.println("Make sure to backup all important data before running the application.");
		System.out.print("Do you wish to continue running? [Y/N]: ");
		userInput = scan.nextLine();
		
		/* ****************************************
		 * 		If YES, run the application 
		 * ****************************************/
		if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")){
			
			System.out.println("*** Starting Supersense Mapper ***");
			EvaluationRunner runner = new EvaluationRunner();
			runner.run();
			System.out.println("*** Finishing Supersense Mapper ***");
			
			
		/* ****************************************
		 * 				If NO, abort!
		 * ****************************************/
		}else{
			System.out.println("\n*** Application aborted by user! ***");
		}
        
		
		
	}

}
