package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250323152039 extends SchemaMigrationBase {
    @Override
    public void forward() {
        forwardBuilder.dropColumn("user_video_info", "section_id");
        forwardBuilder.dropColumn("user_video_info", "label_ids");
    }
    @Override
    public void backward() {
        backwardBuilder.addColumn("user_video_info",
                ColumnMetadata.builder().name("section_id").type("BIGINT").nullable(false).unique(false).build());
        backwardBuilder.addColumn("user_video_info",
                ColumnMetadata.builder().name("label_ids").type("VARCHAR(300)").nullable(true).unique(false).build());
    }
}