
# Grid all paramters combinations once ------------------------------------
p = c(0.25,0.5,0.75)
t = c(1:5)*0.2
s = c(1:5)*0.2

grid = expand.grid(p,t,s)

names(grid)[1]="p"
names(grid)[2]="t"
names(grid)[3]="s"


write.csv(grid, "../input/grid.csv")



# fixed combination multiple times ----------------------------------------
numbers = c(1:150)

sets = data.frame(Number = numbers)
sets$p=0.25
sets$t=0.4
sets$s=0.6
sets$Number=NULL
write.csv(sets, "../input/grid.csv")



# p equals s --------------------------------------------------------------
p = c(1:20)*0.05
t = c(1:20)*0.05
s = c(1:20) # >> gives 20 points per combinations s will be set to p later

grid = expand.grid(p,t,s)

names(grid)[1]="p"
names(grid)[2]="t"
names(grid)[3]="s"

grid$s = grid$p
length(grid$p)

write.csv(grid, "../input/peqsgrid20.csv")



# p vs s; tau 0.7 ---------------------------------------------------------
p = c(1:10)*0.05
t = 0.7
s = c(1:20)*0.05 # >> gives 20 points per combinations s will be set to p later
n=c(1:10)

grid = expand.grid(p,t,s,n)

names(grid)[1]="p"
names(grid)[2]="t"
names(grid)[3]="s"

length(grid$p)

write.csv(grid, "../input/tfixed1.csv")



# Detailed Grid -----------------------------------------------------------



# p vs s; tau 0.7 ---------------------------------------------------------
p = c(20:25)*0.02
t = 0.7
s = c(1:20)*0.05 
n=c(1:100)

grid = expand.grid(p,t,s,n)

names(grid)[1]="p"
names(grid)[2]="t"
names(grid)[3]="s"

length(grid$p)

write.csv(grid, "../input/detail.csv")




