package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.healthcond.description.getcaredocumentationresponder.v2.GetCareDocumentationResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;


@RunWith(SpringJUnit4ClassRunner.class)
public class GACDCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GACDAgpServiceConfiguration configuration = new GACDAgpServiceConfiguration();
  private static AgpServiceFactory<GetCareDocumentationResponseType> agpServiceFactory = new GACDAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACDCreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetCareDocumentationResponseType responseType = (GetCareDocumentationResponseType)response;
    return responseType.getCareDocumentation().size();
  }
}