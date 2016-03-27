package com.app.knowledgebase.ui.sections.rules.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Rule;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RulesListAdapter extends RealmBaseAdapter<Rule> {

    public RulesListAdapter(Context context, RealmResults<Rule> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rule currentRule = getItem(position);
        RuleHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_knowledge_base, parent, false);
            holder = new RuleHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RuleHolder) convertView.getTag();
        }
        holder.textRuleNumber.setText(String.valueOf(position));
        holder.textDescription.setText(currentRule.getDescription());

        return convertView;
    }

    @Override
    public Rule getItem(int i) {
        return realmResults.get(i);
    }

    @Override
    public int getCount() {
        return realmResults.size();
    }

    static class RuleHolder {
        public RuleHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
        @Bind(R.id.text_rule_number) TextView textRuleNumber;
        @Bind(R.id.text_rule_description) TextView textDescription;
    }
}
