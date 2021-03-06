package br.com.caelum.diabetes.fragment.estatisticas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.diabetes.R;
import br.com.caelum.diabetes.activity.MainActivity;
import br.com.caelum.diabetes.dao.DbHelper;
import br.com.caelum.diabetes.dao.GlicemiaDao;
import br.com.caelum.diabetes.extras.PlanilhaExcel;
import br.com.caelum.diabetes.fragment.glicemia.NovaGlicemiaFragment;
import br.com.caelum.diabetes.model.Glicemia;

/**
 * Created by Fernanda Bernardo on 26/04/2016.
 */
public class DashboardEstatisticasFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleHeader("Estatísticas");
        ((MainActivity) getActivity()).setBackArrowIcon();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dashboard_estatisticas, null);

        Button gerarTabela = (Button) view.findViewById(R.id.tabela_glicemias);
        gerarTabela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, new GeraTabelaFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
