package io.pushkar4;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {

  private static Properties properties;
  private static StanfordCoreNLP stanfordCoreNLP;

  private Pipeline() {}

  static {
    properties = new Properties();
    properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
    properties.setProperty("sutime.markTimeRanges", "true");
    properties.setProperty("ner.combinationMode", "HIGH_RECALL");
    properties.setProperty(
        "ner.model",
        "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz,"
            + "edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz,"
            + "edu/stanford/nlp/models/ner/english.conll.4class.distsim.crf.ser.gz,"
            + "/Users/psharma/GithubProjects/named-entity-recognition/train-models/ner-model.ser.gz");
  }

  public static StanfordCoreNLP getPipeline() {
    if (stanfordCoreNLP == null) {
      stanfordCoreNLP = new StanfordCoreNLP(properties);
    }
    return stanfordCoreNLP;
  }
}
