/*
 *  Copyright (c) 2002-2022 Manorrock.com. All Rights Reserved.
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests Workflow Generator.
 */
public class ParrotTest {

    private static Parrot gwg;

    @BeforeAll
    static void init() {
        gwg = new Parrot();
        gwg.setBaseDirectory(new File("src/test/resources"));
        gwg.setOutputDirectory(new File("target"));
        gwg.setShellScriptOutputDirectory(new File("target"));
    }

    @Test
    void generateEmptyReadme() throws IOException {
        File inputFile = new File("src/test/resources/empty_README.md");
        String workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        String shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);

        // Generates the workflow
        gwg.processFile(inputFile);

        // Checks if the workflow is correct
        Path testWorkflow = gwg.getBaseDirectory().toPath().resolve(workflowOutputFilename);
        Path generatedWorkflow = gwg.getOutputDirectory().toPath().resolve(workflowOutputFilename);
        assertEquals(-1l, Files.mismatch(testWorkflow, generatedWorkflow));

        // Checks if the shell script is correct
        Path testShellscript = gwg.getBaseDirectory().toPath().resolve(shellScriptOutputFilename);
        Path generatedShellscript = gwg.getShellScriptOutputDirectory().toPath().resolve(shellScriptOutputFilename);
        assertEquals(-1l, Files.mismatch(testShellscript, generatedShellscript));
    }

    @Test
    void generateSimpleReadme() throws IOException {
        File inputFile = new File("src/test/resources/simple_README.md");
        String workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        String shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);

        // Generates the workflow
        gwg.processFile(inputFile);

        // Checks if the workflow is correct
        Path testWorkflow = gwg.getBaseDirectory().toPath().resolve(workflowOutputFilename);
        Path generatedWorkflow = gwg.getOutputDirectory().toPath().resolve(workflowOutputFilename);
        assertEquals(-1l, Files.mismatch(testWorkflow, generatedWorkflow));
        
        // Checks if the shell script is correct
        Path testShellscript = gwg.getBaseDirectory().toPath().resolve(shellScriptOutputFilename);
        Path generatedShellscript = gwg.getShellScriptOutputDirectory().toPath().resolve(shellScriptOutputFilename);
        assertEquals(-1l, Files.mismatch(testShellscript, generatedShellscript));
    }

    @Test
    void generateReadmeWithInclude() throws IOException {
        File inputFile = new File("src/test/resources/include_README.md");
        String workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        String shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);

        // Generates the workflow
        gwg.processFile(inputFile);

        // Checks if the workflow is correct
        Path testWorkflow = gwg.getBaseDirectory().toPath().resolve(workflowOutputFilename);
        Path generatedWorkflow = gwg.getOutputDirectory().toPath().resolve(workflowOutputFilename);
        assertEquals(-1l, Files.mismatch(testWorkflow, generatedWorkflow));

        // Checks if the shell script is correct
        Path testShellscript = gwg.getBaseDirectory().toPath().resolve(shellScriptOutputFilename);
        Path generatedShellscript = gwg.getShellScriptOutputDirectory().toPath().resolve(shellScriptOutputFilename);
        assertEquals(-1l, Files.mismatch(testShellscript, generatedShellscript));
    }
    @Test
    void generateFileNames() throws IOException {
        File inputFile = new File("src/test/resources/empty_README.md");
        String workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        String shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);
        assertEquals("empty_README_md.yml", workflowOutputFilename);
        assertEquals("empty.sh", shellScriptOutputFilename);

        inputFile = new File("src/test/resources/simple_README.md");
        workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);
        assertEquals("simple_README_md.yml", workflowOutputFilename);
        assertEquals("simple.sh", shellScriptOutputFilename);

        inputFile = new File("src/test/resources/include_README.md");
        workflowOutputFilename = gwg.generateWorkflowOutputFilename(inputFile);
        shellScriptOutputFilename = gwg.generateShellScriptOutputFilename(inputFile);
        assertEquals("include_README_md.yml", workflowOutputFilename);
        assertEquals("include.sh", shellScriptOutputFilename);
    }
}
