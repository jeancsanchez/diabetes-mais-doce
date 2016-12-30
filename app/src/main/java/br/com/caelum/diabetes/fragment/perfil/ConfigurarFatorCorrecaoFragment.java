package br.com.caelum.diabetes.fragment.perfil;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.caelum.diabetes.R;
import br.com.caelum.diabetes.activity.MainActivity;
import br.com.caelum.diabetes.dao.DadosMedicosDao;
import br.com.caelum.diabetes.dao.DbHelper;
import br.com.caelum.diabetes.extras.Extras;
import br.com.caelum.diabetes.extras.ValidaCampos;
import br.com.caelum.diabetes.model.DadosMedicos;
import br.com.caelum.diabetes.model.TipoDadoMedico;
import br.com.caelum.diabetes.util.ValidatorUtils;

/**
 * Created by fernandabernardo on 18/12/16.
 */
public class ConfigurarFatorCorrecaoFragment extends Fragment {
    private View view;
    private EditText cafe;
    private EditText almoco;
    private EditText jantar;
    private Button salvar;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleHeader("Fator Correção");
        ((MainActivity) getActivity()).setBackArrowIcon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.configurar_fator_correcao, null);
        getValoresGlobais();
        settarTextos();

        ValidaCampos.validateEditText(cafe, salvar);
        ValidaCampos.validateEditText(almoco, salvar);
        ValidaCampos.validateEditText(jantar, salvar);
        salvar.setEnabled(ValidatorUtils.checkIfIsValid(cafe, almoco, jantar));

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DadosMedicos dadosMedicos = new DadosMedicos(TipoDadoMedico.FATOR_CORRECAO);
                dadosMedicos.setCafeManha(Double.parseDouble(cafe.getText().toString()));
                dadosMedicos.setAlmoco(Double.parseDouble(almoco.getText().toString()));
                dadosMedicos.setJantar(Double.parseDouble(jantar.getText().toString()));

                DbHelper helper = new DbHelper(getActivity());

                DadosMedicosDao dadosDao = new DadosMedicosDao(helper);
                dadosDao.salva(dadosMedicos);

                helper.close();

                SharedPreferences settings = getActivity().getSharedPreferences(Extras.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(Extras.PREFS_NAME_FATOR_CORRECAO, true);
                editor.commit();

                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void settarTextos() {
        DbHelper helper = new DbHelper(getActivity());
        DadosMedicosDao dao = new DadosMedicosDao(helper);

        DadosMedicos dadosMedicosAntigo = dao.getDadosMedicosCom(TipoDadoMedico.FATOR_CORRECAO);
        if (dadosMedicosAntigo == null) return;

        cafe.setText(String.valueOf(dadosMedicosAntigo.getCafeManha()));
        almoco.setText(String.valueOf(dadosMedicosAntigo.getAlmoco()));
        jantar.setText(String.valueOf(dadosMedicosAntigo.getJantar()));

        helper.close();
    }

    private void getValoresGlobais() {
        cafe = (EditText) view.findViewById(R.id.valor_cafe_fator_correcao);
        almoco = (EditText) view.findViewById(R.id.valor_almoco_fator_correcao);
        jantar = (EditText) view.findViewById(R.id.valor_jantar_fator_correcao);
        salvar = (Button) view.findViewById(R.id.salvar_insulina_fator_correcao);
    }
}

