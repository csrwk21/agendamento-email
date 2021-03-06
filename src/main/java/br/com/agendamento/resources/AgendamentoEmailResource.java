package br.com.agendamento.resources;

import java.util.List;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.agendamento.business.AgendamentoEmailBusiness;
import br.com.agendamento.entity.AgendamentoEmail;
import br.com.agendamento.exception.BussinesException;

@Path("/agendamentoemail")
public class AgendamentoEmailResource {
	
	
	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAgendamentosEmail() {
		
		List<AgendamentoEmail> emails = agendamentoEmailBusiness.listarAgemndamentosEmail();
		return Response.ok(emails).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail ) throws BussinesException {
		
		agendamentoEmailBusiness.salvarAgendamentoEmail(agendamentoEmail);
		
		return Response
				.status(201)
				.build();
	}

}
