package primitives;

public class Material {

    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Setter receiving a Double3 and setting kD (Diffuse factor) to it
     * @param kD
     * @return
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter receiving a Double3 and setting kS (Shininess factor) to it
     * @param kS
     * @return
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter receiving a double and setting all kD's (Diffuse factor) attributes to it
     * @param d
     * @return
     */
    public Material setKd(double d) {
        this.kD = new Double3(d);
        return this;
    }

    /**
     * Setter receiving a double and setting all kS's (Shininess factor) attributes to it
     * @param d
     * @return
     */
    public Material setKs(double d) {
        this.kS = new Double3(d);
        return this;
    }

    /**
     * Setter receiving an and setting
     * nShininess (Exponential reduction of light factor) to it
     * @param nShininess
     * @return
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}