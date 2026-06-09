package com.altynbekova.banks.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.altynbekova.banks.R;
import com.altynbekova.banks.databinding.FragmentPageBinding;
import com.altynbekova.banks.db.model.Bank;
import com.altynbekova.banks.util.ApiService;
import com.altynbekova.banks.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment {
    private MapView mapView;
    private FragmentPageBinding binding;
    private MapObjectCollection mapObjects;
    private MapObjectTapListener tapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            Toast.makeText(requireContext(), "tapped map", Toast.LENGTH_LONG).show();
            return true;
        }
    };

    public static MapFragment newInstance(int page) {
        MapFragment fragment = new MapFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(requireContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageBinding.inflate(inflater, container, false);
        mapView = binding.mapview;
        mapView.getMap().move(new CameraPosition(
                new Point(55.030144, 82.920515),
                10.0f,
                0.0f,
                30.0f
        ), new Animation(Animation.Type.LINEAR, 1));

        mapObjects = mapView.getMap().getMapObjects();
        fetchBanksData();

        return binding.getRoot();
    }

    private void fetchBanksData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suggestions.dadata.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);


        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,
                "{ \"query\": \"банк\", \n" +
                        "\t\"locations\":[\n" +
                        "\t\t{\n" +
                        "\t\t\t\"kladr_id\":\"6100000100000\"\n" +
                        "\t\t}, \n" +
                        "\t\t{\n" +
                        "\t\t\t\"kladr_id\":\"5400000100000\"\n" +
                        "\t\t}] }");
        Call<ResponseBody> call = service.suggestions(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    List<Bank> banksResponse = Util.parseResponse(response.body());

                    ImageProvider icon = ImageProvider.fromResource(
                            requireContext(), R.drawable.ic_pin);
                    for (Bank bank : banksResponse) {
                        PlacemarkMapObject placemarkMapObject =
                                mapView.getMap().getMapObjects().addPlacemark(
                                bank.getPoint(),
                                icon
                        );
                        placemarkMapObject.addTapListener(tapListener);
                    }

                    Log.d(Util.TAG, "onResponse: api response " + banksResponse);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Util.TAG, "onFailure: failed request", t);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}