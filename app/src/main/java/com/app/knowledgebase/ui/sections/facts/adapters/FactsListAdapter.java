package com.app.knowledgebase.ui.sections.facts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Fact;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class FactsListAdapter extends RealmBaseAdapter<Fact> implements ListAdapter {

    static class FactHolder {
        @Bind(R.id.text_fact_name) TextView textViewFact;

        public FactHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public FactsListAdapter(Context context, RealmResults<Fact> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FactHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_fact, parent, false);
            holder = new FactHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (FactHolder) convertView.getTag();
        }

        Fact currentFact = realmResults.get(position);
        holder.textViewFact.setText(currentFact.getDescription());

        int backgroundColor = (position % 2 == 0) ? android.R.color.white : R.color.colorPrimaryLight;
        convertView.setBackgroundColor(context.getResources().getColor(backgroundColor));

        return convertView;
    }
}
