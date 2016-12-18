
# Prepare Data ------------------------------------------------------------
#Load
record <- read.csv(file="mruns.csv",sep=",",head=TRUE)

#Look only at final TS
finalStep = subset(record, record$ts==max(record$ts))


#Aggregate Runs with similar Input
finalStep$combinationIdentifier = finalStep$theta*100000+finalStep$p*1000+finalStep$pMedia*10
finalStep$Win =  ifelse(finalStep$posOpinionShare>0.5,1,0)

aggregated = ddply(finalStep,~combinationIdentifier,summarise,pEnd=mean(posOpinionShare),sdPEnd=sd(posOpinionShare), p=mean(p), s=mean(pMedia), t=mean(theta), n=length(theta), pWin = mean(Win))
aggregated$Win = ifelse(aggregated$pEnd>0.5,1,0)




# Mean Outcome P0 vs T -----------------------------------------------------
#Means
ggplot(aggregated, aes(x=t, y=p))+
  geom_tile(aes(fill=pEnd))+
  #scale_fill_gradientn(name="Share of Proponents \nin Final Time Step\n ",colours=brewer.pal(9,"RdYlGn"))+
  scale_fill_gradientn(name=expression(p[final]) ,colours=brewer.pal(9,"RdYlGn"))+
  beamerPlot+
  xlab(expression(tau))+
  #ylab(paste(expression(p[0]), "Initial Share of Proponents"))
  ylab(expression(p[0]))
#  geom_abline(slope=1, intercept= 0)+
#  geom_abline(slope=-1, intercept= 1)+
#  geom_abline(intercept = 0.7, slope = 0)
ggsave(file="MeanOtucomePvsT.pdf", width=16, height=10, dpi=100)



# Mean Outcome P0 vs S -----------------------------------------------------
#Means
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=pEnd))+
  scale_fill_gradientn(name=expression(p[final]) ,colours=brewer.pal(9,"RdYlGn"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="MeanOtucomePvsS.pdf", width=16, height=10, dpi=100)



# Chance To Win P0 vs S ---------------------------------------------------
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=pWin))+
  scale_fill_gradientn(name="Share of \nRuns Won\nby Proponents\n" ,colours=brewer.pal(9,"RdYlGn"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="PWinPvsS.pdf", width=16, height=10, dpi=100)




# Win Loose P0 vs S -------------------------------------------------------
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=as.factor(Win)))+
  #scale_fill_discrete(name=expression(p[final]))+
  scale_fill_manual(name="Proponents:", values=c("0"="red", "1"="darkgreen"), labels=c("0"="Loose", "1"="Win"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="WinPvsS.pdf", width=16, height=10, dpi=100)


# n  p0 vs s ----------------------------------------------------
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=n))+
  scale_fill_continuous(name=expression(n))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="nPvsS.pdf", width=16, height=10, dpi=100)


# sd  p0 vs s ----------------------------------------------------
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=sdPEnd))+
  scale_fill_continuous(name=expression(sigma(p)["final"]))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="sdPvsS.pdf", width=16, height=10, dpi=100)


# Standard Dev p vs t ----------------------------------------------------
ggplot(aggregated, aes(x=t, y=p))+
  geom_tile(aes(fill=sdPEnd))+
  scale_fill_continuous(name=expression(sigma(p)["final"]))+
  beamerPlot+
  xlab(expression(tau))+
  ylab(expression(p[M]))
ggsave(file="sdpvst.pdf", width=16, height=10, dpi=100)








# p(s) over time ----------------------------------------------------------
#ggplot(record)+
#  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(pMedia)))





