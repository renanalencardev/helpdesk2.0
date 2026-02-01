package br.com.renanalencar.orderserviceapi.mapper;

import br.com.renanalencar.orderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    Order fromRequest(CreateOderRequest request);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status){
        return OrderStatusEnum.toEnum(status);
    }
}
