#Assignment 2-ROCKET SIMULATION

# Name: Mia Valderrama-Lopez
# Student ID: 261239153

import math

#CONSTANTS:

DENSITY= 1.225
CUT_OFF_MASS_1 = 100000
CUT_OFF_MASS_2 = 400000
COST_METAL = 5 # $/m^2
FUEL_COST_UNIT = 6.1 # $/kg 
TAX = 0.15 #15%
MIN_WEIGHT = 20.0 #kg
MAX_WEIGHT = 500.0 #kg
MAX_VOLUME = 0.125 #m^3
WEIGHT_LIMIT = 0.05 # 5% of rocket initial weight
VOLUME_LIMIT = 0.4 # 40$ of storage space 
a = 9.81  #m/s^2

#  PART 1: ROCKET DESIGN 

# 1.Conversion from feet to meter

def feet_to_meter(lenght_in_feet):

    """
    Returns the lenght in meters as a positive float rounded up to 2 decimals

    Parameters:
        lenght_in_feet: a number (int or float)
        
    Returns: 
        lenght in meter rounded up to two decimal places (int or float)
    
    Examples:

    >>> rounded_lenght_in_meter(5.0)
    1.52

    >>> rounded_lenght_in_meter(345.78)
    105.42

    >>> rounded_lenght_in_meter(12.345)
    3.76

    """

    lenght_in_meter = float(lenght_in_feet * (1 / 3.28)) # 1ft= 1/3.28 m
    
    return round(lenght_in_meter, 2) 

# 2. Rocket Volume

def rocket_volume(radius, height_cone, height_cyl):

    """
    Returns the volume of a cone stacked on a cylinder

    Parameters: 
        radius= number (int or float)
        height_cone= number (int or float)
        height_cyl= number (int or float)
    
    Returns:
        total volume (number  rounded up to two decimal places (int or float)

    Examples:
    >>> total_volume(2.0,7.0,3.0)
    67.02

     >>> total_volume(40.6,10.0,100.56)
    538009.1

     >>> total_volume(40.6,10.0,100.5)
    537698.39

    """

    volume_cone = math.pi * (radius ** 2) * (height_cone / 3)
    volume_cylinder = math.pi * (radius ** 2) * height_cyl
    
    total_volume = volume_cone + volume_cylinder

    return round(total_volume, 2)

#3. Rocket Area

def rocket_area(radius, height_cone, height_cyl):
    """

    Returns the surface area of the rocket (surface area of cone+cylinder)

    Parameters: 
        radius= number (int or float)
        height_cone= number (int or float)
        height_cyl= number (int or float)    
    
    Returns: 
        rounded total area up to two decimal places (int or float) 


    <<< rocket_area(2.0,7.0,3.0)
    96.01

    <<< rocket_area(50.0,46.0,36.0)
    29835.89

    <<< rocket_area(15.0,10.0,25.0)
    3912.59

    """

    area_cone = math.pi * radius * (radius + math.sqrt((height_cone ** 2) +
                                                        radius ** 2))
    area_cyl = 2 * math.pi * radius * (height_cyl + radius)
    area_circle = math.pi * (radius ** 2) #shared circle are not counted

    total_area = area_cone + area_cyl - (2 * area_circle)

    return round(total_area, 2) 

#4. Rocket Mass

def rocket_mass(radius, height_cone, height_cyl):
    """
    Returns the mass of the rocket 

    Parameters: 
        radius= number (int or float)
        height_cone= number (int or float)
        height_cyl= number (int or float)  
    
    Returns: 
        rounded mass of the rocket (number) up to two decimals places (int or float)

    >>>rocket_mass(2.0,7.0,3.0)
    82.1

    >>>rocket_mass(50.0,46.0,36.0)
    493884.55

    >>>rocket_mass(15.0,10.0,25.0)
    24533.87

    """
    volume_rocket = rocket_volume(radius, height_cone, height_cyl)
    mass_rocket = volume_rocket * DENSITY

    return round(mass_rocket, 2)

#5. Rocket Fuel 

