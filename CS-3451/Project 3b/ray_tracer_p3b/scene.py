class Scene:
    # fov - the scene's camera fov (perspective view)
    # objectArray - an array of all the scene's objects
    # lightArray - an array of all the scene's lights
    # r,g,b - the scene's background color
    def __init__(self):
        self.fov = 0
        self.objectArray = []
        self.lightArray = []
        self.backgroundR = 0
        self.backgroundG = 0
        self.backgroundB = 0
    
    # sets the scene's background color
    def setBackgroundColor(self, r,g,b):
        self.backgroundR = r
        self.backgroundG = g
        self.backgroundB = b
    
    # adds a light to the scene's light array
    def addLight(self, light):
        self.lightArray.append(light)
    
    # adds an object to the scene's object array
    def addObject(self, object):
        self.objectArray.append(object)