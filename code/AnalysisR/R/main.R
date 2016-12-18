rm(list=ls(all=TRUE))
setwd("~/git/mss/MSS/R")
library("ggplot2")
library("plyr")
library("RColorBrewer")
source("defineBeamerPlot.R")



runDir = "testRun";
runDir = "peqs20";
runDir = "t70fix20";
runDir = "t70fix20PromVsStupid";
runDir = "t790fix20SwingVsStupid";
runDir = "1481575424202";
runDir = "1481584054667";

setwd(paste("~/git/mss/MSS/output/", runDir, sep=""))

setwd("~/git/mss/MSS/output/1481660744993/")
setwd("~/git/mss/MSS/D150Out/smallSwingStupid/1481926279694/")

source("~/git/mss/MSS/R/generateStandardPlots.R")




# Check whether number of time steps was sufficient  -------------------------------
maxTs = max(record$ts)
plot(density(ddply(record, ~runNumber, summarize, diff12=posOpinionShare[1]-posOpinionShare[2])$diff12))
plot(density(ddply(record, ~runNumber, summarize, diff56=posOpinionShare[maxTs-1]-posOpinionShare[maxTs])$diff56))
