#ACTUAL MAIN
import datetime
import math
import matplotlib.pyplot as plt
#Constants
m_sun= 1.9885e30 #kg
m_earth= 5.9722e24 #kg
m_mars = 6.4169e23

d_earth_to_sun = 1.5108e11
d_mars_to_sun = 2.28e11

G=6.67430e-11 #m^3 kg^-1 s^-2

DT = 60   # Time step, make this 60 for final product, yes this will cause program to be slow, but it needs that precision so that
#thetas can be near each other
T_MAX = 60*60*24*1000 # Max time of the simulation
STEPS = int(T_MAX/DT) # Number of steps in the simulation
def force_gravity(m1,m2, rhat, r):
  return rhat*(G*m1*m2)/r**2

#initial conditions, based on December 8th, 2022, AKA the last time mars and earth passed by each other
t = [0]

#earth initial conditions
Ex_0 = d_earth_to_sun
Ey_0 =0
Evx_0 = 0
Evy_0 = 29800
Eax_0 = 0
Eay_0 = 0

ER = [Ex_0] #earth r vector to sun
#sun initial conditions
Sx_0 = 0
Sy_0 = 0
Svx_0 = 0
Svy_0 = 0
Sax_0 = 0
Say_0 = 0


#mars initial conditions
Mx_0 = d_mars_to_sun
My_0 = 0
Mvx_0 = 0
Mvy_0 = 24077
Max_0 = 0
May_0 = 0

MR = [Mx_0] #mars r vector to sun
#EMR = [Mx_0-Ex_0] #mars r vector to earth
EMR = [Ex_0-Mx_0]

#makin lists
#Earth lists
Ex = [Ex_0]
Ey = [Ey_0]
Evx = [Evx_0]
Evy = [Evy_0]
Eax = [Eax_0]
Eay = [Eay_0]


#Sun lists
Sx = [Sx_0]
Sy = [Sy_0]
Svx = [Svx_0]
Svy = [Svy_0]
Sax = [Sax_0]
Say = [Say_0]

#Mars lists
Mx = [Mx_0]
My = [My_0]
Mvx = [Mvx_0]
Mvy = [Mvy_0]
Max = [Max_0]
May = [May_0]

# #hat vectors
ESihat = [(Sx_0 - Ex_0)/math.sqrt((Sx_0 - Ex_0)**2 +(Sy_0 - Ey_0)**2)] #unit vectors, earth to sun
ESjhat = [(Sy_0 - Ey_0)/math.sqrt((Sx_0 - Ex_0)**2 +(Sy_0 - Ey_0)**2)]

SEihat = [(Ex_0 - Sx_0)/math.sqrt((Ex_0 - Sx_0)**2 +(Ey_0 - Sy_0)**2)] #unit vectors, sun to earth
SEjhat = [(Ey_0 - Sy_0)/math.sqrt((Ex_0 - Sx_0)**2 +(Ey_0 - Sy_0)**2)]

MSihat = [(Sx_0 - Mx_0)/math.sqrt((Sx_0 - Mx_0)**2 +(Sy_0 - My_0)**2)] #unit vectors, mars to sun
MSjhat = [(Sy_0 - My_0)/math.sqrt((Sx_0 - Mx_0)**2 +(Sy_0 - My_0)**2)]

SMihat = [(Mx_0 - Sx_0)/math.sqrt((Mx_0 - Sx_0)**2 +(My_0 - Sy_0)**2)] #unit vectors, sun to mars
SMjhat = [(My_0 - Sy_0)/math.sqrt((Mx_0 - Sx_0)**2 +(My_0 - Sy_0)**2)]

EMihat = [(Mx_0 - Ex_0)/math.sqrt((Mx_0 - Ex_0)**2 +(My_0 - Ey_0)**2)] #unit vectors, earth to mars
EMjhat = [(My_0 - Ey_0)/math.sqrt((Mx_0 - Ex_0)**2 +(My_0 - Ey_0)**2)]

MEihat = [(Ex_0 - Mx_0)/math.sqrt((Ex_0 - Mx_0)**2 +(Ey_0 - My_0)**2)] #unit vectors, mars to earth
MEjhat = [(Ey_0 - My_0)/math.sqrt((Ex_0 - Mx_0)**2 +(Ey_0 - My_0)**2)]

earth_theta = [math.atan(Ey_0/Ex_0)]
mars_theta = [math.atan(My_0/Mx_0)]

cross = []
cross_theta  = []
for i in range(STEPS):
  t.append(t[i]+DT)
#distances between bodies)
  ER.append(math.sqrt((Sx[i] - Ex[i])**2 +(Sy[i] - Ey[i])**2))
  MR.append(math.sqrt((Sx[i] - Mx[i])**2 +(Sy[i] - My[i])**2))
  EMR.append(math.sqrt((Ex[i] - Mx[i])**2 +(Ey[i] - My[i])**2))

