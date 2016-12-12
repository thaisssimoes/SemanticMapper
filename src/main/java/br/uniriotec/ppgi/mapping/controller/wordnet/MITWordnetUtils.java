package br.uniriotec.ppgi.mapping.controller.wordnet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.exception.WordnetHelperException;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ILexFile;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.item.SynsetID;
import edu.mit.jwi.morph.WordnetStemmer;

public class MITWordnetUtils {
	private static Logger logger = Logger.getLogger(MITWordnetUtils.class);
	private static IDictionary dict = null;

	/**
	 * Retrieves the first possible stem of a word.
	 * 
	 * @param word
	 * @param pos
	 * @return
	 * @throws IOException
	 */
	public static String stemWord(String word, POS pos) throws IOException {
		WordnetStemmer stemmer = new WordnetStemmer(getDictionary());
		return stemmer.findStems(word, pos).get(0);
	}

	/**
	 * List all synsets under a certain Part-Of-Speech tag (POS), not
	 * discriminating by posible semantic relations it presents. the same as
	 * using "listAllSynsets(pos, null, true)"
	 * 
	 * @param pos
	 *            - the part-of-speech
	 * @return list of all synsets related to the part-of-speech
	 * @throws IOException
	 * @throws WordnetHelperException
	 */
	public static Map<Integer, HashSet<ISynset>> listAllSynsets(POS pos) throws IOException, WordnetHelperException {
		return listAllSynsets(pos, null, false);
	}

	/**
	 * List all synsets under a certain Part-Of-Speech tag (POS), filtering by
	 * the presence or absence of a certain Pointer. If no Pointer is informed
	 * (null) and noPointerRelation is TRUE, no synset will be returned, since
	 * all synsets have at least on pointer relation. Instances are not returned
	 * on the returning object inner lists, since the synsets they instantiate
	 * are already in the lists of ISynset objects.
	 * 
	 * @param pos
	 * @param pointer
	 * @param noPointerRelation
	 * @return Map with LexFile id as index and arrays of ISynset as values
	 * @throws IOException
	 * @throws WordnetHelperException
	 */
	public static Map<Integer, HashSet<ISynset>> listAllSynsets(POS pos, IPointer pointer, boolean noPointerRelation)
			throws IOException, WordnetHelperException {

		/*
		 * *********************************************************************
		 * Checks if the given POS is NULL. if affirmative raises an exception.
		 *********************************************************************/
		if (pos == null) {
			WordnetHelperException e = new WordnetHelperException(
					"A valid Part-Of-Speech tag (POS) value must be informed.");
			logger.error(e);
			throw e;
		}

		/* *********************************************************************/

		// The returned object
		Map<Integer, HashSet<ISynset>> synsetsPerSupersenses = new TreeMap<Integer, HashSet<ISynset>>();

		try {
			// Retrieve iterator over all Synsets under a certain Part-Of-Speech
			// tag.
			IDictionary dict = getDictionary();
			Iterator<ISynset> it = dict.getSynsetIterator(pos);

			while (it.hasNext()) {
				ISynset synset = it.next();

				// Determine the number of related synsets considering if a
				// specific
				// pointer was informed or all pointers should be considered
				int relatedSynsetsCount = 0;
				if (pointer == null) {
					relatedSynsetsCount = synset.getRelatedSynsets().size();
				} else {
					relatedSynsetsCount = synset.getRelatedSynsets(pointer).size();
				}

				/*
				 * Determine if the method should add to the resulting object a
				 * synset that DOES present a relation of the type specified by
				 * the pointer or if it should only add synsets that DOES NOT
				 * present such type of relation.
				 */
				boolean searchAssertion;
				if (noPointerRelation) {
					searchAssertion = relatedSynsetsCount == 0; // Will add
																// every synset
																// that DOES NOT
																// present the
																// relation
				} else {
					searchAssertion = relatedSynsetsCount > 0; // Will add every
																// synset that
																// DOES indeed
																// present the
																// relation
				}

				// Filter synsets adding them to the resulting object
				if (searchAssertion) {

					// If it is an instance (has HYPERNYM_INSTANCE pointer)
					// Ignore it
					if (synset.getRelatedSynsets(Pointer.HYPERNYM_INSTANCE).size() > 0) {
						continue;
					}

					ILexFile lexFile = synset.getLexicalFile();

					// Checks if the resulting map already have the supersense
					// as index
					// if negative instantiate a new index position for that
					// supersense
					if (synsetsPerSupersenses.get(lexFile.getNumber()) == null) {
						synsetsPerSupersenses.put(lexFile.getNumber(), new HashSet<ISynset>());
					}
					// adds the synset to its related supersense
					synsetsPerSupersenses.get(lexFile.getNumber()).add(synset);
				}
			}

		} catch (IOException e) {
			logger.error(e);
			throw new IOException("Exception when retrieving nouns and their supersenses.", e);
		}

		return synsetsPerSupersenses;

	}

