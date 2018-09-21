from scene import *
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
    elif key == '0':
        interpreter("i10.cli")

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
            # Usually, 0 <= Cd,Ca,Cs,Krefl <= 1
            # When Krefl is larger than one, this indicates that you will need to create reflection rays for this surface
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
            polygon = Polygon(surfaceMaterial)
        
        elif words[0] == 'vertex':
            # One vertex of a polygon. For this project, all of the provided polygons will be triangles
            # This means you can assume that there will be exactly three "vertex" commands between a "begin" and "end"
            x = float(words[1])
            y = float(words[2])
            z = float(words[3])
            polygon.addVertex(x,y,z)
        
        elif words[0] == 'end':
            # Ends the definition of a polygon
            polygon.calculateNormal()
            scene.addObject(polygon)
        
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
            
            # call ray tracing function with first eye ray
            pix_color = rayTrace(scene, eyeRay, 0)
            finalColor = color(pix_color[0], pix_color[1], pix_color[2])
            
            # fill the pixel with the calculated color
            set(i, j, finalColor)

# recursive method of doing ray tracing
def rayTrace(scene, incomingRay, depth):
    # calculate the closest intersection between the eye ray and all the scene's objects
    for object in scene.objectArray:
        incomingRay.findIntersection(object)
    
    # calculate the correct pixel color at closest intersection from all the scene's lights
    hitPoint = incomingRay.closestIntersection
    # if there is an intersection/hit
    if (hitPoint is not None):
        diffSumR = 0
        diffSumG = 0
        diffSumB = 0
        specSumR = 0
        specSumG = 0
        specSumB = 0
        
        # some offset constants to make sure rays (shadow, reflection) doesn't hit it's own object that it's coming from
        offsetX = hitPoint.surfaceNormal[0] * 0.001
        offsetY = hitPoint.surfaceNormal[1] * 0.001
        offsetZ = hitPoint.surfaceNormal[2] * 0.001
        
        for light in scene.lightArray:
            # find the vector from the intersection point to the light source
            hitToLightX = light.x - hitPoint.x
            hitToLightY = light.y - hitPoint.y
            hitToLightZ = light.z - hitPoint.z
            
            # cast a shadow ray from the intersection point to the light source
            shadowRay = Ray(hitPoint.x + offsetX, hitPoint.y + offsetY, hitPoint.z + offsetZ, hitToLightX, hitToLightY, hitToLightZ)
            
            # set the shadow ray's closest intersection at the light source
            shadowRay.closestIntersection = None
            
            # find if shadow ray intersects any object between the intersection point to the light source
            for object in scene.objectArray:
                shadowRay.findIntersection(object)
            
            # if the shadow ray doesn't hit an object or hits an objects beyond the light source
            if (shadowRay.closestIntersection is None or shadowRay.closestIntersection.t >= 1):
                # ===== FOR DIFFUSE COLOR CALCULATION =====
                # getting the normalized light vector
                lightNormal = findUnitVector(hitToLightX, hitToLightY, hitToLightZ)
                
                # find the dot product between the light vector and surface normal
                LdotN = dot_product(lightNormal, hitPoint.surfaceNormal)
                
                # multiply the dot product with the color of the associated light
                LdotN = max(0, LdotN)
                lightDiffR = LdotN * light.r
                lightDiffG = LdotN * light.g
                lightDiffB = LdotN * light.b
                
                # ===== FOR SPECULAR COLOR CALCULATION =====
                # find the vector from the intersection point to the incoming ray's point of origin
                hitToEyeX = incomingRay.x - hitPoint.x
                hitToEyeY = incomingRay.y - hitPoint.y
                hitToEyeZ = incomingRay.z - hitPoint.z
                hitToEye = findUnitVector(hitToEyeX, hitToEyeY, hitToEyeZ)
                
                # find the halfway vector between the hitToLight and hitToEye rays
                halfX = hitToEye[0] + lightNormal[0]
                halfY = hitToEye[1] + lightNormal[1]
                halfZ = hitToEye[2] + lightNormal[2]
                halfNormal = findUnitVector(halfX, halfY, halfZ)
                
                # find the dot product between the halfway vector and surface normal
                HdotN = dot_product(halfNormal, hitPoint.surfaceNormal)
                
                # raise the product to the specular power
                HdotN = max(0, HdotN)
                specularDot = HdotN**hitPoint.object.surfaceMaterial.P
                
                # multiply the dot product raised to the specular power with the color of the associated light
                specularR = specularDot * light.r
                specularG = specularDot * light.g
                specularB = specularDot * light.b
            
                # get the sum of the diff color results
                diffSumR += lightDiffR
                diffSumG += lightDiffG
                diffSumB += lightDiffB
                
                # get the sum of the spec color results
                specSumR += specularR
                specSumG += specularG
                specSumB += specularB
        
        # multiply the summation of the light colors with the surface color to get surface diffuse color
        diff_colorR = diffSumR * hitPoint.object.surfaceMaterial.Cdr
        diff_colorG = diffSumG * hitPoint.object.surfaceMaterial.Cdg
        diff_colorB = diffSumB * hitPoint.object.surfaceMaterial.Cdb
        
        # ambient color should be some constant from the surface material
        ambi_colorR = hitPoint.object.surfaceMaterial.Car
        ambi_colorG = hitPoint.object.surfaceMaterial.Cag
        ambi_colorB = hitPoint.object.surfaceMaterial.Cab
        
        # specular color = light color * highlight color * (halfway vector * normal) ^ specular power
        spec_colorR = specSumR * hitPoint.object.surfaceMaterial.Csr
        spec_colorG = specSumG * hitPoint.object.surfaceMaterial.Csg
        spec_colorB = specSumB * hitPoint.object.surfaceMaterial.Csb
        
        # reflection
        if (hitPoint.object.surfaceMaterial.Krefl > 0 and depth < 50):
            # find the reflection ray
            # reflection ray = 2 * dot product of incoming ray and normal vector * normal vector - incoming ray
            hitToEyeX = incomingRay.x - hitPoint.x
            hitToEyeY = incomingRay.y - hitPoint.y
            hitToEyeZ = incomingRay.z - hitPoint.z
            unitV = findUnitVector(hitToEyeX, hitToEyeY, hitToEyeZ)
            unitN = hitPoint.surfaceNormal
            vDotN = dot_product(unitV, unitN)
            
            vDotN = max(0, vDotN)
            reflX = (2 * vDotN * unitN[0]) - unitV[0]
            reflY = (2 * vDotN * unitN[1]) - unitV[1]
            reflZ = (2 * vDotN * unitN[2]) - unitV[2]
            
            reflectionRay = Ray(hitPoint.x + offsetX, hitPoint.y + offsetY, hitPoint.z + offsetZ, reflX, reflY, reflZ)
            
            # recursively call ray trace
            reflectionColor = rayTrace(scene, reflectionRay, depth + 1)
            
            # multiply the result of recursion with surface Krefl
            refl_colorR = reflectionColor[0] * hitPoint.object.surfaceMaterial.Krefl
            refl_colorG = reflectionColor[1] * hitPoint.object.surfaceMaterial.Krefl
            refl_colorB = reflectionColor[2] * hitPoint.object.surfaceMaterial.Krefl
        else:
            refl_colorR = 0
            refl_colorG = 0
            refl_colorB = 0
        
        # final color = diffuse + ambient + specular + reflection
        pix_colorR = diff_colorR + ambi_colorR + spec_colorR + refl_colorR
        pix_colorG = diff_colorG + ambi_colorG + spec_colorG + refl_colorG
        pix_colorB = diff_colorB + ambi_colorB + spec_colorB + refl_colorB
        
        return (pix_colorR, pix_colorG, pix_colorB)
    else:
        return (scene.backgroundR, scene.backgroundG, scene.backgroundB)

# should remain empty for this assignment
def draw():
    pass