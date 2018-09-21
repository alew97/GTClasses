class Hit:
    # t - result of intersection between line and object (quadratic equation)
    # x,y,z - position of intersection
    # object - id of object that was hit
    # surfaceNormal - surface normal vector at intersection point
    def __init__(self, t, x, y, z, object, surfaceNormal):
        self.t = t
        self.x = x
        self.y = y
        self.z = z
        self.object = object
        self.surfaceNormal = surfaceNormal
    