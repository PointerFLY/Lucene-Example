import org.apache.lucene.analysis.StopwordAnalyzerBase;

public class CustomAnalyzer extends StopwordAnalyzerBase {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return null;
    }
}