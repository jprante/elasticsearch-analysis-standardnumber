/*
 * Copyright (C) 2014 Jörg Prante
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * The interactive user interfaces in modified source and object code
 * versions of this program must display Appropriate Legal Notices,
 * as required under Section 5 of the GNU Affero General Public License.
 *
 */
package org.xbib.elasticsearch.index.analysis.standardnumber;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.xbib.elasticsearch.index.mapper.standardnumber.StandardnumberService;

public class StandardnumberTokenFilterFactory extends AbstractTokenFilterFactory  {

    private final Settings settings;

    private final Injector injector;

    @Inject
    public StandardnumberTokenFilterFactory(Index index,
                                            IndexSettingsService indexSettingsService,
                                            @Assisted String name,
                                            @Assisted Settings settings,
                                            Injector injector) {
        super(index, indexSettingsService.indexSettings(), name, settings);
        this.injector = injector;
        this.settings = settings;
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        StandardnumberService service = injector.getInstance(StandardnumberService.class);
        return new StandardnumberTokenFilter(tokenStream, service, settings);
    }
}
