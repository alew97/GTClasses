from vertexObj import *
import random

class Face:
    # v1, v2, v3 - the collection of vertex indices that are part of the face, used in the vertexTable
    # corners - the collection of corners that are part of the face
    def __init__(self, v1, v2, v3, vertexTable):
        self.v1 = v1
        self.v2 = v2
        self.v3 = v3
        self.centroid = Vertex((vertexTable[v1].x + vertexTable[v2].x + vertexTable[v3].x)/3,
                               (vertexTable[v1].y + vertexTable[v2].y + vertexTable[v3].y)/3,
                               (vertexTable[v1].z + vertexTable[v2].z + vertexTable[v3].z)/3)
        self.rgb = (random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))
    
    def equals(self, otherFace, vertexTable):
        return (vertexTable[self.v1].equals(vertexTable[otherFace.v1]) and 
                vertexTable[self.v2].equals(vertexTable[otherFace.v2]) and 
                vertexTable[self.v3].equals(vertexTable[otherFace.v3]))
    