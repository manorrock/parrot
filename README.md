# Manorrock Parrot - a GitHub Workflow Generator

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

## Our code of Conduct

See [Code of Conduct](CODE_OF_CONDUCT.md)
