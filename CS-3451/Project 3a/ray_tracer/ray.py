from hit import *

class Ray:
    # x,y,z - position of ray's origin
    # dx, dy, dz - direction of ray vector
    # closestIntersection - the closest intersection of the ray in relation to a scene
    def __init__(self, x, y, z, dx, dy, dz):
        self.x = x
        self.y = y
        self.z = z
        self.dx = dx
        self.dy = dy
        self.dz = dz
        self.closestIntersection = None
    
    # compares an input t and the ray's t to find a minimal value, then sets the ray's closest intersection object
    def getClosestIntersection(self, t, object):
        # if there is no closest intersection yet or if t is smaller than the current intersection's t
        if (self.closestIntersection is None or t < self.closestIntersection.t):
            # calculate the position of the intersection using parametric equations
            xHit = self.x + (t * self.dx)
            yHit = self.y + (t * self.dy)
            zHit = self.z + (t * self.dz)
            
            # calculate surface normal vector (the vector from the center of the sphere to the intersection point)
            normX = xHit - object.x
            normY = yHit - object.y
            normZ = zHit - object.z
            magnitude = sqrt((normX**2) + (normY**2) + (normZ**2))
            surfaceNormal = (normX/magnitude, normY/magnitude, normZ/magnitude)
            
            # replace the closest intersection with a new hit object using the smallest t
            self.closestIntersection = Hit(t, xHit, yHit, zHit, object, surfaceNormal)
    