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

import com.manorrock.parrot.model.Workflow;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The GitHub Workflow Generator context.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class ParrotContext {
    
    /**
     * Stores the runs-on.
     */
    private String runsOn = "ubuntu-latest";

    /**
     * Stores the file stack.
     */
    private final Stack<File> fileStack;

    /**
     * Stores the output filename.
     */
    private String outputFilename;

    /**
     * Stores the script builder.
     */
    private StringBuilder scriptBuilder;

    /**
     * Stores the current snippets we are processing.
     */
    private List snippets;

    /**
     * Stores the snippet stack.
     */
    private final Stack snippetStack;

    /**
     * Stores the workflow.
     */
    private Workflow workflow;

    /**
     * Constructor.
     */
    public ParrotContext() {
        this.fileStack = new Stack();
        this.snippets = new ArrayList();
        this.snippetStack = new Stack();
    }

    /**
     * Get the current file.
     *
     * @return the current file.
     */
    public File getCurrentFile() {
        return fileStack.peek();
    }

    /**
     * Get the runs-on.
     * 
     * @return the runs-on.
     */
    public String getRunsOn() {
        return runsOn;
    }

    /**
     * Get the file stack.
     *
     * @return the file stack.
     */
    public Stack getFileStack() {
        return fileStack;
    }

    /**
     * Get the script builder.
     *
     * @return the script builder.
     */
    public StringBuilder getScriptBuilder() {
        return scriptBuilder;
    }

    /**
     * Get the snippets.
     *
     * @return the snippets.
     */
    public List getSnippets() {
        return snippets;
    }

    /**
     * Get the stack.
     *
     * @return the stack.
     */
    public Stack getSnippetStack() {
        return snippetStack;
    }

    /**
     * Get the output filename.
     * 
     * @return the output filename.
     */
    public String getOutputFilename() {
        return outputFilename;
    }

    /**
     * Get the workflow.
     *
     * @return the workflow.
     */
    public Workflow getWorkflow() {
        return workflow;
    }

    /**
     * Set the current file.
     *
     * @param currentFile the current file.
     */
    public void setCurrentFile(File currentFile) {
        fileStack.push(currentFile);
    }

    /**
     * Set the runs-on.
     * 
     * @param runsOn the runs-on.
     */
    public void setRunsOn(String runsOn) {
        this.runsOn = runsOn;
    }

    /**
     * Set the output filename.
     *
     * @param outputFilename the output filename.
     */
    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    /**
     * Set the script builder.
     *
     * @param scriptBuilder the script builder.
     */
    public void setScriptBuilder(StringBuilder scriptBuilder) {
        this.scriptBuilder = scriptBuilder;
    }

    /**
     * Set the snippets.
     *
     * @param snippets the snippets.
     */
    public void setSnippets(List snippets) {
        this.snippets = snippets;
    }

    /**
     * Set the workflow.
     *
     * @param workflow the workflow.
     */
    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}
