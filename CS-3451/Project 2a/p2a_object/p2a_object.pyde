'''
So my project uses keyboard controls to handle rotation instead of the time function.
This was to not have to wait forever to get a certain angle.

W - Counter-clockwise around the x-axis
S - Clockwise around the x-axis
A - Clockwise around the y-axis
D - Counter-clockwise around the y-axis
Q - Counter-clockwise around the z-axis
E - Clockwise around the z-axis

There is also a small animation regarding the state of the wings

1 - Flight Mode
2 - "Lock S-Foils in Attack Position"
'''

# time = 0   # use time to move objects from one frame to the next

def setup():
    size (800, 800, P3D)
    perspective (60 * PI / 180, 1, 0.1, 1000)  # 60 degree field of view
    
    global cameraRotXState
    cameraRotXState = "down"
    global cameraRotX
    cameraRotX = 0
    global cameraRotYState
    cameraRotYState = "right"
    global cameraRotY
    cameraRotY = 0
    global cameraRotZState
    cameraRotZState = "stop"
    global cameraRotZ
    cameraRotZ = 0
    
    global state
    state = "flight"
    
    global animAngle
    animAngle = 0
    
    global img
    img = loadImage("death.png")
    
def draw():
    # global time
    # time += 0.005

    camera (0, 0, 100, 0, 0, 0, 0,  1, 0)  # position the virtual camera

    background (0, 0, 0)  # clear screen and set background to white
    
    # create a directional light source
    ambientLight(50, 50, 50);
    lightSpecular(255, 255, 255)
    directionalLight (100, 100, 100, -0.3, 0.5, -1)
    
    noStroke()
    specular (180, 180, 180)
    shininess (15.0)
    
    pushMatrix()
    translate(0,0,-40)
    
    #Rotation Controls
    global cameraRotX
    if(cameraRotXState == "up"):
        cameraRotX += 0.005
    elif(cameraRotXState == "down"):
        cameraRotX -= 0.005
    else:
        cameraRotX += 0
    rotateX(cameraRotX)
    
    global cameraRotY
    if(cameraRotYState == "left"):
        cameraRotY -= 0.005
    elif(cameraRotYState == "right"):
        cameraRotY += 0.005
    else:
        cameraRotY += 0
    rotateY(cameraRotY)
    
    global cameraRotZ
    if(cameraRotZState == "clockwise"):
        cameraRotZ += 0.005
    elif(cameraRotZState == "counter"):
        cameraRotZ -= 0.005
    else:
        cameraRotZ += 0
    rotateZ(cameraRotZ)
    
    # Drawing the X-Wing
    xWing()
    
    # Drawing the Death Star
    translate(0, 0, 300)
    rotateY(radians(-90))
    scale(100, 100, 100)
    rotateX(radians(-90))
    customSphere()
    
    popMatrix()

def keyPressed():
    global state
    global cameraRotXState
    global cameraRotYState
    global cameraRotZState

    if (key == '1'):
        state = "flight"
    elif (key == '2'):
        state = "attack"
    elif (key == 'w'):
        if(cameraRotXState == "down"):
            cameraRotXState = "stop"
        elif(cameraRotXState == "stop"):
            cameraRotXState = "up"
    elif (key == 's'):
        if(cameraRotXState == "up"):
            cameraRotXState = "stop"
        elif(cameraRotXState == "stop"):
            cameraRotXState = "down"
    elif (key == 'a'):
        if(cameraRotYState == "right"):
            cameraRotYState = "stop"
        elif(cameraRotYState == "stop"):
            cameraRotYState = "left"
    elif (key == 'd'):
        if(cameraRotYState == "left"):
            cameraRotYState = "stop"
        elif(cameraRotYState == "stop"):
            cameraRotYState = "right"
    elif (key == 'q'):
        if(cameraRotZState == "clockwise"):
            cameraRotZState = "stop"
        elif(cameraRotZState == "stop"):
            cameraRotZState = "counter"
    elif (key == 'e'):
        if(cameraRotZState == "counter"):
            cameraRotZState = "stop"
        elif(cameraRotZState == "stop"):
            cameraRotZState = "clockwise"
    else:
        print 'key not recognized: ', key

