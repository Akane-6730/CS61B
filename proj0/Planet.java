public class Planet {

    public double xxPos; // Its current x position
    public double yyPos; // Its current y position
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName; // The name of the file that corresponds to the image that depicts the body

    private static final double G = 6.67e-11;

    /*Body Constructor 1 */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /*Body Constructor 2 (Copy constructor) */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /* a method called calcDistance that calculates the distance between two Bodys */
    public double calcDistance(Planet p) {
	double dx = this.xxPos - p.xxPos;
	double dy = this.yyPos - p.yyPos;
	return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
	return G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
    }

    public double calcForceExertedByX(Planet p) {
	return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
	return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
	double sum = 0;
	for (int i = 0; i < allPlanets.length; i++) {
	    if (!this.equals(allPlanets[i])) {
		sum += this.calcForceExertedByX(allPlanets[i]);
	    }
	}
	return sum;
    }


    public double calcNetForceExertedByY(Planet[] allPlanets) {
	double sum = 0;
	for (int i = 0; i < allPlanets.length; i++) {
	    if (!this.equals(allPlanets[i])) {
		sum += this.calcForceExertedByY(allPlanets[i]);
	    }
	}
	return sum;
    }

    public void update(double dt, double fx, double fy) {

	/* Calculate the acceleration. */
	double ax = fx / this.mass;
	double ay = fy / this.mass;

	/* Update the new velocity. */
	this.xxVel = this.xxVel + dt * ax;
	this.yyVel = this.yyVel + dt * ay;

	/* Update the new position. */
	this.xxPos = this.xxPos + dt * xxVel;
	this.yyPos = this.yyPos + dt * yyVel;
    }

    /* Draw itself */
    public void draw() {
         StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}
