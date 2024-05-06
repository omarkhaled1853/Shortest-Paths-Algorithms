import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    void testDijkstra() {
        int vertices = 3;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        Graph graph = new Graph(vertices, edges, "Dijkstra");

        int[] parents = new int[vertices];
        Arrays.fill(parents,-2);
        int[] costs = new int[vertices];
        Arrays.fill(costs,Integer.MAX_VALUE);
        graph.Dijkstra(0, parents, costs);
        assertEquals(0, costs[0]);
        assertEquals(1, costs[1]);
        assertEquals(3, costs[2]);
        assertEquals(-1, parents[0]);
        assertEquals(0, parents[1]);
        assertEquals(1, parents[2]);
    }

    @Test
    void testBellmanFord() {
        int vertices = 3;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        Graph graph = new Graph(vertices, edges, "Bellman Ford");

        int[][] bfParents = new int[vertices][vertices];
        Arrays.fill(bfParents[0],-2);
        for (int i = 1; i < vertices; i++) {
            Arrays.fill(bfParents[i], -2);
        }
        int[][] bfCosts = new int[vertices][vertices];
        Arrays.fill(bfCosts[0], Integer.MAX_VALUE);
        for (int i = 1; i < vertices; i++) {
            Arrays.fill(bfCosts[i], Integer.MAX_VALUE);
        }
        boolean result = graph.getAllBellmanFord(bfParents, bfCosts);

        assertTrue(result);
        assertEquals(0, bfCosts[0][0]);
        assertEquals(1, bfCosts[0][1]);
        assertEquals(3, bfCosts[0][2]);
        assertEquals(-1, bfParents[0][0]);
        assertEquals(0, bfParents[0][1]);
        assertEquals(1, bfParents[0][2]);
    }

    @Test
    void testFloydWarshall() {
        int vertices = 3;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        Graph graph = new Graph(vertices, edges, "Floyd Warshall");

        long[][] distances = new long[vertices][vertices];
        int[][] next = new int[vertices][vertices];
        boolean result = graph.floyd_warshall(distances, next);

        assertTrue(result);
        assertEquals(0, distances[0][0]);
        assertEquals(1, distances[0][1]);
        assertEquals(3, distances[0][2]);

        StringBuilder path = graph.floydWarshallFindPath(0, 2, distances, next);
        assertNotNull(path);
        assertEquals("0 -> 1 -> 2", path.toString());
    }

    @Test
    void testNegativeCycleDetectionB() {
        int vertices = 3;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        edges.add(new edge(2, 0, -5)); // introducing a negative cycle
        Graph graph = new Graph(vertices, edges, "Bellman Ford");

        int[][] bfParents = new int[vertices][vertices];
        int[][] bfCosts = new int[vertices][vertices];
        boolean result = graph.getAllBellmanFord(bfParents, bfCosts);

        assertFalse(result);
    }
    @Test
    void testFloydWarshallWithDisconnectedGraph() {
        int vertices = 4;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        edges.add(new edge(2, 3, 3));
        Graph graph = new Graph(vertices, edges, "Floyd Warshall");

        long[][] distances = new long[vertices][vertices];
        int[][] next = new int[vertices][vertices];
        boolean result = graph.floyd_warshall(distances, next);

        assertTrue(result);
        assertEquals(0, distances[0][0]);
        assertEquals(1, distances[0][1]);
        assertEquals(3, distances[0][2]);
        assertEquals(6, distances[0][3]);

        StringBuilder path = graph.floydWarshallFindPath(0, 2, distances, next);
        assertNotNull(path);
        assertEquals("0 -> 1 -> 2", path.toString());
    }
    @Test
    void testFloydWarshallWithNegativeWeightCycle() {
        int vertices = 3;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        edges.add(new edge(2, 0, -5)); // introducing a negative weight cycle
        Graph graph = new Graph(vertices, edges, "Floyd Warshall");

        long[][] distances = new long[vertices][vertices];
        int[][] next = new int[vertices][vertices];
        boolean result = graph.floyd_warshall(distances, next);

        assertFalse(result); // Negative weight cycle detected
    }
    @Test
    void testFloydWarshall2() {
        int vertices = 6;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 4));
        edges.add(new edge(0, 3, 5));
        edges.add(new edge(1, 2, 1));
        edges.add(new edge(1, 4, 6));
        edges.add(new edge(2, 0, 2));
        edges.add(new edge(2, 3, 3));
        edges.add(new edge(3, 2, 1));
        edges.add(new edge(3, 4, 2));
        edges.add(new edge(4, 0, 1));
        edges.add(new edge(4, 3, 4));
        edges.add(new edge(5, 2, 1)); // additional edge for node 5
        Graph graph = new Graph(vertices, edges, "Floyd Warshall");

        long[][] distances = new long[vertices][vertices];
        int[][] next = new int[vertices][vertices];
        boolean result = graph.floyd_warshall(distances, next);

        assertTrue(result);
        assertEquals(0, distances[0][0]);
        assertEquals(4, distances[0][1]);
        assertEquals(5, distances[0][2]);
        assertEquals(5, distances[0][3]);
        assertEquals(7, distances[0][4]);
        assertEquals(Integer.MAX_VALUE, distances[0][5]); // distance to unreachable node 5 should be MAX_VALUE

        StringBuilder path = graph.floydWarshallFindPath(4, 2, distances, next);
        assertNotNull(path);
        assertEquals("4 -> 3 -> 2", path.toString());
    }

    @Test
    void testBellmanFord2() {
        int vertices = 6;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 4));
        edges.add(new edge(0, 3, 5));
        edges.add(new edge(1, 2, 1));
        edges.add(new edge(1, 4, 6));
        edges.add(new edge(2, 0, 2));
        edges.add(new edge(2, 3, 3));
        edges.add(new edge(3, 2, 1));
        edges.add(new edge(3, 4, 2));
        edges.add(new edge(4, 0, 1));
        edges.add(new edge(4, 3, 4));
        edges.add(new edge(5, 2, 1)); // additional edge for node 5
        Graph graph = new Graph(vertices, edges, "Bellman Ford");

        int[][] bfParents = new int[vertices][vertices];
        Arrays.fill(bfParents[0],-2);
        for (int i = 1; i < vertices; i++) {
            Arrays.fill(bfParents[i], -2);
        }
        int[][] bfCosts = new int[vertices][vertices];
        Arrays.fill(bfCosts[0], Integer.MAX_VALUE);
        for (int i = 1; i < vertices; i++) {
            Arrays.fill(bfCosts[i], Integer.MAX_VALUE);
        }
        boolean result = graph.getAllBellmanFord(bfParents, bfCosts);

        assertTrue(result);
        assertEquals(0, bfCosts[0][0]);
        assertEquals(4, bfCosts[0][1]);
        assertEquals(5, bfCosts[0][2]);
        assertEquals(5, bfCosts[0][3]);
        assertEquals(7, bfCosts[0][4]);
        assertEquals(Integer.MAX_VALUE, bfCosts[0][5]);
        assertEquals(-1, bfParents[0][0]); // Parent of node 0 is itself
        assertEquals(0, bfParents[0][1]);  // Parent of node 1 is 0
        assertEquals(1, bfParents[0][2]);  // Parent of node 2 is 1
        assertEquals(0, bfParents[0][3]);  // Parent of node 3 is 3
        assertEquals(3, bfParents[0][4]);  // Parent of node 4 is 4
        assertEquals(-2, bfParents[0][5]); // Node 5 is unreachable
    }
    @Test
    void testDijkstra2() {
        int vertices = 6;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 4));
        edges.add(new edge(0, 3, 5));
        edges.add(new edge(1, 2, 1));
        edges.add(new edge(1, 4, 6));
        edges.add(new edge(2, 0, 2));
        edges.add(new edge(2, 3, 3));
        edges.add(new edge(3, 2, 1));
        edges.add(new edge(3, 4, 2));
        edges.add(new edge(4, 0, 1));
        edges.add(new edge(4, 3, 4));
        edges.add(new edge(5, 2, 1)); // additional edge for node 5
        Graph graph = new Graph(vertices, edges, "Dijkstra");

        int[] parents = new int[vertices];
        Arrays.fill(parents,-2);
        int[] costs = new int[vertices];
        Arrays.fill(costs,Integer.MAX_VALUE);
        graph.Dijkstra(0, parents, costs);

        assertEquals(0, costs[0]);
        assertEquals(4, costs[1]);
        assertEquals(5, costs[2]);
        assertEquals(5, costs[3]);
        assertEquals(7, costs[4]);
        assertEquals(Integer.MAX_VALUE, costs[5]);

        // Check parent array values
        assertEquals(-1, parents[0]); // Parent of node 0 is itself
        assertEquals(0, parents[1]);  // Parent of node 1 is 0
        assertEquals(1, parents[2]);  // Parent of node 2 is 1
        assertEquals(0, parents[3]);  // Parent of node 3 is 3============0-->3
        assertEquals(3, parents[4]);  // Parent of node 4 is 4
        assertEquals(-2, parents[5]); // Node 5 is unreachable
    }


    @Test
    void testNegativeCycleDetectionB2() {
        int vertices = 6;
        ArrayList<edge> edges = new ArrayList<>();
        edges.add(new edge(0, 1, 1));
        edges.add(new edge(1, 2, 2));
        edges.add(new edge(2, 0, -5));
        Graph graph = new Graph(vertices, edges, "Bellman Ford");
        int[][] bfParents = new int[vertices][vertices];
        int[][] bfCosts = new int[vertices][vertices];
        boolean result = graph.getAllBellmanFord(bfParents, bfCosts);
        assertFalse(result);
    }



}
