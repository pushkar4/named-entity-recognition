import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class NerExample {

  public static void main(String[] args) {

    String sentence = "What is Pushkar working on in Q3?";

    StanfordCoreNLP pipeline = Pipeline.getPipeline();
    CoreDocument coreDocument = new CoreDocument(sentence);
    pipeline.annotate(coreDocument);

    List<CoreLabel> coreLabelList = coreDocument.tokens();

    coreLabelList
        .forEach(
            coreLabel -> {
              String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
              System.out.println(coreLabel.originalText() + " : " + ner);
            });
  }
}
