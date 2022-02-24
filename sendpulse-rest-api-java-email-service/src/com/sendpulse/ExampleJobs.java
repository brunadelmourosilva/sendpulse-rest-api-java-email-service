package com.sendpulse;

import com.sendpulse.restapi.JsonTools;
import com.sendpulse.restapi.PersonalData;
import com.sendpulse.restapi.Sendpulse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Estágio Online
 * */

public class ExampleJobs {

    // https://login.sendpulse.com/settings/#api
    private static String userId = "2be7d28fc637441d8358c940a8df871b"; // **insert ID**
    private static String secret = "0cf8894c2a35732ce8ee9b7b336f5a94"; // **insert secret**

    public static void main(String[] args) {
        Sendpulse sendpulse = new Sendpulse(userId, secret);

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy_hh:mm:ss_a");
        Date now = new Date();

        JSONObject addressBook = new JSONObject(sendpulse.
                createAddressBook("contatos[" + sdf.format(now) + "]"));


        String listId = addressBook.toString(2).substring(19, 27); //get a specific id
        System.out.println("bookName: " + "contatos[" + sdf.format(now) + "]");
        System.out.println("listId: " + listId + "\n");


        /* Creating a JSON format to add the emails and variables */
        //StringBuilder listByJson = listEmailsJsonFormat(listCSV);

        JsonTools jt = new JsonTools();
        String json = null;
        try {
            json = jt.jsonToString("vagas1498.json");

            /* Add Emails to a Mailing List */
            System.out.println(sendpulse.addEmails(Integer.parseInt(listId), json));
            System.out.println("E-mails list added!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------------------------------------------------------");



        /* Add a time sleep between mailing list and campaign */
        try {
            Thread.sleep(300000); //5 minutes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("----------------------------------------------------------------------------");

        //templateId: 1656009
        //bookId: 89398056
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
        System.out.println(
                sendpulse.createCampaign("Estágio Online",
                        "contato@estagioonline.com",
                        "Empresas abriram vagas em sua região e área de atuação",
                        1656009,
                        89398056,
                        "VAGAS - 1498 usuários",
                        "")
        );

        System.out.println("Campaign created!");
        System.out.println("----------------------------------------------------------------------------");

    }

    public static StringBuilder listEmailsJsonFormat(List<PersonalData> listCSV) {
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

        return listEmailsJsonFormat;
    }
}