#unit vectors appending
  ESihat.append((Sx[i] - Ex[i])/ER[i])
  ESjhat.append((Sy[i] - Ey[i])/ER[i])

  SEihat.append((Ex[i] - Sx[i])/ER[i])
  SEjhat.append((Ey[i] - Sy[i])/ER[i])

  MSihat.append((Sx[i] - Mx[i])/MR[i]) #unit vectors, mars to sun
  MSjhat.append((Sy[i] - My[i])/MR[i])

  SMihat.append((Mx[i] - Sx[i])/MR[i]) #unit vectors, sun to mars
  SMjhat.append((My[i] - Sy[i])/MR[i])

  EMihat.append((Mx[i] - Ex[i])/EMR[i]) #unit vectors, earth to mars
  EMjhat.append((My[i] - Ey[i])/EMR[i])

  MEihat.append((Ex[i] - Mx[i])/EMR[i]) #unit vectors, mars to earth
  MEjhat.append((Ey[i] - My[i])/EMR[i])

  # VERLET ALGORITHM EARTH
  Ex.append(Ex[i]+Evx[i]*DT+0.5*Eax[i]*DT**2)
  Ey.append(Ey[i]+Evy[i]*DT+0.5*Eay[i]*DT**2)
  Eax.append(force_gravity(m_sun, m_earth, ESihat[i], ER[i])/m_earth + force_gravity(m_mars, m_earth, EMihat[i], EMR[i])/m_earth)
  Eay.append(force_gravity(m_sun, m_earth, ESjhat[i], ER[i])/m_earth + force_gravity(m_mars, m_earth, EMjhat[i], EMR[i])/m_earth)
  Evx.append(Evx[i]+0.5*(Eax[i+1]+Eax[i])*DT)
  Evy.append(Evy[i]+0.5*(Eay[i+1]+Eay[i])*DT)

  # VERLET ALGORITHM SUN
  Sx.append(Sx[i]+Svx[i]*DT+0.5*Sax[i]*DT**2)
  Sy.append(Sy[i]+Svy[i]*DT+0.5*Say[i]*DT**2)
  Sax.append(force_gravity(m_sun, m_earth, SEihat[i], ER[i])/m_sun + force_gravity(m_sun, m_mars, SMihat[i], MR[i])/m_sun)
  Say.append(force_gravity(m_sun, m_earth, SEjhat[i], ER[i])/m_sun + force_gravity(m_sun, m_mars, SMjhat[i], MR[i])/m_sun)
  Svx.append(Svx[i]+0.5*(Sax[i+1]+Sax[i])*DT)
  Svy.append(Svy[i]+0.5*(Say[i+1]+Say[i])*DT)

  #VERLET ALGORITHM MARS
  Mx.append(Mx[i]+Mvx[i]*DT+0.5*Max[i]*DT**2)
  My.append(My[i]+Mvy[i]*DT+0.5*May[i]*DT**2)
  Max.append(force_gravity(m_sun, m_mars, MSihat[i], MR[i])/m_mars + force_gravity(m_mars, m_earth, MEihat[i], EMR[i])/m_mars)
  May.append(force_gravity(m_sun, m_mars, MSjhat[i], MR[i])/m_mars + force_gravity(m_mars, m_earth, MEjhat[i], EMR[i])/m_mars)
  Mvx.append(Mvx[i]+0.5*(Max[i+1]+Max[i])*DT)
  Mvy.append(Mvy[i]+0.5*(May[i+1]+May[i])*DT)

  earth_theta.append(math.atan(Ey[i]/Ex[i]))
  mars_theta.append(math.atan(My[i]/Mx[i]))

  if round(earth_theta[i], 5) == round(mars_theta[i], 5): #ACCURACY TO 5 DECIMAL PLACES)
    cross.append(t[i]/(60*60*24))
    cross_theta.append(earth_theta[i])
#cross contains days in which earth and mars are opposite to each other relative to the sun. This is accounted for by making the simulation
#1000 days long, only allowing for one pass by (martian year is 687 earth days long), and taking the last entry on the list, which will be when
#earth and mars cross each other in the same quadrant.
#https://phys.org/news/2017-04-mars-earth.html
#^contains info for both martian year length and actual dates for pass bys ("mars opposition")
start_date = "12/08/22" #last time earth/mars passed by
date_1 = datetime.datetime.strptime(start_date, "%m/%d/%y")

end_date = date_1 + datetime.timedelta(days=cross[-1])

#print(cross)
print("Earth and Mars will pass by each other next on around " + str(end_date) + " in year, month and day.")



plt.figure(figsize=(10,10))
plt.plot(Sx, Sy, linewidth=2.0, label="Sun", color="red")
plt.plot(Ex, Ey, linewidth=2.0, label="Earth", color="royalblue")
plt.plot(Mx, My, linewidth=2.0, label="Mars", color="darkorange")
plt.xlabel(r"$x(m)$")
plt.ylabel(r"$y(m)$")
plt.title("Figure 1. Numerical Simulation of Newton's Law of Universal Gravitation")
plt.legend()
plt.grid(True)
plt.show()