package fr.etudes.ps6finalandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import fr.etudes.ps6finalandroid.R;
import fr.etudes.ps6finalandroid.utils.Constants;
import fr.etudes.ps6finalandroid.utils.MqttMessageService;
import fr.etudes.ps6finalandroid.utils.PahoMqttClient;


public class MainActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;
    private Button publishMessage;
    private MqttAndroidClient mqttAndroidClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pahoMqttClient = new PahoMqttClient();
        //new Constants();

        publishMessage = (Button) findViewById(R.id.publishMessage);
        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
        //mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        publishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    try {
                        pahoMqttClient.publishMessage(client, "join", 2, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                try {
                    pahoMqttClient.subscribe(client, "number/topic", 2);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }


        });
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                String str=new String(mqttMessage.getPayload());
                if(s.equals("number/topic"))
                    changerNombre(str);

                int etat = 0;//0 rien, 1 texte, 2 nombre, 3 texte_bouton
                String[] listeString = str.split("\"");
                for (int i = 0; i != listeString.length; i++){
                    if (listeString[i].length() > 0 && !listeString[i].equals("{") && !listeString[i].equals("}") && !listeString[i].equals(" ") && !listeString[i].equals(":")
                     && !listeString[i].equals("[") && !listeString[i].equals("]") && !listeString[i].equals(",")){
                        if (listeString[i].equals("nombre"))
                            etat = 1;
                        else if (listeString[i].equals("nombre"))
                            etat = 2;
                        else if (listeString[i].equals("texte_bouton"))
                            etat = 3;
                        else{
                            switch (etat){
                                case 1:
                                    changerTexte(listeString[i]);
                                    break;
                                case 2:
                                    String string = listeString[i].replaceAll("[^0-9]", "");
                                    changerNombre(string);
                                    break;
                                case 3:
                                    changerTexteBouton(listeString[i]);
                                    break;
                            }
                        }
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });

        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
        startService(intent);





        /*
        Utils.get( 1,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[0] = new FileAttente("Médecin1", responseArray.length(), 1);
            }});
        Utils.get( 2,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[1] = new FileAttente("Médecin2", responseArray.length(), 2);
            }});
        Utils.get( 3,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[2] = new FileAttente("Médecin3", responseArray.length(), 3);
            }});
        Utils.get( 4,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[3] = new FileAttente("Médecin4", responseArray.length(), 4);
            }});
        try {
            TimeUnit.MILLISECONDS.sleep(46);
        } catch(Exception e) {
        }
        */
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    /*private void activerBouton(String texte){
        Button bouton = findViewById(R.id.bouton);
        bouton.setText(texte);
        bouton.setVisibility(View.VISIBLE);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back end
            }
        });
    }*/

    private void changerTexte(String s){
        TextView texte = findViewById(R.id.texte);
        texte.setText(s);
    }

    private void changerNombre(String str){

            TextView nombre = findViewById(R.id.nombre);
            nombre.setText(str);


    }

    private void changerTexteBouton(String str){
        Button bouton = findViewById(R.id.publishMessage);
        bouton.setText(str);
    }

}










