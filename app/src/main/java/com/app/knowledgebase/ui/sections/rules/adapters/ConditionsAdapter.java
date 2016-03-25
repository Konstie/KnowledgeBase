package com.app.knowledgebase.ui.sections.rules.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Condition;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ConditionsAdapter extends RealmBaseAdapter<Condition> {
    public ConditionsAdapter(Context context, RealmResults<Condition> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    static class ConditionHolder {
        @Bind(R.id.text_fact_pos) TextView textFactPosition;
        @Bind(R.id.text_condition_operator) TextView textOperator;
        @Bind(R.id.text_condition_fact) TextView textFact;

        public ConditionHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public Condition getItem(int i) {
        return realmResults.get(i);
    }

    @Override
    public int getCount() {
        return realmResults.size();
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

        Condition currentCondition = getItem(position);
        holder.textFactPosition.setText(String.format(context.getResources().getString(R.string.rule_fact), position));

        holder.textOperator.setText(currentCondition.getConditionItem().getConditionOperator());
        if (position == 0) {
            holder.textOperator.setVisibility(View.GONE);
        }

        holder.textFact.setText(currentCondition.getConditionItem().getConditionFact());

        return convertView;
    }
}