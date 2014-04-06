
package org.xbib.elasticsearch.index.mapper.standardnumber;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.mapper.FieldMapperListener;
import org.elasticsearch.index.mapper.Mapper;
import org.elasticsearch.index.mapper.MapperParsingException;
import org.elasticsearch.index.mapper.MergeContext;
import org.elasticsearch.index.mapper.MergeMappingException;
import org.elasticsearch.index.mapper.ObjectMapperListener;
import org.elasticsearch.index.mapper.ParseContext;
import org.elasticsearch.index.mapper.core.StringFieldMapper;
import org.xbib.elasticsearch.index.analysis.standardnumber.Detector;
import org.xbib.standardnumber.StandardNumber;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;

import static org.elasticsearch.index.mapper.MapperBuilders.stringField;

public class StandardNumberMapper implements Mapper {

    public static final String CONTENT_TYPE = "standardnumber";

    public static class Builder extends Mapper.Builder<Builder, StandardNumberMapper> {

        private StringFieldMapper.Builder contentBuilder;

        private StringFieldMapper.Builder stdnumBuilder = stringField("standardnumber");

        private Detector detector;

        public Builder(String name, Detector detector) {
            super(name);
            this.detector = detector;
            this.contentBuilder = stringField(name);
            this.builder = this;
        }

        public Builder content(StringFieldMapper.Builder content) {
            this.contentBuilder = content;
            return this;
        }

        public Builder stdnum(StringFieldMapper.Builder stdnum) {
            this.stdnumBuilder = stdnum;
            return this;
        }

        @Override
        public StandardNumberMapper build(BuilderContext context) {
            context.path().add(name);
            StringFieldMapper contentMapper = contentBuilder.build(context);
            StringFieldMapper stdnumMapper = stdnumBuilder.build(context);
            context.path().remove();
            return new StandardNumberMapper(name, detector, contentMapper, stdnumMapper);
        }
    }

    public static class TypeParser implements Mapper.TypeParser {

        private Detector detector;

        public TypeParser(Detector detector) {
            this.detector = detector;
        }

        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public Mapper.Builder parse(String name, Map<String, Object> node, ParserContext parserContext)
                throws MapperParsingException {
            StandardNumberMapper.Builder builder = new Builder(name, detector);
            for (Map.Entry<String, Object> entry : node.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldNode = entry.getValue();

                if (fieldName.equals("fields")) {
                    Map<String, Object> fieldsNode = (Map<String, Object>) fieldNode;
                    for (Map.Entry<String, Object> fieldsEntry : fieldsNode.entrySet()) {
                        String propName = fieldsEntry.getKey();
                        Object propNode = fieldsEntry.getValue();

                        if (name.equals(propName)) {
                            builder.content((StringFieldMapper.Builder) parserContext.typeParser("string").parse(name,
                                    (Map<String, Object>) propNode, parserContext));
                        } else if ("standardnumber".equals(propName)) {
                            builder.stdnum((StringFieldMapper.Builder) parserContext.typeParser("string").parse(propName,
                                    (Map<String, Object>) propNode, parserContext));
                        }
                    }
                }
            }

            return builder;
        }
    }

    private final String name;
    private final Detector detector;
    private final StringFieldMapper contentMapper;
    private final StringFieldMapper stdnumMapper;

    public StandardNumberMapper(String name, Detector detector,
                                StringFieldMapper contentMapper,
                                StringFieldMapper stdnumMapper) {
        this.name = name;
        this.detector = detector;
        this.contentMapper = contentMapper;
        this.stdnumMapper = stdnumMapper;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void parse(ParseContext context) throws IOException {
        String content = null;

        XContentParser parser = context.parser();
        XContentParser.Token token = parser.currentToken();

        if (token == XContentParser.Token.VALUE_STRING) {
            content = parser.text();
        }

        context.externalValue(content);
        contentMapper.parse(context);

        try {
            Collection<StandardNumber> stdnums = detector.detect(content);
            for (StandardNumber stdnum : stdnums) {
                context.externalValue(stdnum.normalizedValue());
                stdnumMapper.parse(context);
            }
        } catch(NumberFormatException e) {
            context.externalValue("unknown");
            stdnumMapper.parse(context);
        }
    }

    @Override
    public void merge(Mapper mergeWith, MergeContext mergeContext) throws MergeMappingException {
    }

    @Override
    public void traverse(FieldMapperListener fieldMapperListener) {
        contentMapper.traverse(fieldMapperListener);
        stdnumMapper.traverse(fieldMapperListener);
    }

    @Override
    public void traverse(ObjectMapperListener objectMapperListener) {
    }

    @Override
    public void close() {
        contentMapper.close();
        stdnumMapper.close();
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(name);
        builder.field("type", CONTENT_TYPE);

        builder.startObject("fields");
        contentMapper.toXContent(builder, params);
        stdnumMapper.toXContent(builder, params);
        builder.endObject();

        builder.endObject();
        return builder;
    }
}