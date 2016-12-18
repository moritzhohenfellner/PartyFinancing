rm(list=ls(all=TRUE))
library("ggplot2")
library("plyr")
library(RColorBrewer)

#record <- read.csv(file="../output/peqs20/mruns.csv",sep=",",head=TRUE)
#record <- read.csv(file="../output/t70fix20/mruns.csv",sep=",",head=TRUE)
record <- read.csv(file="../output/1481299291929/mruns.csv",sep=",",head=TRUE)



finalStep = subset(record, record$ts==max(record$ts))
finalStep$combinationIdentifier = finalStep$theta*100000*finalStep$p*1000+finalStep$pMedia*10
aggregated = ddply(finalStep,~combinationIdentifier,summarise,pEnd=mean(posOpinionShare),sdPEnd=sd(posOpinionShare), p=mean(p), s=mean(pMedia), t=mean(theta))


#Means
ggplot(aggregated, aes(x=t, y=p))+
  geom_tile(aes(fill=pEnd))+
  scale_fill_gradientn(colours=brewer.pal(9,"RdYlGn"))+
  geom_abline(slope=1, intercept= 0)+
  geom_abline(slope=-1, intercept= 1)+
  geom_abline(intercept = 0.7, slope = 0)


#Standard Deviation 
ggplot(aggregated, aes(x=t, y=p))+
  geom_tile(aes(fill=sdPEnd))+
  scale_fill_gradientn(colours=brewer.pal(9,"YlOrRd"))
  #geom_abline(slope=1, intercept= 0)+
  #geom_abline(slope=-1, intercept= 1)




#p vs s 
ggplot(aggregated, aes(x=p, y=s))+
  geom_tile(aes(fill=pEnd))+
  scale_fill_gradientn(colours=brewer.pal(9,"RdYlGn"))





