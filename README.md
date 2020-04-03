# agendamento-email

Comanos para fazer dentro da CLI no servidor WildFLy

/subsystem=mail/mail-session=agendamentoMailSession:add(jndi-name=java:jboss/mail/AgendamentoMailSession)


/socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=my-smtp-binding:add(host=smtp.mailtrap.io, port=2525)

 Criei um email no  Mailtrap para pegar o user e o password

/subsystem=mail/mail-session=agendamentoMailSession/server=smtp:add(outbound-socket-binding-ref= my-smtp-binding, username="seu usuario", password="sua senha", tls=true)

Comando para adicionar o JMS 

jms-queue add --queue-address=EmailQueue --entries=java:/jms/queue/EmailQueue
