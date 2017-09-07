//import java.net.NetPermission;
//import java.util.jar.Attributes.Name;

//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdOut;


public class BaseballElimination {
	private int N=0; 
	private String[] teamList;
	private ST<String, int[]> st;
	private ST<String, Integer> st_index;
	
	public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
	{
		In in = new In(filename);
		st = new ST<String, int[]>();
		st_index = new ST<String, Integer>();
		String name = null;
        this.N = in.readInt();
		teamList = new String[this.N];
        for (int i=0;i<this.N;i++) {
        	name = in.readString();
        	teamList[i] = name;
        	//System.out.println(name);
        	int[] tmp = new int[this.N+3];
        	for (int j=0;j<this.N+3;j++) {
        		tmp[j]=in.readInt();
        	}
        	st.put(name, tmp); 
        	st_index.put(name, i);
        }
		
	}
	public              int numberOfTeams()                        // number of teams
	{
		return N;
	}
	public Iterable<String> teams()                                // all teams
	{
		return st.keys();

	}
	public              int wins(String team)                      // number of wins for given team
	{
		int score[] = null;
		if (st.contains(team)) {
			score = st.get(team);
			return score[0];
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public              int losses(String team)                    // number of losses for given team
	{
		int score[] = null;
		if (st.contains(team)) {
			score = st.get(team);
			return score[1];
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	public              int remaining(String team)                 // number of remaining games for given team
	{
		int score[] = null;
		if (st.contains(team)) {
			score = st.get(team);
			return score[2];
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public              int against(String team1, String team2)    // number of remaining games between team1 and team2
	{
		int score1[] = null;
		int index2 = 0;
		if (st.contains(team1) && st.contains(team2)) {
			score1 = (int[]) st.get(team1);
			index2 = (int) st_index.get(team2);
			return score1[3+index2];
		}
		else{
			throw new IllegalArgumentException();
		}
	}
	private boolean isTrivial(String team)
	{
		boolean flag = false;
		for (int i=0;i<numberOfTeams();i++) {
			if (i==st_index.get(team)) continue;
			else if (wins(team)+remaining(team)-wins(teamList[i]) < 0) {
				flag = true; 
			}
		}		
		return flag;
	}
	public          boolean isEliminated(String team)              // is given team eliminated?
	{
		boolean flag=false;
		if (st.contains(team)) {
			if (isTrivial(team)) return true;
			//non-trivial case
			else {
				createFlow fn = new createFlow(team);
				FordFulkerson ff = new FordFulkerson(fn.G, 0, fn.G.V()-1); 
				if (ff.value()< fn.maxFlow) flag=true;
			}
			return flag;

		} else {
			throw new IllegalArgumentException();
		}
	}
	public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
	{
		if (st.contains(team)) {
			Queue<String> q_trivial = new Queue<String>();
			if (isTrivial(team)) {
				for (int i=0;i<numberOfTeams();i++) {
					if (i==st_index.get(team)) continue;
					else if (wins(team)+remaining(team)-wins(teamList[i]) < 0) {
						q_trivial.enqueue(teamList[i]);
					}
				}
				//System.out.println("trivial case");
			return q_trivial;
			}
			
			createFlow fn = new createFlow(team);
			Queue<String> q = new Queue<String>();
			FordFulkerson ff = new FordFulkerson(fn.G, 0, fn.G.V()-1);
			int i_norm=0;
			int index=0; //actual index in teamList
			if (isEliminated(team)) {
			for (int i=fn.G.V()-numberOfTeams();i<=fn.G.V()-1;i++) {
				i_norm = i-(numberOfTeams()-1)*(numberOfTeams()-2)/2;
				if (i_norm <=st_index.get(team)) {
					index = i_norm-1;
				} else {
					index = i_norm;
				}
				
				if  (ff.inCut(i)) {
					q.enqueue(teamList[index]);
					//System.out.println("index="+index);
				}
				}
			    return q;
			}
			else {
				return null;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
    private class createFlow {
    	
    	private FlowNetwork G;
    	private double maxFlow;
    	
    	private createFlow(String team)
    	{
    		int totalGameVertice = (numberOfTeams()-1)*(numberOfTeams()-2)/2; 
    		int V = 1+totalGameVertice+numberOfTeams()-1+1;
    		G = new FlowNetwork(V);

    		int teamNum = st_index.get(team);
    		int gameV=0;
    		maxFlow=0;
			for (int i=0;i<numberOfTeams();i++) {
				if (i==teamNum) continue;
				else {
					for (int j=i+1;j<numberOfTeams();j++) {
						if (j==teamNum) continue;
						//System.out.println(i+"--->"+j+" "+against(teamList[i],teamList[j]));
						gameV++;
						G.addEdge(new FlowEdge(0, gameV, against(teamList[i],teamList[j])));
						maxFlow=maxFlow+against(teamList[i],teamList[j]);
						int i_norm=i;
						int j_norm=j;
						if (i>teamNum) i_norm = i-1;
						if (j>teamNum) j_norm = j-1;
						G.addEdge(new FlowEdge(gameV,totalGameVertice+1+i_norm,Double.POSITIVE_INFINITY));
						G.addEdge(new FlowEdge(gameV,totalGameVertice+1+j_norm,Double.POSITIVE_INFINITY));
						//System.out.println(gameV+"->"+(totalGameVertice+1+i_norm));
						//System.out.println(gameV+"->"+(totalGameVertice+1+j_norm));
						//System.out.println("maxFlow ->"+maxFlow);
					}
				}
			}
				
			int i_vertice = 0;
			for (int i=0;i<numberOfTeams()-1;i++) {
				i_vertice = i;
				if (i>=teamNum) i_vertice++; 
					G.addEdge(new FlowEdge(totalGameVertice+1+i,V-1,wins(team)+remaining(team)-wins(teamList[i_vertice])));
					//System.out.println(totalGameVertice+1+i+"-->"+(V-1)+"   "+wins(team)+" "+remaining(team)+" "+(-wins(teamList[i_vertice])));
			}    	
    	}
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//seballElimination be = new BaseballElimination("teams7.txt");
		//System.out.println(be.numberOfTeams());
		//System.out.println(be.against("England","France"));
		//System.out.println(be.isEliminated("China"));
		//System.out.println(be.certificateOfElimination("China"));
	    BaseballElimination division = new BaseballElimination("teams5.txt");
	    System.out.println(division.losses("hahahaha"));
	    System.out.println(division.certificateOfElimination("hahahaha"));

	    /*for (String team : division.teams()) { if (division.isEliminated(team)) { StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }*/ 
	}

}
