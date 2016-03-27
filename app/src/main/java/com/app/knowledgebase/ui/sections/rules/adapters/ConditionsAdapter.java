package com.app.knowledgebase.ui.sections.rules.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.ui.sections.rules.presenters.AddRulePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ConditionsAdapter extends RealmBaseAdapter<Condition> {
    private RealmResults<Condition> conditions;
    private AddRulePresenter presenter;

    public ConditionsAdapter(Context context, RealmResults<Condition> conditions, boolean autoUpdate) {
        super(context, conditions, autoUpdate);
        this.conditions = conditions;
        presenter = new AddRulePresenter(context, null);
    }

    static class ConditionHolder {
        @Bind(R.id.text_fact_pos) TextView textFactPosition;
        @Bind(R.id.text_condition_operator) TextView textOperator;
        @Bind(R.id.text_condition_fact) TextView textFact;
        @Bind(R.id.btn_remove) ImageButton buttonRemove;

        public ConditionHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public Condition getItem(int i) {
        return conditions.get(i);
    }

    @Override
    public int getCount() {
        return conditions.size();
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

        holder.textFact.setText(currentCondition.getConditionItem().getConditionFact().getDescription());
        holder.buttonRemove.setOnClickListener(v -> {
            presenter.onRemoveConditionClicked(currentCondition.getId());
            notifyDataSetChanged();
        });

        return convertView;
    }
}