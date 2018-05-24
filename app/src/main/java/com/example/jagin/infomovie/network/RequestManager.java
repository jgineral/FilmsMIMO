package com.example.jagin.infomovie.network;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//Manejador para encolar las peticiones.
public class RequestManager {
    private  static RequestManager instance;
    private RequestQueue requestQueue;  //cola para nuestras peticiones.

    public RequestManager(){

    }

    //devolvemos una instancia para que se pueda usar. Lo hacemos sync para que solo tengamos una instancia de ella a causa de dos llamadas.
    public static synchronized RequestManager getInstance(){
        if(instance == null){
            instance = new RequestManager();
        }
        return instance;
    }

    //comprobar si existe y si no la crea.
    public RequestQueue getRequestQueue(Context ctx){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }

        return  requestQueue;
    }

    //añadir petición
    public <T> void addRequest(Context ctx, Request<T> req){
        getRequestQueue(ctx).add(req);
    }


}
