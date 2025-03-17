package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.ColumnMetadata;
import stark.coderaider.fluentschema.commons.schemas.KeyMetadata;
import stark.coderaider.fluentschema.commons.schemas.SchemaMigrationBase;
import java.util.List;

public class SchemaMigration20250317235538 extends SchemaMigrationBase {
    @Override
    public void forward() {
        setInitialized(false);
        forwardBuilder.createTable("account_base_info", builder -> {
            builder.column().name("phone_number").type("VARCHAR(20)").nullable(false).unique(false);
            builder.column().name("encrypted_password").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("username").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("nickname").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("avatar_url").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("email").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("phone_number_prefix").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("gender").type("VARCHAR(10)").nullable(false).unique(false);
            builder.column().name("state").type("INT").nullable(false).unique(false);
            builder.column().name("id").type("BIGINT").nullable(false).unique(false).autoIncrement(1);
            builder.column().name("creator_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false).defaultValue("NOW()");
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false)
                    .defaultValue("NOW()").onUpdate("NOW()");
            builder.primaryKey().columnName("id");
            builder.engine("InnoDB");
        });
        forwardBuilder.createTable("user_video_info", builder -> {
            builder.column().name("name_in_oss").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("title").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("cover_url").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("section_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("label_ids").type("VARCHAR(300)").nullable(true).unique(false);
            builder.column().name("introduction").type("VARCHAR(1000)").nullable(true).unique(false);
            builder.column().name("summary_file_path").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("id").type("BIGINT").nullable(false).unique(false).autoIncrement(1);
            builder.column().name("creator_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false).defaultValue("NOW()");
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false)
                    .defaultValue("NOW()").onUpdate("NOW()");
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
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false).defaultValue("NOW()");
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false)
                    .defaultValue("NOW()").onUpdate("NOW()");
            builder.primaryKey().columnName("id");
            builder.engine("InnoDB");
        });
    }
    @Override
    public void backward() {
        backwardBuilder.dropTable("account_base_info");
        backwardBuilder.dropTable("user_video_info");
        backwardBuilder.dropTable("user_video_comment");
    }
}