from basic_objects import *

def xWing(xWingWingTop, xWingWingBottom, xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack, animAngle = 0):
    # Main Body
    chassis(xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack)
    
    # Cockpit
    cockpit()
    
    # Wing - Top Left
    pushMatrix()
    translate(14, -8, -26)
    rotateZ(radians(-animAngle))
    wingTL_BR(xWingWingTop, xWingWingBottom)
    popMatrix()
    
    # Wing - Top Right
    pushMatrix()
    translate(-14, -8, -26)
    rotateZ(radians(animAngle))
    wingTR_BL(xWingWingTop, xWingWingBottom)
    popMatrix()
    
    # Wing - Bottom Right
    pushMatrix()
    translate(-14, 4, -26)
    rotateZ(radians(180))
    rotateZ(radians(-animAngle))
    wingTL_BR(xWingWingTop, xWingWingBottom)
    popMatrix()
    
    # Wing - Bottom Left
    pushMatrix()
    translate( 14, 4, -26)
    rotateZ(radians(180))
    rotateZ(radians(animAngle))
    wingTR_BL(xWingWingTop, xWingWingBottom)
    popMatrix()

def cockpit():
    fill(150, 150, 150)
    stroke(255,255,255)
    
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
    
    noStroke()

def chassis(xWingFrontTop, xWingSideTopSide, xWingSternTop, xWingSternBack):
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
    texture(xWingFrontTop)
    vertex(  2,  -2, 50, 0, 0)
    vertex(  5,  -7, 3, 128, 0)
    vertex( -5,  -7, 3, 128, 16)
    vertex( -2,  -2, 50, 0, 16)
    endShape(CLOSE)
    #MAIN BODY - FRONT HULL PLATE - BOTTOM
    beginShape()
    vertex( -2,   2, 50)
    vertex( -6, 3.5, -1)
    vertex(  6, 3.5, -1)
    vertex(  2,   2, 50)
    endShape(CLOSE)
    
    #MAIN BODY - FRONT HULL PLATE - TOP LEFT
    beginShape()
    texture(xWingSideTopSide)
    vertex(  2,  -2, 50, 0, 10)
    vertex(  5,  -7, 3, 36, 10)
    vertex(  7,  -5, -1, 88, 10)
    vertex(  8,   0, -1, 88, 0)
    vertex(  3,   0, 51, 0, 0)
    endShape(CLOSE)
    #MAIN BODY - FRONT HULL PLATE - TOP RIGHT
    beginShape()
    texture(xWingSideTopSide)
    vertex( -2,  -2, 50, 0, 10)
    vertex( -5,  -7, 3, 36, 10)
    vertex( -7,  -5, -1, 88, 10)
    vertex( -8,   0, -1, 88, 0)
    vertex( -3,   0, 51, 0, 0)
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
    
    #MAIN BODY - SIDE HULL PLATE - TOP LEFT
    beginShape()
    texture(xWingSideTopSide)
    vertex(  8,   0, -1, 0, 0)
    vertex(  7,  -5, -1, 0, 10)
    vertex( 10,  -4, -12, 88, 10)
    vertex( 11,   0, -12, 88, 0)
    endShape(CLOSE)
    beginShape()
    vertex( 11,   0, -12)
    vertex( 10,  -4, -12)
    vertex(  8,  -8, -15)
    vertex( 11,   0, -15)
    endShape(CLOSE)
    #MAIN BODY - SIDE HULL PLATE - TOP RIGHT
    beginShape()
    texture(xWingSideTopSide)
    vertex( -8,   0, -1, 0, 0)
    vertex( -7,  -5, -1, 0, 10)
    vertex(-10,  -4, -12, 88, 10)
    vertex(-11,   0, -12, 88, 0)
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
    texture(xWingSternTop)
    vertex(  8,  -8, -15, 0, 0)
    vertex( -8,  -8, -15, 0, 48)
    vertex( -8,  -8, -43, 100, 48)
    vertex(  8,  -8, -43, 100, 0)
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
    
    #ENGINE - STERN FOUNDATION - BASE
    beginShape()
    texture(xWingSternBack)
    vertex( -7,  -7, -42, 0, 60)
    vertex(  7,  -7, -42, 60, 60)
    vertex( 10,   0, -42, 60, 30)
    vertex(  7,   6, -42, 60, 0)
    vertex( -7,   6, -42, 0, 0)
    vertex(-10,   0, -42, 0, 30)
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

