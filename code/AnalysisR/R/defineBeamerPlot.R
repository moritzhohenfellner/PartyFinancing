library(ggplot2)
library("grid", lib.loc="/Library/Frameworks/R.framework/Versions/3.2/Resources/library")
beamerPlot =   
  theme_bw()+
  #theme_light()+
  #theme_minimal()+
  theme(axis.text = element_text(size = rel(3)))+
  theme(axis.title = element_text(size = rel(3)))+
  theme(legend.text=element_text(size=25), legend.title=element_text(size=25))+
  theme(legend.key.size = unit(2, 'lines'))+
  theme(axis.title.y=element_text(vjust=2))#+
  #theme(axis.title.x=element_text(vjust=-1))
  #theme(axis.title.y=element_text(margin=margin(0,20,0,0)))

beamerWidth=1



# Multiplot ---------------------------------------------------------------
multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
  library(grid)
  
  # Make a list from the ... arguments and plotlist
  plots <- c(list(...), plotlist)
  
  numPlots = length(plots)
  
  # If layout is NULL, then use 'cols' to determine layout
  if (is.null(layout)) {
    # Make the panel
    # ncol: Number of columns of plots
    # nrow: Number of rows needed, calculated from # of cols
    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
                     ncol = cols, nrow = ceiling(numPlots/cols))
  }
  
  if (numPlots==1) {
    print(plots[[1]])
    
  } else {
    # Set up the page
    grid.newpage()
    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
    
    # Make each plot, in the correct location
    for (i in 1:numPlots) {
      # Get the i,j matrix positions of the regions that contain this subplot
      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
      
      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
                                      layout.pos.col = matchidx$col))
    }
  }
}

#   -----------------------------------------------------------------------

