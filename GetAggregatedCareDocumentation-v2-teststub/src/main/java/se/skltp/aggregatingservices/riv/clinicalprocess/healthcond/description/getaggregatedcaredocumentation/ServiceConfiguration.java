package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.healthcond.description.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderInterface;
import riv.clinicalprocess.healthcond.description.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedcaredocumentation.v2.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_healthcond_description/interactions/GetCareDocumentationInteraction/GetCareDocumentationInteraction_2.1_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetCareDocumentationResponderInterface.class.getName());
    setServiceNamespace("urn:riv:clinicalprocess:healthcond:description:GetCareDocumentationResponder:2");
    setPortName(GetCareDocumentationResponderService.GetCareDocumentationResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
    setServiceTimeout(27000);
  }

}