def wingTL_BR(xWingWingTop, xWingWingBottom):
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
    texture(xWingWingTop)
    vertex( -5,   5, -14, 0, 78)
    vertex( 42,   5, -2, 0, 0)
    vertex( 42,   5, 6, 60, 0)
    vertex( -5,   5, 6, 60, 78)
    endShape(CLOSE)
    #WING - MAIN PART - BOTTOM
    beginShape()
    texture(xWingWingBottom)
    vertex( -5,   6, -14, 0, 78)
    vertex( 42,   6, -2, 0, 0)
    vertex( 42,   6, 6, 60, 0)
    vertex( -5,   6, 6, 60, 78)
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
    
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER
    pushMatrix()
    translate(41, 3.5, 2)
    scale(2, 2, 6)
    cylinder()
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER 2
    pushMatrix()
    translate(41, 3.5, 11)
    scale(2, 2, 1)
    cylinder()
    popMatrix()
    
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER CONNECTOR
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 9)
    scale(1.4, 1.4, 1)
    cylinder()
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - EXTENSION
    pushMatrix()
    translate(41, 3.5, 22)
    scale(0.8, 0.8, 10)
    cylinder()
    popMatrix()
    
    #WING - EDGE CYLINDER BLASTER - EXTENSION 2
    fill(150, 150, 150)
    pushMatrix()
    translate(41, 3.5, 42)
    scale(0.4, 0.4, 12)
    cylinder()
    popMatrix()
    
    fill(255, 255, 255)
    #WING - EDGE CYLINDER BLASTER - DETAIL UP HOOK
    pushMatrix()
    translate(41,2,55)
    rotateX(radians(-60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - DETAIL MIDDLE HOOK
    pushMatrix()
    translate(41,3.5,54)
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - DETAIL DOWN HOOK
    pushMatrix()
    translate(41,5,55)
    rotateX(radians(60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    
def wingTR_BL(xWingWingTop, xWingWingBottom):
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
    
    ################################
    pushMatrix()
    translate(0, 0, -3.8)
    rotateY(radians(180))
    #-------------------------------
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
    #-------------------------------
    ################################
    popMatrix()
    
    ################################
    pushMatrix()
    translate(0, 11, 0)
    rotateZ(radians(180))
    #-------------------------------
    fill(255, 255, 255)
    #WING - MAIN PART - BOTTOM
    beginShape()
    texture(xWingWingBottom)
    vertex( -5,   5, -14, 0, 78)
    vertex( 42,   5, -2, 0, 0)
    vertex( 42,   5, 6, 60, 0)
    vertex( -5,   5, 6, 60, 78)
    endShape(CLOSE)
    #WING - MAIN PART - TOP
    beginShape()
    texture(xWingWingTop)
    vertex( -5,   6, -14, 0, 78)
    vertex( 42,   6, -2, 0, 0)
    vertex( 42,   6, 6, 60, 0)
    vertex( -5,   6, 6, 60, 78)
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
    #-------------------------------
    ################################
    popMatrix()
    
    ################################
    pushMatrix()
    translate(0, 7, 0)
    rotateZ(radians(180))
    #-------------------------------
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER
    pushMatrix()
    translate(41, 3.5, 2)
    scale(2, 2, 6)
    cylinder()
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER 2
    pushMatrix()
    translate(41, 3.5, 11)
    scale(2, 2, 1)
    cylinder()
    popMatrix()
    
    #WING - EDGE CYLINDER BLASTER - MAIN CYLINDER CONNECTOR
    fill(100, 100, 100)
    pushMatrix()
    translate(41, 3.5, 9)
    scale(1.4, 1.4, 1)
    cylinder()
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - EXTENSION
    pushMatrix()
    translate(41, 3.5, 22)
    scale(0.8, 0.8, 10)
    cylinder()
    popMatrix()
    
    #WING - EDGE CYLINDER BLASTER - EXTENSION 2
    fill(150, 150, 150)
    pushMatrix()
    translate(41, 3.5, 42)
    scale(0.4, 0.4, 12)
    cylinder()
    popMatrix()
    
    fill(255, 255, 255)
    #WING - EDGE CYLINDER BLASTER - DETAIL UP HOOK
    pushMatrix()
    translate(41,2,55)
    rotateX(radians(-60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - DETAIL MIDDLE HOOK
    pushMatrix()
    translate(41,3.5,54)
    scale(0.5,2,0.5)
    box(1)
    popMatrix()
    #WING - EDGE CYLINDER BLASTER - DETAIL DOWN HOOK
    pushMatrix()
    translate(41,5,55)
    rotateX(radians(60))
    scale(0.5,3,0.5)
    box(1)
    popMatrix()
    #-------------------------------
    ################################
    popMatrix()