	/**
	 * Instantiate a dictionary object used by the MIT JWI api to query the
	 * Wordnet. The dictionary returned is a singleton.
	 * 
	 * @return the wordnet dictionary
	 * @throws IOException
	 */
	private static synchronized IDictionary getDictionary() throws IOException {
		if (dict == null) {
			logger.debug("Instantiating new Wordnet Dictionary");
			// construct the URL to the Wordnet dictionary directory
			String wnhome = System.getenv("WNHOME");

			// Verify if the WNHOME environment variable is set and print warn
			// if not
			if (wnhome.trim().equals("")) {
				logger.error("The WNHOME variable is not set. Set it to indicate your Wordnet home directory.");
			}

			String path = wnhome + File.separator + "dict";
			URL url = new URL("file", null, path);
			// build the dictionary object and open it
			dict = new Dictionary(url);
			dict.open();
		}

		return dict;
	}

	/**
	 * Determine whether the modifier is present on the synset hypernym tree
	 * (between the synset and the supersense).
	 * 
	 * @param synsetID
	 *            - The synset ID as it appears in WordNet
	 * @param modifierID
	 *            - The modifiers ID as it appears in WordNet
	 * @return
	 * @throws IOException
	 */
	public static boolean hasModifierSynset(String synsetID, String modifierID) throws IOException {
		IDictionary dict = getDictionary();
		ISynset synset = dict.getSynset(SynsetID.parseSynsetID(synsetID));
		ISynset modifier = dict.getSynset(SynsetID.parseSynsetID(modifierID));

		// While the current synset being queried has a hypernym
		if (modifier != null) {
			while (synset.getRelatedSynsets(Pointer.HYPERNYM).size() > 0) {
				ISynsetID hypernymID = synset.getRelatedSynsets(Pointer.HYPERNYM).get(0);
				ISynset hypernym = dict.getSynset(hypernymID);

				// Checks if the hypernym directly above is the modifier.
				// If it is, return true
				if (hypernym.toString().equals(modifier.toString())) {
					return true;
				}

				// Update current synset to the hypernym, so it goes up on the
				// tree.
				synset = hypernym;
			}
		} else {
			logger.warn("Problems with modifier ID: " + modifierID);
		}

		// At this point all hypernyms have been checked and none of the
		// are the modifier given as parameter, so returns false.
		return false;
	}

	/**
	 * It checks if the synset is a holonym of other synset. Being a Holonym
	 * means thar it is a part of something else. The method takes into account
	 * the HOLONYM_SUBSTANCE, HOLONYM_MEMBER and HOLONYM_PART pointers.
	 * 
	 * @param wordnetID
	 * @return
	 * @throws IOException
	 */
	public static boolean isHolonym(String wordnetID) throws IOException {
		IDictionary dict = getDictionary();
		ISynset synset = dict.getSynset(SynsetID.parseSynsetID(wordnetID));

		int holonymRelations = 0;
		holonymRelations += synset.getRelatedSynsets(Pointer.HOLONYM_SUBSTANCE).size();
		holonymRelations += synset.getRelatedSynsets(Pointer.HOLONYM_MEMBER).size();
		holonymRelations += synset.getRelatedSynsets(Pointer.HOLONYM_PART).size();

		if (holonymRelations > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if the synset is a related to a verb, being a Deverbal Noun. When
	 * related it presents the DERIVATIONALLY_RELATED pointer.
	 * 
	 * @param wordnetID
	 * @return
	 * @throws IOException
	 */
	public static boolean isDerivationallyRelated(String wordnetID) throws IOException {
		IDictionary dict = getDictionary();
		ISynset synset = dict.getSynset(SynsetID.parseSynsetID(wordnetID));

		// Since DERIVATIONALLY_RELATED is a Lexical Pointer, it is only used
		// for IWord objects
		// It means that the words should be iterated.
		for (IWord word : synset.getWords()) {
			// If the word has a DERIVATIONALLY_RELATED relation...
			if (word.getRelatedWords(Pointer.DERIVATIONALLY_RELATED).size() > 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Checks if at least one of the words on the synset is homograph to at
	 * least one of the words in the verb synset that the noun is derivationally
	 * related to.
	 * 
	 * @param wordnetID
	 * @return
	 * @throws IOException
	 */
	public static boolean isHomographToVerb(String wordnetID) throws IOException {
		IDictionary dict = getDictionary();
		ISynset nounSynset = dict.getSynset(SynsetID.parseSynsetID(wordnetID));

		// For each word on the synset check for derivationally related forms
		for (IWord nounSynsetWord : nounSynset.getWords()) {
			List<IWordID> relatedWordIDs = nounSynsetWord.getRelatedWords(Pointer.DERIVATIONALLY_RELATED);
			// Foreach related verb...
			for (IWordID relatedWordID : relatedWordIDs) {
				IWord relatedWord = dict.getWord(relatedWordID);
				// ... check if the related word lemma is homograph to the noun
				// synset
				// word lemma being iterated and that the related word is a
				// VERB...
				boolean isVerb = (relatedWord.getPOS() == POS.VERB);
				boolean isHomograph = (relatedWord.getLemma().equalsIgnoreCase(nounSynsetWord.getLemma()));
				if (isVerb && isHomograph) {
					// If an homograph is found, ends the method
					return true;
				}
			}
		}

		// no homograph foun in any iteration.
		return false;

	}

}
