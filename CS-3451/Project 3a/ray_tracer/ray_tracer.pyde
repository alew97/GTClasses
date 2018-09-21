from scene import *
from create_sphere import *
from polygon import *
from surface import *
from light import *
from ray import *

# This is the starter code for the CS 3451 Ray Tracing project.
#
# The most important part of this code is the interpreter, which will
# help you parse the scene description (.cli) files.

def setup():
    size(500, 500) 
    noStroke()
    colorMode(RGB, 1.0)  # Processing color values will be in [0, 1]  (not 255)
    background(0, 0, 0)

# read and interpret the appropriate scene description .cli file based on key press
def keyPressed():
    if key == '1':
        interpreter("i1.cli")
    elif key == '2':
        interpreter("i2.cli")
    elif key == '3':
        interpreter("i3.cli")
    elif key == '4':
        interpreter("i4.cli")
    elif key == '5':
        interpreter("i5.cli")
    elif key == '6':
        interpreter("i6.cli")
    elif key == '7':
        interpreter("i7.cli")
    elif key == '8':
        interpreter("i8.cli")
    elif key == '9':
        interpreter("i9.cli")

def interpreter(fname):
    global scene
    scene = Scene()
    
    global surfaceMaterial
    surfaceMaterial = None
    
    fname = "data/" + fname
    # read in the lines of a file
    with open(fname) as f:
        lines = f.readlines()
    
    # parse each line in the file in turn
    for line in lines:
        words = line.split()  # split the line into individual tokens
        
        if len(words) == 0:   # skip empty lines
            continue
        
        if words[0] == 'sphere':
            radius = float(words[1])
            x = float(words[2])
            y = float(words[3])
            z = float(words[4])
            # call your sphere creation routine here
            if (surfaceMaterial is None):
                raise ValueError("Can't create an object without a surface material")
            scene.addObject(Sphere(x,y,z,radius,surfaceMaterial))
            surfaceMaterial = None
        
        elif words[0] == 'fov':
            # Specifies the field of view (in degrees) for a perspective projection
            # The viewer's eye position is assumed to be at the origin and to be looking down the negative z-axis
            # (giving us a right-handed coordinate system)
            # The y-axis points up
            scene.fov = float(words[1])
        
        elif words[0] == 'background':
            # Background color. If a ray misses all the objects in the scene, the pixel should be given this color
            r = float(words[1])
            g = float(words[2])
            b = float(words[3])
            scene.setBackgroundColor(r,g,b)
        
        elif words[0] == 'light':
            # Point light source at position (x, y, z) and its color (r, g, b)
            # Your code should allow up to 10 light sources
            # For the second part of this assignment, you will cause these lights to cast shadows
            x = float(words[1])
            y = float(words[2])
            z = float(words[3])
            r = float(words[4])
            g = float(words[5])
            b = float(words[6])
            scene.addLight(LightSource(x,y,z,r,g,b))
        
        elif words[0] == 'surface':
            # This command describes the reflectance properties of a surface
            # This reflectance should be given to the objects that follow the command in the scene description, such as spheres and triangles
            # For this first part of the project, you only need to use the first three coefficients (diffuse color)
            # The first three values are the diffuse coefficients (red, green, blue), followed by ambient and specular coefficients
            # Next comes the specular power P (the Phong exponent), which says how shiny the highlight of the surface should be
            # The final value is the reflection coefficient (0 = no reflection, 1 = perfect mirror)
            # You do not need to implement ambient and specular shading and reflections until the second part of this assignment (P3B)
            # Usually, 0 <= Cd,Ca,Cs,Krefl <= 1.
            Cdr = float(words[1])
            Cdg = float(words[2])
            Cdb = float(words[3])
            
            Car = float(words[4])
            Cag = float(words[5])
            Cab = float(words[6])
            
            Csr = float(words[7])
            Csg = float(words[8])
            Csb = float(words[9])
            
            P = float(words[10])
            Krefl = float(words[11])
            
            surfaceMaterial = Surface(Cdr,Cdg,Cdb,Car,Cag,Cab,Csr,Csg,Csb,P,Krefl)
            
        elif words[0] == 'begin':
            # Begins the definition of a polygon
            # Should be followed by "vertex" commands, and the polygon definition is terminated by an "end"
            pass
        
        elif words[0] == 'vertex':
            # One vertex of a polygon. For this project, all of the provided polygons will be triangles
            # This means you can assume that there will be exactly three "vertex" commands between a "begin" and "end"
            # You do not need to implement polygons until the second part of this assignment (P3B)
            pass
        
        elif words[0] == 'end':
            # Ends the definition of a polygon
            pass
        
        elif words[0] == 'write':
            render_scene(scene)  # render the scene
            save(words[1])  # write the image to a file

