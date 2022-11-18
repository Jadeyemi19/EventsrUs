package com.example.eventsrus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsrus.Adapter.WeatherForecastAdapter;
import com.example.eventsrus.Common.Common;
import com.example.eventsrus.Model.WeatherForecastResult;
import com.example.eventsrus.Retrofit.IOpenWeatherMap;
import com.example.eventsrus.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class ForecastFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    static ForecastFragment instance;
    IOpenWeatherMap mService;
    RecyclerView recyclerView;
    TextView txt_city_name;






    public ForecastFragment() {
        compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        mService=retrofit.create(IOpenWeatherMap.class);
    }


    public static ForecastFragment getInstance() {

        if(instance==null){
            instance=new ForecastFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forecast, container, false);
        txt_city_name= view.findViewById(R.id.txt_city_name);
        recyclerView= view.findViewById(R.id.recycler_forecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
         getForecastWeatherInformation();
         return  view;

    }

    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getWeatherForecastByLatLng(
                Double.toString(Common.current_location.getLatitude()),
                Double.toString(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                            displayForecastWeather(weatherForecastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",""+throwable.getMessage());
                    }
                }
                ));
    }

        private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
                 txt_city_name.setText(new StringBuilder(weatherForecastResult
                 .city.name));
            WeatherForecastAdapter weatherForecastAdapter=new WeatherForecastAdapter(getContext(),weatherForecastResult);
            recyclerView.setAdapter(weatherForecastAdapter);
        
    }

    }