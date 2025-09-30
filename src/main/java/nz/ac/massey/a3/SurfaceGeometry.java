package nz.ac.massey.a3;

/*
    Deal with Ray intersection geometry for various surface types

    Work in local coordinates of the surface
 */

/*
    Use this to record all the necessary information from the ray shooting
    and ray-surface intersection calculations
 */
class HitRecord {
    public Point4 pSurface;
    public Point4 vNormal;
    public double u, v;
    public double tHit;
    public boolean isHit;
    public HitRecord() {
        pSurface = Point4.createPoint(0,0,0);
        vNormal = Point4.createVector(0,0,0);
    }
}

public abstract class SurfaceGeometry {

    protected final static double TINY = 0.01;

    /*
    Implementation details of these abstract methods will depend upon the
    type geometry of the surface
     */

    // Shoot a ray onto the surface
    public abstract boolean shoot(Ray ray, HitRecord hit);

    // Get the smallest box that completely surrounds the surface
    public abstract BoundingBox getBB();
 }

// Shooting to an infinite plane
class Plane extends SurfaceGeometry {

    private final Point4 pOrigin = Point4.createPoint(0,0,0);

    public Plane() {
    }

    @Override
    public boolean shoot(Ray ray, HitRecord hit) {
        // Local coordinates
        Point4 p0 = ray.pOrigin;
        Point4 p1 = ray.pDest;

        Point4 U = p1.minus(p0);
        Point4 V = pOrigin.minus(p0);

        double t = V.z / U.z;

        hit.vNormal = Point4.createVector(0, 0, 1);
        hit.pSurface = ray.calculate(t);
        hit.u = hit.pSurface.x + 0.5;
        hit.v = 0.5 - hit.pSurface.y;
        hit.tHit = t;
        hit.isHit = true;
        return true;
    }

    @Override
    public BoundingBox getBB() {
        return null;
    }
}

// Shooting onto a square - one type of bounded planar region
class Square extends Plane {
    private final static double h = 0.5;
    public boolean shoot(Ray ray, HitRecord hit) {
        super.shoot(ray, hit);
        hit.isHit = hit.tHit > 0 && hit.u >= 0 && hit.u <= 1 && hit.v >= 0 && hit.v <= 1;
        return hit.isHit;
    }
    public BoundingBox getBB() {
        return new BoundingBox(-h, h,-h, h,-TINY,TINY);
    }
}

/*
Provide an implementation for ray shooting onto a Spherical surface
 */

class Sphere extends SurfaceGeometry {
    private final double radius = 0.5; // Unit sphere
    private final Point4 center = Point4.createPoint(0, 0, 0);

    @Override
    public boolean shoot(Ray ray, HitRecord hit) {
        Point4 p0 = ray.pOrigin;
        Point4 p1 = ray.pDest;
        Point4 d = p1.minus(p0); // Ray direction
        Point4 oc = p0.minus(center); // Origin to center

        // Quadratic equation coefficients: at² + bt + c = 0
        double a = Point4.dot(d, d);
        double b = 2.0 * Point4.dot(oc, d);
        double c = Point4.dot(oc, oc) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            hit.isHit = false;
            return false;
        }

        // Find closest positive intersection
        double sqrt_disc = Math.sqrt(discriminant);
        double t1 = (-b - sqrt_disc) / (2 * a);
        double t2 = (-b + sqrt_disc) / (2 * a);

        double t = (t1 > TINY) ? t1 : t2;

        if (t <= TINY) {
            hit.isHit = false;
            return false;
        }

        hit.tHit = t;
        hit.pSurface = ray.calculate(t);

        // Normal vector (pointing outward)
        Point4 normal = hit.pSurface.minus(center);
        normal.normalize();
        hit.vNormal = normal;

        // Spherical texture coordinates
        double theta = Math.atan2(normal.z, normal.x);
        double phi = Math.acos(normal.y);
        hit.u = (theta + Math.PI) / (2 * Math.PI);
        hit.v = phi / Math.PI;

        hit.isShaded = true;
        hit.isHit = true;
        return true;
    }

    @Override
    public BoundingBox getBB() {
        return new BoundingBox(-radius, radius, -radius, radius, -radius, radius);
    }
}

