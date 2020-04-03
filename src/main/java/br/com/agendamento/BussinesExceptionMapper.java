package br.com.agendamento;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.agendamento.dto.MensagemErroDto;
import br.com.agendamento.exception.BussinesException;

@Provider
public class BussinesExceptionMapper implements javax.ws.rs.ext.ExceptionMapper <BussinesException> {

	@Override
	public Response toResponse(BussinesException exception) {
		return Response
	            .status(Response.Status.BAD_REQUEST)
	            .entity( MensagemErroDto.build(exception.getMensagens()))
	            .build();
	}

}
