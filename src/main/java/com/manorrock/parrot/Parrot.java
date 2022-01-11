/*
 *  Copyright (c) 2002-2021, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.parrot;

import com.manorrock.yaml.YAMLLiteralBlock;
import com.manorrock.yaml.YAMLWriter;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ast.HtmlCommentBlock;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.manorrock.parrot.model.Cron;
import com.manorrock.parrot.model.Job;
import com.manorrock.parrot.model.On;
import com.manorrock.parrot.model.Push;
import com.manorrock.parrot.model.Workflow;
import com.manorrock.parrot.model.WorkflowDispatch;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.WARNING;

/**
 * The GitHub Workflow Generator.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Parrot {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = System.getLogger(Parrot.class.getName());

    /**
     * Stores the base directory.
     */
    private File baseDirectory;
    
    /**
     * Stores the default runs-on.
     */
    private String runsOn;

    /**
     * Stores the output directory.
     */
    private File outputDirectory = new File(".");

    /**
     * Main method.
     *
     * @param arguments the command line arguments.
     */
    public static void main(String[] arguments) {
        Parrot gwg = new Parrot();
        gwg.parseArguments(arguments);
        gwg.run();
    }

    /**
     * Parse the command line arguments.
     *
     * @param arguments the arguments.
     */
    private void parseArguments(String[] arguments) {
        if (arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i].equals("--baseDirectory")) {
                    baseDirectory = new File(arguments[i + 1]);
                }
                if (arguments[i].equals("--outputDirectory")) {
                    outputDirectory = new File(arguments[i + 1]);
                }
                if (arguments[i].equals("--runsOn")) {
                    runsOn = arguments[i + 1];
                }
            }
        }
    }

    /**
     * Run the generator.
     */
    private void run() {
        processDirectory(baseDirectory);
    }

    /**
     * Process the given directory.
     *
     * @param directory the directory.
     */
    private void processDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals("README.md")) {
                    processFile(file);
                } else if (file.isDirectory()) {
                    processDirectory(file);
                }
            }
        }
    }

    /**
     * Process the given file.
     *
     * @param file the file to process.
     */
    void processFile(File file) {
        LOGGER.log(INFO, "--- Processing file: " + file);
        ParrotContext context = new ParrotContext();
        if (runsOn != null) {
            context.setRunsOn(runsOn);
        }
        context.setCurrentFile(file);
        context.getSnippets().addAll(loadFile(file));
        context.setOutputFilename(generateOutputFilename(file));
        Workflow workflow = generateWorkflow(context);
        StringWriter stringWriter = new StringWriter();
        try {
            YAMLWriter writer = new YAMLWriter(stringWriter);
            writer.writeObject(workflow);
            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        LOGGER.log(INFO, stringWriter.toString());
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        try {
            File outputFile = new File(outputDirectory, context.getOutputFilename());
            FileWriter outputWriter = new FileWriter(outputFile);
            outputWriter.write(stringWriter.toString());
            outputWriter.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    String generateOutputFilename(File file) {
        return getRelativeFilename(file).replaceAll("/", "_").replaceAll("\\.", "_") + ".yml";
    }

    /**
     * Collect the Markdown nodes.
     *
     * @param file the file to include.
     */
    private List loadFile(File file) {
        LOGGER.log(INFO, "--- Loading file - " + file.toPath().normalize());
        ArrayList<Node> nodes = new ArrayList<>();
        try {
            MutableDataSet options = new MutableDataSet();
            Parser parser = Parser.builder(options).build();
            Document document = parser.parseReader(new FileReader(file));
            Iterator<Node> iterator = document.getChildIterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                if (node instanceof HtmlCommentBlock) {
                    HtmlCommentBlock comment = (HtmlCommentBlock) node;
                    nodes.add(comment);
                }
                if (node instanceof FencedCodeBlock) {
                    FencedCodeBlock code = (FencedCodeBlock) node;
                    if (code.getInfo().toString().equals("shell")) {
                        nodes.add(code);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return nodes;
    }

    /**
     * Geenerate the worflow.
     *
     * @param context the context.
     * @return the GitHub workflow.
     */
    private Workflow generateWorkflow(ParrotContext context) {
        LOGGER.log(INFO, "--- Generating GitHub workflow");
        Workflow workflow = new Workflow();
        workflow.setName(getRelativeFilename(context.getCurrentFile()));
        HashMap<String, Object> jobs = new HashMap<>();
        Job job = new Job();
        job.setRunsOn("ubuntu-latest");
        jobs.put("validate", job);
        workflow.setJobs(jobs);
        LinkedHashMap<String, Object> login = new LinkedHashMap<>();
        login.put("uses", "azure/login@v1");
        HashMap<String, Object> with = new HashMap<>();
        with.put("creds", "${{ secrets.AZURE_CREDENTIALS }}");
        with.put("allow-no-subscriptions", "true");
        login.put("with", with);
        LinkedHashMap<String, Object> checkout = new LinkedHashMap<>();
        checkout.put("uses", "actions/checkout@v2");
        job.getSteps().add(login);
        job.getSteps().add(checkout);
        HashMap<String, Object> run = new HashMap<>();
        YAMLLiteralBlock literalBlock = new YAMLLiteralBlock();
        run.put("run", literalBlock);
        StringBuilder builder = new StringBuilder();
        context.setWorkflow(workflow);
        context.setScriptBuilder(builder);
        while (context.getSnippets().size() > 0) {
            processSnippet(context);
            if (context.getSnippets().isEmpty() && !context.getSnippetStack().isEmpty()) {
                context.setSnippets((ArrayList) context.getSnippetStack().pop());
                File processedFile = (File) context.getFileStack().peek();
                LOGGER.log(INFO, "End processing - " + processedFile.toPath().normalize());
                context.getFileStack().pop();
            }
        }
        literalBlock.setString(context.getScriptBuilder().toString());
        job.getSteps().add(run);
        return workflow;
    }

    /**
     * Get relative filename.
     *
     * @param file the file.
     * @return the relative filename.
     */
    private String getRelativeFilename(File file) {
        return file.getAbsolutePath().substring(baseDirectory.getAbsolutePath().length() + 1);
    }

    /**
     * Process the comment snippet (aka. HTML comment block).
     *
     * @param context the context.
     * @param comment the HTML comment block.
     */
    private void processCommentSnippet(ParrotContext context, HtmlCommentBlock comment) {
        String firstLine = comment.getContentChars(0, 1).toString();
        if (firstLine.endsWith("\n")) {
            firstLine = firstLine.substring(0, firstLine.length() - 1);
        }
        Pattern pattern = Pattern.compile("<!-- workflow\\.(\\w+)\\((.*)\\).*");
        Matcher matcher = pattern.matcher(firstLine);
        if (matcher.matches()) {
            String action = matcher.group(1);
            if (action.equals("cron")) {
                processCronDirective(context, matcher.group(2));
            } else if (action.equals("directOnly")) {
                processDirectOnlySnippet(context, comment);
            } else if (action.equals("dispatch")) {
                processWorkflowDispatchDirective(context);
            } else if (action.equals("filename")) {
                processOutputFilename(context, matcher.group(2));
            } else if (action.equals("include")) {
                processIncludeSnippet(context, matcher.group(2));
            } else if (action.equals("includeOnce")) {
                processIncludeOnceSnippet(context, matcher.group(2));
            } else if (action.equals("name")) {
                processWorkflowName(context, matcher.group(2));
            } else if (action.equals("pushPath")) {
                processPushPath(context, matcher.group(2));
            } else if (action.equals("run")) {
                processRunSnippet(context, comment);
            } else if (action.equals("runsOn")) {
                processRunsOn(context, matcher.group(2));
            } else if (action.equals("skip")) {
                processSkip(context);
            }
        }
    }

    /**
     * Process the cron directive.
     *
     * <p>
     * This directive adds a cron schedule for the workflow.
     * </p>
     *
     * @param context the context.
     * @param cron the cron schedule.
     */
    private void processCronDirective(ParrotContext context, String cron) {
        if (context.getSnippetStack().isEmpty()) {
            On on = context.getWorkflow().getOn();
            if (on.getSchedule() == null) {
                on.setSchedule(new ArrayList<>());
            }
            on.getSchedule().add(new Cron(cron));
        }
    }

    /**
     * Process the directOnly snippet.
     *
     * <p>
     * A directOnly snippet is only added if the snippet is not part of an
     * include. If the snippet is processed while an include is being processed
     * it will be skipped.
     * </p>
     *
     * @param context the context.
     * @param comment the HtmlCommentBlock.
     */
    private void processDirectOnlySnippet(ParrotContext context, HtmlCommentBlock comment) {
        if (context.getSnippetStack().isEmpty()) {
            List<BasedSequence> content = comment.getContentLines();
            content.remove(0);
            content.remove(content.size() - 1);
            Iterator<BasedSequence> iterator = content.iterator();
            while (iterator.hasNext()) {
                BasedSequence sequence = iterator.next();
                context.getScriptBuilder().append(sequence.toString());
            }
        }
    }

    /**
     * Process the fenced code block.
     *
     * @param context the context.
     * @param code the fenced code block.
     */
    private void processFencedCodeBlock(ParrotContext context, FencedCodeBlock code) {
        context.getScriptBuilder().append(code.getContentChars().toString().trim());
        context.getScriptBuilder().append("\n");
    }

    /**
     * Process the snippet include.
     *
     * <p>
     * The following steps happen upon an include.
     * </p>
     * <ol>
     * <li>The current list of snippets is pushed onto the stack</li>
     * <li>A new empty list of snippets is set for processing</li>
     * <li>The snippets coming from the include file are loaded</li>
     * </li>
     *
     * @param context the context.
     * @param includeFilename the include filename.
     */
    private void processIncludeSnippet(ParrotContext context, String includeFilename) {
        File includeFile = new File(context.getCurrentFile().getParent(), includeFilename);
        if (includeFile.exists()) {
            context.getSnippetStack().push(context.getSnippets());
            context.getFileStack().push(includeFile);
            LOGGER.log(INFO, "Begin processing - " + includeFile.toPath().normalize());
            List snippets = loadFile(includeFile);
            context.setSnippets(snippets);
        } else {
            LOGGER.log(WARNING, "File could not be found: " + includeFile.toPath().normalize());
            context.getScriptBuilder().append("# WARN File could not be found: " + includeFile.toPath().normalize());
            context.getScriptBuilder().append("\n");
        }
    }

    /**
     * Process the "include once" snippet.
     *
     * <p>
     * The following steps happen upon an include.
     * </p>
     * <ol>
     * <li>The current list of snippets is pushed onto the stack</li>
     * <li>A new empty list of snippets is set for processing</li>
     * <li>The snippets coming from the include file are loaded</li>
     * </li>
     *
     * @param context the context.
     * @param includeFilename the include filename.
     */
    private void processIncludeOnceSnippet(ParrotContext context, String includeFilename) {
        context.getSnippetStack().push(context.getSnippets());
        File includeFile = new File(context.getCurrentFile().getParent(), includeFilename);
        context.getFileStack().push(includeFile);
        LOGGER.log(INFO, "Begin processing - " + includeFile.toPath().normalize());
        List snippets = loadFile(includeFile);
        List list = new ArrayList();
        for(int i=0; i<snippets.size(); i++) {
            if (snippets.get(i) instanceof HtmlCommentBlock) {
                HtmlCommentBlock comment = (HtmlCommentBlock) snippets.get(i);
                String firstLine = comment.getContentChars(0, 1).toString();
                if (!firstLine.startsWith("<!-- workflow.include")) {
                    list.add(snippets.get(i));
                }
            } else {
                list.add(snippets.get(i));
            }
        }
        context.setSnippets(list);
    }

    /**
     * Process the output filename.
     *
     * @param context the context.
     * @param outputFilename the output filename.
     */
    private void processOutputFilename(ParrotContext context, String outputFilename) {
        if (context.getSnippetStack().isEmpty()) {
            context.setOutputFilename(outputFilename);
        }
    }

    /**
     * Process the push path flag.
     *
     * <p>
     * This directive enables a push path which limits triggering of the
     * workflow to changes of the original Markdown file.
     * </p>
     *
     * @param pushPathFlag the push path flag.
     */
    private void processPushPath(ParrotContext context, String pushPathFlag) {
        if (context.getSnippetStack().isEmpty()) {
            Boolean booleanValue = Boolean.parseBoolean(pushPathFlag);
            if (booleanValue) {
                On on = context.getWorkflow().getOn();
                if (on.getPush() == null) {
                    on.setPush(new Push());
                }
                Push push = on.getPush();
                if (push.getPaths() == null) {
                    push.setPaths(new ArrayList<>());
                }
                push.getPaths().add(getRelativeFilename(context.getCurrentFile()));
            }
        }
    }

    /**
     * Process the run snippet.
     *
     * <p>
     * A run snippet is the simplest form of a snippet. This type of snippet can
     * be used to include a snippet that should not be visible when the Markdown
     * page is being rendered, but it needs to be present when the workflow
     * runs.
     * </p>
     *
     * @param context the context.
     * @param comment the comment.
     */
    private void processRunSnippet(ParrotContext context, HtmlCommentBlock comment) {
        List<BasedSequence> content = comment.getContentLines();
        content.remove(0);
        content.remove(content.size() - 1);
        Iterator<BasedSequence> iterator = content.iterator();
        while (iterator.hasNext()) {
            BasedSequence sequence = iterator.next();
            context.getScriptBuilder().append(sequence.toString());
        }
    }

    /**
     * Process the runs-on.
     *
     * <p>
     * This directive sets the type of runner the workflow needs to run on.
     * </p>
     *
     * @param context the context.
     * @param runsOn the runs-on.
     */
    private void processRunsOn(ParrotContext context, String runsOn) {
        if (context.getSnippetStack().isEmpty()) {
            Job job = (Job) context.getWorkflow().getJobs().get("validate");
            job.setRunsOn(runsOn);
        }
    }

    /**
     * Process the skip.
     *
     * <p>
     * This directive instructs the generator to skip the next snippet.
     * </p>
     *
     * @param context the context.
     */
    private void processSkip(ParrotContext context) {
        if (context.getSnippets().size() > 0) {
            context.getSnippets().remove(0);
        }
    }

    /**
     * Process the snippets.
     *
     * @param context the context.
     */
    private void processSnippet(ParrotContext context) {
        Object snippet = context.getSnippets().remove(0);
        if (snippet instanceof FencedCodeBlock) {
            FencedCodeBlock code = (FencedCodeBlock) snippet;
            processFencedCodeBlock(context, code);
        } else if (snippet instanceof HtmlCommentBlock) {
            HtmlCommentBlock comment = (HtmlCommentBlock) snippet;
            processCommentSnippet(context, comment);
        } else if (snippet != null) {
            context.getScriptBuilder().append(snippet.toString());
            context.getScriptBuilder().append("\n");
        }
    }

    /**
     * Process the workflow dispatch directive.
     *
     * <p>
     * This directive enables the workflow_dispatch.
     * </p>
     *
     * @param context the context.
     */
    private void processWorkflowDispatchDirective(ParrotContext context) {
        if (context.getSnippetStack().isEmpty()) {
            On on = context.getWorkflow().getOn();
            if (on.getWorkflow_dispatch() == null) {
                on.setWorkflow_dispatch(new WorkflowDispatch());
            }
        }
    }

    /**
     * Process the workflow name.
     *
     * <p>
     * Thid directive sets the name of the workflow.
     * </p>
     *
     * @param context the context.
     * @param name the workflow name.
     */
    private void processWorkflowName(ParrotContext context, String name) {
        if (context.getSnippetStack().isEmpty()) {
            context.getWorkflow().setName(name);
        }
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
