'''
I wish I had more time for the assignment but I have 2 other projects, 2 other homeworks, and junior design paperwork from all my different classes. 

Plus, I didn't sleep for 2 days. Please enjoy.
'''

from basic_objects import *
from x_wing import xWing
from star_dest import starDestroyer
from tie_fighter import tieFighter

time = 0   # use time to move objects from one frame to the next
centerX = 0
posX = -400
posY = -100
posZ = 900
animAngle = 0
hyperspaceRotate = 0
hyperspaceScale = 1.6
sceneScale = 0.1
xWingRotationX = 0
xWingRotationY = 0
xWingRotationZ = 0

# Star Destroyer Initial Position
sDestMoveX = 140
sDestMoveY = -65
sDestMoveZ = 640

# Tie Fighter Squadron Initial Position
tieFighterSquadronX = 100
tieFighterSquadronY = -105
tieFighterSquadronZ = 700

# X-Wing Initial Position
xWingX = -1000
xWingY = -90
xWingZ = 1400
# X-Wing2 Initial Position
xWing2X = -900
xWing2Y = -130
xWing2Z = 1100
# X-Wing3 Initial Position
xWing3X = -710
xWing3Y = -110
xWing3Z = 1300

# X-Wing4 Initial Position
xWing4X = -403
xWing4Y = -94
xWing4Z = 876

def setup():
    size(800, 800, P3D)
    perspective(60 * PI / 180, 1, 0.1, 1000)  # 60 degree field of view
    
    # Death Star Texture
    global deathStar
    deathStar = loadImage("death.png")
    # Planet Texture
    global planet
    planet = loadImage("planet.png")
    # Star Destroyer Texture
    global hullTexture
    hullTexture = loadImage("sdest-hull.png")
    global hullSide
    hullSide = loadImage("sdest-hull-side.png")
    global engine
    engine = loadImage("sdest-engine.png")
    # Tie Fighter Texture
    global tieHull
    tieHull = loadImage("tie-hull.png")
    global tieWing
    tieWing = loadImage("tie-wing.png")
    # X-Wing Texture
    global xWingWingTop
    xWingWingTop = loadImage("xwing-wing-top.png")
    global xWingWingBottom
    xWingWingBottom = loadImage("xwing-wing-bottom.png")
    global xWingFrontTop
    xWingFrontTop = loadImage("xwing-front-top.png")
    global xWingSideTopSide
    xWingSideTopSide = loadImage("xwing-side-topside.png")
    global xWingSternTop
    xWingSternTop = loadImage("xwing-stern-top.png")
    global xWingSternBack
    xWingSternBack = loadImage("xwing-stern-back.png")
    # Hyperspace Texture
    global hyperspace
    hyperspace = loadImage("hyperspace.jpg")

