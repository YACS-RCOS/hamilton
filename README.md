<img src="docs/images/logo.png" alt="Hamilton">

<!-- TODO: Badges go here-->

---

## Table Of Contents

- [Introduction](#introduction)
- [Pipeline](#pipeline)
- [Sources](#sources)
- [Contributing](#contributing)
- [Code of Conduct](#code-of-conduct)
- [License](#license)

---

## Introduction
Hamilton is, put mostly simply, a batteries-included streaming data pipeline. It consumes data from a number of sources, and produces a combined result.

What makes Hamilton special is its ability to identify and combine, and update records across different sources in an automatic and configurable way. Hamilton also supports and handles relationships between records, and can automatically resolve relationships based on arbitrary attributes. Hamilton is strictly schema-less, and makes no assumptions about the contents of the records or the structure of the relationships.

Hamilton is build on Kafka, and makes heavy use of the Kafka Streams and Kafka Connect APIs. Kafka affords Hamilton the ability to consume both streaming and purely-stateful data sources, and provides an API specification for stateful data sources.

Any arbitrary data source can therefore be used with Hamilton by creating an adapter microservice in the programmer's language of choice

---

## Pipeline

---

## Sources

A Hamilton instance can consume any number of sources. A source is meant to be an adapter between a stateful data source and the Hamilton pipeline. These sources may contain entirely disjoint data sets, or may contain overlapping or incomplete collections and records. The Hamilton pipeline will automatically resolve and amalgamate all of the collections and records from each of the sources.

To allow for this, and to simplify development, all sources must comply with the given specification (below). As long as the source complies with the specification, it can be written in any language or framework, and may interface with any external services or dependencies.

### Specification

The source format borrows much of its structure from json:api, with a few modifications. The most significant difference is in the "relationship object". The Hamilton "relationship object" allows for relationships defined by arbitrary fields.

A source SHOULD represent a logical or physical source of data. A source MUST contain one or more HTTP endpoints. Each endpoint MUST respond with JSON. The JSON response MUST have the top-level objects `meta` and `data`.

The `meta` field MUST contain the field `source`. The `source` field containts meta-information on the source itself. It MUST be an object and MUST have the following fields:
- `type`: the type of records that `data` will contain
- `name`: a unique identifier for this source\
    Note: Multiple endpoints of the same source SHOULD share the same `name`.

The `data` field contains the records themselves. It MUST be an array containing zero or more objects. We will call these objects Records.

Each record in MUST have the following fields:
- `type`: the type of the record
    - `type` MUST be the same value for each record in `data`
- `id`: an arbitrary identifier for the record
    - `id` MUST be unique within the scope of the source
    - `id` SHOULD have some semantic meaning and be human readable
- `attributes`: the attributes for the record ("attributes object")
    - `attributes` MUST be an object
    - `attributes` MAY contain any valid json values
    - `attributes` MUST NOT contain any member named `relationships`, `links`, `removed`, `id`, `uuid`
- `relationships`: describes how the record is related to other records
    - unlike `json:api`, `relationships` in hamilton are defined by `attributes` objects. This allows the programmer to specify a relationship based on any field of the related record, allowing for relationships between records that may have originiated from different or multiple sources.
    - `relationships` MUST be an object
    - `relationships` MUST contain an object for each relation ("relationship object")
    - Each "relationship object" SHOULD have the singularized type of the related object as a key
- "relationship object"
    - A "relationship object" MUST contain one object named `data`
    - `data` MUST contain a member `type`, which MUST be the type of the related record
    - `data` MUST contain an "attributes object"
    - The "attributes object" MUST contain at least one member
    - The members of the "attributes object" SHOULD identify exactly one record of the related type

### Examples

```
{
    "meta": {
        "source": {
            "type": "courses",
            "name": "catalog"
        }
    },
    "data": [
        {
            "type": "courses",
            "id": "courses/CSCI/1200",
            "attributes": {
                "shortname": "CSCI 1200",
                "number": "1200",
                "longname": "Data Structures",
                ...
            },
            "relationships": {
                "subject": {
                    "data": {
                        type: "subjects",
                        "attributes": { "shortname": "CSCI" }
                    }
                }
            }
        }
    ]
}
```

```
{
    "meta": {
        "source": {
            "type": "sessions",
            "name": "sis"
        }
    },
    "data": [
        {
            "type": "sessions",
            "id": "sessions/201809",
            "attributes": {
                "shortname": "201809",
                "longname": "Fall 2018"
            }
        }
    ]
}
```

```
{
    "meta": {
        "source": {
            "type": "sections",
            "name": "sis"
        }
    },
    "data": [
        {
            "type": "sections",
            "id": "sections/CSCI/1200/201809/01",
            "attributes": {
                "shortname": "01",
                "seats": 10,
                "seats_taken": 8,
                "status": "open"
            },
            "relationships": {
                "session": {
                    "data": {
                        "type": "sessions",
                        "attributes": { "shortname": "201809" }
                    }
                },
                "course": {
                    "data": {
                        "type": "courses",
                        "attributes": { "shortname": "CSCI 1200" }
                    }
                }
            }
        }
    ]
}
```

---

## Contributing

We encourage you to create issues and contribute to Hamilton! To contribute fork the repo, comment on an issue, and submit a pull request to the master branch. Build checks and code reviews are required before merging. Once checks have passed, a project owner will merge the changes.

---

## Code of Conduct

We strive to create and maintain an inclusive, welcoming and safe community for all. Please review our Code of Conduct in `CODE_OF_CONDUCT.md` before participating in the project.

---

## License

Hamilton is currently released without a license! Oh no! The project will soon be released with an OSI approved license, once it has reached a higher level of maturity (very soon!).

---