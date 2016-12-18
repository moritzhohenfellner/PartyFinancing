signalRecord <- read.csv(file="mruns40.csv",sep=",",head=TRUE) #40
#signalRecordB <- read.csv(file="mruns0.csv",sep=",",head=TRUE)

signalRecord$inputBinary = ifelse(signalRecord$inputSignal == 1,1,0)



mean(signalRecord$inputBinary)
table(signalRecord$inputSignal, signalRecord$previousOpinion)

prop.table(table(signalRecord$ts,signalRecord$inputBinary), margin = 1)
prop.table(table(signalRecord$ts,signalRecord$previousOpinion), margin = 1)

ggplot()+
  geom_density(aes(x=signalRecord$tauPos, group=signalRecord$ts, color=signalRecord$ts))


duplicated(signalRecordB$AgentID)
agent = unique(signalRecordB$AgentID[duplicated(signalRecordB$AgentID)])[5]
table(signalRecordB$inputSignal, signalRecordB$previousOpinion)
signalRecord[(signalRecord$AgentID==agent),]
signalRecordB[(signalRecordB$AgentID==agent),]
