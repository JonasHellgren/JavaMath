package plotting.table_shower;

import lombok.Builder;
import lombok.NonNull;
import lombok.With;

@Builder
public record TableSettings(
        @NonNull int nX,
        @NonNull int nY,
        int fontSize,
        int maxCharsPerCell,
        int padding,
        String name,
        String fontName,
        String format,
        String xName,
        String yName,
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

}