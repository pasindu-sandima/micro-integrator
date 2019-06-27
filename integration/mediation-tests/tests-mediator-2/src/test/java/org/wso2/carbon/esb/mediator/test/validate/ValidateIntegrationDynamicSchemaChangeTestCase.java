/*
 *Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *WSO2 Inc. licenses this file to you under the Apache License,
 *Version 2.0 (the "License"); you may not use this file except
 *in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
 */
package org.wso2.carbon.esb.mediator.test.validate;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.MicroRegistryManager;

import java.io.File;

public class ValidateIntegrationDynamicSchemaChangeTestCase extends ESBIntegrationTest {

    private MicroRegistryManager registryManager = null;

    @BeforeClass(alwaysRun = true)
    public void init() throws Exception {

        // Initialize ESBMediatorTest
        super.init();
        registryManager = new MicroRegistryManager();
    }

    /**
     * This test is for Validate Mediator
     * Test Scenario:
     * Add two schema to registry
     * Add a validate mediator, which validates the first element of the SOAP body of incoming message using above schema (static key)
     * Send a request and see whether the validation happens
     * Now change the schema (now validation should happen according to the new schema)
     * Check whether the validation happens accordingly
     * <p/>
     *
     * @throws Exception
     */
    @Test(groups = "wso2.esb")
    public void validateMediatorDynamicSchemaChangeTest() throws Exception {
        try {
            axis2Client
                    .sendSimpleStockQuoteRequest(getProxyServiceURLHttp("validateMediatorDynamicSchemaChangeTestProxy"),
                            null, "WSO2");
            Assert.fail("Validate mediator on-fail did not executed as expected");
        } catch (AxisFault e) {
            Assert.assertTrue(e.getMessage().contains("Invalid custom quote request for Validate Mediator Test"),
                    "Received Fault message - after validation schema failure");
        }

        // Update schema reside in registry
        String newSchemaPath = getESBResourceLocation() + File.separator + "synapseconfig" + File.separator + "filters"
                                                        + File.separator + "validate" + File.separator + "schema1a.xml";
        registryManager.updateResource("conf:/validate/schema1", newSchemaPath, true);

        //Work - Schema 2
        /** Time to set up schema - strictly necessary */
        Thread.sleep(30000);

        OMElement response = axis2Client
                .sendSimpleStockQuoteRequest(getProxyServiceURLHttp("validateMediatorDynamicSchemaChangeTestProxy"),
                        null, "WSO2");

        Assert.assertTrue(response.toString().contains("GetQuoteResponse"), "GetQuoteResponse not found in response");
        Assert.assertTrue(response.toString().contains("WSO2 Company"), "GetQuoteResponse not found in response");
    }

    @AfterClass(alwaysRun = true)
    public void clear() throws Exception {
        registryManager.restoreOriginalResources();
    }

}
