/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.esb.nhttp.transport.mtom.test;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.engine.annotations.ExecutionEnvironment;
import org.wso2.carbon.automation.engine.annotations.SetEnvironment;
import org.wso2.carbon.automation.engine.frameworkutils.FrameworkPathUtil;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.servers.axis2.SampleAxis2Server;

import java.io.File;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Test case check whether attachment is received by the client
 */
public class ESBJAVA4909MultipartRelatedTestCase extends ESBIntegrationTest {
    private final String MTOM_SERVICE = "MTOMSwASampleService";
    private SampleAxis2Server axis2Server;

    @SetEnvironment(executionEnvironments = { ExecutionEnvironment.STANDALONE })
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
        axis2Server = new SampleAxis2Server("test_axis2_server_9001.xml");
        axis2Server.start();
        axis2Server.deployService(MTOM_SERVICE);
    }

    @Test(groups = { "wso2.esb" }, description = "ESBJAVA4909MultipartTest")
    public void callOutMediatorWithMTOMTest() throws IOException {
        String targetEPR = getProxyServiceURLHttp("MTOMChecker");
        String fileName =
                FrameworkPathUtil.getSystemResourceLocation() + "artifacts" + File.separator + "ESB" + File.separator
                        + "mtom" + File.separator + "content.xml";
        sendUsingMTOM(fileName, targetEPR);
    }

    @AfterClass(alwaysRun = true)
    public void close() throws Exception {

        if (axis2Server != null && axis2Server.isStarted()) {
            axis2Server.stop();
        }
    }

    public void sendUsingMTOM(String fileName, String targetEPR) throws IOException {
        final String EXPECTED = "<m0:uploadFileUsingMTOMResponse xmlns:m0=\"http://services.samples\"><m0:response>"
                + "<m0:image>PHByb3h5PkFCQzwvcHJveHk+</m0:image></m0:response></m0:uploadFileUsingMTOMResponse>";
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://services.samples", "m0");
        OMElement payload = factory.createOMElement("uploadFileUsingMTOM", ns);
        OMElement request = factory.createOMElement("request", ns);
        OMElement image = factory.createOMElement("image", ns);

        FileDataSource fileDataSource = new FileDataSource(new File(fileName));
        DataHandler dataHandler = new DataHandler(fileDataSource);
        OMText textData = factory.createOMText(dataHandler, true);
        image.addChild(textData);
        request.addChild(image);
        payload.addChild(request);

        ServiceClient serviceClient = new ServiceClient();
        Options options = new Options();
        options.setTo(new EndpointReference(targetEPR));
        options.setAction("urn:uploadFileUsingMTOM");
        options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
        options.setCallTransportCleanup(true);
        serviceClient.setOptions(options);
        OMElement response = serviceClient.sendReceive(payload);
        Assert.assertTrue(response.toString().contains(EXPECTED), "Attachment is missing in the response");
    }
}
