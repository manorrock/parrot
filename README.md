# Manorrock Parrot - a GitHub Workflow Generator

## ⚠️ Project Archival Notice

This project is part of the Manorrock Sustainability Initiative. We are seeking new maintainers to take over this project. If no maintainers step forward by December 31, 2025, this repository will be archived and moved to the manorrock-attic organization.

### Project Timeline

- **Through December 31, 2025**: Repository remains active while seeking maintainers
- **After December 31, 2025**: If no maintainers found, project moves to manorrock-attic
- **Until December 31, 2030**: Project remains available read-only in the attic
- **After December 31, 2030**: Project may be removed

### Interested in Maintaining This Project?

If you're interested in becoming a maintainer, please see [this GitHub issue](https://github.com/manorrock/parrot/issues/100) for details on how to express your interest and what's involved. Note that new maintainers will need to migrate the project to a new namespace, as the Manorrock branding will remain with Manorrock.com.

**After December 31, 2025**: If this project moves to the manorrock-attic, GitHub issues will no longer be available. If you become interested in maintaining this project after it's archived, please email info@manorrock.com with the subject "Revival Request: [Project Name]".

### More Information

For more information about the Manorrock Projects Sustainability Initiative, please visit our [blog post](https://www.manorrock.com/blog/2025/04/14/manorrock_sustainability_initiative.html).

---

[![build](https://github.com/manorrock/parrot/actions/workflows/build.yml/badge.svg)](https://github.com/manorrock/parrot/actions/workflows/build.yml)

## Prerequisites

1. Java 21

## Cron

To set the cron schedule for a given workflow use

<!-- workflow.skip() -->
```
<!-- workflow.cron(* * * * *) -->
```

## Direct only

To only run a snippet when it is in the original Markdown and not coming in 
through workflow.include use the following:

<!-- workflow.skip() -->
```
<!-- workflow.directOnly()

script commands

  -->
```

## (Workflow) dispatch

To allow for triggering the workflow manually use the snippet below:

<!-- workflow.skip() -->
```
<!-- workflow.dispatch() -->
```

## Include

To include content from another file one can use the following:

<!-- workflow.skip() -->
```
<!-- workflow.include(relativeFilename.md) -->
```

## (Always) Run

To always run a specific set of comments one can use the following:

<!-- workflow.skip() -->
```
<!-- workflow.run()

script commands

  -->
```

## Skip

To skip a snippet for inclusion use the following:

<!-- workflow.skip() -->
```
<!-- workflow.skip() -->
```

## How do you generate GitHub workflows?

In the root directory of your GitHub repository the following invocation will
generate the GitHub workflows into the .github/workflows directory.

```
 java -jar parrot.jar --baseDirectory . --outputDirectory .github/workflows
```

## How do I contribute?

See [Contributing](CONTRIBUTING.md)

## Our code of Conduct

See [Code of Conduct](CODE_OF_CONDUCT.md)

## Important notice

Note if you file issues or answer questions on the issue tracker and/or issue 
pull requests you agree that those contributions will be owned by Manorrock.com
and that Manorrock.com can use those contributions in any manner Manorrock.com
so desires.
