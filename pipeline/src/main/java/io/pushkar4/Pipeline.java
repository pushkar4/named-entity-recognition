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
    }

    public static StanfordCoreNLP getPipeline() {
        if (stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
