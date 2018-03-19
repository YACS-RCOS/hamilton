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

## Pipeline


## Sources



### Source Format

The source format borrows much of its structure from json:api, with a few modifications. The most significant difference is in the "relationship object". The Hamilton "relationship object" allows for relationships defined by arbitrary fields.

Each source endpoint MUST have the top-level objects `meta` and `data`.

The `meta` field MUST contain the field `source`. The `source` field containts meta-information on the source itself. It MUST be an object and MUST have the following fields:
- `type`: the type of records that `data` will contain
- `name`: a unique identifier for this source\
    Note: Multiple endpoints of the same source SHOULD share the same `name`.

The `data` field contains the records themselves. It MUST be an array containing zero or more objects. We will call these objects Records.

Each record in MUST have the following fields:
- `type`: the type of the record\
    Note: `type` MUST be the same value for each record in `data`
- `id`: an arbitrary identifier for the record\
    Note: `id` MUST be unique within the scope of the source\
    Note: `id` SHOULD have some semantic meaning and be human readable
- `attributes`: the attributes for the record\
    Note: `attributes` MUST be an object
    Note: `attributes` MAY contain any valid json values
    Note: `attributes` MUST NOT contain any member named `relationships`, `links`, `removed`, `id`, `uuid`

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
                    "data": { "attributes": { "shortname": "CSCI" } }
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
                    "data": { "attributes": { "shortname": "201809" } }
                },
                "course": {
                    "data": { "attributes": { "shortname": "CSCI 1200" } }
                }
            }
        }
    ]
}
```

## Contributing

We encourage you to create issues and contribute to Hamilton! To contribute fork the repo, comment on an issue, and submit a pull request to the master branch. Build checks and code reviews are required before merging. Once checks have passed, a project owner will merge the changes.

## Code of Conduct

We strive to create and maintain an inclusive, welcoming and safe community for all. Please review our Code of Conduct in `CODE_OF_CONDUCT.md` before participating in the project.

## License

Hamilton is currently released without a license! Oh no! The project will soon be released with an OSI approved license, once it has reached a higher level of maturity (very soon!).