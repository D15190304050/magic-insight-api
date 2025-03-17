package stark.magicinsight.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stark.magicinsight.domain.entities.AccountBaseInfo;

import java.util.List;

@Mapper
public interface AccountBaseInfoMapper
{
    AccountBaseInfo getAccountByUsername(String username);
    int updatePasswordByUsername(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword);
    List<AccountBaseInfo> getByUsernamePhoneNumberEmail(@Param("username") String username, @Param("phoneNumber") String phoneNumber, @Param("phoneNumberPrefix") String phoneNumberPrefix, @Param("email") String email);
    int insert(AccountBaseInfo accountBaseInfo);
    AccountBaseInfo getAccountByUserId(long userId);
    int countByUsername(String username);
    int countByUserId(long userId);
    Long getIdByUsername(String username);
}