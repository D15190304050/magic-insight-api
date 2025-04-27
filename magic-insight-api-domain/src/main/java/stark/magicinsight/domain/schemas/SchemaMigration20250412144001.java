package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250412144001 extends SchemaMigrationBase {
    @Override
    public void forward() {
        forwardBuilder.addColumn("user_video_info", ColumnMetadata.builder().name("marked_name_in_oss")
                .type("VARCHAR(255)").nullable(true).unique(false).build());
    }
    @Override
    public void backward() {
        backwardBuilder.dropColumn("user_video_info", "marked_name_in_oss");
    }
}