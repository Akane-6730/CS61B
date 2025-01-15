public class NBody {
    /* Method readradius : return the radius of the universe in the given file. */
    public static double readRadius(String file_name) {
	In universe = new In(file_name);
	int num = universe.readInt();
	double radius = universe.readDouble();
	return radius;
    }

    /* Method readplanets : return an array of Planets corresponding to the planets in the file. */
    public static Planet[] readPlanets(String file_name) {

	In universe = new In(file_name);
	int num = universe.readInt();
	double radius = universe.readDouble();

	Planet[] PlanetArray = new Planet[num];

	for (int i = 0; i < num; i++) {
	    double xxPos = universe.readDouble();
            double yyPos = universe.readDouble();
	    double xxVel = universe.readDouble();
	    double yyVel = universe.readDouble();
	    double mass = universe.readDouble();
	    String imgFileName = universe.readString();
	    PlanetArray[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
	}

	return PlanetArray;
    }

    public static void main(String[] args) {

	/* Collecting All Needed Input */
	double T = Double.parseDouble(args[0]);
	double dt = Double.parseDouble(args[1]);
	String filename = args[2];

	Planet[] Planets = readPlanets(filename);
	double radius = readRadius(filename);
	int num = Planets.length;

	/* Drawing The Background. */

	/* Set the scale that matches the radius of the universe. */
	StdDraw.setScale(-1 * radius, radius);

	/* Clears the drawing window. */
	StdDraw.clear();

	/* Draw the background */
	StdDraw.picture(0, 0, "images/starfield.jpg");

	/* Draw all of the planets */
	for (int i = 0; i < Planets.length; i++) {
	    Planets[i].draw();
	}

	/* Creating an Animation */

	/* A graphics technique to prevent flickering in the animation. */
	StdDraw.enableDoubleBuffering();

	double time = 0;
	int waitTimeMilliseconds = 10;

	while(time < T) {
	    double[] xForces = new double[num];
	    double[] yForces = new double[num];

	    for (int i = 0; i < num; i++) {
		xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
		yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
	    }

	    for (int i = 0; i < num; i++) {
		Planets[i].update(dt, xForces[i], yForces[i]);
	    }

	    /* Drawing The Background. */

	    /* Set the scale that matches the radius of the universe. */
	    StdDraw.setScale(-1 * radius, radius);

	    /* Clears the drawing window. */
	    StdDraw.clear();

	    /* Draw the background */
	    StdDraw.picture(0, 0, "images/starfield.jpg");

	    /* Draw all of the planets */
	    for (int i = 0; i < Planets.length; i++) {
		Planets[i].draw();
	    }

	    StdDraw.show();
	    StdDraw.pause(waitTimeMilliseconds);

	    time += dt;
	}

	/* Printing the Universe */
	StdOut.printf("%d\n", Planets.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < Planets.length; i++) {
	    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
	}

    }
}