def rocket_fuel(radius, height_cone, height_cyl, exhaust_vel, init_vel, time):

    """
    Returns the fuel that a rocket needs 

    Parameters: 
        radius = number (int or float)
        height_cone = number (int or float)
        height_cyl = number (int or float)
        velocity_i = number (int or float)
        time = number (int or float)

    Returns: 
        total fuel needed ( number ) rounded  up to two decimals (int or float)

    <<<rocket_fuel(50.0, 100.0, 800.0, 700.0, 300.0, 120.0)
    4616444.53

    <<<rocket_fuel(25.0, 75.0, 10000.45, 600.0, 500.0, 150.0)
    31779925.79

    <<<rocket_fuel(40.0, 150.0, 500.0, 750.0, 250.0, 200.0)
    1883995.63

    """

    mass_rocket = rocket_mass(radius, height_cone, height_cyl)
    m_fuel_lauch= mass_rocket * ((math.e) ** (init_vel / exhaust_vel) - 1)

    if rocket_mass(radius, height_cone, height_cyl) < CUT_OFF_MASS_1:
        burn_rate = 1360 # kg/s

    elif rocket_mass(radius, height_cone, height_cyl) < CUT_OFF_MASS_2:
        burn_rate = 2000 # kg/s

    else: 
        burn_rate = 2721 #kg/s 

    #Calculate the mass of fuel needed for the rest of the trip
    m_fuel_trip = burn_rate * time

    #Calculate the total fuel needed
    total_fuel_needed = m_fuel_lauch + m_fuel_trip

    return round (total_fuel_needed, 2)

#6. Calculate Cost 

def calculate_cost(radius, height_cone, height_cyl, exhaust_velo,
                    init_velo, time, tax):

    """
    Returns the cost to fund the rocket

    Parameters: 
        radius = number (int or float)
        height_cone = number (int or float)
        height_cyl = number (int or float)
        velocity_i = number (int or float)
        time = number (int or float)
        tax = boolean (True or False)

    Returns: 

        either the cost with tax included or the total cost without tax rounded up to two decimals (int or float)


    <<<calculate_cost(11.2, 51.8, 105.7, 123.45, 99.65, 81.94, True)
    1354550.56
    
    <<<calculate_cost(11.2, 51.8, 105.7, 123.45, 99.65, 81.94, False)
    1177870.05

    <<<calculate_cost(150.0, 55.5, 100.5, 120.45, 150.85, 80.05, True)
    183527690.87

    """

    surface_rocket = rocket_area(radius, height_cone, height_cyl)
    total_fuel = rocket_fuel(radius, height_cone,height_cyl,exhaust_velo,init_velo,time)

    cost_material = surface_rocket * COST_METAL
    cost_fuel = total_fuel * FUEL_COST_UNIT

    total_cost =  cost_material + cost_fuel

    if tax :
        tax_included_cost = total_cost + (total_cost * TAX)
        return round(tax_included_cost, 2)
    
    else:
        return round(total_cost, 2)
    

# PART 2 : TEST ROCKET SIMULATION

#7. Compute Storage Space

def compute_storage_space(radius, height_cyl):
    """
    Returns the dimensions of the rectangular storage box

    Parameters:
        radius = number (int or float)
        height_cyl = number (int or float)
    
    Returns: 
        a tuple: the lenght, width and height of the rocket (numbers) rounded up to two decimals (int or float)

    <<<compute_storage_space(5.0, 10.0)
    (7.07, 7.07, 5.0)

    <<<compute_storage_space(50.5, 15.0)
    (71.42, 71.42, 7.5)

    <<<compute_storage_space(15.0, 10.0)
    (21.21, 21.21, 5.0)

    """

    l_rocket = w_rocket = round(math.sqrt(2) * radius, 2)
    h_rocket = round(height_cyl / 2, 2)
    
    return l_rocket, w_rocket, h_rocket



### comments that the teacher said about this code:
## in doc string when return rounded thing just write rounded thing up to two decimals not variable  = rjvnnv
## parameters are the arguments not the local variables

## not everything needs a space so loose the spaces something 

#8. Load Rocket

