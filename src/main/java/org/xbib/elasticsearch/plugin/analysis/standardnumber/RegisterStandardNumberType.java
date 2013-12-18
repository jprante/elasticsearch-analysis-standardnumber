package org.xbib.elasticsearch.plugin.analysis.standardnumber;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.AbstractIndexComponent;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.settings.IndexSettings;
import org.xbib.elasticsearch.index.analysis.Detector;
import org.xbib.elasticsearch.index.mapper.standardnumber.StandardNumberMapper;

public class RegisterStandardNumberType extends AbstractIndexComponent {

    @Inject
    public RegisterStandardNumberType(Index index, @IndexSettings Settings indexSettings,
                                      MapperService mapperService, Detector detector) {
        super(index, indexSettings);
        mapperService.documentMapperParser().putTypeParser("stdnum",
                new StandardNumberMapper.TypeParser(detector));
    }
}
