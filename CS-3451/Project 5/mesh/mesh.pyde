from cornerObj import *
from faceObj import *

# Sample code for starting the mesh processing project

rotate_flag = True    # automatic rotation of model
time = 0   # keep track of passing time, for automatic rotation

print_flag = False
color_flag = False
normalMode = False

vertexTable = [] # keeps vertex objects
faceTable = [] # keeps face objects
cornersTable = [] # keeps track of corners

# initalize stuff
def setup():
    size (600, 600, OPENGL)
    noStroke()

# draw the current mesh
def draw():
    global time
    global color_flag
    global normalMode
    
    background(0)    # clear screen to black

    perspective (PI*0.333, 1.0, 0.01, 1000.0)
    camera (0, 0, 5, 0, 0, 0, 0, 1, 0)    # place the camera in the scene
    scale (1, -1, 1)    # change to right-handed coordinate system
    
    # create an ambient light source
    ambientLight (102, 102, 102)
  
    # create two directional light sources
    lightSpecular (204, 204, 204)
    directionalLight (102, 102, 102, -0.7, -0.7, -1)
    directionalLight (152, 152, 152, 0, 0, -1)
    
    pushMatrix();

    fill (50, 50, 200)            # set polygon color
    ambient (200, 200, 200)
    specular (0, 0, 0)            # no specular highlights
    shininess (1.0)
  
    rotate (time, 1.0, 0.0, 0.0)

    # THIS IS WHERE YOU SHOULD DRAW THE MESH
    for face in faceTable:
        v1 = face.v1
        v2 = face.v2
        v3 = face.v3
        r = face.rgb[0]
        g = face.rgb[1]
        b = face.rgb[2]
        
        if (color_flag):
            fill(r, g, b)
        else:
            fill(200, 200, 200)
        
        beginShape()
        
        if (normalMode):
            vertexNormal = gourandInterpolation(v1)
            normal(vertexNormal[0], vertexNormal[1], vertexNormal[2])
        vertex (vertexTable[v1].x, vertexTable[v1].y, vertexTable[v1].z)
        
        if (normalMode):
            vertexNormal = gourandInterpolation(v2)
            normal(vertexNormal[0], vertexNormal[1], vertexNormal[2])
        vertex (vertexTable[v2].x, vertexTable[v2].y, vertexTable[v2].z)
        
        if (normalMode):
            vertexNormal = gourandInterpolation(v3)
            normal(vertexNormal[0], vertexNormal[1], vertexNormal[2])
        vertex (vertexTable[v3].x, vertexTable[v3].y, vertexTable[v3].z)
        
        endShape(CLOSE)
    
    popMatrix()
    
    # maybe step forward in time (for object rotation)
    if rotate_flag:
        time += 0.02

# process key presses
def keyPressed():
    global rotate_flag
    global color_flag
    global normalMode
    
    if key == ' ':
        rotate_flag = not rotate_flag
    elif key == '1':
        read_mesh ('tetra.ply')
    elif key == '2':
        read_mesh ('octa.ply')
    elif key == '3':
        read_mesh ('icos.ply')
    elif key == '4':
        read_mesh ('star.ply')
    elif key == '5':
        read_mesh ('torus.ply')
    elif key == 'n':
        # toggle per-vertex shading
        if (normalMode):
            normalMode = False
        else:
            normalMode = True
    elif key == 'r':
        # randomly color faces
        color_flag = True
    elif key == 'w':
        # color faces white
        color_flag = False
    elif key == 'd':
        # calculate the dual mesh
        dual()
    elif key == 'q':
        exit()

# read in a mesh file
def read_mesh(filename):
    global vertexTable
    global faceTable
    
    # resetting the global variables
    vertexTable = []
    faceTable = []

    fname = "data/" + filename
    # read in the lines of a file
    with open(fname) as f:
        lines = f.readlines()
        
    # determine number of vertices (on first line)
    words = lines[0].split()
    num_vertices = int(words[1])
    if (print_flag):
        print "number of vertices =", num_vertices

    # determine number of faces (on first second)
    words = lines[1].split()
    num_faces = int(words[1])
    if (print_flag):
        print "number of faces =", num_faces

    # read in the vertices
    for i in range(num_vertices):
        words = lines[i+2].split()
        x = float(words[0])
        y = float(words[1])
        z = float(words[2])
        if (print_flag):
            print "vertex = ", x, y, z
        
        vertexTable.append(Vertex(x, y, z))
    
    # read in the faces
    for i in range(num_faces):
        j = i + num_vertices + 2
        words = lines[j].split()
        nverts = int(words[0])
        if nverts != 3:
            print "error: this face is not a triangle"
            exit()
        index1 = int(words[1])
        index2 = int(words[2])
        index3 = int(words[3])
        if (print_flag):
            print "face =", index1, index2, index3
        
        faceTable.append(Face(index1, index2, index3, vertexTable))
    
    # build the corners table
    buildCornersTable()

