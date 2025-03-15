package stark.reshaper.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250310205719 extends SchemaMigrationBase {
    @Override
    public void forward() {
        forwardBuilder.createTable("user_video_info", builder -> {
            builder.column().name("name_in_oss").type("VARCHAR(200)").nullable(true).unique(false);
            builder.column().name("title").type("VARCHAR(200)").nullable(true).unique(false);
            builder.column().name("cover_url").type("VARCHAR(500)").nullable(true).unique(false);
            builder.column().name("introduction").type("VARCHAR(200)").nullable(true).unique(false);
            builder.column().name("summary_file_path").type("VARCHAR(200)").nullable(true).unique(false);
            builder.column().name("id").type("BIGINT").nullable(false).unique(false).autoIncrement(1);
            builder.column().name("creator_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false);
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false);
            builder.primaryKey().columnName("id");
            builder.engine("InnoDB");
        });
        forwardBuilder.createTable("user_video_comment", builder -> {
            builder.column().name("user_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("video_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("content").type("VARCHAR(200)").nullable(true).unique(false);
            builder.column().name("parent_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("id").type("BIGINT").nullable(false).unique(false).autoIncrement(1);
            builder.column().name("creator_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false);
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false);
            builder.primaryKey().columnName("id");
            builder.engine("InnoDB");
        });
    }
    @Override
    public void backward() {
        backwardBuilder.dropTable("user_video_info");
        backwardBuilder.dropTable("user_video_comment");
    }
}