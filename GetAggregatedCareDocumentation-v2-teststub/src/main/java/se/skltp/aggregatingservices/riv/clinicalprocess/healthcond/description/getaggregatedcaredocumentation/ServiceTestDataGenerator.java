
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.healthcond.description.enums.v2.ClinicalDocumentNoteCodeEnum;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationType;
import riv.clinicalprocess.healthcond.description.v2.CPatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.description.v2.CareDocumentationBodyType;
import riv.clinicalprocess.healthcond.description.v2.CareDocumentationType;
import riv.clinicalprocess.healthcond.description.v2.ClinicalDocumentNoteType;
import riv.clinicalprocess.healthcond.description.v2.HealthcareProfessionalType;
import riv.clinicalprocess.healthcond.description.v2.LegalAuthenticatorType;
import riv.clinicalprocess.healthcond.description.v2.OrgUnitType;
import riv.clinicalprocess.healthcond.description.v2.PersonIdType;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;

import static se.skltp.aggregatingservices.data.TestDataDefines.*;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

    public static final String TEST_REASON_DEFAULT = "default reason";
    public static final String TEST_REASON_UPDATED = "updated reason";

	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetCareDocumentationType request = (GetCareDocumentationType) messageContentsList.get(1);
		String patientId = request.getPatientId().getId();
		return patientId;
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetCareDocumentationResponseType response = new GetCareDocumentationResponseType();
		for (int i = 0; i < responseItems.length; i++) {
            response.getCareDocumentation().add((CareDocumentationType)responseItems[i]);
		}

		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

        CareDocumentationType response = new CareDocumentationType();
        CPatientSummaryHeaderType header = new CPatientSummaryHeaderType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(registeredResidentId);
        patientId.setType("1.2.752.129.2.1.3.1");
        header.setPatientId(patientId);
        header.setApprovedForPatient(true);
        header.setDocumentTime(time);
        HealthcareProfessionalType author = new HealthcareProfessionalType();
        author.setHealthcareProfessionalCareUnitHSAId(logicalAddress + "-0002" + ThreadLocalRandom.current().nextInt(1, 20));
        author.setHealthcareProfessionalCareGiverHSAId(logicalAddress + "-0001" + ThreadLocalRandom.current().nextInt(20));
        
        OrgUnitType orgUnit = new OrgUnitType();
        if(TEST_LOGICAL_ADDRESS_1.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Kusten, Kärna");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-12345" + ThreadLocalRandom.current().nextInt(20));
        } else if(TEST_LOGICAL_ADDRESS_2.equals(logicalAddress)){
        	orgUnit.setOrgUnitName("Vårdcentralen Molnet");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-12347" + ThreadLocalRandom.current().nextInt(20));
        } else {
        	orgUnit.setOrgUnitName("Vårdcentralen Stacken");
            orgUnit.setOrgUnitHSAId(logicalAddress + "-1238"  + ThreadLocalRandom.current().nextInt(20));
        }

        
        author.setHealthcareProfessionalOrgUnit(orgUnit);
        header.setAccountableHealthcareProfessional(author);
        header.setSourceSystemHSAid(logicalAddress + "-"  + ThreadLocalRandom.current().nextInt(20));
        header.setDocumentId(businessObjectId);
        LegalAuthenticatorType legalAuthenticator = new LegalAuthenticatorType();
        legalAuthenticator.setSignatureTime("20130707070707");
        legalAuthenticator.setLegalAuthenticatorHSAId(logicalAddress + "2222" + ThreadLocalRandom.current().nextInt(20));
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

	public Object createRequest(String patientId, String sourceSystemHSAId){
		GetCareDocumentationType outcomeType = new GetCareDocumentationType();

        PersonIdType patient = new PersonIdType();
        patient.setId(patientId);
        outcomeType.setPatientId(patient);
        outcomeType.setSourceSystemHSAid(sourceSystemHSAId);
        // outcomeType.setTimePeriod(datePeriod);
        // outcomeType.getCareContactId().add(arg0)
        
		return outcomeType;
	}
	
}
