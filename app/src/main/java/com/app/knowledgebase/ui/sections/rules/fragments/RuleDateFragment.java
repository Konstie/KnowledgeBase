//package com.app.knowledgebase.ui.sections.rules.fragments;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.app.knowledgebase.R;
//import com.app.knowledgebase.events.RuleDateSetEvent;
//import com.app.knowledgebase.events.SwipedRulePanelEvent;
//import com.app.knowledgebase.ui.sections.rules.SwipeDirection;
//import com.app.knowledgebase.ui.sections.rules.presenters.IRuleDateView;
//import com.app.knowledgebase.ui.sections.rules.presenters.RuleDatePresenter;
//import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.Calendar;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//public class RuleDateFragment extends Fragment implements IRuleDateView {
//    private static final String KEY_RULE = "currentRule";
//
//    private int currentRuleId;
//
//    public static RuleDateFragment newInstance(int currentRuleId) {
//        RuleDateFragment fragment = new RuleDateFragment();
//        Bundle args = new Bundle();
//        args.putInt(KEY_RULE, currentRuleId);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            currentRuleId = getArguments().getInt(KEY_RULE);
//            Log.w("RuleDateFragment", "RuleId: " + currentRuleId);
//        }
//
//        presenter = new RuleDatePresenter(getActivity(), this, currentRuleId);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.content_rule_date, container, false);
//        ButterKnife.bind(this, rootView);
//
//        presenter.setInitialDateForRule();
//        textDate.setText(presenter.getFormattedDate());
//
//        buttonSwipeLeft.setOnClickListener(v -> {
//            EventBus.getDefault().post(new SwipedRulePanelEvent(SwipeDirection.LEFT));
//        });
//
//        buttonEditDate.setOnClickListener(v -> {
//            presenter.onDateChangeClicked();
//        });
//
//        return rootView;
//    }
//
//
//    @Override
//    public void showDatePickerDialog() {
//        CalendarDatePickerDialogFragment.OnDateSetListener dateSetListener = (dialog, year, monthOfYear, dayOfMonth) -> {
//            Calendar date = Calendar.getInstance();
//            date.set(Calendar.YEAR, year);
//            date.set(Calendar.MONTH, monthOfYear);
//            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//            presenter.saveNewDateForRule(year, monthOfYear, dayOfMonth);
//            textDate.setText(presenter.getFormattedDate());
//            EventBus.getDefault().post(new RuleDateSetEvent(presenter.getRuleDate()));
//        };
//
//        CalendarDatePickerDialogFragment.OnDialogDismissListener dismissListener = dialoginterface -> {
//
//        };
//
//        CalendarDatePickerDialogFragment dialog = new CalendarDatePickerDialogFragment();
//        dialog.setOnDateSetListener(dateSetListener);
//        dialog.setOnDismissListener(dismissListener);
//        dialog.show(getChildFragmentManager(), "DATE_PICKER_TAG");
//    }
//}
