

import java.util.ArrayList;

public class MyWebGraph {
	// this field is made public for testing purposes 
	public MyHashTable<String, WebVertex> vertexList; 
	
	public MyWebGraph () {
		vertexList = new MyHashTable<String, WebVertex>();
	}
	
	
	/*
	 * adds a vertex given a url
	 * returns true if the graph has changed as a result of this operation
	 * false otherwise. Note that the method should add the vertex only if a vertex
	 * associated to the given url is not there yet. 
	 */
	public boolean addVertex(String s) {
		// add a vertex to the graph if it's not there yet
		//ADD YOUR CODE BELOW HERE
		if(vertexList.get(s) == null){
			vertexList.put(s, new WebVertex(s));;
			return true;
		}
		

		return false;

		//ADD YOUR CODE ABOVE HERE
	}
	

	/*
	 * add an edge between two vertices.
	 * returns true if the graph has changed as a result of this operation
	 * false otherwise. An edge between two vertices can be added only if 
	 * both vertices belong to the graph. 
	 */
	public boolean addEdge(String s, String t) {
		//ADD YOUR CODE BELOW HERE
		WebVertex init = vertexList.get(s);
		WebVertex end = vertexList.get(t);

		if(init != null && end !=null){

			return init.addEdge(t);
		}




		return false;

		//ADD YOUR CODE ABOVE HERE
	}
	
	
	// Returns an ArrayList of urls that are neighbors with the given url
    public ArrayList<String> getNeighbors(String url) {
        return vertexList.get(url).getNeighbors();
    } 
    
    
    // Returns a list of all urls in the graph
    public ArrayList<String> getVertices() {
		//ADD YOUR CODE BELOW HERE
		return vertexList.keySet();

		//return null;

		//ADD YOUR CODE ABOVE HERE
    } 
    
    // Returns the list of pages that have links to v
    public ArrayList<String> getEdgesInto(String v) {
		//ADD YOUR CODE BELOW HERE
		ArrayList<String> blibli = new ArrayList<>();

		for (String s : vertexList.keySet()){
			WebVertex vertex = vertexList.get(s);
			if (vertex != null && vertex.containsEdge(v)){
				blibli.add(s);
			}
		}

		return blibli;

		//ADD YOUR CODE ABOVE HERE
    } 
    
    
    // Returns the number of links in the page with the specified url
    public int getOutDegree(String url) {
    	// NullPointerException raised if there's no vertex with specified url
        return vertexList.get(url).links.size();
    }        
    
    // sets the pageRank of a given url
    public void setPageRank(String url, double pr) {
        vertexList.get(url).rank = pr;
    }
    
    
    // returns the page rank of a given url. 
    // If the vertex with the specified url doesn't exist, returns 0
    public double getPageRank(String url) {
        if (vertexList.get(url)!=null) 
        	return (vertexList.get(url)).rank;
        
        return 0;
    }

    // sets the visited status of a given url
    public boolean setVisited(String url, boolean b) {
        if (vertexList.get(url)!=null) {
        	(vertexList.get(url)).visited = b;
        	return true;
        }
        return false;
    }
    
    // returns the visited status of a given url
    public boolean getVisited(String url) {
        if (vertexList.get(url)!=null) 
        	return (vertexList.get(url)).visited;
        
        return false;
    }

       
    public String toString() {
    	String info = "";
        for (String s: vertexList.keySet()) {
        	info += s.toString() + "\n";
        }
        return info;
    }
	
    public class WebVertex {
		private String url;
		public ArrayList<String> links;
		private boolean visited;
		private double rank;
		
		/*
		 *  Creates a vertex given a url.
		 *  This vertex has no edges yet. 
		 */
		public WebVertex (String url) {
			this.url = url;
			this.links = new ArrayList<String>();
			this.visited = false;
			this.rank = 0;
		}
		
		
		public boolean addEdge(String v) {
			if (!this.links.contains(v)) {
				this.links.add(v);
				return true;
			}
			return false;
		}
		
		
	    public ArrayList<String> getNeighbors() {
	        return this.links;
	    } 
	    
	    
	    public boolean containsEdge(String e) {
	    	return this.links.contains(e);
	    }
		
	    
		public String toString() {
			return this.url + "\t" + this.visited + "\t" + this.rank;
		}
		
	}
	
	

}
