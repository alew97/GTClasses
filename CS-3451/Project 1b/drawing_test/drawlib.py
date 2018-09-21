# Drawing Routines, like OpenGL

from matlib import *
import math

def gtOrtho(left, right, bottom, top, near, far):
    global projection
    projection = ["O", left, right, bottom, top]

def gtPerspective(fov, near, far):
    global projection
    projection = ["P", fov]

def gtBeginShape():
    global vertexPairs
    vertexPairs = []

def gtEndShape():
    for vPoint in range(0, len(vertexPairs), 2):
        line(vertexPairs[vPoint][0], vertexPairs[vPoint][1], vertexPairs[vPoint + 1][0], vertexPairs[vPoint + 1][1])

def gtVertex(x, y, z):
    vectorMatrix = [
    [x],
    [y],
    [z],
    [1]]
    
    ctm = returnCTM()
    
    result = multMatrices(ctm, vectorMatrix)
    
    x = result[0][0]
    y = result[1][0]
    z = result[2][0]
    
    if(projection[0] == "O"):
        left = projection[1]
        right = projection[2]
        bottom = projection[3]
        top = projection[4]
        
        x1 = (x - left) * (width / (right - left))
        y1 = (y - top) * (height / (bottom - top))
        #z1 = (z - near) * (0 / (far - near))
    
    if(projection[0] == "P"):
        fov = projection[1]
        
        theta_rad = fov * math.pi / 180
        k = math.tan(theta_rad / 2.0)
        
        x1 = ((x / abs(z)) + k) * (width / (2 * k))
        y1 = ((y / -abs(z)) + k) * (height / (2 * k))
    
    vertexPairs.append([x1, y1, 0])
    
    