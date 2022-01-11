# README

Text does not appear in the workflow.

## CRON

This generates a cron entry in the workflow:

<!-- workflow.cron(0 1 * * 2) -->

## Skip

What's inside the skip is not added to the workflow:

<!-- workflow.skip() 
This is workflow.skip() 
-->

## Run

What's inside the run method does not appear in the workflow but is executed

<!-- workflow.run() 
This is workflow.run() 
-->

## Direct Only

Appears in the workflow as is

<!-- workflow.directOnly()
This is workflow.directOnly() 
  -->

## Include

This includes the file into the generated workflow

<!-- workflow.include(./to_be_included.md) -->

This included file doesn't exit, a comment should be generated in the workflow

<!-- workflow.include(./dummy.md) -->

## Shell

Anything in a shell is added to the workflow:

```shell
  This is a shell
```

