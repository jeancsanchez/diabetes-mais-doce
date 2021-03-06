package br.com.caelum.diabetes.fragment.calculadora;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.diabetes.R;
import br.com.caelum.diabetes.model.AlimentoFisico;

/**
 * Created by Fê on 18/03/2015.
 */
public class BuscaAdapter extends BaseAdapter implements Filterable {

    private List<AlimentoFisico> alimentos;
    private Activity activity;
    private List<AlimentoFisico> alimentosTemporario;
    private View view;

    public BuscaAdapter(List<AlimentoFisico> alimentos, Activity activity) {
        this.alimentos = alimentos;
        this.activity = activity;
        this.alimentosTemporario = alimentos;
    }

    @Override
    public int getCount() {
        return alimentos.size();
    }

    @Override
    public Object getItem(int pos) {
        return alimentos.get(pos);
    }

    public void setItem (int pos, AlimentoFisico alimento) {
        alimentos.set(pos, alimento);
    }

    @Override
    public long getItemId(int pos) {
        return alimentos.get(pos).getId();
    }

    @Override
    public View getView(final int pos, View v, ViewGroup viewGroup) {
        view = v;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (v == null) {
            view = inflater.inflate(R.layout.item_busca_alimento, null);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view.findViewById(R.id.check_alimento);
                checkbox.setChecked(!checkbox.isChecked());
                onClickCheckbox(pos);
            }
        });

        AlimentoFisico alimento = alimentos.get(pos);

        TextView campoNome = (TextView) view.findViewById(R.id.alimento_nome);
        campoNome.setText(alimento.getNome());

        TextView campoCho = (TextView) view.findViewById(R.id.alimento_cho);
        campoCho.setText(alimento.getUnidadeDeMedida() + ": " + alimento.getCarboidrato() + "g");

        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.check_alimento);
        if (alimento.isCheck()) checkbox.setChecked(true);
        else checkbox.setChecked(false);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckbox(pos);
            }
        });

        return view;
    }

    public void onClickCheckbox(int position) {
        alimentos.get(position).setCheck(!alimentos.get(position).isCheck());
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                alimentos = (List<AlimentoFisico>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<AlimentoFisico> filteredArrayNames = new ArrayList<AlimentoFisico>();

                if (alimentosTemporario == null) {
                    alimentosTemporario = new ArrayList<AlimentoFisico>(alimentos);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = alimentosTemporario.size();
                    results.values = alimentosTemporario;
                } else {
                    constraint = removeDiacriticalMarks(constraint.toString().toLowerCase().trim());
                    for (int i = 0; i < alimentosTemporario.size(); i++) {
                        AlimentoFisico data = alimentosTemporario.get(i);
                        if (removeDiacriticalMarks(data.getNome().toLowerCase()).contains(constraint.toString())) {
                            filteredArrayNames.add(data);
                        }
                    }
                    results.count = filteredArrayNames.size();
                    results.values = filteredArrayNames;
                }
                return results;
            }
        };
        return filter;
    }

    private static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
