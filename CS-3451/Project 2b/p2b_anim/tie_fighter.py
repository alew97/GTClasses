from basic_objects import *

def tieFighter(tieHull, tieWing):
    # Main Body
    cockpit(tieHull)
    
    # Wing - Left
    pushMatrix()
    translate(-6,0,0)
    rotateY(radians(-90))
    wing(tieWing)
    popMatrix()
    
    # Wing - Right
    pushMatrix()
    translate(6,0,0)
    rotateY(radians(90))
    wing(tieWing)
    popMatrix()

def cockpit(tieHull):
    pushMatrix()
    translate(0,-5,0)
    scale(10,10,10)
    rotateY(radians(90))
    rotateX(radians(-90))
    customSphere(tieHull, 180, 100, 16)
    popMatrix()

def wing(tieWing):
    #WING - OUTER FACE
    beginShape()
    texture(tieWing)
    vertex(  6, -11, 2, 50, 0)
    vertex( -6, -11, 2, 10, 0)
    vertex(-10,   0, 2, 0, 30)
    vertex( -6,  11, 2, 10, 60)
    vertex(  6,  11, 2, 50, 60)
    vertex( 10,   0, 2, 60, 30)
    endShape(CLOSE)
    
    #WING - INNER FACE
    beginShape()
    texture(tieWing)
    vertex(  6, -11, 1, 50, 0)
    vertex( -6, -11, 1, 10, 0)
    vertex(-10,   0, 1, 0, 30)
    vertex( -6,  11, 1, 10, 60)
    vertex(  6,  11, 1, 50, 60)
    vertex( 10,   0, 1, 60, 30)
    endShape(CLOSE)
    
    #WING - SIDE - TOP
    beginShape()
    vertex(  6, -11, 1)
    vertex( -6, -11, 1)
    vertex( -6, -11, 2)
    vertex(  6, -11, 2)
    endShape(CLOSE)
    #WING - SIDE - TOP FRONT
    beginShape()
    vertex( -6, -11, 1)
    vertex( -6, -11, 2)
    vertex(-10,   0, 2)
    vertex(-10,   0, 1)
    endShape(CLOSE)
    #WING - SIDE - TOP BACK
    beginShape()
    vertex(  6, -11, 1)
    vertex(  6, -11, 2)
    vertex( 10,   0, 2)
    vertex( 10,   0, 1)
    endShape(CLOSE)
    #WING - SIDE - BOTTOM
    beginShape()
    vertex(  6,  11, 1)
    vertex( -6,  11, 1)
    vertex( -6,  11, 2)
    vertex(  6,  11, 2)
    endShape(CLOSE)
    #WING - SIDE - BOTTOM FRONT
    beginShape()
    vertex( -6,  11, 1)
    vertex( -6,  11, 2)
    vertex(-10,   0, 2)
    vertex(-10,   0, 1)
    endShape(CLOSE)
    
    #WING - CONNECTOR
    pushMatrix()
    translate(0,0,-1)
    scale(1,1,2)
    cylinder()
    popMatrix()