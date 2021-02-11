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
package com.manorrock.parrot.model;

import java.util.Map;

/**
 * A GitHub workflow.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Workflow {
    
    /**
     * Stores the on.
     */
    private On on;
    
    /**
     * Stores the name.
     */
    private String name;
    
    /**
     * Stores the jobs.
     */
    private Map<String, Object> jobs;
    
    /**
     * Constructor.
     */
    public Workflow() {
        this.on = new On();
    }
    
    /**
     * Get the on.
     * 
     * @return the on.
     */
    public On getOn() {
        return on;
    }
    
    /**
     * Get the name.
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the jobs.
     * 
     * @return the jobs.
     */
    public Map<String, Object> getJobs() {
        return jobs;
    }
    
    /**
     * Set the on.
     * 
     * @param on the on.
     */
    public void setOn(On on) {
        this.on = on;
    }
    
    /**
     * Set the name.
     * 
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set the jobs.
     * 
     * @param jobs the jobs.
     */
    public void setJobs(Map<String, Object> jobs) {
        this.jobs = jobs;
    }
}