def load_rocket(initial_weight, radius,height_cyl):
    
    #Get the dimensions of the storage space
    lenght, width, height = compute_storage_space(radius, height_cyl)

    #Calculate the volume of the storage space
    storage_volume = lenght * width * height

    # Determine constraints Weight and volume limits
    max_item_weight = initial_weight * WEIGHT_LIMIT
    max_storage_volume = storage_volume * VOLUME_LIMIT

    #Initializing current weight and volume 
    current_weight = initial_weight
    total_item_weight = 0
    total_item_volume = 0

     # Check if the rocket can accept any items
    if max_item_weight < MIN_WEIGHT or max_storage_volume < MAX_VOLUME:  #this thing is to pass test number 2
        print("No more items can be added")
        return round(current_weight, 2)

    while (total_item_weight <= max_item_weight and
            total_item_volume <= max_storage_volume):
        # Ask the user for item details
        item_weight = input('Please enter the weight of the next item'
                             '(type "Done" when you are done'
                                'filling the rocket) : ')

        # Check if user wants to stop
        if item_weight == 'done':   #before there was item_weight.lower()
            #print("No more items can be added")
            return round(current_weight, 2)
        
        # Convert the item weight input to float
        item_weight = float(item_weight)

        # Ask for the item's dimensions
        item_width = float(input("Enter item width: "))
        item_length = float(input("Enter item length: "))
        item_height = float(input("Enter item height: "))

        # Compute the item's volume
        item_volume = item_length * item_width * item_height

         # Check weight and volume constraints
        if (item_weight < MIN_WEIGHT or item_weight > MAX_WEIGHT or
             item_weight + total_item_weight > max_item_weight):
            
            print("Item could not be added... please try again...")

        elif (item_volume < MAX_VOLUME or
               item_volume + total_item_volume > max_storage_volume):
            
            print("Item could not be added... please try again...")

        else:
            # Add the item weight/volume to the totals if constraints are met
            total_item_weight += item_weight
            total_item_volume += item_volume
            current_weight += item_weight

            # Check if the next item can be added after this one
            if (total_item_weight + MIN_WEIGHT > max_item_weight or
                 total_item_volume + MAX_VOLUME > max_storage_volume):
                
                print("No more items can be added")
                return round(current_weight, 2)

        # If the loop ends due to exceeding constraints
    print("No more items can be added")
    return round(current_weight, 2)

#9. Projectile Simulation
def projectile_sim(sim_time, interval, init_velo, angle_rad):

    """
    Returns None

    Parameters: 
        sim_time = number (int or float)
        interval = number (int or float)
        init_v = number (int or float)
        angle_rad = number (int or float)

    Returns: 
        None 
    
    
    """
    total_steps= int(sim_time / interval) + 1

    for step in range(total_steps):
        
        # Convert the index to the actual time in the simulation
        time = time * interval  
        height = (((-1 / 2) * a * (time ** 2)) +
                  (init_velo * math.sin(angle_rad) * time))

        # Only print positive heights (except at the start)
        if height > 0 or time == 0:
            print(round(height, 2))
        
        #Stop printing if the height becomes negative after rocket takeoff
        if height <= 0 and time > 0:
            return None

#10. Rocket Main

def rocket_main(): 
    print("Welcome to the Rocket Simulation !")

    radius_feet = float(input("Enter the rocket radius in feet:"))
    height_cone_feet = float(input("Enter the rocket cone height in feet: "))
    height_cyl_feet = float(input("Enter the rocket cylinder"
                                  "height in feet: "))

    # Convert dimensions to meters
    radius = feet_to_meter(radius_feet)
    height_cone = feet_to_meter(height_cone_feet)
    height_cyl = feet_to_meter(height_cyl_feet)

    #ask user for other stuff
    velocity_e = float(input("Enter the exhaust velocity"
                             "for the upcoming trip: "))
    velocity_i = float(input("Enter the initial velocity"
                             "for the upcoming trip: "))
    angle_rad = float(input("Enter the angle of launch"
                            "for the upcoming trip: "))
    trip_time = float(input("Enter the length of the upcoming trip: "))

    #ask user whether to factor tax in 
    tax_choice = int(input("Would you like to factor in tax?"
                           "1 for yes, 0 for no: "))
    tax = tax_choice == 1 #idk why this is there

    # Calculate the cost of the trip and display it
    cost = calculate_cost(radius, height_cone, height_cyl,velocity_e,
                           velocity_i, trip_time, tax)
    print("The trip will cost", cost)

    #Determine the initial weight of the rocket (excluding fuel)
    initial_weight = rocket_mass(radius, height_cone, height_cyl)

    #Load the rocket 
    print('Now loading the rocket:')
    final_weight = load_rocket(initial_weight, radius, height_cyl)
    print("The rocket and its equipment will weight", final_weight, "kg")

    # Ask user for sim time and sim interval for projectile simulation 
    sim_time = float(input("Enter the simulation total time: "))
    interval = float(input("Enter the simulation interval: "))

    #Launch the projectile simulation
    print("Now simulating the rocket trajectory:")
    projectile_sim(sim_time, interval, velocity_i, angle_rad)



print(projectile_sim(10, 2, 100.0, 0.79))





    

















        









    











    