# cylinder with radius = 1, z range in [-1,1]
def cylinder(sides = 64):
    # first endcap
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex ( x,  y, -1)
    endShape(CLOSE)
    # second endcap
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex ( x,  y, 1)
    endShape(CLOSE)
    # sides
    x1 = 1
    y1 = 0
    for i in range(sides):
        theta = (i + 1) * 2 * PI / sides
        x2 = cos(theta)
        y2 = sin(theta)
        beginShape()
        normal (x1, y1, 0)
        vertex (x1, y1, 1)
        vertex (x1, y1, -1)
        normal (x2, y2, 0)
        vertex (x2, y2, -1)
        vertex (x2, y2, 1)
        endShape(CLOSE)
        x1 = x2
        y1 = y2
        
def customSphere(lon = 64):
    #latitude can be anything, but half the longitude makes the most sense
    lat = lon/2

    # assume circle radius of .5
    # t and b are short for top and bottom (lesser or greater z, respectively)
    for j in range(lat):
        #z is in range of 0 to 1 (where 2r = 1)
        phit = float(j)/lat * PI
        phib = float(j+1)/lat * PI 
        #z = (r- cos(phi))/2
        zt = 0.5 * (1 - cos(phit))
        zb = 0.5 * (1 - cos(phib))
        # radius for each longitude circle is a function of the vertical angle, phi
        rt = 0.5 * sin(phit)
        rb = 0.5 * sin(phib)
        for i in range(lon):
            theta1 = (i) * 2 * PI / lon
            theta2 = (i + 1) * 2 * PI / lon
            #left side of quad
            x1t = rt*cos(theta1)
            y1t = rt*sin(theta1)
            x1b = rb*cos(theta1)
            y1b = rb*sin(theta1)
            
            #right side of quad
            x2t = rt*cos(theta2)
            y2t = rt*sin(theta2)
            x2b = rb*cos(theta2)
            y2b = rb*sin(theta2)
            
            # specifying quads makes the code more efficient
            beginShape(QUADS)
            texture(img)
            # to add texture, write the vertex call like this:
            # vertex(x, y, z, u, v)
            # where u and v are the x and y locations of a particular point in 
            # your texture image.
            #
            # u should be a function of the latitude, j, and v should
            # be a function of the longitude, i.
            #
            # specifically, 
            # u1 = (width_of_image/lon) * i
            # and 
            # vt = (height_of_image/lat) * j
            #
            # u2 and vb are the same, but with i+1 and j+1 substituted for i and j
            
            u1 = (640/lon) * i
            vt = (381/lat) * j
            u2 = (640/lon) * (i+1)
            vb = (381/lat) * (j+1)
            
            vertex (x1t, y1t, zt, u1, vt)
            vertex (x1b, y1b, zb, u1, vb)
            vertex (x2b, y2b, zb, u2, vb)
            vertex (x2t, y2t, zt, u2, vt)
            
            endShape(CLOSE)

def xWing():
    global animAngle
    if(state == "attack"):
        if(animAngle < 10):
            animAngle += 0.2
    else:
        if(animAngle > 0):
            animAngle -= 0.2
    
    #Main Body
    chassis()
    
    #Wings
    pushMatrix()
    translate(14, -8, -26)
    rotateZ(radians(-animAngle))
    wingTL_BR()
    popMatrix()
    
    pushMatrix()
    translate(-14, -8, -26)
    rotateZ(radians(animAngle))
    wingTR_BL()
    popMatrix()
    
    pushMatrix()
    translate(-14, 4, -26)
    rotateZ(radians(180))
    rotateZ(radians(-animAngle))
    wingTL_BR()
    popMatrix()
    
    pushMatrix()
    translate( 14, 4, -26)
    rotateZ(radians(180))
    rotateZ(radians(animAngle))
    wingTR_BL()
    popMatrix()
    
    #Cockpit
    stroke(255,255,255)
    cockpit()
    noStroke()

def cockpit():
    fill(150, 150, 150)
    #COCKPIT - FRONT WINDOW
    beginShape()
    vertex(  5,  -7, 3)
    vertex( -5,  -7, 3)
    vertex( -5, -11, -12)
    vertex(  5, -11, -12)
    endShape(CLOSE)
    #COCKPIT - BACK WINDOW
    beginShape()
    vertex( -5, -11, -12)
    vertex(  5, -11, -12)
    vertex(  8,  -8, -15)
    vertex( -8,  -8, -15)
    endShape(CLOSE)
    #COCKPIT - LEFT WINDOW
    beginShape()
    vertex(  5,  -7, 3)
    vertex(  5, -11, -12)
    vertex(  8,  -8, -15)
    vertex( 10,  -4, -12)
    vertex(  7,  -5, -1)
    endShape(CLOSE)
    #COCKPIT - RIGHT WINDOW
    beginShape()
    vertex( -5,  -7, 3)
    vertex( -5, -11, -12)
    vertex( -8,  -8, -15)
    vertex(-10,  -4, -12)
    vertex( -7,  -5, -1)
    endShape(CLOSE)

