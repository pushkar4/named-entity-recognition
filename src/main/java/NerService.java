import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class NerService {

  public static void main(String[] args) {

    String sentence =
        "Show me summary of what Pushkar Sharma was working on between Q2 to Q4 at Los Angeles.";

    StanfordCoreNLP pipeline = Pipeline.getPipeline();
    CoreDocument coreDocument = pipeline.processToCoreDocument(sentence);

    System.out.println("\nNamed Entities:");
    List<CoreLabel> coreLabelList = coreDocument.tokens();
    coreLabelList.forEach(
        coreLabel -> {
          String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
          System.out.println(coreLabel.originalText() + " : " + ner);
        });

    System.out.println("\nParts of Speech:");
    coreLabelList.forEach(
        coreLabel -> {
          String pos = coreLabel.tag();
          System.out.println(coreLabel.originalText() + " : " + pos);
        });

    System.out.println("\nEntity Mentions:");
    List<CoreEntityMention> coreEntityMentionList = coreDocument.entityMentions();
    coreEntityMentionList.forEach(
        coreEntityMention -> System.out.println(coreEntityMention.text() + " : " + coreEntityMention.entityType()));
  }
}
