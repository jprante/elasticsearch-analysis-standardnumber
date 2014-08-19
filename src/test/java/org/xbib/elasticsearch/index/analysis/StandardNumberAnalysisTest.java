package org.xbib.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;
import org.elasticsearch.index.analysis.NamedAnalyzer;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.xbib.elasticsearch.plugin.analysis.standardnumber.AnalysisStandardNumberPlugin;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class StandardNumberAnalysisTest {

    @Test
    public void testStandardNumberAnalyzer() throws IOException {
        NamedAnalyzer namedAnalyzer = createAnalysisService().analyzer("standardnumber");
        String[] expected = new String[]{
                "Die",
                "ISBN",
                "von",
                "Elasticsearch",
                "in",
                "Action",
                "lautet",
                "9781617291623",
                "EAN 9781617291623",
                "GTIN 9781617291623",
                "978-1-61729-162-3"
        };
        assertSimpleTSOutput(namedAnalyzer.tokenStream("content", "Die ISBN von Elasticsearch in Action lautet 9781617291623"), expected);
    }

    @Test
    public void testPunctuation() throws IOException {
        NamedAnalyzer namedAnalyzer = createAnalysisService().analyzer("standardnumber");
        String[] expected = new String[]{
                "ISBN:",
                "978-3-12-606004-2.",
                "GTIN 9783126060042",
                "978-3-12-606004-2",
                "9783126060042"
        };
        assertSimpleTSOutput(namedAnalyzer.tokenStream("content",
                "ISBN: 978-3-12-606004-2."), expected);
    }

    /**
     * Avoid nested ZDB-ID in ISBN like this
     * 3826628225
     * 978-3-8266-2822-1
     * 9783826628221
     * ZDB 826-6
     * ZDB 8266
     * by boundary matching for ZDBID.
     *
     * @throws IOException
     */
    @Test
    public void testISBNWithEmbeddedZDB() throws IOException {
        NamedAnalyzer namedAnalyzer = createAnalysisService().analyzer("standardnumber");
        String[] expected = new String[]{
                "ISBN",
                "3-8266-2822-5",
                "GTIN 3826628225",
                "3826628225",
                "978-3-8266-2822-1",
                "9783826628221"

        };
        assertSimpleTSOutput(namedAnalyzer.tokenStream("content",
                "ISBN 3-8266-2822-5"), expected);
    }

    /**
     * "linux" is not an ISSN
     *
     * @throws IOException
     */
    @Test
    public void testNonISSN() throws IOException {
        NamedAnalyzer namedAnalyzer = createAnalysisService().analyzer("standardnumber");
        String[] expected = new String[]{
                "linux"
        };
        assertSimpleTSOutput(namedAnalyzer.tokenStream("content",
                "linux"), expected);
    }

    private AnalysisService createAnalysisService() {
        Settings settings = ImmutableSettings.settingsBuilder().build();

        Index index = new Index("test");

        Injector parentInjector = new ModulesBuilder().add(new SettingsModule(settings),
                new EnvironmentModule(new Environment(settings)),
                new IndicesAnalysisModule())
                .createInjector();

        AnalysisModule analysisModule = new AnalysisModule(settings, parentInjector.getInstance(IndicesAnalysisService.class));
        new AnalysisStandardNumberPlugin().onModule(analysisModule);

        Injector injector = new ModulesBuilder().add(
                new IndexSettingsModule(index, settings),
                new IndexNameModule(index),
                analysisModule)
                .createChildInjector(parentInjector);

        return injector.getInstance(AnalysisService.class);
    }

    private void assertSimpleTSOutput(TokenStream stream, String[] expected) throws IOException {
        stream.reset();
        CharTermAttribute termAttr = stream.getAttribute(CharTermAttribute.class);
        assertNotNull(termAttr);
        int i = 0;
        while (stream.incrementToken()) {
            assertTrue(i < expected.length);
            assertEquals(termAttr.toString(), expected[i++], "expected different term at index " + i);
        }
        assertEquals(i, expected.length, "not all tokens produced");
        stream.close();
    }
}
