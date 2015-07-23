package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.description.getaggregatedcaredocumentation

import se.skltp.agp.testnonfunctional.TP01WarmUpAbstract

/**
 * Simple requests to warm up service.
 */
class TP01WarmUp extends TP01WarmUpAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
