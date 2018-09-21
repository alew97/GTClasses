from basic_objects import *

def starDestroyer(hullTexture, hullSide, engine):
    # Main Body
    chassis(hullTexture, hullSide, engine)
    
    # Bridge Foundation
    bridgeBase(hullTexture, hullSide)
    
    # Bridge Foundation - Small Up Front
    pushMatrix()
    translate(0, -0.5, 3)
    scale(0.7, 0.7, 0.7)
    bridgeBase(hullTexture, hullSide)
    popMatrix()
    
    # Bridge Foundation - On Top of Original
    pushMatrix()
    translate(0, -3.8, -7)
    scale(0.7, 0.7, 0.7)
    bridgeBase(hullTexture, hullSide)
    popMatrix()
    
    bridgeConnector(hullTexture, hullSide)
    bridge()

def chassis(hullTexture, hullSide, engine):
    fill(255, 255, 255)
    
    #MAIN BODY - TOP LEFT
    beginShape()
    texture(hullTexture)
    vertex(  0,   0, 29, 0, 0)
    vertex(  0,  -5, -29, 200, 0)
    vertex( 20,   0, -26, 200, 200)
    endShape(CLOSE)
    #MAIN BODY - TOP RIGHT
    beginShape()
    texture(hullTexture)
    vertex(  0,   0, 29, 0, 0)
    vertex(  0,  -5, -29, 200, 0)
    vertex(-20,   0, -26, 200, 200)
    endShape(CLOSE)
    #MAIN BODY - BOTTOM LEFT
    beginShape()
    texture(hullTexture)
    vertex(  0,   1, 29, 0, 0)
    vertex(  0,   5, -29, 200, 0)
    vertex( 20,   1, -26, 200, 200)
    endShape(CLOSE)
    #MAIN BODY - BOTTOM RIGHT
    beginShape()
    texture(hullTexture)
    vertex(  0,   1, 29, 0, 0)
    vertex(  0,   5, -29, 200, 0)
    vertex(-20,   1, -26, 200, 200)
    endShape(CLOSE)
    
    #MAIN BODY - SIDE - LEFT
    beginShape()
    texture(hullSide)
    vertex(  0,   0, 29, 0, 0)
    vertex(  0,   1, 29, 0, 20)
    vertex( 20,   1, -26, 300, 20)
    vertex( 20,   0, -26, 300, 0)
    endShape(CLOSE)
    #MAIN BODY - SIDE - RIGHT
    beginShape()
    texture(hullSide)
    vertex(  0,   0, 29, 0, 0)
    vertex(  0,   1, 29, 0, 20)
    vertex(-20,   1, -26, 300, 20)
    vertex(-20,   0, -26, 300, 0)
    endShape(CLOSE)
    
    #MAIN BODY - BACK
    beginShape()
    texture(engine)
    vertex(-20,    0, -26, 0, 0)
    vertex(-20,    1, -26, 0, 0)
    vertex(  0,  4.7, -26, 0, 100)
    vertex( 20,    1, -26, 208, 100)
    vertex( 20,    0, -26, 208, 100)
    vertex(  0, -4.7, -26, 208, 0)
    endShape(CLOSE)

