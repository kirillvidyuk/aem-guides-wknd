package com.adobe.aem.guides.wknd.core.workflows;

import javax.jcr.Node;
import javax.jcr.Session;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = WorkflowProcess.class,
    immediate = true,
    property = {
        "process.label" + " = WKND Test Workflow Step",
        Constants.SERVICE_VENDOR + " = WKND Test",
        Constants.SERVICE_DESCRIPTION + " = Custom WKND Test workflow step."
    }
)
public class WkndWorkflowProcess implements WorkflowProcess {
    private final Logger logger = LoggerFactory.getLogger(WkndWorkflowProcess.class);

    @Override
    public void execute(WorkItem item, WorkflowSession wfSession, MetaDataMap pArgs) throws WorkflowException {
        logger.info("\n-----------------------------------------------");
        try {
            WorkflowData workflowData = item.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = wfSession.getSession();
                String path = workflowData.getPayload().toString() + "/jcr:content";
                Node node = (Node) session.getItem(path);
                String[] processArgs = pArgs.get("PROCESS_ARGS", "string").toString().split(",");
                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];
                    if (node != null) {
                        node.setProperty(prop, value);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Error from WkndWorkflowStep: {}", e.getMessage());
        }
    }
    
}
