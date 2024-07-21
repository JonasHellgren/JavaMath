package plotting.table_shower;

import com.google.common.base.Preconditions;
import lombok.Builder;

@Builder
public class TableDataDouble implements TableDataI {
    Double[][] doubleMat;
    TableSettings settings;

    public static TableDataDouble ofMatAndSettings(Double[][] stringMat, TableSettings settings) {
        return TableDataDouble.builder()
                .doubleMat(stringMat)
                .settings(settings)
                .build();
    }

    @Override
    public String read(int x, int y) {
        Preconditions.checkArgument(y<doubleMat[0].length && x<doubleMat.length,"Bad x/y");
        return String.format(settings.format(), doubleMat[x][y]);
    }
}
