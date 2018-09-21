# In the routine below, you should draw your initials in perspective

from matlib import *
from drawlib import *

def persp_initials():
    gtInitialize()
    gtPerspective (60, 0, 0)
    
    gtPushMatrix()
    gtTranslate(0,0,-10)
    gtRotateX(-30)
    
    gtBeginShape ()
    
    #J - First Initial
    gtVertex (-2.5,  2.0,  1.0)
    gtVertex ( 0.5,  2.0,  1.0)
    
    gtVertex (-1.0,   2.0,  1.0)
    gtVertex (-1.0,  -2.0,  1.0)
    
    gtVertex (-3.0,  -2.0,  1.0)
    gtVertex (-1.0,  -2.0,  1.0)
    
    gtVertex (-3.0,  -2.0,  1.0)
    gtVertex (-3.0,  -1.0,  1.0)
    
    #L - Last Initial
    gtVertex (1.0,  -2.0,  1.0)
    gtVertex (3.5,  -2.0,  1.0)
    
    gtVertex (1.0,   2.0,  1.0)
    gtVertex (1.0,  -2.0,  1.0)
    
    gtEndShape()
    
    gtPopMatrix()