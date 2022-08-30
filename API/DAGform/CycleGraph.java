/**
source path:flink/flink-libraries/flink-gelly/src/main/java/org/apache/flink/graph/generator/CycleGraph.java
**/
package org.apache.flink.graph.generator;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Graph;
import org.apache.flink.types.LongValue;
import org.apache.flink.types.NullValue;
import org.apache.flink.util.Preconditions;

/**
 * @see <a href="http://mathworld.wolfram.com/CycleGraph.html">Cycle Graph at Wolfram MathWorld</a>
 */
public class CycleGraph extends GraphGeneratorBase<LongValue, NullValue, NullValue> {

    public static final int MINIMUM_VERTEX_COUNT = 2;

    // Required to create the DataSource
    private final ExecutionEnvironment env;

    // Required configuration
    private final long vertexCount;

    /**
     * An undirected {@link Graph} with {@code n} vertices where each vertex v<sub>i</sub> is
     * connected to adjacent vertices v<sub>(i+1)%n</sub> and v<sub>(i-1)%n</sub>.
     *
     * @param env the Flink execution environment
     * @param vertexCount number of vertices
     */
    public CycleGraph(ExecutionEnvironment env, long vertexCount) {
        Preconditions.checkArgument(
                vertexCount >= MINIMUM_VERTEX_COUNT,
                "Vertex count must be at least " + MINIMUM_VERTEX_COUNT);

        this.env = env;
        this.vertexCount = vertexCount;
    }

    @Override
    public Graph<LongValue, NullValue, NullValue> generate() {
        return new GridGraph(env)
                .addDimension(vertexCount, true)
                .setParallelism(parallelism)
                .generate();
    }
}
