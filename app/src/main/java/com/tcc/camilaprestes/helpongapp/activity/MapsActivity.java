package com.tcc.camilaprestes.helpongapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.Permissoes;
import com.tcc.camilaprestes.helpongapp.model.EnderecoONG;
import com.tcc.camilaprestes.helpongapp.model.EnderecoUsuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private EditText editLocal;
    private List<EnderecoONG> enderecosONG = new ArrayList<>();
    private GoogleMap mMap;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationManager locationManager;
    private LocationListener locationListener;
    private DatabaseReference firebaseRef;
    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        editLocal = findViewById(R.id.editLocal);
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Objeto responsável por gerenciar a localização do usuário
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Localizacao", "onLocationChanged: " + location.toString() );

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                LatLng localUsuario = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(localUsuario)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ponto_user))
                        .title("Meu local"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario,15));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        recuperarEnderecos();

        /*
         * 1) Provedor da localização
         * 2) Tempo mínimo entre atualizacões de localização (milesegundos)
         * 3) Distancia mínima entre atualizacões de localização (metros)
         * 4) Location listener (para recebermos as atualizações)
         * */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    100000000,
                    0,
                    locationListener
            );
        }

    }

    public void mudarLocalizacao(View view){
        String novaLocalizacao = editLocal.getText().toString();

        if(!novaLocalizacao.equals("") || novaLocalizacao != null){
            final Address addressLocal = recuperarEndereco(novaLocalizacao);
            if(addressLocal != null){
                final EnderecoUsuario enderecoUsuario = new EnderecoUsuario();
                enderecoUsuario.setCidade(addressLocal.getSubAdminArea());
                enderecoUsuario.setCep(addressLocal.getPostalCode());
                enderecoUsuario.setBairro(addressLocal.getSubLocality());
                enderecoUsuario.setRua(addressLocal.getThoroughfare());
                enderecoUsuario.setNumero(addressLocal.getFeatureName());
                enderecoUsuario.setLatitude(String.valueOf(addressLocal.getLatitude()));
                enderecoUsuario.setLongitude(String.valueOf(addressLocal.getLongitude()));

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Cidade: " + enderecoUsuario.getCidade());
                mensagem.append("\nRua: " + enderecoUsuario.getRua());
                mensagem.append("\nBairro: " + enderecoUsuario.getBairro());
                mensagem.append("\nNúmero: " + enderecoUsuario.getNumero());
                mensagem.append("\nCep: " + enderecoUsuario.getCep());

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Confirme a localização")
                        .setMessage(mensagem)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LatLng novoLocal = new LatLng(addressLocal.getLatitude(), addressLocal.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(novoLocal).title(enderecoUsuario.getRua()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(novoLocal,15));

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }else{
            Toast.makeText(this, "Informe uma localização", Toast.LENGTH_SHORT).show();
        }
    }

    private Address recuperarEndereco(String endereco){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listaEnderecos = geocoder.getFromLocationName(endereco,1);
            if(listaEnderecos != null && listaEnderecos.size() > 0){
                Address address = listaEnderecos.get(0);

                double lat = address.getLatitude();
                double lon = address.getLongitude();

                return address;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {

            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //Recuperar localizacao do usuario

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            100000000,
                            0,
                            locationListener
                    );
                }

            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void recuperarEnderecos(){
        DatabaseReference enderRef = firebaseRef
                .child("enderecos");

        enderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enderecosONG.clear();
                for(DataSnapshot ender: dataSnapshot.getChildren()){
                    for(DataSnapshot enderOng: ender.getChildren()) {
                        enderecosONG.add(enderOng.getValue(EnderecoONG.class));
                    }
                }

                for (EnderecoONG end: enderecosONG) {
                    Double latitude = Double.parseDouble(end.getLatitude());
                    Double longitude = Double.parseDouble(end.getLongitude());

                    LatLng localONG = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions()
                            .position(localONG)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .title(end.getRua()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
