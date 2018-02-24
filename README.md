# Lucene-Example

A simple document search engine powered by Lucene.

## How to run

[Gradle](https://gradle.org) is used thoroughly in this project. Check out build.gradle by yourself if you have knowledge on Gradle.  
I recommend open it with IDE who supports Gradle, such as [Intellij IDEA](https://www.jetbrains.com/idea/).

```bash
# Make sure gradle is installed.
git clone https://github.com/PointerFLY/Lucene-Example.git
cd Lucene-Example
gradle run
```

## Class Structure

|Class|Functionality|
|:--:|:--|
|FileUtils|Create local folder structures, automatically download and decompress [Cranfield](http://ir.dcs.gla.ac.uk/resources/test_collections/cran/) files. Provide access to all necessary files (Cranfield files, index files).|
|FileParser|A parser used to parse and load Cranfield files into a Java supported data structre. |
|DocumentModel|A simple object-oriented way to represent Cranfield documents.|
|Indexer|Responsible to create all indices.|
|Searcher|Read indices created by Indexer, parse the query string and exectute search. A search will return top hits documents ids.|
|CustomAnalyzer|A analyzer created by myself to compare its performances with other built-in Analyzer in Lucene.|
|Evaluator|Calculate Mean Average Precision, Recall.|
|Main|Entry of program, run different analyzers and scoring models and print results.|

## Analyzer

* StandardAnalyzer  
Built-in Analyzer in Lucene, the most robust one. Filters StandardTokenizer with StandardFilter, LowerCaseFilter and StopFilter, using a list of English stop words.

* CustomAnalyzer
I select differnt tokenizer and filters, combine them, fine-tune the pipeline. Finally choose a analysis pipeline below:

```java
public class CustomAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new ClassicTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(source);
        tokenStream = new EnglishPossessiveFilter(tokenStream);
        tokenStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream = new KStemFilter(tokenStream);
        tokenStream = new PorterStemFilter(tokenStream);
        CharArraySet stopSet = CharArraySet.copy(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
        tokenStream = new StopFilter(tokenStream, stopSet);
        return new TokenStreamComponents(source, tokenStream);
    }
}
```

## Scoring Model

I choose two built-in Lucene scoring model, which is subclasses of Similarity in Lucene.

* ClassicSimilarity  
A implementation of TFIDFSimilarity. It is actually a Vector Sapce Model base on TF-IDF term weighting.

* BM25Similarity  
A TF-IDF-like ranking function used in document retrieval, which is generally considered superior to TF-IDF.

## Performance
Note: MAP and recall are average of 225 queries.

|N/A|StandardAnalyzer|CustomAnalyzer|
|:--:|:--:|:--:|
|Vector Space Model|MAP: 0.4905 Recall: 0.6445|MAP: 0.5038 Recall: 0.6839|
|BM25 Model|MAP: 0.5418 Recall: 0.6826|MAP: 0.5435 Recall: 0.7233|

**Conclusion:**

* BM25 Model outperforms Vector Space Model. 
* CustomAnalyzer outperforms StandardAnalzyer.




