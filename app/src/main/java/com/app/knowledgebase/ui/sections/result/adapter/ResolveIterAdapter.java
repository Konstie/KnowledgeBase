package com.app.knowledgebase.ui.sections.result.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.ResolveIteration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by konstie on 03.04.16.
 */
public class ResolveIterAdapter extends ArrayAdapter<ResolveIteration> {
    private Context context;
    private List<ResolveIteration> resolveIterations;

    public ResolveIterAdapter(Context context, List<ResolveIteration> resolveIterations) {
        super(context, 0, resolveIterations);
        this.context = context;
        this.resolveIterations = resolveIterations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResolveIteration iteration = getItem(position);
        ResolveIterationHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_resolve_iteration, parent, false);
            holder = new ResolveIterationHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ResolveIterationHolder) convertView.getTag();
        }
        holder.iterNumTextView.setText(String.valueOf(iteration.getNumber()));
        holder.iterFactsTextView.setText(getFormattedFactsList(iteration.getFacts()));
        holder.iterRulesTextView.setText(getFormattedFactsList(iteration.getRulesUsed()));
        holder.iterResultTextView.setText(iteration.getConflict());

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public ResolveIteration getItem(int position) {
        return super.getItem(position);
    }

    static class ResolveIterationHolder {
        @Bind(R.id.text_iter_num) TextView iterNumTextView;
        @Bind(R.id.text_iter_facts) TextView iterFactsTextView;
        @Bind(R.id.text_iter_rules) TextView iterRulesTextView;
        @Bind(R.id.text_iter_result) TextView iterResultTextView;

        public ResolveIterationHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }
    }

    private String getFormattedFactsList(List<String> facts) {
        String resultStr = "[";
        for (int i = 0 ; i < facts.size(); i++) {
            if (i > 0) {
                resultStr += ", ";
            }
            resultStr += String.valueOf(facts.get(i));
        }
        resultStr += "]";
        return resultStr;
    }
}
