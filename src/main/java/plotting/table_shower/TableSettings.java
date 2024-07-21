package plotting.table_shower;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record TableSettings(
        @NonNull int nX,
        @NonNull int nY,
        int fontSize,
        int maxCharsPerCell,
        int padding,
        String name,
        String fontName,
        String format
) {

    public static TableSettings defOfNxNy(int nX, int nY) {
        return TableSettings.builder()
                .nX(nX).nY(nY)
                .fontSize(12).fontName("Serif").format("%.2f")
                .maxCharsPerCell(4).padding(20)
                .name("")
                .build();
    }

}