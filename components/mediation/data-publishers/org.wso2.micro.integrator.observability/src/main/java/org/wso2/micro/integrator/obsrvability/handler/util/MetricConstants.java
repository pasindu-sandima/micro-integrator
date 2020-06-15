/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.micro.integrator.obsrvability.handler.util;

/**
 * Constants required in instrumenting Prometheus metrics.
 */
public class MetricConstants {

    // Constants for Synapse artifacts
    public static final String PROXY_NAME = "proxy.name";
    public static final String SYNAPSE_REST_API = "SYNAPSE_REST_API";
    public static final String INBOUND_ENDPOINT = "inbound-endpoint";
    public static final String INBOUND_ENDPOINT_NAME = "inbound.endpoint.name";

    public static final String HTTP_PORT = "http.nio.port";
    public static final String JAVA_VERSION = "java.vm.specification.version";
    public static final String JAVA_HOME = "java.home";

    //Constants for Synapse properties
    public static final String IS_ALREADY_PROCESSED_REST_API = "IS_ALREADY_PROCESSED_REST_API";
    public static final String PROCESSED_API = "PROCESSED_API";
    public static final String TRANSPORT_IN_URL = "TransportInURL";
    public static final String SERVICE_PREFIX = "SERVICE_PREFIX";
    public static final String PORT_OFFSET = "portOffset";
    public static final String INTERNAL_HTTP_API_PORT = "internal.http.api.port";

    //Constants for metric names
    public static final String PROXY_REQUEST_COUNT_TOTAL = "wso2_integration_proxy_request_count_total";
    public static final String PROXY_REQUEST_COUNT_ERROR_TOTAL = "wso2_integration_proxy_request_count_error_total";
    public static final String PROXY_LATENCY_SECONDS = "wso2_integration_proxy_latency_seconds";

    public static final String API_REQUEST_COUNT_TOTAL = "wso2_integration_api_request_count_total";
    public static final String API_REQUEST_COUNT_ERROR_TOTAL = "wso2_integration_api_request_count_error_total";
    public static final String API_LATENCY_SECONDS = "wso2_integration_api_latency_seconds";

    public static final String INBOUND_ENDPOINT_REQUEST_COUNT_TOTAL =
            "wso2_integration_inbound_endpoint_request_count_total";
    public static final String INBOUND_ENDPOINT_REQUEST_COUNT_ERROR_TOTAL =
            "wso2_integration_inbound_endpoint_request_count_error_total";
    public static final String INBOUND_ENDPOINT_LATENCY_SECONDS =
            "wso2_integration_inbound_endpoint_latency_seconds";

    public static final String SERVER_UP = "wso2_integration_server_up";
    public static final String SERVICE_UP = "wso2_integration_service_up";

    public static final String PROMETHEUS_HANDLER = "prometheus_handler";
    public static final String PROXY_LATENCY_BUCKETS = "proxy_latency_buckets";
    public static final String API_LATENCY_BUCKETS = "api_latency_buckets";
    public static final String INBOUND_ENDPOINT_LATENCY_BUCKETS = "inbound_endpoint_latency_buckets";

    public static final String PROXY_LATENCY_TIMER = "PROXY_LATENCY_TIMER";
    public static final String API_LATENCY_TIMER = "API_LATENCY_TIMER";
    public static final String INBOUND_ENDPOINT_LATENCY_TIMER = "INBOUND_ENDPOINT_LATENCY_TIMER";

    public static final String SERVER = "Server";
    public static final String SERVICE = "Service";

    //Constants for Prometheus Metrics types
    public static final String COUNTER = "Counter";
    public static final String HISTOGRAM = "Histogram";
    public static final String GAUGE = "Gauge";
}
