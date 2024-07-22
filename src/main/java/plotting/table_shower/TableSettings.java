package plotting.table_shower;

import lombok.Builder;
import lombok.NonNull;
import lombok.With;

import java.util.Optional;

@Builder
public record TableSettings(
        @NonNull Integer nX,
        @NonNull Integer nY,
        int fontSize,
        int maxCharsPerCell,
        int padding,
        String name,
        String fontName,
        String format,
        String xName,
        String yName,
        @With Optional<String[]> colNames,
        @With Optional<String[]> rowNames,
        @With double nXstart,
        @With double nXend,
        @With double nYstart,
        @With double nYend

) {

    public static TableSettings defOfNxNy(int nX, int nY) {
        return TableSettings.builder()
                .nX(nX).nY(nY)
                .fontSize(12).fontName("Serif").format("%.2f")
                .maxCharsPerCell(4).padding(20)
                .name("")
                .xName("x").yName("y")
                .colNames(Optional.empty()).rowNames(Optional.empty())
                .nXstart(0).nXend(nX)
                .nYstart(0).nYend(nY)
                .build();
    }

    public double nXstep() {
      return (nXend-nXstart)/nX;
    }

    public double nYstep() {
        return (nYend-nYstart)/nY;
    }

    public boolean isNofColNamesOk() {
        return colNames().isPresent() && colNames().orElseThrow().length == nX();
    }

    public boolean isNofRowNamesOk() {
        return rowNames().isPresent() && rowNames().orElseThrow().length == nY();
    }


}