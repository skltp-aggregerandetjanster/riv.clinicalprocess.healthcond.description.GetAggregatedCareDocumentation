/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation.integrationtest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.description.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderInterface;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetCareDocumentationResponderService", portName = "GetCareDocumentationResponderPort", targetNamespace = "urn:riv:ehr:patientsummary:GetCareDocumentationResponder:2:rivtabp21", name = "GetCareDocumentationInteraction")
public class CareDocumentationTestProducer implements GetCareDocumentationResponderInterface {

    private static final Logger log = LoggerFactory.getLogger(CareDocumentationTestProducer.class);

    private TestProducerDb testDb;

    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    @Override
    @WebResult(name = "GetCareDocumentationResponse", targetNamespace = "urn:riv:ehr:patientsummary:GetCareDocumentationResponder:2", partName = "parameters")
    @WebMethod(operationName = "GetCareDocumentation", action = "urn:riv:ehr:patientsummary:GetCareDocumentationResponder:2:GetCareDocumentation")
    public GetCareDocumentationResponseType getCareDocumentation(
            @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String logicalAddress,
            @WebParam(partName = "parameters", name = "GetCareDocumentation", targetNamespace = "urn:riv:ehr:patientsummary:GetCareDocumentationResponder:2") GetCareDocumentationType request) {
        log.info("### Virtual service for GetCareDocumentation call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPatientId().getId());

        testDb.refreshDb();
        GetCareDocumentationResponseType response = (GetCareDocumentationResponseType)testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            response = new GetCareDocumentationResponseType();
        }

        log.info("### Virtual service got {} documents in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getCareDocumentation().size(), logicalAddress, request.getPatientId().getId()});

        // We are done
        return response;
    }
}
