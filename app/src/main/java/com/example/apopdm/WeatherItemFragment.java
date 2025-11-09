package com.example.apopdm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apopdm.databinding.WeatherListBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherItemFragment extends Fragment {

    private static final String TAG = "WeatherItemFragment";

    private DadosTempo dados;
    private WeatherListBinding binding;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = WeatherListBinding.inflate(inflater, container, false);

        // RecyclerView setup
        mRecyclerView = binding.myRecyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        dados = new DadosTempo();

        // Retrofit request
        Retrofit client = new Retrofit.Builder()
                .baseUrl("https://api.hgbrasil.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTempo httpRequest = client.create(ApiTempo.class);

        Call<ApiPojo> call = httpRequest.getInfTempo();
        call.enqueue(callback);

        mAdapter = new ListAdapter(dados);
        mRecyclerView.setAdapter(mAdapter);




        return binding.getRoot();
    }

    private Callback<ApiPojo> callback = new Callback<ApiPojo>() {
        @Override
        public void onResponse(Call<ApiPojo> call, Response<ApiPojo> response) {
            if (response.body() != null) {
                dados.setCidade(response.body().getResults().getCityName());
                dados.setLista(response.body().getResults().getForecast());

                binding.cityText.setText(dados.getCidade());

                mAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Resposta vazia do servidor.");
            }
        }

        @Override
        public void onFailure(Call<ApiPojo> call, Throwable t) {
            Log.e(TAG, "Falha na requisição Retrofit: " + t.getMessage());
        }
    };

}
