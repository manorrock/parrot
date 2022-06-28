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
package com.manorrock.parrot.model;

import com.manorrock.yaml.YAMLSerializerHint;
import java.util.ArrayList;
import java.util.List;

/**
 * A GitHub job.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Job {

    /**
     * Get runs-on.
     */
    @YAMLSerializerHint(name = "runs-on")
    private String runsOn;
    
    /**
     * Stores the steps.
     */
    private List steps;
    
    /**
     * Constructor.
     */
    public Job() {
        this.steps = new ArrayList();
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
     * Get the steps.
     * 
     * @return the steps.
     */
    public List getSteps() {
        return steps;
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
     * Set the steps.
     * 
     * @param steps the steps.
     */
    public void setSteps(List steps) {
        this.steps = steps;
    }
}
