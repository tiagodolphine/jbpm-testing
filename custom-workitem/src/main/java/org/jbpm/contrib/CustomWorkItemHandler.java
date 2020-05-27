/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.contrib;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.process.workitem.core.AbstractLogOrThrowWorkItemHandler;
import org.jbpm.process.workitem.core.util.RequiredParameterValidator;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.jbpm.process.workitem.core.util.Wid;
import org.jbpm.process.workitem.core.util.WidParameter;
import org.jbpm.process.workitem.core.util.WidResult;
import org.jbpm.process.workitem.core.util.service.WidAction;
import org.jbpm.process.workitem.core.util.service.WidAuth;
import org.jbpm.process.workitem.core.util.service.WidService;
import org.jbpm.process.workitem.core.util.WidMavenDepends;

@Wid(widfile = "CustomDefinitions.wid", name = "CustomDefinitions",
        displayName = "CustomDefinitions",
        defaultHandler = "mvel: new org.jbpm.contrib.CustomWorkItemHandler()",
        documentation = "custom-workitem/index.html",
        category = "custom-workitem",
        icon = "CustomDefinitions.png",
        parameters = {
                @WidParameter(name = "MyFirstParam", required = true),
                @WidParameter(name = "MySecondParam", required = true),
                @WidParameter(name = "MyThirdParam", required = false),
        },
        results = {
                @WidResult(name = "Result")
        },
        mavenDepends = {
                @WidMavenDepends(group = "org.jbpm.contrib", artifact = "custom-workitem", version = "7.37.0.Final")
        },
        serviceInfo = @WidService(category = "custom-workitem", description = "${description}",
                keywords = "",
                action = @WidAction(title = "CustomWorkItemHandler Name"),
                authinfo = @WidAuth(required = true, params = {"MyFirstParam", "MySecondParam"},
                        paramsdescription = {"MyFirstParam Description", "MySecondParam Description"},
                        referencesite = "referenceSiteURL")
        )
)
public class CustomWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

    public void executeWorkItem(WorkItem workItem,
                                WorkItemManager manager) {
        try {
            RequiredParameterValidator.validate(this.getClass(),
                                                workItem);

            // sample parameters
            String sampleParam = (String) workItem.getParameter("MyFirstParam");
            String sampleParam2 = (String) workItem.getParameter("MySecondParam");
            String sampleParam3 = (String) workItem.getParameter("MyThirdParam");

            // complete workitem impl...

            // return results
            String sampleResult = "Hello : " + sampleParam + " " + sampleParam2 + " "+ sampleParam3 + " !!!!";
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("Result", sampleResult);
            manager.completeWorkItem(workItem.getId(), results);

            System.out.println("========== CustomWorkItemHandler ============");
            System.out.println(sampleResult);
        } catch (Throwable cause) {
            handleException(cause);
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem,
                              WorkItemManager manager) {
        // stub
    }
}


