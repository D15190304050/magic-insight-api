package stark.magicinsight.domain.schemas;

import stark.coderaider.fluentschema.commons.schemas.SchemaSnapshotBase;
import java.util.List;

public class SchemaSnapshot extends SchemaSnapshotBase {
    @Override
    public void buildSchema() {
        schemaBuilder.table("account_base_info", builder -> {
            builder.column().name("phone_number").type("VARCHAR(20)").nullable(false).unique(false);
            builder.column().name("encrypted_password").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("username").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("nickname").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("avatar_url").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("email").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("phone_number_prefix").type("VARCHAR(200)").nullable(false).unique(false);
            builder.column().name("gender").type("VARCHAR(10)").nullable(false).unique(false);
            builder.column().name("state").type("INT").nullable(false).unique(false).defaultValue("0");
            builder.column().name("id").type("BIGINT").nullable(false).unique(false).autoIncrement(1);
            builder.column().name("creator_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("creation_time").type("DATETIME").nullable(true).unique(false).defaultValue("NOW()");
            builder.column().name("modifier_id").type("BIGINT").nullable(false).unique(false);
            builder.column().name("modification_time").type("DATETIME").nullable(true).unique(false)
                    .defaultValue("NOW()").onUpdate("NOW()");
            builder.primaryKey().columnName("id");
            builder.engine("InnoDB");
        });
        schemaBuilder.table("user_video_comment", builder -> {
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
        schemaBuilder.table("user_video_info", builder -> {
            builder.column().name("name_in_oss").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("marked_name_in_oss").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("title").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("cover_url").type("VARCHAR(255)").nullable(true).unique(false);
            builder.column().name("introduction").type("VARCHAR(1000)").nullable(true).unique(false);
            builder.column().name("summary_file_name").type("VARCHAR(255)").nullable(true).unique(false);
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
}