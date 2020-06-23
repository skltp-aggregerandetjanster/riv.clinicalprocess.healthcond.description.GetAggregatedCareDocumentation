package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.healthcond.description.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderInterface;
import riv.clinicalprocess.healthcond.description.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedcaredocumentation.v2")
public class GACDAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_healthcond_description/interactions/GetCareDocumentationInteraction/GetCareDocumentationInteraction_2.1_RIVTABP21.wsdl";

  public GACDAgpServiceConfiguration() {

    setServiceName("GetAggregatedCareDocumentation-v2");
    setTargetNamespace("urn:riv:clinicalprocess:healthcond:description:GetCareDocumentation:2:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9001/GetAggregatedCareDocumentation/service/v2");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetCareDocumentationResponderInterface.class.getName());
    setInboundPortName(GetCareDocumentationResponderService.GetCareDocumentationResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetCareDocumentationResponderService.class.getName());
    setOutboundPortName(GetCareDocumentationResponderService.GetCareDocumentationResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:healthcond:description");
    setEiCategorization("voo");

    // TAK
    setTakContract("urn:riv:clinicalprocess:healthcond:description:GetCareDocumentationResponder:2");

    // Set service factory
    setServiceFactoryClass(GACDAgpServiceFactoryImpl.class.getName());
    }


}
