# MATLAB Fall 2016 – Research Plan

> * Group Name: Better Rich Party
> * Group participants names: Ioannis Fousekis, Moritz Hohenfellner, Terry Louise Jones
> * Project Title: Party Financing: Opinion Influence Modeling

## General Introduction

Switzerland is a semi-direct democracy which means that in contrast to many other country people in Switzerland can vote on different issues rather than just electing representatives. Therefore there are political campaigns on regular basis which mainly financed by interest groups and parties. Since the financial situations of the involved interest groups often differ a lot, the impact of the campaign budget is a crucial factor on the probability of the outcomes. It should therefore be questioned whether this still fulfills democratic principles. Hence this project sets out to determine whether financing has an impact on the results and to what degree and whether it should be regulated. 

## The Model

The proposed model uses an agent based approach with an underlying simplified mobility model.
The model only looks at binary decisions, where the opinion on the topic can be described on a scale from 0 to 1. A value above .5 means for example that an agent would vote for a certain initiative.
Each agent will be randomly assigned with an initial opinion. The distribution can be part of the tested scenarios.
If two Agents meet at the same location they will discuss. There discussion will influence their opinions. The actual rules are still subject to some literature review which has to be done.
The interest groups place advertisements across the network which influence the agents when they walk by them. The amount and position of advertisements corresponds to the budget of the interest group.
Another kind of advertisement will be via broadcasting services which can reach every agent in the network with a given probability.
The network will be something like a simple grid or hub and spoke network. Origin and destination pairs will be assigned randomly to every agent.

## Fundamental Questions

The research question is divided into two parts:
Does the difference in budget influence the decision on political topics?
Should the financing of political campaigns be regulated?


## References 

Hegselmann, Rainer, and Ulrich Krause. "Opinion dynamics and bounded confidence models, analysis, and simulation." Journal of Artificial Societies and Social Simulation 5.3 (2002)

## Research Methods
Stochastic agent based model (CA for mobility and continuous model for opinions), Testing of different scenarios, statistical evaluation of results. 

