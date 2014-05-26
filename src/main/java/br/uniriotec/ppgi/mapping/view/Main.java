package br.uniriotec.ppgi.mapping.view;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.run.Runner;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) throws IOException {
		
		logger.info("*** Starting Supersense Mapper ***");
		
		Runner runner = new Runner();
		runner.run();
		
		logger.info("*** Finishing Supersense Mapper ***");
	}

}