# render the ray tracing scene
def render_scene(scene):
    for j in range(height):
        for i in range(width):
            # converting pixel (i,j) to 3D space coordinates
            xPrime = i
            yPrime = (height - j)
            k = tan(radians(scene.fov)/2)
            
            xPixelToMap = (xPrime - (width/2)) * (2 * k / width)
            yPixelToMap = (yPrime - (height/2)) * (2 * k / height)
            zPixelToMap = -1
            
            # create a ray from the eye to the mapped coordinate
            eyeXPos = 0
            eyeYPos = 0
            eyeZPos = 0
            
            eyeRay = Ray(eyeXPos, eyeYPos, eyeZPos, xPixelToMap - eyeXPos, yPixelToMap - eyeYPos, zPixelToMap - eyeZPos)
            
            # calculate the closest intersection between the eye ray and all the scene's objects
            for object in scene.objectArray:
                # calculate the intersection between the eye ray and sphere object
                if (isinstance(object, Sphere)):
                    # ray's origin
                    x0 = eyeRay.x
                    y0 = eyeRay.y
                    z0 = eyeRay.z
                    # ray's direction
                    dx = eyeRay.dx
                    dy = eyeRay.dy
                    dz = eyeRay.dz
                    # sphere's center position
                    cx = object.x
                    cy = object.y
                    cz = object.z
                    # sphere's radius
                    r = object.radius
                    
                    a = (dx**2) + (dy**2) + (dz**2)
                    b = 2 * (((x0 * dx) - (cx * dx)) + ((y0 * dy) - (cy * dy)) + ((z0 * dz) - (cz * dz)))
                    c = (x0 - cx)**2 + (y0 - cy)**2 + (z0 - cz)**2 - r**2
                    
                    discriminant = b**2 - (4 * a * c)
                    
                    if (discriminant > 0):
                        #two intersections
                        t = (-b + sqrt(discriminant)) / (2 * a)
                        eyeRay.getClosestIntersection(t, object)
                        
                        t = (-b - sqrt(discriminant)) / (2 * a)
                        eyeRay.getClosestIntersection(t, object)
                    elif (discriminant == 0):
                        #one intersection
                        t = -b / (2 * a)
                        eyeRay.getClosestIntersection(t, object)
                    else:
                        #no intersection
                        pass
                
                # calculate the intersection between the eye ray and polygon object
                if (isinstance(object, Polygon)):
                    pass
            
            # calculate the correct pixel color at closest intersection from all the scene's lights
            hitPoint = eyeRay.closestIntersection
            if (hitPoint is not None):
                lightSumR = 0
                lightSumG = 0
                lightSumB = 0
                
                for light in scene.lightArray:
                    # find the vector from the light source to the intersection point, then normalize it
                    normX = light.x - hitPoint.x
                    normY = light.y - hitPoint.y
                    normZ = light.z - hitPoint.z
                    magnitude = sqrt((normX**2) + (normY**2) + (normZ**2))
                    lightNormal = (normX/magnitude, normY/magnitude, normZ/magnitude)
                    
                    # find the dot product between the previous vector and surface normal
                    dotProduct = (lightNormal[0] * hitPoint.surfaceNormal[0]) + (lightNormal[1] * hitPoint.surfaceNormal[1]) + (lightNormal[2] * hitPoint.surfaceNormal[2])
                    
                    # multiply all the dot products with the color of the associated light
                    dotProduct = max(0, dotProduct)
                    lightR = dotProduct * light.r
                    lightG = dotProduct * light.g
                    lightB = dotProduct * light.b
                    
                    # add all the products together
                    lightSumR += lightR
                    lightSumG += lightG
                    lightSumB += lightB
                
                # multiply the summation of the light colors with the surface color
                pix_colorR = lightSumR * hitPoint.object.surfaceMaterial.Cdr
                pix_colorG = lightSumG * hitPoint.object.surfaceMaterial.Cdg
                pix_colorB = lightSumB * hitPoint.object.surfaceMaterial.Cdb
                
                pix_color = color(pix_colorR, pix_colorG, pix_colorB)
                
            else:
                pix_color = color(scene.backgroundR, scene.backgroundG, scene.backgroundB)
            
            # fill the pixel with the calculated color
            set(i, j, pix_color)

# should remain empty for this assignment
def draw():
    pass