package plotting.table_shower;

import com.google.common.base.Preconditions;
import lombok.Builder;

@Builder
public record TableData(
        Double[][] doubleMat,
        String[][] stringMat,
        boolean isDouble
) {

    public static TableData ofDouble(Double[][] doubleMat) {
        return TableData.builder()
                .doubleMat(doubleMat).isDouble(true).build();
    }

    public static TableData ofString(String[][] stringMat) {
        return TableData.builder()
                .stringMat(stringMat).isDouble(false).build();
    }

   public boolean isString() {
        return !isDouble;
    }

    public Double[][] doubleMat() {
        Preconditions.checkArgument(isDouble,"String defined");
        return doubleMat;
    }

    public String[][] stringMat() {
        Preconditions.checkArgument(isString(),"Double defined");
        return stringMat;
    }
}
