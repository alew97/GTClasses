import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The starting vertex "
                + "cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The input graph "
                + "cannot be null.");
        } else if (graph.getAdjacencyList().get(start) == null) {
            throw new IllegalArgumentException("The starting vertex doesn't "
                    + "exist in the given graph.");
        }
        // Instantiating the returning list
        List<Vertex<T>> returnList = new java.util.ArrayList<>();

        // Instantiating a set for checking efficiency
        Set<Vertex<T>> checkSet = new java.util.HashSet<>();

        // Initializing the queue at starting point
        Queue<Vertex<T>> bfs = new java.util.LinkedList<>();
        bfs.add(start);

        // Goes until the queue is empty
        while (!bfs.isEmpty()) {
            // Dequeue the currently queued vertex
            Vertex<T> dequeuedVertex = bfs.remove();

            // Checks to see if the vertex is visited
            if (!checkSet.contains(dequeuedVertex)) {
                // Adds the dequeued vertex
                returnList.add(dequeuedVertex);
                checkSet.add(dequeuedVertex);

                // Queues all the adjacent vertices
                for (VertexDistancePair<T> vertexPair : graph
                    .getAdjacencyList().get(dequeuedVertex)) {
                    Vertex<T> adjVertex = vertexPair.getVertex();

                    // Checks to add unvisited vertices
                    if (!checkSet.contains(adjVertex)) {
                        bfs.add(adjVertex);
                    }
                }
            }
        }

        return returnList;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The starting vertex "
                + "cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The input graph "
                + "cannot be null.");
        } else if (graph.getAdjacencyList().get(start) == null) {
            throw new IllegalArgumentException("The starting vertex doesn't "
                    + "exist in the given graph.");
        }
        // Instantiating the returning list
        List<Vertex<T>> returnList = new java.util.ArrayList<>();

        // Instantiating a set for checking efficiency
        Set<Vertex<T>> checkSet = new java.util.HashSet<>();

        dfsRecursive(start, graph, returnList, checkSet);

        return returnList;
    }

    /**
     * A helper method to do DFS recursively instead of a stack data structure.
     *
     * @param currVertex the Vertex you are visiting
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph
     * @param returnList a List of vertices in the order that you visited them
     * @param checkSet a Set of vertices that represents the visited vertices
     */
    private static <T> void dfsRecursive(Vertex<T> currVertex, Graph<T> graph,
            List<Vertex<T>> returnList, Set<Vertex<T>> checkSet) {
        // Checks to see if the vertex is visited
        if (!checkSet.contains(currVertex)) {
            // Adds the currently visited vertex
            returnList.add(currVertex);
            checkSet.add(currVertex);

            // Runs DFS on all the adjacent vertices
            for (VertexDistancePair<T> vertexPair : graph
                .getAdjacencyList().get(currVertex)) {
                Vertex<T> adjVertex = vertexPair.getVertex();

                // Checks to run DFS on unvisited vertices
                if (!checkSet.contains(adjVertex)) {
                    dfsRecursive(adjVertex, graph, returnList, checkSet);
                }
            }
        }
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The starting vertex "
                + "cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The input graph "
                + "cannot be null.");
        } else if (graph.getAdjacencyList().get(start) == null) {
            throw new IllegalArgumentException("The starting vertex doesn't "
                    + "exist in the given graph.");
        }

        // Initializing the return map
        Map<Vertex<T>, Integer> returnMap = new java.util.HashMap<>();
        // Setting all vertices to "infinity"
        for (Vertex<T> vertex : graph.getAdjacencyList().keySet()) {
            if (vertex.equals(start)) {
                returnMap.put(vertex, 0);
            } else {
                returnMap.put(vertex, Integer.MAX_VALUE);
            }
        }

        // Instantiating a set for checking visited vertices
        Set<Vertex<T>> visitedVertexSet = new java.util.HashSet<>();

        // Initializing the priority queue from the starting point
        PriorityQueue<VertexDistancePair<T>> distances = new PriorityQueue<>();
        // Adds the starting vertex-distance pair to the priority queue
        VertexDistancePair<T> startPair = new VertexDistancePair<T>(start, 0);
        distances.add(startPair);

        // Goes until the priority queue is empty
        while (!distances.isEmpty()) {
            // Gets the smallest pair
            VertexDistancePair<T> smallestDistance = distances.remove();

            // Gets the vertex of the pair
            Vertex<T> pairVertex = smallestDistance.getVertex();
            int pairDistance = smallestDistance.getDistance();

            // Checks if the vertex is not visited
            if (!visitedVertexSet.contains(pairVertex)) {
                // Adds the pair's vertex to the visited vertex set
                visitedVertexSet.add(pairVertex);
                
                // Adds pair to the map if it's smaller than the previous
                if (pairDistance < returnMap.get(pairVertex)) {
                    returnMap.put(pairVertex, pairDistance);
                }

                // Adds all adjacent vertices of the edge's ending vertex
                for (VertexDistancePair<T> vertexPair : graph
                    .getAdjacencyList().get(pairVertex)) {
                    int prevDistance = pairDistance;
                    int currDistance = vertexPair.getDistance();
                    int totalDistance = prevDistance + currDistance;

                    // Adds the new adjacent vertex pairs
                    distances.add(new VertexDistancePair<T>(
                        vertexPair.getVertex(), totalDistance));
                }
            }
        }

        return returnMap;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be
     * one valid MST that can be formed. In addition, only an undirected graph
     * will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The starting vertex "
                + "cannot be null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("The input graph "
                + "cannot be null.");
        } else if (graph.getAdjacencyList().get(start) == null) {
            throw new IllegalArgumentException("The starting vertex doesn't "
                    + "exist in the given graph.");
        }
        // Instantiating the return edge set
        Set<Edge<T>> returnSet = new java.util.HashSet<>();

        // Instantiating a set for checking visited vertices
        Set<Vertex<T>> visitedVertexSet = new java.util.HashSet<>();

        // Initializing the priority queue from the starting point
        PriorityQueue<Edge<T>> mst = new PriorityQueue<>();
        // Adding all the starting edges to the priority queue
        for (VertexDistancePair<T> vertexPair : graph
            .getAdjacencyList().get(start)) {
            Edge<T> createdEdge = new Edge<T>(start, vertexPair.getVertex(),
                vertexPair.getDistance(), graph.isDirected());
            mst.add(createdEdge);
        }

        // Goes until the priority queue is empty
        while (!mst.isEmpty()) {
            // Gets the smallest edge
            Edge<T> smallestEdge = mst.remove();

            // Gets the vertices of the edge
            Vertex<T> startVertex = smallestEdge.getU();
            Vertex<T> endVertex = smallestEdge.getV();

            // Checks if either vertex of the smallest edge is not visited
            if (!(visitedVertexSet.contains(startVertex))
                || !(visitedVertexSet.contains(endVertex))) {
                // Adds the edge to the return set
                returnSet.add(smallestEdge);

                // Adds the edge's starting vertex to the visited vertex set
                visitedVertexSet.add(startVertex);
                visitedVertexSet.add(endVertex);

                // Adds all adjacent vertices of the edge's ending vertex
                for (VertexDistancePair<T> vertexPair : graph
                    .getAdjacencyList().get(endVertex)) {
                    mst.add(new Edge<T>(endVertex, vertexPair.getVertex(),
                        vertexPair.getDistance(), graph.isDirected()));
                }
            }
        }

        // Checks to see if all vertices are visited
        if (!visitedVertexSet.equals(graph.getAdjacencyList().keySet())) {
            return null;
        } else {
            return returnSet;
        }
    }

}
