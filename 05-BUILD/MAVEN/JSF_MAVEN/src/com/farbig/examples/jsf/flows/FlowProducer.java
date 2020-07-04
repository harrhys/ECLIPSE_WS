package com.farbig.examples.jsf.flows;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class FlowProducer {
	
	@Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
		
        String flowId = "customerorder";
        flowBuilder.id("flows", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/order.xhtml").markAsStartNode();
        flowBuilder.returnNode("returnFromOrderFlow").fromOutcome("#{customerOrderBean.returnValue}");
        return flowBuilder.getFlow();
    }

}
