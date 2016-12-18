
# Load Data ---------------------------------------------------------------
rm(list=ls(all=TRUE))
setwd("~/git/mss/MSS/R")
library("ggplot2")
library("plyr")
library("RColorBrewer")
library("xtable")
source("defineBeamerPlot.R")


#Stupid Stupid
setwd("~/git/mss/MSS/D150Out/smallStupidStuipd/1481908670856/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
StupidStupid = aggregated
StupidStupid$Game="StupidStupid"

#Swing Stupid
setwd("~/git/mss/MSS/D150Out/smallSwingStupid/1481926279694/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
SwingStupid = aggregated
SwingStupid$Game="SwingStupid"

#Prom Stupid
setwd("~/git/mss/MSS/D150Out/smallPromStupid/1481955685931/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
PromStupid = aggregated
PromStupid$Game="PromStupid"

setwd("~/git/mss/MSS/R/tables/")
# Merge -------------------------------------------------------------------
Merged=rbind(StupidStupid, SwingStupid, PromStupid)




# Table -------------------------------------------------------------------
#Identifiers
tabelIdentA = 0.7*100000+0.46*1000+c(0.25,0.5,0.75)*10
tabelIdentB = 0.7*100000+0.48*1000+c(0.25,0.5,0.75)*10
tabelIdentC = 0.7*100000+0.5*1000+c(0.25,0.5,0.75)*10
allIdent = c(tabelIdentA, tabelIdentB, tabelIdentC)

TableData = subset(Merged, (Merged$combinationIdentifier%in%allIdent))
TabelRduced = TableData[,c()]

rawTable = ddply(TableData, ~combinationIdentifier, summarise, p0=mean(p), pm=mean(s))

TabelDataSort = TableData[(order(TableData$combinationIdentifier)),]

#Means
rawTable$MeanRandom=0
rawTable[(order(rawTable$combinationIdentifier)),]$MeanRandom = TabelDataSort[(TabelDataSort$Game=="StupidStupid"),]$pEnd

rawTable$MeanProtect=0
rawTable[(order(rawTable$combinationIdentifier)),]$MeanProtect = TabelDataSort[(TabelDataSort$Game=="SwingStupid"),]$pEnd


rawTable$MeanConvincible=0
rawTable[(order(rawTable$combinationIdentifier)),]$MeanConvincible = TabelDataSort[(TabelDataSort$Game=="PromStupid"),]$pEnd

#SD

rawTable$SDRandom=0
rawTable[(order(rawTable$combinationIdentifier)),]$SDRandom = TabelDataSort[(TabelDataSort$Game=="StupidStupid"),]$sdPEnd

rawTable$SDProtect=0
rawTable[(order(rawTable$combinationIdentifier)),]$SDProtect = TabelDataSort[(TabelDataSort$Game=="SwingStupid"),]$sdPEnd


rawTable$SDConvincible=0
rawTable[(order(rawTable$combinationIdentifier)),]$SDConvincible = TabelDataSort[(TabelDataSort$Game=="PromStupid"),]$sdPEnd






hlines=c(-1,0,3,6,9)
MeanTable = xtable(rawTable[,c(2:9)], caption="Mean result for proponents. Oponents play stupid. \\rot{10 runs per cell}", digits = 2)
align(MeanTable) <- "|l|l|l|ccc|ccc"
print.xtable(x=MeanTable, hline.after = hlines, include.rownames = FALSE, vlines.after=vlines, caption.placement = "top")#, file = "meanTable.txt")



# Winner Plot -------------------------------------------------------------
Winner = ddply(Merged, ~combinationIdentifier, summarize, winnerIs=Game[which.max(pWin)], p0=mean(p), pm=mean(s), MaxpWin = max(pWin), sd=sd(pWin), diff=max(pWin)-min(pWin))
Winner$winnerIs=ifelse(Winner$diff < 0.0001, "non", Winner$winnerIs)

ggplot(Winner, aes(x=p0, y=pm))+
  geom_tile(aes(fill=as.factor(winnerIs)))+
  #scale_fill_discrete(name=expression(p[final]))+
  scale_fill_manual(name="Most Victories:", drop=FALSE, values=c("SwingStupid"="red", "StupidStupid"="darkgreen", "PromStupid"="yellow", "non"="gray"),
                    labels=c("SwingStupid"="Protect", "StupidStupid"="Random", "PromStupid"="Convincible", "non"="Even"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))
ggsave(file="WinnerPlot.pdf", width=16, height=10, dpi=100)





# Chance To Win P0 vs S ---------------------------------------------------
#Order
Merged$Game=factor(Merged$Game,
       levels = c("StupidStupid", "PromStupid", "SwingStupid"),
       labels = c("Random", "Convincible", "Protect"))

ggplot(Merged, aes(x=p, y=s))+
  geom_tile(aes(fill=pWin))+
  scale_fill_gradientn(name="Share of \nRuns Won\nby Proponents\n" ,colours=brewer.pal(9,"RdYlGn"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))+
  theme(axis.text.x = element_text(angle = 90, hjust = 1))+
  #facet_grid(Game~.)+
  facet_grid(~Game)+
  theme(panel.spacing.x=unit(0.5, "lines"),panel.spacing.y=unit(1, "lines"))+
  theme(strip.text.x = element_text(size = 18))+
  theme(strip.text.y = element_text(size = 18))
ggsave(file="PWinPvsS.pdf", width=16, height=10, dpi=100)


