package nz.ac.massey.a3;

/*
    This class handles the reflective properties of the surface.

    Makes sense to do it this way. How the shading is done, does depend on the material property
 */
public class Material {

    // Parameters of the Phong reflection model as described in the lectures
    private double alpha, beta, nShiny;

    Material(double a, double b, double n) {
        alpha = a;
        beta = b;
        nShiny = n;
    }

    /*
    Put in your implementation of the Phong model - the result should be some scale factor
    0<=f<=1 which will later be applied to the surface colour.

    Define methods and parameters as you see fit.
     */

    public double calculate(Point4 vNormal, Point4 vLight, Point4 vView, double shadowFactor) {
        vNormal.normalize();
        vLight.normalize();
        vView.normalize();

        // Ambient component
        double ambient = alpha;

        // Diffuse component
        double diffuse = Math.max(0, Point4.dot(vNormal, vLight));

        // Specular component
        double dotNL = Point4.dot(vNormal, vLight);
        Point4 vReflect = Point4.createVector(
                2.0 * dotNL * vNormal.x - vLight.x,
                2.0 * dotNL * vNormal.y - vLight.y,
                2.0 * dotNL * vNormal.z - vLight.z
        );
        vReflect.normalize();
        double specular = Math.pow(Math.max(0, Point4.dot(vReflect, vView)), nShiny);

        return Math.min(1.0, ambient + shadowFactor * (beta * diffuse + (1.0 - beta) * specular));
    }

}
