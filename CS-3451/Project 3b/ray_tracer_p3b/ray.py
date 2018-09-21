from hit import *
from create_sphere import *
from polygon import *

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
    
    def findIntersection(self, object):
        # calculate the intersection between the eye ray and sphere object
        if (isinstance(object, Sphere)):
            # ray's origin
            x0 = self.x
            y0 = self.y
            z0 = self.z
            # ray's direction
            dx = self.dx
            dy = self.dy
            dz = self.dz
            # sphere's center position
            cx = object.x
            cy = object.y
            cz = object.z
            # sphere's radius
            r = object.radius
            
            a = (dx**2) + (dy**2) + (dz**2)
            b = 2 * (((x0 * dx) - (cx * dx)) + ((y0 * dy) - (cy * dy)) + ((z0 * dz) - (cz * dz)))
            c = (x0 - cx)**2 + (y0 - cy)**2 + (z0 - cz)**2 - r**2
            
            discriminant = b**2 - (4.0 * a * c)
            
            if (discriminant > 0):
                #two intersections
                t = (-b + sqrt(discriminant)) / (2.0 * a)
                self.getClosestIntersection(t, object)
                
                t = (-b - sqrt(discriminant)) / (2.0 * a)
                self.getClosestIntersection(t, object)
            elif (discriminant == 0):
                #one intersection
                t = -b / (2.0 * a)
                self.getClosestIntersection(t, object)
            else:
                #no intersection
                pass
        
        # calculate the intersection between the eye ray and polygon object
        elif (isinstance(object, Polygon)):
            # check to see if the ray is parallel to the polygon plane by taking the cross product of the polygon normal and eye ray
            polyNorm = object.surfaceNormal
            eyeRay = findUnitVector(self.dx, self.dy, self.dz)
            parallelTest = dot_product(eyeRay, polyNorm)
            
            # checks to see if the eye ray is in the same direction as the surface normal
            if (parallelTest > 0):
                object.surfaceNormal = (-object.surfaceNormal[0], -object.surfaceNormal[1], -object.surfaceNormal[2])
            
            # if (normal dot eye) is not 0, a.k.a. not parallel to the plane
            if (parallelTest != 0):
                # ray's origin
                x0 = self.x
                y0 = self.y
                z0 = self.z
                # ray's direction
                dx = self.dx
                dy = self.dy
                dz = self.dz
                # polygon plane variables
                a = object.surfaceNormal[0]
                b = object.surfaceNormal[1]
                c = object.surfaceNormal[2]
                
                somePoint = (object.vertices[0][0], object.vertices[0][1], object.vertices[0][2])
                d = -dot_product(object.surfaceNormal, somePoint)
                
                t = -((a * x0) + (b * y0) + (c * z0) + d)/((a * dx) + (b * dy) + (c * dz))
                
                # Point-In-Polygon Test (Half-Plane)
                pointToTest = (x0 + dx * t, y0 + dy * t, z0 + dz * t)
                
                vertices = object.vertices
                
                edge12 = (vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2])
                edge23 = (vertices[2][0] - vertices[1][0], vertices[2][1] - vertices[1][1], vertices[2][2] - vertices[1][2])
                edge31 = (vertices[0][0] - vertices[2][0], vertices[0][1] - vertices[2][1], vertices[0][2] - vertices[2][2])
                
                pointFromV1 = (vertices[0][0] - pointToTest[0], vertices[0][1] - pointToTest[1], vertices[0][2] - pointToTest[2])
                pointFromV2 = (vertices[1][0] - pointToTest[0], vertices[1][1] - pointToTest[1], vertices[1][2] - pointToTest[2])
                pointFromV3 = (vertices[2][0] - pointToTest[0], vertices[2][1] - pointToTest[1], vertices[2][2] - pointToTest[2])
                
                edgeTest1 = dot_product(crossProduct(edge12, pointFromV1), polyNorm)
                edgeTest2 = dot_product(crossProduct(edge23, pointFromV2), polyNorm)
                edgeTest3 = dot_product(crossProduct(edge31, pointFromV3), polyNorm)
                
                if ((edgeTest1 <= 0 and edgeTest2 <= 0 and edgeTest3 <= 0) or (edgeTest1 >= 0 and edgeTest2 >= 0 and edgeTest3 >= 0)):
                    self.getClosestIntersection(t, object)
    
    def getClosestIntersection(self, t, object):
        # if there is no closest intersection yet or if t is smaller than the current intersection's t
        if (t > 0 and (self.closestIntersection is None or t < self.closestIntersection.t)):
            # calculate the position of the intersection using parametric equations
            xHit = self.x + (t * self.dx)
            yHit = self.y + (t * self.dy)
            zHit = self.z + (t * self.dz)
            
            if (isinstance(object, Sphere)):
                # calculate surface normal vector (the vector from the center of the sphere to the intersection point)
                normX = xHit - object.x
                normY = yHit - object.y
                normZ = zHit - object.z
                surfaceNormal = findUnitVector(normX, normY, normZ)
                
                # replace the closest intersection with a new hit object using the smallest t
                self.closestIntersection = Hit(t, xHit, yHit, zHit, object, surfaceNormal)
                
            elif (isinstance(object, Polygon)):
                self.closestIntersection = Hit(t, xHit, yHit, zHit, object, object.surfaceNormal)

#helper method to do dot product
def dot_product(v1, v2):
    return (v1[0] * v2[0]) + (v1[1] * v2[1]) + (v1[2] * v2[2])

#helper method to do normalization
def findUnitVector(x, y, z):
    magnitude = sqrt((x**2) + (y**2) + (z**2))
    return (x/magnitude, y/magnitude, z/magnitude)