def chassis():
    fill(255, 255, 255)
    #MAIN BODY - FRONT TIP - TOP
    beginShape()
    vertex(  2,  -2, 50)
    vertex(  3,   0, 51)
    vertex( -3,   0, 51)
    vertex( -2,  -2, 50)
    endShape(CLOSE)
    #MAIN BODY - FRONT TIP - BOTTOM
    beginShape()
    vertex(  2,   2, 50)
    vertex(  3,   0, 51)
    vertex( -3,   0, 51)
    vertex( -2,   2, 50)
    endShape(CLOSE)
    
    #MAIN BODY - FRONT HULL PLATE - TOP
    beginShape()
    vertex(  2,  -2, 50)
    vertex(  5,  -7, 3)
    vertex( -5,  -7, 3)
    vertex( -2,  -2, 50)
    endShape(CLOSE)
    #MAIN BODY - FRONT HULL PLATE - BOTTOM
    beginShape()
    vertex( -2,   2, 50)
    vertex( -6, 3.5, -1)
    vertex(  6, 3.5, -1)
    vertex(  2,   2, 50)
    endShape(CLOSE)
    fill(255, 100, 100)
    #MAIN BODY - FRONT HULL PLATE - TOP LEFT
    beginShape()
    vertex(  2,  -2, 50)
    vertex(  5,  -7, 3)
    vertex(  7,  -5, -1)
    vertex(  8,   0, -1)
    vertex(  3,   0, 51)
    endShape(CLOSE)
    #MAIN BODY - FRONT HULL PLATE - TOP RIGHT
    beginShape()
    vertex( -2,  -2, 50)
    vertex( -5,  -7, 3)
    vertex( -7,  -5, -1)
    vertex( -8,   0, -1)
    vertex( -3,   0, 51)
    endShape(CLOSE)
    fill(255, 255, 255)
    #MAIN BODY - FRONT HULL PLATE - BOTTOM LEFT
    beginShape()
    vertex(  2,   2, 50)
    vertex(  3,   0, 51)
    vertex(  8,   0, -1)
    vertex(  6, 3.5, -1)
    endShape(CLOSE)
    #MAIN BODY - FRONT HULL PLATE - BOTTOM RIGHT
    beginShape()
    vertex( -2,   2, 50)
    vertex( -3,   0, 51)
    vertex( -8,   0, -1)
    vertex( -6, 3.5, -1)
    endShape(CLOSE)
    
    fill(255, 100, 100)
    #MAIN BODY - SIDE HULL PLATE - TOP LEFT
    beginShape()
    vertex(  8,   0, -1)
    vertex(  7,  -5, -1)
    vertex( 10,  -4, -12)
    vertex( 11,   0, -12)
    endShape(CLOSE)
    beginShape()
    vertex( 11,   0, -12)
    vertex( 10,  -4, -12)
    vertex(  8,  -8, -15)
    vertex( 11,   0, -15)
    endShape(CLOSE)
    #MAIN BODY - SIDE HULL PLATE - TOP RIGHT
    beginShape()
    vertex( -8,   0, -1)
    vertex( -7,  -5, -1)
    vertex(-10,  -4, -12)
    vertex(-11,   0, -12)
    endShape(CLOSE)
    beginShape()
    vertex(-11,   0, -12)
    vertex(-10,  -4, -12)
    vertex( -8,  -8, -15)
    vertex(-11,   0, -15)
    endShape(CLOSE)
    fill(255, 255, 255)
    #MAIN BODY - SIDE HULL PLATE - BOTTOM LEFT
    beginShape()
    vertex(  8,   0, -1)
    vertex( 11,   0, -12)
    vertex( 11,   0, -15)
    vertex(  9,   4, -15)
    vertex(  6, 3.5, -1)
    endShape(CLOSE)
    #MAIN BODY - SIDE HULL PLATE - BOTTOM RIGHT
    beginShape()
    vertex( -8,   0, -1)
    vertex(-11,   0, -12)
    vertex(-11,   0, -15)
    vertex( -9,   4, -15)
    vertex( -6, 3.5, -1)
    endShape(CLOSE)
    #MAIN BODY - SIDE HULL PLATE - BOTTOM
    beginShape()
    vertex( -9,   4, -15)
    vertex( -6, 3.5, -1)
    vertex(  6, 3.5, -1)
    vertex(  9,   4, -15)
    endShape(CLOSE)
    
    #MAIN BODY - BACK HULL PLATE - TOP
    beginShape()
    vertex(  8,  -8, -15)
    vertex( -8,  -8, -15)
    vertex( -8,  -8, -43)
    vertex(  8,  -8, -43)
    endShape(CLOSE)
    #MAIN BODY - BACK HULL PLATE - TOP LEFT
    beginShape()
    vertex(  8,  -8, -43)
    vertex(  8,  -8, -15)
    vertex( 11,   0, -15)
    vertex( 11,   0, -43)
    endShape(CLOSE)
    #MAIN BODY - BACK HULL PLATE - TOP RIGHT
    beginShape()
    vertex( -8,  -8, -43)
    vertex( -8,  -8, -15)
    vertex(-11,   0, -15)
    vertex(-11,   0, -43)
    endShape(CLOSE)
    #MAIN BODY - BACK HULL PLATE - BOTTOM
    beginShape()
    vertex( -9,   4, -15)
    vertex(  9,   4, -15)
    vertex(  8,   7, -23)
    vertex( -8,   7, -23)
    endShape(CLOSE)
    beginShape()
    vertex( -8,   7, -23)
    vertex(  8,   7, -23)
    vertex(  8,   7, -43)
    vertex( -8,   7, -43)
    endShape(CLOSE)
    #MAIN BODY - BACK HULL PLATE - BOTTOM LEFT
    beginShape()
    vertex( 11,   0, -15)
    vertex( 11,   0, -43)
    vertex(  8,   7, -43)
    vertex(  8,   7, -23)
    vertex(  9,   4, -15)
    endShape(CLOSE)
    #MAIN BODY - BACK HULL PLATE - BOTTOM RIGHT
    beginShape()
    vertex(-11,   0, -15)
    vertex(-11,   0, -43)
    vertex( -8,   7, -43)
    vertex( -8,   7, -23)
    vertex( -9,   4, -15)
    endShape(CLOSE)
    
    fill(50, 50, 50)
    #ENGINE - STERN FOUNDATION - BASE
    beginShape()
    vertex( -7,  -7, -42)
    vertex(  7,  -7, -42)
    vertex( 10,   0, -42)
    vertex(  7,   6, -42)
    vertex( -7,   6, -42)
    vertex(-10,   0, -42)
    endShape(CLOSE)
    fill(255, 60, 120)
    #ENGINE - STERN FOUNDATION - TOP
    beginShape()
    vertex( -7,  -7, -42)
    vertex(  7,  -7, -42)
    vertex(  8,  -8, -43)
    vertex( -8,  -8, -43)
    endShape(CLOSE)
    #ENGINE - STERN FOUNDATION - TOP LEFT
    beginShape()
    vertex(  8,  -8, -43)
    vertex( 11,   0, -43)
    vertex( 10,   0, -42)
    vertex(  7,  -7, -42)
    endShape(CLOSE)
    #ENGINE - STERN FOUNDATION - TOP RIGHT
    beginShape()
    vertex( -8,  -8, -43)
    vertex(-11,   0, -43)
    vertex(-10,   0, -42)
    vertex( -7,  -7, -42)
    endShape(CLOSE)
    #ENGINE - STERN FOUNDATION - BOTTOM
    beginShape()
    vertex( -7,   6, -42)
    vertex(  7,   6, -42)
    vertex(  8,   7, -43)
    vertex( -8,   7, -43)
    endShape(CLOSE)
    #ENGINE - STERN FOUNDATION - BOTTOM LEFT
    beginShape()
    vertex(  8,   7, -43)
    vertex( 11,   0, -43)
    vertex( 10,   0, -42)
    vertex(  7,   6, -42)
    endShape(CLOSE)
    #ENGINE - STERN FOUNDATION - BOTTOM RIGHT
    beginShape()
    vertex( -8,   7, -43)
    vertex(-11,   0, -43)
    vertex(-10,   0, -42)
    vertex( -7,   6, -42)
    endShape(CLOSE)
    fill(0, 0, 0)
    #ENGINE - STERN PLATE
    pushMatrix()
    translate (0, 0, -42)
    scale (10, 12, 1)
    box(1)
    popMatrix()
    fill(255, 60, 120)
    #ENGINE - TURBINE
    pushMatrix()
    translate (0, 0, -42)
    scale (4, 4, 2)
    cylinder()
    popMatrix()

