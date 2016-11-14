package com.google.android.stardroid.retriever;

/**
 * 
 * @author landon
 * Transient class.
 *
 */

public class SQLTransient {
	private String author;
	private String transientId;
	private String dateAlerted;
	private String datePublished;
	private Double right_asencsion;
	private Double declination;
	
	public SQLTransient(String a, String t, String dA, String dP, Double r, Double d){
        author = a;
        transientId = t;
        dateAlerted = dA;
        datePublished = dP;
        right_asencsion = r;
        declination = d;
	}
	
	public String getAuthor(){
		return author;
	}

	public String getTransientId(){
		return transientId;
	}
	
	public String getDateAlerted(){
		return dateAlerted;
	}
	
	public String getDatePublished(){
		return datePublished;
	}
	
	public Double getRightAsencsion(){
		return right_asencsion;
	}
	
	public Double getDeclination(){
		return declination;
	}
	
}
