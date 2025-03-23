package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250323152348 extends SchemaMigrationBase {
    @Override
    public void forward() {
        forwardBuilder.alterColumn("user_video_info", "summary_file_path", ColumnMetadata.builder()
                .name("summary_file_name").type("VARCHAR(255)").nullable(true).unique(false).build());
    }
    @Override
    public void backward() {
        backwardBuilder.alterColumn("user_video_info", "summary_file_name", ColumnMetadata.builder()
                .name("summary_file_path").type("VARCHAR(255)").nullable(true).unique(false).build());
    }
}