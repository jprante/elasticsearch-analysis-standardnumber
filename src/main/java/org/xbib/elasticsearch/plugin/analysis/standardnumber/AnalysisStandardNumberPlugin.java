
package org.xbib.elasticsearch.plugin.analysis.standardnumber;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;
import org.xbib.elasticsearch.index.analysis.ConcatTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.standardnumber.StandardNumberAnalyzerProvider;
import org.xbib.elasticsearch.index.analysis.standardnumber.StandardNumberTokenFilterFactory;

import java.util.Collection;

import static org.elasticsearch.common.collect.Lists.newArrayList;

public class AnalysisStandardNumberPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "analysis-standardnumber-"
                + Build.getInstance().getVersion() + "-"
                + Build.getInstance().getShortHash();
    }

    @Override
    public String description() {
        return "Analysis plugin for standard numbers";
    }

    public void onModule(AnalysisModule module) {
        module.addAnalyzer("standardnumber", StandardNumberAnalyzerProvider.class);
        module.addTokenFilter("standardnumber", StandardNumberTokenFilterFactory.class);
        module.addTokenFilter("concat", ConcatTokenFilterFactory.class);
    }

    @Override
    public Collection<Class<? extends Module>> indexModules() {
        Collection<Class<? extends Module>> modules = newArrayList();
        modules.add(StandardNumberIndexModule.class);
        return modules;
    }
}
