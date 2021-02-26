import com.sun.management.VMOption;

public class HashSet {

    private int[] values;

    int[] valuesCopy;
    boolean[] isRemoved;
    private int size = 0;// FIXME more member variables here
    private int ratio;
    private int initArrayLength;
    private int originalSize;
    private int BucketsProbed = 0;
    private int probe;
    private int Bucket = 0;
    private int value = 0;
    private int FirstHash = 0;


    //create variable implementing probe strategy (as in simple linear probe)
    // set equal to bucket variable reset bucket as nee


    ProbeStrategy strategy;

    public HashSet(int initArrayLength, int ratio, ProbeStrategy strategy) {
        this.strategy = strategy;
        values = new int[initArrayLength];// FIXME to be written
        isRemoved = new boolean[initArrayLength];
        for (int i = 0; i < initArrayLength; i++) {
            values[i] = -1;
        }
        this.ratio = ratio;
        originalSize = initArrayLength;

        BucketsProbed = 0;

    }




    public boolean add(int value) {
        if (this.ratio * this.size >= values.length) {
            this.DyResize();
        }

        int attemptNum = 0;
        FirstHash = value % values.length;
        int index = this.strategy.probe(values.length, FirstHash, attemptNum);
        int InDeck;

        while (values[index] != -1 && attemptNum < values.length) {
            if (values[index] == value) {
                return false;
            }
            attemptNum++;
            index = this.strategy.probe(values.length, FirstHash, attemptNum);
        }
        // make sure value is not elsewhere in case a remove happened before adding
        InDeck = index;
        while((values[InDeck] != -1 || isRemoved[InDeck] == true)
                && attemptNum < values.length) {
            if (values[InDeck] == value) {
                return false;
            } else {
                attemptNum++;
                InDeck = this.strategy.probe(values.length, FirstHash, attemptNum);
            }
        }
        values[index] = value;
        isRemoved[index] = false;
        size++;
        return true; // FIXME remove when ready
    }


    private void DyResize() {
        this.isRemoved = new boolean[2 * values.length + 1];
        int[] temp = this.values;
        this.values = new int[2 * values.length + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = -1;
        }
        int Copy = 0;
        int Current = 0;
        while (Copy < this.size) {
            if (temp[Current] != -1) {
                this.add(temp[Current]);
                size--;
                Copy++;
            }
            Current++;
        }

    }
    

    public int size() {
        return size;
    }


    public boolean contains(int value) {
        FirstHash = value % values.length;
        BucketsProbed = 0;
        Bucket = strategy.probe(values.length, FirstHash, BucketsProbed);
        for (BucketsProbed = 0; BucketsProbed < values.length; BucketsProbed++) {
            if (values[BucketsProbed] == value) {
                return true;
            }
        }
        return false;
    }




    public boolean remove(int value) {
        //DON'T TOUCH THIS!! IT'S FINE!!
        BucketsProbed = 0;
        FirstHash = value % values.length;
        Bucket = strategy.probe(values.length, FirstHash, BucketsProbed);
        //!!-1 = empty since start, -2 = empty after removal!!
        while ((values[Bucket] >= 0) && (BucketsProbed < values.length)) {
            for (int i = 0; i < values.length; i ++) {
                if (values[i] == value) {
                    values[i] = -2;
                    size = size - 1;
                    return true;
                }
            }
            BucketsProbed = BucketsProbed + 1;
        }
        //FIXME to be written
        return false; // FIXME remove when ready
    }


    public int[] toArray() {
        int[] result = new int[this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i];
        }
        return result;
    }

    public static void main(String[] args) {
        ProbeStrategy linear = new SimpleLinearProbe();
        HashSet set = new HashSet(7, 3, linear);

        int[] numbers = {20, 5, 3, 9};
        for (int i = 0; i < numbers.length; i++) {
            // add the number
            set.add(numbers[i]);

            // print out the array
            int[] array = set.toArray();
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[j] + ", ");
            }
            System.out.println();
        }
    }
}