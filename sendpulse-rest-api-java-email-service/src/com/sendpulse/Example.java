package com.sendpulse;

import com.sendpulse.restapi.PersonalData;
import com.sendpulse.restapi.Sendpulse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Bruna Delmouro
 * */

public class Example {
    // https://login.sendpulse.com/settings/#api
    private static String userId = "5e14191f99644a02c3fd04bde7c43751"; // **insert ID**
    private static String secret = "3e91766291ab769c4dac7adbe50cb7b5"; // **insert secret**

    public static void main(String[] args) {
        Sendpulse sendpulse = new Sendpulse(userId, secret);

        //PersonalData p1 = new PersonalData("bruna@inovags.com", "Sistemas de Informação", "Belo Horizonte");
        //PersonalData p2 = new PersonalData("d2021001809@unifei.edu.br", "Administração", "São Paulo");
       // PersonalData p3 = new PersonalData("suporte@inovags.com", "Engenharia", "Belo Horizonte");
        //PersonalData p4 = new PersonalData("rodrigo@inovags.com", "Sistemas de Informação", "Belo Horizonte");

        //List<PersonalData> list = new ArrayList<>();
        //list.add(p1);
        //list.add(p2);
        //list.add(p3);
        //list.add(p4);

        /* Creating and adding data by CSV */
        List<PersonalData> listCSV = new ArrayList<>();

        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("CSVTest.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);

                listCSV.add(new PersonalData(data[0], data[1], data[2])); //email, cidade and curso
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* Creating a Mailing List */
        /* Get list_id by JSON format */
        JSONObject addressBook = new JSONObject(sendpulse.
                createAddressBook("lista-contatos-java[" + java.time.LocalDateTime.now() + "]"));

        String listId = addressBook.toString(2).substring(19, 25); //get a specific id
        System.out.println("bookName: " + "lista-contatos-java[" + java.time.LocalDateTime.now() + "]");
        System.out.println("listId: " + listId + "\n");


        /* Creating a JSON format to add the emails and variables */
        StringBuilder listEmailsJsonFormat = new StringBuilder();

        listEmailsJsonFormat.append("[");
        for (int j = 0; j < listCSV.size(); j++) {
            listEmailsJsonFormat.append("{"
                    + "\"email\":\"" + listCSV.get(j).getEmail() + "\","
                    + "\"variables\":{"
                    + "\"cidade\":\"" + listCSV.get(j).getCidade() + "\","
                    + "\"curso\":\"" + listCSV.get(j).getCurso() + "\""
                    + "}"
                    + "},");
        }
        listEmailsJsonFormat.append("]");


        /* Removing the last comma */
        for (int i = listEmailsJsonFormat.toString().length() - 1; i >= 0; i--) {
            //System.out.println(i);
            if (listEmailsJsonFormat.charAt(i) == ',') {
                listEmailsJsonFormat.setCharAt(i, ' ');
                //System.out.println("OK");
                break;
            } else {
                continue;
            }
        }
        System.out.println("listEmailsJsonFormat: ");
        System.out.println(listEmailsJsonFormat);
        System.out.println("----------------------------------------------------------------------------");

        /* Add Emails to a Mailing List */
        System.out.println(sendpulse.addEmails(Integer.parseInt(listId), listEmailsJsonFormat.toString()));
        System.out.println("E-mails list added!");

        /* Add a time sleep between mailing list and campaign */
        try {
            Thread.sleep(120000); //2 minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * x minutes
         * */

        /* Add an exception if the list creation or campaign creation returns an error */
        /**
         * try-catch
         * */

        System.out.println("----------------------------------------------------------------------------");

        /* -------------------------------------------------------------------------------- */

        //templateId: 22285
        /* Creating and sending a campaign */
        /**
         * Sending email via SendPulse Email Service
         * @param senderName - Estágio Online
         * @param senderEmail - e-mail do Estágio Online
         * @param subject - título do e-mail
         * @param templateId - id do template a ser utilizado
         * @param listId - id da lista de contatos criada anteriormente
         * @param name - nome da campanha
         * @param attachments - anexos
         */
        // **insert a sender e-mail**
        // **insert a template id**
        System.out.println(sendpulse.createCampaign("Bruna Delmouro", "brunadelmouro@gmail.com", "Test 26/12 - Email Service SendPulse with AddressesBook", 22285, Integer.parseInt(listId), "Final test", ""));
        System.out.println("Campaign created!");

        System.out.println("----------------------------------------------------------------------------");
        //System.out.println(sendpulse.listCampaigns(0, 0));
    }
}
