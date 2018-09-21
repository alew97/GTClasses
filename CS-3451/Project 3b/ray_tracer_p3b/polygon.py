class Polygon:
    # vertices - a list of all the polygon's vertices
    def __init__(self, surfaceMaterial):
        self.vertices = []
        self.surfaceNormal = None
        self.surfaceMaterial = surfaceMaterial
    
    def addVertex(self, x, y, z):
        self.vertices.append((x,y,z))
    
    def calculateNormal(self):
        v1 = self.vertices[0]
        v2 = self.vertices[1]
        v3 = self.vertices[2]
        e1 = (v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2])
        e2 = (v1[0] - v3[0], v1[1] - v3[1], v1[2] - v3[2])
        result = crossProduct(e1, e2)
        crossX = result[0]
        crossY = result[1]
        crossZ = result[2]
        
        magnitude = sqrt((crossX**2) + (crossY**2) + (crossZ**2))
        self.surfaceNormal = (crossX/magnitude, crossY/magnitude, crossZ/magnitude)
    
    def setNormal(self, x,y,z):
        self.surfaceNormal = (x,y,z)

# helper method to do cross product
def crossProduct(e1, e2):
    crossX = (e1[1] * e2[2]) - (e1[2] * e2[1])
    crossY = (e1[2] * e2[0]) - (e1[0] * e2[2])
    crossZ = (e1[0] * e2[1]) - (e1[1] * e2[0])
    return (crossX, crossY, crossZ)