// Abe Choi
// Extra Credit: Dijkstra.java

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


class Node{

	public Node(Node Refrence, int index,  int weight){

		this.Weight = weight;
		this.RefrenceNode = Refrence;
	    this.index =index;

	}
	
	public String GetIndex(Map<Integer, String> map){
		
		return map.get(this.index);

	}

	public int index;
	public Node RefrenceNode;
	public int Weight = 0;
} // END NODE CLASS


public class Dijkstra{
	
	public static void main(String [] args){
		
		Map<String, Integer> map = new HashMap<String, Integer>();

		// GENERATES NODES
		for(int i = 0; i <= 25; ++i){

			String ascii = (char)(65 + i) + "";
			map.put(ascii, new Integer(i));

		}
		
		Map<Integer, String> valueToCharacter = new HashMap<>();

		for(Entry<String, Integer> entry : map.entrySet()){

			valueToCharacter.put(entry.getValue(), entry.getKey());
		}

		
		int graph[][] = new int[26][26];
		
		try{

			BufferedReader br = new BufferedReader(new FileReader("NetworkGraph.txt"));
			String line = br.readLine();
			while(line != null){

				line = line.replaceAll("\\s+", "");
				
				String[] tokens = line.split(",");
				
				graph[map.get(tokens[0])][map.get(tokens[1])] = Integer.parseInt(tokens[2]);
				graph[map.get(tokens[1])][map.get(tokens[0])] = Integer.parseInt(tokens[2]);
				
				line = br.readLine();
				 
			}
			
		} catch(FileNotFoundException e){
			e.printStackTrace();

		} catch(IOException e){
			e.printStackTrace();

		}

		Map<Integer, Node> nodeMapping = new HashMap<>();
		HashSet<Integer> excluded = new HashSet<Integer>();

		Node currNode = new Node(null,0,0);

		nodeMapping.put(currNode.index,currNode);
		
		while(true){

			for(int x = 0; x < graph[currNode.index].length; x++){

				if(!excluded.contains(x) && graph[currNode.index][x] > 0){

					Node n = new Node(currNode,x,graph[currNode.index][x] + currNode.Weight);
					if(!nodeMapping.containsKey(new Integer(x)) || n.Weight < nodeMapping.get(new Integer(x)).Weight)
						nodeMapping.put(new Integer(x),n);

				}
			}
			
	
			if(nodeMapping.size() == excluded.size())
				break;
			
			excluded.add(new Integer(currNode.index));
						
			
			System.out.print("{");
			for(int x =0 ;x <  excluded.size(); x++){

				System.out.print(valueToCharacter.get(excluded.toArray()[x]));
				if(excluded.size()-1 != x)
					System.out.print(",");

			}

			System.out.print("}\n");
	
			for( Entry<Integer, Node> n : nodeMapping.entrySet()){

				System.out.println(valueToCharacter.get(n.getValue().index) +"," + n.getValue().Weight);

			}
			
			System.out.println();

			
			int max = Integer.MAX_VALUE;
			for( Entry<Integer, Node> n : nodeMapping.entrySet()){

				if(!excluded.contains(n.getKey())){

					if(n.getValue().Weight < max){

						max = n.getValue().Weight;
						currNode = n.getValue();
					}
				}	
			}
		}
		
		Node lastNode = nodeMapping.get(map.get("G"));
		String output = " ";
		while(lastNode != null){

			if(lastNode.RefrenceNode == null)
				output += valueToCharacter.get(lastNode.index);
			else
				output += valueToCharacter.get(lastNode.index) + ">-";
			lastNode = lastNode.RefrenceNode;
		}

		System.out.println("THE SHORTEST PATH: " + new StringBuilder(output).reverse().toString());
	
	} // END MAIN
} // END DIJKSTRA CLASS


