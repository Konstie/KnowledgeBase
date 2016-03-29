package com.app.knowledgebase.ui.sections.rules.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Rule;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class RulesListAdapter extends BaseAdapter {
    private Context context;
    private RealmList<Rule> rules;

    public RulesListAdapter(Context context, RealmList<Rule> realmResults) {
        this.context = context;
        this.rules = realmResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rule currentRule = getItem(position);
        RuleHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_rule, parent, false);
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
        return rules.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return rules.size();
    }

    static class RuleHolder {
        public RuleHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
        @Bind(R.id.text_rule_number) TextView textRuleNumber;
        @Bind(R.id.text_rule_description) TextView textDescription;
    }
}
