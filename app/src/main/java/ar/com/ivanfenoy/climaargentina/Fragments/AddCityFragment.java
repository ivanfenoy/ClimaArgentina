package ar.com.ivanfenoy.climaargentina.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import ar.com.ivanfenoy.climaargentina.Controllers.SharedPreferencesController;
import ar.com.ivanfenoy.climaargentina.MainActivity;
import ar.com.ivanfenoy.climaargentina.R;
import ar.com.ivanfenoy.climaargentina.Utils.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCityFragment extends DialogFragment {
    @Bind(R.id.sp_states) Spinner mSpStates;
    @Bind(R.id.sp_cities) Spinner mSpCities;
    @Bind(R.id.btn_add) Button mBtnAdd;

    private String mCities = "#Aeroparque Buenos Aires#Buenos Aires|#9 Julio#Avellaneda#Azul#Bahía Blanca#Balcarce#Banfield#Benito Juárez#Bolívar#Carmen Patagones#Chacabuco#Chascomús#Ciudad Evita#Coronel Pringles#Coronel Suarez#Dolores#Don Torcuato#El Palomar#Ezeiza#General Villegas#Junín#La Plata#Las Flores#Lomas Zamora#Maipú#Marl Plata#Merlo#Miramar#Monte Hermoso#Morón#Necochea#Olavarría#Pehuajó#Pergamino#Pigué#Pinamar#Punta Indio B.A.#Quilmes#San Andrés#San Antonio Areco#SanconvertUTF82Char: error1 BE!rnardo#San Clementel Tuyú#SanconvertUTF82Char: error1 FE!rnando#San Isidro#San Miguel#San Pedro#Santa Teresita#Tandil#Tigre#Trenque Lauquen#Tres Arroyos#Villa Gesell#Zárate|#Andalgalá#Belén#Catamarca#Santa María#Tinogasta|#General José San Martín#Pcia. Roque Saenz Peña#Resistencia#Villa Angela|#Comodoro Rivadavia#Esquel#Paso Indios#Puerto Madryn#Rawson#Trelew|#Almafuerte#Alta Gracia#Bell Ville#Córdoba#Córdoba Observatorio#Coronel Moldes#Cosquín#Cruzl Eje#Larlota#Laboulaye#Marcos Juárez#Mina Clavero#Pilar Obs.#Río Cuarto#Río Tercero#San Francisco#Villa Dolores#Villa GeneralconvertUTF82Char: error1 BE!lgrano#Villa Huidobro#Villa María#Villa Maríal Río Seco|#Corrientes#Curuzú Cuatiá#Empedrado#Goya#Ituzaingó#Monteseros#Paso Los Libres|#Colón#Concepciónl Uruguay#Concordia#Gualeguay#Gualeguaychú#La Paz#Paraná|#Clorinda#Formosa#Las Lomitas#Pirane|#Puerto Argentino|#Humahuaca#Jujuy#La Quiaca#Libertador General San Martín#Palpalá#Perico#San Pedro (J)|#25 Mayo#Eduardostex#GeneralconvertUTF82Char: error1 AC!ha#General Pico#Intendente Alvear#Santa Rosa|#Aimogasta#Chamical#Chepes#Chilecito#La Rioja|#General Alvear#Malargue#Mendoza#Mendoza Observatorio#Punta Vacas#Sanrlos#San Martín (Mza)#San Rafael#Tunuyán#Uspallata|#Apóstoles#Bernardo Irigoyen#Eldorado#Iguazú#Oberá#Posadas|#Chapelco#Cutral Co#Neuquén#San Martín los Andes#Villa La Angostura#Zapala|#Bariloche#Choele Choel#Cipolletti#El Bolsón#General Roca#Maquinchao#Río Colorado#San Antonio Oeste#Viedma#Villa Regina|#Metán#Orán#Rivadavia#Salta#Tartagal|#Caucete#Jachal#San Juan|#J.ract#La Toma#Merlo (SL)#San Luis#Santa Rosal Conlara#Villa Reynolds|#Caleta Olivia#Ellafate#Gobernador Gregores#Perito Moreno#Puertoseado#Río Gallegos#San Julián#Santa Cruz|#Casilda#Ceres#El Trébol#Las Rosas#Melincué#Rafaela#Reconquista#Rosario#San Cristóbal#San Javier#SantaconvertUTF82Char: error1 FE!#Sastre#Sunchales#Tostado#Venado Tuerto|#Añatuya#Frías#Santiagol Estero#Termas Río Hondo|#Río Grande B.A.#Tolhuin#Ushuaia|#Aguilares#Concepción#Tafí Viejo#Tucumán";
    private ArrayList<String> mListCities = new ArrayList<>();

    public AddCityFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((int)(getResources().getDisplayMetrics().widthPixels*((float)0.9)), LinearLayout.LayoutParams.WRAP_CONTENT
        );
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillCities(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillCities(int pPosition) {
        mListCities.clear();
        mListCities.add(getString(R.string.lbl_select_city));
        ArrayAdapter wAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,mListCities);
        mSpCities.setAdapter(wAdapter);

        String[] wLista = mCities.split("\\|");
        String[] wLista2 = wLista[pPosition].split("#");
        for (int wCount = 1; wCount < wLista2.length; wCount++)
        {
            mListCities.add(wLista2[wCount]);
        }
        wAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_add)
    public void AddCity(View pView){
        if (mSpCities.getSelectedItemPosition() != 0) {
            ((MainActivity)getActivity()).putNewCity(mSpCities.getSelectedItem().toString().trim(), mSpStates.getSelectedItemPosition());
            SharedPreferencesController.saveCity(getActivity(), mSpCities.getSelectedItem().toString().trim());
            this.dismiss();
        }
        else{
            Util.Toast(getActivity(), getString(R.string.lbl_city_dont_selected));
        }
    }

}
