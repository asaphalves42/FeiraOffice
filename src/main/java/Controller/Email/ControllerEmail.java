package Controller.Email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class ControllerEmail {

    /**
     * Envia um email contendo as credenciais de acesso à Feira Office.
     *
     * @param username O endereço de email do destinatário.
     * @param password A senha a ser enviada no email.
     */
    public void enviarEmail( String username, String password) {
        String from = "jakartafrom@example.com";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("engenheirosemcurso@gmail.com", "fhhjuclsiiqefslx");
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
            message.setSubject("Credenciais de acesso à Feira Office!");

            // Corpo do email em HTML com tabela
            String htmlContent = "<html><body style='font-family: Arial, sans-serif;'>" +
                    "<div style='background-color: #f2f2f2; padding: 20px;'>" +
                    "<h1 style='color: #333333;'>Credenciais de acesso à Feira Office</h1>" +
                    "<h2 style='color: #666666;'>Bem-vindo(a) à Feira Office</h2>" +
                    "</div>" +
                    "<table style='border-collapse: collapse; margin-top: 20px;'>" +
                    "<tr style='background-color: #f2f2f2;'>" +
                    "<th style='border: 1px solid black; padding: 8px;'>Informação</th>" +
                    "<th style='border: 1px solid black; padding: 8px;'>Detalhes</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='border: 1px solid black; padding: 8px;'><strong>Email</strong></td>" +
                    "<td style='border: 1px solid black; padding: 8px;'>" + username + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='border: 1px solid black; padding: 8px;'><strong>Password</strong></td>" +
                    "<td style='border: 1px solid black; padding: 8px;'>" + password + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</body></html>";

            message.setContent(htmlContent, "text/html");

            Transport.send(message);
            System.out.println("Email enviado com sucesso");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
