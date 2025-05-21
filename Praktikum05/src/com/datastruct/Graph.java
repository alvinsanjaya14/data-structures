/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */
package com.datastruct;

import java.util.*;

class Edge<T> { 
	private T neighbor; //connected vertex
	private int weight; //weight
	
	//Constructor, Time O(1) Space O(1)
	public Edge(T v, int w) {
		this.neighbor = v; 
		this.weight = w;
	}

	public void setNeighbor(T neighbor) {
		this.neighbor = neighbor;
	}
	public T getNeighbor() {
		return neighbor;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
	
	//Time O(1) Space O(1)
	@Override
	public String toString() {
		return "(" + neighbor + "," + weight + ")";
	}
}

public class Graph<T> { 
    //Map<T, LinkedList<Edge<T>>> adj;
	private Map<T, MyLinearList<Edge<T>>> adj;
	boolean directed;
	
	//Constructor, Time O(1) Space O(1)
	public Graph (boolean type) { 
        adj = new HashMap<>();
		directed = type; // false: undirected, true: directed
	}

    //Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		adj.putIfAbsent(a, new MyLinearList<>()); //add node
		adj.putIfAbsent(b, new MyLinearList<>()); //add node
		Edge<T> edge1 = new Edge<>(b, w);
		adj.get(a).pushQ(edge1);//add(edge1); //add edge
		if (!directed) { //undirected
			Edge<T> edge2 = new Edge<>(a, w);
			adj.get(b).pushQ(edge2);
		}			
	}

    //Print graph as hashmap, Time O(V+E), Space O(1)
	public void printGraph() {
		for (T key: adj.keySet()) {
			//System.out.println(key.toString() + " : " + adj.get(key).toString());
            System.out.print(key.toString() + " : ");
			MyLinearList<Edge<T>> thelist = adj.get(key);
			Node<Edge<T>> curr = thelist.head;
			while(curr != null) {
				System.out.print(curr.getData());
				curr = curr.getNext();
			}
			System.out.println();
		}
	}

	//DFS 
	public void DFS(T src) {
		Set<T> visited = new HashSet<>();
    	MyLinearList<T> stack = new MyLinearList<>();
    stack.pushS(src);
    
    System.out.print("DFS traversal from " + src + ": ");
    while (!stack.isEmpty()) {
        T current = stack.remove();
        if (!visited.contains(current)) {
            visited.add(current);
            System.out.print(current + " ");
            MyLinearList<Edge<T>> neighbors = adj.get(current);
            if (neighbors != null) {
                List<T> tempNeighbors = new ArrayList<>();
                Node<Edge<T>> curr = neighbors.head;
                while (curr != null) {
                    tempNeighbors.add(curr.getData().getNeighbor());
                    curr = curr.getNext();
                }
                Collections.reverse(tempNeighbors);
                for (T neighbor : tempNeighbors) {
                    if (!visited.contains(neighbor)) {
                        stack.pushS(neighbor);
                    }
                }
            }
        }
    }
    System.out.println();
	}

	//BFS
	public void BFS(T src) { 
		Set<T> visited = new HashSet<>();
    MyLinearList<T> queue = new MyLinearList<>();
    visited.add(src);
    queue.pushQ(src);

    System.out.print("BFS traversal from " + src + ": ");
    while (!queue.isEmpty()) {
        T current = queue.remove();
        System.out.print(current + " ");
        MyLinearList<Edge<T>> neighbors = adj.get(current);
        if (neighbors != null) {
            Node<Edge<T>> curr = neighbors.head;
            while (curr != null) {
                T neighbor = curr.getData().getNeighbor();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.pushQ(neighbor);
                }
                curr = curr.getNext();
            }
        }
    }
    System.out.println(); 
	}
}
