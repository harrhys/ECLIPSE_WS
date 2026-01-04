package com.mango.misc;

import java.util.ArrayList;
import java.util.List;

public class FileRecorder implements Recorder {
	
	private List<String> places = new ArrayList<String>(10);
	public String[] getPlaces() {
		return (String[]) places.toArray();
	}

	public void record(String place) {
		places.add(place);
	}

}
