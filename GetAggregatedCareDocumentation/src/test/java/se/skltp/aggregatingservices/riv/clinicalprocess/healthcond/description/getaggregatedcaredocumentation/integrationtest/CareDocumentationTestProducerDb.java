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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.description.enums.v2.ClinicalDocumentNoteCodeEnum;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import riv.clinicalprocess.healthcond.description.v2.CPatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.description.v2.CareDocumentationBodyType;
import riv.clinicalprocess.healthcond.description.v2.CareDocumentationType;
import riv.clinicalprocess.healthcond.description.v2.ClinicalDocumentNoteType;
import riv.clinicalprocess.healthcond.description.v2.HealthcareProfessionalType;
import riv.clinicalprocess.healthcond.description.v2.LegalAuthenticatorType;
import riv.clinicalprocess.healthcond.description.v2.OrgUnitType;
import riv.clinicalprocess.healthcond.description.v2.PatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.description.v2.PersonIdType;
import se.skltp.agp.test.producer.TestProducerDb;

public class CareDocumentationTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(CareDocumentationTestProducerDb.class);

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);
        GetCareDocumentationResponseType response = new GetCareDocumentationResponseType();
        for (int i = 0; i < responseItems.length; i++) {
            response.getCareDocumentation().add((CareDocumentationType)responseItems[i]);
        }
        return response;
    }

    public static final String TEST_REASON_DEFAULT = "default reason";
    public static final String TEST_REASON_UPDATED = "updated reason";

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
                new Object[] {logicalAddress, registeredResidentId, businessObjectId});

        CareDocumentationType response = new CareDocumentationType();
        CPatientSummaryHeaderType header = new CPatientSummaryHeaderType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(registeredResidentId);
        patientId.setType("1.2.752.129.2.1.3.1");
        header.setPatientId(patientId);
        header.setApprovedForPatient(true);
        header.setDocumentTime(time);
        HealthcareProfessionalType author = new HealthcareProfessionalType();
        author.setHealthcareProfessionalCareUnitHSAId(logicalAddress);

        OrgUnitType orgUnit = new OrgUnitType();
        if(TestProducerDb.TEST_LOGICAL_ADDRESS_1.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Kusten, Kärna");
        } else if(TestProducerDb.TEST_LOGICAL_ADDRESS_2.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Molnet");
        } else {
        	orgUnit.setOrgUnitName("Vårdcentralen Stacken");
        }

        author.setHealthcareProfessionalOrgUnit(orgUnit);
        header.setAccountableHealthcareProfessional(author);
        header.setSourceSystemHSAid(logicalAddress);
        header.setDocumentId(businessObjectId);
        LegalAuthenticatorType legalAuthenticator = new LegalAuthenticatorType();
        legalAuthenticator.setSignatureTime("20130707070707");
        header.setLegalAuthenticator(legalAuthenticator);
        response.setCareDocumentationHeader(header);

        CareDocumentationBodyType body = new CareDocumentationBodyType();
        ClinicalDocumentNoteType note = new ClinicalDocumentNoteType();
        note.setClinicalDocumentNoteCode(ClinicalDocumentNoteCodeEnum.ATB);
        note.setClinicalDocumentNoteText("text");
        note.setClinicalDocumentNoteTitle("title");
        body.setClinicalDocumentNote(note);
        response.setCareDocumentationBody(body);
        return response;
    }
}
