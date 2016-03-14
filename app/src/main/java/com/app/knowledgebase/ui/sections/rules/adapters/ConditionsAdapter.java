package com.app.knowledgebase.ui.sections.rules.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.dao.FactsDao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ConditionsAdapter extends BaseAdapter {
    private static final int OPERATOR_POS = 0;
    private static final int FACT_POS = 1;

    private Context context;
    private HashMap<Integer, List<String>> conditionsMap;

    static class ConditionHolder {
        @Bind(R.id.text_fact_pos) TextView textFactPosition;
        @Bind(R.id.spinner_or_and) Spinner spinnerOperator;
        @Bind(R.id.edit_fact_pos) AutoCompleteTextView editFact;

        public ConditionHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ConditionsAdapter(Context context, HashMap<Integer, List<String>> conditionsMap) {
        this.context = context;
        this.conditionsMap = conditionsMap;
    }

    @Override
    public int getCount() {
        return conditionsMap.size();
    }

    @Override
    public Object getItem(int position) {
        return conditionsMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConditionHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_condition, parent, false);
            holder = new ConditionHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ConditionHolder) convertView.getTag();
        }

        List<String> currentCondition = conditionsMap.get(position);
        holder.textFactPosition.setText(String.format(context.getResources().getString(R.string.rule_fact), position));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.condition_operators, android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerOperator.setAdapter(adapter);

        if (position == 0) {
            holder.spinnerOperator.setVisibility(View.GONE);
        }

        holder.spinnerOperator.setSelection(getSelectedOperatorIndex(adapter, currentCondition.get(OPERATOR_POS)));

        List<String> allFacts = getAllFacts();
        ArrayAdapter<String> factsAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_dropdown_item_1line,
                allFacts
        );
        holder.editFact.setAdapter(factsAdapter);
        holder.editFact.setListSelection((allFacts.indexOf(currentCondition.get(FACT_POS)) != -1) ? allFacts.indexOf(currentCondition.get(FACT_POS)) : 0);
        holder.editFact.setOnItemClickListener((parent1, view, position1, id) -> {
            currentCondition.set(FACT_POS, factsAdapter.getItem(position1));
        });

        return convertView;
    }

    private int getSelectedOperatorIndex(ArrayAdapter adapter, String value) {
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            if (adapter.getItem(i).equals(value)) return i;
        }
        return -1;
    }

    private List<String> getAllFacts() {
        List<String> allFacts = FactsDao.get().getAllFacts(Realm.getInstance(context));
        Collections.sort(allFacts);
        return allFacts;
    }
}
