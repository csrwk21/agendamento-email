package br.com.agendamento.business;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import br.com.agendamento.dao.AgendamentoEmailDao;
import br.com.agendamento.entity.AgendamentoEmail;
import br.com.agendamento.exception.BussinesException;
import br.com.agendamento.interception.Logger;

@Stateless
@Logger 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AgendamentoEmailBusiness {
	
	
	@Resource(lookup = "java:jboss/mail/AgendamentoMailSession")
	private Session sessaoEmail;
	
	private static String EMAIL_FROM = "mail.address";
	private static String EMAIL_USER = "mail.smtp.user";
	private static String EMAIL_PASSWORD = "mail.smtp.pass";
	
	@Inject
	private AgendamentoEmailDao agendamentoEmailDao;
	
	public List<AgendamentoEmail> listarAgemndamentosEmail(){
	
		return agendamentoEmailDao.listarAgendamentosEmail();
		
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarAgendamentoEmail(@Valid AgendamentoEmail agendamentoEmail) throws BussinesException {
		
		if(!agendamentoEmailDao.listarAgendamentoEmailPorEmail(agendamentoEmail.getEmail()).isEmpty()) {
			throw new BussinesException("Email já está agendado.");
		}
		
		agendamentoEmail.setEnviado(false);
		agendamentoEmailDao.salvarAgendamentoEmail(agendamentoEmail);
		
		throw new BussinesException();
		
	}
	
	public List<AgendamentoEmail> listarAgendamentoEmailNoEnviados (){
		
		return agendamentoEmailDao.listarAgendamentoEmailNoEnviados();
	}
	
	public void marcarEnviadas(AgendamentoEmail agendamentoEmail) {
		
		agendamentoEmail.setEnviado(true);
		agendamentoEmailDao.atualizarAgendamentoEmail(agendamentoEmail);
	}
	
	public void enviarEmail(AgendamentoEmail agendamentoEmail) {
		try {
			
		    MimeMessage mensagem = new MimeMessage(sessaoEmail);
		    mensagem.setFrom(sessaoEmail.getProperty(EMAIL_FROM));
		    mensagem.setRecipients(Message.RecipientType.TO, agendamentoEmail.getEmail());
		    mensagem.setSubject(agendamentoEmail.getAssunto());
		    mensagem.setText(Optional.ofNullable(agendamentoEmail.getMensagem()).orElse(""));
		    Transport.send(mensagem,
		    sessaoEmail.getProperty(EMAIL_USER),
		    sessaoEmail.getProperty(EMAIL_PASSWORD));
		    
		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
	}
}
