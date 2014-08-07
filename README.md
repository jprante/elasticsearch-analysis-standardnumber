
# Standardnumber Analysis Plugin for Elasticsearch

Standardnumber is an analysis plugin for [Elasticsearch](http://github.com/elasticsearch/elasticsearch).

What are standard numbers? Standard numbers are globally unique character sequences, often digits, with
rules, domains, and canonical forms defined by standardization bodies or organizations like the
International Organisation for Standardization (ISO).
For using valid standard numbers only, often check digits are used. The main
purpose of standard numbers is the identifcation of items.

Because standard numbers may appear in some literal variants, the canonical form must be deduced from the input
so standard numbers can be compared for equivalence.

With the standard number analysis, you can use a token filter for finding standard numbers and index
them in canonical form plus all their valid variants.

Available standard numbers:

ARK Archival Resource Key

DOI ISO 26324 Digital Object Identifier System

EAN European Article Number

GTIN Global Trade Item Number

IBAN ISO 13616 International Bank Account Number

ISAN ISO 15706 International Standard Audiovisual Number

ISBN ISO 2108 International Standard Book Number

ISMN ISO 10957 International Standard Music Number

ISNI ISO 27729 International Standard Name Identifier

ISSN ISO 3297 International Standard Serial Number

ISTC ISO 21047 International Standard Text Code

ISWC ISO 15707 International Standard Musical Work Code

ORCID Open Researcher and Contributor ID (compatible to ISNI)

PPN Pica Productie Nummer

SICI Serial Item and Contribution Identifier

UPC ISO 15420 Universal Product Code

ZDB Zeitschriftendatenbank ID

## Versions

| Elasticsearch version    | Plugin      | Release date |
| ------------------------ | ----------- | -------------|
| 1.3.1                    | 1.3.0.2     | Aug  7, 2014 |
| 1.3.1                    | 1.3.0.1     | Aug  6, 2014 |
| 1.3.1                    | 1.3.0.0     | Jul 30, 2014 |
| 1.2.1                    | 1.2.1.0     | Jun  6, 2014 |

## Installation

    ./bin/plugin -install standardnumber -url http://xbib.org/repository/org/xbib/elasticsearch/plugin/elasticsearch-standardnumber/1.3.0.2/elasticsearch-standardnumber-1.3.0.2-plugin.zip

Do not forget to restart the node after installing.

## Checksum

| File                                             | SHA1                                     |
| -------------------------------------------------| -----------------------------------------|
| elasticsearch-standardnumber-1.3.0.2-plugin.zip  | 0241a9c57238c7ed769d38a8372ac4ad1226d701 |
| elasticsearch-standardnumber-1.3.0.1-plugin.zip  | d142c896b1631b119e457ba9a8190d648d8f9005 |
| elasticsearch-standardnumber-1.3.0.0-plugin.zip  | 72e799ee309c6c0a05d230e400bfff9fdcc70d4d |
| elasticsearch-standardnumber-1.2.1.0.zip         | 3c881daed9a5355e8c37c2112ef80c7bb0feef97 |

## Project docs

The Maven project site is available at [Github](http://jprante.github.io/elasticsearch-standardnumber)

## Issues

All feedback is welcome! If you find issues, please post them at [Github](https://github.com/jprante/elasticsearch-standardnumber/issues)

# Example

In the settings, set up a token filter of type "standardnumber"::

    {
       "index" : {
          "analysis" : {
              "filter" : {
                  "standardnumber" : {
                      "type" : "standardnumber"
                  }
              },
              "analyzer" : {
                  "standardnumber" : {
                      "tokenizer" : "whitespace",
                      "filter" : [ "standardnumber", "unique" ]
                  }
              }
          }
       }
    }

By using such an analyzer, the content ``Die ISBN von Elasticsearch in Action lautet 9781617291623``
will be tokenized into::

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

The formatting of ISBN-13 was added as an extra token to the stream.

It is recommended to add the [unique token filter](http://www.elasticsearch.org/guide/reference/index-modules/analysis/unique-tokenfilter.html)
to skip tokens that occur more than once.

## Another example

The content ``Die ISBN von Lucene in Action lautet 1-9339-8817-7.`` will be tokenized into::

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

ISBN-10 were the only form valid before Januar 1, 2007. Such old ISBNs will be reformatted, validated, and
normalized into ISBN-10/ISBN13 variant forms, which are added as extra tokens to the token stream.

## Search example

    curl -XPUT '0:9200/stdnum' -d '
    {
        "mappings": {
            "_default_" : {
                "properties": {
                    "num" : { "type" : "standardnumber" }
                 }
            }
        }
    }
    '

    curl -XPOST '0:9200/stdnum/test/1' -d '
    {
        "num" : "1-9339-8817-7"
    }
    '

    curl -XPOST '0:9200/stdnum/test/_search' -d '
    {
        "query" : {
            "match" : {
                "_all" : "1933988177"
            }
        }
    }
    '

## Analyzer and token filter

With this plugin it is possible to use an analyzer `standardnumber` or a filter `standardnumber`,
which is equivalent to::

          "filter" : {
              "standardnumber" : {
                  "type" : "standardnumber"
              }
          },
          "analyzer" : {
              "standardnumber" : {
                  "tokenizer" : "whitespace",
                  "filter" : [ "standardnumber", "unique" ]
              }
          }

# License

Standardnumber Analysis Plugin for Elasticsearch

Copyright (C) 2013 Jörg Prante

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.