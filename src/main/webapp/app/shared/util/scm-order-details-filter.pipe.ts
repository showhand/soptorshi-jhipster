import { Pipe, PipeTransform } from '@angular/core';
import { SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

@Pipe({ name: 'scmOrderDetailsFilter' })
export class ScmOrderDetailsFilterPipe implements PipeTransform {
    transform(value: SupplyOrderDetails[], args: number): SupplyOrderDetails[] {
        return value && value.length > 0 ? value.filter(val => val.supplyOrderId === args) : [];
    }
}
