package com.sendpulse;

import com.sendpulse.restapi.JsonTools;
import com.sendpulse.restapi.Sendpulse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

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

        System.out.print("Nome da campanha: ");
        String campaignName = SC.nextLine();

        System.out.println("Lista de templates: ");

        /* Return a list of all user templates */
        JSONObject templates = new JSONObject(sendpulse.getTemplatesFromUser("me"));

        /* Convert a JSONObject to ArrayList */
        List<Object> result = jsonToArrayList(templates);

        System.out.println("----------------------------------------------------------------------------");

        for (int i = 0; i < result.size(); i++) {
            String[] infos;

            infos = extractInfo(result, "\"preview\":", i);
            System.out.println(infos[0]);

            infos = extractInfo(result, "\"real_id\":", i);
            System.out.println(infos[0]);

           infos = extractInfo(result, "\"name\":", i);
           System.out.println(infos[0]);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("----------------------------");
        }


        System.out.println("----------------------------------------------------------------------------");

        System.out.print("\nEscolha o template, copie o Id e cole no console: ");
        Integer idTemplate = SC.nextInt();

        System.out.println("----------------------------------------------------------------------------");

        //templateId: 1656009
        //bookId: 89398056
        /* Creating and sending a campaign */
        int res = 0;

        do {
            System.out.println("Deseja enviar a campanha?[1-Sim/2-Não]");
            res = SC.nextInt();

            if (res == 1) {
                System.out.println(
                        sendpulse.createCampaign(
                                "Estágio Online",
                                "contato@estagioonline.com",
                                emailTitle,
                                idTemplate,
                                Integer.parseInt(listId),
                                campaignName,
                                "")
                );
                System.out.println("Campaign created!");
                break;
            } else {
                continue;
            }

        } while (res != 1);


        System.out.println("----------------------------------------------------------------------------");

    }

    public static List<Object> jsonToArrayList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        ArrayList<Object> listdata = new ArrayList<>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                listdata.add(jsonArray.get(i));
            }
        }
        return listdata;
    }

    public static String[] extractInfo(List<Object> result, String info, int index){
        String array[] = (result.get(index).toString().substring( result
                                                .get(index)
                                                .toString()
                                                .lastIndexOf(info)))
                                                .split(",");

        return array;
    }


}
/* https://processing.github.io/processing-javadocs/core/processing/data/JSONObject.html */