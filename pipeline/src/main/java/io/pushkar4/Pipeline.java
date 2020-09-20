package io.pushkar4;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {

  private static Properties properties;
  private static StanfordCoreNLP stanfordCoreNLP = null;
  private static final Object lock = new Object();

  private Pipeline() {}

  static {
    properties = new Properties();
    properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, tokensregex, ner");
//    properties.setProperty("sutime.markTimeRanges", "true");
    properties.setProperty("ner.applyFineGrained", "false");
    properties.setProperty("ner.combinationMode", "HIGH_RECALL");
    properties.setProperty("tokensregex.rules", "./pipeline/src/main/resources/ner.rules");
    properties.setProperty(
        "ner.model",
        "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz,"
            + "edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz,"
            + "edu/stanford/nlp/models/ner/english.conll.4class.distsim.crf.ser.gz,");
//            + "./pipeline/src/main/resources/ner-model.ser.gz");
  }

  public static StanfordCoreNLP getPipeline() {
    synchronized (lock) {
      if (stanfordCoreNLP == null) {
        stanfordCoreNLP = new StanfordCoreNLP(properties);
        System.out.println("StanfordCoreNLP instance created.");
      }
    }
    return stanfordCoreNLP;
  }

  public static Properties getProperties() {
    return Pipeline.properties;
  }
}
