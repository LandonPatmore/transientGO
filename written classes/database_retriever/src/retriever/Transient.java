package retriever;

/**
 * 
 * @author landon
 * Transient class.
 *
 */

public class Transient {
	private String id;
	private Double ra;
	private Double dec;
	private Double ut_date;
	private Double mag;
	private String last_time;
	private String light_curve;
	
	public Transient(String i, Double r, Double d, Double ut, Double m, String last, String light){
		id = i;
		ra = r;
		dec = d;
		ut_date = ut;
		mag = m;
		last_time = last;
		light_curve = light;
	}
	
	public String get_id(){
		return id;
	}

	public Double get_ra(){
		return ra;
	}
	
	public Double get_dec(){
		return dec;
	}
	
	public Double get_ut(){
		return ut_date;
	}
	
	public Double get_mag(){
		return mag;
	}
	
	public String get_last(){
		return last_time;
	}
	
	public String get_light(){
		return light_curve;
	}

	@Override
	public String toString() {
		return "Transient [id=" + id + ", ra=" + ra + ", dec=" + dec + ", ut_date=" + ut_date + ", mag=" + mag
				+ ", last_time=" + last_time + ", light_curve=" + light_curve + "]";
	}
	
	
	
	
	
}
