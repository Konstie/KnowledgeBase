package com.app.knowledgebase.ui.sections.facts;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.app.knowledgebase.ui.sections.facts.presenters.CreateFactPresenter;
import com.app.knowledgebase.ui.sections.facts.presenters.CreateFactPresenterImpl;

public class CreateFactActivity extends AppCompatActivity {
    private CreateFactPresenter presenter;

    private AppCompatEditText editFact;
    private FloatingActionButton buttonAdd;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        presenter = new CreateFactPresenterImpl(this);
        presenter.initializeDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddFactClicked(editFact.getText().toString());
            }
        });
    }
}
