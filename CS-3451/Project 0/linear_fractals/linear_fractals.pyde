# Example Processing.py program

# called just once at the start of program execution
def setup():
    size( 800, 800 )      # set size of the screen (in pixels)

# called repeatedly, usually to draw on the screen
def draw():
    background( 0, 0, 0 ) # set background color to specified value
    noStroke()            # do not draw shape outlines
    
    # converts the mouse position to the specified range
    newMousePos = pixelToRange( mouseX, mouseY, 4 )
    
    # draws the fractal (can input certain coordinates to check certain patterns)
    drawFractal( newMousePos[0], newMousePos[1], -1, 0, 0 )
    
    if( mousePressed ):
        print("")
        print( mouseX )
        print( mouseY )

# called to draw a fractal pattern depending on coordinates within a pre-determined range
def drawFractal( realComponent, imagComponent, power, prevRealComponent, prevImagComponent ):
    # condition to determine the greatest exponent power the v-term can achieve
    if( power > 9 ):
        return
    
    # determining the coordinates of the dot
    finalVTerm = [ realComponent, imagComponent ]
    originTerm = [ realComponent, imagComponent ]
    
    # anything to the power of 0 is 1, otherwise, do multiplication to determine the complex number
    if( power == -1 ):
        finalVTerm[0] = 0
        finalVTerm[1] = 0
    elif( power == 0 ):
        finalVTerm[0] = 1
        finalVTerm[1] = 0
    else:
        for iteration in range( power - 1 ):
            # does FOIL multiplication
            multList = foilMult( finalVTerm[0], finalVTerm[1], originTerm[0], originTerm[1] )
            
            # overwrites the first term to be multiplied with the original term again (for power multiplication)
            finalVTerm[0] = multList[0]
            finalVTerm[1] = multList[1]
    
    # adds the v-term to the previous sum of terms
    realCompSumPos = prevRealComponent + finalVTerm[0]
    imagCompSumPos = prevImagComponent + finalVTerm[1]
    
    # subtracts the v-term to the previous sum of terms
    realCompSumNeg = prevRealComponent - finalVTerm[0]
    imagCompSumNeg = prevImagComponent - finalVTerm[1]
    
    # calculates the dot's coordinates on the pixel area and draws it
    xPosDraw, yPosDraw = rangeToPixel( realCompSumPos, imagCompSumPos, 4 )
    determineColorFill( xPosDraw, yPosDraw )
    ellipse( xPosDraw, yPosDraw, 5, 5 )
    xPosDraw, yPosDraw = rangeToPixel( realCompSumNeg, imagCompSumNeg, 4 )
    determineColorFill( xPosDraw, yPosDraw )
    ellipse( xPosDraw, yPosDraw, 5, 5 )
    
    # recurses to find the other sum of terms based on this v-term
    drawFractal( realComponent, imagComponent, power + 1, realCompSumPos, imagCompSumPos )
    drawFractal( realComponent, imagComponent, power + 1, realCompSumNeg, imagCompSumNeg )

# called to deal with FOIL multiplication between two complex terms
def foilMult( firstTermReal, firstTermImag, secondTermReal, secondTermImag ):
    realTerm = ( firstTermReal * secondTermReal ) - ( firstTermImag * secondTermImag )
    imagTerm = ( firstTermImag * secondTermReal ) + ( firstTermReal * secondTermImag )
    return [ realTerm, imagTerm ]

# called to convert the input from pixel range [0, size] to the specified axis range
def pixelToRange( originalX, originalY, axisRange ):
    originX = width  / 2.0        # get the X position of the origin
    originY = height / 2.0        # get the Y position of the origin
    
    xPos = originalX - originX    # get the original X position relative to the origin
    yPos = originY   - originalY  # get the original Y position relative to the origin
    
    # calculate and rescale the X position on the range[-axisRange, axisRange]
    if( xPos != 0 ):
        xPos /= originX
        xPos *= axisRange
    # calculate and rescale the Y position on the range[-axisRange, axisRange]
    if( yPos != 0 ):
        yPos /= originY
        yPos *= axisRange
    
    # returns the converted coordinates
    return [ xPos, yPos ]

# called to convert the input from the specified axis range to the pixel range [0, size]
def rangeToPixel( originalX, originalY, axisRange ):
    # shifts the range from -axisRange to 0
    xPos = originalX  + axisRange
    yPos = -originalY + axisRange
    
    # calculate and rescale the coordinates to the pixel draw area
    xPos = xPos * 800 / ( axisRange * 2 )
    yPos = yPos * 800 / ( axisRange * 2 )
    
    # returns the converted coordinates
    return [ xPos, yPos ]

# called to determine the fill color, based on position, for the dot about to be drawn
# (makes a certain symbol if enough dots cover the screen)
def determineColorFill( xPos, yPos ):
    if(    ( 50  < xPos and xPos < 150 and 300 < yPos and yPos < 600 ) or
           ( 650 < xPos and xPos < 750 and 300 < yPos and yPos < 600 ) or
           ( 150 < xPos and xPos < 650 and 500 < yPos and yPos < 700 ) or
           ( 100 < xPos and xPos < 250 and 450 < yPos and yPos < 600 ) or
           ( 550 < xPos and xPos < 700 and 450 < yPos and yPos < 600 ) or
           ( 220 < xPos and xPos < 580 and 700 < yPos and yPos < 730 ) or
           ( 100 < xPos and xPos < 180 and 170 < yPos and yPos < 300 ) or
           ( 620 < xPos and xPos < 700 and 170 < yPos and yPos < 300 ) or
           ( 160 < xPos and xPos < 230 and 150 < yPos and yPos < 180 ) or
           ( 570 < xPos and xPos < 640 and 150 < yPos and yPos < 180 ) or
           ( 340 < xPos and xPos < 460 and 450 < yPos and yPos < 500 ) or
           ( 365 < xPos and xPos < 435 and 400 < yPos and yPos < 450 ) or
           ( 383 < xPos and xPos < 417 and 110 < yPos and yPos < 400 ) or
           ( 360 < xPos and xPos < 440 and 145 < yPos and yPos < 180 )
      ):
        fill( 255, 0, 0 )
    else:
        fill( 255, 255, 255 )