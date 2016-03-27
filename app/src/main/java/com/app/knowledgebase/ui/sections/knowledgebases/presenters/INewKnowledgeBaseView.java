package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import java.util.List;

public interface INewKnowledgeBaseView {
    void fillFactsSpinner(List<String> factsTitles);
    void fillStrategiesSpinner(List<String> strategieTitles);
}
