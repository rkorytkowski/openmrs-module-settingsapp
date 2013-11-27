package org.openmrs.module.settingsapp;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.RepHandler;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/settingsapp/globalproperty", supportedClass = GlobalProperty.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class GlobalPropertyResource extends DelegatingCrudResource<GlobalProperty> {

	@Override
	public GlobalProperty getByUniqueId(String uniqueId) {
		return Context.getAdministrationService().getGlobalPropertyByUuid(uniqueId);
	}

	@Override
	protected void delete(GlobalProperty delegate, String reason, RequestContext context) throws ResponseException {
		Context.getAdministrationService().purgeGlobalProperty(delegate);
	}

	@Override
	public GlobalProperty newDelegate() {
		return new GlobalProperty();
	}

	@Override
	public GlobalProperty save(GlobalProperty delegate) {
		return Context.getAdministrationService().saveGlobalProperty(delegate);
	}

	@Override
	public void purge(GlobalProperty delegate, RequestContext context) throws ResponseException {
		Context.getAdministrationService().purgeGlobalProperty(delegate);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display", findMethod("getDisplayString"));
			description.addProperty("property");
			description.addProperty("value");
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display", findMethod("getDisplayString"));
			description.addProperty("property");
			description.addProperty("value");
			description.addSelfLink();
			return description;
		}
		return null;
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		return new NeedsPaging<GlobalProperty>(Context.getAdministrationService().getAllGlobalProperties(), context);
	}

	@RepHandler(RefRepresentation.class)
	public SimpleObject asRef(GlobalProperty delegate) throws ConversionException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addProperty("display", findMethod("getDisplayString"));
		description.addSelfLink();
		return convertDelegateToRepresentation(delegate, description);
	}

	public String getDisplayString(GlobalProperty gp) {
		return gp.getProperty() + " - " + gp.getPropertyValue();
	}
}
