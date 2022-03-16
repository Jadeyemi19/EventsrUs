package com.example.eventsrus;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.eventsrus.Common.Common;
import com.example.eventsrus.Model.WeatherResult;
import com.example.eventsrus.Retrofit.IOpenWeatherMap;
import com.example.eventsrus.Retrofit.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.function.Consumer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class TodayWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView txt_city_name,txt_humidity,txt_sunrise,txt_sunset,txt_pressure,txt_temperature,txt_description,txt_date_time,txt_wind,txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;


   static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null) {
           instance=new TodayWeatherFragment();
        }
        return instance;
    }

    public TodayWeatherFragment(){
        compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        mService=retrofit.create(IOpenWeatherMap.class);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View itemView= inflater.inflate(R.layout.fragment_today_weather, container, false);
       img_weather=(ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name=(TextView) itemView.findViewById(R.id.txt_city_name);
        txt_humidity=(TextView) itemView.findViewById(R.id.txt_humidity);
        txt_sunrise=(TextView) itemView.findViewById(R.id.txt_sunrise);
        txt_sunset=(TextView) itemView.findViewById(R.id.txt_sunset);
        txt_pressure=(TextView) itemView.findViewById(R.id.txt_pressure);
        txt_temperature=(TextView) itemView.findViewById(R.id.txt_temperature);
        txt_description=(TextView) itemView.findViewById(R.id.txt_description);
        txt_date_time=(TextView) itemView.findViewById(R.id.txt_date_time);
        txt_wind=(TextView) itemView.findViewById(R.id.txt_wind);
        txt_geo_coord=(TextView) itemView.findViewById(R.id.txt_geo_coord);
        weather_panel=(LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading=(ProgressBar) itemView.findViewById(R.id.loading);

        getWeatherInformation();
        return itemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWeatherInformation() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            compositeDisposable.add(mService.getWeatherByLatLang(String.valueOf(Common.current_location),
                    String.valueOf(Common.current_location.getLongitude()),
                    Common.APP_ID,
                    "metric")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WeatherResult>(){
                        @Override
                        public void accept(WeatherResult weatherResult) throws Exception{

                        }
                        , new Consumer<Throwable>(){
                            @Override
                                    public void accept(Throwable throwable) throws Exception{
                                Toast.makeText(getActivity(),""+throwable.getMessage(),Toast.LENGTH_SHORT.show();
                            }
                        }
                    })
            )
        }
    }
}