package com.app.knowledgebase.ui.sections.result;

import android.content.Context;
import android.util.Log;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.dao.StrategiesTitles;
import com.app.knowledgebase.dao.comparators.RulesByIdComparator;
import com.app.knowledgebase.dao.comparators.RulesByMaxConditionsComparator;
import com.app.knowledgebase.dao.comparators.RulesByMaxDateComparator;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.ResolveIteration;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.models.Strategy;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;
import com.app.knowledgebase.ui.sections.rules.presenters.ConditionOperators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmResults;

public class ConflictResolverLogicPresenter extends BasePresenter {
    private int knowledgeBaseId;
    private boolean reachedGoal = false;
    private List<ResolveIteration> resolveIterations;
    private RealmList<Fact> usedFacts;
    private Set<String> usedFactsDescr;
    private RealmList<Rule> activeRules;
    private RealmList<Rule> expiredRules;
    private HashSet<Integer> expiredRulesIds;

    public ConflictResolverLogicPresenter(Context context, int knowledgeBaseId) {
        super(context);
        this.knowledgeBaseId = knowledgeBaseId;
        this.resolveIterations = new ArrayList<>();
        this.usedFacts = new RealmList<>();
        this.usedFactsDescr = new HashSet<>();
        this.activeRules = new RealmList<>();
        this.expiredRules = new RealmList<>();
        this.expiredRulesIds = new HashSet<>();
    }

    public void initializeKnowledgeBaseRules() {
        KnowledgeBase currentKnowledgeBase = KnowledgeBaseDao.get().findKnowledgeBaseById(getDatabase(), knowledgeBaseId);
        RealmList<Rule> rules = currentKnowledgeBase.getRules();
        RealmList<Fact> startFacts = currentKnowledgeBase.getStartFacts();
        RealmList<Strategy> strategies = currentKnowledgeBase.getStrategies();

        usedFacts.addAll(startFacts);
        for (Fact fact : usedFacts) {
            usedFactsDescr.add(fact.getDescription());
        }
        executeKnowledgeBase(rules, strategies);
    }

    private void executeKnowledgeBase(RealmList<Rule> rules, RealmList<Strategy> strategies) {
        int counter = 1;

        while (!reachedGoal) {
            checkRulesAndFacts(rules);

            ResolveIteration resolveIteration = new ResolveIteration();
            resolveIteration.setNumber(counter);
            resolveIteration.setFacts(new ArrayList<>(usedFactsDescr));

            // starts from given facts values, defines matching rules
            Rule solutionRule = getSolutionRuleForIteration(strategies);
            resolveIteration.setRulesUsed(new ArrayList<>(getUsedRulesTitles(activeRules)));
            resolveIteration.setConflict(solutionRule.getDescription());
            resolveIterations.add(resolveIteration);

            // checks matching rule as expired, will be filtered for next iterations
            expiredRules.add(solutionRule);
            expiredRulesIds.add(solutionRule.getId());

            int expiredRulePosition = getExpiredRulePosition(activeRules, solutionRule.getId());
            if (expiredRulePosition != -1) {
                activeRules.remove(expiredRulePosition);
            }

            // checks if reached goal
            Log.e("Solution rule", "Result fact: " + solutionRule.getResultFact().getDescription());
            if (usedFactsDescr.contains(Constants.GOAL)) {
                Log.w("Conflict", "Reached goal");
                reachedGoal = true;
                return;
            }
            counter++;
        }
    }

    private void checkRulesAndFacts(RealmList<Rule> rulesInBase) {
        for (Rule rule : rulesInBase) {
            if (isRulePasses(rule) && !usedFactsDescr.contains(rule.getResultFact().getDescription())) {
                usedFacts.add(rule.getResultFact());
                usedFactsDescr.add(rule.getResultFact().getDescription());
                if (!expiredRulesIds.contains(rule.getId())) {
                    activeRules.add(rule);
                }
            }
        }
        for (Fact fact : usedFacts) {
            Log.e("Conflict", "Used fact: " + fact.getDescription());
        }
    }

