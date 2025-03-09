package stark.reshaper.magicinsight.domain.dtos.params;

import lombok.Data;

@Data
public abstract class PaginationQueryParam
{
    private long pageCapacity;
    private long offset;

    public void setPaginationParam(PaginationRequestParam request)
    {
        this.pageCapacity = request.getPageCapacity();
        this.offset = this.pageCapacity * (request.getPageIndex() - 1);
    }
}