def bridgeBase(hullTexture, hullSide):
    #FOUNDATION - TOP - LEFT
    beginShape()
    texture(hullTexture)
    vertex(  0,   -6, -10, 0, 0)
    vertex(  7,   -4, -10, 0, 200)
    vertex(  7,   -5, -26, 200, 200)
    vertex(  0,   -7, -26, 200, 0)
    endShape(CLOSE)
    #FOUNDATION - TOP - RIGHT
    beginShape()
    texture(hullTexture)
    vertex(  0,   -6, -10, 0, 0)
    vertex( -7,   -4, -10, 0, 200)
    vertex( -7,   -5, -26, 200, 200)
    vertex(  0,   -7, -26, 200, 0)
    endShape(CLOSE)
    
    #FOUNDATION - FRONT - LEFT
    beginShape()
    texture(hullSide)
    vertex(  7,   -4, -10, 0, 0)
    vertex(  7,   -1, -10, 0, 20)
    vertex(  0,   -3, -10, 300, 20)
    vertex(  0,   -6, -10, 300, 0)
    endShape(CLOSE)
    #FOUNDATION - FRONT - RIGHT
    beginShape()
    texture(hullSide)
    vertex( -7,   -4, -10, 0, 0)
    vertex( -7,   -1, -10, 0, 20)
    vertex(  0,   -3, -10, 300, 20)
    vertex(  0,   -6, -10, 300, 0)
    endShape(CLOSE)
    #FOUNDATION - SIDE - LEFT
    beginShape()
    texture(hullSide)
    vertex(  7,   -4, -10, 0, 0)
    vertex(  7,   -1, -10, 0, 20)
    vertex(  7,   -2, -26, 300, 20)
    vertex(  7,   -5, -26, 300, 0)
    endShape(CLOSE)
    #FOUNDATION - SIDE - RIGHT
    beginShape()
    texture(hullSide)
    vertex( -7,   -4, -10, 0, 0)
    vertex( -7,   -1, -10, 0, 20)
    vertex( -7,   -2, -26, 300, 20)
    vertex( -7,   -5, -26, 300, 0)
    endShape(CLOSE)
    #FOUNDATION - BACK - LEFT
    beginShape()
    texture(hullSide)
    vertex(  7,   -5, -26, 0, 0)
    vertex(  7,   -3, -26, 0, 20)
    vertex(  0, -4.7, -26, 300, 20)
    vertex(  0,   -7, -26, 300, 0)
    endShape(CLOSE)
    #FOUNDATION - BACK - RIGHT
    beginShape()
    texture(hullSide)
    vertex( -7,   -5, -26, 0, 0)
    vertex( -7,   -3, -26, 0, 20)
    vertex(  0, -4.7, -26, 300, 20)
    vertex(  0,   -7, -26, 300, 0)
    endShape(CLOSE)

def bridgeConnector(hullTexture, hullSide):
    #CONNECTOR - SIDE - FRONT
    beginShape()
    texture(hullSide)
    vertex( 1.5,  -11, -22, 0, 0)
    vertex(-1.5,  -11, -22, 0, 20)
    vertex(-1.5,   -8, -22, 300, 20)
    vertex( 1.5,   -8, -22, 300, 0)
    endShape(CLOSE)
    #CONNECTOR - SIDE - BACK
    beginShape()
    texture(hullSide)
    vertex( 1.5,  -11, -26, 0, 0)
    vertex(-1.5,  -11, -26, 0, 20)
    vertex(-1.5, -4.6, -28, 300, 20)
    vertex( 1.5, -4.6, -28, 300, 0)
    endShape(CLOSE)
    #CONNECTOR - SIDE - LEFT
    beginShape()
    texture(hullTexture)
    vertex( 1.5, -4.6, -22, 0, 0)
    vertex( 1.5, -4.6, -28, 0, 200)
    vertex( 1.5,  -11, -26, 200, 200)
    vertex( 1.5,  -11, -22, 200, 0)
    endShape(CLOSE)
    #CONNECTOR - SIDE - RIGHT
    beginShape()
    texture(hullTexture)
    vertex(-1.5, -4.6, -22, 0, 0)
    vertex(-1.5, -4.6, -28, 0, 200)
    vertex(-1.5,  -11, -26, 200, 200)
    vertex(-1.5,  -11, -22, 200, 0)
    endShape(CLOSE)
    #CONNECTOR - SIDE - TOP
    beginShape()
    texture(hullSide)
    vertex( 1.5,  -11, -22, 0, 0)
    vertex(-1.5,  -11, -22, 0, 20)
    vertex(-1.5,  -11, -26, 300, 20)
    vertex( 1.5,  -11, -26, 300, 0)
    endShape(CLOSE)

def bridge():
    fill(150,150,150)
    
    #BRIDGE - MAIN
    pushMatrix()
    translate(0,-11,-23)
    scale(10,1,4)
    box(1)
    popMatrix()
    
    #BRIDGE - LEFT DEFLECTOR SHIELD
    pushMatrix()
    translate(3,-12,-23)
    box(1.5)
    pushMatrix()
    translate(0,-1,0)
    sphere(1.2)
    popMatrix()
    popMatrix()
    
    #BRIDGE - RIGHT DEFLECTOR SHIELD
    pushMatrix()
    translate(-3,-12,-23)
    box(1.5)
    pushMatrix()
    translate(0,-1,0)
    sphere(1.2)
    popMatrix()
    popMatrix()
    