    private boolean isRulePasses(Rule currentRule) {
        RealmList<Condition> ruleConditions = new RealmList<>();
        RealmResults<Condition> conditionsForRuleResult = ConditionsDao.get().findConditionsByRuleId(getDatabase(), currentRule.getId());
        for (Condition condition : conditionsForRuleResult) {
            ruleConditions.add(condition);
        }

        Log.e("Conflict", "Rule conditions count: " + ruleConditions.size());

        boolean ruleResult = usedFactsDescr.contains(ruleConditions.get(0).getConditionItem().getConditionFact().getDescription());
        if (ruleConditions.size() > 1) {
            for (int i = 0; i < ruleConditions.size() - 1; i++) {
                ruleResult = isExpressionMatching(ruleResult, ruleConditions.get(i + 1));
            }
        }
        Log.w("Conflict", "Rule " + currentRule.getDescription() + " passes: {" + ruleResult + "}");
        return ruleResult;
    }

    private boolean isExpressionMatching(boolean formerResult, Condition followingCondition) {
        boolean matches = formerResult;
        String operator = followingCondition.getConditionItem().getConditionOperator();

        if (operator.equals(ConditionOperators.OR)) {
            matches = matches || usedFactsDescr.contains(followingCondition.getConditionItem().getConditionFact().getDescription());
        } else if (operator.equals(ConditionOperators.AND)) {
            matches = matches && usedFactsDescr.contains(followingCondition.getConditionItem().getConditionFact().getDescription());
        }

        return matches;
    }

    private Rule getSolutionRuleForIteration(RealmList<Strategy> appliedStrategies) {
        for (Rule rule : activeRules) {
            Log.e("Conflict", "Active rules: " + rule);
        }

        Rule resultRule;
        Set<String> strategiesNames = new HashSet<>();
        for (Strategy strategy : appliedStrategies) {
            strategiesNames.add(strategy.getName());
        }

        if (strategiesNames.contains(StrategiesTitles.COMPLEXITY)) {
            Collections.sort(activeRules, new RulesByMaxConditionsComparator());
        }
        if (strategiesNames.contains(StrategiesTitles.SIMPLICITY)) {
            Collections.sort(activeRules, new RulesByMaxConditionsComparator());
            Collections.sort(activeRules, Collections.reverseOrder(new RulesByMaxConditionsComparator()));
        }
        if (strategiesNames.contains(StrategiesTitles.FIRST_ACTIVATED)) {
            Collections.sort(activeRules, new RulesByIdComparator());
        }
        if (strategiesNames.contains(StrategiesTitles.LAST_ACTIVATED)) {
            Collections.sort(activeRules, new RulesByIdComparator());
            Collections.sort(activeRules, Collections.reverseOrder(new RulesByIdComparator()));
        }
        if (strategiesNames.contains(StrategiesTitles.LEX)) {
            Collections.sort(activeRules, new RulesByMaxDateComparator());
        }
        if (strategiesNames.contains(StrategiesTitles.MEA)) {
            Collections.sort(activeRules, new RulesByMaxDateComparator());
            Collections.sort(activeRules, Collections.reverseOrder(new RulesByMaxDateComparator()));
        }
        resultRule = activeRules.get(0);

        Log.e("Conflict", "Result rule: " + resultRule.getDescription());

        return resultRule;
    }

    private List<String> getUsedRulesTitles(RealmList<Rule> rules) {
        List<String> titles = new ArrayList<>();
        for (Rule rule : rules) {
            titles.add(rule.getDescription());
        }
        return titles;
    }

    private int getExpiredRulePosition(RealmList<Rule> rulesInBase, int expiredId) {
        for (int i = 0; i < rulesInBase.size(); i++) {
            if (rulesInBase.get(i).getId() == expiredId) {
                return i;
            }
        }
        return -1;
    }

    public List<ResolveIteration> getResolveIterations() {
        return resolveIterations;
    }
}
