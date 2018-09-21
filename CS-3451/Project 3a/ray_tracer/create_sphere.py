class Sphere:
    # x,y,z - position of the sphere's center
    # radius - radius of the sphere
    # surfaceMaterial - the surface material of the sphere
    def __init__(self, x, y, z, radius, surfaceMaterial):
        self.x = x
        self.y = y
        self.z = z
        self.radius = radius
        self.surfaceMaterial = surfaceMaterial
    