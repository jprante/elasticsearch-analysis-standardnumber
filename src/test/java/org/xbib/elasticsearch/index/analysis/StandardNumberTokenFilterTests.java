package org.xbib.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
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
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.junit.Test;
import org.xbib.elasticsearch.plugin.analysis.standardnumber.StandardnumberPlugin;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StandardNumberTokenFilterTests {

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
                "978-1-61729-162-3"
        };
        AnalysisService analysisService = createAnalysisService();
        NamedAnalyzer analyzer = analysisService.analyzer("standardnumber");
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
                "1-933988-17-7",
                "1933988177",
                "978-1-933988-17-7",
                "9781933988177"
        };
        AnalysisService analysisService = createAnalysisService();
        NamedAnalyzer analyzer = analysisService.analyzer("standardnumber");
        assertSimpleTSOutput(analyzer.tokenStream("content", source), expected);
    }

    private AnalysisService createAnalysisService() {
        InputStream in = getClass().getResourceAsStream("/org/xbib/elasticsearch/index/analysis/standardnumber.json");
        Settings settings = Settings.settingsBuilder()
                .put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT)
                .put("path.home", System.getProperty("path.home"))
                .loadFromStream("settings", in)
                .build();
        Index index = new Index("test");
        Injector parentInjector = new ModulesBuilder()
                .add(new SettingsModule(settings),
                        new EnvironmentModule(new Environment(settings)))
                .createInjector();
        AnalysisModule analysisModule = new AnalysisModule(settings, parentInjector.getInstance(IndicesAnalysisService.class));
        new StandardnumberPlugin(settings).onModule(analysisModule);
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
            assertEquals(expected[i++], termAttr.toString());
        }
        assertEquals(expected.length, i);
        stream.close();
    }
}
