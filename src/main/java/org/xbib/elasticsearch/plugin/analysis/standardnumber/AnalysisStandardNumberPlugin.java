package org.xbib.elasticsearch.plugin.analysis.standardnumber;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;
import org.xbib.elasticsearch.index.analysis.ConcatTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.StandardNumberTokenFilterFactory;

public class AnalysisStandardNumberPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "analysis-standardnumber";
    }

    @Override
    public String description() {
        return "Analysis plugin for standard numbers";
    }

    public void onModule(AnalysisModule module) {
        module.addTokenFilter("standardnumber", StandardNumberTokenFilterFactory.class);
        module.addTokenFilter("concat", ConcatTokenFilterFactory.class);
    }

}
