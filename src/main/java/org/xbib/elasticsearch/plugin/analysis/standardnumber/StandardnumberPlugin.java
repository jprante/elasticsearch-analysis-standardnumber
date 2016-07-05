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
package org.xbib.elasticsearch.plugin.analysis.standardnumber;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;
import org.xbib.elasticsearch.index.analysis.standardnumber.StandardnumberAnalysisBinderProcessor;
import org.xbib.elasticsearch.index.mapper.standardnumber.StandardnumberMapperModule;
import org.xbib.elasticsearch.index.mapper.standardnumber.StandardnumberMapperTypeParser;

import java.util.ArrayList;
import java.util.Collection;

public class StandardnumberPlugin extends Plugin {

    private final Settings settings;

    private final StandardnumberMapperTypeParser standardnumberMapperTypeParser;
    @Inject
    public StandardnumberPlugin(Settings settings) {
        this.settings = settings;
        this.standardnumberMapperTypeParser = new StandardnumberMapperTypeParser();
    }

    @Override
    public String name() {
        return "standardnumber";
    }

    @Override
    public String description() {
        return "Standard number analysis and mapper for Elasticsearch";
    }

    public void onModule(AnalysisModule module) {
        if (settings.getAsBoolean("plugins.standardnumber.enabled", true)) {
            module.addProcessor(new StandardnumberAnalysisBinderProcessor());
        }
    }

    @Override
    public Collection<Module> nodeModules() {
        Collection<Module> modules = new ArrayList<>();
        if (settings.getAsBoolean("plugins.standardnumber.enabled", true)) {
            modules.add(new StandardnumberMapperModule(standardnumberMapperTypeParser));
        }
        return modules;
    }
}
