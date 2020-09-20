package io.pushkar4;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.Env;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.*;

public class NerService {

  private static StanfordCoreNLP pipeline;
  private static String sentence;
  private static Map<String, Set<String>> entityMap;

  public static void main(String[] args) {

    sentence = "Show me summary of what John Smith was working on between 2020 Q2 and Q4 at Los Angeles for frontend portfolio. Also show me the cloudytech goals and my server investments. Can I get a report for ABC team? What is going on with the vesper theme?";
//    sentence = "show me all resources working in Chicago";
//    sentence = "show me report of our frontend team working on our agile goals in agile portfolio.";
//    sentence = "show me report of all investments.";
//    sentence = "show me report for frontend team working on our agile portfolio.";

    pipeline = Pipeline.getPipeline();
    entityMap = new HashMap<>();

    showEntityMentions();
    showMatchedExpressions();
//    showNamedEntities();
    showEntityMap();
  }

  private static void showMatchedExpressions() {
    System.out.println("\n==============================================================================");
    System.out.println("Matched Expressions:");

    Annotation sentenceAnnotation = new Annotation(sentence);
    pipeline.annotate(sentenceAnnotation);

    // get the rules files
    String[] rulesFiles = Pipeline.getProperties().getProperty("tokensregex.rules").split(",");

    // set up an environment with reasonable defaults
    Env env = TokenSequencePattern.getNewEnv();

    // build the CoreMapExpressionExtractor
    CoreMapExpressionExtractor
            extractor = CoreMapExpressionExtractor.createExtractorFromFiles(env, rulesFiles);

    List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentenceAnnotation);
    for (MatchedExpression expression : matchedExpressions) {
      System.out.println(expression.getValue() + " : " + expression.getText());
      putInEntityMap(expression.getValue().get().toString(), expression.getText());
    }

    System.out.println("\n==============================================================================");
    System.out.println("Named Entities:");
    for (CoreLabel coreLabel : sentenceAnnotation.get(CoreAnnotations.TokensAnnotation.class)) {
      System.out.println(coreLabel.word() + " : " + coreLabel.ner() + " : " + coreLabel.tag());
      putInEntityMap(coreLabel.ner(), coreLabel.word());
    }
  }

  private static void showEntityMentions() {
    System.out.println("\n==============================================================================");
    System.out.println("Entity Mentions:");
    CoreDocument coreDocument = pipeline.processToCoreDocument(sentence);
    List<CoreEntityMention> coreEntityMentionList = coreDocument.entityMentions();
    coreEntityMentionList.forEach(
            coreEntityMention -> {
              System.out.println(coreEntityMention.text() + " : " + coreEntityMention.entityType());
              putInEntityMap(coreEntityMention.entityType(), coreEntityMention.text());
            });
  }

  private static void showNamedEntities() {
    System.out.println("\n==============================================================================");
    System.out.println("Named Entities:");
    CoreDocument coreDocument = pipeline.processToCoreDocument(sentence);
    List<CoreLabel> coreLabelList = coreDocument.tokens();
    coreLabelList.forEach(
            coreLabel -> {
              String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
              String pos = coreLabel.tag();
              System.out.println(coreLabel.originalText() + " : " + ner + " : " + pos);
            });
  }

  private static void putInEntityMap(String key, String value) {
    Set<String> set = entityMap.get(key);
    if (set == null)
      set = new LinkedHashSet<>();

    set.add(value);
    entityMap.put(key, set);
  }

  private static void showEntityMap() {
    System.out.println("\n==============================================================================");
    System.out.println("Entity Map:");
    for (Map.Entry<String,Set<String>> entry : entityMap.entrySet())
      System.out.println(entry.getKey() + " : " + entry.getValue());
  }
}
