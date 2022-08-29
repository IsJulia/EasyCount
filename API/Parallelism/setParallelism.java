   public O setParallelism(int parallelism) {
        OperatorValidationUtils.validateParallelism(parallelism);

        this.parallelism = parallelism;

        @SuppressWarnings("unchecked")
        O returnType = (O) this;
        return returnType;
    }
