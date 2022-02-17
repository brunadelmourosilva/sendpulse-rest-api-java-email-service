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

public class ExampleJobs {

    // https://login.sendpulse.com/settings/#api
    private static String userId = "730c093d2a1335bc027fbc508a8fa20e"; // **insert ID**
    private static String secret = "cc3af0d755e97713c4df503aed1ef9fa"; // **insert secret**

    public static void main(String[] args) {
        Sendpulse sendpulse = new Sendpulse(userId, secret);

        //PersonalData p1 = new PersonalData("bruna@inovags.com", "Sistemas de Informação", "Belo Horizonte");
        //PersonalData p2 = new PersonalData("d2021001809@unifei.edu.br", "Administração", "São Paulo");
        //PersonalData p4 = new PersonalData("rodrigo@inovags.com", "Sistemas de Informação", "Belo Horizonte");

        //List<PersonalData> list = new ArrayList<>();
        //list.add(p1);
        //list.add(p2);
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

        //ADICIONAR VAGA 1, VAGA 2, VAGA 3
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
         * @param senderName
         * @param senderEmail
         * @param subject
         * @param templateId
         * @param listId
         * @param name
         * @param attachments
         */
        // **insert a sender e-mail**
        // **insert a template id**
        System.out.println(sendpulse.createCampaign("Estágio Online", "", "", 22285, Integer.parseInt(listId), "", ""));
        System.out.println("Campaign created!");

        System.out.println("----------------------------------------------------------------------------");
        //System.out.println(sendpulse.listCampaigns(0, 0));
    }
}
