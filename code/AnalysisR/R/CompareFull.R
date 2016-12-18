
# Load Data ---------------------------------------------------------------
rm(list=ls(all=TRUE))
setwd("~/git/mss/MSS/R")
library("ggplot2")
library("plyr")
library("RColorBrewer")
library("xtable")
source("defineBeamerPlot.R")


#Stupid Stupid
setwd("~/git/mss/MSS/Out/smallStupidStuipd/1481661508310/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
StupidStupid = aggregated

StupidStupid$StratPro="Random"
StupidStupid$StratOp="Random"

#Swing Stupid
setwd("~/git/mss/MSS/out/smallSwingStupid/1481668257821/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
SwingStupid = aggregated

SwingStupid$StratPro="Protect"
SwingStupid$StratOp="Random"

#Prom Stupid
setwd("~/git/mss/MSS/out/smallPromStupid/1481691355761/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
PromStupid = aggregated

PromStupid$StratPro="Convincible"
PromStupid$StratOp="Random"


Merged=rbind(StupidStupid, SwingStupid, PromStupid)

#Prom Prom
setwd("~/git/mss/MSS/out/smallPromProm/1481687090999/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
Symm = aggregated
Symm$StratPro="Convincible"
Symm$StratOp="Convincible"
Merged=rbind(Merged, Symm)



#Swing Swing
setwd("~/git/mss/MSS/out/smallSwingSwing/1481676966607/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
Symm = aggregated
Symm$StratPro="Protect"
Symm$StratOp="Protect"
Merged=rbind(Merged, Symm)


#Prom Prom
setwd("~/git/mss/MSS/out/smallSwingProm/1481698303634/")
source("~/git/mss/MSS/R/generateStandardPlots.R")
Symm = aggregated
Symm$StratPro="Protect"
Symm$StratOp="Convincible"
Merged=rbind(Merged, Symm)


setwd("~/git/mss/MSS/R/tablesFull/")
# Merge -------------------------------------------------------------------





# Chance To Win P0 vs S ---------------------------------------------------
#Order
# Merged$StratOp=factor(Merged$StratOp,
#        levels = c("Random", "Convincible", "Protect"),
#        labels = c("Opponent\nRandom", "Opponent\nConvincible", "Opponent\nProtect"))
# Merged$StratPro=factor(Merged$StratPro,
#                    levels = c("Random", "Convincible", "Protect"),
#                    labels = c("Proponent\nRandom", "Proponent\nConvincible", "Proponent\nProtect"))

ggplot(Merged, aes(x=p, y=s))+
  geom_tile(aes(fill=pWin))+
  scale_fill_gradientn(name="Share of \nRuns Won\nby Proponents\n" ,colours=brewer.pal(9,"RdYlGn"))+
  beamerPlot+
  xlab(expression(p[0]))+
  ylab(expression(p[M]))+
  theme(axis.text.x = element_text(angle = 90, hjust = 1))+
  #facet_grid(Game~.)+
  facet_grid(StratPro~StratOp)+
  theme(panel.spacing.x=unit(0.5, "lines"),panel.spacing.y=unit(1, "lines"))+
  theme(strip.text.x = element_text(size = 18))+
  theme(strip.text.y = element_text(size = 18))

ggsave(file="PWinPvsS.pdf", width=16, height=10, dpi=100)


