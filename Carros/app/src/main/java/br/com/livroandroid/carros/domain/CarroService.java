package br.com.livroandroid.carros.domain;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;
import livroandroid.lib.utils.HttpHelper;

public class CarroService {
    private static final String
            URL = "http://livrowebservices.com.br/rest/carros/tipo/%s";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String url = String.format(URL, tipoString);

        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Carro>>() {
        }.getType();
        List<Carro> carros = gson.fromJson(json, listType);

        return carros;
    }

    // Converte a constante para string, para criar a URL do web service
    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        }
        return "luxo";
    }
}