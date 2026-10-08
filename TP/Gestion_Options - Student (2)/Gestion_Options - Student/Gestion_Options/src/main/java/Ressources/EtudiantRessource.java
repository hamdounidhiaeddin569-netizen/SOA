package Ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantRessource {

    private EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // B.1 POST /etudiants -> 200 | 404 (option inexistante)
    @POST
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.2 GET /etudiants -> 200
    @GET
    public List<Etudiant> getAllEtudiants() {
        return etudiantBusiness.getAllEtudiants();
    }

    // B.3 GET /etudiants/I003 -> 200 | 404
    @GET
    @Path("/{identifiant}")
    public Response getByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant e = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // B.4 DELETE /etudiants/I003 -> 204 | 404
    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.5 PUT /etudiants/I001 -> 200 | 404
    @PUT
    @Path("/{identifiant}")
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.6 GET /etudiants/option?codeOption=1 -> XML | 404
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> liste = etudiantBusiness.getEtudiantsByOption(option);
        return Response.ok(new EtudiantList(liste)).build();
    }
}