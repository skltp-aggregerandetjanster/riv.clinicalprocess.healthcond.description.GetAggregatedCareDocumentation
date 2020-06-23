package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GACDAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetCareDocumentationType, GetCareDocumentationResponseType>{

@Override
public String getPatientId(GetCareDocumentationType queryObject){
    return queryObject.getPatientId().getId();
    }

@Override
public String getSourceSystemHsaId(GetCareDocumentationType queryObject){
    return queryObject.getSourceSystemHSAid();
    }

@Override
public GetCareDocumentationResponseType aggregateResponse(List<GetCareDocumentationResponseType> aggregatedResponseList ){

    GetCareDocumentationResponseType aggregatedResponse=new GetCareDocumentationResponseType();

    for (GetCareDocumentationResponseType item : aggregatedResponseList) {
        GetCareDocumentationResponseType response = item;
        aggregatedResponse.getCareDocumentation().addAll(response.getCareDocumentation());
    }
    
    return aggregatedResponse;
    }
}

