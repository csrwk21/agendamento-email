package br.com.agendamento.timer;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import br.com.agendamento.business.AgendamentoEmailBusiness;
import br.com.agendamento.entity.AgendamentoEmail;

@Singleton
public class AgendamentoEmailTimer {
	
	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBussiness;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext context;
	
	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;
	
	@Schedule(hour="*", minute ="*")
	public void enviarEmailsAgendados() {
		
		List<AgendamentoEmail> agendamentoEmails = agendamentoEmailBussiness.listarAgendamentoEmailNoEnviados();
		agendamentoEmails.stream().forEach(agendamentoEmail->{
			context.createProducer().send(queue, agendamentoEmail);
			agendamentoEmailBussiness.marcarEnviadas(agendamentoEmail);
		});
	}
}
