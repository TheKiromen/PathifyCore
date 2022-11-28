
# PathifyCore

Algorithm for converting images into a single path. It can be used for example as a means of drawing image sketch in a single stroke. I wrote every algorithm myself instead of using existing (and probably better) solutions because I wanted more flexibility to change how they work internally, and I wanted to challenge myself. Example application using it can be found [here](https://github.com/TheKiromen/Pathify).


## Documentation

**To install just download the .java files and add them to your project.**

[ImageToPathConverter.java](ImageToPathConverter.java) - Facade class with everything already integrated. Loads specified image and converts it to array of pixels.

[GrayscaleConversion.java](GrayscaleConversion.java) - Converts pixel array into grayscale.

[GaussianBlur.java](GaussianBlur.java) - Applies Gaussian blur to the pixel array. You can manually change the kernel by modifying kernel ale weight variables. Weight is a sum of all values in the kernel.

[SobelEdgeDetection.java](SobelEdgeDetection.java) - Performs standard Sobel edge detection. Returns both gradients, angles and final edge magnitude wrappend in [SobelResult](SobelResult.java) class.

[SobelResult.java](SobelResult.java) - Wrapper for data obtained in Sobel edge detection. Contains x and y gradients, magnitude and angle values of edges.

[CannyEdgeDetection.java](CannyEdgeDetection.java) - Performs basic Canny edge detection. Takes [SobelResult](SobelResult.java) as an input, thresholds can be modified.

[PathCreator.java](PathCreator.java) - Creates paths from edges obtained in Canny and then connects them to form a single path.

[PathifiedImage.java](PathifiedImage.java) - Contains results from all processing steps. Can be used to save each step as an image.


## Features

- Image blurring
- Grayscale conversion
- Canny and Sobel edge detection
- Conversion of edges into a single path


## Authors

- [@dkrucze](https://github.com/TheKiromen)
