package model;

import java.util.ArrayList;

public class ResourceRecommendScore implements Comparable<ResourceRecommendScore> {

	private Resource resource;
	
	private ArrayList<Resource> borrowedResources;
	
	public ResourceRecommendScore() {
		resource = null;
		borrowedResources = new ArrayList<Resource>();
	}
	
	public int calculateLikeness() {
		int likeness=0;
		
		for(Resource borrowedResource: borrowedResources) {
			likeness+=borrowedResource.getLikenessScore(resource);
		}
		
		return likeness;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public ArrayList<Resource> getBorrowedResources() {
		return borrowedResources;
	}

	public void setBorrowedResources(ArrayList<Resource> borrowedResources) {
		this.borrowedResources = borrowedResources;
	}
	
	public int compareTo(ResourceRecommendScore other) {
		int likenessDifference = calculateLikeness() - other.calculateLikeness();
		if(likenessDifference>0) {
			return -1;
		} else if(likenessDifference<0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public String toString() {
		String result=resource.toString()+" score: "+calculateLikeness();
		return result;
	}
}
