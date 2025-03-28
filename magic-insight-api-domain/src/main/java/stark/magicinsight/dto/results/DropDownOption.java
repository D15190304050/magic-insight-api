package stark.magicinsight.dto.results;

import lombok.Data;

@Data
public class DropDownOption<T>
{
    private String title;
    private T value;
}
