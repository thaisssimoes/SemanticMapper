package br.uniriotec.ppgi.mapping.controller.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.SemanticType;
import br.uniriotec.ppgi.mapping.model.exception.WordnetHelperException;
import edu.mit.jwi.item.ILexFile;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class Runner {
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public void run(){
		try{
			//List only nouns that DOES NOT present a hyponym relation to other nouns
			Map<String, ArrayList<ISynset>> synsetsPerSupersenses = MITWordnetUtils.listAllSynsets(POS.NOUN, Pointer.HYPONYM, true);
			
			for(Entry<String, ArrayList<ISynset>> e : synsetsPerSupersenses.entrySet()){
				logger.info("Supersense: "+e.getKey()+"| -- Total de synsets: "+e.getValue().size());
			}
			
			//Map each synset to a semantic type
			Map<ISynset, Pair<ILexFile, SemanticType>> mappedSynsets;
			
		}catch(WordnetHelperException e){
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		
		
		
		
//		Session session =  HibernateUtil.getSessionFactory().openSession();
//		 
//		SemanticType st = new SemanticType();
//		st.setName("test");
//		st.setDefinition("descri'c~ao");
// 
//		//realizando operação para salvar no banco
//		session.beginTransaction();
//		session.save(st);
//		session.getTransaction().commit();
//		session.close();
		
		
	}
	
}
