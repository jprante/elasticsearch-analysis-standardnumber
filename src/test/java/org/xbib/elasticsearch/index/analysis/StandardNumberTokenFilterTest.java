package org.xbib.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.analysis.NamedAnalyzer;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xbib.elasticsearch.plugin.analysis.standardnumber.AnalysisStandardNumberPlugin;

import java.io.IOException;


public class StandardNumberTokenFilterTest extends Assert {

    private final static ESLogger logger = ESLoggerFactory.getLogger("test");

    NamedAnalyzer analyzer;

    @BeforeClass
    public void create() {
        AnalysisService analysisService = createAnalysisService();
        analyzer = analysisService.analyzer("standardnumber");
    }

    @Test
    public void testISBN1() throws IOException {
         String source = "Die ISBN von Elasticsearch in Action lautet 9781617291623";

        String[] expected = new String[] {
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
                "ISBN 978-1-61729-162-3",
                "ISBN 9781617291623"
        };
        assertSimpleTSOutput(analyzer.tokenStream("content", source), expected);
    }

    @Test
    public void testISBN2() throws IOException {
        String source = "Die ISBN von Lucene in Action lautet 1-9339-8817-7.";

        String[] expected = new String[] {
                "Die",
                "ISBN",
                "von",
                "Lucene",
                "in",
                "Action",
                "lautet",
                "1-9339-8817-7.",
                "ISBN 1-933988-17-7",
                "ISBN 1933988177",
                "ISBN 978-1-933988-17-7",
                "ISBN 9781933988177"
        };
        assertSimpleTSOutput(analyzer.tokenStream("content", source), expected);
    }

    private AnalysisService createAnalysisService() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .loadFromClasspath("org/xbib/elasticsearch/index/analysis/standardnumber.json").build();

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

    private static void assertSimpleTSOutput(TokenStream stream,
                                             String[] expected) throws IOException {
        stream.reset();
        CharTermAttribute termAttr = stream.getAttribute(CharTermAttribute.class);
        assertNotNull(termAttr);
        int i = 0;
        while (stream.incrementToken()) {
            assertTrue(i < expected.length);
            assertEquals(termAttr.toString(), expected[i++], "expected different term at index " + i);
        }
        assertEquals(i, expected.length, "not all tokens produced");
    }
}
