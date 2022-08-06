_This project will be archived Jan 1st, 2023 if nobody steps up to take over
 development_

# Manorrock Parrot - a GitHub Workflow Generator

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

## How do I contribute?

See [Contributing](CONTRIBUTING.md)