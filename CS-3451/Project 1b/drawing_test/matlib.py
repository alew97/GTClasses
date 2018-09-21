# Matrix Stack Library -- Use your code from Project 1A

import math

def gtInitialize():
    identityMatrix = [
    [1,0,0,0],
    [0,1,0,0],
    [0,0,1,0],
    [0,0,0,1]]
    
    global stack
    stack = []
    stack.append(identityMatrix)

def gtPushMatrix():
    ctm = stack[len(stack) - 1]
    stack.append(ctm)

def gtPopMatrix():
    if len(stack) == 1:
        print("cannot pop the matrix stack")
    else:
        stack.pop()

def gtTranslate(x, y, z):
    ctm = stack[len(stack) - 1]
    
    translateMatrix = [
    [1,0,0,x],
    [0,1,0,y],
    [0,0,1,z],
    [0,0,0,1]]
    
    result = multMatrices(ctm, translateMatrix)
    stack[len(stack)-1] = result

def gtScale(x, y, z):
    ctm = stack[len(stack) - 1]
    
    scaleMatrix = [
    [x,0,0,0],
    [0,y,0,0],
    [0,0,z,0],
    [0,0,0,1]]
    
    result = multMatrices(ctm, scaleMatrix)
    stack[len(stack)-1] = result

def gtRotateX(theta):
    ctm = stack[len(stack) - 1]
    
    theta_rad = theta * math.pi / 180
    cosTheta = math.cos(theta_rad)
    sinTheta = math.sin(theta_rad)
    
    rotateMatrix = [
    [1,0,0,0],
    [0,cosTheta,-sinTheta,0],
    [0,sinTheta, cosTheta,0],
    [0,0,0,1]]
    
    result = multMatrices(ctm, rotateMatrix)
    stack[len(stack)-1] = result

def gtRotateY(theta):
    ctm = stack[len(stack) - 1]
    
    theta_rad = theta * math.pi / 180
    cosTheta = math.cos(theta_rad)
    sinTheta = math.sin(theta_rad)
    
    rotateMatrix = [
    [cosTheta, 0,sinTheta,0],
    [0,1,0,0],
    [-sinTheta,0,cosTheta,0],
    [0,0,0,1]]
    
    result = multMatrices(ctm, rotateMatrix)
    stack[len(stack)-1] = result

def gtRotateZ(theta):
    ctm = stack[len(stack) - 1]
    
    theta_rad = theta * math.pi / 180
    cosTheta = math.cos(theta_rad)
    sinTheta = math.sin(theta_rad)
    
    rotateMatrix = [
    [cosTheta,-sinTheta,0,0],
    [sinTheta, cosTheta,0,0],
    [0,0,1,0],
    [0,0,0,1]]
    
    result = multMatrices(ctm, rotateMatrix)
    stack[len(stack)-1] = result

def print_ctm():
    ctm = stack[len(stack)-1]
    
    for mat_row in range(4):
        print(ctm[mat_row])
    
    print("")
    
def returnCTM():
    return stack[len(stack) - 1]

def multMatrices(a, b):
    #initializing the result matrix
    mat_rows = len(a)
    mat_cols = len(b[0])
    returnMatrix = []
    for mat_row in range(mat_rows):
        returnMatrix.append([])
        for mat_col in range(mat_cols):
            returnMatrix[mat_row].append(0)
    
    #setting the values
    for mat_row in range(mat_rows):
        for mat_col in range(mat_cols):
            for mat_iter in range(len(b)):
                returnMatrix[mat_row][mat_col] += a[mat_row][mat_iter] * b[mat_iter][mat_col]
    
    return returnMatrix