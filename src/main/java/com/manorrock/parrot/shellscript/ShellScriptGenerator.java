/*
 *  Copyright (c) 2002-2024 Manorrock.com. All Rights Reserved.
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
package com.manorrock.parrot.shellscript;

import com.manorrock.parrot.ParrotContext;
import com.manorrock.parrot.ParrotGenerator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.Logger.Level.INFO;

/**
 * The GitHub shell script generator.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class ShellScriptGenerator implements ParrotGenerator {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(ShellScriptGenerator.class.getName());

    /**
     * Stores the base directory.
     */
    private File baseDirectory = new File(".");

    /**
     * Stores the workflows output directory.
     */
    private File outputDirectory = new File(".");

    String generateScriptOutputFilename(File file) {
        return getRelativeFilename(file).replaceAll("/", "_").replaceAll("\\.", "_") + ".sh";
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
     * Parse the command line arguments.
     *
     * @param arguments the arguments.
     */
    public void parseArguments(String[] arguments) {
        if (arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i].equals("--baseDirectory")) {
                    baseDirectory = new File(arguments[i + 1]);
                }
                if (arguments[i].equals("--outputDirectory")) {
                    outputDirectory = new File(arguments[i + 1]);
                }
            }
        }
        LOGGER.log(INFO, "Base directory: " + baseDirectory);
        LOGGER.log(INFO, "Output directory: " + outputDirectory);
    }

    /**
     * Process the given directory.
     *
     * @param directory the directory.
     */
    private void processDirectory(File directory) {
        LOGGER.log(INFO, "Profcessing directory: " + directory);
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
    private void processFile(File file) {
        LOGGER.log(INFO, "Processing file: " + file);
        ParrotContext context = new ParrotContext();
        context.setOutputFilename(generateScriptOutputFilename(file));
        
        try {
            File outputFile = new File(outputDirectory, context.getShellScriptOutputFilename());
            FileWriter workflowOutputWriter = new FileWriter(outputFile);
            workflowOutputWriter.write("#!/bin/bash");
            /*
             * Generate the shell script.
             */
            workflowOutputWriter.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Run the generator.
     */
    @Override
    public void run() {
        processDirectory(baseDirectory);
    }
}
