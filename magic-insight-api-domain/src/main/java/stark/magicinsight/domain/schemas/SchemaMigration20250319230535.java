package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250319230535 extends SchemaMigrationBase {
    @Override
    public void forward() {
        forwardBuilder.alterColumn("account_base_info", "state", ColumnMetadata.builder().name("state").type("INT")
                .nullable(false).unique(false).defaultValue("0").build());
    }
    @Override
    public void backward() {
        backwardBuilder.alterColumn("account_base_info", "state",
                ColumnMetadata.builder().name("state").type("INT").nullable(false).unique(false).build());
    }
}