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
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import riv.clinicalprocess.healthcond.description.v2.CareDocumentationType;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;

public class ResponseListFactoryImplTest {

    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetCareDocumentationResponseType.class, ProcessingStatusType.class);

    // FIXME - currently fails with
    //         javax.xml.bind.UnmarshalException: unexpected element (uri:"urn:riv:clinicalprocess:healthcond:description:GetCareDocumentationResponder:2", local:"GetCareDocumentationResponse"). Expected elements are <{urn:riv:interoperability:headers:1}Actor>,<{urn:riv:interoperability:headers:1}ProcessingStatus>,<{urn:riv:clinicalprocess:healthcond:description:2}communicableDisease>,<{urn:riv:clinicalprocess:healthcond:description:2.1}result>
    @Ignore
    @Test
    public void getXmlFromAggregatedResponse(){
        FindContentType fc = new FindContentType();
        fc.setRegisteredResidentIdentification("1212121212");
        QueryObject queryObject = new QueryObject(fc, null);
        List<Object> responseList = new ArrayList<Object>(2);
        responseList.add(createGetCareDocumentationResponse());
        responseList.add(createGetCareDocumentationResponse());
        ResponseListFactoryImpl responseListFactory = new ResponseListFactoryImpl();

        String responseXML = responseListFactory.getXmlFromAggregatedResponse(queryObject, responseList);
        GetCareDocumentationResponseType response = (GetCareDocumentationResponseType)jaxbUtil.unmarshal(responseXML);
        assertEquals(2, response.getCareDocumentation().size());
    }

    private GetCareDocumentationResponseType createGetCareDocumentationResponse(){
        GetCareDocumentationResponseType getCareDocResponse = new GetCareDocumentationResponseType();
        getCareDocResponse.getCareDocumentation().add(new CareDocumentationType());
        return getCareDocResponse;
    }
}
