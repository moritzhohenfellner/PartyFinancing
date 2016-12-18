rm(list=ls(all=TRUE))
library("ggplot2")

record <- read.csv(file="../output/1481580003092/mruns.csv",sep=",",head=TRUE)


ggplot(record)+
  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(theta)))



ggplot(record)+
  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(pMedia)))


ggplot(record[(record$pMedia==0.2),])+
  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(theta)))

ggplot(record[(record$ts>30),])+
  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(theta)))


ggplot(record[(record$theta==0.4 & record$pMedia>0.5),])+
  geom_line(aes(x=ts, y = posOpinionShare, group = runNumber, color=as.factor(pMedia)))+
  xlim(c(1000, 1010))


ggplot(record)+
  geom_smooth(aes(x=ts, y = posOpinionShare, color=as.factor(theta)))

max(record[(record$ts>30),]$posOpinionShare)
max(record[(record$posOpinionShare>0),]$ts)

hist(record[(record$ts==18),]$posOpinionShare)



