package com.project.zipsa.unit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.xml.transform.StringSource;

import static org.springframework.ws.test.client.RequestMatchers.payload;

@WebServiceClientTest
public class TestCode {

    @Autowired
    private MockWebServiceServer server;


    @Test
    void a() {
        this.server
                .expect(RequestMatchers.payload(new StringSource("<request/>")))
                .andRespond(ResponseCreators.withPayload(new StringSource("<response><status>200</status></response>")));
    }

}
