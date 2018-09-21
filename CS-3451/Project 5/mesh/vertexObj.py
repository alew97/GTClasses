class Vertex:
    # x, y, z - vertex position
    # corners - table of corners associated with the vertex
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
        self.corners = []
    
    def equals(self, otherVertex):
        return (self.x == otherVertex.x and 
                self.y == otherVertex.y and 
                self.z == otherVertex.z)
    