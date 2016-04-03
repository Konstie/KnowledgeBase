package com.app.knowledgebase.ui.sections.result.presenters;

import com.app.knowledgebase.models.ResolveIteration;

import java.util.List;

public interface IKnowledgeBaseResultView {
    void showResolveIterationsList(List<ResolveIteration> resolveIterations);
    void showNoRulesWarning();
}
