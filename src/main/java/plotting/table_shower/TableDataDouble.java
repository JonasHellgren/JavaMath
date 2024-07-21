package plotting.table_shower;

import com.google.common.base.Preconditions;
import lombok.Builder;

@Builder
public class TableDataDouble implements TableDataI {
    public static final String DEFAULT_FORMAT = "%.2f";
    Double[][] doubleMat;
    String format;

    public static TableDataDouble ofMat(Double[][] stringMat) {
        return TableDataDouble.builder()
                .doubleMat(stringMat)
                .format(DEFAULT_FORMAT)
                .build();
    }

    @Override
    public String read(int x, int y) {
        Preconditions.checkArgument(x<doubleMat[0].length && y<doubleMat.length,"Bad x/y");
        return String.format(format, doubleMat[x][y]);
    }
}
