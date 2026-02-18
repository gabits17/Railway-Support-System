package system.data;

import system.base.Time;
import system.base.Train;

import java.io.Serial;

public record PassingTrainClass(Time time, Train train) implements PassingTrain {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    public PassingTrainClass {
    }

    public int compareTo(PassingTrain o) {
        int res = time.getHour() - o.time().getHour();
        if(res == 0) res = time.getMin() - o.time().getMin();
        if(res == 0) res = train.getNumber() - o.train().getNumber();
        return res;
    }
}
