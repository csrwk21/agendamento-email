package br.com.agendamento.mdb;

import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import br.com.agendamento.business.AgendamentoEmailBusiness;
import br.com.agendamento.entity.AgendamentoEmail;
import br.com.agendamento.interception.Logger;

import javax.ejb.ActivationConfigProperty;


@Logger
@MessageDriven(activationConfig = {
		
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:/jms/queue/EmailQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})

public class EmailMDB implements MessageListener {
	
    @Inject
    private AgendamentoEmailBusiness agendamentoEmailBusiness;
    
    @Override
    public void onMessage(javax.jms.Message message) {
    	
    	try {
    		
			AgendamentoEmail agendamentoEmail = message.getBody(AgendamentoEmail.class);
			agendamentoEmailBusiness.enviarEmail(agendamentoEmail);
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
			
		}
    }
}