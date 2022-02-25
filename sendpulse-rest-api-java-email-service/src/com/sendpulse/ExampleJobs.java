package com.sendpulse;

import com.sendpulse.restapi.JsonTools;
import com.sendpulse.restapi.Sendpulse;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*
 * Estágio Online
 * */

public class ExampleJobs {

    static final Scanner SC = new Scanner(System.in);

    // https://login.sendpulse.com/settings/#api
    private static String userId = "2be7d28fc637441d8358c940a8df871b"; // **insert ID**
    private static String secret = "0cf8894c2a35732ce8ee9b7b336f5a94"; // **insert secret**

    public static void main(String[] args) {
        Sendpulse sendpulse = new Sendpulse(userId, secret);

        /* Creating a Mailing List */
        /* Get list_id by JSON format */
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy_hh:mm:ss_a");
        Date now = new Date();

        JSONObject addressBook = new JSONObject(sendpulse.
                createAddressBook("contatos[" + sdf.format(now) + "]"));


        String listId = addressBook.toString(2).substring(19, 27); //get a specific id

        System.out.println("bookName: " + "contatos[" + sdf.format(now) + "]");
        System.out.println("listId: " + listId + "\n");


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

        System.out.print("Título do e-mail: ");
        String emailTitle = SC.nextLine();

        //adicionar template - listar templates

        System.out.print("Nome da campanha: ");
        String campaignName = SC.nextLine();

        System.out.println("----------------------------------------------------------------------------");

        //templateId: 1656009
        //bookId: 89398056
        /* Creating and sending a campaign */
        System.out.println(
                sendpulse.createCampaign("Estágio Online",
                        "contato@estagioonline.com",
                        emailTitle,
                        1656009,
                        Integer.parseInt(listId),
                        campaignName,
                        "")
        );

        System.out.println("Campaign created!");
        System.out.println("----------------------------------------------------------------------------");

    }
}
