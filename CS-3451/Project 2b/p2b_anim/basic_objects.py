# cylinder with radius = 1, z range in [-1,1]
def cylinder(sides=64):
    # first endcap
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex(x, y, -1)
    endShape(CLOSE)
    # second endcap
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex(x, y, 1)
    endShape(CLOSE)
    # sides
    x1 = 1
    y1 = 0
    for i in range(sides):
        theta = (i + 1) * 2 * PI / sides
        x2 = cos(theta)
        y2 = sin(theta)
        beginShape()
        normal(x1, y1, 0)
        vertex(x1, y1, 1)
        vertex(x1, y1, -1)
        normal(x2, y2, 0)
        vertex(x2, y2, -1)
        vertex(x2, y2, 1)
        endShape(CLOSE)
        x1 = x2
        y1 = y2

# custom sphere function with ability to apply a texture from TA Caitlin
def customSphere(img, picWidth, picHeight, lon=64):
    # latitude can be anything, but half the longitude makes the most sense
    lat = lon / 2

    # assume circle radius of .5
    # t and b are short for top and bottom (lesser or greater z, respectively)
    for j in range(lat):
        # z is in range of 0 to 1 (where 2r = 1)
        phit = float(j) / lat * PI
        phib = float(j + 1) / lat * PI
        #z = (r- cos(phi))/2
        zt = 0.5 * (1 - cos(phit))
        zb = 0.5 * (1 - cos(phib))
        # radius for each longitude circle is a function of the vertical angle,
        # phi
        rt = 0.5 * sin(phit)
        rb = 0.5 * sin(phib)
        for i in range(lon):
            theta1 = (i) * 2 * PI / lon
            theta2 = (i + 1) * 2 * PI / lon
            # left side of quad
            x1t = rt * cos(theta1)
            y1t = rt * sin(theta1)
            x1b = rb * cos(theta1)
            y1b = rb * sin(theta1)

            # right side of quad
            x2t = rt * cos(theta2)
            y2t = rt * sin(theta2)
            x2b = rb * cos(theta2)
            y2b = rb * sin(theta2)

            # specifying quads makes the code more efficient
            beginShape(QUADS)
            texture(img)
            # to add texture, write the vertex call like this:
            # vertex(x, y, z, u, v)
            # where u and v are the x and y locations of a particular point in
            # your texture image.
            #
            # u should be a function of the latitude, j, and v should
            # be a function of the longitude, i.
            #
            # specifically,
            # u1 = (width_of_image/lon) * i
            # and
            # vt = (height_of_image/lat) * j
            #
            # u2 and vb are the same, but with i+1 and j+1 substituted for i
            # and j

            u1 = (picWidth / lon) * i
            vt = (picHeight / lat) * j
            u2 = (picWidth / lon) * (i + 1)
            vb = (picHeight / lat) * (j + 1)

            vertex(x1t, y1t, zt, u1, vt)
            vertex(x1b, y1b, zb, u1, vb)
            vertex(x2b, y2b, zb, u2, vb)
            vertex(x2t, y2t, zt, u2, vt)

            endShape(CLOSE)