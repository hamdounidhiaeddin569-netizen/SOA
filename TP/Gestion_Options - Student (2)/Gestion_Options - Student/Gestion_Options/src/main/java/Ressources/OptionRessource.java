package Ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionRessource {

    private OptionBusiness optionBusiness = new OptionBusiness();

    // A.1 POST /options -> 200 | 404
    @POST
    public Response addOption(Option option) {
        if (option != null && optionBusiness.addOption(option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // A.2 GET /options  et  A.3 GET /options?domaine=Mathématiques
    @GET
    public List<Option> getOptions(@QueryParam("domaine") String domaine) {
        if (domaine == null || domaine.isEmpty()) {
            return optionBusiness.getListeOptions();
        }
        return optionBusiness.getOptionsByDomaine(domaine);
    }

    // A.6 GET /options/1 -> 200 | 404
    @GET
    @Path("/{code}")
    public Response getByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // A.4 DELETE /options/2 -> 204 | 404
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // A.5 PUT /options/1 -> 200 | 404
    @PUT
    @Path("/{code}")
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (optionBusiness.updateOption(code, option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}