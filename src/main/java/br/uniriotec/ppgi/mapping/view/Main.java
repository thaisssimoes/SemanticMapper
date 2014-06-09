package br.uniriotec.ppgi.mapping.view;

import java.util.Iterator;
import java.util.Scanner;

import br.uniriotec.ppgi.mapping.controller.DefaultRunner;
import br.uniriotec.ppgi.mapping.controller.EvaluationRunner;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;


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
     * @throws Exception 
     */
	public static void main(String[] args) throws Exception {
		
		//Parse parameters from command line
		JSAPResult parameters = parseParameters(args);
		
		
		/*
		 * Warns the user that the Database schema will be dropped and
		 * all current data will be lost. Aske if He/She wishes to proceed.
		 */
		System.out.println("===============================================");
		System.out.println("==    Supersense to Semantic Type Mapper     ==");
		if(parameters.getBoolean("evaluation")){
			System.out.println("==   		(EVALUATION MODE)	     ==");
		}
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
			if(parameters.getBoolean("evaluation")){
				EvaluationRunner runner = new EvaluationRunner();
				runner.run();
			}else{
				DefaultRunner runner = new DefaultRunner();
				runner.run();
			}
			System.out.println("*** Finishing Supersense Mapper ***");
			
			
		/* ****************************************
		 * 				If NO, abort!
		 * ****************************************/
		}else{
			System.out.println("\n*** Application aborted by user! ***");
		}
        
		
		
	}
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	private static JSAPResult parseParameters(String[] args) throws Exception{
		
		JSAP jsap = new JSAP();
		
		
		/* ****************************************
		 * 		Create Parametized options
		 * ****************************************/
		
		//Switch to alternate between Preprocessing mode and Learning mode
		Switch evaluation = new Switch("evaluation")
		    .setShortFlag('e') 
		    .setLongFlag("evaluation");
		evaluation.setHelp("Runs the application in Evaluation Mode, which generates data for evaluating the mapping rules.");
    	
			
        
        Switch swHelp = new Switch("help")
			.setShortFlag('h')
			.setLongFlag("help");
			swHelp.setHelp("Shows this help info.");
		
			
			
			
		/* ****************************************
		 * 			Register Parameters
		 * ****************************************/	
		
		try {
			jsap.registerParameter(evaluation);
			jsap.registerParameter(swHelp);
		} catch (Exception e) {
			throw new Exception("FATAL Exception when registering JSAP Parameters. "
					+ e.getMessage());
		}
		

		
		/* ****************************************
		 * 			Parser arguments
		 * ****************************************/
		
        JSAPResult config = jsap.parse(args);  
        
        //Check if its a call for Help
        if(config.getBoolean("help")){
        	System.err.println("This HELP further explains parameters that should be passed to the application.");
        	System.err.println("Flags indicated with \"()\" are required while flags indicated with \"[]\" are optional.\n");
        	System.err.println(jsap.getHelp());
			System.exit(1);
			
		//Check if its an usage mistake	
        }else if(!config.success()) {
        	for (Iterator errs = config.getErrorMessageIterator(); errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }    
        	System.err.println("Usage: java -jar SupersenseMapping.jar "+jsap.getUsage());
        	System.exit(1);
        
        //Otherwise, return the parsed parameters 
        }else{
        	return config; 
        }
        
        return null;
        
	}
	

}
