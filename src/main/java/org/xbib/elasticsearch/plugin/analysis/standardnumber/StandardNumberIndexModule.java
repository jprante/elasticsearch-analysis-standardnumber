package org.xbib.elasticsearch.plugin.analysis.standardnumber;

import org.elasticsearch.common.inject.AbstractModule;

public class StandardNumberIndexModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RegisterStandardNumberType.class).asEagerSingleton();
    }
}
