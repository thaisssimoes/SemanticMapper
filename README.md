Supersense to SemanticType Mapper
=================================

This application is a command line tool to map synsets along with their respective Supersenses[1][2] (Lexical Files) to the Semantic Types described by Dixon (2005)[3]. This code is intended to be used along with a local installation of Princeton's WordNet (US-English).

----------

Table of Contents
-----------------

 1. Why mapping Supersenses to Semantic Types?
 2. Preparing the environment
 3. Running the application
 4. After Running
 5. License


Why mapping Supersenses to Semantic Types?
-------------------------------------------

Supersenses are high level generic classes intended to serve as top hypernyms to WordNet's synsets. They are derived from the work by Ciaramita and Johnson (2003)[1] and Ciaramita and Altun (2006)[2], and have been implemented in WordNet since early versions. Currently WordNet describe [45 Supersenses](http://wordnet.princeton.edu/wordnet/man/lexnames.5WN.html) according to the distribution shown by the table below:

Open Word Class  | Quantity of Supersenses
---------------: | :-----
Noun             | 26
Verb             | 15
Adjective        | 3
Adverb           | 1

On the other hand, another kind of high level classes have been proposed, not only grouping words by common meaning, but also describing some characteristics they share. These classes are the Semantic Types, proposed by Dixon (2005)[3]. Semantic Types are able to indicate whether a word refers to something that should be counted by mass instead of unit (Semantic Type "*Quantity*") or something that refers to an abstract concept (Semantic Type "*Other*").

As proven by other works such as Castro *et.al.* (2011)[4], the identification of which Semantic type is related to a word may provide enough semantic to associate a word to a construct of a well-founded ontology language such as OntoUML, based on the Unified Foundational Ontology by Guizzardi (2005)[5].

This mapping tool is being developed under as a part of the research I'm currently developing as a M.SC. candidate at the UNIRIO (Universidade Federal do Estado do Rio de Janeiro). For further information please refer to my [website](http://www.fleao.com.br).


Preparing the environment
--------------------------

The application uses a local installation of [Princeton WordNet](http://wordnet.princeton.edu/), and does it through [MIT JWI](http://projects.csail.mit.edu/jwi/) API which requires  a environment variable (WNHOME) to be set.

To install WordNet you can simply refer to the website project and download the version that best fits your Operational System. remember to set the WNHOME enviroment variable to point at WordNet's home folder. if you are under Linux/MacOSX using verson 3.0 that would probably be `/usr/local/WordNet-3.0`.

Once the repository has been cloned, import it into [Eclipse IDE](https://www.eclipse.org/) and don't forget to run a `maven update`, since [Maven](https://maven.apache.org/) is used to manage all the dependencies.

You must also have a MySQL database installed and running. To run the tool the user must configure the resource file **hibernate.cfg.xml** under `src/main/resources` folder, indicating the URL, user and password for the MySQL database where the output data will be saved. The application will generate the schema itself on first run. 

**Observation**: If any warnings concerning foreign key show up, ignore them, starting from the second run they won't happen again (For some reason Hibernate can't decide the right order for creating tables and respect the foreign keys).

```xml
<hibernate-configuration>
   <session-factory>

      <property name="connection.url">jdbc:mysql://simba:3306/supersense_mapping</property>
      <property name="connection.username">mapper</property>
      <property name="connection.password">1234</property>

      <!-- Other parameters omitted -->

   </session-factory>
</hibernate-configuration>

```

The database schema DDL script is provided on the repository root and should be used to create the database prior running the system, since even though Hibernate is able to generate the schema, some initial data must be persisted before the tool can be used.




Running the application
------------------------

Once the environment is prepared the tool can be run simply by calling the application Main class, under `br.uniriotec.ppgi.mapping.view.Main`. It is recommended to export a Runnable JAR from eclipse setting the Main class as the responsible for running the application. Once the jar is generated, it can be simply called by the command line:

```sh
java -jar SupersenseMapping.jar
```

Optionally the user may add the `-e` parameter when invoking the JAR to activate **Evaluation Mode**. This way, instead of applying the mapping rule to all synsets (noun, leaf synsets) only samples for each supersense are mapped, along with some intentional wrong mappings for additional synsets, that can be used as control group when evaluating the mapping rules. To this mode simple use the following command:

```sh
java -jar SupersenseMapping.jar -e
```

When evaluation mode is activted the application loads additional information from the database prior running the mapping, such as z-value for cnofidence level and confidence interval, which are used when determining the sample sizes for each supersense mapping evaluation.
 


After Running
--------------

Once the application has run, the database schema will have been generated, relating the sampled synsets to semantic types. if you wish to run the following step of the mapping evaluation described in my [Master's Thesis](http://www.fleao.com.br/researches), create the tables **User** and **Evaluation** through the DDL scripts on the file *other_tables.sql* in the project's root folder (same place as this README file).

The evaluation can be conducted through another application, a PHP webapp also available as a repository at the [MappingEvaluation](https://github.com/felipeleao/MappingEvaluation) project.


System Requirements
-------

You should be able to have the system up and running, along with your Mysql database, on any operational system with at least 4GB of RAM and 2.0GHz processor. A big part of the application consists of I/O, so an SSD drive is welcome.


License
-------

This application is provided under the MIT License (check the license.txt file).


------


  [1]: Ciaramita, Massimiliano, and Mark Johnson. **Supersense Tagging of Unknown Nouns in Wordnet**. Proceedings of the 2003 Conference on Empirical Methods in Natural Language Processing (EMNLP), 2003, 168–75.

  [2]: Ciaramita, Massimiliano, and Yasemin Altun. **Broad-Coverage Sense Disambiguation and Information Extraction with a Supersense Sequence Tagger**. In Proceedings of the 2006 Conference on Empirical Methods in Natural Language Processing, 594–602. EMNLP ’06. Stroudsburg, PA, USA: Association for Computational Linguistics, 2006. http://dl.acm.org/citation.cfm?id=1610075.1610158.

  [3]: Dixon, R. M. W. **A Semantic Approach to English Grammar**. 2nd ed. Oxford University Press, USA, 2005.

  [4]: Castro, Lucia, Fernanda Baião, and Giancarlo Guizzardi. **A Semantic Oriented Method for Conceptual Data Modeling in ontoUML Based on Linguistic Concepts**. In Proceedings of the 30th International Conference on Conceptual Modeling, 486–94. ER’11. Berlin, Heidelberg: Springer-Verlag, 2011. http://dl.acm.org/citation.cfm?id=2075144.2075195.

  [5]:Guizzardi, Giancarlo. **Ontological Foundations for Structural Conceptual Models**. Vol. 15. Fundamental Research Series. The Netherlands: Telematica Insituut, 2005.
