package org.apache.flink.state.api;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.streaming.api.datastream.DataStream;

@PublicEvolving
@SuppressWarnings("WeakerAccess")
public final class OperatorTransformation {

    private OperatorTransformation() {}

    /**
     * Create a new {@link OperatorTransformation} from a {@link DataSet}.
     *
     * @param dataSet A dataset of elements.
     * @param <T> The type of the input.
     * @return A {@link OneInputOperatorTransformation}.
     * @deprecated use {@link #bootstrapWith(DataStream)} to bootstrap a savepoint using the data
     *     stream api under batch execution.
     */
    @Deprecated
    public static <T> OneInputOperatorTransformation<T> bootstrapWith(DataSet<T> dataSet) {
        return new OneInputOperatorTransformation<>(dataSet);
    }

    /**
     * Create a new {@link OneInputStateTransformation} from a {@link DataStream}.
     *
     * @param stream A data stream of elements.
     * @param <T> The type of the input.
     * @return A {@link OneInputStateTransformation}.
     */
    public static <T> OneInputStateTransformation<T> bootstrapWith(DataStream<T> stream) {
        return new OneInputStateTransformation<>(stream);
    }
}
