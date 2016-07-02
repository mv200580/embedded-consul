package com.pszymczyk.consul.infrastructure

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

class SimpleConsulClient {

    private static final String NO_LEADER_ELECTED_RESPONSE = "";

    private final RESTClient http

    SimpleConsulClient(int httpPort) {
        this.http = new RESTClient("http://localhost:$httpPort")
    }

    boolean isLeaderElected() {
        HttpResponseDecorator response = http.get(path: '/v1/status/leader', contentType: ContentType.JSON)

        response.getData() != NO_LEADER_ELECTED_RESPONSE
    }

    Collection getServicesIds() {
        HttpResponseDecorator resonse = http.get(path: '/v1/agent/services', contentType: ContentType.JSON)

        resonse.getData()
                .keySet()
                .findAll({ it -> it != 'consul' })
    }

    void deregister(String id) {
        http.get(path: "/v1/agent/service/deregister/$id", contentType: ContentType.ANY)
    }

    void clearKvStore() {
        http.delete(path: '/v1/kv/', query: [recurse: true], contentType: ContentType.ANY)
    }
}
