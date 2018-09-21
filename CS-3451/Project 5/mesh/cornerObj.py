class Corner:
    # v - the vertex index of the corner
    # t - the face the corner is part of, "triangle number" used in the faceTable
    # n - the "next" corner neighbor
    # p - the "previous" corner neighbor
    # r - the "right" corner neighbor
    # l - the "left" corner neighbor
    def __init__(self, v, t):
        self.v = v
        self.t = t
        self.n = None
        self.p = None
        self.o = None
        self.r = None
        self.l = None
    
    def equals(self, otherCorner, vertexTable, faceTable):
        return (vertexTable[self.v].equals(vertexTable[otherCorner.v]) and 
                faceTable[self.t].equals(faceTable[otherCorner.t], vertexTable))
    