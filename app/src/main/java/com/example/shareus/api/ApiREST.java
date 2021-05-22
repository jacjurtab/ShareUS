package com.example.shareus.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareus.Session;
import com.example.shareus.model.Pasajero;
import com.example.shareus.model.Ubicacion;
import com.example.shareus.model.Usuario;
import com.example.shareus.model.Valoracion;
import com.example.shareus.model.Viaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class ApiREST {
    private static ApiREST apiREST = null;
    private final Context context;
    private RequestQueue queue;
    private static final String BASE = "https://api.share-us.tech";


    private ApiREST(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public static ApiREST getInstance(Context context) {
        if(apiREST == null) {
            apiREST = new ApiREST(context);
        }
        return apiREST;
    }
    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public interface Callback {
        void onResult(Object res);
    }

    /**
     * Peticiones relacionadas con VIAJES
     * */
    public static void crearViaje(int idConductor, int idOrigen, int idDestino, long fecha_hora, int max_plazas,
                                  float precio, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viaje";
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.POST, url, res -> {
            callback.onResult(res);
            System.out.println("[REST][insertarPasajero]Respuesta crearViaje: " + res);
        }, error ->
                System.out.println("[REST] Error respuestas: crearViaje :"+error.getMessage()))
        {
           @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                JSONObject json = new JSONObject();
                try {
                    json.put("conductor", idConductor);
                    json.put("origen", idOrigen);
                    json.put("destino", idDestino);
                    json.put("fecha_hora", fecha_hora);
                    json.put("max_plazas", max_plazas);
                    json.put("precio", precio);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return json.toString().getBytes();
            }
        };
        mRequestQueue.add(request);
    }


    public static void obtenerViajes(boolean disponible, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viajes?disponibles=" + disponible;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Viaje> viajes = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Viaje viaje = null;
                    JSONObject item = res.getJSONObject(j);
                    List<Pasajero> pasajeros = new ArrayList<>();

                    JSONArray pasajeros_js = item.getJSONArray("pasajeros");
                    for (int i = 0; i < pasajeros_js.length(); i++) {
                        JSONObject pasajero = pasajeros_js.getJSONObject(i);
                        pasajeros.add(new Pasajero(pasajero.getInt("id"),
                                pasajero.getString("nombre")));
                    }
                    viaje = new Viaje(item.getInt("id"),
                            item.getInt(("idConductor")),
                            item.getString("conductor"),
                            item.getString("origen"),
                            item.getString("destino"),
                            new Timestamp(item.getLong("fecha_hora")),
                            item.getInt("num_pasajeros"),
                            item.getInt("max_plazas"),
                            pasajeros,
                            Float.parseFloat(item.getString("nota_conductor")),
                            Float.parseFloat(item.getString("precio")));
                    viajes.add(viaje);

                }
                callback.onResult(viajes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerViajesDisponibles] Respuesta recibida: " + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerViajesDisponibles  :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    public static void obtenerViajesUbi(String origen, String destino, boolean disponible,
                                        RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viajes?origen=" + origen + "&destino=" + destino + "&disponibles=" + disponible;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Viaje> viajes = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Viaje viaje = null;
                    JSONObject item = res.getJSONObject(j);

                    viaje = new Viaje(item.getInt("id"),
                            item.getInt(("idConductor")),
                            item.getString("conductor"),
                            item.getString("origen"),
                            item.getString("destino"),
                            new Timestamp(item.getLong("fecha_hora")),
                            item.getInt("num_pasajeros"),
                            item.getInt("max_plazas"),
                            null,
                            Float.parseFloat(item.getString("nota_conductor")),
                            Float.parseFloat(item.getString("precio")));
                    viajes.add(viaje);

                }
                Log.d("DEBUG", "Preparando callback");
                callback.onResult(viajes);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("DEBUG", "Error bbdd");
            }
            System.out.println("[REST][obtenerViajesUbi] Respuesta recibida: " + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerViajesUbi  :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    public static void obtenerViaje(int idViaje, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viaje/" + idViaje;
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, res -> {
            Viaje viaje;
            List<Pasajero> pasajeros = new ArrayList<>();
            try {
                viaje = new Viaje(res.getInt("id"),
                        res.getInt(("idConductor")),
                        res.getString("conductor"),
                        res.getString("origen"),
                        res.getString("destino"),
                        new Timestamp(res.getLong("fecha_hora")),
                        res.getInt("num_pasajeros"),
                        res.getInt("max_plazas"),
                        pasajeros,
                        Float.parseFloat(res.getString("nota_conductor")),
                        Float.parseFloat(res.getString("precio")));

                if(viaje.getNum_pasajeros() > 0) {
                    JSONArray pasajeros_js = res.getJSONArray("pasajeros");
                    for (int i = 0; i < pasajeros_js.length(); i++) {
                        JSONObject pasajero = pasajeros_js.getJSONObject(i);
                        pasajeros.add(new Pasajero(pasajero.getInt("id"),
                                pasajero.getString("nombre")));
                    }
                    viaje.setPasajeros(pasajeros);
                }
                callback.onResult(viaje);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerViaje]Respuesta recibida" + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerViaje");
        });
        mRequestQueue.add(request);
    }

    public static void obtenerViajesCond(int idCond, boolean vencidos, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viajes?conductor=" + idCond + "&vencidos=" + vencidos;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Viaje> viajes = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Viaje viaje = null;
                    JSONObject item = res.getJSONObject(j);

                    viaje = new Viaje(item.getInt("id"),
                            item.getInt(("idConductor")),
                            item.getString("conductor"),
                            item.getString("origen"),
                            item.getString("destino"),
                            new Timestamp(item.getLong("fecha_hora")),
                            item.getInt("num_pasajeros"),
                            item.getInt("max_plazas"),
                            null,
                            Float.parseFloat(item.getString("nota_conductor")),
                            Float.parseFloat(item.getString("precio")));
                    viajes.add(viaje);

                }
                callback.onResult(viajes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerViajesCond]Respuesta recibida" + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerViajesCond  :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    public static void obtenerViajesPas(int idPas, boolean vencidos, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viajes?pasajero=" + idPas + "&vencidos=" + vencidos;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Viaje> viajes = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Viaje viaje = null;
                    JSONObject item = res.getJSONObject(j);

                    viaje = new Viaje(item.getInt("id"),
                            item.getInt(("idConductor")),
                            item.getString("conductor"),
                            item.getString("origen"),
                            item.getString("destino"),
                            new Timestamp(item.getLong("fecha_hora")),
                            item.getInt("num_pasajeros"),
                            item.getInt("max_plazas"),
                            null,
                            Float.parseFloat(item.getString("nota_conductor")),
                            Float.parseFloat(item.getString("precio")));
                    viajes.add(viaje);

                }
                callback.onResult(viajes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerViajesPas]Respuesta recibida" + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerViajesPas :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    public static void eliminarPasajero(int idViaje, int idPasajero, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viaje/" + idViaje + "/pasajeros/eliminar";
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.PUT, url, res -> {
            callback.onResult(res);
            System.out.println("[REST][insertarPasajero]Respuesta eliminarPasajero: " + res);
        }, error ->
                System.out.println("[REST] Error respuestas: eliminarPasajero :"+error.getMessage()))
        {
            @Override
            public byte[] getBody(){
                String id = Integer.toString(idPasajero);
                return id.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "text/plain";
            }
        };
        mRequestQueue.add(request);
    }

    public static void insertarPasajero(int idViaje, int idPasajero, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viaje/" + idViaje + "/pasajeros/insertar";
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.PUT, url, res -> {
            callback.onResult(res);
            System.out.println("[REST][insertarPasajero]Respuesta insertarPasajero: " + res);
        }, error ->
                System.out.println("[REST] Error respuestas: insertarPasajero :"+error.getMessage()))
        {
            @Override
            public byte[] getBody(){
                String id = Integer.toString(idPasajero);
                return id.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "text/plain";
            }
        };
        mRequestQueue.add(request);
    }

    public static void eliminarViaje(int idViaje, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/viaje/" + idViaje;
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.DELETE, url, res -> {
            callback.onResult(res);
            System.out.println("[REST][insertarViaje]Respuesta eliminarPasajero: " + res);
        }, error ->
                System.out.println("[REST] Error respuestas: eliminarViaje :"+error.getMessage()));
        mRequestQueue.add(request);
    }

    /**
     * Consultas relacionadas con UBICACIONES
     * */
    public static void obtenerUbicaciones(int tipo, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/ubicaciones?tipo=" + tipo;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Ubicacion> ubicaciones = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Ubicacion ubicacion= null;
                    JSONObject item = res.getJSONObject(j);
                    ubicacion = new Ubicacion(item.getInt("id"),
                                                item.getString("nombre"),
                                                Float.parseFloat(item.getString("latitud")),
                                                Float.parseFloat(item.getString("longitud")),
                                                item.getInt("tipo"),
                                                 item.getBoolean("disponible")
                    );
                    ubicaciones.add(ubicacion);

                }
                callback.onResult(ubicaciones);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerUbicaciones] Respuesta recibida" + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerUbicaciones  :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    /**
     * Consultas relacionadas con VALORACIONES
     * */
    public static void obtenerValoracion(int idViaje, int idValorado, RequestQueue mRequestQueue, Callback callback) {
        String url = BASE + "/valoracion?viaje=" + idViaje + "&valorado=" + idValorado;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, res -> {

            List<Valoracion> valoraciones = new ArrayList<>();
            try {
                for (int j = 0; j< res.length(); j++) {

                    Valoracion valoracion= null;
                    JSONObject item = res.getJSONObject(j);
                    valoracion = new Valoracion(item.getInt("id"),
                                                item.getInt("viaje"),
                                                item.getString("valorador"),
                                                item.getString("comentario"),
                                                Float.parseFloat(item.getString("nota"))
                    );
                    valoracion.setValorado(idValorado);
                    valoraciones.add(valoracion);

                }
                callback.onResult(valoraciones);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("[REST][obtenerValoraciones] Respuesta recibida" + res.toString());
            VolleyLog.v("Response:%n %s", res);
        }, error -> {
            VolleyLog.e("Error: ", error.getMessage());
            System.out.println("[REST] Error respuestas: obtenerValoraciones  :"+error.getMessage());
        });
        mRequestQueue.add(request);
    }

    public static void crearValoracion(int idViaje, int idValorador, int idValorado, float nota,
                                       RequestQueue mRequestQueue, Callback callback){
        String url = BASE + "/valoracion";
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.POST, url, res -> {
            callback.onResult(res);
            System.out.println("[REST][insertarPasajero]Respuesta crearValoracion: " + res);
        }, error ->
                System.out.println("[REST] Error respuestas: crearValoracion :"+error.getMessage()))
        {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                JSONObject json = new JSONObject();
                try {
                    json.put("viaje", idViaje);
                    json.put("valorador", idValorador);
                    json.put("valorado", idValorado);
                    json.put("nota", nota);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return json.toString().getBytes();
            }
        };
        mRequestQueue.add(request);
    }

    public static void loginOrRegister(String userName, Callback callback) {
        Usuario usuario = new Usuario();
        usuario.setId(2);
        usuario.setUsuario("angrodboh");
        callback.onResult(usuario);
    }
}