def draw():
    # declaring time global variable
    global time
    time += 0.005
    
    # declaring some other globals
    global sDestMoveX
    global sDestMoveY
    global sDestMoveZ
    global tieFighterSquadronX
    global tieFighterSquadronY
    global tieFighterSquadronZ
    global xWingX
    global xWingY
    global xWingZ
    global xWing2X
    global xWing2Y
    global xWing2Z
    global xWing3X
    global xWing3Y
    global xWing3Z
    global xWing4X
    global xWing4Y
    global xWing4Z
    global animAngle
    global hyperspaceRotate
    global hyperspaceScale
    global sceneScale
    global xWingRotationX
    global xWingRotationY
    global xWingRotationZ
    
    # defining scene details
    background(0, 0, 0)
    noStroke()
    specular(180, 180, 180)
    shininess(15.0)
    
    # create light sources
    ambientLight(140, 140, 140)
    lightSpecular(255, 255, 255)
    directionalLight(10, 10, 10, 4, 1, -1)
    directionalLight(10, 10, 10, -0.3, 0.5, -1)
    # highlight (for the planet)
    spotLight(10, 10, 10, -2000, 1200, 1000, 1, 1, -1, radians(150), 1)
    
    # CAMERA MOVEMENT
    global centerX
    global posX
    global posY
    global posZ
    # SCENE 1
    if(time < 2.5):
        camera(0, -100, 600, 0, 0, 0, 0, 1, 0)
    elif(time < 3.5):
        camera(0, -100, 600, centerX, 0, 0, 0, 1, 0)
        centerX -= 1
    elif(time < 3.7):
        camera(0, -100, 600, centerX, 0, 0, 0, 1, 0)
    elif(time < 4.7):
        camera(-210, -80, -60, 0, 0, 1000, 0, 1, 0)
    elif(time < 5.4):
        camera(-400, -100, 900, 0, 0, -1000, 0, 1, 0)
    elif(time < 5.8):
        camera(posX, posY, posZ, 0, 0, -1000, 0, 1, 0)
        posY -= 1
        posZ -= 2
        centerX = 0
    elif(time >= 5.8):
        camera(posX, posY, posZ, centerX, 0, -1000, 0, 1, 0)
        posY += 0.1
        posZ -= 1
        centerX += 10
    print(time)
    
    # ==============================
    if(time < 3.7):
        # drawing the planet
        pushMatrix()
        translate(0, -300, -800)
        scale(1000, 1000, 1000)
        rotateY(radians(20))
        customSphere(planet, 2000, 1000)
        popMatrix()
        
        # drawing the Death Star
        pushMatrix()
        translate(-500, -100, 0)
        rotateY(radians(150))
        scale(100, 100, 100)
        rotateX(radians(-90))
        customSphere(deathStar, 640, 381)
        popMatrix()
    
    if(time < 4.7):
        # Star Destroyer
        pushMatrix()
        translate(sDestMoveX, sDestMoveY, sDestMoveZ)
        rotateY(radians(230))
        scale(4,4,4)
        starDestroyer(hullTexture, hullSide, engine)
        popMatrix()
        
        # Tie Fighter Squadron
        pushMatrix()
        translate(tieFighterSquadronX, tieFighterSquadronY, tieFighterSquadronZ)
        rotateY(radians(230))
        scale(0.5,0.5,0.5)
        
        pushMatrix()
        translate(-260, 0, -200)
        tieFighter(tieHull, tieWing)
        popMatrix()
        
        pushMatrix()
        translate(150, 40, -300)
        tieFighter(tieHull, tieWing)
        popMatrix()
        
        tieFighter(tieHull, tieWing)
        popMatrix()
        
        if(time > 0.4):
            if(time < 3.7):
                sDestMoveX -= 0.4
                sDestMoveY += 0.005
                sDestMoveZ -= 0.55
                
                if(time > 1.5 and time < 2.4):
                    tieFighterSquadronX -= 0.8
                    tieFighterSquadronY += 0.005
                    tieFighterSquadronZ -= 1.3
                elif(time >= 2.4):
                    tieFighterSquadronX -= 0.6
                    tieFighterSquadronY += 0.005
                    tieFighterSquadronZ -= 0.9
            elif(time < 4.7):
                sDestMoveX -= 0.1
                
                tieFighterSquadronX -= 0.06
        
        if(time > 4.5):
            pushMatrix()
            translate(xWingX, xWingY, xWingZ)
            rotateY(radians(160))
            scale(0.3,0.3,0.3)
            xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, animAngle)
            popMatrix()
            
            if(time < 4.53):
                xWingX += 100
                xWingZ -= 100
            elif(time < 4.7):
                xWingX += 0.1
                xWingZ -= 0.1
        
        if(time > 4.615):
            pushMatrix()
            translate(xWing2X, xWing2Y, xWing2Z)
            rotateY(radians(160))
            scale(0.3,0.3,0.3)
            xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, animAngle)
            popMatrix()
            
            if(time < 4.645):
                xWing2X += 100
                xWing2Z -= 100
            elif(time < 4.7):
                xWing2X += 0.1
                xWing2Z -= 0.1
        
        if(time > 4.61):
            pushMatrix()
            translate(xWing3X, xWing3Y, xWing3Z)
            rotateY(radians(160))
            scale(0.3,0.3,0.3)
            xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, animAngle)
            popMatrix()
            
            if(time < 4.64):
                xWing3X += 100
                xWing3Z -= 100
            elif(time < 4.7):
                xWing3X += 0.1
                xWing3Z -= 0.1
    else:
        if(time < 5.5):
            pushMatrix()
            translate(-350,-50,620)
            rotateZ(radians(hyperspaceRotate))
            rotateY(radians(-8))
            scale(hyperspaceScale,hyperspaceScale,1)
            if(time > 5.3 and hyperspaceScale > 0):
                hyperspaceScale -= 0.1
            beginShape()
            texture(hyperspace)
            vertex(-300,-200,0,0,0)
            vertex( 300,-200,0,303,0)
            vertex( 300, 200,0,303,160)
            vertex(-300, 200,0,0,160)
            endShape(CLOSE)
            popMatrix()
            
            hyperspaceRotate += 5
            
        if(time > 5.3):
            pushMatrix()
            scale(sceneScale,sceneScale,sceneScale)
            if(sceneScale < 1):
                sceneScale += 0.04
            
            # drawing the planet
            pushMatrix()
            translate(0, -300, -800)
            scale(1000, 1000, 1000)
            rotateY(radians(20))
            customSphere(planet, 2000, 1000)
            popMatrix()
            
            # drawing the Death Star
            pushMatrix()
            translate(-500, -100, 0)
            rotateY(radians(150))
            scale(100, 100, 100)
            rotateX(radians(-90))
            customSphere(deathStar, 640, 381)
            popMatrix()
            
            # Star Destroyer
            pushMatrix()
            translate(sDestMoveX, sDestMoveY, sDestMoveZ)
            rotateY(radians(230))
            scale(4,4,4)
            starDestroyer(hullTexture, hullSide, engine)
            popMatrix()
            
            # Tie Fighter Squadron
            pushMatrix()
            translate(tieFighterSquadronX, tieFighterSquadronY, tieFighterSquadronZ)
            rotateY(radians(230))
            scale(0.5,0.5,0.5)
            
            pushMatrix()
            translate(-260, 0, -200)
            tieFighter(tieHull, tieWing)
            popMatrix()
            
            pushMatrix()
            translate(150, 40, -300)
            tieFighter(tieHull, tieWing)
            popMatrix()
            
            tieFighter(tieHull, tieWing)
            popMatrix()
            
            # X-Wings
            if(time < 5.6):
                pushMatrix()
                translate(xWingX, xWingY, xWingZ)
                rotateY(radians(160))
                scale(0.3,0.3,0.3)
                xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, 10)
                popMatrix()
            
            pushMatrix()
            translate(xWing2X, xWing2Y, xWing2Z)
            rotateY(radians(160))
            scale(0.3,0.3,0.3)
            xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, 10)
            popMatrix()
            
            popMatrix()
        
        # Closest X-Wing
        pushMatrix()
        translate(xWing4X, xWing4Y, xWing4Z)
        rotateY(radians(xWingRotationY))
        rotateZ(radians(xWingRotationZ))
        rotateX(radians(xWingRotationX))
        if(time > 5.8):
            xWingRotationZ += 0.1
            xWingRotationY -= 0.15
            xWingRotationX += 0.05
            xWing4X += 0.2
            xWing4Y += 0.1
            xWing4Z -= 1
        elif(time > 5.4):
            xWingRotationX -= 0.05
            xWing4Y -= 1
            xWing4Z -= 2
        
        rotateY(radians(180))
        scale(0.3,0.3,0.3)
        xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, animAngle)
        popMatrix()
        
        if(time > 5.45):
            if(animAngle < 10):
                animAngle += 0.2
    
    if(time > 7):
        exit()