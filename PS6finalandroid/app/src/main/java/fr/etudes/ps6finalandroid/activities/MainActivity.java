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

import static fr.etudes.ps6finalandroid.utils.Constants.MQTT_BROKER_URL;


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


        publishMessage = (Button) findViewById(R.id.publishMessage);
        client = pahoMqttClient.getMqttClient(getApplicationContext(), MQTT_BROKER_URL, Constants.CLIENT_ID);
        //mqttAndroidClient = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);




        publishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!publishMessage.getText().equals("Quitter la file")) {
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
                }else{
                    try {
                        pahoMqttClient.publishMessage(client, "suppr", 2, Constants.SUPPR_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
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
                String str = new String(mqttMessage.getPayload());

                    int etat = 0;//0 rien, 1 texte, 2 nombre, 3 texte_bouton
                    String[] listeString = str.split("\"");
                    for (int i = 0; i != listeString.length; i++) {
                        if (listeString[i].length() > 0 && !listeString[i].equals("{") && !listeString[i].equals("}") && !listeString[i].equals(" ") && !listeString[i].equals(":")
                                && !listeString[i].equals("[") && !listeString[i].equals("]") && !listeString[i].equals(",") && !listeString[i].equals("}]")) {
                            if (listeString[i].equals("texte"))
                                etat = 1;
                            else if (listeString[i].equals("nombre"))
                                etat = 2;
                            else if (listeString[i].equals("texte_bouton"))
                                etat = 3;
                            else {
                                switch (etat) {
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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void changerTexte(String s) {
        TextView texte = findViewById(R.id.texte);
        texte.setText(s);
    }

    private void changerNombre(String str) {

        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(str);


    }

    private void changerTexteBouton(String str) {
        Button bouton = findViewById(R.id.publishMessage);
        bouton.setText(str);
    }

}










