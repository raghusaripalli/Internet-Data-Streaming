package IDS_4;

import java.util.Random;

public class Flow {
    private final String flowId;
    private final int elementCount;
    private final int[] elements;

    Flow(String flowId, int elementCount) {
        this.flowId = flowId;
        this.elementCount = elementCount;
        this.elements = new int[elementCount];
        Random random = new Random(Integer.MAX_VALUE);
        for (int i = 0; i < elementCount; i++) {
            elements[i] = random.nextInt();
        }
    }

    public String getFlowId() {
        return this.flowId;
    }

    public int getElementCount() {
        return this.elementCount;
    }

    public int[] getElements() {
        return this.elements;
    }
}
