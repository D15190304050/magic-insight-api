package stark.magicinsight.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import stark.coderaider.fluentschema.commons.NamingConvention;
import stark.coderaider.fluentschema.commons.annotations.Column;
import stark.coderaider.fluentschema.commons.annotations.Table;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(namingConvention = NamingConvention.LOWER_CASE_WITH_UNDERSCORE)
public class AccountBaseInfo extends DomainBase
{
    @Column(type = "VARCHAR(20)", nullable = false)
    private String phoneNumber;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String encryptedPassword;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String username;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String nickname;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String avatarUrl;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String email;

    @Column(type = "VARCHAR(200)", nullable = false)
    private String phoneNumberPrefix;

    @Column(type = "VARCHAR(10)", nullable = false)
    private String gender;

    @Column(defaultValue = "0", nullable = false)
    private int state;
}