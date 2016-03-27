package com.app.knowledgebase.ui.sections.knowledgebases.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Strategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by konstie on 21.03.16.
 */
public class KnowledgeBaseListAdapter extends RealmBaseAdapter<KnowledgeBase> {
    public KnowledgeBaseListAdapter(Context context, RealmResults<KnowledgeBase> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KnowledgeBase knowledgeBase = getItem(position);
        KnowledgeBaseHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_knowledge_base, parent, false);
            holder = new KnowledgeBaseHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (KnowledgeBaseHolder) convertView.getTag();
        }
        holder.baseTitleTextView.setText(knowledgeBase.getTitle());
        holder.baseStrategyTextView.setText(getFormattedStrategiesText(knowledgeBase.getStrategies()));

        return convertView;
    }

    private String getFormattedStrategiesText(List<Strategy> strategies) {
        String strategiesText = "";
        for (int i = 0; i < strategies.size(); i++) {
            strategiesText += strategies.get(i).getName();
            if (i < strategies.size() - 1) {
                strategiesText += ", ";
            }
        }
        return strategiesText;
    }

    @Override
    public KnowledgeBase getItem(int i) {
        return realmResults.get(i);
    }

    static class KnowledgeBaseHolder {
        @Bind(R.id.base_title_text) TextView baseTitleTextView;
        @Bind(R.id.base_title_strategy) TextView baseStrategyTextView;

        public KnowledgeBaseHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }
    }
}
