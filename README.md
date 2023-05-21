# Manorrock Parrot - a GitHub Workflow Generator

_Note this project has gone into passive mode. See below for an explanation_

[![build](https://github.com/manorrock/parrot/actions/workflows/build.yml/badge.svg)](https://github.com/manorrock/parrot/actions/workflows/build.yml)

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

## What is passive mode?

A project can go into passive mode for either of two reasons. Either the project
is feature complete and no active development is needed. Or the project is no
longer considered a priority. Whatever the reason the end result is the same.

This means:

1. No more scheduled monthly releases.
2. If a bug is filed it is addressed on a best effort basis.
3. No new features are anticipated.
4. Releases are only cut on a needs basis and not more than once a month.
5. If you want your bug or feature to receive attention sponsoring is your best bet.
