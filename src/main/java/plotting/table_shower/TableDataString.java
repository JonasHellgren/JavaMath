package plotting.table_shower;

import com.google.common.base.Preconditions;
import lombok.Builder;

@Builder
public class TableDataString implements TableDataI{

    String[][] stringMat;

    public static TableDataString ofMat(String[][] stringMat) {
        return TableDataString.builder()
                .stringMat(stringMat)
                .build();
    }

    @Override
    public String read(int x, int y) {
        Preconditions.checkArgument(x<stringMat[0].length && y<stringMat.length,"Bad x/y");
        return stringMat[x][y];
    }
}
