public class Transient {
	private String id;
	private double rightAscension;
	private double declination;
	private String discoveryDate;
	private double magnitude;
	private boolean sdss;
	private String lastObservationDate;
	private int lightCurve;
	private boolean fc;
	private String classification;
	
	public Transient(String id, double ra, double dec, String dDate, double mag, boolean sdss, String lDate, int lc, boolean fc, String cl){
		this.id = id;
		rightAscension = ra;
		declination = dec;
		discoveryDate = dDate;
		magnitude = mag;
		this.sdss = sdss;
		lastObservationDate = lDate;
		lightCurve = lc;
		this.fc = fc;
		classification = cl;
	}
	
	public String getID(){
		return id;
	}
	
	public double getRightAscension(){
		return rightAscension;
	}
	
	public String getDiscoveryDate(){
		return discoveryDate;
	}
	
	public double getMagnitude(){
		return magnitude;
	}
	
	public boolean isSDSS(){
		return sdss;
	}
	
	public String getLastObservationDate(){
		return lastObservationDate;
	}
	
	public int getLightCurve(){
		return lightCurve;
	}
	
	public boolean getFc(){
		return fc;
	}
	
	public String getClassification(){
		return classification;
	}
	
	public String toString(){
		return id + ", " + rightAscension + ", " + declination + ", " + discoveryDate + ", " + magnitude + ", " 
				+ sdss + ", " + lastObservationDate + ", " + lightCurve + ", " + fc + ", " + classification;
	}

}