# helper function to deal with building the corners table
def buildCornersTable():
    global vertexTable
    global faceTable
    global cornersTable
    
    cornersTable = []
    
    # building the corners table
    for faceIndex in range(len(faceTable)):
        cornersTable.append(Corner(faceTable[faceIndex].v1, faceIndex))
        cornersTable.append(Corner(faceTable[faceIndex].v2, faceIndex))
        cornersTable.append(Corner(faceTable[faceIndex].v3, faceIndex))
        for i in range(3):
            cornersTable[(3 * faceIndex) + i].n = cornersTable[(3 * faceIndex) + ((i + 1) % 3)]
            cornersTable[(3 * faceIndex) + i].p = cornersTable[(3 * faceIndex) + ((i + 2) % 3)]
    
    # setting the corners' opposites
    for aCorner in range(len(cornersTable)):
        for bCorner in range(len(cornersTable)):
            a = cornersTable[aCorner]
            b = cornersTable[bCorner]
            if (vertexTable[a.n.v].equals(vertexTable[b.p.v]) and 
                vertexTable[a.p.v].equals(vertexTable[b.n.v])):
                cornersTable[aCorner].o = b
                cornersTable[bCorner].o = a
    
    # setting the corners' left and right neighbors
    for corner in range(len(cornersTable)):
        cornersTable[corner].r = cornersTable[corner].n.o
        cornersTable[corner].l = cornersTable[corner].p.o
    
    # add all the associated corners to appropriate vertices
    for corner in cornersTable:
        vertexTable[corner.v].corners.append(corner)

# helper function to deal with creating geometry duals
def dual():
    global vertexTable
    global faceTable
    global cornersTable
    
    # creating new tables for drawing
    newVertexTable = []
    newFaceTable = []
    
    # initialize the variable for the "starting" index in the face table
    faceEndIndex = 0
    
    # iterate through all the vertices
    for vert in vertexTable:
        # initialize the avg centroid sums
        avgCentroidX = 0
        avgCentroidY = 0
        avgCentroidZ = 0
        
        # get a "first" corner
        first = vert.corners[0]
        
        # get the current corner
        current = first
        firstSwing = True
        
        # getting the valence number of the current vertex
        valence = 0
        
        # iterate through all the corners associated with the current vertex
        while ((not current.equals(first, vertexTable, faceTable)) or firstSwing):
            firstSwing = False
            
            # get the centroid from the corner
            currentCentroid = faceTable[current.t].centroid
            
            # adding the centroid values to the total avg centroid sum
            avgCentroidX += currentCentroid.x
            avgCentroidY += currentCentroid.y
            avgCentroidZ += currentCentroid.z
            
            # add the face's centroid to the new vertex table
            newVertexTable.append(currentCentroid)
            
            # increment valence
            valence += 1
            
            # swing to the next corner/face
            current = current.r.n
        
        # get the final avg value of the centroid
        avgCentroidX /= valence
        avgCentroidY /= valence
        avgCentroidZ /= valence
        
        # adding the "super-centroid" to the vertex table
        newVertexTable.append(Vertex(avgCentroidX, avgCentroidY, avgCentroidZ))
        
        # creating faces using the new vertices
        for i in range(valence):
            newFaceTable.append(Face(faceEndIndex + i, faceEndIndex + ((i+1) % valence), faceEndIndex + valence, newVertexTable))
        
        # moving faceEndIndex to the beginning of the next "available" index
        faceEndIndex = len(newVertexTable)
    
    # replace the vertex table and face table
    vertexTable = newVertexTable
    faceTable = newFaceTable
    
    # rebuild the corners table
    buildCornersTable()

# helper method to do gourand interpolation (per-vertex shading)
def gourandInterpolation(vIndex):
    global vertexTable
    global faceTable
    
    faceNormalSumX = 0
    faceNormalSumY = 0
    faceNormalSumZ = 0
    
    # get the normals for all the faces attached to the current vertex
    for corner in vertexTable[vIndex].corners:
        face = faceTable[corner.t]
        v1 = vertexTable[face.v1]
        v2 = vertexTable[face.v2]
        v3 = vertexTable[face.v3]
        
        e1 = (v1.x - v2.x, v1.y - v2.y, v1.z - v2.z)
        e2 = (v1.x - v3.x, v1.y - v3.y, v1.z - v3.z)
        
        crossX = (e1[1] * e2[2]) - (e1[2] * e2[1])
        crossY = (e1[2] * e2[0]) - (e1[0] * e2[2])
        crossZ = (e1[0] * e2[1]) - (e1[1] * e2[0])
        magnitude = sqrt((crossX**2) + (crossY**2) + (crossZ**2))
        
        faceNormalSumX += crossX/magnitude
        faceNormalSumY += crossY/magnitude
        faceNormalSumZ += crossZ/magnitude
    
    # get the average of all the face normals
    faceNormalSumX /= len(vertexTable[vIndex].corners)
    faceNormalSumY /= len(vertexTable[vIndex].corners)
    faceNormalSumZ /= len(vertexTable[vIndex].corners)
    
    return (-faceNormalSumX, -faceNormalSumY, -faceNormalSumZ)