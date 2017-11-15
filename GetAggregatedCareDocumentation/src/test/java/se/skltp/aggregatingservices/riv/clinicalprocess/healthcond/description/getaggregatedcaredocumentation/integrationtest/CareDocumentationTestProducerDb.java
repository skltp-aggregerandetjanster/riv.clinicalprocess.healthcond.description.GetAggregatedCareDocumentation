package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation.integrationtest;

import java.util.Random;

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
import riv.clinicalprocess.healthcond.description.v2.PersonIdType;
import se.skltp.agp.test.producer.TestProducerDb;

public class CareDocumentationTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(CareDocumentationTestProducerDb.class);
    private static Random rand = new Random(); 

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
        author.setHealthcareProfessionalCareUnitHSAId(logicalAddress + "-0002" + rand.nextInt(20));
        author.setHealthcareProfessionalCareGiverHSAId(logicalAddress + "-0001" + rand.nextInt(20));
        
        OrgUnitType orgUnit = new OrgUnitType();
        if(TestProducerDb.TEST_LOGICAL_ADDRESS_1.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Kusten, Kärna");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-12345" + rand.nextInt(20));
        } else if(TestProducerDb.TEST_LOGICAL_ADDRESS_2.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Molnet");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-12347" + rand.nextInt(20));
        } else {
        	orgUnit.setOrgUnitName("Vårdcentralen Stacken");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-1238"  + rand.nextInt(20));
        }

        
        author.setHealthcareProfessionalOrgUnit(orgUnit);
        header.setAccountableHealthcareProfessional(author);
        header.setSourceSystemHSAid(logicalAddress + "-"  + rand.nextInt(20));
        header.setDocumentId(businessObjectId);
        LegalAuthenticatorType legalAuthenticator = new LegalAuthenticatorType();
        legalAuthenticator.setSignatureTime("20130707070707");
        legalAuthenticator.setLegalAuthenticatorHSAId(logicalAddress + "2222" + rand.nextInt(20));
        legalAuthenticator.setLegalAuthenticatorName("L. Egal");
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