def wingTL_BR():
    fill(255, 255, 255)
    #WING - ENGINE - PRIMARY
    pushMatrix()
    translate(0, 0, -2)
    scale(5, 5, 10)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    #WING - ENGINE - PRIMARY DETAIL
    pushMatrix()
    translate(0, 0, 7.5)
    scale(4.5, 4.5, 1)
    cylinder()
    popMatrix()
    #WING - ENGINE - SECONDARY
    pushMatrix()
    translate(0, 0, -15)
    scale(2.5, 2.5, 8)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(0, 0, -24)
    scale(3, 3, 3)
    cylinder()
    popMatrix()
    fill(255, 60, 120)
    #WING - ENGINE - SECONDARY DETAIL
    pushMatrix()
    translate(0, 0, -26.5)
    scale(2.5, 2.5, 1)
    cylinder()
    popMatrix()
    
    fill(255, 100, 100)
    #WING - ENGINE SUPPORT - FRONT
    beginShape()
    vertex( -3, 5.5, 6)
    vertex( 10, 5.5, 6)
    vertex( 10, 3.5, 6)
    vertex( -3,   -2, 6)
    endShape(CLOSE)
    #WING - ENGINE SUPPORT - BEHIND
    beginShape()
    vertex( -3, 5.5, -10)
    vertex( 10, 5.5, -10)
    vertex( 10, 3.5, -10)
    vertex( -3,   -2, -10)
    endShape(CLOSE)
    #WING - ENGINE SUPPORT - SIDES
    beginShape()
    vertex( 10, 5.5, 6)
    vertex( 10, 5.5, -10)
    vertex( 10, 3.5, -10)
    vertex( 10, 3.5, 6)
    endShape(CLOSE)
    beginShape()
    vertex( 10, 3.5, -10)
    vertex( 10, 3.5, 6)
    vertex( -3,  -2, 6)
    vertex( -3,  -2, -10)
    endShape(CLOSE)
    
    fill(255, 255, 255)
    #WING - MAIN PART - TOP
    beginShape()
    vertex( -5,   5, -14)
    vertex( 42,   5, -2)
    vertex( 42,   5, 6)
    vertex( -5,   5, 6)
    endShape(CLOSE)
    #WING - MAIN PART - BOTTOM
    beginShape()
    vertex( -5,   6, -14)
    vertex( 42,   6, -2)
    vertex( 42,   6, 6)
    vertex( -5,   6, 6)
    endShape(CLOSE)
    #WING - MAIN PART - SIDES
    beginShape()
    vertex( -5,   5, 6)
    vertex( -5,   6, 6)
    vertex( 42,   6, 6)
    vertex( 42,   5, 6)
    endShape(CLOSE)
    beginShape()
    vertex( 42,   5, 6)
    vertex( 42,   6, 6)
    vertex( 42,   6, -2)
    vertex( 42,   5, -2)
    endShape(CLOSE)
    beginShape()
    vertex( 42,   5, -2)
    vertex( 42,   6, -2)
    vertex( -5,   6, -14)
    vertex( -5,   5, -14)
    endShape(CLOSE)
    
    #WING - EDGE CYLINDER BLASTER
    pushMatrix()
    translate(41, 3.5, 2)
    scale(2, 2, 6)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 9)
    scale(1.4, 1.4, 1)
    cylinder()
    popMatrix()
    fill(255, 255, 255)
    pushMatrix()
    translate(41, 3.5, 11)
    scale(2, 2, 1)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 22)
    scale(0.8, 0.8, 10)
    cylinder()
    popMatrix()
    fill(150, 150, 150)
    pushMatrix()
    translate(41, 3.5, 42)
    scale(0.4, 0.4, 12)
    cylinder()
    popMatrix()
    fill(255, 255, 255)
    #WING - EDGE CYLINDER BLASTER - DETAIL
    pushMatrix()
    translate(41,2,55)
    rotateX(radians(-60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    pushMatrix()
    translate(41,3.5,54)
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    pushMatrix()
    translate(41,5,55)
    rotateX(radians(60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    
def wingTR_BL():
    fill(255, 255, 255)
    #WING - ENGINE - PRIMARY
    pushMatrix()
    translate(0, 0, -2)
    scale(5, 5, 10)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    #WING - ENGINE - PRIMARY DETAIL
    pushMatrix()
    translate(0, 0, 7.5)
    scale(4.5, 4.5, 1)
    cylinder()
    popMatrix()
    #WING - ENGINE - SECONDARY
    pushMatrix()
    translate(0, 0, -15)
    scale(2.5, 2.5, 8)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(0, 0, -24)
    scale(3, 3, 3)
    cylinder()
    popMatrix()
    fill(255, 60, 120)
    #WING - ENGINE - SECONDARY DETAIL
    pushMatrix()
    translate(0, 0, -26.5)
    scale(2.5, 2.5, 1)
    cylinder()
    popMatrix()
    
    fill(255, 100, 100)
    pushMatrix()
    translate(0, 0, -3.8)
    rotateY(radians(180))
    #WING - ENGINE SUPPORT - FRONT
    beginShape()
    vertex( -3, 5.5, 6)
    vertex( 10, 5.5, 6)
    vertex( 10, 3.5, 6)
    vertex( -3,   -2, 6)
    endShape(CLOSE)
    #WING - ENGINE SUPPORT - BEHIND
    beginShape()
    vertex( -3, 5.5, -10)
    vertex( 10, 5.5, -10)
    vertex( 10, 3.5, -10)
    vertex( -3,   -2, -10)
    endShape(CLOSE)
    #WING - ENGINE SUPPORT - SIDES
    beginShape()
    vertex( 10, 5.5, 6)
    vertex( 10, 5.5, -10)
    vertex( 10, 3.5, -10)
    vertex( 10, 3.5, 6)
    endShape(CLOSE)
    beginShape()
    vertex( 10, 3.5, -10)
    vertex( 10, 3.5, 6)
    vertex( -3,  -2, 6)
    vertex( -3,  -2, -10)
    endShape(CLOSE)
    popMatrix()
    
    fill(255, 255, 255)
    pushMatrix()
    translate(0, 11, 0)
    rotateZ(radians(180))
    #WING - MAIN PART - TOP
    beginShape()
    vertex( -5,   5, -14)
    vertex( 42,   5, -2)
    vertex( 42,   5, 6)
    vertex( -5,   5, 6)
    endShape(CLOSE)
    #WING - MAIN PART - BOTTOM
    beginShape()
    vertex( -5,   6, -14)
    vertex( 42,   6, -2)
    vertex( 42,   6, 6)
    vertex( -5,   6, 6)
    endShape(CLOSE)
    #WING - MAIN PART - SIDES
    beginShape()
    vertex( -5,   5, 6)
    vertex( -5,   6, 6)
    vertex( 42,   6, 6)
    vertex( 42,   5, 6)
    endShape(CLOSE)
    beginShape()
    vertex( 42,   5, 6)
    vertex( 42,   6, 6)
    vertex( 42,   6, -2)
    vertex( 42,   5, -2)
    endShape(CLOSE)
    beginShape()
    vertex( 42,   5, -2)
    vertex( 42,   6, -2)
    vertex( -5,   6, -14)
    vertex( -5,   5, -14)
    endShape(CLOSE)
    popMatrix()
    
    #WING - EDGE CYLINDER BLASTER
    pushMatrix()
    translate(0, 7, 0)
    rotateZ(radians(180))
    pushMatrix()
    translate(41, 3.5, 2)
    scale(2, 2, 6)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 9)
    scale(1.4, 1.4, 1)
    cylinder()
    popMatrix()
    fill(255, 255, 255)
    pushMatrix()
    translate(41, 3.5, 11)
    scale(2, 2, 1)
    cylinder()
    popMatrix()
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 22)
    scale(0.8, 0.8, 10)
    cylinder()
    popMatrix()
    fill(150, 150, 150)
    pushMatrix()
    translate(41, 3.5, 42)
    scale(0.4, 0.4, 12)
    cylinder()
    popMatrix()
    fill(255, 255, 255)
    #WING - EDGE CYLINDER BLASTER - DETAIL
    pushMatrix()
    translate(41,2,55)
    rotateX(radians(-60))
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    pushMatrix()
    translate(41,3.5,54)
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    pushMatrix()
    translate(41,5,55)
    rotateX(radians(60))
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    popMatrix()